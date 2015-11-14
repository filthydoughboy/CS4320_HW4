import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;

import java.io.IOException;
import java.util.ArrayList;

public class UpdateJobRunner
{

    static float EPSILON = (float) .00001;

    /**
     * Create a map-reduce job to update the current centroids.
     * @param jobId Some arbitrary number so that Hadoop can create a directory "<outputDirectory>/<jobname>_<jobId>"
     *        for storage of intermediate files.  In other words, just pass in a unique value for this
     *        parameter.
     * @param The input directory specified by the user upon executing KMeans, in which the points
     *        to find the KMeans point files are located.
     * @param The output directory for which to write job results, specified by user.
     * @precondition The global centroids variable has been set.
     */
    public static Job createUpdateJob(int jobId, String inputDirectory, String outputDirectory)
        throws IOException
    {
        Job our_job = new Job(new Configuration(), "ours" + jobId);
        our_job.setJarByClass(KMeans.class);
        our_job.setMapperClass(PointToClusterMapper.class);
        our_job.setMapOutputKeyClass(Point.class);
        our_job.setMapOutputValueClass(Point.class);
        our_job.setReducerClass(ClusterToPointReducer.class);
        our_job.setOutputKeyClass(Point.class);
        our_job.setOutputValueClass(Point.class);
        FileInputFormat.addInputPath(our_job, new Path(inputDirectory));
        FileOutputFormat.setOutputPath(our_job, new Path(outputDirectory));
        our_job.setInputFormatClass(KeyValueTextInputFormat.class);
        return our_job;
    }

    /**
     * Run the jobs until the centroids stop changing.
     * Let C_old and C_new be the set of old and new centroids respectively.
     * We consider C_new to be unchanged from C_old if for every centroid, c, in 
     * C_new, the L2-distance to the centroid c' in c_old is less than [epsilon].
     *
     * Note that you may retrieve publically accessible variables from other classes
     * by prepending the name of the class to the variable (e.g. KMeans.one).
     *
     * @param maxIterations   The maximum number of updates we should execute before
     *                        we stop the program.  You may assume maxIterations is positive.
     * @param inputDirectory  The path to the directory from which to read the files of Points
     * @param outputDirectory The path to the directory to which to put Hadoop output files
     * @return The number of iterations that were executed.
     */
    public static int runUpdateJobs(int maxIterations, String inputDirectory,
        String outputDirectory)
    {
        int iterations = 0;
        ArrayList<Point> oldCentroid = KMeans.centroids;
        try{
            Job tempJob = createUpdateJob(iterations, inputDirectory, outputDirectory);
            tempJob.waitForCompletion(true);
        } catch (Exception e){
            System.out.println("You messed up in UpdateJobRunner runUpdateJobs 1");
        }
        iterations++;
        while (compareCentroids(oldCentroid, KMeans.centroids) && iterations < maxIterations){
            oldCentroid = KMeans.centroids;
            try{
                Job tempJob = createUpdateJob(iterations, inputDirectory, outputDirectory);
                tempJob.waitForCompletion(true);
            } catch (Exception e){
                System.out.println("You messed up in UpdateJobRunner runUpdateJobs 2");
            }
            iterations++;
        }
        return iterations;
    }


    private static boolean compareCentroids(ArrayList<Point> argOld, ArrayList<Point> argNew){
        for (int i = 0; i < argOld.size(); i++){
            if (Point.distance(argOld.get(i),argNew.get(i)) > EPSILON){
                return false;
            }
        }
        return true;
    }
}
