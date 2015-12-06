package model;

public class Node {
  private String name;
  private int id;
  private boolean state;

  public Node(String name, int id, boolean s) {
    this.name = name;
    this.id = id;
    this.state = s;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Boolean getState() {
    return state;
  }

  public void setState(boolean s) {
    this.state = s;
  }

}
