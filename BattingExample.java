import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;








public class BattingExample {


public static void main(String[] args) throws Exception {
Configuration conf = new Configuration();

        //    conf.set("fs.default.name", "hdfs://10.184.37.158:9000");
        //    conf.set("mapred.job.tracker", "10.184.37.158:9001");


/*conf.set("yarn.resourcemanager.address", "172.31.27.147:8050"); // see step 3
conf.set("mapreduce.framework.name", "yarn"); 
conf.set("fs.defaultFS", "hdfs://172.31.27.147:9000"); // see step 2
conf.set("yarn.application.classpath",        
             "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,"
                + "$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,"
                + "$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,"
                + "$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*");


*/

String[] programArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
if (programArgs.length != 2) {
System.err.println("Usage: BattingExample <in> <out>");
System.exit(2);
}
Job job = Job.getInstance(conf, "BattingExample");
job.setJarByClass(BattingExample.class);
job.setMapperClass(BattingMapper.class);
job.setCombinerClass(BattingReducer.class);
job.setReducerClass(BattingReducer.class);

//job.setNumReduceTasks(3);
job.setOutputKeyClass(Text.class);
job.setOutputValueClass(Text.class);
FileInputFormat.addInputPath(job, new Path(programArgs[0]));
FileOutputFormat.setOutputPath(job, new Path(programArgs[1]));
// Submit the job and wait for it to finish.

job.waitForCompletion(true);
//System.exit(job.waitForCompletion(true) ? 0 : 1);



}
}


/*

java -cp Batting.jar:$CLASSPATH BattingExample /SampleDataSterin/Batting.csv /SampleDataSterin/NSD5123wejjr


*/
