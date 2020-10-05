package dispenser;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Control extends Remote {
    boolean addMachine(Machine machine) throws RemoteException;
    boolean removeMachine(Machine machine) throws RemoteException;
}
