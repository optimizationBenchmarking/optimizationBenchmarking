package org.optimizationBenchmarking.experimentation.attributes.functions;

import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IElementSet;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.parsers.NumberParser;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A constant which can be updated based on a bound of a dimension, i.e.,
 * either an experiment parameter or instance feature.
 */
final class _BoundConstant extends _DataBasedConstant {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the dimension */
  private final IDimension m_dimension;
  /** the end for the upper bound of the given dimension */
  static final String UPPER_BOUND_END = ".max"; //$NON-NLS-1$
  /** the end for the lower bound of the given dimension */
  static final String LOWER_BOUND_END = ".min";//$NON-NLS-1$

  /**
   * {@code true} if we want the upper bound, {@code false} for the lower
   * bound
   */
  private final boolean m_upper;

  /** the default boundary */
  private Number m_defaultBound;

  /**
   * create the dimension bound constant
   *
   * @param dimension
   *          the dimension
   * @param upper
   *          {@code true} if we want the upper bound, {@code false} for
   *          the lower bound
   */
  _BoundConstant(final IDimension dimension, final boolean upper) {
    super();

    if (dimension == null) {
      throw new IllegalArgumentException(//
          "Dimensions cannot be null."); //$NON-NLS-1$
    }
    this.m_dimension = dimension;
    this.m_upper = upper;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isLongArithmeticAccurate() {
    return this.m_dimension.getDataType().isInteger();
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  final Object _getValue(final IDataElement element) {
    if (element instanceof IInstance) {
      return this.__getValue((IInstance) element);
    }
    if (element instanceof IInstanceRuns) {
      return this.__getValue((IInstanceRuns) element);
    }
    if (element instanceof IRun) {
      return this.__getValue((IRun) element);
    }
    if (element instanceof IExperiment) {
      return this.__getValue((IExperiment) element);
    }
    if (element instanceof IExperimentSet) {
      return this.__getValue((IExperimentSet) element);
    }
    if (element instanceof IInstanceSet) {
      return this.__getValue((IInstanceSet) element);
    }
    if (element instanceof IElementSet) {
      return this.__getValue((IElementSet) element);
    }
    if (element instanceof Iterable) {
      return this.__getValue((Iterable) element);
    }
    return this.__getDefaultBound();
  }

  /**
   * Get the default, instance-independent dimension boundary
   *
   * @return the default, instance-independent dimension boundary
   */
  private final Number __getDefaultBound() {
    final NumberParser<Number> parser;

    if (this.m_defaultBound == null) {
      parser = this.m_dimension.getParser();
      if (parser.areBoundsInteger()) {
        if (this.m_upper) {
          this.m_defaultBound = NumericalTypes.valueOf(parser
              .getUpperBoundLong());
        } else {
          this.m_defaultBound = NumericalTypes.valueOf(parser
              .getLowerBoundLong());
        }
      } else {
        if (this.m_upper) {
          this.m_defaultBound = NumericalTypes.valueOf(parser
              .getUpperBoundDouble());
        } else {
          this.m_defaultBound = NumericalTypes.valueOf(parser
              .getLowerBoundDouble());
        }
      }
    }
    return this.m_defaultBound;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return ((this.m_upper ? //
    "upper bound of dimension " : //$NON-NLS-1$
        "lower bound of dimension ") + //$NON-NLS-1$
    this.m_dimension.getName());
  }

  /**
   * Get the value of a feature from an instance
   *
   * @param instance
   *          the instance
   * @return the feature value
   */
  private final Object __getValue(final IInstance instance) {
    Number number;

    if (this.m_upper) {
      number = instance.getUpperBound(this.m_dimension);
    } else {
      number = instance.getLowerBound(this.m_dimension);
    }

    if (number == null) {
      return this.__getDefaultBound();
    }
    return number;
  }

  /**
   * Get the value of a feature from an instance run set
   *
   * @param instanceRuns
   *          the instance runs
   * @return the feature value
   */
  private final Object __getValue(final IInstanceRuns instanceRuns) {
    return this.__getValue(instanceRuns.getInstance());
  }

  /**
   * Get the value of a feature from a run
   *
   * @param run
   *          the run
   * @return the feature value
   */
  private final Object __getValue(final IRun run) {
    return this.__getValue(run.getOwner());
  }

  /**
   * Get the value from a collection of things
   *
   * @param list
   *          the list
   * @return the result
   */
  private final Object __getValue(final Iterable<? extends Object> list) {
    Object value, newVal;

    value = null;
    looper: for (final Object element : list) {
      if (element instanceof IDataElement) {
        newVal = this._getValue((IDataElement) element);
        if (newVal == null) {
          value = null;
          break looper;
        }
        if (value == null) {
          value = newVal;
        } else {
          if (!(EComparison.equals(value, newVal))) {
            throw new IllegalArgumentException(//
                "Dimension boundary value must be the same over the whole experiment, otherwise computation is inconsistent, but we discovered the non-equal values "//$NON-NLS-1$
                    + value + " and " + newVal);//$NON-NLS-1$
          }
        }
      } else {
        throw new IllegalArgumentException(//
            "Cannot deal with collections containing "//$NON-NLS-1$
                + element);
      }
    }

    if (value == null) {
      return this.__getDefaultBound();
    }

    return value;
  }

  /**
   * Get the value of a feature from an experiment
   *
   * @param experiment
   *          the experiment
   * @return the feature value
   */
  private final Object __getValue(final IExperiment experiment) {
    return this.__getValue(experiment.getData());
  }

  /**
   * Get the value of a feature from an experiment set
   *
   * @param experimentSet
   *          the experimentSet
   * @return the feature value
   */
  private final Object __getValue(final IExperimentSet experimentSet) {
    return this.__getValue(experimentSet.getData());
  }

  /**
   * Get the value of a feature from an element set
   *
   * @param elementSet
   *          the elementSet
   * @return the feature value
   */
  private final Object __getValue(final IElementSet elementSet) {
    return this.__getValue(elementSet.getData());
  }

  /**
   * Get the value of a feature from an instance set
   *
   * @param instanceSet
   *          the instanceSet
   * @return the feature value
   */
  private final Object __getValue(final IInstanceSet instanceSet) {
    return this.__getValue(instanceSet.getData());
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    this.m_dimension.mathRender(out, renderer);
    out.append(this.m_upper ? _BoundConstant.UPPER_BOUND_END
        : _BoundConstant.LOWER_BOUND_END);
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer) {
    this.m_dimension.mathRender(out, renderer);
    try (final IText name = out.name()) {
      name.append(this.m_upper ? _BoundConstant.UPPER_BOUND_END
          : _BoundConstant.LOWER_BOUND_END);
    }
  }
}
