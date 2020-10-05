package dispenser;

import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Control extends Remote {
    boolean addMachine(Machine machine) throws RemoteException, MalformedURLException;
    boolean removeMachine(Machine machine) throws RemoteException;


}
