package org.optimizationBenchmarking.utils.math.fitting.impl.opti;

import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.math.fitting.spec.FittingJob;
import org.optimizationBenchmarking.utils.math.fitting.spec.FittingJobBuilder;
import org.optimizationBenchmarking.utils.math.fitting.spec.IParameterGuesser;
import org.optimizationBenchmarking.utils.math.fitting.spec.ParametricUnaryFunction;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.StableSum;

/** The base class for optimization-based fitting. */
public class OptiFittingJob extends FittingJob {

  /** the data points */
  protected final DataPoint[] m_points;

  /**
   * the parameter guesser: This guesser is used during the process of
   * finding starting points for the fitting, it may be set to {@code null}
   * afterwards.
   */
  protected IParameterGuesser m_guesser;

  /** the error sul */
  final StableSum m_sum;

  /** the dimension */
  final int m_dim;

  /**
   * create the fitting job
   *
   * @param builder
   *          the job builder
   */
  protected OptiFittingJob(final FittingJobBuilder builder) {
    super(builder);

    final IMatrix points;
    int index;

    points = builder.getPoints();

    this.m_guesser = this.m_function.createParameterGuesser(points);

    index = points.m();

    this.m_points = new DataPoint[index];
    for (; (--index) >= 0;) {
      this.m_points[index] = new DataPoint(//
          points.getDouble(index, 0), points.getDouble(index, 1));
    }

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

    sum = this.m_sum;
    sum.reset();
    for (final DataPoint p : use) {
      sum.append(p.m_error);
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
}
