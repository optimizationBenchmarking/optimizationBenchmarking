package org.optimizationBenchmarking.utils.math.text;

import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.NumericalTypes;

/**
 * A named constant is, well, a constant which can render itself to a maths
 * context.
 */
public abstract class NamedConstant extends BasicNumber implements
    IMathRenderable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** Create the named constant */
  protected NamedConstant() {
    super();
  }

  /**
   * Return {@code true} if this constant will <em>always</em> return
   * values which can be cast to {@code long} without loss of fidelity.
   * {@code false} if there could be {@code double}s or {@code float}s.
   *
   * @return {@code true} if this constant will <em>always</em> return
   *         values which can be cast to {@code long} without loss of
   *         fidelity, {@code false} otherwise
   */
  public boolean isLongArithmeticAccurate() {
    return ((NumericalTypes.getTypes(this) & NumericalTypes.IS_LONG) != 0);
  }

}
