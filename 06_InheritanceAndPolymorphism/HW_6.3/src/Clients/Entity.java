package Clients;

import java.math.BigDecimal;

public class Entity extends Client {
    public Entity(double amount) {
        super(amount);
    }

    private final Double COMMISSION_FOR_REPLENISHMENT = 0.01;

    @Override
    public void withdrawMoney(double amount) {
            System.out.printf("Try to withdraw %.2f with %s commission.\n",amount, asPercent(COMMISSION_FOR_REPLENISHMENT));
            amount = amount + (amount * COMMISSION_FOR_REPLENISHMENT);
            super.withdrawMoney(amount);
    }

    @Override
    public void showInfo() {
        System.out.printf("Entity: Replenishment occurs without commission."+
                " Withdraw occur with %s commission.\n", asPercent(COMMISSION_FOR_REPLENISHMENT));
    }

}
