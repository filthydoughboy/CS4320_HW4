import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * You can modify this class as you see fit.  You may assume that the global
 * centroids have been correctly initialized.
 */
public class PointToClusterMapper extends Mapper<Text, Text, Text, Text>
{
	public PointToClusterMapper(){

	}

	public void map(Point key, ArrayList<Point> values, OutputCollector<Point, Point> argOutput, Reporter argReporter){
		ArrayList<Point> tempCentroids = KMeans.centroids;
		for (int i = 0; i < values.size(); i++){
			Point newCentroid = key;
			for (int j = 0; j < tempCentroids.size(); j++){
				if (Point.distance(tempCentroids.get(j),values.get(i)) < Point.distance(newCentroid,values.get(i))){
					newCentroid = tempCentroids.get(j);
				}
			}
			argOutput.collect(newCentroid, values.get(i));
		}
	}
}
