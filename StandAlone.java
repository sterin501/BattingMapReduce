import java.io.*;
import java.util.*;


public class StandAlone {


public static void main (String[] args) {




    File file = new File(args[0]);  // Reading content dIDs 

     InputStream input = null;
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    DataInputStream dis = null;


try {
      fis = new FileInputStream(file);

      // Here BufferedInputStream is added for fast reading.
      bis = new BufferedInputStream(fis);
      dis = new DataInputStream(bis);


// HashMap<String, String> mapper =   new HashMap<String, String>();

TreeMap<String, String> mapper = new TreeMap<String, String>();

 int i=1;

      while (dis.available() != 0) {

  // System.out.println(dis.readLine());

       try {



    String [] tokens = dis.readLine().split("\\,+");

     String year = tokens[1];
     String player =  tokens[0];
     String runs =  tokens[8];
     String value = player+","+runs;
          

                     if (mapper.containsKey(year))
                           {

                                String newvalue = mapper.get(year);
                                 newvalue = newvalue+"~"+value;
                                 mapper.put(year,newvalue);
                                  
                           }
                       
                        else 
                              mapper.put(year,value);
                            


  // System.out.println ( i++ + " " + year + " " + player + " " + runs);


            }catch (ArrayIndexOutOfBoundsException e) {
                     //  e.printStackTrace();
            }


         

       //    reduce (year,player,runs);


 

                                   }


 File Outputfile = new File("output.txt");
 FileWriter fw = new FileWriter(Outputfile.getAbsoluteFile());
 BufferedWriter bw = new BufferedWriter(fw);   


for ( String key :  mapper.keySet())

{

// System.out.println(key + " - " + mapper.get(key));
//System.out.println("In the year " + key);
String token1[] = mapper.get(key).split("\\~+");

 int maxTemp = Integer.MIN_VALUE;
 String toPrint="";

for ( String we : token1 )

         {

           //  System.out.println (we);
                String token2[] = we.split("\\,+");
             //    System.out.print(" player " +  token2[0]+ " Scored " + token2[1] + "\n");

               
                int  run = Integer.parseInt(token2[1]);

                maxTemp = Math.max(maxTemp, run);


                       if ( maxTemp == run)
                //   System.out.println("In the year " + key + " player " + token2[0] + "Scored max runs  " + token2[1] );
                           toPrint=" " + key + "  " + token2[0] + " " + token2[1];
 

         }


//System.out.println(toPrint);

bw.write(toPrint+"\n");

}



fis.close();
bis.close();
dis.close();
bw.close();



     } catch (FileNotFoundException e) {
      e.printStackTrace();
     }catch (IOException e) {
      e.printStackTrace();
    }


} // end of main





}

