package dispenser;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Machine extends Remote {
    byte[] read(String file_name) throws RemoteException;
    void write(String file_name, byte data[]) throws RemoteException;
    String getMachineId() throws RemoteException;
    void CheckResources(String file_name) throws IOException;
}
