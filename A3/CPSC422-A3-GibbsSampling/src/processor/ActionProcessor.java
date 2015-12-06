package processor;

import java.util.ArrayList;
import java.util.Random;

import model.Node;
import model.UArc;
import utils.Utils;

public class ActionProcessor {

  private Node[] nodes;
  private UArc[] conns;
  private Random randomGenerator;
  private ArrayList<Boolean> sampleResA;
  private ArrayList<Float> p_Ab;

  public ActionProcessor() {
    resetAll();
  }

  public void run(int iteration) {
    System.out.println("Reseting...");
    resetAll();
    // start sampling
    // Keep B = 1, Re-sample non-evidence variables in a pre-defined order
    // A -> B -> C -> D
    // A -> C -> D
    // P(A|b,D) -> P(C|b,D) -> P(D|A,C)
    int tempA1 = 0;
    for (int i = 0; i < iteration; i++) {
      for (int j = 0; j < 4; j++) {

        Utils.printSamplingStepGraph(j + 1); // UI
        double sample = generateDistribution(); // 0 to 1

        switch (j) {
          case 0: // P(A|b,D), record value for A, Uarc 1 and 4
            UArc arcAB = this.conns[0];
            double[] arcABres = arcAB.getProbFromStateN2(nodes[1].getState());
            UArc arcDA = this.conns[3];
            double[] arcDAres = arcDA.getProbFromStateN1(nodes[3].getState());
            double[] probA_bD = { arcABres[0] * arcDAres[0], arcABres[1] * arcDAres[1] };
            double totalA = probA_bD[0] + probA_bD[1];
            probA_bD[0] = probA_bD[0] / totalA;
            probA_bD[1] = probA_bD[1] / totalA;

            // classify sample, sample<= prob_bD[0] == T, A = 1
            boolean resultA = sample <= probA_bD[0];
            sampleResA.add(resultA);
            Utils.printSamplingStepDetail(1, probA_bD, sample, resultA);
            // update A
            this.nodes[0].setState(resultA);
            if (resultA) {
              tempA1 += 1;
            }

            break;
          case 1:
            // Do not Sample B, it is already known, b = 1
            break;
          case 2: // P(C|b,D), Uarc 2 and 3
            UArc arcBC = this.conns[1];
            double[] arcBCres = arcBC.getProbFromStateN1(nodes[1].getState());
            UArc arcCD = this.conns[2];
            double[] arcCDres = arcCD.getProbFromStateN2(nodes[3].getState());
            double[] probC_bD = { arcBCres[0] * arcCDres[0], arcBCres[1] * arcCDres[1] };
            double totalC = probC_bD[0] + probC_bD[1];
            probC_bD[0] = probC_bD[0] / totalC;
            probC_bD[1] = probC_bD[1] / totalC;

            // classify sample, sample<= probC_bD[0] == T
            boolean resultC = sample <= probC_bD[0];
            Utils.printSamplingStepDetail(3, probC_bD, sample, resultC);
            // update C
            this.nodes[2].setState(resultC);

            break;
          case 3: // P(D|A,C)
            UArc arcDA2 = this.conns[3];
            double[] arcDA2res = arcDA2.getProbFromStateN2(nodes[0].getState());
            UArc arcCD2 = this.conns[2];
            double[] arcCD2res = arcCD2.getProbFromStateN1(nodes[2].getState());
            double[] probD_AC = { arcDA2res[0] * arcCD2res[0], arcDA2res[1] * arcCD2res[1] };
            double totalD = probD_AC[0] + probD_AC[1];
            probD_AC[0] = probD_AC[0] / totalD;
            probD_AC[1] = probD_AC[1] / totalD;

            // classify sample, sample<= probD_AC[0] == T
            boolean resultD = sample <= probD_AC[0];
            Utils.printSamplingStepDetail(4, probD_AC, sample, resultD);
            // update D
            this.nodes[3].setState(resultD);

            break;
        }
      }
      Utils.printCommandFeedBack("Iteration: " + (i + 1) + "/" + iteration + " ======================================");
      float temp_p = ((float) tempA1 / (float) (i + 1));
      this.p_Ab.add(temp_p);
    }
    // iteration completed
    int numA_1 = 0;
    int numA_0 = 0;
    int numA_total = 0;
    for (Boolean r : this.sampleResA) {
      numA_total += 1;
      if (r) {
        numA_1 += 1;
      } else {
        numA_0 += 1;
      }
    }
    System.out.println("A = 1: " + numA_1);
    System.out.println("A = 0: " + numA_0);
    System.out.println("A = total: " + numA_total);
    System.out.println("P(A|B=1): " + ((float) numA_1 / (float) numA_total));
    try {
      Utils.writeToDisk(this.p_Ab);
    } catch (Exception e) {
    }
  }

  public void resetAll() {
    this.randomGenerator = new Random();
    this.sampleResA = new ArrayList<Boolean>();
    this.p_Ab = new ArrayList<Float>();
    this.nodes = new Node[4];
    this.conns = new UArc[4];

    this.nodes[0] = new Node("A", 1, true); // preset to 1
    this.nodes[1] = new Node("B", 2, true); // always set to 1
    this.nodes[2] = new Node("C", 3, false); // preset to 0
    this.nodes[3] = new Node("D", 4, false); // preset to 0

    // -------0---------------1-------------2----------------3-----------
    // n1 = X, n2 = X; n1 = X, n2 = V; n1 = V, n2 = X; n1 = V, n2 = V;
    // ------------------------------------------------------ FF FT TF TT
    this.conns[0] = new UArc(this.nodes[0], this.nodes[1], 1, 30, 5, 1, 10); // AB
    this.conns[1] = new UArc(this.nodes[1], this.nodes[2], 2, 100, 1, 1, 100); // BC
    this.conns[2] = new UArc(this.nodes[2], this.nodes[3], 3, 1, 100, 100, 1); // CD
    this.conns[3] = new UArc(this.nodes[3], this.nodes[0], 4, 100, 1, 1, 100); // DA
  }

  private Double generateDistribution() {
    if (randomGenerator == null) {
      randomGenerator = new Random();
    }
    double result = randomGenerator.nextDouble();
    return result;
  }
}
