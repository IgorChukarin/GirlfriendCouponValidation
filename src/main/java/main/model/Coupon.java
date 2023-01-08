package main.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Coupon {

    @Id
    private int id;
    private String description;
    private boolean used;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
