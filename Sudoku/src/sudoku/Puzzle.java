package sudoku;

import java.util.Random;

import java.util.ArrayList;
import java.util.Collections;

public class Puzzle {
    // All variables have package access
    // The numbers on the puzzle
    int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE]; 
    
    // The clues - isGiven (no need to guess) or need to guess
    boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    static int[][] temp; 
    static int[][] generateNumbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    static boolean[][] generateIsShown; 
    boolean[][] isShown; 

    private static Random ran = new Random(); 

    // Constructor
    public Puzzle() {
       super();
    }
 
    // Generate a new puzzle given the number of cells to be guessed, which can be used
    //  to control the difficulty level.
    // This method shall set (or update) the arrays numbers and isGiven
    public void newPuzzle(int cellsToGuess) {
       // I hardcode a puzzle here for illustration and testing.
       int[][] hardcodedNumbers =
          {{5, 3, 4, 6, 7, 8, 9, 1, 2},
           {6, 7, 2, 1, 9, 5, 3, 4, 8},
           {1, 9, 8, 3, 4, 2, 5, 6, 7},
           {8, 5, 9, 7, 6, 1, 4, 2, 3},
           {4, 2, 6, 8, 5, 3, 7, 9, 1},
           {7, 1, 3, 9, 2, 4, 8, 5, 6},
           {9, 6, 1, 5, 3, 7, 2, 8, 4},
           {2, 8, 7, 4, 1, 9, 6, 3, 5},
           {3, 4, 5, 2, 8, 6, 1, 7, 9}};
           

         /*int [][] randomNumbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE]; 
         Random rand = new Random(); 
         rand_int1 = rand.nextInt(1, 9);

         for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row)
         {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
               randomNumbers[row][col] = rand_int1;
            }
         }*/
 
       // Copy from hardcodedNumbers into the array "numbers"
       for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
          for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
             numbers[row][col] = hardcodedNumbers[row][col];
          }
       }
 
       // Need to use input parameter cellsToGuess!
       // Hardcoded for testing, only 5 cells of "8" is NOT GIVEN
       boolean[][] hardcodedIsGiven =
          {{true, false,  true, true, true,  true, true, true, true},
           {true, true, true,  true, true, true, true, true,  true},
           {true, true, true, true, true, true, true, true, true},
           {true, true, true, true, true, false, true, true, true},
           {true, true, true, true, true, true, true, true, false},
           {true, false, true, true, true, true, true, false, true},
           {true, true, true, true, true, true, true, true, true},
           {true, true, false, true, true, true, true, true, true},
           {true, true, false, true, true, true, true, true, true},
           {true, true, true, true, true, true, true, true, true},
           {true, true, true, true, true, true, true, true, true},
           {true, true, true, true, true, true, true, true, true},
           {true, true, true, true, true, true, true, true, true}};
 
       // Copy from hardcodedIsGiven into array "isGiven"
       for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
          for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
             isGiven[row][col] = hardcodedIsGiven[row][col];
          }
       }
       
    } 

   private static ArrayList<Integer> getRandomNum() {
      ArrayList<Integer> numbers = new ArrayList<Integer>();
      for (Integer i = 1; i < 10; i++)
      {
         numbers.add(i);
      }

      Collections.shuffle(numbers);
      return numbers;
    }
 
    //(For advanced students) use singleton design pattern for this class
}
 