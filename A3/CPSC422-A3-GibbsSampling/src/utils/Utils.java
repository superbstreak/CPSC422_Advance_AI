package utils;

import java.io.FileWriter;
import java.util.ArrayList;

public class Utils {

  public static void printCommandFeedBack(String command) {
    System.out.println("");
    System.out.println(command);
    System.out.println("");
  }

  public static void printAll() {
    System.out.println();
    System.out.println("             ---");
    System.out.println("            | A |");
    System.out.println("             ---");
    System.out.println("            /   \\");
    System.out.println("       4   /     \\   1");
    System.out.println("          /       \\");
    System.out.println("       ---         ---");
    System.out.println("      | D |       | B |  <- known B = 1");
    System.out.println("       ---         ---");
    System.out.println("           \\     /");
    System.out.println("       3    \\   /    2");
    System.out.println("             ---");
    System.out.println("            | C |");
    System.out.println("             ---");
    System.out.println();
  }

  public static void printSamplingStepGraph(int s) {
    switch (s) {
      case 1: // Sample A
        System.out.println();
        System.out.println("Sampling A");
        System.out.println("             ---");
        System.out.println("            | A |");
        System.out.println("             ---");
        System.out.println("            /   \\");
        System.out.println("           /     \\");
        System.out.println("          /       \\");
        System.out.println("       ---         ---");
        System.out.println("      | D |       | B | <- known B = 1");
        System.out.println("       ---         ---");
        System.out.println();
        break;
      case 2: // Sample B
        System.out.println();
        System.out.println("Sampling B: Skipped. B is already known.");
        System.out.println("             ---");
        System.out.println("            | A |");
        System.out.println("             ---");
        System.out.println("                \\");
        System.out.println("                 \\");
        System.out.println("                  \\");
        System.out.println("                   ---");
        System.out.println("                  | B | <- known B = 1");
        System.out.println("                   ---");
        System.out.println("                  /");
        System.out.println("                 /");
        System.out.println("             ---");
        System.out.println("            | C |");
        System.out.println("             ---");
        System.out.println();
        break;
      case 3: // Sample C
        System.out.println();
        System.out.println("Sampling C");
        System.out.println("       ---         ---");
        System.out.println("      | D |       | B | <- known B = 1");
        System.out.println("       ---         ---");
        System.out.println("           \\     /");
        System.out.println("            \\   /");
        System.out.println("             ---");
        System.out.println("            | C |");
        System.out.println("             ---");
        System.out.println();
        break;
      case 4: // Sample D
        System.out.println();
        System.out.println("Sampling D");
        System.out.println("             ---");
        System.out.println("            | A |");
        System.out.println("             ---");
        System.out.println("            /");
        System.out.println("           /");
        System.out.println("          /");
        System.out.println("       --- ");
        System.out.println("      | D |");
        System.out.println("       --- ");
        System.out.println("           \\");
        System.out.println("            \\");
        System.out.println("             ---");
        System.out.println("            | C |");
        System.out.println("             ---");
        System.out.println();
        break;
    }
  }

  public static void printSamplingStepDetail(int s, double[] p, double sample, boolean r) {
    System.out.println();
    String node = "A";
    switch (s) {
      case 1:
        node = "A";
        break;
      case 2:
        node = "B";
        break;
      case 3:
        node = "C";
        break;
      case 4:
        node = "D";
        break;
    }
    System.out.println(node + " = 1: " + p[0]);
    System.out.println(node + " = 0: " + p[1]);
    System.out.println("Sample: " + sample);
    System.out.println("Resul: " + r);
  }

  public static void writeToDisk(ArrayList<Float> p) throws Exception {
    String path = Utils.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("cpsc422a3q1_42764118.jar", "");
    FileWriter writer = new FileWriter(path + "/CS422A3Q1Gibbs.txt", true);
    writer.write("P(A|B = 1)");
    int size = p.size();
    for (int i = 0; i < size; i++) {
      writer.write("\r\n"); // write new line
      writer.write("" + p.get(i));
    }
    writer.close();
    System.out.println("Writing data to disk. File name: CS422A3Q1Gibbs.txt");
  }
}
