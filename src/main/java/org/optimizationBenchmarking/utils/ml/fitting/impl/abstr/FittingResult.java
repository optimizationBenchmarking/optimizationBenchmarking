package org.optimizationBenchmarking.utils.ml.fitting.impl.abstr;

import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.text.DoubleConstantParameters;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFittingResult;
import org.optimizationBenchmarking.utils.ml.fitting.spec.ParametricUnaryFunction;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a fitting result */
public final class FittingResult extends FittingCandidateSolution
    implements IFittingResult {

  /** the unary function */
  private final ParametricUnaryFunction m_function;

  /** the internal hash code */
  private int m_hashCode;

  /**
   * create the optimization result
   *
   * @param function
   *          the function
   */
  FittingResult(final ParametricUnaryFunction function) {
    super(function.getParameterCount());

    this.m_function = function;
  }

  /** {@inheritDoc} */
  @Override
  public final double getQuality() {
    return this.quality;
  }

  /** {@inheritDoc} */
  @Override
  public final double[] getFittedParametersRef() {
    return this.solution;
  }

  /** {@inheritDoc} */
  @Override
  public final ParametricUnaryFunction getFittedFunction() {
    return this.m_function;
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    this.m_function.mathRender(textOut, //
        new DoubleConstantParameters(this.solution));
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    if (this.m_hashCode == 0) {
      this.m_hashCode = HashUtils.combineHashes(//
          HashUtils.hashCode(this.m_function), //
          super.hashCode());
      if (this.m_hashCode == 0) {
        this.m_hashCode = FittingResult.class.hashCode();
      }
    }
    return this.m_hashCode;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final FittingResult other;

    if (o == this) {
      return true;
    }

    if (o instanceof FittingResult) {
      other = ((FittingResult) o);
      return ((other.m_function == this.m_function) && //
          super.equals(o));
    }
    return super.equals(o);
  }
}
