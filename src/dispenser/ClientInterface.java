package dispenser;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    void getData(byte[] data) throws RemoteException;
    }
