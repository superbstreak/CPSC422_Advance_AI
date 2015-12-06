package processor;

import utils.Utils;

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
        try {
          int iteration = Integer.parseInt(input[1]);
          if (iteration <= 0) {
            throw new IllegalArgumentException("run command must be accompanied by a valid iteration number greater than 0.\n");
          } else {
            Utils.printCommandFeedBack("Running Gibbs " + iteration + " times");
            actionProcessor.run(iteration);
          }
        } catch (Exception e) {
          throw new IllegalArgumentException("run command must be accompanied by a valid iteration number in integer form.\n" + e);
        }
      } else {
        throw new IllegalArgumentException("run command must be accompanied by an iteration number.\n");
      }
    } else if (input[0].equalsIgnoreCase("reset")) {
      Utils.printCommandFeedBack("Reseting...");
      Utils.printAll();
      actionProcessor.resetAll();
    } else if (input[0].equalsIgnoreCase("help") || input[0].equalsIgnoreCase("-h")) {
      System.out.println("");
      System.out.println("Help Commands:");
      System.out.println("> exit: Quit the program");
      System.out.println("> run [int]: run sampling [int] number of times. A file of P(A|b) will be written on to your disk.");
      System.out.println("> reset: reset all to DEFAULT");
      System.out.println("");
    } else {
      throw new IllegalArgumentException("Unknown command. Please type help or -h to read more on how to use it.\n");
    }

  }
}
