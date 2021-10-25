package seedu.academydirectory.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.academydirectory.commons.core.Messages;
import seedu.academydirectory.model.VersionedModel;
import seedu.academydirectory.model.student.NameContainsKeywordsPredicate;

/**
 * Finds and lists all students in academy directory whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String HELP_MESSAGE = "### Locating students by name: `find`\n"
            + "\n"
            + "Finds students whose names contain any of the given keywords.\n"
            + "\n"
            + "Format: `find KEYWORD [MORE_KEYWORDS]`\n"
            + "\n"
            + "* The search is case-insensitive. e.g `hans` will match `Hans`\n"
            + "* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`\n"
            + "* Only the name is searched.\n"
            + "* Only full words will be matched e.g. `Han` will not match `Hans`\n"
            + "* Students matching at least one keyword will be returned (i.e. `OR` search).\n"
            + "  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`\n"
            + "\n"
            + "Examples:\n"
            + "* `find John` returns `john` and `John Doe`\n"
            + "* `find alex david` returns `Alex Yeoh`, `David Li`<br>";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all students whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(VersionedModel model) {
        requireNonNull(model);
        model.updateFilteredStudentList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, model.getFilteredStudentList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
