package core;

import java.io.IOException;

import processor.ActionProcessor;
import processor.InputProcessor;
import utils.Utils;

public class Main {

  private static final int MAX_LEN = 255;
  private static InputProcessor inputlistener;
  private static ActionProcessor actionProcessor;

  public static void main(String[] args) {

    System.out.println();
    System.out.println("===========================================");
    System.out.println(" CPSC 422 A3 Q1: Gibbs Sampling");
    System.out.println(" Name: Rob Wu");
    System.out.println(" SID: 42764118");
    System.out.println(" CSID: y4d8");
    System.out.println("===========================================");
    System.out.println();

    // initializing board
    actionProcessor = new ActionProcessor();
    inputlistener = new InputProcessor(actionProcessor);

    Utils.printAll();
    System.out.println();
    System.out.println("Please enter run [int] and press enter to get started.");
    System.out.println("A file of P(A|b) will be written on to your disk. This file will contain all 1...t iteration.");
    System.out.println("For more information on other commands, please type 'help' or '-h'");
    System.out.println();

    // starts
    byte cmdString[] = new byte[MAX_LEN];
    try {
      for (int len = 1; len > 0;) {
        System.out.print("cpsc422a3q1> ");
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
