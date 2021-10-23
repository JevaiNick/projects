package models;

import models.keys.KeyLinkedPurchase;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "linked_purchase_list")
public class LinkedPurchaseList {
    @EmbeddedId
    private KeyLinkedPurchase id;

    public KeyLinkedPurchase getId() {
        return id;
    }

    public void setId(KeyLinkedPurchase id) {
        this.id = id;
    }
}
