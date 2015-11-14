import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;

/** 
 * You can modify this class as you see fit, as long as you correctly update the
 * global centroids.
 */
public class ClusterToPointReducer extends Reducer<Point, Point, Point, Point>
{
	/**
	* Add up all the Points in iteratorVersion and divide by the number of Points in them. Output a key value
	* pair where the key is the new centroid and the value is an ArrayList of all the Points in iteratorVersion.
	*
	*
	*/
	@Override
	protected void reduce(Point key, Iterable<Point> value, Context context) throws IOException, InterruptedException{
		int numberOfPoints = 0;
		Iterator<Point> iteratorVersion = value.iterator();
		if (!(iteratorVersion.hasNext())){
			return;
		}
		ArrayList<Point> accumulatedArrayList = new ArrayList<Point>();
		Point temp = iteratorVersion.next();
		Point accumulatedPoint = temp;
		accumulatedArrayList.add(temp);
		numberOfPoints++;
		while (iteratorVersion.hasNext()){
			temp = iteratorVersion.next();
			accumulatedArrayList.add(temp);
			accumulatedPoint = Point.addPoints(accumulatedPoint, temp);
			numberOfPoints++;
		}
		accumulatedPoint = Point.multiplyScalar(accumulatedPoint, 1/((float) numberOfPoints));
		context.write(key, accumulatedPoint);
		ArrayList<Point> tempCentroids = KMeans.centroids;
		int index = tempCentroids.indexOf(key);
		tempCentroids.set(index, accumulatedPoint);
	}
}
