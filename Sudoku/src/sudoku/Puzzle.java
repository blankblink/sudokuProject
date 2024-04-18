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
      numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
      isShown = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
      temp = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
      generateNumbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
      generateIsShown = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    } 

    private static void generatePuzzle (int numToGuess)
    {
      int count = 0; 

      ArrayList<Integer> randomnumber = getRandomNum();

      for (int a = 0; a < 9; a++) {
         for (int b = 0; b < 9; b++) {
            generateNumbers[a][b] = 0;
            if (((b + 2) % 2) == 0 && ((a + 2) % 2) == 0) {
               generateNumbers[a][b] = randomnumber.get(count);
               count++;
               if (count == 9) {
                  count = 0;
               }
            }
         }
      }

      if (search(generateNumbers)) {
         System.out.println("generate");
      }

      //Generating sudoku display for player to see
      int rand = ran.nextInt(numToGuess);
      int c = 0;
      for (int a = 0; a < 9; a++) {
         for (int b = 0; b < 9; b++) {
            temp[a][b] = 0;
            if (c < rand) {
               c++;
               continue;
            } 
            
            else {
               rand = ran.nextInt(numToGuess);
               c = 0; 
               temp[a][b] = generateNumbers[a][b];
            }
         }
      } 

      //generatedNumbers
      for (int a = 0; a < 9; a++) {
         for (int b = 0; b < 9; b++) {
            System.out.print(generateNumbers[a][b]);
         }
         System.out.println();
      }
      System.out.println("---------");

      for (int a = 0; a < 9; a++) {
         for (int b = 0; b < 9; b++) {
            System.out.print(temp[a][b]);
         }
         System.out.println();
      }
    } 

   private static int[][] getEmptyCellList(int[][] grid) 
   {

      int numberOfEmptyCells = 0;
      for (int a = 0; a < 9; a++) {
         for (int b = 0; b < 9; b++) {
            if (grid[a][b] == 0) {
               numberOfEmptyCells++;
            }
         }
      }

      int[][] emptyCellList = new int[numberOfEmptyCells][2];
      int count = 0;
      for (int a = 0; a < 9; a++) {
         for (int b = 0; b < 9; b++) {
            if (grid[a][b] == 0) 
            {
               emptyCellList[count][0] = a;
               emptyCellList[count][1] = b;
               count++;
            }
         }
      }

      return emptyCellList;
   }

    private static boolean search(int[][] grid) 
    {
      int[][] emptyCellList = getEmptyCellList(grid);
      int k = 0;
      boolean found = false;

      while (!found) {
         // get free element one by one
         int a = emptyCellList[k][0];
         int b = emptyCellList[k][1];

         // if element equal 0 give 1 to first test
         if (grid[a][b] == 0) {
            grid[a][b] = 1;
         }

         // now check 1 if is available
         if (isAvailable(a, b, grid)) {
            // if free is equal k ==> board solved
            if (k + 1 == emptyCellList.length) {
               found = true;
            } else {
               k++;
            }
         }
         // Increase element by 1
         else if (grid[a][b] < 9) {
            grid[a][b] = grid[a][b] + 1;
         }
         
         // now if value equals to 9 backtrack to later element
         else {
            while (grid[a][b] == 9) {
               grid[a][b] = 0;
               if (k == 0) {
                  return false;
               }
               k--; // backtrack to later element
               a = emptyCellList[k][0];
               b = emptyCellList[k][1];
            }
            grid[a][b] = grid[a][b] + 1;
         }
      }

      return true;
   } 

   private static boolean isAvailable(int i, int j, int[][] grid) {

      // Check row
      for (int column = 0; column < 9; column++) {
         if (column != j && grid[i][column] == grid[i][j]) {
            return false;
         }
      }

      // Check column
      for (int row = 0; row < 9; row++) {
         if (row != i && grid[row][j] == grid[i][j]) {
            return false;
         }
      }

      // Check box
      for (int row = (i / 3) * 3; row < (i / 3) * 3 + 3; row++) {// i=5 ,j=2 || row =3 col=0 ||i=3 j=0
         for (int col = (j / 3) * 3; col < (j / 3) * 3 + 3; col++) {
            if (row != i && col != j && grid[row][col] == grid[i][j]) {
               return false;
            }
         }
      }

      return true; // else return true
   } 

   private static void convertIntToBoolean() {
      for (int row = 0; row < 9; row++) {
         for (int col = 0; col < 9; col++) {
            int tempNo = temp[row][col];
            if (tempNo == 0)
               generateIsShown[row][col] = false;
            else
               generateIsShown[row][col] = true;
         }
      }
   } 

 
    // Generate a new puzzle given the number of cells to be guessed, which can be used
    //  to control the difficulty level.
    // This method shall set (or update) the arrays numbers and isGiven
    public void newPuzzle(int numToGuess) {
      
      // // I hardcode a puzzle here for illustration and testing.
      //  int[][] hardcodedNumbers =
      //     {{5, 3, 4, 6, 7, 8, 9, 1, 2},
      //      {6, 7, 2, 1, 9, 5, 3, 4, 8},
      //      {1, 9, 8, 3, 4, 2, 5, 6, 7},
      //      {8, 5, 9, 7, 6, 1, 4, 2, 3},
      //      {4, 2, 6, 8, 5, 3, 7, 9, 1},
      //      {7, 1, 3, 9, 2, 4, 8, 5, 6},
      //      {9, 6, 1, 5, 3, 7, 2, 8, 4},
      //      {2, 8, 7, 4, 1, 9, 6, 3, 5},
      //      {3, 4, 5, 2, 8, 6, 1, 7, 9}}; 

      generatePuzzle(numToGuess);
      convertIntToBoolean(); 
 
       // Copy from hardcodedNumbers into the array "numbers" 
       // Copy from hardcoded number
      for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
         for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
            numbers[row][col] = generateNumbers[row][col];
         }
      }
 
       // Need to use input parameter cellsToGuess!
       boolean[][] hardcodedIsGiven =
          {{true, false,  true, true, true,  true, true, true, true},
           {true, true, true,  true, true, true, true, true,  true},
           {true, true, true, true, true, true, true, true, true},
           {true, true, true, true, true, false, true, true, true},
           {true, true, true, true, true, true, true, true, false},
           {true, false, true, true, true, true, true, false, true},
           {true, true, true, true, true, true, true, true, true},
           {true, true, false, true, true, true, true, true, true},
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
 