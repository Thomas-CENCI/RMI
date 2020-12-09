package dispenser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Switcher implements SwitcherInterface {

    private ArrayList<String> inwriting = new ArrayList<String>();
    private ArrayList<Machine> machines = new ArrayList<Machine>();

    public Machine machineChoice() throws RemoteException {
        Machine chosen_machine = machines.get(0);
        Integer min_value = chosen_machine.getLoad();

        for(Machine machine : machines){
            String machine_name = machine.getMachineId();

            if(!(machine_name.equals("switcher"))) {

                if(machine.getLoad() < min_value) {
                    min_value = machine.getLoad();
                    chosen_machine = machine;
                }
            }
        }
        return chosen_machine;
    }

    @Override
    public void addMachine(Machine machine) throws IOException, NotBoundException {
        System.out.println("[SWITCHER][MACHINE] "+machine.getMachineId()+" has been added to switcher");
        this.machines.add(machine);
    }

    @Override
    public void removeMachine(Machine machine){
        try{
            this.machines.remove(machine);
            this.removeResources(machine.getMachineId());
        }
        catch(Exception e){}
    }

    @Override
    public void removeResources(String id) throws IOException {
        File temp_dir = new File("./Resources/R" + id);

        if (temp_dir.exists()){
            String[]entries = temp_dir.list();
            for(String s: entries){
                File current_file = new File(temp_dir.getPath(),s);
                current_file.delete();
            }
            if (temp_dir.delete()){
                System.out.println("Machine resources "+id+" deleted");
            }
            else{
                System.out.println("Deletion failed");
            }
        }
    }

    @Override
    public void updateResources(String file_name, byte[] data, String machine_id) throws Exception {
        for (Machine current_machine : this.machines){
            if (!current_machine.getMachineId().equals(machine_id)){
                System.out.println("[SWITCHER][MACHINE] "+current_machine.getMachineId()+ " UPDATE "+file_name);
                current_machine.write(file_name, data);
            }
        }
    }

    @Override
    public byte[] read(String file_name, ClientInterface client) throws IOException, NotBoundException, InterruptedException {
        if (!this.inwriting.contains(file_name)){
            Machine machine_choice = this.machineChoice();
            System.out.println("[SWITCHER][MACHINE] " + machine_choice.getMachineId() + " : read " + file_name);
            machine_choice.addLoad();
            byte[] res = machine_choice.read(file_name, client);
            machine_choice.unload();
            return res;
        }
        else{
            return ("File "+file_name+" in writing, please try again later").getBytes();
        }
    }

    @Override
    public byte[] readWithSwitcher(String file_name) throws IOException, NotBoundException {
        if (!this.inwriting.contains(file_name)){
            Machine machine_choice = this.machineChoice();
            System.out.println("[SWITCHER][MACHINE] " + machine_choice.getMachineId() + " : read with switcher " + file_name);
            machine_choice.addLoad();
            byte[] res = machine_choice.readWithSwitcher(file_name);
            machine_choice.unload();
            return res;
        }
        else{
            return ("File "+file_name+" in writing, please try again later").getBytes();
        }
    }

    @Override
    public void write(String file_name, byte[] data) throws Exception {
        if (!this.inwriting.contains(file_name)){
            this.inwriting.add(file_name);
            Machine machine_choice = this.machineChoice();
            System.out.println("[SWITCHER][MACHINE] " + machine_choice.getMachineId() + " : write " + file_name);
            machine_choice.addLoad();
            machine_choice.write(file_name, data);
            this.updateResources(file_name, data, machine_choice.getMachineId());
            machine_choice.unload();
            this.inwriting.remove(file_name);
        }
        else{
            System.out.println("File "+file_name+" already in writing");
        }
    }

    @Override
    public void append(String file_name, byte[] data) throws Exception {
        if (!this.inwriting.contains(file_name)){
            this.inwriting.add(file_name);
            Machine machine_choice = this.machineChoice();
            System.out.println("[SWITCHER][MACHINE] " + machine_choice.getMachineId() + " : write " + file_name);
            machine_choice.addLoad();
            machine_choice.append(file_name, data);
            this.updateResources(file_name, data, machine_choice.getMachineId());
            machine_choice.unload();
            this.inwriting.remove(file_name);
        }
        else{
            System.out.println("File "+file_name+" already in writing");
        }
    }

    @Override
    public String getMachineId() {
        return null;
    }

    @Override
    public void checkResources(String file_name) {
    }

    @Override
    public int getLoad() {
        return 0;
    }

    @Override
    public void setLoad(Integer load) {

    }

    @Override
    public void addLoad() {

    }

    @Override
    public void unload() {

    }

    @Override
    public void load(Machine machine, int load) {

    }
}
