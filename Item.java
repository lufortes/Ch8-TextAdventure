
/**
 * Item class 
 *
 * @author Lucita Fortes
 * @version Dec. 8, 2017
 */
public class Item
{
    // instance variables - replace the example below with your own
    private double weight;
    private String description;

    /**
     * Constructor for objects of class Item
     */
    public Item(String description, double weight)
    {
        // initialise instance variables
        this.weight = weight;
        this.description = description;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public double getWeight()
    {
        // put your code here
        return weight;
    }
    
    /**
     * Return description
     */
    public String getDescription()
    {
        return description;
    }
}
