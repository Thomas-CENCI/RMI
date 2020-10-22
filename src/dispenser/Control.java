package dispenser;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Control extends Remote {
    void addMachine(Machine machine) throws IOException, NotBoundException;
    void removeMachine(Machine machine) throws RemoteException, NotBoundException;
    void removeResources(String host) throws IOException;


}
