package org.optimizationBenchmarking.utils.ml.fitting.spec;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.tools.spec.IToolJobBuilder;

/** A builder for function/model fitting jobs. */
public interface IFittingJobBuilder extends IToolJobBuilder {

  /**
   * Set the matrix with the points to be fitted. This must be a
   * {@code m*2} matrix, where the first column contains the inputs of the
   * function to fit and the second column contains the outputs.
   *
   * @param points
   *          the coordinates
   * @return this builder
   */
  public abstract IFittingJobBuilder setPoints(final IMatrix points);

  /**
   * Set the function to fit
   *
   * @param func
   *          the function to fit
   * @return this builder
   */
  public abstract IFittingJobBuilder setFunctionToFit(
      final ParametricUnaryFunction func);

  /**
   * Set the fitting quality measure
   *
   * @param measure
   *          the fitting quality measure
   * @return this builder
   */
  public abstract IFittingJobBuilder setQualityMeasure(
      final IFittingQualityMeasure measure);

  /** {@inheritDoc} */
  @Override
  public abstract IFittingJob create();

  /** {@inheritDoc} */
  @Override
  public abstract IFittingJobBuilder setLogger(final Logger logger);
}
