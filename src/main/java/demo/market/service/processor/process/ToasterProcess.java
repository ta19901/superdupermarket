package demo.market.service.processor.process;

import demo.market.model.Product;

public class ToasterProcess implements Process {

    private static final double DISCOUNT_MULTIPLICATOR = 0.1;
    private static final double MAXIMUM_DISCOUNT = 0.9;
    private static final double MINIMUM_PRICE = 20;

    private static double calculateDiscount(int day) {
        return Math.min((day / 5D) * DISCOUNT_MULTIPLICATOR, MAXIMUM_DISCOUNT);
    }

    @Override
    public void process(Product product, int day) {
        if (day % 5 == 0) {
            product.setCurrentPrice(product.getBasePrice() * (1 - calculateDiscount(day)));
        }

        if (product.getCurrentPrice() < MINIMUM_PRICE) {
            product.setOverdue(true);
        }
    }
}
