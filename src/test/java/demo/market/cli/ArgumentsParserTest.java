package demo.market.cli;

import demo.market.exception.InvalidCliArgumentException;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ArgumentsParserTest {

    @Test
    void parsesArgument() {
        var expectedValue = 10;
        var argument = Argument.of(
                "iterations",
                Integer::parseInt
        );

        var parser = new ArgumentsParser(Set.of(argument));

        parser.parse(new String[] {"--iterations", String.valueOf(expectedValue)});

        assertThat(argument.getValue()).hasValue(expectedValue);
    }

    @Test
    void throwsOnFirstMissingValue() {
        var argumentIterations = Argument.of("iterations");
        var parser = new ArgumentsParser(Set.of(argumentIterations));

        assertThatExceptionOfType(InvalidCliArgumentException.class)
                .isThrownBy(() -> parser.parse(new String[] {"--iterations"}));
    }

    @Test
    void throwsOnFurtherMissingValue() {
        var argumentFoo = Argument.of("foo");
        var argumentBar = Argument.of("bar");
        var parser = new ArgumentsParser(Set.of(argumentFoo, argumentBar));

        assertThatExceptionOfType(InvalidCliArgumentException.class)
                .isThrownBy(() -> parser.parse(new String[] {"--foo", "foo", "--bar"}));
    }
}
