package org.optimizationBenchmarking.utils.math.fitting.spec;

import java.util.Arrays;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashObject;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.text.DoubleConstantParameters;
import org.optimizationBenchmarking.utils.text.ITextable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** a fitting result */
public final class FittingResult extends HashObject
    implements Comparable<FittingResult>, ITextable {

  /** the unary function */
  private final ParametricUnaryFunction m_function;

  /** the fitted parameters */
  final double[] m_parameters;

  /** the fitting quality */
  double m_quality;

  /**
   * create the optimization result
   *
   * @param function
   *          the function
   */
  FittingResult(final ParametricUnaryFunction function) {
    super();

    this.m_function = function;
    this.m_parameters = new double[function.getParameterCount()];
    this.m_quality = Double.POSITIVE_INFINITY;
  }

  /**
   * Obtain the fitting quality. The quality values are the better the
   * smaller they are. Quality values coming from the same
   * {@link FunctionFitter} are comparable.
   *
   * @return the fitting quality
   */
  public final double getQuality() {
    return this.m_quality;
  }

  /**
   * Obtain the fitted parameters
   *
   * @return the parameters obtained from the fitting
   */
  public final double[] getFittedParameters() {
    return this.m_parameters;
  }

  /**
   * Get the function whose parameters have been fitted
   *
   * @return the function whose parameters have been fitted
   */
  public final ParametricUnaryFunction getFittedFunction() {
    return this.m_function;
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final FittingResult o) {
    if (o == null) {
      return (-1);
    }
    return EComparison.compareDoubles(this.m_quality, o.m_quality);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    final MemoryTextOutput mto;

    mto = new MemoryTextOutput();
    this.toText(mto);
    return mto.toString();
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    this.m_function.mathRender(textOut, //
        new DoubleConstantParameters(this.m_parameters));
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(//
        HashUtils.hashCode(this.m_function), //
        HashUtils.combineHashes(//
            Arrays.hashCode(this.m_parameters), //
            HashUtils.hashCode(this.m_quality)));
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
          Arrays.equals(this.m_parameters, other.m_parameters) && //
          EComparison.EQUAL.compare(this.m_quality, other.m_quality));
    }
    return false;
  }
}
