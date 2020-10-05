package dispenser;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Switcher extends UnicastRemoteObject implements SwitcherIntf {

    public Switcher() throws RemoteException {
        super(0);
        try { //special exception handler for registry creation
            LocateRegistry.createRegistry(1099);
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            //do nothing, error means registry already exists
            System.out.println("java RMI registry already exists.");
        }
    }

    public void createMachine() throws RemoteException, MalformedURLException {
        Registry registry = LocateRegistry.getRegistry();
        System.out.println(registry.list().length);

        Machine test = new MachineObj(1);

        this.addMachine(test);
        System.out.println(registry.list().length);

        Machine test2 = new MachineObj(2);

        this.addMachine(test2);
        System.out.println(registry.list().length);

        System.out.println(LocateRegistry.getRegistry());

    }

    @Override
    public boolean addMachine(Machine machine) throws RemoteException, MalformedURLException {
        String machine_number = machine.getMachineNumber();
        Naming.rebind("//localhost/"+machine_number, UnicastRemoteObject.exportObject(machine, Integer.parseInt(machine_number)));
        return true;
    }

    @Override
    public boolean removeMachine(Machine machine) throws RemoteException {
        return false;
    }

    @Override
    public byte[] read(String file_name) throws RemoteException {
        System.out.println(file_name+"FILE");
        return new byte[0];
    }

    @Override
    public boolean write(String file_name, byte[] data, boolean update) throws RemoteException {
        return false;
    }

    @Override
    public String getMachineNumber() {
        return null;
    }

    @Override
    public void load(Machine machine, int load) throws RemoteException {

    }
}
