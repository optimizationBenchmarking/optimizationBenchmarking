package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import org.junit.experimental.categories.Category;

import test.junit.CategorySlowTests;
import test.junit.TestBase;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.Example1;

/** Test the first example. */
@Category(CategorySlowTests.class)
public class Example1Test extends ExperimentSetTest {

  /** create */
  public Example1Test() {
    super(new Example1(TestBase.getNullLogger()));
  }
}
