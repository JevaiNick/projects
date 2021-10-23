package models;

import models.keys.KeyPurchase;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "purchaselist")
public class Purchases {
    @EmbeddedId
    KeyPurchase id;


    int price;

    @Column(name = "subscription_date")
    Date subscriptionDate;

    public KeyPurchase getId() {
        return id;
    }

    public void setId(KeyPurchase id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }
}
