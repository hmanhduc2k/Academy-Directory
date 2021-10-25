package seedu.academydirectory.testutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import seedu.academydirectory.versioncontrol.objects.Commit;
import seedu.academydirectory.versioncontrol.objects.Tree;

/**
 * A utility class containing a list of {@code Commit} objects to be used in tests.
 */
public class TypicalCommits {
    private static final Date date;

    static {
        Date date1;
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/1998");
        } catch (ParseException e) {
            date1 = null;
            e.printStackTrace();
        }
        date = date1;
    }

    public static final Commit COMMIT1 = new CommitBuilder()
            .withHash("1d83638a25901e76c8e3882afca2347f8352cd06")
            .withAuthor("Alice Pauline")
            .withDate(date)
            .withMessage("Hello, World!")
            .withParentSupplier(() -> Commit.NULL)
            .withTreeSupplier(() -> Tree.NULL)
            .build();

    // Commit Chain
    public static final Commit COMMIT2 = new CommitBuilder()
            .withHash("1")
            .withAuthor("Alice")
            .withDate(date)
            .withMessage("This is Initial Commit")
            .withParentSupplier(() -> Commit.NULL)
            .withTreeSupplier(() -> Tree.NULL)
            .build();

    public static final Commit COMMIT3 = new CommitBuilder()
            .withHash("2")
            .withAuthor("Alice")
            .withDate(date)
            .withMessage("This is Second Commit")
            .withParentSupplier(() -> COMMIT2)
            .withTreeSupplier(() -> Tree.NULL)
            .build();

    public static final Commit COMMIT4 = new CommitBuilder()
            .withHash("3")
            .withAuthor("Alice")
            .withDate(date)
            .withMessage("This is third Commit")
            .withParentSupplier(() -> COMMIT2)
            .withTreeSupplier(() -> Tree.NULL)
            .build();

    public static final Commit COMMIT5 = new CommitBuilder()
            .withHash("4")
            .withAuthor("Alice")
            .withDate(date)
            .withMessage("This is fourth Commit")
            .withParentSupplier(() -> COMMIT4)
            .withTreeSupplier(() -> Tree.NULL)
            .build();

    public static List<Commit> getTypicalCommits() {
        return new ArrayList<>(List.of(COMMIT1, COMMIT2, COMMIT3));
    }
}
