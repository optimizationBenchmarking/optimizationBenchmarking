package org.optimizationBenchmarking.utils.ml.fitting.spec;

/**
 * This interface can compute the fitting quality of a given set of model
 * parameters on a data set.
 */
public interface IFittingQualityMeasure {

  /**
   * Compute the quality of the given model parameters
   *
   * @param model
   *          the model to be assessed
   * @param parameters
   *          the model parameters
   * @return the quality
   */
  public abstract double evaluate(final ParametricUnaryFunction model,
      final double[] parameters);

  /**
   * Compute the quality, Jacobian, residuals, root-mean-square error and
   * root-square error of a given set of model parameters. The member
   * variables of {@code dest} are re-used if they are not {@code null} and
   * of the right dimension. Otherwise they may be overwritten.
   *
   * @param model
   *          the model
   * @param parameters
   *          the model parameters
   * @param dest
   *          the destination record.
   */
  public abstract void evaluate(final ParametricUnaryFunction model,
      final double[] parameters, final FittingEvaluation dest);
}
