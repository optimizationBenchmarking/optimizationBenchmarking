package org.optimizationBenchmarking.experimentation.attributes.functions.ecdf;

import org.optimizationBenchmarking.experimentation.attributes.functions.FunctionAttribute;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IElementSet;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.document.impl.FunctionToMathBridge;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Identity;
import org.optimizationBenchmarking.utils.math.matrix.ColumnTransformedMatrix;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.numbers.SimpleNumberAppender;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The Estimated Cumulative Distribution Function returns, for an
 * experiment or instance runs set, the fraction of runs which have reached
 * a specified goal.
 */
public final class ECDF extends FunctionAttribute<IElementSet> {

  /**
   * the string to be used in text documents to identify the ECDF: {@value}
   */
  public static final String ECDF_SHORT_NAME = "ecdf"; //$NON-NLS-1$

  /** the time dimension */
  private final IDimension m_timeDim;

  /** the goal dimension */
  private final IDimension m_goalDim;

  /** the goal value as {@code double} */
  private final double m_goalValueDouble;
  /** the goal value as {@code long} */
  private final long m_goalValueLong;
  /**
   * should we use {@link #m_goalValueDouble} as goal ({@code true}) or
   * {@link #m_goalValueLong} ({@code false}?
   */
  private final boolean m_useLongGoal;

  /** the time index */
  private final int m_timeIndex;
  /** the goal index */
  private final int m_goalIndex;

  /** the time transformation */
  private final UnaryFunction m_timeTransform;

  /** the source attribute */
  private final ECDF m_source;

  /**
   * Create the ECDF attribute
   * 
   * @param timeDim
   *          the time dimension
   * @param goalDim
   *          the goal dimension
   * @param goalValue
   *          the goal value
   * @param timeTransform
   *          the time transformation
   */
  public ECDF(final IDimension timeDim, final UnaryFunction timeTransform,
      final IDimension goalDim, final Number goalValue) {
    super(EAttributeType.TEMPORARILY_STORED);

    if ((timeDim == null) || (goalDim == null) || (goalValue == null)) {
      throw new IllegalArgumentException(//
          "Cannot compute ECDF for time dimension "//$NON-NLS-1$
              + timeDim + //
              ", goal dimension" //$NON-NLS-1$
              + goalDim + //
              ", and goal value " + goalValue);//$NON-NLS-1$
    }

    this.m_timeDim = timeDim;
    this.m_timeIndex = timeDim.getIndex();

    this.m_goalDim = goalDim;
    this.m_goalIndex = goalDim.getIndex();

    switch (goalDim.getDataType()) {
      case BYTE:
      case SHORT:
      case INT:
      case LONG: {
        this.m_useLongGoal = true;

        if ((NumericalTypes.getTypes(goalValue) & NumericalTypes.IS_LONG) != 0) {
          this.m_goalValueLong = goalValue.longValue();
        } else {
          this.m_goalValueLong = ECDF.__doubleToLong(
              goalValue.doubleValue(), goalDim);
        }

        this.m_goalValueDouble = this.m_goalValueLong;
        break;
      }
      default: {
        this.m_goalValueDouble = goalValue.doubleValue();
        this.m_useLongGoal = false;
        this.m_goalValueLong = ECDF.__doubleToLong(this.m_goalValueDouble,
            goalDim);
      }
    }

    this.m_timeTransform = timeTransform;
    if (timeTransform != null) {
      this.m_source = new ECDF(timeDim, null, goalDim, goalValue);
    } else {
      this.m_source = null;
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.m_timeIndex),//
            HashUtils.hashCode(this.m_goalIndex)),//
        HashUtils.combineHashes(//
            (this.m_useLongGoal//
            ? HashUtils.hashCode(this.m_goalValueLong)//
                : HashUtils.hashCode(this.m_goalValueDouble)),//
            HashUtils.hashCode(this.m_timeTransform)));//
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final ECDF other;

    if (o == this) {
      return true;
    }

    if (o instanceof ECDF) {
      other = ((ECDF) o);
      if ((this.m_timeIndex == other.m_timeIndex) && //
          (this.m_goalIndex == other.m_goalIndex) && //
          (EComparison.equals(this.m_timeTransform, other.m_timeTransform))) {
        if (this.m_useLongGoal) {
          if (other.m_useLongGoal) {
            return (this.m_goalValueLong == other.m_goalValueLong);
          }
        } else {
          if (!(other.m_useLongGoal)) {
            return (EComparison.compareDoubles(this.m_goalValueDouble,
                other.m_goalValueDouble) == 0);
          }
        }
      }
    }

    return false;
  }

  /**
   * convert a {@code double} goal value to a {@code long}
   * 
   * @param d
   *          the goal value
   * @param goalDim
   *          the goal dimension
   * @return the converted value
   */
  private static final long __doubleToLong(final double d,
      final IDimension goalDim) {
    if (d <= Long.MIN_VALUE) {
      return Long.MIN_VALUE;
    }
    if (d >= Long.MAX_VALUE) {
      return Long.MAX_VALUE;
    }

    if (goalDim.getDirection().isIncreasing()) {
      if (d != d) {
        return Long.MAX_VALUE;
      }
      return ((long) (0.5d + Math.ceil(d)));
    }

    if (d != d) {
      return Long.MIN_VALUE;
    }
    return ((long) (0.5d + Math.floor(d)));
  }

  /**
   * Get the time dimension
   * 
   * @return the time dimension
   */
  public final IDimension getTimeDimension() {
    return this.m_timeDim;
  }

  /**
   * Get the goal dimension
   * 
   * @return the goal dimension
   */
  public final IDimension getGoalDimension() {
    return this.m_goalDim;
  }

  /**
   * Is the goal value a {@code long} value?
   * 
   * @return {@code true} if the goal value is a {@code long} value,
   *         {@code false} if it is a {@code double}
   * @see #getGoalValueDouble()
   * @see #getGoalValueLong()
   */
  public final boolean isGoalValueLong() {
    return this.m_useLongGoal;
  }

  /**
   * Get the goal value as a {@code long}. The result of this method will
   * only be accurate if {@link #isGoalValueLong()} returned {@code true}.
   * Otherwise, {@link #getGoalValueDouble()} should be used.
   * 
   * @return the goal value as a {@code long}
   * @see #isGoalValueLong()
   * @see #getGoalValueDouble()
   */
  public final long getGoalValueLong() {
    return this.m_goalValueLong;
  }

  /**
   * Get the goal value as a {@code double}. The result of this method will
   * only be accurate if {@link #isGoalValueLong()} returned {@code false}.
   * Otherwise, {@link #getGoalValueLong()} should be used.
   * 
   * @return the goal value as a {@code double}
   * @see #isGoalValueLong()
   * @see #getGoalValueLong()
   */
  public final double getGoalValueDouble() {
    return this.m_goalValueDouble;
  }

  /**
   * add the experiment set
   * 
   * @param experimentSet
   *          the experimentSet
   * @param list
   *          the list
   */
  private static final void __addExperimentSet(
      final IExperimentSet experimentSet, final _List list) {
    for (final IExperiment experiment : experimentSet.getData()) {
      ECDF.__addExperiment(experiment, list);
    }
  }

  /**
   * add the experiment
   * 
   * @param experiment
   *          the experiment
   * @param list
   *          the list
   */
  private static final void __addExperiment(final IExperiment experiment,
      final _List list) {
    for (final IInstanceRuns runs : experiment.getData()) {
      ECDF.__addInstanceRuns(runs, list);
    }
  }

  /**
   * add the instance runs
   * 
   * @param runs
   *          the runs
   * @param list
   *          the list
   */
  private static final void __addInstanceRuns(final IInstanceRuns runs,
      final _List list) {
    for (final IRun run : runs.getData()) {
      list._addRun(run);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final IMatrix compute(final IElementSet data) {
    final _List list;
    IMatrix computed;

    if (this.m_source != null) {
      computed = this.m_source.get(data);
    } else {
      if (this.m_goalDim.getDataType().isFloat()) {
        if (this.m_timeDim.getDataType().isInteger()) {
          list = new _LongTimeDoubleGoal(this.m_timeDim, this.m_goalIndex,
              this.m_goalValueDouble);
        } else {
          list = new _DoubleTimeDoubleGoal(this.m_timeDim,
              this.m_goalIndex, this.m_goalValueDouble);
        }
      } else {
        if (this.m_timeDim.getDataType().isInteger()) {
          list = new _LongTimeLongGoal(this.m_timeDim, this.m_goalIndex,
              this.m_goalValueLong);
        } else {
          list = new _DoubleTimeLongGoal(this.m_timeDim, this.m_goalIndex,
              this.m_goalValueLong);
        }
      }

      if (data instanceof IExperimentSet) {
        ECDF.__addExperimentSet(((IExperimentSet) data), list);
      } else {
        if (data instanceof IExperiment) {
          ECDF.__addExperiment(((IExperiment) data), list);
        } else {
          if (data instanceof IInstanceRuns) {
            ECDF.__addInstanceRuns(((IInstanceRuns) data), list);
          } else {
            if (data instanceof IRun) {
              list._addRun((IRun) data);
            } else {
              throw new IllegalArgumentException(//
                  "ECDF can only be computed over an IExperimentSet, IExperiment, IInstanceRuns, or IRun, but you provided " //$NON-NLS-1$
                      + ((data != null)//
                      ? (TextUtils.className(data.getClass()) + '.')//
                          : "null."));//$NON-NLS-1$
            }
          }
        }
      }

      computed = list._toMatrix();
    }

    if (this.m_timeTransform != null) {
      computed = new ColumnTransformedMatrix(computed,
          this.m_timeTransform, Identity.INSTANCE);
    }

    return computed;
  }

  /** {@inheritDoc} */
  @Override
  public final String getPathComponentSuggestion() {
    final MemoryTextOutput mto;

    mto = new MemoryTextOutput();
    mto.append("ecdf_for_"); //$NON-NLS-1$
    mto.append(this.m_goalDim.getPathComponentSuggestion());
    mto.append('_');
    if (this.m_useLongGoal) {
      mto.append(this.m_goalValueLong);
    } else {
      SimpleNumberAppender.INSTANCE.appendTo(this.m_goalValueDouble,
          ETextCase.IN_SENTENCE, mto);
    }
    mto.append("_over_"); //$NON-NLS-1$
    if (this.m_timeTransform != null) {
      mto.append(this.m_timeTransform.toString());
      mto.append('_');
    }
    mto.append(this.m_timeDim.getPathComponentSuggestion());

    return mto.toString();
  }

  /** {@inheritDoc} */
  @Override
  protected final ETextCase appendXAxisTitlePlain(
      final ITextOutput textOut, final ETextCase textCase) {
    final ETextCase next;

    next = ((textCase != null) ? textCase.nextCase()
        : ETextCase.IN_SENTENCE);

    if (this.m_timeTransform != null) {
      textOut.append(this.m_timeTransform.toString());
      textOut.append(' ');
    }

    return this.m_timeDim.appendName(textOut, next);
  }

  /** {@inheritDoc} */
  @Override
  protected final ETextCase appendYAxisTitlePlain(
      final ITextOutput textOut, final ETextCase textCase) {
    ETextCase next;

    next = ((textCase != null) ? textCase.nextCase()
        : ETextCase.IN_SENTENCE);

    textOut.append(ECDF.ECDF_SHORT_NAME);
    textOut.append('(');
    next = this.m_goalDim.appendName(textOut, next);
    textOut.append(',');
    if (this.m_useLongGoal) {
      textOut.append(this.m_goalValueLong);
    } else {
      SimpleNumberAppender.INSTANCE.appendTo(this.m_goalValueDouble,
          ETextCase.IN_SENTENCE, textOut);
    }
    textOut.append(')');
    return next;
  }

  /** {@inheritDoc} */
  @Override
  public final void appendXAxisTitle(final IMath math) {
    try (final IMath inner = FunctionToMathBridge.bridge(
        this.m_timeTransform, math)) {
      this.m_timeDim.appendName(inner);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void appendYAxisTitle(final IMath math) {
    try (final IMath ecdf = math.nAryFunction(ECDF.ECDF_SHORT_NAME, 2, 2)) {
      this.m_goalDim.appendName(ecdf);
      try (final IText number = ecdf.number()) {
        if (this.m_useLongGoal) {
          number.append(this.m_goalValueLong);
        } else {
          number.append(this.m_goalValueDouble);
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase appendLongName(final ITextOutput textOut,
      final ETextCase textCase) {
    ETextCase next;

    next = ((textCase != null) ? textCase : ETextCase.IN_SENTENCE);
    next = next.appendWords(//
        "estimated cumulative distribution function for",//$NON-NLS-1$
        textOut);
    textOut.append(' ');

    next = this.m_goalDim.appendName(textOut, next);
    next = ((textCase != null) ? textCase : ETextCase.IN_SENTENCE);
    textOut.append(' ');
    next = next.appendWords(//
        "with goal", textOut);//$NON-NLS-1$
    textOut.append(' ');

    if (this.m_useLongGoal) {
      textOut.append(this.m_goalValueLong);
    } else {
      textOut.append(this.m_goalValueDouble);
    }

    textOut.append(' ');
    next = next.appendWord(//
        "over", textOut);//$NON-NLS-1$
    textOut.append(' ');

    return this.appendXAxisTitle(textOut, next);
  }
}
