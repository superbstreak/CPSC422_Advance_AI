package cpsc422a1.core;

public class Config {
  // Board
  public final static int ySize = 3;
  public final static int xSize = 4;
  public final static int[] xWall = { 1 };
  public final static int[] yWall = { 1 };
  public final static int NUM_OF_WALL = xWall.length;
  public final static int[] xTerminalPos = { 3, 3 };
  public final static int[] yTerminalPos = { 2, 1 };
  public final static int[] rewardAmount = { 1, -1 };
  public final static int NUM_OF_TERMINAL = xTerminalPos.length;

  // System
  public final static int MAX_VAL_PRINT_LEN = 6;

  // ACtions
  public static final int ACTION_UP = 1;
  public static final int ACTION_DOWN = 2;
  public static final int ACTION_LEFT = 3;
  public static final int ACTION_RIGHT = 4;
  public static final String[] ACTION_NAME = { "up", "down", "left", "right" };

  // Observation
  public static final int OBSERVATION_1WALL = 1;
  public static final int OBSERVATION_2WALL = 2;
  public static final int OBSERVATION_END = 3;
  public static final String[] OBSERVATION_NAME = { "1-wall", "2-wall", "end" };
}
