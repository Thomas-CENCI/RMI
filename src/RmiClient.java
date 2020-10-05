import dispenser.SwitcherIntf;

import java.rmi.Naming;

public class RmiClient{

    public static void main(String args[]) throws Exception {
        SwitcherIntf server = (SwitcherIntf) Naming.lookup("//localhost/1");
        System.out.println(server.read("11"));
    }
}
