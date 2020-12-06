public class TestClient2 {

    public static void main(String[] args) throws Exception {
        RmiClient client = new RmiClient();
        System.out.println("Write in text.txt : ");
        client.write("text.txt", "Tentative réécriture");
        System.out.println("Write in text2.txt : ");
        client.write("text2.txt", "Un second test");

        System.out.println("Test read :");
        client.read("text.txt");
        System.out.println("\nTest read with switcher :");
        client.readWithSwitcher("text2.txt");

        /*switcher.write("text.txt", "Un test".getBytes());
        System.out.println(new String(switcher.read("text.txt"), StandardCharsets.UTF_8));
        switcher.write("text.txt", "Test2".getBytes());
        System.out.println(new String(switcher.read("text.txt"), StandardCharsets.UTF_8));

        switcher.removeResources("4");*/
    }
}
