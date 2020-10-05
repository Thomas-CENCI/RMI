package dispenser;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Machine extends Remote {
    byte[] read(String file_name) throws RemoteException;
    boolean write(String file_name, byte data[], boolean update) throws RemoteException;

    String getMachineNumber() throws RemoteException;
}
