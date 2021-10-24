package seedu.academydirectory.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.academydirectory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.academydirectory.commons.core.index.Index;
import seedu.academydirectory.logic.commands.ViewCommand;
import seedu.academydirectory.logic.parser.exceptions.ParseException;

public class ViewCommandParser implements Parser<ViewCommand> {

    @Override
    public ViewCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        if (userInput.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }
        Index index;
        try {
            index = ParserUtil.parseIndex(userInput);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }
        return new ViewCommand(index);
    }
}
