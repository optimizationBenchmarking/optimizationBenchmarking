package org.optimizationBenchmarking.utils.ml.fitting.models;

import org.optimizationBenchmarking.utils.math.functions.arithmetic.AddN;
import org.optimizationBenchmarking.utils.math.functions.power.Exp;
import org.optimizationBenchmarking.utils.math.functions.power.Ln;
import org.optimizationBenchmarking.utils.math.functions.power.Pow;
import org.optimizationBenchmarking.utils.math.functions.power.Sqrt;
import org.optimizationBenchmarking.utils.ml.fitting.spec.ParametricUnaryFunction;

/** A base class with some utility methods */
abstract class _ModelBase extends ParametricUnaryFunction {

  /** create */
  _ModelBase() {
    super();
  }

  /**
   * compute the square root
   *
   * @param a
   *          the number
   * @return the result
   */
  static final double _sqrt(final double a) {
    return Sqrt.INSTANCE.computeAsDouble(a);
  }

  /**
   * compute the exponent
   *
   * @param a
   *          the number
   * @return the exponent
   */
  static final double _exp(final double a) {
    return Exp.INSTANCE.computeAsDouble(a);
  }

  /**
   * compute the square
   *
   * @param a
   *          the number
   * @return the result
   */
  static final double _sqr(final double a) {
    return a * a;
  }

  /**
   * compute the power
   *
   * @param a
   *          the base
   * @param b
   *          the power
   * @return the result
   */
  static final double _pow(final double a, final double b) {
    final double res;
    res = Pow.INSTANCE.computeAsDouble(a, b);
    return ((res != 0d) ? res : 0d);
  }

  /**
   * compute the natural logarithm
   *
   * @param a
   *          the number
   * @return the logarithm
   */
  static final double _log(final double a) {
    return Ln.INSTANCE.computeAsDouble(a);
  }

  /**
   * sum up some numbers
   *
   * @param summands
   *          the numbers to sum up
   * @return the sum
   */
  static final double _add(final double... summands) {
    return AddN.destructiveSum(summands);
  }
}
