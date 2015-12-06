package cpsc422a1.core;

import java.io.IOException;

import cpsc422a1.processor.ActionProcessor;
import cpsc422a1.processor.InputProcessor;
import cpsc422a1.utils.Utils;

public class Main {

  private static final int MAX_LEN = 255;
  private static InputProcessor inputlistener;
  private static ActionProcessor actionProcessor;

  public static void main(String[] args) {

    System.out.println();
    System.out.println("===========================================");
    System.out.println(" CPSC 422 A1 Q3: POMDP");
    System.out.println(" Name: Rob Wu");
    System.out.println(" SID: 42764118");
    System.out.println(" CSID: y4d8");
    System.out.println("===========================================");
    System.out.println();

    // initializing board
    Utils.printEmptyBoardMapping();
    actionProcessor = new ActionProcessor();
    inputlistener = new InputProcessor(actionProcessor);

    System.out.println();
    System.out.println("Please enter 'run all' and press enter to get started.");
    System.out.println("For more information on other commands, please type 'help' or '-h'");
    System.out.println();

    // starts
    byte cmdString[] = new byte[MAX_LEN];
    try {
      for (int len = 1; len > 0;) {
        System.out.print("cpsc422a1> ");
        len = System.in.read(cmdString);
        if (len <= 0)
          break;
        try {
          inputlistener.processCommand(cmdString);
        } catch (Exception e) {
          System.out.println(e);
        }
      }
    } catch (IOException exception) {
      System.err.println("Input error while reading input, terminating.");
      System.exit(1);
    }

  }

}
