package dispenser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Control extends Remote {
    boolean addMachine(Machine machine) throws RemoteException, MalformedURLException, IOException, NotBoundException;
    boolean removeMachine(Machine machine) throws RemoteException, NotBoundException;
    boolean removeResources(String host) throws IOException;


}
