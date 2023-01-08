package main.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Coupon {

    @Id
    private int id;
    private String description;
    private boolean relevant;

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

    public boolean isRelevant() {
        return relevant;
    }

    public void setRelevant(boolean relevant) {
        this.relevant = relevant;
    }
}
