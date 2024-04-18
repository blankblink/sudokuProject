package sudoku;

import java.awt.Color;
import java.awt.Font;
import java.awt.Window.Type;

import javax.swing.JTextField;
/**
 * The Cell class model the cells of the Sudoku puzzle, by customizing (subclass)
 * the javax.swing.JTextField to include row/column, puzzle number and status.
 */
public class Cell extends JTextField {
   private static final long serialVersionUID = 1L;  // to prevent serial warning

   // Define named constants for JTextField's colors and fonts
   //  to be chosen based on CellStatus

   //LIGHT MODE
   public static final Color BG_GIVEN = new Color(240, 240, 240); // RGB
   public static final Color FG_GIVEN = Color.BLACK;
   public static final Color FG_NOT_GIVEN = Color.BLACK;
   public static final Color BG_TO_GUESS  = Color.YELLOW;
   public static final Color BG_CORRECT_GUESS = new Color(0, 216, 0);
   public static final Color BG_WRONG_GUESS   = new Color(216, 0, 0);
   public static final Font FONT_NUMBERS = new Font("A Extended", Font.PLAIN, 28);

   //DARK MODE 
   public static final Color DBG_GIVEN = new Color(0, 0, 0); // RGB 51-204-255 240
   public static final Color DFG_GIVEN = Color.decode("#FFFFFF");
   public static final Color DFG_NOT_GIVEN = Color.BLACK;
   public static final Color DBG_TO_GUESS  = Color.YELLOW;
   public static final Color DBG_CORRECT_GUESS = new Color(0, 216, 0);
   public static final Color DBG_WRONG_GUESS   = new Color(216, 0, 0);
   public static final Color DBG_COMMON_GUESS   = new Color(255, 0, 255);
   public static final Font DFONT_NUMBERS = new Font("A Extended", Font.PLAIN, 28);
   
   // Define properties (package-visible)
   /** The row and column number [0-8] of this cell */
   int row, col;
   /** The puzzle number [1-9] for this cell */
   int number;
   /** The status of this cell defined in enum CellStatus */
   CellStatus status; 
   // Cell sourceCell = (Cell)e.getSource();


   /** Constructor */
   public Cell(int row, int col) {
      super();   // JTextField
      this.row = row;
      this.col = col;
      // Inherited from JTextField: Beautify all the cells once for all
      super.setHorizontalAlignment(JTextField.CENTER);
      super.setFont(FONT_NUMBERS);
   }

   /** Reset this cell for a new game, given the puzzle number and isGiven */
   public void newGame(int number, boolean isGiven) {
      this.number = number;
      status = isGiven ? CellStatus.GIVEN : CellStatus.TO_GUESS;
      rPaint();    // paint itself
   }

   public void rPaint() {
      if (status == CellStatus.GIVEN) {
         // Inherited from JTextField: Set display properties 
         super.setText(number + "");
         super.setEditable(false); 
         super.setBackground(BG_GIVEN);
         super.setForeground(FG_GIVEN);
      } 
      
      else if (status == CellStatus.TO_GUESS) {
         super.setText("");
         super.setEditable(true);
         super.setBackground(BG_TO_GUESS);
         super.setForeground(FG_NOT_GIVEN);
      } 
      
      else if (status == CellStatus.CORRECT_GUESS) {  // from TO_GUESS
         if (!Integer.toString(number).isEmpty())
         {
            super.setEditable(false);
         }
         
         if (super.getBackground() == BG_CORRECT_GUESS)
         {
            SoundEffect.CORRECT.play();
         }
         
         super.setBackground(BG_CORRECT_GUESS);
      } 
      
      else if (status == CellStatus.WRONG_GUESS) {    // from TO_GUESS
         if (super.getBackground() == BG_TO_GUESS)
         {
            SoundEffect.WRONG.play();
         }
         super.setBackground(BG_WRONG_GUESS);
      }
   }

   /** This Cell (JTextField) paints itself based on its status */
   public void paint() {
      if (status == CellStatus.GIVEN) {
         // Inherited from JTextField: Set display properties
         super.setText(number + "");
         super.setEditable(false); 
         super.setBackground(BG_GIVEN);
         super.setForeground(FG_GIVEN);
      } 
      
      else if (status == CellStatus.TO_GUESS) {
         // Inherited from JTextField: Set display properties
         System.out.println("testTest: " + super.getText());
         if (!super.getText().isEmpty())
         {
            if (super.getText().length() > 0)
            {
               super.setEditable(false);
            }

            else
            {
               super.setText("");
            }
         }
         
         if(super.getText().length() > 0)
         {
            System.out.println("yes");
            super.setEditable(false);
         } 
         super.setEditable(true);
         super.setBackground(BG_TO_GUESS);
         super.setForeground(FG_NOT_GIVEN);
      } 
      
      else if (status == CellStatus.CORRECT_GUESS) {  // from TO_GUESS
         if (!Integer.toString(number).isEmpty())
         {
            if (super.getBackground() == BG_CORRECT_GUESS)
            {
               if (super.isEditable())
               {
                  SoundEffect.CORRECT.play();
                  super.setEditable(false);
               } 
            }

            super.setBackground(BG_CORRECT_GUESS);
         }
      } 
      
      else if (status == CellStatus.WRONG_GUESS) {    // from TO_GUESS
         if (super.getBackground() == BG_WRONG_GUESS)
         {
            SoundEffect.WRONG.play();
         }
         super.setBackground(BG_WRONG_GUESS);
      }
   } 

   public void Dpaint() {
      if (status == CellStatus.GIVEN) {
         // Inherited from JTextField: Set display properties
         super.setText(number + "");
         super.setEditable(false); 
         super.setBackground(DBG_GIVEN);
         super.setForeground(DFG_GIVEN);
      } 

      else if (status == CellStatus.TO_GUESS) {
         // Inherited from JTextField: Set display properties
         
         if(!super.getText().isEmpty())
         {
            super.setEditable(false);
         }
         // super.setText(number + "");
            
      
         super.setEditable(true);
         super.setBackground(DBG_TO_GUESS);
         super.setForeground(DFG_NOT_GIVEN);
      } 

      else if (status == CellStatus.CORRECT_GUESS) { 

         if (!Integer.toString(number).isEmpty())
         {
            if (super.getBackground() == DBG_CORRECT_GUESS)
            {
               if (super.isEditable())
               {
                  SoundEffect.CORRECT.play();
                  super.setEditable(false);
               } 
            }

            super.setBackground(DBG_CORRECT_GUESS);
         }
      } 

      else if (status == CellStatus.WRONG_GUESS) {    // from TO_GUESS
         if (super.getBackground() == DBG_TO_GUESS)
         {
            SoundEffect.WRONG.play();
         }
         super.setBackground(DBG_WRONG_GUESS);
      }
   }
}
