import dispenser.ClientInterface;
import dispenser.SwitcherInterface;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.server.UnicastRemoteObject;

public class RmiClient implements ClientInterface {
    private SwitcherInterface switcher;

    public void getData(byte[] data){
        System.out.println(new String(data, StandardCharsets.UTF_8));
    }

    public void read(String file_name) throws IOException, NotBoundException, InterruptedException {
        this.switcher.read(file_name, (ClientInterface) UnicastRemoteObject.exportObject((ClientInterface) this, 0));
    }

    public void readWithSwitcher(String file_name) throws Exception {
        System.out.println(new String(this.switcher.readWithSwitcher(file_name), StandardCharsets.UTF_8));
    }

    public void write(String file_name, String data) throws Exception {
        this.switcher.write(file_name, data.getBytes());
    }

    public RmiClient() throws Exception {
        this.switcher = (SwitcherInterface) Naming.lookup("//localhost/switcher");
    }

}
