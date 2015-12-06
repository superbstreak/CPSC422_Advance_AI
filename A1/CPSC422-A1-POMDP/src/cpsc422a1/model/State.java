package cpsc422a1.model;

import java.text.DecimalFormat;

import cpsc422a1.core.Config;

public class State {

  private int x;
  private int y;
  private boolean isWall;
  private float value;
  private boolean isTerminal;

  public State(int x, int y, boolean isWall, float value, boolean terminal) {
    this.x = x;
    this.y = y;
    this.isWall = isWall;
    this.value = value;
    this.isTerminal = terminal;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public boolean isWall() {
    return isWall;
  }

  public void setWall(boolean isWall) {
    this.isWall = isWall;
  }

  public float getValue() {
    return value;
  }

  public void setValue(float val) {
    this.value = val;
  }

  public boolean isTerminal() {
    return isTerminal;
  }

  public void setTerminal(boolean isTerminal) {
    this.isTerminal = isTerminal;
  }

  public String GetPrintableValue() {
    DecimalFormat df = new DecimalFormat("0.00000");
    String value = df.format(this.value);
    return value.substring(0, Config.MAX_VAL_PRINT_LEN);
  }

}
