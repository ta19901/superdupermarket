package demo.market;

import demo.market.model.Product;
import demo.market.service.ProductsPrinter;
import demo.market.service.processor.ProcessorFactory;
import demo.market.service.source.ProductDto;
import demo.market.service.source.StaticProductSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SimulationIT {

    private static Simulation createSimulation(List<ProductDto> products, int iterations) {
        var processor = ProcessorFactory.defaultProcessor();
        var productsPrinter = new ProductsPrinter(false);
        var productSource = new StaticProductSource(products);
        return new Simulation(processor, productsPrinter, productSource, iterations);
    }

    @DisplayName("simulate removal of overdue product")
    @Test
    void simulates() {
        var iterations = 6;
        var wine = new ProductDto("w1", "wine", 30, 28, 9D);
        var cheese = new ProductDto("c1", "cheese", 30, 35, 5D);
        var simulation = createSimulation(List.of(wine, cheese), iterations);

        simulation.simulate();

        assertThat(simulation.getProducts())
            .hasSize(1)
            .element(0)
            .extracting(Product::getType)
            .isEqualTo("wine");
    }
}