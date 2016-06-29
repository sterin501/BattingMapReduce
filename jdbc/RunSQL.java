
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.*;
 
public class RunSQL {
 
   // private static final String QUERY = "select id,name,email,country,password from Users";


public static void main(String[] args) {
                 
             

    
//String selectSQL = "SELECT a.year, a.name, a.run4 from table20M a  JOIN (SELECT year, max(run4) run4 FROM table20M GROUP BY year ) b  ON (a.year = b.year AND a.run4 = b.run4)";

String selectSQL = "SELECT a.year, a.name, a.run4 from "+args[0]+"  a  JOIN (SELECT year, max(run4) run4 FROM "+args[0]+" GROUP BY year ) b  ON (a.year = b.year AND a.run4 = b.run4)   ORDER BY year ASC";

                       try  {
                             Connection con = getConnection();
                Statement stmt = con.createStatement();

                long millisStart = System.currentTimeMillis();
                      ResultSet rs = stmt.executeQuery(selectSQL) ;


                                 

                long millisEnd = System.currentTimeMillis();
                 long time1= millisEnd - millisStart ;
                System.out.println("Query1 " + time1  + "msec");

 File Outputfile = new File(args[1]);
 FileWriter fw = new FileWriter(Outputfile.getAbsoluteFile());
 BufferedWriter bw = new BufferedWriter(fw);   





            while(rs.next()){
                 String name = rs.getString("YEAR");
                String email = rs.getString("NAME");
                String country = rs.getString("RUN4");
                 bw.write(name+ " " +email+ " " +country+ "\n" );

                          // bw.write("\n");

                          }

bw.close();

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



