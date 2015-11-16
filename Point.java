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
public class Point implements WritableComparable<Point>{
    /**
     * Construct a Point with the given dimensions [dim]. The coordinates should all be 0.
     * For example:
     * Constructing a Point(2) should create a point (x_0 = 0, x_1 = 0)
     */

    ArrayList<Float> attributes; // Field that stores ArrayList of attributes of the Point

    public Point(){
        attributes = new ArrayList<Float>();
    }

    public Point(int dim)
    {
        attributes = new ArrayList<Float>();
        for (int i = 0; i < dim; i++) 
        {
            attributes.add(new Float(0.0));
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
            Float intOfString = Float.parseFloat(element);
            attributes.add(intOfString);
        }
    }

    /**
     * Copy constructor
     */
    public Point(Point other)
    {
        attributes = new ArrayList<Float>();
        for (int i = 0; i < other.getDimension(); i++) 
        {
            attributes.add(new Float(other.attributes.get(i)));
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
    * Sets the attributes field to the argument.
    */
    public void setAttributes(ArrayList<Float> arg0){
        attributes = arg0;
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
            String asString = Float.toString(attributes.get(i)); 
            toReturn += asString + " ";
        }
        String asString = Float.toString(attributes.get(this.getDimension() - 1));
        toReturn += asString;
        return toReturn;
    }

    /**
     * One of the WritableComparable methods you need to implement.
     * See the Hadoop documentation for more details.
     * You should order the points "lexicographically" in the order of the coordinates.
     * Comparing two points of different dimensions results in undefined behavior.
     * @return -1 if oject > point, 0 if equal, 1 if object < point 
     */
    // @Override
    // public int compareTo(Object o)
    // {   
    //     if (!(o instanceof Point)){
    //         return 1;
    //     }
    //     Point argPoint = (Point) o;
    //     for (int i = 0; i < this.getDimension(); i++) {
    //         if (this.attributes.get(i).compareTo(argPoint.attributes.get(i)) == 0)
    //         {
    //             continue;
    //         }
    //         return this.attributes.get(i).compareTo(argPoint.attributes.get(i));
    //     }
    //     return 0;
    // }

    @Override
    public int compareTo(Point p){
        for (int i = 0; i < this.getDimension(); i++){
            if (this.attributes.get(i).compareTo(p.attributes.get(i)) == 0){
                continue;
            }
            return this.attributes.get(i).compareTo(p.attributes.get(i));
        }
        return 0;
    }

    public void readFields(DataInput in){
        attributes = new ArrayList<Float>();
        while(true){
            try{
                Float temp = in.readFloat();
                attributes.add(temp);
            } catch (Exception e){
                break;
                //e.printStackTrace();
            }
        }
    }

    public void write(DataOutput out) throws IOException{
        for (int i = 0; i < attributes.size(); i++){
            out.writeFloat(attributes.get(i));
        }
    }

    /**
     * @return The L2 distance between two points.
     */
    public static final float distance(Point x, Point y)
    {
        float sum = new Float(0.0);
        for (int i = 0; i < x.getDimension(); i++) {
            sum += (x.attributes.get(i) - y.attributes.get(i)) * (x.attributes.get(i) - y.attributes.get(i));
        }
        return (float)(Math.sqrt(sum));
    }

    /**
     * @return A new point equal to [x]+[y]
     */
    public static final Point addPoints(Point x, Point y)
    {
        Point temp = new Point(x.getDimension());
        ArrayList<Float> tempAttributes = new ArrayList<Float>();
        for (int i = 0; i < x.getDimension(); i++){
            tempAttributes.add(x.attributes.get(i) + y.attributes.get(i));
        }
        temp.setAttributes(tempAttributes);
        return temp;
    }

    /**
     * @return A new point equal to [c][x]
     */
    public static final Point multiplyScalar(Point x, float c)
    {
        Point temp = new Point(x.getDimension());
        ArrayList<Float> tempAttributes = new ArrayList<Float>();
        for (int i = 0; i < x.getDimension(); i++){
            tempAttributes.add(x.attributes.get(i) * c);
        }
        temp.setAttributes(tempAttributes);
        return temp;
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof Point)){
            return false;
        }
        Point temp = (Point) o;
        return this.compareTo(temp) == 0;
    }
}
