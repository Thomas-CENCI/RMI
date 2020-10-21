import dispenser.Machine;
import dispenser.MachineObj;
import dispenser.SwitcherInterface;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CreateMachine {
    private SwitcherInterface switcher;

    public CreateMachine() throws IOException, NotBoundException {
        this.switcher = (SwitcherInterface) Naming.lookup("//localhost/switcher");
        this.createMachine();
    }

    public static void main(String args[]) throws IOException, NotBoundException {
        new CreateMachine();
    }

    public void createMachine() throws IOException, NotBoundException {
        for (int i=1; i<=4; i++){
            Machine machine = new MachineObj(i);
            machine.setLoad(i);
            if(i==1){
                machine.setLoad(19); // Just to see if it chooses the right one (i.e the 2nd one)
            }
            System.out.println("Load : " + machine.getLoad());
            this.addMachine(machine);
        }
        System.out.println("Choice : " + switcher.machineChoice());
    }

    public boolean addMachine(Machine machine){
        try{
            String machine_id = machine.getMachineId();
            this.switcher.addMachine((Machine) UnicastRemoteObject.exportObject(machine, 0));
            //machine.CheckResources("text.txt");
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
}
