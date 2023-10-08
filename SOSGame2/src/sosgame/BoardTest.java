package sosgame;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import sosgame.Board;
import sosgame.Button; // Assuming Button is your button class

public class BoardTest {
    private Board gameBoard;

    @Before
    public void setUp() {
        gameBoard = new Board(8); // Change the board size as needed
    }

    @Test
    public void testIsBoardFullInitiallyFalse() {
        assertFalse(gameBoard.isBoardFull());
    }

    @Test
    public void testIsBoardFullAfterFilling() {
        // Fill the entire board
        Button[][] buttons = (Button[][]) gameBoard.getButtons();
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                // Use the actual Button objects to set text
                buttons[i][j].setText("S");
            }
        }
        assertTrue(gameBoard.isBoardFull());
    }

    // Add more test methods as needed
}
