package sosgame;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import sosgame.BoardGeneral;

public class BoardGeneralTest {
    private BoardGeneral gameBoard;

    @Before
    public void setUp() {
        // Initialize a General Game Mode board before each test
        gameBoard = new BoardGeneral(8); // Assuming an 8x8 board
    }

    @Test
    public void testGeneralGameModeInitialization() {
        // Verify that the General Game Mode board is correctly initialized
        assertEquals(8, gameBoard.getBoardSize());
        assertEquals(0, gameBoard.getRedScore());
        assertEquals(0, gameBoard.getBlueScore());
    }

    @Test
    public void testGeneralGameModeMove() {
        // Simulate a move in General Game Mode
        gameBoard.makeMove(0, 0, "S", "Red");

        // Verify that the move was correctly registered
        assertEquals("S", gameBoard.getCellValue(0, 0));
        assertEquals("Red", gameBoard.getCellColor(0, 0));
    }

    @Test
    public void testGeneralGameModeScoring() {
        // Simulate a sequence that results in a point for Blue
        gameBoard.makeMove(0, 0, "S", "Red");
        gameBoard.makeMove(0, 1, "O", "Blue");
        gameBoard.makeMove(0, 2, "S", "Red");
        gameBoard.makeMove(0, 3, "S", "Blue");

        // Verify that the scoring updates correctly
        assertEquals(1, gameBoard.getRedScore());
        assertEquals(1, gameBoard.getBlueScore());
    }

    // Add more test methods to cover specific game logic scenarios
}
