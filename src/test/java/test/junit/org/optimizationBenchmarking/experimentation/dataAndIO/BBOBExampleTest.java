package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import org.junit.experimental.categories.Category;

import test.junit.CategorySlowTests;
import test.junit.TestBase;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.BBOBExample;

/** A class for creating experiment sets */
@Category(CategorySlowTests.class)
public class BBOBExampleTest extends ExperimentSetTest {

  /** create */
  public BBOBExampleTest() {
    super(new BBOBExample(TestBase.getNullLogger()));
  }
}
