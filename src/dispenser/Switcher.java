package dispenser;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class Switcher implements SwitcherInterface {
    private HashMap MachinesHashMap;

    public Switcher(){
        this.MachinesHashMap = new HashMap<String, Machine>();
    }

    public void createMachine() throws RemoteException {
        Machine test = new MachineObj(2);

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
            this.MachinesHashMap.put("machine_"+machine_id, machine);
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
            Machine machine_delete = (Machine) this.MachinesHashMap.get("machine_"+id);
            machine_delete = null;
            this.MachinesHashMap.remove("machine_"+id);
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
    public boolean write(String file_name, byte[] data, boolean update) throws RemoteException {
        return false;
    }

    @Override
    public String getMachineId() throws RemoteException {
        return null;
    }

    @Override
    public void load(Machine machine, int load) throws RemoteException {

    }
}
