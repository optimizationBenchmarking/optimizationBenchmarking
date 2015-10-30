package org.optimizationBenchmarking.utils.math.fitting.impl.opti;

import java.util.Arrays;
import java.util.Comparator;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.math.fitting.spec.FittingJob;
import org.optimizationBenchmarking.utils.math.fitting.spec.IParameterGuesser;
import org.optimizationBenchmarking.utils.math.fitting.spec.ParametricUnaryFunction;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.StableSum;

/** The base class for optimization-based fitting. */
public class OptiFittingJob extends FittingJob {

  /** the data points */
  protected final DataPoint[] m_points;

  /** the parameter guesser */
  protected final IParameterGuesser m_guesser;

  /**
   * the the number of worst data points used during the objective value
   * computation
   */
  protected final int m_numberOfPointsUsed;

  /** the error sul */
  final StableSum m_sum;

  /** sort by error */
  final __SortByError m_sortByError;
  /** the dimension */
  final int m_dim;

  /**
   * create the fitting job
   *
   * @param builder
   *          the job builder
   */
  protected OptiFittingJob(final OptiFittingJobBuilder builder) {
    super(builder);

    final IMatrix points;
    int index;

    points = builder.getPoints();

    this.m_guesser = this.m_function.createParameterGuesser(points);

    index = points.m();

    this.m_numberOfPointsUsed = Math.max(0, //
        Math.min((index - 1),
            Math.max(builder.getMinCriticalPoints(), //
                Math.min(70, Math.max(10, //
                    (1 + ((int) (0.5d + (index / 13d)))))))));

    this.m_points = new DataPoint[index];
    for (; (--index) >= 0;) {
      this.m_points[index] = new DataPoint(//
          points.getDouble(index, 0), points.getDouble(index, 1));
    }

    this.m_sortByError = new __SortByError();
    this.m_sum = new StableSum();
    this.m_dim = this.m_function.getParameterCount();
  }

  /**
   * Create a new optimization curve fitting individual
   *
   * @return the new optimization curve fitting individual
   */
  protected final OptiIndividual createIndividual() {
    return new OptiIndividual(this.m_dim);
  }

  /**
   * Compute the quality of a given fitting
   *
   * @param params
   *          the fitting, i.e., the parameters of the function to be
   *          fitted
   * @return the fitting quality
   */
  protected final double evaluate(final double[] params) {
    final ParametricUnaryFunction func;
    final DataPoint[] use;
    final int dim;
    final StableSum sum;
    double val;
    DataPoint point;
    int index;

    dim = params.length;
    for (index = dim; (--index) >= 0;) {
      val = params[index];
      if (MathUtils.isFinite(val)) {
        if (Math.abs(val) < Double.MIN_NORMAL) {
          params[index] = 0d;
        }
      } else {
        return Double.POSITIVE_INFINITY;
      }
    }

    func = this.m_function;
    func.canonicalizeParameters(params);

    use = this.m_points;
    for (final DataPoint dp : use) {
      dp.m_error = (Math.abs(dp.output - func.value(dp.input, params))
          / dp.inverseWeight);
    }

    Arrays.sort(use, this.m_sortByError);

    sum = this.m_sum;
    sum.reset();
    for (index = this.m_numberOfPointsUsed; (--index) >= 0;) {
      point = use[index];
      sum.append(point.m_error);
    }

    val = sum.doubleValue();
    if (MathUtils.isFinite(val)) {
      this.register(params, val);
      return val;
    }
    return Double.POSITIVE_INFINITY;
  }

  /**
   * Evaluate an individual fully and set its quality and critical points.
   *
   * @param indi
   *          the individual to be evaluated
   */
  protected final void evaluate(final OptiIndividual indi) {
    indi.quality = this.evaluate(indi.solution);
  }

  /** sort by error, so that the biggest errors come first */
  private static final class __SortByError
      implements Comparator<DataPoint> {
    /** create */
    __SortByError() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final int compare(final DataPoint o1, final DataPoint o2) {
      return EComparison.compareDoubles(o2.m_error, o1.m_error);
    }
  }
}
