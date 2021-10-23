public class Account {
    private long money;
    private String accNumber;
    private boolean available;


    public Account(long money, String accNumber) {
        this.money = money;
        this.accNumber = accNumber;
        this.available = true;
    }

    public void blockAccount() {
        available = false;
    }

    private boolean mayWithdrawal(long amount) {
        if (amount <= money) {
            return true;
        }
        return false;
    }

    public boolean isAvailable() {
        return available;
    }

    public long getBalance() {
        return money;
    }

    public void withdrawal(long amount) throws NotEnoughMoneyException {

        if (!mayWithdrawal(amount)) {
            throw new NotEnoughMoneyException("Not enough money for withdrawal!");
        } else {
            synchronized (this) {
                money = money - amount;
            }
        }
    }

    public synchronized void replenishment(long amount) {
        money = money + amount;

    }
}
