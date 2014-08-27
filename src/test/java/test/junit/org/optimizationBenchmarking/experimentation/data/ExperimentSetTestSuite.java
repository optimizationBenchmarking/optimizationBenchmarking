package test.junit.org.optimizationBenchmarking.experimentation.data;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/** the test suite class for experiment sets */
@RunWith(Suite.class)
@Suite.SuiteClasses({ ExperimentSetCreatorExample1.class,
    ExperimentSetCreatorExample2Random.class,
    ExperimentSetCreatorRandom.class,
    ExperimentSetCreatorTSPSuiteExample.class,
    ExperimentSetCreatorRandomTSPSuiteParallel.class,
    ExperimentSetCreatorBBOBExample.class,
    ExperimentSetCreatorRandomBBOBParallel.class })
public class ExperimentSetTestSuite {
  // a test suite class
}
