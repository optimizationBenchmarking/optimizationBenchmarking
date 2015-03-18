package examples.org.optimizationBenchmarking.experimentation.dataAndIO;

import java.util.Random;

import org.optimizationBenchmarking.experimentation.data.DimensionContext;
import org.optimizationBenchmarking.experimentation.data.EDimensionDirection;
import org.optimizationBenchmarking.experimentation.data.EDimensionType;
import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.utils.math.random.RandomUtils;
import org.optimizationBenchmarking.utils.parsers.DoubleParser;
import org.optimizationBenchmarking.utils.parsers.LongParser;

/** A class for creating experiment sets */
public final class Example2Random extends RandomExample {

  /** create */
  public Example2Random() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  final void _createDimensionSet(final ExperimentSetContext dsc,
      final Random r) {

    do {
      try (DimensionContext dc = dsc.createDimension()) {
        dc.setName(RandomUtils.longToString(RandomExample.NAMING,
            this.m_v.incrementAndGet()));
        dc.setParser(LongParser.INSTANCE);
        dc.setType(EDimensionType.ITERATION_FE);
        dc.setDirection(EDimensionDirection.INCREASING_STRICTLY);
      }

      try (DimensionContext dc = dsc.createDimension()) {
        dc.setName(RandomUtils.longToString(RandomExample.NAMING,
            this.m_v.incrementAndGet()));
        dc.setParser(LongParser.INSTANCE);
        dc.setType(EDimensionType.ITERATION_SUB_FE);
        dc.setDirection(EDimensionDirection.DECREASING);
      }

      try (DimensionContext dc = dsc.createDimension()) {
        dc.setName(RandomUtils.longToString(RandomExample.NAMING,
            this.m_v.incrementAndGet()));
        dc.setParser(LongParser.INSTANCE);
        dc.setType(EDimensionType.RUNTIME_CPU);
        dc.setDirection(EDimensionDirection.INCREASING);
      }

      try (DimensionContext dc = dsc.createDimension()) {
        dc.setName(RandomUtils.longToString(RandomExample.NAMING,
            this.m_v.incrementAndGet()));
        dc.setParser(DoubleParser.INSTANCE);
        dc.setType(EDimensionType.RUNTIME_NORMALIZED);
        dc.setDirection(EDimensionDirection.INCREASING);
      }

      try (DimensionContext dc = dsc.createDimension()) {
        dc.setName(RandomUtils.longToString(RandomExample.NAMING,
            this.m_v.incrementAndGet()));
        dc.setParser(LongParser.INSTANCE);
        dc.setType(EDimensionType.QUALITY_PROBLEM_DEPENDENT);
        dc.setDirection(EDimensionDirection.DECREASING);
      }

      try (DimensionContext dc = dsc.createDimension()) {
        dc.setName(RandomUtils.longToString(RandomExample.NAMING,
            this.m_v.incrementAndGet()));
        dc.setParser(DoubleParser.INSTANCE);
        dc.setType(EDimensionType.QUALITY_PROBLEM_INDEPENDENT);
        dc.setDirection(EDimensionDirection.INCREASING);
      }
    } while (r.nextBoolean());
  }

  /**
   * The main routine
   * 
   * @param args
   *          the command line arguments
   */
  public static final void main(final String[] args) {
    new Example2Random().run();
  }
}
