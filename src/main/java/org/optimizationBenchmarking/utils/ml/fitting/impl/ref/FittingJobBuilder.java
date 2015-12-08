package org.optimizationBenchmarking.utils.ml.fitting.impl.ref;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFittingJobBuilder;
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
    FittingJobBuilder._validateData(points);
    this.m_points = points;
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
   * Validate the data matrix
   *
   * @param points
   *          the matrix' points
   */
  static final void _validateData(final IMatrix points) {
    if (points == null) {
      throw new IllegalArgumentException("Cannot set points to null."); //$NON-NLS-1$
    }
    if (points.m() <= 0) {
      throw new IllegalArgumentException(
          "Cannot set empty set of points.");//$NON-NLS-1$
    }
    if (points.n() != 2) {
      throw new IllegalArgumentException(
          "Point matrix must have n=2, but has n="//$NON-NLS-1$
              + points.n());
    }
  }

  /**
   * Validate the function to fit
   *
   * @param func
   *          the function to fit
   */
  static final void _validateFunction(final ParametricUnaryFunction func) {
    if (func == null) {
      throw new IllegalArgumentException(
          "Cannot set function to fit to null."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final FittingJobBuilder setFunctionToFit(
      final ParametricUnaryFunction func) {
    FittingJobBuilder._validateFunction(func);
    this.m_function = func;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  protected void validate() {
    super.validate();
    FittingJobBuilder._validateData(this.m_points);
    FittingJobBuilder._validateFunction(this.m_function);
  }

  /** {@inheritDoc} */
  @Override
  public final FittingJob create() {
    this.validate();
    return this.m_tool.create(this);
  }
}
