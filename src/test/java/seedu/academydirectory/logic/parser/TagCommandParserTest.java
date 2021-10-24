package seedu.academydirectory.logic.parser;

import static seedu.academydirectory.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.academydirectory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.academydirectory.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.academydirectory.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.academydirectory.logic.commands.TagCommand;
import seedu.academydirectory.model.tag.Tag;

public class TagCommandParserTest {
    private final TagCommandParser parser = new TagCommandParser();
    private final Set<Tag> tag = new HashSet<>();
    private final String validTagName = "mission";
    private final String invalidTagName = "!!!!!!";
    private final String expectedInvalidTagMessage = Tag.MESSAGE_CONSTRAINTS;

    @Test
    public void parse_validArgument_success() {
        String input = INDEX_FIRST_STUDENT.getOneBased() + " "
                + PREFIX_TAG + validTagName;
        Tag validTag = new Tag(validTagName);
        tag.clear();
        tag.add(validTag);
        TagCommand expectedCommand =
                new TagCommand(INDEX_FIRST_STUDENT, tag);

        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_invalidArgument_failure() {
        String inputInvalidTag = INDEX_FIRST_STUDENT.getOneBased() + " "
                + PREFIX_TAG + invalidTagName;

        assertParseFailure(parser, inputInvalidTag, expectedInvalidTagMessage);
    }
}
