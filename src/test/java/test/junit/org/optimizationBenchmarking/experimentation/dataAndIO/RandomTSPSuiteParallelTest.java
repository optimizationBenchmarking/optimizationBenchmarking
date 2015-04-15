package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import org.junit.experimental.categories.Category;

import test.junit.CategorySlowTests;
import test.junit.TestBase;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.RandomTSPSuiteParallelExample;

/** A class for creating in parallel sets according to the TSP Suite format */
@Category(CategorySlowTests.class)
public class RandomTSPSuiteParallelTest extends ExperimentSetTest {

  /** create */
  public RandomTSPSuiteParallelTest() {
    super(new RandomTSPSuiteParallelExample(TestBase.getNullLogger()));
  }
}