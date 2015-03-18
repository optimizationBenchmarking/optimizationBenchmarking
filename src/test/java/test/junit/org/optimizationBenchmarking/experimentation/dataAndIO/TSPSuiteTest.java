package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import org.junit.experimental.categories.Category;

import test.junit.CategorySlowTests;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.TSPSuiteExample;

/** A class for creating experiment sets */
@Category(CategorySlowTests.class)
public class TSPSuiteTest extends ExperimentSetTest {

  /** create */
  public TSPSuiteTest() {
    super(new TSPSuiteExample());
  }
}
