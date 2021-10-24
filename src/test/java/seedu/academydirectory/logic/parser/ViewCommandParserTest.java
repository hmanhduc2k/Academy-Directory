package seedu.academydirectory.logic.parser;

import static seedu.academydirectory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.academydirectory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.academydirectory.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.academydirectory.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.academydirectory.testutil.TypicalIndexes.INDEX_THIRD_STUDENT;

import org.junit.jupiter.api.Test;

import seedu.academydirectory.logic.commands.ViewCommand;

public class ViewCommandParserTest {
    private ViewCommandParser viewCommandParser = new ViewCommandParser();

    /**
     * ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
     */
    @Test
    public void parse_validArgument_success() {
        ViewCommand expectedCommand1 = new ViewCommand(INDEX_THIRD_STUDENT);
        assertParseSuccess(viewCommandParser, "3", expectedCommand1);
        ViewCommand expectedCommand2 = new ViewCommand(INDEX_FIRST_STUDENT);
        assertParseSuccess(viewCommandParser, "1", expectedCommand2);
    }

    @Test
    public void parse_invalidArgument_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);
        assertParseFailure(viewCommandParser, "CS2103T", expectedMessage);
        assertParseFailure(viewCommandParser, "please send help", expectedMessage);
    }
}
