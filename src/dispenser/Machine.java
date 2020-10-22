package dispenser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Machine extends Remote {
    byte[] read(String file_name, ClientInterface client) throws IOException, NotBoundException;
    byte[] read_machine(String file_name) throws IOException, NotBoundException;
    void write(String file_name, byte data[]) throws Exception;
    String getMachineId() throws RemoteException;
    void CheckResources(String file_name) throws IOException;
    int getLoad() throws RemoteException;
    void setLoad(Integer load) throws RemoteException;
    void addLoad() throws RemoteException;
    void unLoad() throws RemoteException;
}
