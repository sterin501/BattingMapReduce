import java.rmi.*;  
import java.rmi.registry.*;  



public class MyServer{  
public static void main(String args[]){  
try{  
MapperRemoteInterface stub=new MapperRemote();  
Naming.rebind("rmi://localhost:5000/sonoo",stub);  
System.out.println("Started");

}catch(Exception e){System.out.println(e);}  


}  



}  
