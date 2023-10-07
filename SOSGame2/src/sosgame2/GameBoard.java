package sosgame2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class GameBoard extends JPanel {
	private final int BOARD_SIZE;
	private GameButton[][] buttons;
	private char[][] boardValues;
	private static Color currentColor = Color.RED;
	private int redScore = 0;
	private int blueScore = 0;
	private static boolean isSTurn = true;  // Start with "S" turn
	private static String currentLetter = "S"; // Default letter

	public GameBoard(int boardSize) {
	    this.BOARD_SIZE = boardSize;
	    boardValues = new char[BOARD_SIZE][BOARD_SIZE];
	    setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
	    buttons = new GameButton[BOARD_SIZE][BOARD_SIZE];
	    initializeButtons();
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
	        int boardSize = SOSGame2.promptForBoardSize();
	        frame.getContentPane().removeAll();
	        frame.add(new GameBoard(boardSize), BorderLayout.CENTER);
	        frame.add(SOSGame2.initializeControlPanel(), BorderLayout.NORTH);
	        SOSGame2.initializeMenu(frame);
	        frame.pack();
	        frame.setLocationRelativeTo(null);
	    }
	}

	
	public boolean isBoardFull() {
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
	            GameButton btn = new GameButton(i, j);
	            btn.addActionListener(new ActionListener() {
	                
	            	@Override
	            	public void actionPerformed(ActionEvent e) {
	            	    GameButton source = (GameButton) e.getSource();
	            	    if ("".equals(source.getText())) {
	            	        source.setText(GameBoard.getCurrentLetter());
	            	        source.setForeground(GameBoard.getCurrentColor());
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
	
	private void checkForSOS(int row, int col) {
	    int[][] directions = {
	        {0, 1},  // Horizontal
	        {1, 0},  // Vertical
	        {1, 1},  // Diagonal from top-left to bottom-right
	        {1, -1}  // Diagonal from top-right to bottom-left
	    };

	    for (int[] direction : directions) {
	        int countS = 0;
	        int countO = 0;

	        for (int i = -1; i <= 1; i++) {
	            int newRow = row + direction[0] * i;
	            int newCol = col + direction[1] * i;

	            if (newRow >= 0 && newRow < BOARD_SIZE && newCol >= 0 && newCol < BOARD_SIZE) {
	                if ("S".equals(buttons[newRow][newCol].getText())) {
	                    countS++;
	                } else if ("O".equals(buttons[newRow][newCol].getText())) {
	                    countO++;
	                }
	            }
	        }

	        if (countS == 2 && countO == 1) {
	            if (getCurrentColor() == Color.RED) {
	                redScore++;
	            } else {
	                blueScore++;
	            }
	            // Optionally, display the current scores
	            System.out.println("Red Score: " + redScore + ", Blue Score: " + blueScore);
	        }
	    }
	}

}
