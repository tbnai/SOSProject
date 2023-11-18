package sosgame;

import javax.swing.*;
import java.awt.*;

public class SOSGame {
    private static Board gameBoard;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("SOS Game");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());

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
                    gameBoard = new BoardSimple(boardSize); // Simple Mode
                } else {
                    gameBoard = new BoardGeneral(boardSize); // General Mode
                }
                frame.add(gameBoard, BorderLayout.CENTER);

                frame.add(initializeControlPanel(), BorderLayout.NORTH);
                initializeMenu(frame);

                frame.pack();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            }
        });
    }

    public static int promptForBoardSize() {
        String result = JOptionPane.showInputDialog(null, "Enter board size (e.g., 8 for 8x8):", "New Game", JOptionPane.PLAIN_MESSAGE);
        int boardSize = 8; // default size
        try {
            boardSize = Integer.parseInt(result);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Using default size of 8x8.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        return boardSize;
    }
    public static JPanel initializeControlPanel() {
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

    public static void initializeMenu(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Option");
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(e -> {
            int boardSize = promptForBoardSize();
            boolean isSimpleMode = (gameBoard instanceof BoardSimple); // Check if the current board is an instance of BoardSimple

            // Create a new game board with the selected size and the same game mode
            Board newGameBoard = isSimpleMode ? new BoardSimple(boardSize) : new BoardGeneral(boardSize);

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