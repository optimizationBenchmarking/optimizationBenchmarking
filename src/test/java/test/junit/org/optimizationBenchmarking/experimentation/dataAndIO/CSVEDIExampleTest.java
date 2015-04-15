package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import test.junit.TestBase;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.CSVEDIExample;

/** A class for loading experiment sets from a CSV/EDI mixture */
public class CSVEDIExampleTest extends ExperimentSetTest {

  /** create */
  public CSVEDIExampleTest() {
    super(new CSVEDIExample(TestBase.getNullLogger()));
  }
}
