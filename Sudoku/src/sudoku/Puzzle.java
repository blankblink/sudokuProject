package sudoku;

import java.util.Random;

import java.util.ArrayList;
import java.util.Arrays;
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

    Integer exclude[] = {0}; 
    Integer Vexclude[]= {0}; 

    ArrayList<Integer> excludeList = new ArrayList<Integer>(Arrays.asList(exclude)); 
    ArrayList<Integer> VexcludeList = new ArrayList<Integer>(Arrays.asList(Vexclude));

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

      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
            generateNumbers[i][j] = 0;
            if (((j + 2) % 2) == 0 && ((i + 2) % 2) == 0) {
               generateNumbers[i][j] = randomnumber.get(count);
               count++;
               if (count == 9) {
                  count = 0;
               }
            }
         }
      }

      if (search(generateNumbers)) {
         System.out.println("OK !!");
      }
      int rann = ran.nextInt(numToGuess);
      int c = 0;
      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
            temp[i][j] = 0;
            if (c < rann) {
               c++;
               continue;
            } else {
               rann = ran.nextInt(numToGuess);
               c = 0;
               temp[i][j] = generateNumbers[i][j];
            }
         }
      }

      /*------------------------ View Generated Sudoku -------------------------------------*/
      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
            System.out.print(generateNumbers[i][j]);
         }
         System.out.println();
      }
      System.out.println("---------");

      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
            System.out.print(temp[i][j]);
         }
         System.out.println();
      }
    } 

   private static int[][] getFreeCellList(int[][] grid) 
   {

      int numberOfFreeCells = 0;
      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
            if (grid[i][j] == 0) {
               numberOfFreeCells++;
            }
         }
      }

      int[][] freeCellList = new int[numberOfFreeCells][2];
      int count = 0;
      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
            if (grid[i][j] == 0) {
               freeCellList[count][0] = i;
               freeCellList[count][1] = j;
               count++;
            }
         }
      }

      return freeCellList;
   }

    private static boolean search(int[][] grid) 
    {
      int[][] freeCellList = getFreeCellList(grid);
      int k = 0;
      boolean found = false;

      while (!found) {
         // get free element one by one
         int i = freeCellList[k][0];
         int j = freeCellList[k][1];
         // if element equal 0 give 1 to first test
         if (grid[i][j] == 0) {
            grid[i][j] = 1;
         }
         // now check 1 if is available
         if (isAvailable(i, j, grid)) {
            // if free is equal k ==> board solved
            if (k + 1 == freeCellList.length) {
               found = true;
            } else {
               k++;
            }
         }
         // increase element by 1
         else if (grid[i][j] < 9) {
            grid[i][j] = grid[i][j] + 1;
         }
         // now if element value equal 9 backtrack to later element
         else {
            while (grid[i][j] == 9) {
               grid[i][j] = 0;
               if (k == 0) {
                  return false;
               }
               k--; // backtrack to later element
               i = freeCellList[k][0];
               j = freeCellList[k][1];
            }
            grid[i][j] = grid[i][j] + 1;
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


    public int getRandomNumber(int min, int max)
    { 
      int randomNum = min + (int)(Math.random() * ((max - min) + 1));
      if (excludeList.size() > 0)
      {
         while (excludeList.contains(randomNum))
         {
            randomNum = min + (int)(Math.random() * ((max - min) + 1));
            if (!excludeList.contains(randomNum))
            {
               return randomNum;
            } 
         }
         // for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) 
         // {
         //    randomNum = min + (int)(Math.random() * ((max - min) + 1));
         //    if (!excludeList.contains(randomNum))
         //    {
         //       return randomNum;
         //    } 
         // }
      }

      return randomNum;
   } 

    public int bGetRandom(int min, int max, int nc)
    {
      if (nc == 0)
      {
         excludeList.clear();
      }

      int bRandomNum = min + (int)(Math.random() * ((max - min) + 1));
      System.out.println("BTRY check" + bRandomNum);

      if (VexcludeList.size() > 0)
      {
         System.out.println("debug " + excludeList.size());
         if (nc > 0)
         {
            System.out.println(VexcludeList + " " + excludeList);
            while ((VexcludeList.contains(bRandomNum)) && (excludeList.contains(bRandomNum)))
            {
               bRandomNum = min + (int)(Math.random() * ((max - min) + 1));
               if ((!VexcludeList.contains(bRandomNum)) && (!excludeList.contains(bRandomNum)))
               {
                  System.out.println("B CHECKA: " + bRandomNum);
                  return bRandomNum;
               } 
            }
         }

   
         while ((VexcludeList.contains(bRandomNum)))
         {
            bRandomNum = min + (int)(Math.random() * ((max - min) + 1));
            if ((!VexcludeList.contains(bRandomNum)))
            {
               System.out.println("B CHECK: " + bRandomNum);
               return bRandomNum;
            } 
         }
      }

      System.out.println("B CHECK2: " + bRandomNum);
      return bRandomNum;
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
 