package sudoku;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
      Container cp = getContentPane(); 
      cp.setLayout(new BorderLayout()); 

      /* user Panel */ 
      JPanel userPanel = new JPanel();
      userPanel.setBackground(Color.decode("#D4D4D4"));
      userPanel.setLayout(new GridLayout(2, 4, 50, 20)); 

      JButton newGameBtn = new JButton("New Game"); 
      JButton button2 = new JButton("Button 2"); 

      JButton button3 = new JButton("Button 3 "); 

      newGameBtn.setSize(20, 50); 
      button2.setSize(20, 50);
      button3.setSize(20, 50);

      userPanel.add(newGameBtn);
      userPanel.add(button2);
      userPanel.add(button3);

      cp.add(board, BorderLayout.CENTER); 
      cp.add(userPanel, BorderLayout.NORTH);
      // Add a button to the south to re-start the game via board.newGame()
      // ......

      // Initialize the game board to start the game
      board.newGame(); 

      pack();     // Pack the UI components, instead of using setSize()
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
      setTitle("Sudoku");
      setVisible(true); 

      newGameBtn.addActionListener(new ActionListener() 
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            board.newGame();
         }
      });
      newGameBtn.addChangeListener(new ButtonHoverListener());
   } 

   // private class ButtonHoverListener implements ChangeListener {
   //    @Override
   //    public void stateChanged(ChangeEvent e)
   //    {
   //       JButton source = (JButton)e.getSource();
   //       ButtonModel model = source.getModel();

   //       model.isRollover()
   //    }
   // }

   private class ButtonHoverListener implements ChangeListener {

      @Override
      public void stateChanged(ChangeEvent e) {
         JButton source = (JButton) e.getSource();
         ButtonModel model = source.getModel();
      }
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
