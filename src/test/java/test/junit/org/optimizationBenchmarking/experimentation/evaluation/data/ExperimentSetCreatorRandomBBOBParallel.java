package test.junit.org.optimizationBenchmarking.experimentation.evaluation.data;

import java.util.Random;

import org.optimizationBenchmarking.experimentation.evaluation.data.DimensionSet;
import org.optimizationBenchmarking.experimentation.evaluation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.evaluation.io.bbob.BBOBInput;

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
    BBOBInput.makeBBOBDimensionSet(dsc);
  }

  /** {@inheritDoc} */
  @Override
  final void _createInstanceSet(final ExperimentSetContext isc,
      final DimensionSet dims, final Random r) {
    BBOBInput.makeBBOBInstanceSet(isc);
  }
}
