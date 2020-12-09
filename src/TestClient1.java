public class TestClient1 {

    public static void main(String[] args) throws Exception {
        RmiClient client = new RmiClient();
        System.out.println("Write in text.txt ");

        client.write("text.txt", "Un test random");
        System.out.println("Test read after write:");
        client.read("text.txt");

        System.out.println("Append in text.txt ");
        client.append("text.txt", "Un test random");
        System.out.println("Test read after append with switcher:");
        client.readWithSwitcher("text.txt");
    }
}
