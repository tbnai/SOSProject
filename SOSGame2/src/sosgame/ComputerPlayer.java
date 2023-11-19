// ComputerPlayer.java
package sosgame;

import java.awt.*;
import java.util.Random;

public class ComputerPlayer {
    private Color playerColor;
    private Board gameBoard;

    public ComputerPlayer(Color playerColor, Board gameBoard) {
        this.playerColor = playerColor;
        this.gameBoard = gameBoard;
    }

    public void makeMove() {
        if (gameBoard != null) {
            // Implement the logic for the computer's move
            // For simplicity, a random move is made in this example

            Button[][] buttons = gameBoard.getButtons();
            int boardSize = gameBoard.BOARD_SIZE;
            Random random = new Random();

            int randomRow, randomCol;
            do {
                randomRow = random.nextInt(boardSize);
                randomCol = random.nextInt(boardSize);
            } while (!"".equals(buttons[randomRow][randomCol].getText()));

            buttons[randomRow][randomCol].setText(Board.getCurrentLetter());
            buttons[randomRow][randomCol].setForeground(Board.getCurrentColor());
            gameBoard.checkForSOS(randomRow, randomCol);

            if (gameBoard.isBoardFull()) {
                gameBoard.displayEndOfGamePopup(null);
            }
        }
    }
}