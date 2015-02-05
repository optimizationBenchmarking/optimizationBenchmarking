package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import examples.org.optimizationBenchmarking.experimentation.dataAndIO.RandomExample;

/** A class for creating experiment sets */
public class RandomTest extends ExperimentSetTest {

  /** create */
  public RandomTest() {
    super(new RandomExample());
  }
}