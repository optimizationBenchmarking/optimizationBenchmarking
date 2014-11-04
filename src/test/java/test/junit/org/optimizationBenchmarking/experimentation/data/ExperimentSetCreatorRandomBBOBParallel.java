package test.junit.org.optimizationBenchmarking.experimentation.data;

import java.util.Random;

import org.optimizationBenchmarking.experimentation.data.DimensionSet;
import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.io.bbob.BBOBInputDriver;

/** A class for creating in parallel sets */
public class ExperimentSetCreatorRandomBBOBParallel extends
    ExperimentSetCreatorRandomParallel {

  /** create */
  public ExperimentSetCreatorRandomBBOBParallel() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  final void _createDimensionSet(final ExperimentSetContext dsc,
      final Random r) {
    BBOBInputDriver.makeBBOBDimensionSet(dsc);
  }

  /** {@inheritDoc} */
  @Override
  final void _createInstanceSet(final ExperimentSetContext isc,
      final DimensionSet dims, final Random r) {
    BBOBInputDriver.makeBBOBInstanceSet(isc);
  }
}
