package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.impl.shadow.ShadowExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;

import test.junit.TestBase;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.Example1;
import examples.org.optimizationBenchmarking.experimentation.dataAndIO.ExperimentSetCreator;

/** Test the multiple-times shadowing of the first example data. */
public class Example1MultiShadowTest extends ExperimentSetTest {

  /** create */
  public Example1MultiShadowTest() {
    super(new __ExperimentSet1CreatorWrapper(TestBase.getNullLogger()));
  }

  /** wrap an experiment set creator */
  private static final class __ExperimentSet1CreatorWrapper extends
  ExperimentSetCreator {

    /** the example */
    private final Example1 m_example1;

    /**
     * create
     *
     * @param logger
     *          the logger, or {@code null} to use the global logger
     */
    protected __ExperimentSet1CreatorWrapper(final Logger logger) {
      super(logger);
      this.m_example1 = new Example1(logger);
    }

    /** {@inheritDoc} */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected final IExperimentSet buildExperimentSet() throws Exception {
      int i;
      IExperimentSet set;

      set = this.m_example1.getExperimentSet();
      for (i = 10; (--i) >= 0;) {
        set = new ShadowExperimentSet(null, set, null);
      }

      return set;
    }

  }
}
