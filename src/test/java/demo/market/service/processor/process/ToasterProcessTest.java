package demo.market.service.processor.process;

import demo.market.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ToasterProcessTest {

    @DisplayName("applies discount when processed")
    @ParameterizedTest
    @CsvSource({"5,9", "10,8", "15,7"})
    void appliesDiscount(Integer day, Double expectedPrice) {
        var toasterProcess = new ToasterProcess();
        var toaster = Product.builder().type("toaster").basePrice(10D).currentPrice(10D).build();

        toasterProcess.process(toaster, day);

        assertThat(toaster.getCurrentPrice()).isEqualTo(expectedPrice);
    }

    @DisplayName("declares toaster overdue when minimum price is reached")
    @Test
    void declaresOverdue() {
        var toasterProcess = new ToasterProcess();
        var toaster = Product.builder().type("toaster").basePrice(80D).currentPrice(80D).build();

        toasterProcess.process(toaster, 40);

        assertThat(toaster.isOverdue()).isTrue();
    }
}