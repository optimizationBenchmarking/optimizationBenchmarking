package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import org.junit.experimental.categories.Category;

import test.junit.CategorySlowTests;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.RandomTSPSuiteParallelExample;

/** A class for creating in parallel sets */
@Category(CategorySlowTests.class)
public class RandomTSPSuiteParallelTest extends ExperimentSetTest {

  /** create */
  public RandomTSPSuiteParallelTest() {
    super(new RandomTSPSuiteParallelExample());
  }
}