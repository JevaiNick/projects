package BankAccounts;

import java.math.BigDecimal;

public class CardAccount extends BankAccount{
    public CardAccount(BigDecimal accountBalance) {
        super(accountBalance);
    }

    @Override
    public void withdrawalOfFunds(BigDecimal amount) {
        BigDecimal amountWithPercent = amount.add(amount.multiply(BigDecimal.valueOf(0.01)));
        System.out.println("Commission 1% is " + amountWithPercent);
        super.withdrawalOfFunds(amountWithPercent);
    }

    @Override
    public void showBalance() {
        System.out.println("Card account balance is " + getAccountBalance());
    }
}
