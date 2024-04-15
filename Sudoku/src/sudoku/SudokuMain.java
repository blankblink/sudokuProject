package sudoku;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {
   private static final long serialVersionUID = 1L;  // to prevent serial warning

   public static final int WINDOW_WIDTH = 700;
   public static final int WINDOW_HEIGHT = 150;

   // private variables
   GameBoardPanel board = new GameBoardPanel(); 
   
   JButton resetBtn = new JButton("Reset"); 

   // Constructor
   public SudokuMain() {
   
      Container pewpew = this.getContentPane();
      Container cp = this.getContentPane();
      cp.setLayout(new BorderLayout());

      cp.add(board, BorderLayout.CENTER); 
      cp.add(resetBtn, BorderLayout.SOUTH);


      // Add a button to the south to re-start the game via board.newGame()
      // ......

      // Initialize the game board to start the game
      board.newGame();

      pack();     // Pack the UI components, instead of using setSize()
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
      setTitle("Sudoku");
      setVisible(true); 
   } 

   private static void createAndShowGUI() {
    JFrame frame = new JFrame("Sudoku");
    frame.setContentPane(new SudokuMain());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    frame.setVisible(true);
   }

   /** The entry main() entry method */
   public static void main(String[] args) {
      // [TODO 1] Check "Swing program template" on how to run
      //  the constructor of "SudokuMain"
      // ......... 

      // Run GUI codes in the Event-Dispatching thread for thread safety
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            createAndShowGUI();  // Let the constructor do the job
         }
     });
   }
}
