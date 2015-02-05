package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import examples.org.optimizationBenchmarking.experimentation.dataAndIO.TSPSuiteExample;

/** A class for creating experiment sets */
public class TSPSuiteTest extends ExperimentSetTest {

  /** create */
  public TSPSuiteTest() {
    super(new TSPSuiteExample());
  }
}
