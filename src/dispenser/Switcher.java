package dispenser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Switcher implements SwitcherInterface {

    private ArrayList<String> inwriting = new ArrayList<String>();

    public void createMachine() throws IOException {
        for (int i=1; i<=3; i++){
            Machine machine = new MachineObj(i);
            machine.setLoad(i);
            System.out.println("Load : " + machine.getLoad());
            this.addMachine(machine);
        }
        System.out.println(LocateRegistry.getRegistry().list().length);
    }

    public Machine machineChoice() throws RemoteException, MalformedURLException, NotBoundException {
        HashMap<String, Integer> loadRecord= new HashMap<String, Integer>();
        Registry registry = LocateRegistry.getRegistry();
        for(String machineName : registry.list()){
            System.out.println("Name : " + machineName);
            if(!(machineName.equals("switcher"))) {
                Machine machine = (Machine) Naming.lookup("//localhost/" + machineName);
                System.out.println("machine : " + machine + "\n");
                loadRecord.put(machineName, machine.getLoad());
            }
        }
        Machine chosenMachine = (Machine) Naming.lookup(keyOfMinValue(loadRecord));
        System.out.println("Chosen machine : " + chosenMachine.getLoad());
        return chosenMachine;
    }

    public String keyOfMinValue(HashMap<String, Integer> hashmap) throws RemoteException, MalformedURLException, NotBoundException {
        List<String> machineNames = new ArrayList<>(hashmap.keySet());
        Integer minValue = hashmap.get(machineNames.get(0));
        String minKey = machineNames.get(0);
        for (String machineName : machineNames) {
            Machine machine = (Machine) Naming.lookup("//localhost/" + machineName);
            if (machine.getLoad() <= minValue) {
                minValue = machine.getLoad();
                minKey = machineName;
            }
        }
        return minKey;
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
    public void updateResources(String file_name, String machine_id) throws Exception {
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
    public byte[] read(String file_name) throws IOException, NotBoundException {
        if (!this.inwriting.contains(file_name)){
            System.out.println(file_name);
            return this.machineChoice().read(file_name);
        }
        else{
            return "Fichier en cours d'écriture".getBytes();
        }
    }

    @Override
    public void write(String file_name, byte[] data) throws Exception {
        if (!this.inwriting.contains(file_name)){
            this.inwriting.add(file_name);
            Machine machine_choice = this.machineChoice();
            machine_choice.addLoad();
            machine_choice.write(file_name, data);
            this.updateResources(file_name, machine_choice.getMachineId());
            machine_choice.unLoad();
            this.inwriting.remove(file_name);
        }
        else{
            System.out.println("File already in writing");
        }
    }

    @Override
    public String getMachineId() throws RemoteException {
        return null;
    }

    @Override
    public void CheckResources(String file_name) throws IOException {
    }

    @Override
    public int getLoad() throws RemoteException {
        return 0;
    }

    @Override
    public void setLoad(Integer load) throws RemoteException {

    }

    @Override
    public void addLoad() throws RemoteException {

    }

    @Override
    public void unLoad() throws RemoteException {

    }

    @Override
    public void load(Machine machine, int load) throws RemoteException {

    }
}
