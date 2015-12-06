package cpsc422a1.utils;

import cpsc422a1.core.Config;
import cpsc422a1.model.State;

public class Utils {

  public static void printEmptyBoardMapping() {
    System.out.println("            CPSC 422 A1 POMDP  ");
    System.out.println("    |-----------------------------------| ");
    System.out.println("    |        |        |        |        |");
    System.out.println("  2 |        |        |        |  +1    |");
    System.out.println("    |        |        |        |        |");
    System.out.println("    |-----------------------------------|");
    System.out.println("    |        |        |        |        |");
    System.out.println("  1 |        |  WALL  |        |  -1    |");
    System.out.println("    |        |        |        |        |");
    System.out.println("    |-----------------------------------|");
    System.out.println("    |        |        |        |        |");
    System.out.println("  0 |        |        |        |        |");
    System.out.println("    |        |        |        |        |");
    System.out.println("    |-----------------------------------|");
    System.out.println("         0        1        2        3");
  }

  public static void printBoardMapping(State[][] updatedBoard, String status) {
    System.out.println("");
    System.out.println("    |-----------------------------------| ");
    System.out.println("    |        |        |        |        |");
    System.out.println("  2 | " + updatedBoard[0][2].GetPrintableValue() + " | " + updatedBoard[1][2].GetPrintableValue() + " | " + updatedBoard[2][2].GetPrintableValue() + " | "
        + updatedBoard[3][2].GetPrintableValue() + " |");
    System.out.println("    |        |        |        |        |");
    System.out.println("    |-----------------------------------|");
    System.out.println("    |        |        |        |        |");
    System.out.println("  1 | " + updatedBoard[0][1].GetPrintableValue() + " | WALL 0 | " + updatedBoard[2][1].GetPrintableValue() + " | " + updatedBoard[3][1].GetPrintableValue() + " |");
    System.out.println("    |        |        |        |        |");
    System.out.println("    |-----------------------------------|");
    System.out.println("    |        |        |        |        |");
    System.out.println("  0 | " + updatedBoard[0][0].GetPrintableValue() + " | " + updatedBoard[1][0].GetPrintableValue() + " | " + updatedBoard[2][0].GetPrintableValue() + " | "
        + updatedBoard[3][0].GetPrintableValue() + " |");
    System.out.println("    |        |        |        |        |");
    System.out.println("    |-----------------------------------|");
    System.out.println("         0        1        2        3");
    System.out.println(status);
    System.out.println("");
  }

  public static void printCommandFeedBack(String command) {
    System.out.println("");
    System.out.println(command);
    System.out.println("");
  }

  public static void printDetailMapping(State[][] board) {

    for (int y = Config.ySize - 1; y >= 0; y--) {
      for (int x = 0; x < Config.xSize; x++) {
        State s = board[x][y];
        if (x == 3) {
          System.out.println("(" + s.getX() + ", " + s.getY() + ", BS: " + s.getValue() + ")  ");
        } else {
          System.out.print("(" + s.getX() + ", " + s.getY() + ", BS: " + s.getValue() + ")  ");
        }
      }
    }
  }
}
