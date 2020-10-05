package dispenser;

import java.rmi.Remote;

public interface SwitcherIntf extends Remote, Machine, Control, Notification {
}