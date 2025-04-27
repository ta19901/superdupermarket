package demo.market;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import demo.market.exception.InvalidCliArgumentException;
import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void simulatePredefined() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }

    @Test
    void simulateCsv() {
        assertDoesNotThrow(() -> App.main(new String[]{"--csv", "example.csv"}));
    }

    @Test
    void throwsOnInvalidPath() {
        assertThrows(InvalidCliArgumentException.class, () -> App.main(new String[]{"--csv", '\0' + "invalidPath"}));
    }

    @Test
    void throwsOnCsvIsNonexistent() {
        assertThrows(InvalidCliArgumentException.class,
            () -> App.main(new String[]{"--csv", "whatever/path/you/take.csv"}));
    }

    // ...
}