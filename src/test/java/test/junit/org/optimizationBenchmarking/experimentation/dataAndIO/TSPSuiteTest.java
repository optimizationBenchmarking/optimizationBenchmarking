package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import org.junit.experimental.categories.Category;

import test.junit.CategorySlowTests;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.TSPSuiteExample;

/** A class for loading a TSPSuite experiment set */
@Category(CategorySlowTests.class)
public class TSPSuiteTest extends ExperimentSetTest {

  /** create */
  public TSPSuiteTest() {
    super(new TSPSuiteExample());
  }
}
