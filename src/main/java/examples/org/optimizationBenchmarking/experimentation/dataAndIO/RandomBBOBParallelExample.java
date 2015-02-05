package examples.org.optimizationBenchmarking.experimentation.dataAndIO;

import java.util.Random;

import org.optimizationBenchmarking.experimentation.data.DimensionSet;
import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.io.impl.bbob.BBOBInput;

/** A class for creating in parallel sets */
public final class RandomBBOBParallelExample extends RandomParallelExample {

  /** create */
  public RandomBBOBParallelExample() {
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

  /**
   * The main routine
   * 
   * @param args
   *          the command line arguments
   */
  public static void main(final String[] args) {
    new RandomBBOBParallelExample().run();
  }
}
