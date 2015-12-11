package org.optimizationBenchmarking.utils.ml.fitting.spec;

/** A quality evaluation record */
public class FittingEvaluation {

  /** the jacobian */
  public double[][] jacobian;
  /** the residuals */
  public double[] residuals;
  /** the root of the sum of the squared errors */
  public double rsError;
  /** the root of the mean of the squared errors */
  public double rmsError;
  /** the quality value */
  public double quality;

  /** create the quality evaluation record */
  public FittingEvaluation() {
    super();
  }
}
