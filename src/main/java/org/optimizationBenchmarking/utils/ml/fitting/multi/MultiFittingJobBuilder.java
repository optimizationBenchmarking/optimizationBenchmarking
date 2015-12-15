package org.optimizationBenchmarking.utils.ml.fitting.multi;

import java.util.Collection;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.ml.fitting.impl.DefaultFunctionFitter;
import org.optimizationBenchmarking.utils.ml.fitting.impl.abstr.FittingJobBuilder;
import org.optimizationBenchmarking.utils.ml.fitting.quality.FittingQualityMeasure;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFittingQualityMeasure;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFunctionFitter;
import org.optimizationBenchmarking.utils.ml.fitting.spec.ParametricUnaryFunction;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJobBuilder;

/**
 * The builder for multi-fitting jobs.
 */
public final class MultiFittingJobBuilder
    extends ToolJobBuilder<MultiFittingJob, MultiFittingJobBuilder> {

  /** the owning fitter */
  private final MultiFunctionFitter m_tool;

  /** the fitters */
  Iterable<IFunctionFitter> m_fitters;

  /** the {@code x-y}-coordinate pairs */
  IMatrix m_points;

  /** a functions to fit */
  Iterable<ParametricUnaryFunction> m_functions;

  /** the fitting quality measure */
  IFittingQualityMeasure m_measure;

  /**
   * create
   *
   * @param owner
   *          the owning tool
   */
  MultiFittingJobBuilder(final MultiFunctionFitter owner) {
    super();
    this.m_tool = owner;
  }

  /**
   * Set the points, i.e., the matrix of data to be fitted
   *
   * @param points
   *          the points to be fitted
   * @return this builder
   */
  public final MultiFittingJobBuilder setPoints(final IMatrix points) {
    FittingQualityMeasure.validateData(points);
    this.m_points = points;
    return this;
  }

  /**
   * Set the quality measure to use
   *
   * @param measure
   *          the quality measure
   * @return this builder
   */
  public final MultiFittingJobBuilder setQualityMeasure(
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
   * Get the model functions to be fitted
   *
   * @return the model functions to be fitted
   */
  public final Iterable<ParametricUnaryFunction> getFunctions() {
    return this.m_functions;
  }

  /**
   * Set the functions to fit
   *
   * @param functions
   *          the functions to fit
   * @return this builder
   */
  public final MultiFittingJobBuilder setFunctionsToFit(
      final Iterable<ParametricUnaryFunction> functions) {
    MultiFittingJobBuilder._validateFunctions(functions);
    this.m_functions = functions;
    return this;
  }

  /**
   * Validate the functions to fit
   *
   * @param functions
   *          the functions
   */
  static final void _validateFunctions(
      final Iterable<ParametricUnaryFunction> functions) {
    if (functions == null) {
      throw new IllegalArgumentException(
          "Function to fit cannot be null."); //$NON-NLS-1$
    }
  }

  /**
   * Get the function fitters to be used
   *
   * @return the function fitters to be used
   */
  public final Iterable<IFunctionFitter> getFitters() {
    return this.m_fitters;
  }

  /**
   * Set the function fitters to be used. If this method is not called,
   * only the
   * {@linkplain org.optimizationBenchmarking.utils.ml.fitting.impl.DefaultFunctionFitter#getAvailableInstance()
   * default fitter} of the system will be applied.
   *
   * @param fitters
   *          the function fitters to be used
   * @return this builder
   */
  public final MultiFittingJobBuilder setFitters(
      final IFunctionFitter... fitters) {
    if (fitters == null) {
      throw new IllegalArgumentException(
          "Function fitters cannot be null."); //$NON-NLS-1$
    }
    if (fitters.length <= 0) {
      throw new IllegalArgumentException(
          "There must be at least one Function fitter.");//$NON-NLS-1$
    }
    return this.setFitters(new ArrayListView<>(fitters));
  }

  /**
   * Set the function fitters to be used. If this method is not called,
   * only the
   * {@linkplain org.optimizationBenchmarking.utils.ml.fitting.impl.DefaultFunctionFitter#getAvailableInstance()
   * default fitter} of the system will be applied.
   *
   * @param fitters
   *          the function fitters to be used
   * @return this builder
   */
  public final MultiFittingJobBuilder setFitters(
      final Iterable<IFunctionFitter> fitters) {
    MultiFittingJobBuilder._validateFitters(fitters);
    this.m_fitters = fitters;
    return this;
  }

  /**
   * Validate the function fitters to be used
   *
   * @param fitters
   *          the function fitters to be used
   */
  @SuppressWarnings("rawtypes")
  static final void _validateFitters(
      final Iterable<IFunctionFitter> fitters) {
    if (fitters == null) {
      throw new IllegalArgumentException(
          "Function fitters cannot be null."); //$NON-NLS-1$
    }
    if (fitters instanceof Collection) {
      if (((Collection) fitters).isEmpty()) {
        throw new IllegalArgumentException(
            "Function fitters set cannot be empty."); //$NON-NLS-1$
      }
    }
  }

  /**
   * Get the fitting quality measure
   *
   * @return the fitting quality measure
   */
  public final IFittingQualityMeasure getQualityMeasure() {
    return this.m_measure;
  }

  /** {@inheritDoc} */
  @Override
  protected void validate() {
    super.validate();
    FittingQualityMeasure.validateData(this.m_points);
    MultiFittingJobBuilder._validateFunctions(this.m_functions);
    MultiFittingJobBuilder._validateFitters(this.m_fitters);
    FittingJobBuilder.validateMeasure(this.m_measure);
  }

  /** {@inheritDoc} */
  @Override
  public final MultiFittingJob create() {
    final ArrayListView<IFunctionFitter> fitters;
    if (this.m_fitters == null) {
      fitters = DefaultFunctionFitter.getAvailableInstance();
      if (fitters.size() > 0) {
        this.setFitters(fitters);
      }
    }
    this.validate();
    return this.m_tool._create(this);
  }

}
