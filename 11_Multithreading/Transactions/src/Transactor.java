public class Transactor implements Runnable{
    String fromAccountNum;
    String toAccountNum;
    long amount;
    Bank bank;

    public Transactor(String fromAccountNum, String toAccountNum, long amount, Bank bank) {
        this.fromAccountNum = fromAccountNum;
        this.toAccountNum = toAccountNum;
        this.amount = amount;
        this.bank = bank;
    }






    @Override
    public void run() {
        try {
            bank.transfer(fromAccountNum,toAccountNum,amount);
        } catch (BlockedAccountException e) {
            System.out.println(e.getMessage());
        } catch (NotEnoughMoneyException e) {
            System.out.println(e.getMessage());
        }
    }
}
