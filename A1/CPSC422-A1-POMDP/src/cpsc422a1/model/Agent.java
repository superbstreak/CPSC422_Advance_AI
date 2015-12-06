package cpsc422a1.model;

import java.util.List;

import cpsc422a1.core.Config;
import cpsc422a1.utils.Utils;

public class Agent {
  private Space space;

  public Agent(Space space) {
    this.space = space;
  }

  public void setSpace(Space space) {
    this.space = space;
  }

  public float performAction(State state, int action) {
    switch (action) {
      case Config.ACTION_UP:
        return performUp(state);
      case Config.ACTION_DOWN:
        return performDown(state);
      case Config.ACTION_LEFT:
        return performLeft(state);
      case Config.ACTION_RIGHT:
        return performRight(state);
    }
    return 0;
  }

  public void updateBeliefState(Space newSpace, List<Integer> actions, List<Integer> observations) {
    setSpace(newSpace);
    int actionsSize = actions.size();
    for (int i = 0; i < actionsSize; i++) {
      performUpdate(actions.get(i), observations.get(i));
      Utils.printBoardMapping(space.getBoard(), "Sequence #" + space.getSequenceNumber() + " Iteration Count: " + (i + 1) + "/" + actionsSize + " Action: " + Config.ACTION_NAME[actions.get(i) - 1]
          + " Observation: " + Config.OBSERVATION_NAME[observations.get(i) - 1]);
      Utils.printDetailMapping(space.getBoard());
    }
  }

  // ----------------------------------------------------------
  // HELPER

  private void performUpdate(int action, int observation) {

    // action
    float[][] updatedValues = new float[Config.xSize][Config.ySize];
    float totalSum = 0.0f;

    for (int x = 0; x < Config.xSize; x++) {
      for (int y = 0; y < Config.ySize; y++) {
        State state = space.getBoard()[x][y];
        if (!state.isWall()) {
          float actionValue = performAction(state, action);
          float observationValue = 0.0f;

          // observation
          switch (observation) {
            case Config.OBSERVATION_1WALL:
              if (!state.isTerminal() && x == 2) {
                observationValue = 0.9f;
              } else if (!state.isTerminal()) {
                observationValue = 0.1f;
              } else if (state.isTerminal()) {
                observationValue = 0.0f;
              }
              break;
            case Config.OBSERVATION_2WALL:
              if (!state.isTerminal() && x == 2) {
                observationValue = 0.1f;
              } else if (!state.isTerminal()) {
                observationValue = 0.9f;
              } else if (state.isTerminal()) {
                observationValue = 0.0f;
              }
              break;
            case Config.OBSERVATION_END:
              if (!state.isTerminal() && x == 2) {
                observationValue = 0.0f;
              } else if (!state.isTerminal()) {
                observationValue = 0.0f;
              } else if (state.isTerminal()) {
                observationValue = 1.0f;
              }
              break;
          }

          float updatedValue = actionValue * observationValue;
          updatedValues[x][y] = updatedValue;
          totalSum += updatedValue; // sum up to normalize
        }
      }
    }

    for (int x = 0; x < Config.xSize; x++) {
      for (int y = 0; y < Config.ySize; y++) {
        State state = space.getBoard()[x][y];
        if (!state.isWall()) {
          state.setValue((totalSum == 1f) ? updatedValues[x][y] : (updatedValues[x][y] / totalSum)); // normalize
        }
      }
    }
  }

  private float performUp(State state) {
    // prob. of reaching the state given action was up

    int x = state.getX();
    int y = state.getY();
    float value = 0;

    // 0.8 - Came from Below or hit the wall
    if (y == 2) {
      value += 0.8 * state.getValue(); // hit the wall and bounces back
    }

    if (x == 1 && y == 0) {
      value += 0.8 * state.getValue(); // hit the inner wall
    }

    if (y - 1 >= 0) {
      State below = space.getAdjacentState(state, "below");
      if (isStateValid(below)) {
        value += 0.8 * below.getValue(); // actually went up and reach the current state
      }
    }

    // 0.1 - Perform a Left and end up in the state
    if (x == 0) {
      value += 0.1 * state.getValue(); // hit the wall
    }

    if (x == 2 && y == 1) {
      value += 0.1 * state.getValue(); // hit the innter wall
    }

    if (x + 1 <= 3) {
      State right = space.getAdjacentState(state, "right");
      if (isStateValid(right)) {
        value += 0.1 * right.getValue();
      }
    }

    // 0.1 - Perform a Right and end up in the state
    if (x == 3) {
      value += 0.1 * state.getValue(); // hit the wall
    }

    if (x == 0 && y == 1) {
      value += 0.1 * state.getValue(); // hit the innter wall
    }

    if (x - 1 >= 0) {
      State left = space.getAdjacentState(state, "left");
      if (isStateValid(left)) {
        value += 0.1 * left.getValue();
      }
    }

    return value;
  }

  private float performDown(State state) {
    // prob. of reaching the state given action was down
    int x = state.getX();
    int y = state.getY();
    float value = 0;

    // 0.8
    if (y == 0) {
      value += 0.8 * state.getValue();
    }

    if (x == 1 && y == 2) {
      value += 0.8 * state.getValue();
    }

    if (y + 1 <= 2) {
      State above = space.getAdjacentState(state, "above");
      if (isStateValid(above)) {
        value += 0.8 * above.getValue();
      }
    }

    // 0.1 Perform Left
    if (x == 0) {
      value += 0.1 * state.getValue(); // hit the wall
    }

    if (x == 2 && y == 1) {
      value += 0.1 * state.getValue(); // hit the innter wall
    }

    if (x + 1 <= 3) {
      State right = space.getAdjacentState(state, "right");
      if (isStateValid(right)) {
        value += 0.1 * right.getValue();
      }
    }

    // 0.1
    if (x == 3) {
      value += 0.1 * state.getValue(); // hit the wall
    }

    if (x == 0 && y == 1) {
      value += 0.1 * state.getValue(); // hit the innter wall
    }

    if (x - 1 >= 0) {
      State left = space.getAdjacentState(state, "left");
      if (isStateValid(left)) {
        value += 0.1 * left.getValue();
      }
    }

    return value;
  }

  private float performRight(State state) {
    // prob. of reaching the state given action was right
    int x = state.getX();
    int y = state.getY();
    float value = 0;

    // 0.8
    if (x == 3) {
      value += 0.8 * state.getValue();
    }

    if (x == 0 && y == 1) {
      value += 0.8 * state.getValue();
    }

    if (x - 1 >= 0) {
      State left = space.getAdjacentState(state, "left");
      if (isStateValid(left)) {
        value += 0.8 * left.getValue();
      }
    }

    // 0.1 - Perform up
    if (y == 2) {
      value += 0.1 * state.getValue();
    }

    if (x == 1 && y == 0) {
      value += 0.1 * state.getValue();
    }

    if (y - 1 >= 0) {
      State below = space.getAdjacentState(state, "below");
      if (isStateValid(below)) {
        value += 0.1 * below.getValue();
      }
    }

    // 0.1 - Perform down
    if (y == 0) {
      value += 0.1 * state.getValue();
    }

    if (x == 1 && y == 2) {
      value += 0.1 * state.getValue();
    }

    if (y + 1 <= 2) {
      State above = space.getAdjacentState(state, "above");
      if (isStateValid(above)) {
        value += 0.1 * above.getValue();
      }
    }

    return value;
  }

  private float performLeft(State state) {
    // prob. of reaching the state given action was left
    int x = state.getX();
    int y = state.getY();
    float value = 0;

    // 0.8
    if (x == 0) {
      value += 0.8 * state.getValue();
    }

    if (x == 2 && y == 1) {
      value += 0.8 * state.getValue();
    }

    if (x + 1 <= 3) {
      State right = space.getAdjacentState(state, "right");
      if (isStateValid(right)) {
        value += 0.8 * right.getValue();
      }
    }

    // 0.1 - Perform up
    if (y == 2) {
      value += 0.1 * state.getValue();
    }

    if (x == 1 && y == 0) {
      value += 0.1 * state.getValue();
    }

    if (y - 1 >= 0) {
      State below = space.getAdjacentState(state, "below");
      if (isStateValid(below)) {
        value += 0.1 * below.getValue();
      }
    }

    // 0.1 - Perform down
    if (y == 0) {
      value += 0.1 * state.getValue();
    }

    if (x == 1 && y == 2) {
      value += 0.1 * state.getValue();
    }

    if (y + 1 <= 2) {
      State above = space.getAdjacentState(state, "above");
      if (isStateValid(above)) {
        value += 0.1 * above.getValue();
      }
    }

    return value;
  }

  private boolean isStateValid(State state) {
    return !state.isTerminal() && !state.isWall();
  }
}
