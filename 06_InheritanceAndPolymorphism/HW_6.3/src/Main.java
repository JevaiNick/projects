import Clients.*;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Person client1 = new Person(1000.0);
        IndividualEntrepreneur clientIE = new IndividualEntrepreneur(10000.0);
        Entity clientEntity = new Entity(5000.0);
        client1.showInfo();
        clientEntity.showInfo();
        clientIE.showInfo();

        clientEntity.withdrawMoney(1000.0);
        clientEntity.showBalance();

        clientIE.putMoney(2000.0);
        clientIE.showBalance();

        client1.putMoney(-10.0);
        client1.withdrawMoney(1050.0);
        client1.showBalance();
    }
}
