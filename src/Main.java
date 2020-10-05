import dispenser.Switcher;
import dispenser.SwitcherIntf;
import org.junit.Test;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Main{
    public static void main(String args[]) throws Exception {
        System.out.println("RMI server started");

        //Instantiate RmiServer
        Switcher test = new Switcher();
        test.createMachine();
    }

    @Test
    public void client() throws RemoteException, MalformedURLException, NotBoundException {
        SwitcherIntf server = (SwitcherIntf)Naming.lookup("//localhost/1");
        System.out.println(server.read("11"));
    }
}
