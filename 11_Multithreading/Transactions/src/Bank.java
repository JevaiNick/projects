import java.util.HashMap;
import java.util.Random;

public class Bank {
    private final long AMOUNT_FOR_CHECK = 50000;
    private HashMap<String, Account> accounts = new HashMap<String, Account>();
    private final Random random = new Random();
    private Object locker = new Object();

    public HashMap<String, Account> getAccounts() {
        return accounts;
    }

    public void addAccount(String numAccount, long money) {
        Account account = new Account(money, numAccount);
        accounts.put(numAccount, account);
    }

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
            throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами.
     * Если сумма транзакции > 50000, то после совершения транзакции,
     * она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка
     * счетов (как – на ваше усмотрение)
     */
    public void transfer(String fromAccountNum, String toAccountNum, long amount) throws BlockedAccountException, NotEnoughMoneyException {
        Account fromAccount;
        Account toAccount;

        if (accounts.containsKey(fromAccountNum)) {
            fromAccount = accounts.get(fromAccountNum);
        } else {
            return;
        }
        if (accounts.containsKey(toAccountNum)) {
            toAccount = accounts.get(toAccountNum);
        } else {
            return;
        }

        synchronized (locker) {

            if (amount >= AMOUNT_FOR_CHECK) {
                try {

                    if (isFraud(fromAccountNum, toAccountNum, amount)) {
                        fromAccount.blockAccount();
                        toAccount.blockAccount();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (fromAccount.isAvailable()
                    && toAccount.isAvailable()) {
                fromAccount.withdrawal(amount);
                toAccount.replenishment(amount);
            } else {
                throw new BlockedAccountException("Some account is blocked! (" +
                        fromAccountNum + " or " + toAccountNum + ")");
            }

        }

    }


    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum) throws BlockedAccountException {
        Account account;
        if (accounts.containsKey(accountNum)) {
            account = accounts.get(accountNum);
        } else {
            System.out.println("Can't find account!");
            return -1;
        }
        synchronized (account) {
            if (account.isAvailable()) {
                return account.getBalance();
            }
            throw new BlockedAccountException(accountNum + " account is blocked!");
        }
    }
}
