package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import test.junit.TestBase;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.Example2Random;

/** Test the second example data. */
public class Example2RandomTest extends ExperimentSetTest {

  /** create */
  public Example2RandomTest() {
    super(new Example2Random(TestBase.getNullLogger()));
  }

}
