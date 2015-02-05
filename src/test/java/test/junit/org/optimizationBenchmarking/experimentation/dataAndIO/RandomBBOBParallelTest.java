package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import examples.org.optimizationBenchmarking.experimentation.dataAndIO.RandomBBOBParallelExample;

/** A class for creating in parallel sets */
public class RandomBBOBParallelTest extends ExperimentSetTest {

  /** create */
  public RandomBBOBParallelTest() {
    super(new RandomBBOBParallelExample());
  }
}