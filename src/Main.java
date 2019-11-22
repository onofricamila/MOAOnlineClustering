import persitors.BasicCSVPersistor;

public class Main {
    public static void main(String[] args) {
        BasicCSVPersistor basicCSVPersistor = new BasicCSVPersistor();
        ClustreamClusterer clustreamClusterer = new ClustreamClusterer();
        DenstreamClusterer denstreamClusterer = new DenstreamClusterer();

        int tGlobal = 200;

        System.out.println("CLUSTREAM ----------------------");
       // clustreamClusterer.run(tGlobal);

        System.out.println("\n" + "DENSTREAM ----------------------");
        denstreamClusterer.run(tGlobal);

       /* List<List<String>> rows = Arrays.asList(
                Arrays.asList("Jean", "author", "Java"),
                Arrays.asList("David", "editor", "Python"),
                Arrays.asList("Scott", "editor", "Node.js")
        );

        basicCSVPersistor.storeResult("test", rows);
*/
    }
}
