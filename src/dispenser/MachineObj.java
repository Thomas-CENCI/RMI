package dispenser;

import java.rmi.RemoteException;

public class MachineObj implements Machine{
    Integer id;

    public MachineObj(Integer id){
        this.id = id;
    }

    public String getMachineId() throws RemoteException{
        return Integer.toString(this.id);
    }

    @Override
    public byte[] read(String file_name) throws RemoteException {
        byte[] test = "MACHINE".getBytes();
        System.out.println("dispenser.Machine"+this.getMachineId());
        return test;
    }

    @Override
    public boolean write(String file_name, byte[] data, boolean update) throws RemoteException {
        return false;
    }
}
