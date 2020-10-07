package dispenser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    public void CheckDirectory() throws RemoteException {
        File tempDir = new File("./Resources/R" + getMachineId());

        if(!(tempDir.exists() && tempDir.isDirectory())){
            tempDir.mkdir();
            System.out.println("Directory created");;
        }
        else{
            System.out.println("Directory already exists");
        }
    }

    public void CheckFiles(String file_name) throws IOException {
        File tempFile = new File("./Resources/R" + getMachineId() + "/" + file_name);

        if(!(tempFile.exists() && tempFile.isFile())){
            tempFile.createNewFile();
            System.out.println("File created");
        }
        else {
            System.out.println("File already exists");
        }
    }

    public void CheckResources(String file_name) throws IOException {
        CheckDirectory();
        CheckFiles(file_name);
    }

    @Override
    public void write(String file_name, byte[] data) throws RemoteException {
        try {
            FileWriter myWriter = new FileWriter(file_name);
            myWriter.write(String.valueOf(data));
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
