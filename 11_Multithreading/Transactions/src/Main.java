import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    public static final int COUNT_OF_ACCOUNTS = 30;

    public static void main(String[] args) {
        Bank bank = new Bank();
        for (int i = 1; i <= COUNT_OF_ACCOUNTS; i++) {
            bank.addAccount(String.valueOf(i), Math.round(Math.random() * 10000 + 60000));
        }

        bank.getAccounts().forEach((s, account) -> System.out.println(s + " : " + account.getBalance()));
        System.out.println("--------------------------------------------");
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 1; i <= 20; i++) {
            if (i / 5 == 0) {
                BalanceShower balanceShower = new BalanceShower(String.valueOf(Math.round(Math.random() * COUNT_OF_ACCOUNTS)), bank);
                executor.submit(balanceShower);
            } else {
                Transactor transactor = new Transactor(String.valueOf(Math.round(Math.random() * COUNT_OF_ACCOUNTS)),
                        String.valueOf(Math.round(Math.random() * COUNT_OF_ACCOUNTS)),
                        Math.round(Math.random() * 30000 + 40000), bank);
                executor.submit(transactor);
            }
        }
        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bank.getAccounts().forEach((s, account) -> System.out.println(s + " : " + account.getBalance()));
    }
}
