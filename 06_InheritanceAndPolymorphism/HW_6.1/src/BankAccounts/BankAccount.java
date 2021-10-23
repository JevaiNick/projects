package BankAccounts;

import java.math.BigDecimal;

public class BankAccount {

    private BigDecimal accountBalance;

    public BankAccount(BigDecimal accountBalance){
        this.accountBalance = accountBalance;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    /*public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }*/

    public void topUpAccount(BigDecimal amount){
        accountBalance = accountBalance.add(amount);
        System.out.println("Account has been replenished by " + amount);
    }
    protected boolean mayWithdraw(BigDecimal amount){
        if (accountBalance.compareTo(amount) != -1) {
            return true;
        }else{
        return false;
    }
    }
    public void withdrawalOfFunds(BigDecimal amount){
        if (mayWithdraw(amount)) {
            accountBalance = accountBalance.subtract(amount);
            System.out.println("Funds were withdraw in the amount of " + amount);
        }else{
            System.out.println("You can't withdraw money");
        }
    }
    public void showBalance(){
        System.out.println("Bank account balance is " + getAccountBalance());
    }
    public boolean send(BankAccount receiver, BigDecimal amount) {
        if (mayWithdraw(amount)){
            withdrawalOfFunds(amount);
            receiver.topUpAccount(amount);
            return true;
        }else{
            System.out.println("Operation rejected.");
            return false;
        }

    }
}
