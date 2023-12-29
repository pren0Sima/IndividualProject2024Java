package org.transportCompanyProject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
@Table(name = "goods")
public class Goods extends Cargo {
    @Positive
    @Column(name = "weight")
    private BigDecimal weight;

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Goods() {
    }

    public Goods(long id) {
        super(id);
    }

    public Goods(BigDecimal weight) {
        this.weight = weight;
    }

    public Goods(long id, BigDecimal weight) {
        super(id);
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "weight=" + weight +
                "} " + super.toString();
    }
}
