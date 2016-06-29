
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.*;
 
public class DBTableCreation {
 
   // private static final String QUERY = "select id,name,email,country,password from Users";


public static void main(String[] args) {
                 
             

     InputStream input = null;
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    DataInputStream dis = null;

if ( args.length !=2)
System.exit(0);

File file = new File(args[1]);

String createTable = "create table "+ args[0] +"(name  varchar2(10), year number(4),code number(1),club varchar2(3),state varchar2(2),run1 number(3),run2 number(3), run3 number(3),run4 number(3), desc1 varchar2(3),desc2 varchar2(3),desc3 varchar2(3),run5 number(3),run6 number(3), run7 number(3), run8 number(2),run9 number(3),run10 number(6),run11 number(5),run12 number(4), run13 number(2),run14 number (3) ,club2 varchar2(3),club3 varchar2(3),club4 varchar2(3))";


                       try  {
                             Connection con = getConnection();
                Statement stmt = con.createStatement();

                long millisStart = System.currentTimeMillis();
                 ResultSet rs = stmt.executeQuery(createTable) ;


                                 

                long millisEnd = System.currentTimeMillis();
                 long time1= millisEnd - millisStart ;
                System.out.println("Table Creation time  " + time1  + "msec");


 

     fis = new FileInputStream(file);

      // Here BufferedInputStream is added for fast reading.
      bis = new BufferedInputStream(fis);
      dis = new DataInputStream(bis);

  long  filesize = file.length();

      System.out.println("Size " + filesize);

      int linecount=0;

       int apxL = (int) filesize/100 ;
  
       int lastPercentRendered=0;

  System.out.println("Reading of the line \n");

List<String> queryLines =new ArrayList<String>(); 

      while (dis.available() != 0) {

                                 linecount++;
                          String [] tokens = dis.readLine().split("\\,+");
                          String UpdateEachLine="";

                               
                                     
                                          for (String key : tokens)

                                              {
                                                         if (key.matches(".*\\d+.*"))
                                                         {
                                                                //   System.out.println(key+" Number");

                                                            UpdateEachLine=UpdateEachLine+","+key;
                                                             
                                                         }

                                                         else 
                                                        {

                                                        //  System.out.println(key+" String");   
                                                           UpdateEachLine=UpdateEachLine+",'"+key+"'";
                                                            
                                                                
                                                        }
                                              }

                                 // System.out.println(UpdateEachLine.substring(1,UpdateEachLine.length()-1));

                                             queryLines.add( UpdateEachLine.substring(1,UpdateEachLine.length()-1));                                    


                                     int percentage = (linecount*100/apxL);
                                         int blocktorender = percentage - lastPercentRendered;

                                     // System.out.print("--"+percentage+"%");

                                                for (int i = 0; i < blocktorender; i++)
                                                                    {
                                                                       lastPercentRendered += 5;
                                                                       // System.out.print((lastPercentRendered % 10) == 0 ? percentage+"%" : "");
                                                                           System.out.print("----->"+percentage+"%");
                                                                          
                                                                      }                         


                                   }



      System.out.println("\n Number of Count " + linecount);

       System.out.println("\n Status of DB export ");


String querypart1="INSERT INTO "+args[0]+" (NAME,YEAR,CODE,CLUB,STATE,RUN1,RUN2,RUN3,RUN4,DESC1,DESC2,DESC3,RUN5,RUN6,RUN7,RUN8,RUN9,RUN10,RUN11,RUN12,RUN13,RUN14,CLUB2,CLUB3,CLUB4) values (";

lastPercentRendered=0;

for ( int k=0;k<queryLines.size();k++)



{

String finalQuery=querypart1+queryLines.get(k)+"')";

//System.out.println(finalQuery);

 rs = stmt.executeQuery(finalQuery) ;

int percentage = (k*100/linecount);
                                         int blocktorender = percentage - lastPercentRendered;

                                     // System.out.print("--"+percentage+"%");

                                                for (int i = 0; i < blocktorender; i++)
                                                                    {
                                                                       lastPercentRendered += 5;
                                                                       // System.out.print((lastPercentRendered % 10) == 0 ? percentage+"%" : "");
                                                                           System.out.print("----->"+percentage+"%");
                                                                          
                                                                      }                         


}
      
           

System.out.println("\n Done ");     


fis.close();
bis.close();
dis.close();
                          
//String InsertState="INSERT INTO "+args[0]+" (NAME,YEAR,CODE,CLUB,STATE,RUN1,RUN2,RUN3,RUN4,DESC1,DESC2,DESC3,RUN5,RUN6,RUN7,RUN8,RUN9,RUN10,RUN11,RUN12,RUN13,RUN14,CLUB2,CLUB3,CLUB4) values ('STER',1908,1,'DFG','DF',100,102,103,104,'DFS','SFS','SFS',199,200,222,10,222,453566,12345,1234,12,123,'LSD','SDF','SFS')";



      // ResultSet       rs = stmt.executeQuery(InsertState) ;


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
              e.printStackTrace();
        } catch (IOException e) {
             e.printStackTrace();
        }



           }

    public static Connection getConnection() {
        Properties props = new Properties();
        FileInputStream fis = null;
        Connection con = null;
        try {
            fis = new FileInputStream("db.properties");
            props.load(fis);
 
            // load the Driver Class
            Class.forName(props.getProperty("DB_DRIVER_CLASS"));
 
            // create the connection now
            con = DriverManager.getConnection(props.getProperty("DB_URL"),
                    props.getProperty("DB_USERNAME"),
                    props.getProperty("DB_PASSWORD"));
        } catch (IOException  e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

         catch (ClassNotFoundException  e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        catch (SQLException  e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return con;
    }
}



