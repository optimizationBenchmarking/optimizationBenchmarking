package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import org.junit.experimental.categories.Category;

import test.junit.CategorySlowTests;
import test.junit.TestBase;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.RandomExample;

/** Test the random data. */
@Category(CategorySlowTests.class)
public class RandomTest extends ExperimentSetTest {

  /** create */
  public RandomTest() {
    super(new RandomExample(TestBase.getNullLogger()));
  }
}