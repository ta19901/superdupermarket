package demo.market.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Product {

    private final String name;
    private final String type;
    private final int expiryDate;
    private int quality;
    private final double basePrice;
    private double currentPrice;
    private boolean shelved;
    private boolean overdue;

    @Override
    public String toString() {
        return "Name: " + name + ", " +
            "Expiry Date: " + expiryDate + ", " +
            "Quality: " + quality + ", " +
            "Base Price: " + basePrice + ", " +
            "Current Price: " + currentPrice + ", " +
            "Overdue: " + overdue;
    }
}
