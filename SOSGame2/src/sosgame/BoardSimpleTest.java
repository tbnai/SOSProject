package sosgame;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import sosgame.BoardSimple;

public class BoardSimpleTest {
    private BoardSimple gameBoard;

    @Before
    public void setUp() {
        // Initialize a Simple Game Mode board before each test
        gameBoard = new BoardSimple(8); // Assuming an 8x8 board
    }

    @Test
    public void testSimpleGameModeInitialization() {
        // Verify that the Simple Game Mode board is correctly initialized
        assertEquals(8, gameBoard.getSize());
        assertEquals(0, gameBoard.getRedScore());
        assertEquals(0, gameBoard.getBlueScore());
    }

    @Test
    public void testSimpleGameModeMove() {
        // Simulate a move in Simple Game Mode
        gameBoard.makeMove(0, 0, "S", "Red");

        // Verify that the move was correctly registered
        assertEquals("S", gameBoard.getCellValue(0, 0));
        assertEquals("Red", gameBoard.getCellColor(0, 0));
    }

    @Test
    public void testSimpleGameModeScoring() {
        // Simulate a sequence that results in a point for Red
        gameBoard.makeMove(0, 0, "S", "Red");
        gameBoard.makeMove(0, 1, "O", "Blue");
        gameBoard.makeMove(0, 2, "S", "Red");

        // Verify that the scoring updates correctly
        assertEquals(1, gameBoard.getRedScore());
        assertEquals(0, gameBoard.getBlueScore());
    }

    // Add more test methods to cover specific game logic scenarios
}

