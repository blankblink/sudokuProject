package sudoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
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
      userPanel.setBackground(Color.decode("#F5DEB3"));
      userPanel.setLayout(new FlowLayout()); 

      JButton muteBtn = new JButton("mute"); 
      muteBtn.setSize(20, 50);
      userPanel.add(muteBtn); 

      JButton unmuteBtn = new JButton("unmute"); 
      unmuteBtn.setSize(20, 50);
      userPanel.add(unmuteBtn);

      JButton newGameBtn = new JButton("New Game"); 

      JRadioButton lightModeBtn = new JRadioButton("Light Mode");
      JRadioButton darkModeBtn = new JRadioButton("Dark Mode"); 

      lightModeBtn.setSelected(true);
      lightModeBtn.setEnabled(false);

      newGameBtn.setSize(20, 50); 

      userPanel.add(newGameBtn); 
      userPanel.add(lightModeBtn);
      userPanel.add(darkModeBtn);

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
      //newGameBtn.addChangeListener(new ButtonHoverListener());

      unmuteBtn.addActionListener(new ActionListener() 
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            SoundEffect.BGM.unmute();
            SoundEffect.BGM.stillplay();
         }
      }); 

      muteBtn.addActionListener(new ActionListener() 
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            SoundEffect.BGM.stop();
            SoundEffect.CORRECT.mute();  
         }
      }); 

      lightModeBtn.addActionListener(new ActionListener() {
         @Override 
         public void actionPerformed(ActionEvent e)
         {
            if (lightModeBtn.isSelected())
            {
               darkModeBtn.setSelected(false);
               darkModeBtn.setEnabled(true);
               lightModeBtn.setEnabled(false);

               for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
                  for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                     board.cells[row][col].paint();
                  }
               }
            }
         }
      });

      darkModeBtn.addActionListener(new ActionListener() {
         @Override 
         public void actionPerformed(ActionEvent e)
         {
            if (darkModeBtn.isSelected())
            {
               lightModeBtn.setSelected(false);
               lightModeBtn.setEnabled(true); 
               darkModeBtn.setEnabled(false);

               for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
                  for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                     board.cells[row][col].Dpaint();
                  }
               }
            }
         }
      });

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

      JOptionPane.showMessageDialog(null,"Hello!", 
         "Welcome To Sudoku", JOptionPane.INFORMATION_MESSAGE);
      SoundEffect.BGM.stillplay();
      // Run GUI codes in the Event-Dispatching thread for thread safety
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            createAndShowGUI();  // Let the constructor do the job
         }
     });
   }
}