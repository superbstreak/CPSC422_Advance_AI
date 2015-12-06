package model;

public class UArc {

  private Node[] nodes;
  private int id;
  private double[] probs;

  public UArc(Node n1, Node n2, int id, double p00, double p01, double p10, double p11) {
    this.nodes = new Node[2];
    this.nodes[0] = n1;
    this.nodes[1] = n2;
    this.id = id;
    this.probs = new double[4];
    this.probs[0] = p00; // n1 = X, n2 = X
    this.probs[1] = p01; // n1 = X, n2 = V
    this.probs[2] = p10; // n1 = V, n2 = X
    this.probs[3] = p11; // n1 = V, n2 = V
  }

  public Node[] getNodes() {
    return nodes;
  }

  public void setNodes(Node[] nodes) {
    this.nodes = nodes;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public double[] getProbs() {
    return probs;
  }

  public void setProbs(double[] probs) {
    this.probs = probs;
  }

  public double[] getProbFromStateN1(boolean node1State) {
    double[] result = { 0, 0 };
    if (node1State == true) {
      result[0] = probs[3]; // n1 = V, n2 = V
      result[1] = probs[2]; // n1 = V, n2 = X
    } else {
      result[0] = probs[1]; // n1 = X, n2 = V
      result[1] = probs[0]; // n1 = X, n2 = X
    }
    return result;
  }

  public double[] getProbFromStateN2(boolean node2State) {
    double[] result = { 0, 0 };
    if (node2State == true) {
      result[0] = probs[3]; // n1 = V, n2 = V
      result[1] = probs[1]; // n1 = X, n2 = V
    } else {
      result[0] = probs[2]; // n1 = V, n2 = X
      result[1] = probs[0]; // n1 = X, n2 = X
    }
    return result;
  }

}
