package cpsc422a1.model;

import cpsc422a1.core.Config;
import cpsc422a1.utils.Utils;

public class Space {

  private int sequenceNumber;
  private State[][] board;

  public Space() {
    defaultInitialBeliefState();
  }

  public State[][] getBoard() {
    return board;
  }

  public void setBoard(State[][] board) {
    this.board = board;
  }

  public void setSequenceNumber(int seqNum) {
    this.sequenceNumber = seqNum;
  }

  public int getSequenceNumber() {
    return this.sequenceNumber;
  }

  public void defaultInitialBeliefState() {
    board = new State[Config.xSize][Config.ySize];
    float initialValue = ((float) 1 / (float) (Config.xSize * Config.ySize - Config.NUM_OF_WALL - Config.NUM_OF_TERMINAL));
    for (int x = 0; x < Config.xSize; x++) {
      for (int y = 0; y < Config.ySize; y++) {
        board[x][y] = new State(x, y, isWall(x, y), initialValue, false);
      }
    }
    board[3][1].setValue(0);
    board[3][1].setTerminal(true);
    board[3][2].setValue(0);
    board[3][2].setTerminal(true);
    board[1][1].setValue(0);

    Utils.printBoardMapping(board, "Status: Initial Belief State/Default");
  }

  public void customInitialBeliefState(int knownX, int knownY) {
    board = new State[Config.xSize][Config.ySize];
    for (int x = 0; x < Config.xSize; x++) {
      for (int y = 0; y < Config.ySize; y++) {
        board[x][y] = new State(x, y, isWall(x, y), 0, false);
      }
    }

    board[3][1].setTerminal(true);
    board[3][2].setTerminal(true);

    board[knownX][knownY].setValue(1);
    Utils.printBoardMapping(board, "Status: Initial Belief State/Custom");
  }

  public State getState(int x, int y) {
    return board[x][y];
  }

  public State getAdjacentState(State currentState, String direction) {

    int currentX = currentState.getX();
    int currentY = currentState.getY();
    State adjacentState = currentState;

    if (direction.equalsIgnoreCase("above")) {
      if (currentY < Config.ySize - 1) {
        adjacentState = getState(currentX, currentY + 1);
      }
    } else if (direction.equalsIgnoreCase("below")) {
      if (currentY != 0) {
        adjacentState = getState(currentX, currentY - 1);
      }
    } else if (direction.equalsIgnoreCase("left")) {
      if (currentX != 0) {
        adjacentState = getState(currentX - 1, currentY);
      }
    } else if (direction.equalsIgnoreCase("right")) {
      if (currentX < Config.xSize - 1) {
        adjacentState = getState(currentX + 1, currentY);
      }
    }

    // if hit the non boarder wall
    if (adjacentState.getX() == Config.xWall[0] && adjacentState.getY() == Config.yWall[0]) {
      adjacentState = currentState;
    }
    return adjacentState;
  }

  private boolean isWall(int x, int y) {
    for (int i = 0; i < Config.xWall.length; i++) {
      if ((Config.xWall[i] == x && Config.yWall[i] == y)) {
        return true;
      }
    }
    return false;
  }

}
