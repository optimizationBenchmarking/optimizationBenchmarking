package org.optimizationBenchmarking.utils.math.statistics.parameters;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.ISemanticMathComponent;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.ScalarAggregate;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * <p>
 * This class represents a univariate statistic parameter, that is a
 * property which can be {@link #createSampleAggregate() computed} based on
 * a stream of numerical data. Examples are the
 * </p>
 * <ol>
 * <li>
 * {@link org.optimizationBenchmarking.utils.math.statistics.parameters.Median
 * median},</li>
 * <li>
 * {@link org.optimizationBenchmarking.utils.math.statistics.parameters.ArithmeticMean
 * mean},</li>
 * <li>
 * {@link org.optimizationBenchmarking.utils.math.statistics.parameters.Quantile
 * quantiles},</li>
 * <li>
 * {@link org.optimizationBenchmarking.utils.math.statistics.parameters.Minimum
 * minimum},</li>
 * <li>
 * {@link org.optimizationBenchmarking.utils.math.statistics.parameters.Maximum
 * maximum}</li>
 * <li>
 * {@link org.optimizationBenchmarking.utils.math.statistics.parameters.Variance
 * variance}, or the</li>
 * <li>
 * {@link org.optimizationBenchmarking.utils.math.statistics.parameters.StandardDeviation
 * standard deviation}</li>
 * </ol>
 */
public abstract class StatisticalParameter implements
    ISemanticMathComponent {

  /** the short name */
  private final String m_shortName;
  /** the long name */
  private final String m_longName;

  /**
   * create
   *
   * @param shortName
   *          the short name
   * @param longName
   *          the long name
   */
  StatisticalParameter(final String shortName, final String longName) {
    super();

    this.m_shortName = TextUtils.prepare(shortName);
    this.m_longName = TextUtils.prepare(longName);
  }

  /** {@inheritDoc} */
  @Override
  public ETextCase printShortName(final ITextOutput textOut,
      final ETextCase textCase) {
    return ETextCase.ensure(textCase)
        .appendWord(this.m_shortName, textOut);
  }

  /** {@inheritDoc} */
  @Override
  public void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    out.append(this.m_shortName);
    out.append('(');
    renderer.renderParameter(0, out);
    out.append(')');
  }

  /** {@inheritDoc} */
  @Override
  public void mathRender(final IMath out, final IParameterRenderer renderer) {
    try (final IMath function = out.nAryFunction(this.m_shortName, 1, 1)) {
      renderer.renderParameter(0, out);
    }
  }

  /** {@inheritDoc} */
  @Override
  public ETextCase printLongName(final ITextOutput textOut,
      final ETextCase textCase) {
    return ETextCase.ensure(textCase)
        .appendWords(this.m_longName, textOut);
  }

  /** {@inheritDoc} */
  @Override
  public final String getPathComponentSuggestion() {
    return this.getShortName();
  }

  /**
   * Get the short name of this statistic parameter
   *
   * @return the short name of this statistic parameter
   */
  public final String getShortName() {
    return this.m_shortName;
  }

  /**
   * Get the long name of this statistic parameter
   *
   * @return the long name of this statistic parameter
   */
  public final String getLongName() {
    return this.m_longName;
  }

  /**
   * Create a new
   * {@linkplain org.optimizationBenchmarking.utils.math.statistics.aggregate.ScalarAggregate
   * scalar aggregate} which can be used to compute this parameter from a
   * data sample
   *
   * @return the scalar aggregate used to compute the parameter from a data
   *         sample
   */
  public abstract ScalarAggregate createSampleAggregate();

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {
    return ((o == this) || ((o != null) && //
    (this.getClass() == o.getClass())));
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return HashUtils.hashCode(this.getClass());
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return this.m_shortName;
  }
}
