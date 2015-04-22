package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import test.junit.TestBase;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.RandomExample;

/** Test the random data. */
public class RandomTest extends ExperimentSetTest {

  /** create */
  public RandomTest() {
    super(new RandomExample(TestBase.getNullLogger()));
  }
}