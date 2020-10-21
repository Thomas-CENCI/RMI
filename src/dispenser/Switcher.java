package dispenser;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Switcher implements SwitcherInterface {

    private ArrayList<String> inwriting = new ArrayList<String>();
    private ArrayList<Machine> machines = new ArrayList<Machine>();

    public Machine machineChoice() throws RemoteException {
        Machine chosenMachine = machines.get(0); // For comparaison reasons
        Integer minValue = chosenMachine.getLoad(); // For comparaison reasons

        for(Machine machine : machines){
            System.out.println(("Test"));
            String machineName = machine.getMachineId();
            System.out.println("Name : " + machineName);
            if(!(machineName.equals("switcher"))) {
                System.out.println("machine : " + machine + "\n");
                if(machine.getLoad() <= minValue) {
                    minValue = machine.getLoad();
                    chosenMachine = machine;
                }
            }
        }
        System.out.println("Chosen machine : " + chosenMachine.getMachineId() + "\nLoad :" + chosenMachine.getLoad());
        return chosenMachine;
    }

    /*
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
    */

    @Override
    public boolean addMachine(Machine machine) throws IOException, NotBoundException {
        this.machines.add(machine);
        System.out.println(new String(machine.read("text.txt"), StandardCharsets.UTF_8));
        return true;
    }

    @Override
    public boolean removeMachine(Machine machine){
        try{
            this.machines.remove(machine);
            this.removeResources(machine.getMachineId());
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
        byte[] file_data = this.machines.get(Integer.parseInt(machine_id)).read(file_name);

        for (Machine current_machine : this.machines){
            if (!current_machine.getMachineId().equals(machine_id)){
                current_machine.write(file_name, file_data);
            }
        }
    }

    @Override
    public byte[] read(String file_name) throws IOException, NotBoundException {
        if (!this.inwriting.contains(file_name)){
            return this.machineChoice().read(file_name);
        }
        else{
            return "File in writing".getBytes();
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
