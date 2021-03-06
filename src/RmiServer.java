import dispenser.Switcher;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer extends UnicastRemoteObject {
    public RmiServer() throws RemoteException {
        super(0);
        try { //special exception handler for registry creation
            LocateRegistry.createRegistry(1099);
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            //do nothing, error means registry already exists
            System.out.println("java RMI registry already exists.");
        }
    }

    public static void main(String args[]) throws Exception {
        System.out.println("RMI server started");
        //Instantiate RmiServer
        new RmiServer();
        Switcher switcher = new Switcher();
        Naming.rebind("//localhost/switcher", UnicastRemoteObject.exportObject(switcher, 0));
        //sw.createMachine();
    }

}
