package BankAccounts;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class DepositAccount extends BankAccount {
    public DepositAccount(BigDecimal accountBalance) {
        super(accountBalance);
        lastRefill = LocalDate.now();
    }
     private LocalDate lastRefill;

    @Override
    public void topUpAccount(BigDecimal amount) {
        super.topUpAccount(amount);
        lastRefill = LocalDate.now();
    }

    @Override
    public boolean mayWithdraw(BigDecimal amount) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(currentDate,lastRefill);
        if (super.mayWithdraw(amount) && period.getMonths() >= 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void showBalance() {
        System.out.println("Deposit account balance is " + getAccountBalance());
    }
}
