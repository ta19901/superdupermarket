package demo.market;

import demo.market.model.Product;
import demo.market.service.ProductsPrinter;
import demo.market.service.processor.Processor;
import demo.market.service.source.ProductSource;
import demo.market.utility.ProductUtility;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import lombok.Builder;
import lombok.Getter;

public class Simulation {

    private final Processor processor;
    private final ProductsPrinter productsPrinter;
    private final int iterations;
    @Getter
    private final List<Product> products;

    @Builder
    public Simulation(Processor processor, ProductsPrinter productsPrinter,
                      ProductSource productSource, int iterations) {
        this.processor = processor;
        this.productsPrinter = productsPrinter;
        this.iterations = iterations;
        this.products = new ArrayList<>(ProductUtility.mapProducts(productSource.products()));
    }

    public void simulate() {
        productsPrinter.print(products, 0);
        IntStream.range(1, iterations + 1).forEach(day -> simulateDay(products, day));
    }

    private void simulateDay(List<Product> products, int day) {
        products.forEach(product -> processor.process(product, day));
        productsPrinter.print(products, day);
        products.removeIf(Product::isOverdue);
    }
}
