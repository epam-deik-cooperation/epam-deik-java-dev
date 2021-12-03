package hu.unideb.inf.ticketservice.model.component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.transaction.Transactional;
import java.util.Objects;

@Entity
@Transactional
public class PriceComponent {

    @Id
    @GeneratedValue
    private Long id;

    protected String name;
    protected Integer price;

    public PriceComponent(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    protected PriceComponent() {

    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PriceComponent component = (PriceComponent) o;
        return Objects.equals(name, component.name) && Objects.equals(price, component.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}
