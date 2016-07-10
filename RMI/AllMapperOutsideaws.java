// http://www.javatpoint.com/RMI

import java.rmi.*; 
import java.io.*;
import java.util.*;


class RunnableDemo implements Runnable {
   private Thread t;
   public String threadName;
   public String FileName;
   public String rmiServer;
   public  List<TreeMap> mapperList =new ArrayList<TreeMap>();  

   public boolean done;
   
   RunnableDemo( String name,String FileNameC,String serverdetils){


       
        

        threadName = name;
        FileName = FileNameC;
        done=false;
        rmiServer=serverdetils;

   }  // end of RunnableDemo con



   public void run() {
    //  System.out.println("Running " +  threadName );
     

             

          try {      
            
long millisStart = System.currentTimeMillis();
//"rmi://localhost:5000/sonoo"

MapperRemoteInterface stub=(MapperRemoteInterface)Naming.lookup(rmiServer);

           mapperList=stub.printOne(FileName);
long millisEnd = System.currentTimeMillis();

System.out.println("time for " + threadName +"to finish "+ (millisEnd-millisStart)/1000);

              done=true;
       

          
          

            } catch ( Exception e ) {e.printStackTrace();}
              

      
   }
   
   public void start ()
   {
     // System.out.println("Starting " +  threadName + " " + startC + " " +endC  );
      
     // mapper = new TreeMap<String, String>();

           System.out.println("Starting " +  threadName + " " + FileName + " " +rmiServer  );
                       


     if (t == null)
      {
         t = new Thread (this, threadName);
         t.start ();
         
                                              
                                            
                

      }
   

   }


}  //end of Runnable Demo



 
public class AllMapperOutsideaws {  
public static void main(String args[]){  


   InputStream input = null;
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    DataInputStream dis = null;



try{  

String filename=args[0];

    File file = new File(filename+".1");  // Reading content dIDs 

   //fis = new FileInputStream(file);

      // Here BufferedInputStream is added for fast reading.
     // bis = new BufferedInputStream(fis);
      // dis = new DataInputStream(bis);

LineNumberReader  lnr = new LineNumberReader(new FileReader(file));
lnr.skip(Long.MAX_VALUE);

long apxL =  lnr.getLineNumber() ;

System.out.println("Number lines in one server " + apxL);
  
List<RunnableDemo> RDL = new ArrayList<RunnableDemo>();

//RunnableDemo RDL = new RunnableDemo("Remote",filename+".2");

	

RDL.add(new RunnableDemo("Remote1",filename+".1","rmi://172.31.27.147:5000/sonoo"));

RDL.get(0).start();

RDL.add(new RunnableDemo("Remote2",filename+".2","rmi://172.31.27.146:5000/sonoo"));

RDL.get(1).start();


RDL.add(new RunnableDemo("Remote3",filename+".2","rmi://172.31.27.144:5000/sonoo"));

RDL.get(2).start();

RDL.add(new RunnableDemo("Remote4",filename+".2","rmi://172.31.27.145:5000/sonoo"));

RDL.get(3).start();
    

     long millisStart = System.currentTimeMillis();


TreeMap<String, String> mapper = new TreeMap<String, String>();

List<TreeMap> mapperList =new ArrayList<TreeMap>(); 

long linecount=0;

int lastPercentRendered=0;

System.out.println("Mapping ");

      








int lastVerifed=0;
for ( int p=0;p<RDL.size();p++)

{

//System.out.println(RDL.get(p).threadName+" " +RDL.get(p).done+" "+ RDL.get(p).startC + " "+ RDL.get(p).endC+" "+RDL.get(p).mapper);


while(!RDL.get(p).done)
Thread.sleep(500);

      

}

long millisEnd = System.currentTimeMillis();
System.out.println("\n\n Time Required  for Mapping  " + (millisEnd-millisStart)/1000  + "sec");


System.out.println("Combiner");

millisStart = System.currentTimeMillis();

for ( int k=0; k<RDL.size();k++)

   {

for(int j=0;j<RDL.get(k).mapperList.size();j++)

mapperList.add(RDL.get(k).mapperList.get(j));
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
