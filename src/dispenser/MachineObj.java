package dispenser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;

public class MachineObj implements Machine{
    Integer id;
    Integer load = 0;

    public MachineObj(Integer id){
        this.id = id;
    }

    public String getMachineId() throws RemoteException{
        return Integer.toString(this.id);
    }

    @Override
    public byte[] read(String file_name) throws IOException {
        Path path = Paths.get("./Resources/R"+this.getMachineId()+"/"+file_name);
        return Files.readAllBytes(path);
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
    public int getLoad() throws RemoteException {
        return this.load;
    }

    @Override
    public void setLoad(Integer load) throws RemoteException {
        this.load = load;
    }

    @Override
    public void addLoad() throws RemoteException {
        this.load++;
    }

    @Override
    public void unLoad() throws RemoteException {
        this.load--;
    }

    @Override
    public void write(String file_name, byte[] data) throws RemoteException {
        try {
            FileWriter myWriter = new FileWriter("./Resources/R"+this.getMachineId()+"/"+file_name);
            myWriter.write(new String(data, StandardCharsets.UTF_8));
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
