package demo.market.source;

import static org.assertj.core.api.Assertions.assertThat;

import demo.market.service.source.CsvProductSource;
import demo.market.service.source.ProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CsvProductSourceTest {

    @DisplayName("parses product from csv correctly")
    @Test
    void parsesProductCorrectly() {
        var expectedCsvProduct = new ProductDto("foo", "wine", 10, 20, 15.3);
        var csvProductSource = new CsvProductSource(";", "foo; wine; 10; 20; 15.3");

        var actualCsvProducts = csvProductSource.products();

        assertThat(actualCsvProducts)
            .hasSize(1)
            .contains(expectedCsvProduct);
    }
}