package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import examples.org.optimizationBenchmarking.experimentation.dataAndIO.RandomTSPSuiteParallelExample;

/** A class for creating in parallel sets */
public class RandomTSPSuiteParallelTest extends ExperimentSetTest {

  /** create */
  public RandomTSPSuiteParallelTest() {
    super(new RandomTSPSuiteParallelExample());
  }
}