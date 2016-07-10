import java.io.*;
import java.util.*;


public class StandAloneSplit {


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

List<TreeMap> mapperList =new ArrayList<TreeMap>(); 

System.out.println("Reading Lines");

long millisStart = System.currentTimeMillis();

LineNumberReader  lnr = new LineNumberReader(new FileReader(file));
lnr.skip(Long.MAX_VALUE);

long millisEnd = System.currentTimeMillis();

long apxL =  lnr.getLineNumber() ;

long linesPerSec=apxL/(millisEnd-millisStart);

long  filesize = file.length();

System.out.println("Lines per ms "+ linesPerSec );



       long linecount=0;

       
System.out.println("Number of Lines   "  + apxL );
   
  
       int lastPercentRendered=0;

System.out.println("Mapping  "  );

  millisStart = System.currentTimeMillis();

      while (dis.available() != 0) {

  

       try {

                   linecount++;

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

                                //500000 original value , 10000 --  657 

                          if (linecount%10000==0)
                             {
                                    //  System.out.println("Spliting");

                                        mapperList.add(mapper);
                                        mapper = new TreeMap<String, String>(); 
                              }


                            

                                              lastPercentRendered = caculatePer(linecount,apxL,lastPercentRendered);
                                                    // This will print the % done
                                       



            }catch (ArrayIndexOutOfBoundsException e) {
                     //  e.printStackTrace();
            }



         

       //    reduce (year,player,runs);


 

                                   }

mapperList.add(mapper);


  millisEnd = System.currentTimeMillis();

  System.out.println("\n\n Time Required  for Mapping  " + (millisEnd-millisStart)/1000  + "sec");

  System.out.println("\n Mappers   "+  mapperList.size() );

 File Outputfile = new File("output.txt");
 FileWriter fw = new FileWriter(Outputfile.getAbsoluteFile());
 BufferedWriter bw = new BufferedWriter(fw);   


System.out.println("\n Reducing ");



TreeMap<String, String> Finalmapper = new TreeMap<String, String>();

String finalStringtoPrint="";

int maxTemp;

millisStart = System.currentTimeMillis();

for(int k=0; k<mapperList.size();k++)

{

mapper  = mapperList.get(k);


for ( String keyofi :  mapper.keySet())

 {


 String toPrint=gettheValid(mapper.get(keyofi));



             if (Finalmapper.containsKey( keyofi))

                      {
                               String currenthigh = Finalmapper.get(keyofi);

                               String token2[] = currenthigh.split("\\~+");
             
               
                                  int  run = Integer.parseInt(token2[1]);

                                
                                String token3[] = toPrint.split("\\~+");

                                
                                     int  run2 = Integer.parseInt(token3[1]);

                                               if(run2<run)
                                        
                                             Finalmapper.put(keyofi,currenthigh);      
                                                  else
                                             Finalmapper.put(keyofi,toPrint); 



                      }


            else   {

                         
 
                          Finalmapper.put(keyofi,toPrint);
                          

                   }



  }



}


  millisEnd = System.currentTimeMillis();

  System.out.println("\n\n Time Required  for Reducing  " + (millisEnd-millisStart)/1000  + "sec");

for ( String key :  Finalmapper.keySet())


{

String we = Finalmapper.get(key);

String token2[] = we.split("\\~+");

String toPrint=" " + key + "  " + token2[0] + " " + token2[1];

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


public static int caculatePer (long linecount,long totallinecount,int lastPercentRendered )

{

                                         int percentage =  (int) (linecount*100/totallinecount);
                                         int blocktorender = percentage - lastPercentRendered;


                                                for (int i = 0; i < blocktorender; i++)
                                                                    {
                                                                       lastPercentRendered += 5;
                                                                       // System.out.print((lastPercentRendered % 10) == 0 ? percentage+"%" : "");
                                                                           System.out.print("----->"+percentage+"%");
                                                                          
                                                                      } 


                                                 return lastPercentRendered;


}

public static String gettheValid ( String value)

{

String token1[] = value.split("\\~+");

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
                             toPrint = token2[0]+"~"+token2[1];
 

         }


 return toPrint;



}


}

