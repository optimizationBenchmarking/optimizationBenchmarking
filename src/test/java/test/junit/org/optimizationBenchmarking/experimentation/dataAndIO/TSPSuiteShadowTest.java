package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import java.util.logging.Logger;

import org.junit.experimental.categories.Category;
import org.optimizationBenchmarking.experimentation.data.impl.shadow.ShadowExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;

import test.junit.CategorySlowTests;
import test.junit.TestBase;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.ExperimentSetCreator;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.TSPSuiteExample;

/** Test the shadow of the first example data. */
@Category(CategorySlowTests.class)
public class TSPSuiteShadowTest extends ExperimentSetTest {

  /** create */
  public TSPSuiteShadowTest() {
    super(new __TSPSuiteCreatorWrapper(TestBase.getNullLogger()));
  }

  /** wrap an experiment set creator */
  private static final class __TSPSuiteCreatorWrapper extends
      ExperimentSetCreator {

    /** the example */
    private final TSPSuiteExample m_tspSuiteExample;

    /**
     * create
     * 
     * @param logger
     *          the logger, or {@code null} to use the global logger
     */
    protected __TSPSuiteCreatorWrapper(final Logger logger) {
      super(logger);
      this.m_tspSuiteExample = new TSPSuiteExample(logger);
    }

    /** {@inheritDoc} */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected final IExperimentSet buildExperimentSet() throws Exception {
      return new ShadowExperimentSet(null,
          this.m_tspSuiteExample.getExperimentSet(), null);
    }

  }
}
