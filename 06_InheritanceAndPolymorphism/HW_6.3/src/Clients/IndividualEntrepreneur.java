package Clients;

import java.math.BigDecimal;
import java.util.zip.DeflaterOutputStream;

public class IndividualEntrepreneur extends Client {
    public IndividualEntrepreneur(double amount) {
        super(amount);
    }

    private final double PERCENT_OVER_BORDER = 0.005;
    private final double PERCENT_BELOW_BORDER = 0.01;
    private final double BORDER = 1000.0;

    @Override
    public void putMoney(double amount) {
            amount = amount - (amount * ((amount >= BORDER) ? PERCENT_OVER_BORDER : PERCENT_BELOW_BORDER));
            System.out.printf("Try to top up %.2f with %s commission.\n", amount, asPercent((amount >= BORDER) ? PERCENT_OVER_BORDER : PERCENT_BELOW_BORDER));
       super.putMoney(amount);
    }



    @Override
    public void showInfo() {
        System.out.printf("Individual Entrepreneur: Replenishment occurs with commission of %s if the amount less then %.2f" +
                " and with commission %s if amount more or equal than %.2f.\n", asPercent(PERCENT_BELOW_BORDER), BORDER, asPercent(PERCENT_OVER_BORDER), BORDER);
    }


}
