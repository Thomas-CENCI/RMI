import dispenser.Machine;
import dispenser.MachineObj;
import dispenser.SwitcherInterface;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.server.UnicastRemoteObject;

public class CreateMachine {
    private SwitcherInterface switcher;
    Integer id = 1;

    public CreateMachine() throws IOException, NotBoundException {
        this.switcher = (SwitcherInterface) Naming.lookup("//localhost/switcher");
        //this.createMachine();
    }

    public static void main(String args[]) throws IOException, NotBoundException {
        new CreateMachine();
    }

    public void createMachine() throws IOException {
        Machine machine = new MachineObj(this.id);
        machine.setLoad(1);
        this.addMachine(machine);
        id++;
        /*for (int i=1; i<=4; i++){
            Machine machine = new MachineObj(i);
            machine.setLoad(1);
            this.addMachine(machine);
        }*/
    }

    public void addMachine(Machine machine){
        try{
            String machine_id = machine.getMachineId();
            this.switcher.addMachine((Machine) UnicastRemoteObject.exportObject(machine, 0));
            machine.checkResources("text.txt");
        }
        catch(Exception e){}
    }
}
