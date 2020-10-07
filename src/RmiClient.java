import dispenser.Machine;
import dispenser.SwitcherInterface;

import java.rmi.Naming;

public class RmiClient{
    public static void main(String args[]) throws Exception {
        byte[] s;
        SwitcherInterface switcher = (SwitcherInterface) Naming.lookup("//localhost/switcher");
        Machine machine = (Machine) Naming.lookup("//localhost/machine/2");

        s = switcher.read("11");
        System.out.println(new String(s));
        s = machine.read("11");
        System.out.println(new String(s));
    }
}
