//  java  -Xms2048m -Xmx4048m NewThread 1.csv 


import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.util.stream.*;

class RunnableDemo implements Runnable {
   private Thread t;
   public String threadName;
   public List<String>  toSend = new ArrayList<String>();
   public TreeMap<String, String> mapper =  new TreeMap<String, String>(); 

   public boolean done;
   
   RunnableDemo( String name,List<String> lines ,TreeMap<String, String> mapperC ){


       
        

        threadName = name;
        toSend=lines;
        mapper=mapperC;
        done=false;

   }  // end of RunnableDemo con



   public void run() {
    //  System.out.println("Running " +  threadName );
     

             

                
            
long millisStart = System.currentTimeMillis();
           mapper=Mapper.Mapping(toSend);
long millisEnd = System.currentTimeMillis();

System.out.println("time for " + threadName +"to finish "+ (millisEnd-millisStart));

              done=true;
       

          
          



      
   }
   
   public void start ()
   {
     // System.out.println("Starting " +  threadName + " " + startC + " " +endC  );
      
     // mapper = new TreeMap<String, String>();

                            


     if (t == null)
      {
         t = new Thread (this, threadName);
         t.start ();
         
                                              
                                            
                

      }
   

   }


}  //end of Runnable Demo
 

public class NewThread {



public static void main (String[] args) {




    File file = new File(args[0]);  // Reading content dIDs 

     InputStream input = null;
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    DataInputStream dis = null;
 List<RunnableDemo> RDL = new ArrayList<RunnableDemo>();

try {
      fis = new FileInputStream(file);

      // Here BufferedInputStream is added for fast reading.
      bis = new BufferedInputStream(fis);
      dis = new DataInputStream(bis);




TreeMap<String, String> mapper = new TreeMap<String, String>();



System.out.println("Reading Lines");

long millisStart = System.currentTimeMillis();


long apxL = 0;
int ThreadCount=0;
 List<String> lines = new ArrayList<String>();
   while (dis.available() != 0)
         {
             apxL++;
            
                     
      
            lines.add(dis.readLine());


                  if(apxL%50000==0)

                   {        mapper = new TreeMap<String, String>();
                            RDL.add(new RunnableDemo("Thread"+ThreadCount,lines,mapper ));
                            RDL.get(ThreadCount).start();

                          

                            lines = new ArrayList<String>();
                                                       

                            ThreadCount++;



                   }



         }


  if(ThreadCount*10000 <  apxL)
 {
  //System.out.println("Starting threads for " + j*10000+ "  " + apxL);

  TreeMap<String, String> mapper2 = new TreeMap<String, String>();

                 RDL.add(new RunnableDemo("Thread "+ThreadCount,lines,mapper ));
                            RDL.get(ThreadCount).start();


  }



long millisEnd = System.currentTimeMillis();

//long apxL =  lnr.getLineNumber() ;

long linesPerSec=apxL/(millisEnd-millisStart);

long  filesize = file.length();

System.out.println("Lines per ms "+ linesPerSec );




       
System.out.println("Number of Lines   "  + apxL );
   
  
       int lastPercentRendered=0;


System.out.println("Via threads " +RDL.size());




System.out.println("Mapping  "  );

  millisStart = System.currentTimeMillis();




int lastVerifed=0;
for ( int p=0;p<RDL.size();p++)

{



      if (RDL.get(p).done)

     {

//System.out.println(RDL.get(p).threadName+" " +RDL.get(p).done + " " + RDL.get(p).mapper);

System.out.println(RDL.get(p).threadName+" " +RDL.get(p).done);

        lastVerifed=p;
             
      }

     else
     {

        Thread.sleep(1000);
        p=lastVerifed;
     }

}



    
  millisEnd = System.currentTimeMillis();



  System.out.println("\n\n Time Required  for Mapping  " + (millisEnd-millisStart)/1000  + "sec");


 File Outputfile = new File("output.txt");
 FileWriter fw = new FileWriter(Outputfile.getAbsoluteFile());
 BufferedWriter bw = new BufferedWriter(fw);   


System.out.println("\n Reducing ");



TreeMap<String, String> Finalmapper = new TreeMap<String, String>();

String finalStringtoPrint="";

mapper = new TreeMap<String, String>();

int maxTemp;

millisStart = System.currentTimeMillis();

for(int k=0; k<RDL.size();k++)

{

mapper  = RDL.get(k).mapper;


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
    } catch (InterruptedException e) {}


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


} //end of main class


class Mapper{



public static  TreeMap<String, String>   Mapping (List<String> lines)

{

TreeMap<String, String> ThreadMapper = new TreeMap<String, String>();

try {


  
         
         
                for (int m=0;m<lines.size();m++ )


                 {

     String [] tokens = lines.get(m).split("\\,+");

     String year = tokens[1];
     String player =  tokens[0];
     String runs =  tokens[8];
     String value = player+","+runs;

                     //    System.out.println(m+"...>>>"+ year+"..."+player+"...."+runs);
          

                     if (ThreadMapper.containsKey(year))
                           {

                                String newvalue = ThreadMapper.get(year);
                                 newvalue = newvalue+"~"+value;
                                 ThreadMapper.put(year,newvalue);
                                  
                           }
                       
                        else 
                              ThreadMapper.put(year,value);




                 }


         

            } 
               catch (ArrayIndexOutOfBoundsException e) {}
        

          
return ThreadMapper;

}



} // end of mappser

