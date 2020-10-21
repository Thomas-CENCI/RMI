import dispenser.Machine;
import dispenser.SwitcherInterface;

import java.nio.charset.StandardCharsets;
import java.rmi.Naming;

public class RmiClient{
    public static void main(String args[]) throws Exception {
        SwitcherInterface switcher = (SwitcherInterface) Naming.lookup("//localhost/switcher");

        switcher.write("text.txt", "Un test".getBytes());
        System.out.println(new String(switcher.read("text.txt"), StandardCharsets.UTF_8));
        switcher.write("text.txt", "Test2".getBytes());
        System.out.println(new String(switcher.read("text.txt"), StandardCharsets.UTF_8));

        switcher.removeResources("4");
    }
}
