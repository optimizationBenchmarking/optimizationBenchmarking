package org.optimizationBenchmarking.experimentation.attributes.functions.ecdf;

import org.optimizationBenchmarking.experimentation.attributes.functions.FunctionAttribute;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IElementSet;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.document.impl.FunctionToMathBridge;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Identity;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.processing.ColumnTransformedMatrix;
import org.optimizationBenchmarking.utils.text.ETextCase;
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

  /** the raw ecdf property */
  private final _RawECDF m_raw;

  /** the time transformation */
  private final UnaryFunction m_timeTransform;

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

    this.m_raw = new _RawECDF(timeDim, goalDim, goalValue);
    this.m_timeTransform = timeTransform;
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(//
        HashUtils.hashCode(this.m_raw),//
        HashUtils.hashCode(this.m_timeTransform));
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
      return (EComparison.equals(this.m_raw, other.m_raw) && //
      EComparison.equals(this.m_timeTransform, other.m_timeTransform));
    }

    return false;
  }

  /**
   * Get the time dimension
   * 
   * @return the time dimension
   */
  public final IDimension getTimeDimension() {
    return this.m_raw.m_timeDim;
  }

  /**
   * Get the goal dimension
   * 
   * @return the goal dimension
   */
  public final IDimension getGoalDimension() {
    return this.m_raw.m_goalDim;
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
    return this.m_raw.m_useLongGoal;
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
    return this.m_raw.m_goalValueLong;
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
    return this.m_raw.m_goalValueDouble;
  }

  /** {@inheritDoc} */
  @Override
  protected final IMatrix compute(final IElementSet data) {
    final IMatrix computed;

    computed = this.m_raw.compute(data);
    if (this.m_timeTransform == null) {
      return computed;
    }

    return new ColumnTransformedMatrix(computed, this.m_timeTransform,
        Identity.INSTANCE);
  }

  /** {@inheritDoc} */
  @Override
  public final String getPathComponentSuggestion() {
    final MemoryTextOutput mto;

    mto = new MemoryTextOutput();
    mto.append("ecdf_for_"); //$NON-NLS-1$
    mto.append(this.m_raw.m_goalDim.getPathComponentSuggestion());
    mto.append('_');
    if (this.m_raw.m_useLongGoal) {
      mto.append(this.m_raw.m_goalValueLong);
    } else {
      SimpleNumberAppender.INSTANCE.appendTo(this.m_raw.m_goalValueDouble,
          ETextCase.IN_SENTENCE, mto);
    }
    mto.append("_over_"); //$NON-NLS-1$
    if (this.m_timeTransform != null) {
      mto.append(this.m_timeTransform.toString());
      mto.append('_');
    }
    mto.append(this.m_raw.m_timeDim.getPathComponentSuggestion());

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

    return this.m_raw.m_timeDim.appendName(textOut, next);
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
    next = this.m_raw.m_goalDim.appendName(textOut, next);
    textOut.append(',');
    if (this.m_raw.m_useLongGoal) {
      textOut.append(this.m_raw.m_goalValueLong);
    } else {
      SimpleNumberAppender.INSTANCE.appendTo(this.m_raw.m_goalValueDouble,
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
      this.m_raw.m_timeDim.appendName(inner);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void appendYAxisTitle(final IMath math) {
    try (final IMath ecdf = math.nAryFunction(ECDF.ECDF_SHORT_NAME, 2, 2)) {
      this.m_raw.m_goalDim.appendName(ecdf);
      try (final IText number = ecdf.number()) {
        if (this.m_raw.m_useLongGoal) {
          number.append(this.m_raw.m_goalValueLong);
        } else {
          number.append(this.m_raw.m_goalValueDouble);
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

    next = this.m_raw.m_goalDim.appendName(textOut, next);
    next = ((next != null) ? next : ETextCase.IN_SENTENCE);
    textOut.append(' ');
    next = next.appendWords("with goal", textOut);//$NON-NLS-1$
    textOut.append(' ');

    if (this.m_raw.m_useLongGoal) {
      textOut.append(this.m_raw.m_goalValueLong);
    } else {
      textOut.append(this.m_raw.m_goalValueDouble);
    }

    textOut.append(' ');
    next = next.appendWord("over", textOut);//$NON-NLS-1$
    textOut.append(' ');

    return this.appendXAxisTitle(textOut, next);
  }
}
