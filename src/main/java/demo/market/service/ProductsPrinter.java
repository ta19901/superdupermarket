package demo.market.service;

import demo.market.model.Product;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ProductsPrinter {

    private static final String PATTERN_HEADER = "%-20s %6s %8s %8s%n";
    private static final String PATTERN_PRODUCT = "%-20s %6.1f %8d %8s%n";
    private static final String DIVIDER_LONG = "--------------------";
    private static final String DIVIDER_MID = "--------";
    private static final String DIVIDER_SHORT = "------";

    private boolean enabled = true;

    public void print(List<Product> products, int day) {
        if (enabled) {
            System.out.printf("Day %d %n", day);
            System.out.printf(PATTERN_HEADER, "Name", "Price", "Quality", "Overdue");
            System.out.printf(PATTERN_HEADER, DIVIDER_LONG, DIVIDER_SHORT, DIVIDER_MID, DIVIDER_MID);
            products.forEach(ProductsPrinter::printProduct);
            System.out.println();
        }
    }

    private static void printProduct(Product product) {
        System.out.printf(
            PATTERN_PRODUCT, product.getName(), product.getCurrentPrice(), product.getQuality(), product.isOverdue());
    }
}
