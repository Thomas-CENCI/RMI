package dispenser;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Notification extends Remote {
    public void load(Machine machine, int load) throws RemoteException;
}
