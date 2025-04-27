package demo.market.service.pricing;

public class QualityPricing implements PricingCalculator {

    private static final double MULTIPLICATOR = 0.1D;

    @Override
    public double calculate(double price, int quality) {
        return price + quality * MULTIPLICATOR;
    }
}
