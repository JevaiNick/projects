public class BalanceShower implements Runnable{
    String num;
    Bank bank;

    public BalanceShower(String num, Bank bank) {
        this.num = num;
        this.bank = bank;
    }

    @Override
    public void run() {
        try {
            bank.getBalance(num);
        } catch (BlockedAccountException e) {
            System.out.println(e.getMessage());
        }

    }
}
