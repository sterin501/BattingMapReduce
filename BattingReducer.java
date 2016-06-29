import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;



public class BattingReducer extends Reducer<Text, Text, Text, Text> {



		@Override
		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {


int maxTemp = Integer.MIN_VALUE;
String valuetoprint="";
for (Text value: values) {

String [] tokens = value.toString().split("\\s+");

int  run = Integer.parseInt(tokens[0]);

maxTemp = Math.max(maxTemp, run);



if ( maxTemp == run)
valuetoprint=value.toString();



}
context.write(key, new Text(valuetoprint));


}
}




