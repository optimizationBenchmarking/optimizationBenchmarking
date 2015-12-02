package org.optimizationBenchmarking.utils.math.fitting.spec;

import java.util.concurrent.Callable;

import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.StableSum;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJob;

/** The fitting job */
public class FittingJob extends ToolJob
    implements Callable<FittingResult> {

  /** a function to fit */
  protected final ParametricUnaryFunction m_function;

  /** the fitting result */
  protected final FittingResult m_result;

  /** a stable sum, only to be used during solution evaluation */
  protected final StableSum m_sum;

  /** the matrix */
  protected final IMatrix m_data;

  /**
   * The minimum inverse weight: Basically, we consider the absolute values
   * of {@code y}-coordinates of points as their inverse weight. This way,
   * if the estimate for a point with {@code y}-value {@code 10} is
   * {@code 11}, this has the same impact as if the estimate of a point
   * with {@code y}-value {@code -1e-10} is {@code -1.1e-10}. Of course, we
   * need to cater for points with {@code y}-value {@code 0}, which will
   * then have this inverse weight here.
   */
  protected final double m_minInverseWeight;

  /**
   * create the fitting job
   *
   * @param builder
   *          the fitting job builder
   */
  protected FittingJob(final FittingJobBuilder builder) {
    super(builder);

    IMatrix data;
    int index;
    double currentY, minY, minY2;

    FittingJobBuilder._validateFunction(//
        this.m_function = builder.getFunction());
    this.m_result = new FittingResult(this.m_function);

    FittingJobBuilder._validateData(//
        this.m_data = data = builder.getPoints());

    // find the two smallest non-zero absolute y values
    minY = minY2 = Double.POSITIVE_INFINITY;
    for (index = data.m(); (--index) >= 0;) {
      currentY = Math.abs(data.getDouble(index, 1));
      if (FittingJob.__checkInverseWeight(currentY)) {
        if (currentY < minY2) {
          if (currentY < minY) {
            minY = currentY;
          } else {
            if (currentY > minY) {
              minY2 = currentY;
            }
          }
        }
      }
    }

    // define the weight of points with "0" y-coordinate
    findInverseWeight: {
      if (FittingJob.__checkInverseWeight(minY)) {

        if (FittingJob.__checkInverseWeight(minY2)) {
          // Ideally, the inverse weight of the point with 0 y-value
          // behaves to the weight of the point with next-larger (i.e.,
          // smallest non-zero) absolute y value like this point's weight
          // to the one with second-smallest non-zero absolute y value.
          currentY = (minY * (minY / minY2));
          if (FittingJob.__checkInverseWeight(currentY)) {
            break findInverseWeight;
          }
        }

        // If that is not possible, we say it should just be ten percent
        // smaller.
        currentY = (minY * 0.9d);
        if (FittingJob.__checkInverseWeight(currentY)) {
          break findInverseWeight;
        }

        // If that is not possible, we say it should just be one percent
        // smaller.
        currentY = (minY * 0.99d);
        if (FittingJob.__checkInverseWeight(currentY)) {
          break findInverseWeight;
        }

        // If that is not possible, we say it should just be the tiniest
        // bit smaller.
        currentY = Math.nextAfter(minY, Double.NEGATIVE_INFINITY);
        if (FittingJob.__checkInverseWeight(currentY)) {
          break findInverseWeight;
        }

        // If that is not possible (smallest y-coordinate is
        // |Double.MIN_NORMAL|?), we say it should just be the same.
        currentY = minY;
        break findInverseWeight;
      }

      // If that is not possible (all weights are <=Double.MIN_NORMAL????),
      // we say it should just be 1.
      currentY = 1d;
    }

    this.m_minInverseWeight = currentY;
    this.m_sum = new StableSum();
  }

  /** Perform the fitting */
  protected void fit() {
    //
  }

  /**
   * Create a vector with the weights
   *
   * @return the weight vector
   */
  protected final double[] createWeightVector() {
    final double[] res;
    final double minInverseWeight, alternate;
    double y;
    int index;

    index = this.m_data.m();
    res = new double[index];
    minInverseWeight = this.m_minInverseWeight;
    alternate = (1d / minInverseWeight);

    for (; (--index) >= 0;) {
      y = Math.abs(this.m_data.getDouble(index, 1));
      res[index] = ((y <= minInverseWeight) ? alternate : (1d / y));
    }

    return res;
  }

  /**
   * check the given inverse weight
   *
   * @param w
   *          the weight
   * @return {@code true} if it can be used, {@code false} otherwise
   */
  private static final boolean __checkInverseWeight(final double w) {
    return ((w > Double.MIN_NORMAL) && (w < Double.POSITIVE_INFINITY));
  }

  /**
   * Compute the quality of a given fitting.
   *
   * @param params
   *          the fitting, i.e., the parameters of the function to be
   *          fitted
   * @return the fitting quality, i.e., the weighted RMS sqrt(
   *         ((error*weight)^2) / n )
   */
  protected final double evaluate(final double[] params) {
    final ParametricUnaryFunction func;
    final StableSum sum;
    final double minInverseWeight;
    final int length;
    double y, res;
    int index;

    func = this.m_function;
    sum = this.m_sum;
    sum.reset();

    minInverseWeight = this.m_minInverseWeight;
    length = this.m_data.m();
    for (index = length; (--index) >= 0;) {
      y = this.m_data.getDouble(index, 1);
      res = (func.value(this.m_data.getDouble(index, 0), params) - y);
      y = Math.abs(y);
      res /= ((y < minInverseWeight) ? minInverseWeight : y);
      sum.append(res * res);
    }

    res = Math.sqrt(sum.doubleValue() / length);
    if (MathUtils.isFinite(res)) {
      this.register(res, params);
      return res;
    }
    return Double.POSITIVE_INFINITY;
  }

  /**
   * Register a solution
   *
   * @param quality
   *          the solution quality
   * @param params
   *          the parameters
   */
  public final void register(final double quality, final double[] params) {
    if (quality < this.m_result.m_quality) {
      System.arraycopy(params, 0, this.m_result.m_parameters, 0,
          this.m_result.m_parameters.length);
      this.m_result.m_quality = quality;
    }
  }

  /** {@inheritDoc} */
  @Override
  public final FittingResult call() throws IllegalArgumentException {
    Throwable error;
    String message;

    error = null;
    try {
      this.fit();

      if (MathUtils.isFinite(this.m_result.m_quality)) {
        return this.m_result;
      }
    } catch (final Throwable cause) {
      error = cause;
    }

    message = ((("Could not fit the function " + this.m_function) + //$NON-NLS-1$
        " with method ") //$NON-NLS-1$
        + this + '.');
    if (error != null) {
      throw new IllegalArgumentException(message, error);
    }
    throw new IllegalArgumentException(message);
  }
}
