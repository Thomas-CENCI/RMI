package dispenser;

import java.rmi.RemoteException;

public interface Machine {
    byte[] read(String file_name) throws RemoteException;
    boolean write(String file_nae, byte data[], boolean update) throws RemoteException;
}
