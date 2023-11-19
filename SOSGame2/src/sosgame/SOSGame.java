// SOSGame.java
package sosgame;

import javax.swing.*;
import java.awt.*;

public class SOSGame {
    private static Board gameBoard;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Ask the user to choose the opponent type
            String[] opponentTypes = {"Player vs Player", "Player vs CPU", "CPU vs CPU"};
            String selectedOpponentType = (String) JOptionPane.showInputDialog(
                    null,
                    "Select your opponent:",
                    "Opponent Selection",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opponentTypes,
                    opponentTypes[0]
            );

            if (selectedOpponentType == null) {
                // User closed the dialog or canceled, exit the program
                System.exit(0);
            }

            JFrame frame = new JFrame("SOS Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // Add a variable to track the side chosen by the first player
            Color chosenSide = null;

            if ("Player vs CPU".equals(selectedOpponentType)) {
                // Ask the first player to choose a side
                String[] sideOptions = {"Red", "Blue"};
                String chosenSideString = (String) JOptionPane.showInputDialog(
                        null,
                        "Player 1, choose your side:",
                        "Side Selection",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        sideOptions,
                        sideOptions[0]
                );

                if (chosenSideString == null) {
                    // User closed the dialog or canceled, exit the program
                    System.exit(0);
                }

                // Assign the chosen side to the first player
                chosenSide = "Red".equals(chosenSideString) ? Color.RED : Color.BLUE;
            }

            // Ask the user to choose the game mode
            String[] gameModes = {"Simple Mode", "General Mode"};
            int modeChoice = JOptionPane.showOptionDialog(
                    frame,
                    "Select a game mode:",
                    "Game Mode Selection",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    gameModes,
                    gameModes[0]
            );

            int boardSize = promptForBoardSize();

            if (modeChoice == 0) {
                gameBoard = new BoardSimple(boardSize, selectedOpponentType); // Simple Mode
            } else {
                gameBoard = new BoardGeneral(boardSize, selectedOpponentType); // General Mode
            }

            frame.add(gameBoard, BorderLayout.CENTER);

            frame.add(initializeControlPanel(), BorderLayout.NORTH);
            initializeMenu(frame);

            // If "Player vs CPU" is selected, set the chosen side for the first player
            if ("Player vs CPU".equals(selectedOpponentType)) {
                Board.setCurrentColor(chosenSide);
            }

            frame.pack();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);

            // Play the game if it's CPU vs CPU
            if ("CPU vs CPU".equals(selectedOpponentType)) {
                playCPUGame();
            }
        });
    }

    public static void playCPUGame() {
        ComputerPlayer player1 = new ComputerPlayer(Color.RED, gameBoard);
        ComputerPlayer player2 = new ComputerPlayer(Color.BLUE, gameBoard);

        Thread cpuVsCpuThread = new Thread(() -> {
            while (!Board.isBoardFull()) {
                player1.makeMove();
                gameBoard.repaint(); // Update the display

                if (Board.isBoardFull()) {
                    break;
                }

                // Wait for a short time to visualize the moves
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                player2.makeMove();
                gameBoard.repaint(); // Update the display
            }
        });

        cpuVsCpuThread.start();
    }

    static int promptForBoardSize() {
        String result = JOptionPane.showInputDialog(null, "Enter board size (e.g., 8 for 8x8):", "New Game", JOptionPane.PLAIN_MESSAGE);
        int boardSize = 8; // default size
        try {
            boardSize = Integer.parseInt(result);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Using default size of 8x8.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        return boardSize;
    }

    static JPanel initializeControlPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));

        // For color choice
        JPanel colorPanel = new JPanel();
        JRadioButton redButton = new JRadioButton("Red");
        JRadioButton blueButton = new JRadioButton("Blue");
        ButtonGroup colorGroup = new ButtonGroup();
        colorGroup.add(redButton);
        colorGroup.add(blueButton);
        redButton.setSelected(true);
        redButton.addActionListener(e -> Board.setCurrentColor(Color.RED));
        blueButton.addActionListener(e -> Board.setCurrentColor(Color.BLUE));
        colorPanel.add(redButton);
        colorPanel.add(blueButton);

        // For letter choice
        JPanel letterPanel = new JPanel();
        JRadioButton sButton = new JRadioButton("S");
        JRadioButton oButton = new JRadioButton("O");
        ButtonGroup letterGroup = new ButtonGroup();
        letterGroup.add(sButton);
        letterGroup.add(oButton);
        sButton.setSelected(true); // By default, "S" will be selected
        sButton.addActionListener(e -> Board.setCurrentLetter("S"));
        oButton.addActionListener(e -> Board.setCurrentLetter("O"));
        letterPanel.add(sButton);
        letterPanel.add(oButton);

        panel.add(colorPanel);
        panel.add(letterPanel);
        return panel;
    }

    static void initializeMenu(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Option");
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(e -> {
            int boardSize = promptForBoardSize();
            boolean isSimpleMode = (gameBoard instanceof BoardSimple); // Check if the current board is an instance of BoardSimple

            // Create a new game board with the selected size and the same game mode
            Board newGameBoard = isSimpleMode ? new BoardSimple(boardSize, null) : new BoardGeneral(boardSize, null);

            frame.getContentPane().remove(gameBoard);
            gameBoard = newGameBoard;
            frame.add(gameBoard, BorderLayout.CENTER);

            frame.revalidate();
            frame.repaint();

            frame.pack();
            frame.setLocationRelativeTo(null);
        });

        gameMenu.add(newGame);
        menuBar.add(gameMenu);
        frame.setJMenuBar(menuBar);
    }
}
