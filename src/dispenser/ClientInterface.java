package dispenser;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    void get_data(byte[] data) throws RemoteException;
    }
