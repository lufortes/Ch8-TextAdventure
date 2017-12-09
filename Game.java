/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.08.10
 */
import java.util.*;
public class Game 
{
    private Parser parser;
    //private Room currentRoom;
    private Player player;
    private Stack<Room> st;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        st = new Stack<Room>();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office;
        Item chair, heineken, budweiser, burner, rabbit, desk, lamp;
        
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);
        
        // create the items
        
        chair = new Item("Chair",50);
        heineken = new Item("Heineken",10);
        budweiser = new Item("Budweiser",30);
        burner = new Item("Burner",40);
        rabbit = new Item("Rabbit",20);
        desk = new Item("Desk",200);
        lamp = new Item("Lamp",50);

        // add items
        
        theater.addItem("Chair", chair );
        pub.addItem("Heineken",heineken);
        pub.addItem("Budweiser",budweiser);
        lab.addItem("Burner", burner);
        lab.addItem("Rabbit", rabbit);
        office.addItem("Desk",desk);
        office.addItem("Lamp",lamp);
        
        player = new Player(outside, "Lucy");
        
        //currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case LOOK:
                look();
                break;
                
            case EAT:
                eat();
                break;
                
            case BACK:
                back();
                break;
                
            case TAKE:
                takeItem(command);
                break;
                
            case DROP:
                dropItem(command);
                break;
            
            case ITEMS:
                listItems();
                break;
            
            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            st.push(player.getCurrentRoom());
            player.setCurrentRoom(nextRoom);
            System.out.println(player.getCurrentRoom().getLongDescription());
            player.getCurrentRoom().displayItems(); 
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * "Look" was entered. Return the current room's long description
     */
    private void look()
    {
        System.out.println(player.getCurrentRoom().getLongDescription());
    }
    
    /**
     * "Eat" was entered. Print string.
     */
    private void eat()
    {
        System.out.println("You have eaten now and you are not hungry any more.");
    }
    
    /**
     * "Back" was entered. Go back to where you came from.
     */
    private void back()
    {
        
        if (!st.empty())
        {
            player.setCurrentRoom(st.pop());
            System.out.println(player.getCurrentRoom().getLongDescription()); 
            player.getCurrentRoom().displayItems();
        }
    }
    
    private void takeItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Take what?");
            return;
        }

        String item = command.getSecondWord();

        //Try to pick up item.
        Item itemTaken = player.getCurrentRoom().getItem(item);

        if (itemTaken == null) {
            System.out.println("There is no item!");
        }
        else {
        //    st.push(player.getCurrentRoom());
            player.addItem(item,itemTaken);
            player.getCurrentRoom().removeItem(item);
            System.out.println(player.getCurrentRoom().getLongDescription());
            player.getCurrentRoom().displayItems(); 
        }
    }
    
    private void dropItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            //if there is no second word, we don't know where to go...
            System.out.println("Drop what?");
        }

        String s = command.getSecondWord();
        Item item = player.getItem(s);

        if (player.removeItem(s))
        {
            player.getCurrentRoom().addItem(s, item);
            System.out.println(player.getCurrentRoom().getLongDescription());
            player.getCurrentRoom().displayItems(); 
        }
    }
    
    private void listItems()
    {
        player.items();
    }
}
