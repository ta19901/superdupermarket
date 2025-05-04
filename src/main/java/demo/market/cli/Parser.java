package demo.market.cli;

import demo.market.exception.InvalidCliArgumentException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Parser {

    public static final Function<String, Path> TO_PATH = s -> {
        Path path;
        try {
            path = Path.of(s);
        } catch (InvalidPathException e) {
            throw new InvalidCliArgumentException("Path to csv is invalid.");
        }

        if (!Files.exists(path)) {
            throw new InvalidCliArgumentException("Csv does not exist.");
        }

        return path;
    };
}
