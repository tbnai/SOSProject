package sosgame;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import org.junit.Test;

public class SOSGameTest {
    @Test
    public void testPromptForBoardSizeWithValidInput() {
        // Simulate user input of "6"
        System.setIn(new ByteArrayInputStream("6".getBytes()));

        int boardSize = SOSGame.promptForBoardSize();
        assertEquals(6, boardSize);
    }

    @Test
    public void testPromptForBoardSizeWithInvalidInput() {
        // Simulate user input of "invalid"
        System.setIn(new ByteArrayInputStream("invalid".getBytes()));

        int boardSize = SOSGame.promptForBoardSize();
        // Expecting the default size (8) due to invalid input
        assertEquals(8, boardSize);
    }

}
