import java.rmi.*;  
import java.util.*;

public interface MapperRemoteInterface extends Remote{  
 

public List <TreeMap> printOne(String filename) throws RemoteException;

}  
