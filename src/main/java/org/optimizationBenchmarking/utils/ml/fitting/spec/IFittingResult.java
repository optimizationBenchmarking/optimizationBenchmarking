package org.optimizationBenchmarking.utils.ml.fitting.spec;

import org.optimizationBenchmarking.utils.ml.fitting.impl.abstr.FunctionFitter;

/** The result of a fitting process. */
public interface IFittingResult {

  /**
   * Obtain the fitting quality. The quality values are the better the
   * smaller they are. Quality values coming from the same
   * {@link FunctionFitter} are comparable.
   *
   * @return the fitting quality
   */
  public abstract double getQuality();

  /**
   * Obtain the fitted parameters. This routine returns a reference to the
   * {@code double[]} array with the results. If you modify that array, you
   * also modify this object. Handle with care.
   *
   * @return the parameters obtained from the fitting
   */
  public abstract double[] getFittedParametersRef();

  /**
   * Get the function whose parameters have been fitted
   *
   * @return the function whose parameters have been fitted
   */
  public abstract ParametricUnaryFunction getFittedFunction();
}
