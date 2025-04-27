package demo.market.service.processor.process;

import static org.assertj.core.api.Assertions.assertThat;

import demo.market.model.Product;
import demo.market.service.pricing.QualityPricing;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DefaultProcessTest {

    @DisplayName("calculates daily price by quality")
    @Test
    void calculatesDailyPrice() {
        var defaultProcess = new DefaultProcess(new QualityPricing());
        var unknown = Product.builder().type("unknown").basePrice(1D).quality(10).build();

        defaultProcess.process(unknown, -1);

        assertThat(unknown.getCurrentPrice()).isEqualTo(2D);
    }
}