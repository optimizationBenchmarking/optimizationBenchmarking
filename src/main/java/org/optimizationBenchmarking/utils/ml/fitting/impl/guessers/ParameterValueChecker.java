package org.optimizationBenchmarking.utils.ml.fitting.impl.guessers;

/** A checker for parameter values. */
public abstract class ParameterValueChecker {

  /** create */
  protected ParameterValueChecker() {
    super();
  }

  /**
   * Check whether a parameter value is valid.
   *
   * @param value
   *          the parameter value
   * @return {@code true} if it is valid, {@code false} otherwise
   */
  public abstract boolean check(final double value);

  /**
   * Choose amongst two parameter values.
   *
   * @param a1
   *          the first number
   * @param a2
   *          the second number
   * @param checker
   *          the parameter value checker
   * @return their mean, if finite, or any of the two numbers which is
   *         finite, or mean
   */
  public static final double choose(final double a1, final double a2,
      final ParameterValueChecker checker) {
    final double med;
    final int cmp;

    if (a1 == a2) {
      if (a1 == 0d) {
        return 0d;
      }
      return a1;
    }

    med = ((0.5d * a1) + (0.5d * a2));
    if (checker.check(med)) {
      return med;
    }
    if (checker.check(a1)) {
      if (checker.check(a2)) {
        cmp = Integer.compare(Math.abs(Math.getExponent(a1)),
            Math.abs(Math.getExponent(a2)));
        if (cmp < 0) {
          return a1;
        }
        if (cmp == 0) {
          if (Math.abs(a1) < Math.abs(a2)) {
            return a1;
          }
        }
        return a2;
      }

      return a1;
    }
    if (checker.check(a2)) {
      return a2;
    }
    return med;
  }
}
