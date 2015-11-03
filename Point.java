import java.io.*; // DataInput/DataOuput
import java.util.ArrayList;
import java.util.Collections;
import org.apache.hadoop.io.*; // Writable

/**
 * A Point is some ordered list of floats.
 * 
 * A Point implements WritableComparable so that Hadoop can serialize
 * and send Point objects across machines.
 *
 * NOTE: This implementation is NOT complete.  As mentioned above, you need
 * to implement WritableComparable at minimum.  Modify this class as you see fit.
 */
public class Point {
    /**
     * Construct a Point with the given dimensions [dim]. The coordinates should all be 0.
     * For example:
     * Constructing a Point(2) should create a point (x_0 = 0, x_1 = 0)
     */

    ArrayList<Float> attributes; // Field that stores ArrayList of attributes of the Point

    public Point(int dim)
    {
        attributes = new ArrayList<Float>();
        for (int i = 0; i < dim; i++) 
        {
            attributes.add(0.0);
        }
    }

    /**
     * Construct a point from a properly formatted string (i.e. line from a test file)
     * @param str A string with coordinates that are space-delimited.
     * For example: 
     * Given the formatted string str="1 3 4 5"
     * Produce a Point {x_0 = 1, x_1 = 3, x_2 = 4, x_3 = 5}
     */
    public Point(String str)
    {
        attributes = new ArrayList<Float>();
        for(String element: str.split(" "))
        {
            int intOfString = Integer.parseInt(element);
            attributes.add(intOfString);
        }
    }

    /**
     * Copy constructor
     */
    public Point(Point other)
    {
        ArrayList<Float> attributes = new ArrayList<Float>();
        for (int i = 0; i < other.getDimension(); i++) 
        {
            attributes.add(other.get(i));
        }
    }

    /**
     * @return The dimension of the point.  For example, the point [x=0, y=1] has
     * a dimension of 2.
     */
    public int getDimension()
    {
        return attributes.size();
    }

    /**
     * Converts a point to a string.  Note that this must be formatted EXACTLY
     * for the autograder to be able to read your answer.
     * Example:
     * Given a point with coordinates {x=1, y=1, z=3}
     * Return the string "1 1 3"
     */
    public String toString()
    {
        String toReturn = "";
        for (int i = 0; i < this.getDimension() - 1; i++)
        {
            String asString = Integer.toString(attributes.get(i)); 
            toReturn += asString + " ";
        }
        String asString = Integer.toString(attributes.get(this.getDimension() - 1));
        toReturn += asString + " ";
        return toReturn;
    }

    /**
     * One of the WritableComparable methods you need to implement.
     * See the Hadoop documentation for more details.
     * You should order the points "lexicographically" in the order of the coordinates.
     * Comparing two points of different dimensions results in undefined behavior.
     * @return -1 if oject > point, 0 if equal, 1 if object < point 
     */
    public int compareTo(Object o)
    {   
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i) < o.get(i))
            {
                return -1
            }
            else if (this.get(i) > o.get(i))
            {
                return 1
            }
        }
        return 0;
    }

    /**
     * @return The L2 distance between two points.
     */
    public static final float distance(Point x, Point y)
    {
        float sum = 0.0;
        for (int i = 0; i < x.size(); i++) {
            sum += (x.get(i) - y.get(i)) * (x.get(i) - y.get(i))
        }
        return (float)(Math.sqrt(sum));
    }

    /**
     * @return A new point equal to [x]+[y]
     */
    public static final Point addPoints(Point x, Point y)
    {
        for ()
    }

    /**
     * @return A new point equal to [c][x]
     */
    public static final Point multiplyScalar(Point x, float c)
    {
        System.out.println("TODO");
        System.exit(1);
        return null;
    }
}
