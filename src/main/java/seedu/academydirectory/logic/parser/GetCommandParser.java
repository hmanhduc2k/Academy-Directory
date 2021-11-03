package seedu.academydirectory.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.academydirectory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.academydirectory.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.academydirectory.logic.commands.GetCommand;
import seedu.academydirectory.logic.parser.exceptions.ParseException;
import seedu.academydirectory.model.student.NameContainsKeywordsPredicate;
import seedu.academydirectory.model.student.PersonalDetailRetriever;

/**
 * Parses input arguments and creates a new GetCommand object
 */
public class GetCommandParser implements Parser<GetCommand> {
    private static final Supplier<Stream<Prefix>> RELEVANT_PREFIXES_SUPPLIER = () ->
            Stream.of(PersonalDetailRetriever.SUPPORTED_PREFIX.toArray(Prefix[]::new));

    /**
     * Parses the given {@code String} of arguments in the context of the GetCommand
     * and returns a GetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GetCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        Stream.concat(Stream.of(PREFIX_NAME), RELEVANT_PREFIXES_SUPPLIER.get())
                                .toArray(Prefix[]::new));

        // Check at least one relevant prefix is provided
        boolean noPrefix = RELEVANT_PREFIXES_SUPPLIER.get()
                .map(x -> argMultimap.getAllValues(x).isEmpty())
                .reduce(true, Boolean::logicalAnd);

        if (noPrefix) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetCommand.MESSAGE_USAGE));
        }

        List<String> nameList = argMultimap.getAllValues(PREFIX_NAME).stream()
                .flatMap(name -> Stream.of(name.split("\\s")))
                .collect(Collectors.toList());
        NameContainsKeywordsPredicate nameContainsKeywordsPredicate = nameList.isEmpty()
                ? null
                : new NameContainsKeywordsPredicate(nameList);

        List<PersonalDetailRetriever> functions = RELEVANT_PREFIXES_SUPPLIER.get()
                .filter(x -> !argMultimap.getAllValues(x).isEmpty())
                .map(x -> new PersonalDetailRetriever(x, nameContainsKeywordsPredicate))
                .collect(Collectors.toList());

        return new GetCommand(functions);
    }

}
