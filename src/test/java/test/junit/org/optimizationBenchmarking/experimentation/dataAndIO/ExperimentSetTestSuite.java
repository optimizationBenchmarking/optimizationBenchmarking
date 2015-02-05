package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/** the test suite class for experiment sets */
@RunWith(Suite.class)
@Suite.SuiteClasses({ Example1Test.class, Example2RandomTest.class,
    RandomTest.class, TSPSuiteTest.class,
    RandomTSPSuiteParallelTest.class, BBOBExampleTest.class,
    RandomBBOBParallelTest.class })
public class ExperimentSetTestSuite {
  // a test suite class
}
