package examples.org.optimizationBenchmarking.experimentation.dataAndIO;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.error.ErrorUtils;

/** The set of experiment examples. */
public final class ExperimentSetExamples {

  /** the experiment set creators */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static final ArrayListView<Class<? extends ExperimentSetCreator>> EXAMPLES = //
  new ArrayListView(new Class[] {//
      Example1.class,//
          Example2Random.class,//
          BBOBExample.class,//
          TSPSuiteExample.class,//
          RandomBBOBParallelExample.class,//
          RandomTSPSuiteParallelExample.class,//
          RandomParallelExample.class });

  /** the forbidden constructor */
  private ExperimentSetExamples() {
    ErrorUtils.doNotCall();
  }
}
