package demo.market;

import demo.market.cli.Argument;
import demo.market.cli.ArgumentsParser;
import demo.market.cli.Parser;
import demo.market.exception.InvalidCliArgumentException;
import demo.market.service.ProductsPrinter;
import demo.market.service.processor.ProcessorFactory;
import demo.market.service.source.CsvProductSource;
import demo.market.service.source.SqlProductSource;
import demo.market.service.source.StaticProductSource;
import demo.market.utility.ProductUtility;

import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Stream;

public class App {

    private static final int DEFAULT_ITERATIONS = 50;

    public static void main(String[] args) {
        var cliArguments = CliArguments.create();
        var parser = new ArgumentsParser(cliArguments.asSet());
        parser.parse(args);

        validateArguments(cliArguments);

        var simulationBuilder = Simulation.builder()
                .processor(ProcessorFactory.defaultProcessor())
                .productsPrinter(new ProductsPrinter(true))
                .iterations(cliArguments.iterations.getValue().orElse(DEFAULT_ITERATIONS))
                .productSource(new StaticProductSource(ProductUtility.createProducts()));

        cliArguments.csv.getValue().ifPresent(path -> simulationBuilder.productSource(new CsvProductSource(",", path)));

        if (allPresent(cliArguments)) {
            var jdbcConn = new SqlProductSource.JdbcConn(
                    cliArguments.host.getValue().orElseThrow(),
                    cliArguments.port.getValue().orElseThrow(),
                    cliArguments.database.getValue().orElseThrow(),
                    cliArguments.user.getValue().orElseThrow(),
                    cliArguments.password.getValue().orElseThrow()
            );
            simulationBuilder.productSource(new SqlProductSource(jdbcConn));
        }

        simulationBuilder.build().simulate();
    }

    private static void validateArguments(CliArguments cliArguments) {
        var csvPresent = cliArguments.csv.getValue().isPresent();

        var sqlArguments = Set.of(
                cliArguments.host,
                cliArguments.port,
                cliArguments.database,
                cliArguments.user,
                cliArguments.password
        );

        var sqlAnyPresent = sqlArguments.stream().anyMatch(argument -> argument.getValue().isPresent());
        var sqlAllPresent = sqlArguments.stream().allMatch(argument -> argument.getValue().isEmpty());

        if (csvPresent && sqlAnyPresent) {
            throw new InvalidCliArgumentException(
                    "Exactly one data source must be provided: either --csv or full SQL configuration.");
        }

        if (!csvPresent && sqlAnyPresent && !sqlAllPresent) {
            throw new InvalidCliArgumentException(
                    "Missing arguments for SQL data source. Define --host, --port, --user, --db and --password.");
        }

        cliArguments.iterations.getValue().ifPresent(iterations -> {
            if (iterations <= 0) {
                throw new InvalidCliArgumentException("Iterations must be > 0.");
            }
        });
    }

    private static boolean allPresent(CliArguments cliArguments) {
        return Stream.of(
                cliArguments.host,
                cliArguments.port,
                cliArguments.database,
                cliArguments.user,
                cliArguments.password
        ).allMatch(argument -> argument.getValue().isPresent());
    }

    private record CliArguments(
            Argument<Integer> iterations,
            Argument<Path> csv,
            Argument<String> host,
            Argument<Integer> port,
            Argument<String> database,
            Argument<String> user,
            Argument<String> password
    ) {
        static CliArguments create() {
            return new CliArguments(
                    Argument.of("iterations", Integer::parseInt),
                    Argument.of("csv", Parser.TO_PATH),
                    Argument.of("host"),
                    Argument.of("port", Integer::parseInt),
                    Argument.of("db"),
                    Argument.of("user"),
                    Argument.of("password")
            );
        }

        Set<Argument<?>> asSet() {
            return Set.of(iterations, csv, host, port, database, user, password);
        }
    }
}
