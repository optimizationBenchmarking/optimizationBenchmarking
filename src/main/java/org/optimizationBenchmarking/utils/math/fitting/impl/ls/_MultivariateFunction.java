package org.optimizationBenchmarking.utils.math.fitting.impl.ls;

import org.apache.commons.math3.fitting.leastsquares.MultivariateJacobianFunction;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.util.Pair;
import org.optimizationBenchmarking.utils.math.fitting.spec.ParametricUnaryFunction;

/** the matrix function */
final class _MultivariateFunction implements MultivariateJacobianFunction {

  /** the x coordinates */
  private final double[] m_x;

  /** the function */
  private final ParametricUnaryFunction m_func;

  /** the temporary array */
  private final double[] m_temp;

  /**
   * create the function
   *
   * @param x
   *          the x-coordinates
   * @param func
   *          the function
   */
  _MultivariateFunction(final double[] x,
      final ParametricUnaryFunction func) {
    super();
    this.m_x = x;
    this.m_func = func;
    this.m_temp = new double[func.getParameterCount()];
  }

  /** {@inheritDoc} */
  @Override
  public final Pair<RealVector, RealMatrix> value(final RealVector point) {
    final double[][] jacobian;
    final double[] temp, values;
    final ParametricUnaryFunction func;
    double x;
    int i;

    temp = this.m_temp;
    for (i = temp.length; (--i) >= 0;) {
      temp[i] = point.getEntry(i);
    }

    func = this.m_func;
    values = this.m_x.clone();
    i = values.length;

    jacobian = new double[i][func.getParameterCount()];

    for (; (--i) >= 0;) {
      x = values[i];
      func.gradient(x, temp, jacobian[i]);
      values[i] = func.value(x, temp);
    }

    return new Pair<RealVector, RealMatrix>(//
        new ArrayRealVector(values, false),//
        new Array2DRowRealMatrix(jacobian, false));
  }
}
