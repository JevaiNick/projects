import javax.swing.plaf.TableHeaderUI;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RedisTest {
    public static void main(String[] args) {
        RedisStorage redis = new RedisStorage();
        redis.init();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 1; i <= 20; i++) {
            redis.addingUser(i);
        }
        ArrayList<String> vipNumbers = new ArrayList<>();
        ArrayList<String> numbers = redis.getNumberOfUsers();

        VIPCounter vipCounter = new VIPCounter(redis);
        executor.submit(vipCounter);
        for (; ; ) {

            for (String number : numbers) {

                if (redis.getVIPNumberOfUsers().size() != 0) {
                    vipNumbers.addAll(redis.getVIPNumberOfUsers());
                    for (String vipNumber : vipNumbers) {
                        System.out.println(vipNumber);
                    }
                    synchronized (redis) {
                        redis.clearVIP();
                    }
                    vipNumbers.clear();
                }

                System.out.println(number);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /*executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //redis.shutdown();
    }
}
