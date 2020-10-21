package dispenser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SwitcherInterface extends Machine, Control, Notification {
    void updateResources(String file_name, String machine_id) throws IOException, NotBoundException, Exception;
    Remote machineChoice() throws RemoteException, MalformedURLException, NotBoundException;
    }
