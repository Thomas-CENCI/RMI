package dispenser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Machine extends Remote {
    byte[] read(String file_name) throws IOException;
    void write(String file_name, byte data[]) throws RemoteException;
    String getMachineId() throws RemoteException;
    void CheckResources(String file_name) throws IOException;
    int getLoad() throws RemoteException;
    void setLoad(Integer load) throws RemoteException;
}
