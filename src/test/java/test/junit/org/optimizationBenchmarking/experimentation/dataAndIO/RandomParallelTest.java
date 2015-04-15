package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import test.junit.TestBase;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.RandomParallelExample;

/** A class for creating in parallel sets */
public class RandomParallelTest extends ExperimentSetTest {

  /** create */
  public RandomParallelTest() {
    super(new RandomParallelExample(TestBase.getNullLogger()));
  }
}