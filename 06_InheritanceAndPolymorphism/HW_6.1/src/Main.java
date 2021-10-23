import BankAccounts.BankAccount;
import BankAccounts.CardAccount;
import BankAccounts.DepositAccount;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount(BigDecimal.valueOf(10000));
        DepositAccount depositAccount = new DepositAccount(BigDecimal.valueOf(5000));
        CardAccount cardAccount = new CardAccount(BigDecimal.valueOf(3000));
        bankAccount.topUpAccount(BigDecimal.valueOf(3000));
        bankAccount.withdrawalOfFunds(BigDecimal.valueOf(30000));
        depositAccount.topUpAccount(BigDecimal.valueOf(1));
        cardAccount.withdrawalOfFunds(BigDecimal.valueOf(10000));
        depositAccount.withdrawalOfFunds(BigDecimal.valueOf(10));
        bankAccount.showBalance();
        cardAccount.showBalance();
        depositAccount.showBalance();
        cardAccount.send(bankAccount,BigDecimal.valueOf(100));
        cardAccount.showBalance();
        bankAccount.showBalance();



    }
}
