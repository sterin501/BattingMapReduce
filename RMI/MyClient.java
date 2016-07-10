// http://www.javatpoint.com/RMI

import java.rmi.*; 
import java.io.*;
import java.util.*;


class RunnableDemo implements Runnable {
   private Thread t;
   public String threadName;
   public String FileName;
   public  List<TreeMap> mapperList =new ArrayList<TreeMap>();  

   public boolean done;
   
   RunnableDemo( String name,String FileNameC){


       
        

        threadName = name;
        FileName = FileNameC;
        done=false;

   }  // end of RunnableDemo con



   public void run() {
    //  System.out.println("Running " +  threadName );
     

             

          try {      
            
long millisStart = System.currentTimeMillis();


 MapperRemoteInterface stub=(MapperRemoteInterface)Naming.lookup("rmi://localhost:5000/sonoo"); 

           mapperList=stub.printOne(FileName);
long millisEnd = System.currentTimeMillis();

System.out.println("time for " + threadName +"to finish "+ (millisEnd-millisStart)/1000);

              done=true;
       

          
          

            } catch ( Exception e ) {System.out.println(e);}
              

      
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



 
public class MyClient{  
public static void main(String args[]){  


   InputStream input = null;
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    DataInputStream dis = null;



try{  

String filename=args[0];

    File file = new File(filename+".1");  // Reading Locall

   fis = new FileInputStream(file);

      // Here BufferedInputStream is added for fast reading.
      bis = new BufferedInputStream(fis);
      dis = new DataInputStream(bis);

LineNumberReader  lnr = new LineNumberReader(new FileReader(file));
lnr.skip(Long.MAX_VALUE);

long apxL =  lnr.getLineNumber() ;

System.out.println("Number lines in one server " + apxL);
  

RunnableDemo RDL = new RunnableDemo("Remote",filename+".2");

RDL.start();
    

     long millisStart = System.currentTimeMillis();


TreeMap<String, String> mapper = new TreeMap<String, String>();

List<TreeMap> mapperList =new ArrayList<TreeMap>(); 

long linecount=0;

int lastPercentRendered=0;

System.out.println("Mapping ");

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




long millisEnd = System.currentTimeMillis();

  System.out.println("\n\n Time Required  for Mapping  " + (millisEnd-millisStart)/1000  + "sec");


System.out.println("Combiner");

millisStart = System.currentTimeMillis();

while (!RDL.done)
Thread.sleep(1000);


if(RDL.done)

   {

for(int j=0;j<RDL.mapperList.size();j++)

mapperList.add(RDL.mapperList.get(j));
   }


millisEnd = System.currentTimeMillis();

 System.out.println("\n\n Time Required  for Combiner  " + (millisEnd-millisStart)/1000  + "sec");


TreeMap<String, String> Finalmapper = new TreeMap<String, String>();

String finalStringtoPrint="";

int maxTemp;

millisStart = System.currentTimeMillis();

 File Outputfile = new File("output.txt");
 FileWriter fw = new FileWriter(Outputfile.getAbsoluteFile());
 BufferedWriter bw = new BufferedWriter(fw);  


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

//System.out.println(toPrint);

}





bw.close();



     


} catch (FileNotFoundException e) {
      e.printStackTrace();
     }catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {}
   



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


}
