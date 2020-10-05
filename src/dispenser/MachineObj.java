package dispenser;

import java.rmi.RemoteException;

public class MachineObj implements Machine{
    Integer number;

    public MachineObj(Integer number){
        this.number = number;
    }

    public String getMachineNumber() throws RemoteException{
        return Integer.toString(this.number);
    }

    @Override
    public byte[] read(String file_name) throws RemoteException {
        return new byte[0];
    }

    @Override
    public boolean write(String file_name, byte[] data, boolean update) throws RemoteException {
        return false;
    }
}
