package models;

import models.keys.KeySubscription;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "subscriptions")
public class Subscription {
    @EmbeddedId
    private KeySubscription id;



    @Column(name = "subscription_date")
    Date subscriptionDate;

    public KeySubscription getId() {
        return id;
    }

    public void setId(KeySubscription id) {
        this.id = id;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }
}
