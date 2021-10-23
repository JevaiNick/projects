import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Loader {
    public static final char LETTERS[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
    public static final String PATH1 = "C:\\Users\\Jevai\\IdeaProjects\\java_basics\\14_Performance\\CarNumberGenerator\\res\\";
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int regionCode = 1; regionCode < 100; regionCode++) {
            RegionGeneration regionGeneration = new RegionGeneration(LETTERS,PATH1,regionCode);

            executor.submit(regionGeneration);
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println((System.currentTimeMillis() - start) + " ms");
    }


}
