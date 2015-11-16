import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * You can modify this class as you see fit.  You may assume that the global
 * centroids have been correctly initialized.
 */
public class PointToClusterMapper extends Mapper<Text, Text, Point, Point>
{
	/**
	* For each Point in values, iterate through all the centroids and find the one closest to that Point.
	* Then, add each Point as a value with the closest centroid as the key.
	*/
	@Override
	protected void map(Text key, Text values, Context context) throws IOException, InterruptedException{
		ArrayList<Point> tempCentroids = KMeans.centroids;
		Point thePoint = new Point(key.toString());
		Point newCentroid = new Point(tempCentroids.get(0));
		for (int j = 1; j < tempCentroids.size(); j++){
			if (Point.distance(tempCentroids.get(j), thePoint) < Point.distance(newCentroid, thePoint)){
				newCentroid = new Point(tempCentroids.get(j));
			}
		}
		context.write(newCentroid, thePoint);
	}
}
