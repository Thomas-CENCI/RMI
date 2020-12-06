public class TestClient1 {

    public static void main(String[] args) throws Exception {
        RmiClient client = new RmiClient();
        System.out.println("Write in text.txt : ");
        client.write("text.txt", "J'aime me beurrer la biscotte");

        System.out.println("Test read :");
        client.read("text.txt");
        System.out.println("\nTest read with switcher :");
        client.readWithSwitcher("text.txt");

        /*switcher.write("text.txt", "Un test".getBytes());
        System.out.println(new String(switcher.read("text.txt"), StandardCharsets.UTF_8));
        switcher.write("text.txt", "Test2".getBytes());
        System.out.println(new String(switcher.read("text.txt"), StandardCharsets.UTF_8));

        switcher.removeResources("4");*/
    }
}
