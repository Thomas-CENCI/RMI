package dispenser;

import java.io.IOException;
import java.rmi.NotBoundException;

public interface SwitcherInterface extends Machine, Control, Notification {
    void updateResources(String file_name, String machine_id) throws IOException, NotBoundException;
}
