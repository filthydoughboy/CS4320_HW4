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
public class PointToClusterMapper extends Mapper<Point, ArrayList<Point>, Point, Point>
{
	/**
	* For each Point in values, iterate through all the centroids and find the one closest to that Point.
	* Then, add each Point as a value with the closest centroid as the key.
	*/
	@Override
	protected void map(Point key, ArrayList<Point> values, Context context) throws IOException, InterruptedException{
		ArrayList<Point> tempCentroids = KMeans.centroids;
		for (int i = 0; i < values.size(); i++){
			Point newCentroid = key;
			for (int j = 0; j < tempCentroids.size(); j++){
				if (Point.distance(tempCentroids.get(j),values.get(i)) < Point.distance(newCentroid,values.get(i))){
					newCentroid = tempCentroids.get(j);
				}
			}
			context.write(newCentroid, values.get(i));
		}
	}
}
