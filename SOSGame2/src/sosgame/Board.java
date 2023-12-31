// Board.java
package sosgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends JPanel {
    protected static int BOARD_SIZE;
    private static Button[][] buttons;
    protected char[][] boardValues;
    protected static Color currentColor = Color.RED;
    private int redScore = 0;
    private int blueScore = 0;
    private static boolean isSTurn = true;  // Start with "S" turn
    private static String currentLetter = "S"; // Default letter

    private ComputerPlayer computerPlayer;

    public Board(int boardSize) {
        this.BOARD_SIZE = boardSize;
        boardValues = new char[BOARD_SIZE][BOARD_SIZE];
        setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        buttons = new Button[BOARD_SIZE][BOARD_SIZE];
        initializeButtons();
    }

    public Board(int boardSize, ComputerPlayer computerPlayer) {
        this(boardSize);
        this.computerPlayer = computerPlayer;
    }

    private Color currentPlayerColor = Color.RED;

    public void makeMove(int i, int j, String string, String string2) {
        // TODO: Implement the logic for making a move

        // Toggle between "Red" and "Blue" for each move
        currentPlayerColor = (currentPlayerColor == Color.RED) ? Color.BLUE : Color.RED;
    }

    public Color getCurrentPlayerColor() {
        return currentPlayerColor;
    }

    public static void setCurrentColor(Color color) {
        currentColor = color;
    }

    public static Color getCurrentColor() {
        return currentColor;
    }

    public void displayEndOfGamePopup(JFrame frame) {
        String winner = redScore > blueScore ? "Red" : "Blue";
        if (redScore == blueScore) {
            winner = "It's a tie!";
        }
        String message = "Scores:\nRed: " + redScore + "\nBlue: " + blueScore + "\nWinner: " + winner;

        Object[] options = {"Start New Game", "Dismiss"};

        int choice = JOptionPane.showOptionDialog(frame, message, "Game Over",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == JOptionPane.YES_OPTION) {
            int boardSize = SOSGame.promptForBoardSize();
            frame.getContentPane().removeAll();
            frame.add(new BoardGeneral(boardSize, message), BorderLayout.CENTER);
            frame.add(SOSGame.initializeControlPanel(), BorderLayout.NORTH);
            SOSGame.initializeMenu(frame);
            frame.pack();
            frame.setLocationRelativeTo(null);
        }
    }

    public static boolean isBoardFull() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if ("".equals(buttons[i][j].getText())) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void setCurrentLetter(String letter) {
        currentLetter = letter;
    }

    public static String getCurrentLetter() {
        return currentLetter;
    }

    private void initializeButtons() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Button btn = new Button(i, j);
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Button source = (Button) e.getSource();
                        if ("".equals(source.getText())) {
                            source.setText(Board.getCurrentLetter());
                            source.setForeground(Board.getCurrentColor());

                            // Check for an SOS match and update the score immediately after setting the button text and color.
                            checkForSOS(source.getRow(), source.getCol());

                            if (isBoardFull()) {
                                displayEndOfGamePopup((JFrame) SwingUtilities.getWindowAncestor(source));
                            }
                        }
                    }
                });
                buttons[i][j] = btn;
                add(btn);
            }
        }
    }

    void checkForSOS(int row, int col) {
        // Potential sequences
        String[] potentialMatches = {
                getStringValue(row, col - 1) + getStringValue(row, col) + getStringValue(row, col + 1), // Horizontal
                getStringValue(row - 1, col) + getStringValue(row, col) + getStringValue(row + 1, col), // Vertical
                getStringValue(row - 1, col - 1) + getStringValue(row, col) + getStringValue(row + 1, col + 1), // Diagonal top-left to bottom-right
                getStringValue(row - 1, col + 1) + getStringValue(row, col) + getStringValue(row + 1, col - 1), // Diagonal top-right to bottom-left

                getStringValue(row, col - 2) + getStringValue(row, col-1) + getStringValue(row, col), // Horizontal-left
                getStringValue(row , col) + getStringValue(row, col+1) + getStringValue(row, col+2), // Horizontal-right

                getStringValue(row-2, col) + getStringValue(row-1, col) + getStringValue(row, col), // Vertical-top
                getStringValue(row, col) + getStringValue(row+1, col) + getStringValue(row + 2, col), // Vertical-bottom

                getStringValue(row-2, col - 2) + getStringValue(row-1, col-1) + getStringValue(row, col), // D-T-L
                getStringValue(row , col) + getStringValue(row+1, col+1) + getStringValue(row + 2, col+2), // D-B-R

                getStringValue(row+2, col-2) + getStringValue(row+1, col-1) + getStringValue(row, col), // D-B-L
                getStringValue(row, col) + getStringValue(row-1, col+1) + getStringValue(row-2, col+2) // D-T-R
        };

        for (String sequence : potentialMatches) {
            if ("SOS".equals(sequence)) {
                if (getCurrentColor() == Color.RED) {
                    redScore++;
                } else {
                    blueScore++;
                }
                // Print score
                System.out.println("Red Score: " + redScore + ", Blue Score: " + blueScore);
                // Exit after the first match
            }
        }
    }

    // Get the value of a board cell
    private String getStringValue(int i, int j) {
        if (i >= 0 && i < BOARD_SIZE && j >= 0 && j < BOARD_SIZE) {
            return buttons[i][j].getText();
        }
        return "";
    }

    public Button[][] getButtons() {
        return buttons;
    }

    public void makeComputerMove() {
        // TODO: Implement the computer's move logic
    }

    // Other methods and code common to both modes
}