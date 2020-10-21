package dispenser;

import java.io.File;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

public class Switcher implements SwitcherInterface {

    public void createMachine() throws IOException {
        for (int i=1; i<=3; i++){
            Machine machine = new MachineObj(i);
            this.addMachine(machine);
        }

        System.out.println(LocateRegistry.getRegistry().list().length);
    }

    @Override
    public boolean addMachine(Machine machine){
        try{
            String machine_id = machine.getMachineId();
            Naming.rebind("//localhost/machine/"+machine_id, UnicastRemoteObject.exportObject(machine, Integer.parseInt(machine_id)));
            machine.CheckResources("text.txt");
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
            this.removeResources("1");
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean removeResources(String id) throws IOException {
        File tempDir = new File("./Resources/R" + id);

        if (tempDir.exists()){
            String[]entries = tempDir.list();
            for(String s: entries){
                File currentFile = new File(tempDir.getPath(),s);
                currentFile.delete();
            }
            if (tempDir.delete()){
                System.out.println("Machine resources "+id+" deleted");
            }
            else{
                System.out.println("Deletion failed");
            }
            return true;
        }
        return false;
    }

    @Override
    public void updateResources(String file_name, String machine_id) throws IOException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry();
        ArrayList<String> bound_names = new ArrayList(Arrays.asList(registry.list()));

        Machine reference_machine = (Machine) Naming.lookup("//localhost/machine/"+machine_id);
        byte[] file_data = reference_machine.read(file_name);

        Machine current_machine;

        for (String machine : bound_names){
            if (!machine.equals("switcher") && !machine.equals("machine/"+machine_id)){
                current_machine = (Machine) Naming.lookup("//localhost/"+machine);
                current_machine.write(file_name, file_data);
            }
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
