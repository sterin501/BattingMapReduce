import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.commons.logging.*;





public class BattingMapper extends Mapper<LongWritable, Text, Text, Text> 

{



@Override
  public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
         
  

String line = value.toString();

 try {

String [] tokens = line.split("\\,+");
String year = tokens[1];
String player =  tokens[0];
String runs =  tokens[8];

//int batt;
//batt = Integer.parseInt(tokens[7]);


context.write(new Text(year), new Text(runs+" "+player));

      }catch (ArrayIndexOutOfBoundsException e) {
                     //  e.printStackTrace();
            }



  }



}



