import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/** 
 * You can modify this class as you see fit, as long as you correctly update the
 * global centroids.
 */
public class ClusterToPointReducer extends Reducer<Point, Point, Point, Point>
{
	/**
	* Add up all the Points in values and divide by the number of Points in them. Output a key value
	* pair where the key is the new centroid and the value is an ArrayList of all the Points in values.
	*
	*
	*/
	public void reduce(Point key, Iterator<Point> values, Context context)throws IOException, InterruptedException{
		int numberOfPoints = 0;
		if (!(values.hasNext())){
			return;
		}
		ArrayList<Point> accumulatedArrayList = new ArrayList<Point>();
		Point temp = values.next();
		Point accumulatedPoint = temp;
		accumulatedArrayList.add(temp);
		numberOfPoints++;
		while (values.hasNext()){
			temp = values.next();
			accumulatedArrayList.add(temp);
			accumulatedPoint = Point.addPoints(accumulatedPoint, temp);
			numberOfPoints++;
		}
		accumulatedPoint = Point.multiplyScalar(accumulatedPoint, 1/((float) numberOfPoints));
		context.write(accumulatedPoint, accumulatedArrayList);
	}
}
