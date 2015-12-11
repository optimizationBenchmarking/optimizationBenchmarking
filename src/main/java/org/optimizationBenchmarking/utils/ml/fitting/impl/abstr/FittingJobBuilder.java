package org.optimizationBenchmarking.utils.ml.fitting.impl.abstr;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.ml.fitting.quality.FittingQualityMeasure;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFittingJobBuilder;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFittingQualityMeasure;
import org.optimizationBenchmarking.utils.ml.fitting.spec.ParametricUnaryFunction;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJobBuilder;

/** The fitting job builder */
public class FittingJobBuilder
    extends ToolJobBuilder<FittingJob, FittingJobBuilder>
    implements IFittingJobBuilder {

  /** the tool */
  private final FunctionFitter m_tool;

  /** the {@code x-y}-coordinate pairs */
  private IMatrix m_points;

  /** a function to fit */
  private ParametricUnaryFunction m_function;

  /** the fitting quality measure */
  private IFittingQualityMeasure m_measure;

  /**
   * create
   *
   * @param owner
   *          the owning tool
   */
  protected FittingJobBuilder(final FunctionFitter owner) {
    super();
    this.m_tool = owner;
  }

  /** {@inheritDoc} */
  @Override
  public final FittingJobBuilder setPoints(final IMatrix points) {
    FittingQualityMeasure.validateData(points);
    this.m_points = points;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final FittingJobBuilder setQualityMeasure(
      final IFittingQualityMeasure measure) {
    FittingJobBuilder.validateMeasure(measure);
    this.m_measure = measure;
    return this;
  }

  /**
   * Get the matrix with the points to be fitted.
   *
   * @return the matrix with the points to be fitted
   */
  public final IMatrix getPoints() {
    return this.m_points;
  }

  /**
   * Get the model function to be fitted
   *
   * @return the model function to be fitted
   */
  public final ParametricUnaryFunction getFunction() {
    return this.m_function;
  }

  /**
   * Get the fitting quality measure
   *
   * @return the fitting quality measure
   */
  public final IFittingQualityMeasure getQualityMeasure() {
    return this.m_measure;
  }

  /**
   * Validate the function to fit
   *
   * @param func
   *          the function to fit
   */
  public static final void validateFunction(
      final ParametricUnaryFunction func) {
    if (func == null) {
      throw new IllegalArgumentException(
          "Cannot set function to fit to null."); //$NON-NLS-1$
    }
  }

  /**
   * Validate the fitting quality measure to fit
   *
   * @param measure
   *          the quality measure
   */
  public static final void validateMeasure(
      final IFittingQualityMeasure measure) {
    if (measure == null) {
      throw new IllegalArgumentException(
          "Cannot set fitting quality measure to null."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final FittingJobBuilder setFunctionToFit(
      final ParametricUnaryFunction func) {
    FittingJobBuilder.validateFunction(func);
    this.m_function = func;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  protected void validate() {
    super.validate();
    FittingQualityMeasure.validateData(this.m_points);
    FittingJobBuilder.validateFunction(this.m_function);
    FittingJobBuilder.validateMeasure(this.m_measure);
  }

  /** {@inheritDoc} */
  @Override
  public final FittingJob create() {
    this.validate();
    return this.m_tool.create(this);
  }
}
