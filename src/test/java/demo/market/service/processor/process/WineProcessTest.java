package demo.market.service.processor.process;

import static org.assertj.core.api.Assertions.assertThat;

import demo.market.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WineProcessTest {

    @DisplayName("increases wine quality by 1 on day 10")
    @Test
    void increasesWineQuality() {
        var wineProcess = new WineProcess();
        var wine = Product.builder().type("wine").quality(0).build();

        wineProcess.process(wine, 10);

        assertThat(wine.getQuality()).isOne();
    }
}