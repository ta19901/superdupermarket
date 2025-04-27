package demo.market.service.processor.process;

import demo.market.model.Product;
import demo.market.service.pricing.PricingCalculator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CheeseProcess implements Process {

    private static final int QUALITY_THRESHOLD = 30;

    private final PricingCalculator calculator;

    @Override
    public void process(Product product, int day) {
        if (product.getQuality() > 0) {
            product.setQuality(product.getQuality() - 1);
        }
        product.setOverdue(isBad(product) || isExpired(product, day));
        product.setCurrentPrice(calculator.calculate(product.getBasePrice(), product.getQuality()));
    }

    private boolean isBad(Product product) {
        return product.getQuality() < QUALITY_THRESHOLD;
    }

    private boolean isExpired(Product product, int day) {
        return  day > product.getExpiryDate();
    }
}
