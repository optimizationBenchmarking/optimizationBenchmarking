package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import org.junit.experimental.categories.Category;

import test.junit.CategorySlowTests;
import test.junit.TestBase;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.Example2Random;

/** Test the second example data. */
@Category(CategorySlowTests.class)
public class Example2RandomTest extends ExperimentSetTest {

  /** create */
  public Example2RandomTest() {
    super(new Example2Random(TestBase.getNullLogger()));
  }

}
