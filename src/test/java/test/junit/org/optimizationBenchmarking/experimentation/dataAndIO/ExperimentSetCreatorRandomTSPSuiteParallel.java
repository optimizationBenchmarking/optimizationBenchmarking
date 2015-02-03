package test.junit.org.optimizationBenchmarking.experimentation.dataAndIO;

import java.util.Random;

import org.optimizationBenchmarking.experimentation.data.DimensionSet;
import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.io.impl.tspSuite.TSPSuiteInput;

/** A class for creating in parallel sets */
public class ExperimentSetCreatorRandomTSPSuiteParallel extends
    ExperimentSetCreatorRandomParallel {

  /** create */
  public ExperimentSetCreatorRandomTSPSuiteParallel() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  final void _createDimensionSet(final ExperimentSetContext dsc,
      final Random r) {
    TSPSuiteInput.makeTSPSuiteDimensionSet(dsc);
  }

  /** {@inheritDoc} */
  @Override
  final void _createInstanceSet(final ExperimentSetContext isc,
      final DimensionSet dims, final Random r) {
    TSPSuiteInput.makeTSPLibInstanceSet(isc);
  }
}
