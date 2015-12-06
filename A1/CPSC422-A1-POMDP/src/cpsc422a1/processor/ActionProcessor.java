package cpsc422a1.processor;

import java.util.ArrayList;
import java.util.List;

import cpsc422a1.core.Config;
import cpsc422a1.model.Agent;
import cpsc422a1.model.Space;

public class ActionProcessor {

  private Space space;
  private Agent agent;

  public ActionProcessor() {
    this.space = new Space();
    this.agent = new Agent(this.space);
  }

  public void triggerBeliefStateUpdate(int preSetNum) {
    List<Integer> actions = new ArrayList<Integer>();
    List<Integer> observations = new ArrayList<Integer>();
    switch (preSetNum) {
      case 1:
        space.defaultInitialBeliefState();
        space.setSequenceNumber(1);
        actions.add(Config.ACTION_UP);
        actions.add(Config.ACTION_UP);
        actions.add(Config.ACTION_UP);
        observations.add(Config.OBSERVATION_2WALL);
        observations.add(Config.OBSERVATION_2WALL);
        observations.add(Config.OBSERVATION_2WALL);
        break;
      case 2:
        space.defaultInitialBeliefState();
        space.setSequenceNumber(2);
        actions.add(Config.ACTION_UP);
        actions.add(Config.ACTION_UP);
        actions.add(Config.ACTION_UP);
        observations.add(Config.OBSERVATION_1WALL);
        observations.add(Config.OBSERVATION_1WALL);
        observations.add(Config.OBSERVATION_1WALL);
        break;
      case 3:
        space.customInitialBeliefState(1, 2);
        space.setSequenceNumber(3);
        actions.add(Config.ACTION_RIGHT);
        actions.add(Config.ACTION_RIGHT);
        actions.add(Config.ACTION_UP);
        observations.add(Config.OBSERVATION_1WALL);
        observations.add(Config.OBSERVATION_1WALL);
        observations.add(Config.OBSERVATION_END);
        break;
      case 4:
        space.customInitialBeliefState(0, 0);
        space.setSequenceNumber(4);
        actions.add(Config.ACTION_UP);
        actions.add(Config.ACTION_RIGHT);
        actions.add(Config.ACTION_RIGHT);
        actions.add(Config.ACTION_RIGHT);
        observations.add(Config.OBSERVATION_2WALL);
        observations.add(Config.OBSERVATION_2WALL);
        observations.add(Config.OBSERVATION_1WALL);
        observations.add(Config.OBSERVATION_1WALL);
        break;
      case 999:
        space.defaultInitialBeliefState();
        space.setSequenceNumber(999);
        actions.add(Config.ACTION_LEFT);
        observations.add(Config.OBSERVATION_1WALL);
        break;
    }
    agent.updateBeliefState(space, actions, observations);
  }

  public void resetAll() {
    space.defaultInitialBeliefState();
  }
}
