package demo.market.cli;

import demo.market.exception.InvalidCliArgumentException;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ArgumentsParser {

    private static final String ARGUMENT_PREFIX = "--";

    private final Map<String, Argument<?>> arguments;

    public ArgumentsParser(Set<Argument<?>> arguments) {
        this.arguments = arguments.stream()
                .collect(Collectors.toMap(Argument::getArgument, Function.identity()));
    }

    private static boolean isArgument(String arg) {
        return arg.startsWith(ARGUMENT_PREFIX);
    }

    private static void validateNext(String[] args, int i) {
        if (i + 1 >= args.length || args[i + 1].startsWith(ARGUMENT_PREFIX)) {
            throw new InvalidCliArgumentException(String.format("Incorrect value provided for argument %s", args[i]));
        }
    }

    public void parse(String[] args) {
        for(int i = 0; i < args.length; i++) {
            if (isArgument(args[i])) {
                validateNext(args, i);
                var name = args[i].substring(2);
                var argument = arguments.get(name);
                if (Objects.nonNull(argument)) {
                    argument.setValue(args[i + 1]);
                }
            }
        }
    }
}
