package seedu.academydirectory.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult1 = new CommandResult("sample feedback");
        assertEquals(commandResult1, new CommandResult("sample feedback"));
        assertNotEquals(commandResult1, new CommandResult("sample feedback 1"));
        CommandResult commandResult2 = new CommandResult("sample feedback",
                Optional.empty(), CommandResult.Type.VIEW);
        assertEquals(commandResult2, new CommandResult("sample feedback",
                Optional.empty(), CommandResult.Type.VIEW));
        assertNotEquals(commandResult2, commandResult1);
    }
}
