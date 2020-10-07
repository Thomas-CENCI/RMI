package dispenser;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Switcher implements SwitcherInterface {

    public void createMachine() throws IOException {
        Machine test = new MachineObj(2);
        test.CheckResources("text.txt");

        this.addMachine(test);
        Machine test2 = new MachineObj(3);

        this.addMachine(test2);
        System.out.println(LocateRegistry.getRegistry().list().length);
        this.removeMachine("2");
    }

    @Override
    public boolean addMachine(Machine machine){
        try{
            String machine_id = machine.getMachineId();
            Naming.rebind("//localhost/machine/"+machine_id, UnicastRemoteObject.exportObject(machine, Integer.parseInt(machine_id)));
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean removeMachine(String id){
        try{
            LocateRegistry.getRegistry().unbind("machine/"+id);
            return true;
        }
        catch(Exception e){
            return false;
        }

    }

    @Override
    public byte[] read(String file_name) throws RemoteException {
        System.out.println(file_name+"FILE");
        byte[] test = (file_name+"FILE").getBytes();
        return test;
    }

    @Override
    public void write(String file_name, byte[] data) throws RemoteException {
    }

    @Override
    public String getMachineId() throws RemoteException {
        return null;
    }

    @Override
    public void CheckResources(String file_name) throws IOException {

    }

    @Override
    public void load(Machine machine, int load) throws RemoteException {

    }
}
