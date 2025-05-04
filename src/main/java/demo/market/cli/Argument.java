package demo.market.cli;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Argument<T> {

    private static final UnaryOperator<String> NOOP = s -> s;

    @Getter private final String argument;
    private final Function<String, T> parseValue;
    private T value;

    public static Argument<String> of(String argument) {
        return new Argument<>(argument, NOOP);
    }

    public static <T> Argument<T> of(String argument, Function<String, T> parseValue) {
        return new Argument<>(argument, parseValue);
    }

    public Optional<T> getValue() {
        return Optional.ofNullable(value);
    }

    void setValue(String input) {
        this.value = parseValue.apply(input);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Argument<?> argument1 = (Argument<?>) o;
        return Objects.equals(argument, argument1.argument);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(argument);
    }
}
