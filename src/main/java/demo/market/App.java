package demo.market;

import demo.market.exception.InvalidCliArgumentException;
import demo.market.model.Product;
import demo.market.service.ProductsPrinter;
import demo.market.service.processor.ProcessorFactory;
import demo.market.service.source.CsvProductSource;
import demo.market.service.source.ProductDto;
import demo.market.service.source.StaticProductSource;
import demo.market.utility.ProductUtility;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class App {

    private static final int DEFAULT_ITERATIONS = 50;

    public static void main(String[] args) {
        var csvPath = parseCsvPathArgument(args);
        var iterations = parseIterationsArgument(args);

        var simulationBuilder = Simulation.builder()
            .processor(ProcessorFactory.defaultProcessor())
            .productsPrinter(new ProductsPrinter(true));

        csvPath.ifPresentOrElse(
            path -> simulationBuilder.productSource(new CsvProductSource(",", path)),
            () -> simulationBuilder.productSource(new StaticProductSource(ProductUtility.createProducts()))
        );
        iterations.ifPresentOrElse(
            simulationBuilder::iterations,
            () -> simulationBuilder.iterations(DEFAULT_ITERATIONS)
        );

        simulationBuilder.build().simulate();
    }

    private static Optional<Path> parseCsvPathArgument(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if ("--csv".equals(args[i])) {
                if (i + 1 >= args.length) {
                    throw new InvalidCliArgumentException("Missing value for --csv argument.");
                }

                Path path;
                try {
                    path = Path.of(args[i + 1]);
                } catch (InvalidPathException e) {
                    throw new InvalidCliArgumentException("Path to csv is invalid.");
                }

                if (!Files.exists(path)) {
                    throw new InvalidCliArgumentException("Csv does not exist.");
                }

                return Optional.of(path);
            }
        }
        return Optional.empty();
    }

    private static Optional<Integer> parseIterationsArgument(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if ("--i".equals(args[i])) {
                if (i + 1 >= args.length) {
                    throw new InvalidCliArgumentException("Missing value for --i argument.");
                }

                try {
                    var iterations = Integer.parseInt(args[i + 1]);
                    if (iterations < 1) {
                        throw new InvalidCliArgumentException("Iterations must be > 0.");
                    }
                    return Optional.of(iterations);
                } catch (NumberFormatException e) {
                    throw new InvalidCliArgumentException("Unable to parse iterations value.");
                }
            }
        }
        return Optional.empty();
    }

    private List<Product> mapProducts(List<ProductDto> productDtos) {
        return productDtos.stream()
            .map(this::mapProduct)
            .toList();
    }

    private Product mapProduct(ProductDto productDto) {
        return new Product(
            productDto.name(),
            productDto.type(),
            productDto.expiryDate(),
            productDto.quality(),
            productDto.basePrice(),
            productDto.basePrice(),
            false,
            false);
    }
}
