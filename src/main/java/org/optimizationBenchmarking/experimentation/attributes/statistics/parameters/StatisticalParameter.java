package org.optimizationBenchmarking.experimentation.attributes.statistics.parameters;

import org.optimizationBenchmarking.experimentation.data.impl.SemanticComponentUtils;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.ISemanticComponent;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.ScalarAggregate;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A statistic parameter.
 */
public abstract class StatisticalParameter implements ISemanticComponent {

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
  public ETextCase appendName(final ITextOutput textOut,
      final ETextCase textCase) {
    return this.appendShortName(textOut, textCase);
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(ITextOutput out, IParameterRenderer renderer) {
    SemanticComponentUtils.mathRender(this.m_shortName, out, renderer);
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(IMath out, IParameterRenderer renderer) {
    SemanticComponentUtils.mathRender(this.m_shortName, out, renderer);
  }

  /**
   * Use this parameter as mathematical function
   *
   * @param math
   *          the mathematical context
   * @return the function
   */
  public final IMath asFunction(final IMath math) {
    return math.nAryFunction(this.getShortName(), 1, 1);
  }

  /**
   * Append the {@link #getShortName() short name} of this parameter to the
   * text output device
   *
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case
   * @return the next text case
   */
  public final ETextCase appendShortName(final ITextOutput textOut,
      final ETextCase textCase) {
    return SemanticComponentUtils.appendName(this.m_shortName, textOut,
        textCase, true);
  }

  /**
   * Append the {@link #getLongName() long name} of this parameter to the
   * text output device
   *
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case
   * @return the next text case
   */
  public ETextCase appendLongName(final ITextOutput textOut,
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
}
