package demo.market.service.processor.process;

import demo.market.model.Product;
import demo.market.service.pricing.PricingCalculator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultProcess implements Process {

    private final PricingCalculator calculator;

    @Override
    public void process(Product product, int day) {
        product.setCurrentPrice(
            calculator.calculate(product.getBasePrice(), product.getQuality()));
    }
}
