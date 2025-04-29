package demo.market.service.processor.process;

import demo.market.model.Product;
import demo.market.service.pricing.QualityPricing;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class CheeseProcessTest {

    @DisplayName("Sets product overdue when quality is too low or it is expired")
    @ParameterizedTest
    @CsvSource({
        "cheese, 30, 1, 1.0",
        "cheese, 50, 30, 1.0"
    })
    void name(String type, int expiryDate, int quality, double basePrice) {
        var cheese = new Product("c1", type, expiryDate, basePrice, basePrice, quality,  false, false);
        var cheeseProcess = new CheeseProcess(new QualityPricing());

        cheeseProcess.process(cheese, 30);

        assertThat(cheese.isOverdue()).isTrue();
    }
}