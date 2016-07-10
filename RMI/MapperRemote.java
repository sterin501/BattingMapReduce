import java.rmi.*;  
import java.rmi.server.*;  

import java.io.*;
import java.util.*;


public class   MapperRemote extends UnicastRemoteObject implements MapperRemoteInterface{  



MapperRemote()throws RemoteException{  
super();  
}  



public List <TreeMap> printOne(String filename) throws RemoteException {

System.out.println("Got Call for  ");


TreeMap<String, String> mapper = new TreeMap<String, String>();

List<TreeMap> mapperList =new ArrayList<TreeMap>(); 



  File file = new File(filename);  // Reading content dIDs 

     InputStream input = null;
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    DataInputStream dis = null;
    
  
try {
     fis = new FileInputStream(file);

      // Here BufferedInputStream is added for fast reading.
      bis = new BufferedInputStream(fis);
      dis = new DataInputStream(bis);

 long linecount=0;

 while (dis.available() != 0) {

  // System.out.println(dis.readLine());

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
                            
                              if (linecount%10000==0)
                             {
                                    //  System.out.println("Spliting");

                                        mapperList.add(mapper);
                                        mapper = new TreeMap<String, String>(); 
                              }





     
                   }catch (ArrayIndexOutOfBoundsException e) {
                     //  e.printStackTrace();
            }


         } // end of while

mapperList.add(mapper);

fis.close();
bis.close();
dis.close();




     } catch (FileNotFoundException e) {
      e.printStackTrace();
     }catch (IOException e) {
      e.printStackTrace();
    }


//System.out.println(mapperList);

return mapperList;



}  // end of print
  
}  // end of class
