
/**
 * Player class
 * @author  Lucita Fortes
 * @version Dec 9, 2017
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player
{
    // instance variables - replace the example below with your own
    private Room currentRoom;
    private String playerName;
    //private ArrayList<Item> items;
    private HashMap<String, Item> items;
    
    /**
     * Constructor for objects of class Player
     */
    public Player(Room room, String name)
    {
        // initialise instance variables
        this.currentRoom = room;
        this.playerName = name;
        //items = new ArrayList<Item>();
        items = new HashMap<String, Item>();
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }
    
    /**
     *  set currentRoom
     */
    public void setCurrentRoom(Room room)
    {
        currentRoom = room;
    }
    
    public Item getItem(String item)
    {
        return items.get(item) ;
    }
    
    /** 
     *  add item
     */
    public void addItem(String name, Item item)
    {
        items.put(name, item);
    }
    
    public boolean removeItem(String name)
    {
        return items.remove(name, items.get(name));
    }
    
    public void items()
    {
        for (Map.Entry me : items.entrySet()) {
          Item i = (Item)me.getValue();
          System.out.println("Item - " + i.getDescription() + " Weight - " + i.getWeight());
        }
    }
}
