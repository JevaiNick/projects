package Clients;

import java.math.BigDecimal;

public class Person extends Client {


    public Person(double amount) {
        super(amount);
    }

    @Override
    public void showInfo() {
        System.out.println("Person: Withdraw and replenishment occurs without commission.");
    }


}
