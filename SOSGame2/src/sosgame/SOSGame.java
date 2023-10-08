package sosgame;

import javax.swing.*;

import sosgame.Board;

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

	            int boardSize = promptForBoardSize();
	            
	            // This line instantiates the gameBoard for the first time
	            gameBoard = new Board(boardSize); 
	            frame.add(gameBoard, BorderLayout.CENTER);

	            frame.add(initializeControlPanel(), BorderLayout.NORTH);

	            initializeMenu(frame);

	            frame.pack();
	            frame.setVisible(true);
	            frame.setLocationRelativeTo(null);
	        }
            
            
            
           
            
            
            

        });
    }
	
	public static JPanel initializeControlPanel() {
        JPanel panel = new JPanel(new GridLayout(2,1));

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
        sButton.setSelected(true);  // By default, "S" will be selected
        sButton.addActionListener(e -> Board.setCurrentLetter("S"));
        oButton.addActionListener(e -> Board.setCurrentLetter("O"));
        letterPanel.add(sButton);
        letterPanel.add(oButton);

        panel.add(colorPanel);
        panel.add(letterPanel);
        return panel;
    }
	//
	public static void initializeMenu(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Menu");
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(e -> {
            int boardSize = promptForBoardSize();
            

            // Create a new game board with the selected size
            Board newGameBoard = new Board(boardSize);

            // Remove the old game board from the frame
            frame.getContentPane().remove(gameBoard);
            gameBoard = newGameBoard; // Update the reference to the current game board
            frame.add(gameBoard, BorderLayout.CENTER);

            // Refresh the frame contents
            frame.revalidate();
            frame.repaint();

            frame.pack();
            frame.setLocationRelativeTo(null);
        });

        gameMenu.add(newGame);
        menuBar.add(gameMenu);
        frame.setJMenuBar(menuBar);
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
     
	//
	
}
