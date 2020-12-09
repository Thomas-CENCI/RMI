package dispenser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.util.concurrent.TimeUnit;

public class MachineObj implements Machine{
    Integer id;
    Integer load = 0;

    public MachineObj(Integer id){
        this.id = id;
    }

    public String getMachineId() {
        return Integer.toString(this.id);
    }

    @Override
    public byte[] read(String file_name, ClientInterface client) throws IOException, NotBoundException, InterruptedException {
        TimeUnit.SECONDS.sleep(5);

        Path path = Paths.get("./Resources/R"+this.getMachineId()+"/"+file_name);
        byte[] data = Files.readAllBytes(path);

        client.getData(data);

        return Files.readAllBytes(path);
    }

    @Override
    public byte[] readWithSwitcher(String file_name) throws IOException {
        Path path = Paths.get("./Resources/R"+this.getMachineId()+"/"+file_name);
        return Files.readAllBytes(path);
    }

    public void check_directory() {
        File temp_dir = new File("./Resources/R" + getMachineId());

        if(!(temp_dir.exists() && temp_dir.isDirectory())){
            temp_dir.mkdir();
            System.out.println("[MACHINE] "+getMachineId()+" Directory created");;
        }
        else{
            System.out.println("[MACHINE] "+getMachineId()+" Directory already exists");
        }
    }

    public void checkFiles(String file_name) throws IOException {
        File temp_file = new File("./Resources/R" + getMachineId() + "/" + file_name);

        if(!(temp_file.exists() && temp_file.isFile())){
            temp_file.createNewFile();
            System.out.println("[MACHINE] "+getMachineId()+" File created");
        }
        else {
            System.out.println("[MACHINE] "+getMachineId()+" File already exists");
        }
    }

    public void checkResources(String file_name) throws IOException {
        check_directory();
        checkFiles(file_name);
    }

    @Override
    public int getLoad() {
        return this.load;
    }

    @Override
    public void setLoad(Integer load) {
        this.load = load;
    }

    @Override
    public void addLoad() {
        this.load++;
    }

    @Override
    public void unload() {
        this.load--;
    }

    @Override
    public void append(String file_name, byte[] data) throws InterruptedException {
        try {
            String current_data = new String(this.readWithSwitcher(file_name), StandardCharsets.UTF_8);

            FileWriter my_writer = new FileWriter("./Resources/R"+this.getMachineId()+"/"+file_name);

            String new_data = new String(data, StandardCharsets.UTF_8);
            my_writer.write(current_data + " " + new_data);
            my_writer.close();
            System.out.println("[MACHINE] "+this.getMachineId()+" Successfully wrote to the file "+file_name);
        } catch (IOException e) {
            System.out.println("[MACHINE] "+this.getMachineId()+" An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public void write(String file_name, byte[] data) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);

        try {
            FileWriter my_writer = new FileWriter("./Resources/R"+this.getMachineId()+"/"+file_name);
            String new_data = new String(data, StandardCharsets.UTF_8);
            my_writer.write(new_data);
            my_writer.close();
            System.out.println("[MACHINE] "+this.getMachineId()+" Successfully wrote to the file "+file_name);
        } catch (IOException e) {
            System.out.println("[MACHINE] "+this.getMachineId()+" An error occurred.");
            e.printStackTrace();
        }
    }
}
