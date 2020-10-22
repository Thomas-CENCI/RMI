import dispenser.ClientInterface;
import dispenser.Machine;
import dispenser.SwitcherInterface;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiClient implements ClientInterface {
    public void get_data(byte[] data){
        System.out.println(new String(data, StandardCharsets.UTF_8));
    }

    public void read(String file_name, SwitcherInterface switcher) throws IOException, NotBoundException {
        switcher.read(file_name, (ClientInterface) UnicastRemoteObject.exportObject((ClientInterface)this, 0));
    }

    public RmiClient() throws Exception {
        SwitcherInterface switcher = (SwitcherInterface) Naming.lookup("//localhost/switcher");
        switcher.write("text.txt", "J'aime me beurrer la biscotte 3".getBytes());
        this.read("text.txt", switcher);
    }

    public static void main(String args[]) throws Exception {
        new RmiClient();

        /*switcher.write("text.txt", "Un test".getBytes());
        System.out.println(new String(switcher.read("text.txt"), StandardCharsets.UTF_8));
        switcher.write("text.txt", "Test2".getBytes());
        System.out.println(new String(switcher.read("text.txt"), StandardCharsets.UTF_8));

        switcher.removeResources("4");*/
    }
}
