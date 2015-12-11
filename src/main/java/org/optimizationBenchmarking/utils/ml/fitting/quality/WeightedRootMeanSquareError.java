package org.optimizationBenchmarking.utils.ml.fitting.quality;

import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.StableSum;
import org.optimizationBenchmarking.utils.ml.fitting.spec.FittingEvaluation;
import org.optimizationBenchmarking.utils.ml.fitting.spec.ParametricUnaryFunction;

/**
 * A quality measure which attempts to give each point the same influence
 * on the fitting outcome. This is achieved by weighting residuals by the
 * inverse of the expected output values. Basically, we consider the
 * absolute values of {@code y}-coordinates of points as their inverse
 * weight. This way, if the estimate for a point with {@code y}-value
 * {@code 10} is {@code 11}, this has the same impact as if the estimate of
 * a point with {@code y}-value {@code -1e-10} is {@code -1.1e-10}. Of
 * course, we need to cater for points with {@code y}-value {@code 0},
 * which will then have this inverse weight here.
 */
public final class WeightedRootMeanSquareError
    extends FittingQualityMeasure {

  /** a stable sum, only to be used during solution evaluation */
  private final StableSum m_sum;

  /**
   * The minimum inverse weight: Basically, we consider the absolute values
   * of {@code y}-coordinates of points as their inverse weight. This way,
   * if the estimate for a point with {@code y}-value {@code 10} is
   * {@code 11}, this has the same impact as if the estimate of a point
   * with {@code y}-value {@code -1e-10} is {@code -1.1e-10}. Of course, we
   * need to cater for points with {@code y}-value {@code 0}, which will
   * then have this inverse weight here.
   */
  private final double m_minInverseWeight;

  /**
   * create the root-mean-square error fitting quality measure
   *
   * @param data
   *          the data matrix
   */
  public WeightedRootMeanSquareError(final IMatrix data) {
    super(data);

    int index;
    double currentY, minY, minY2;

    // find the two smallest non-zero absolute y values
    minY = minY2 = Double.POSITIVE_INFINITY;
    for (index = data.m(); (--index) >= 0;) {
      currentY = Math.abs(data.getDouble(index, 1));
      if (WeightedRootMeanSquareError.__checkInverseWeight(currentY)) {
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
      if (WeightedRootMeanSquareError.__checkInverseWeight(minY)) {

        if (WeightedRootMeanSquareError.__checkInverseWeight(minY2)) {
          // Ideally, the inverse weight of the point with 0 y-value
          // behaves to the weight of the point with next-larger (i.e.,
          // smallest non-zero) absolute y value like this point's weight
          // to the one with second-smallest non-zero absolute y value.
          currentY = (minY * (minY / minY2));
          if (WeightedRootMeanSquareError.__checkInverseWeight(currentY)) {
            break findInverseWeight;
          }
        }

        // If that is not possible, we say it should just be ten percent
        // smaller.
        currentY = (minY * 0.9d);
        if (WeightedRootMeanSquareError.__checkInverseWeight(currentY)) {
          break findInverseWeight;
        }

        // If that is not possible, we say it should just be one percent
        // smaller.
        currentY = (minY * 0.99d);
        if (WeightedRootMeanSquareError.__checkInverseWeight(currentY)) {
          break findInverseWeight;
        }

        // If that is not possible, we say it should just be 0.1 percent
        // smaller.
        currentY = (minY * 0.999d);
        if (WeightedRootMeanSquareError.__checkInverseWeight(currentY)) {
          break findInverseWeight;
        }

        // If that is not possible, we say it should just be the tiniest
        // bit smaller.
        currentY = Math.nextAfter(minY, Double.NEGATIVE_INFINITY);
        if (WeightedRootMeanSquareError.__checkInverseWeight(currentY)) {
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

  /** {@inheritDoc} */
  @Override
  public final double evaluate(final ParametricUnaryFunction model,
      final double[] params) {
    final StableSum sum;
    final double minInverseWeight;
    final int length;
    double y, res;
    int index;

    sum = this.m_sum;
    sum.reset();

    minInverseWeight = this.m_minInverseWeight;
    length = this.m_data.m();
    for (index = length; (--index) >= 0;) {
      y = this.m_data.getDouble(index, 1);
      res = (model.value(this.m_data.getDouble(index, 0), params) - y);
      y = Math.abs(y);
      res /= ((y < minInverseWeight) ? minInverseWeight : y);
      sum.append(res * res);
    }

    res = Math.sqrt(sum.doubleValue() / length);
    return (MathUtils.isFinite(res) ? res : Double.POSITIVE_INFINITY);
  }

  /** {@inheritDoc} */
  @Override
  public final void evaluate(final ParametricUnaryFunction model,
      final double[] parameters, final FittingEvaluation dest) {
    double[][] jacobian;
    double[] residuals;
    final int numSamples, numParams;
    final IMatrix data;
    final double minInverseWeight;
    final StableSum sum;
    double[] jacobianRow;
    double x, expectedY, inverseWeight, residual, squareErrorSum;
    int i, j;

    data = this.m_data;
    minInverseWeight = this.m_minInverseWeight;

    numSamples = data.m();

    residuals = dest.residuals;
    if ((residuals == null) || (residuals.length != numSamples)) {
      dest.residuals = residuals = new double[numSamples];
    }

    numParams = parameters.length;// =model.getParameterCount();
    jacobian = dest.jacobian;
    if ((jacobian == null) || (jacobian.length != numSamples)
        || (jacobian[0].length != numParams)) {
      dest.jacobian = jacobian = new double[numSamples][numParams];
    }
    sum = this.m_sum;

    sum.reset();
    for (i = numSamples; (--i) >= 0;) {
      x = data.getDouble(i, 0);
      expectedY = data.getDouble(i, 1);

      inverseWeight = Math.abs(expectedY);
      if (inverseWeight < minInverseWeight) {
        inverseWeight = minInverseWeight;
      }

      residuals[i] = residual = ((expectedY - model.value(x, parameters))
          / inverseWeight);
      sum.append(residual * residual);

      jacobianRow = jacobian[i];
      model.gradient(x, parameters, jacobianRow);
      for (j = numParams; (--j) >= 0;) {
        jacobianRow[j] /= inverseWeight;
      }
    }

    squareErrorSum = sum.doubleValue();
    if (MathUtils.isFinite(squareErrorSum)) {
      dest.rmsError = dest.quality = //
      Math.sqrt(squareErrorSum / numSamples);
      dest.rsError = Math.sqrt(squareErrorSum);
    } else {
      dest.rmsError = dest.rsError = dest.quality = Double.POSITIVE_INFINITY;
    }
  }
}
