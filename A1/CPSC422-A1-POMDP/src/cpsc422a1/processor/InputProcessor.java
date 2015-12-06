package cpsc422a1.processor;

import cpsc422a1.utils.Utils;

public class InputProcessor {

  private ActionProcessor actionProcessor;

  public InputProcessor(ActionProcessor actionProcessor) {
    this.actionProcessor = actionProcessor;
  }

  public void processCommand(final byte[] ipt) throws Exception {

    String arguments = new String(ipt).trim();
    String[] input = arguments.split("[ \n\r]");

    if (input[0].contains("exit")) {
      Utils.printCommandFeedBack("Terminated");
      System.exit(0);
    } else if (input[0].equalsIgnoreCase("run")) {
      if (input.length == 2) {
        if (input[1].equalsIgnoreCase("1")) {
          Utils.printCommandFeedBack("Executing...Seq 1");
          actionProcessor.triggerBeliefStateUpdate(1);
        } else if (input[1].equalsIgnoreCase("2")) {
          Utils.printCommandFeedBack("Executing...Seq 2");
          actionProcessor.triggerBeliefStateUpdate(2);
        } else if (input[1].equalsIgnoreCase("3")) {
          Utils.printCommandFeedBack("Executing...Seq 3");
          actionProcessor.triggerBeliefStateUpdate(3);
        } else if (input[1].equalsIgnoreCase("4")) {
          Utils.printCommandFeedBack("Executing...Seq 4");
          actionProcessor.triggerBeliefStateUpdate(4);
        } else if (input[1].equalsIgnoreCase("all")) {
          Utils.printCommandFeedBack("Executing...All Seq");
          System.out.println("Seq 1");
          actionProcessor.triggerBeliefStateUpdate(1);
          Utils.printCommandFeedBack("-------------------------------------------------");
          System.out.println("Seq 2");
          actionProcessor.triggerBeliefStateUpdate(2);
          Utils.printCommandFeedBack("-------------------------------------------------");
          System.out.println("Seq 3");
          actionProcessor.triggerBeliefStateUpdate(3);
          Utils.printCommandFeedBack("-------------------------------------------------");
          System.out.println("Seq 4");
          actionProcessor.triggerBeliefStateUpdate(4);
          Utils.printCommandFeedBack("-------------------------------------------------");
        } else if (input[1].equalsIgnoreCase("999")) {
          Utils.printCommandFeedBack("Executing...DEBUG: Seq 999");
          actionProcessor.triggerBeliefStateUpdate(999);
        } else {
          throw new IllegalArgumentException("run command must be accompanied by a preset test number. Valid input: 1, 2, 3, 4, all\n");
        }
      } else {
        throw new IllegalArgumentException("run command must be accompanied by a preset test number. Valid input: 1, 2, 3, 4, all\n");
      }
    } else if (input[0].equalsIgnoreCase("reset")) {
      Utils.printCommandFeedBack("Reseting...");
      actionProcessor.resetAll();
    } else if (input[0].equalsIgnoreCase("help") || input[0].equalsIgnoreCase("-h")) {
      System.out.println("");
      System.out.println("Help Commands:");
      System.out.println("> exit: Quit the program");
      System.out.println("> run: run agent with the current preset sequences, accepts 1, 2, 3, 4, all");
      System.out.println("> reset: reset both the board and the agent to DEFAULT");
      System.out.println("");
    } else {
      throw new IllegalArgumentException("Unknown command. Please type help or -h to read more on how to use it.\n");
    }

  }
}
