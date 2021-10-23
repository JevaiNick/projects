package Clients;

import java.math.BigDecimal;

abstract class Client {
    protected double balance;

    protected Client(double amount){
        balance = amount;
    }

    /*public void putMoney(BigDecimal bigDecimalAmount) {
        System.out.println("Try to top up " + bigDecimalAmount);
        balance = balance.putMoney(bigDecimalAmount);
        System.out.println("Balance has been successfully replenished!");
    }*/

    /*public void withdrawMoney(BigDecimal bigDecimalAmount) {
        System.out.println("Try to withdraw " + bigDecimalAmount);
        if (mayWithdraw(bigDecimalAmount)) {
            balance = balance.subtract(bigDecimalAmount);
            System.out.println("Money has been successfully withdraw!");
        } else {
            System.out.println("Insufficient funds.");
        }
    }*/

    public abstract void showInfo();

    protected boolean mayWithdraw(double amount){
        if (balance >= amount){
            return true;
        }
        return false;
    }

    public void showBalance(){System.out.printf("Balance is %.2f\n", balance); }

    public void putMoney(double amount){
        if (amount >= 0) {
            System.out.println("Try to top up " + amount);
            balance += amount;
            System.out.println("Balance has been successfully replenished!");
        }else{
            System.out.println("Amount is below zero.");
        }
    }

    public void withdrawMoney(double amount){
        if (amount >= 0) {
            System.out.printf("Try to withdraw %.2f\n",amount);
            if (mayWithdraw(amount)) {
                balance -= amount;
                System.out.println("Money has been successfully withdraw!");
            } else {
                System.out.println("Insufficient funds.");
            }
        }else{
            System.out.println("Amount is below zero.");
        }
    }

    public String asPercent(double coeff) {

        return (coeff * 100) + "%";
    }
}
