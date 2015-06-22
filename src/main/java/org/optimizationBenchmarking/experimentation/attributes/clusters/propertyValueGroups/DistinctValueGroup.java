package org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.utils.document.spec.EMathComparison;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IMathName;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.math.text.DefaultParameterRenderer;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A group of data elements belonging to a distinct, singular property
 * value.
 */
public final class DistinctValueGroup extends
    PropertyValueGroup<DistinctValueGroups> {

  /** the singular property value */
  private final Object m_value;

  /**
   * create the distinct value group
   *
   * @param owner
   *          the owning element set
   * @param selection
   *          the data selection
   * @param value
   *          the value
   */
  DistinctValueGroup(final DistinctValueGroups owner,
      final DataSelection selection, final Object value) {
    super(owner, selection);
    if (value == null) {
      throw new IllegalArgumentException(//
          "The distinct property value cannot be null."); //$NON-NLS-1$
    }

    this.m_value = value;
  }

  /**
   * Obtain the property value common to all data elements in this group
   *
   * @return the property value common to all data elements in this group
   */
  public final Object getValue() {
    return this.m_value;
  }

  /** {@inheritDoc} */
  @Override
  public final void appendCriterion(final ITextOutput textOut) {
    textOut.append(this.m_value);
  }

  /** {@inheritDoc} */
  @Override
  public final String getCriterionString() {
    return String.valueOf(this.m_value);
  }

  /** {@inheritDoc} */
  @Override
  public final String getPathComponentSuggestion() {
    return ('=' + String.valueOf(this.m_value));
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer) {
    try (final IMath compare = out.compare(EMathComparison.EQUAL)) {
      this.getOwner().m_property.mathRender(compare, renderer);
      if (this.m_value instanceof Number) {
        try (final IText number = compare.number()) {
          PropertyValueGroup._appendNumber(((Number) (this.m_value)),
              number);
        }
      } else {
        try (final IMathName name = compare.name()) {
          name.append(this.m_value);
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    this.getOwner().m_property.mathRender(out, renderer);
    out.append('=');
    if (this.m_value instanceof Number) {
      PropertyValueGroup._appendNumber(((Number) (this.m_value)), out);
    } else {
      out.append(this.m_value);
    }
  }

  /**
   * Print the name
   *
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case
   * @param nameMode
   *          the name mode
   * @return the next case
   */
  private final ETextCase __printName(final ITextOutput textOut,
      final ETextCase textCase, final int nameMode) {
    final ETextCase use;

    use = this._printSelected(textOut, textCase, nameMode);

    if (textOut instanceof IComplexText) {
      try (final IMath math = ((IComplexText) textOut).inlineMath()) {
        this.mathRender(math, DefaultParameterRenderer.INSTANCE);
      }
    } else {
      this.mathRender(textOut, DefaultParameterRenderer.INSTANCE);
    }

    return use.nextCase();
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printShortName(final ITextOutput textOut,
      final ETextCase textCase) {
    return this.__printName(textOut, textCase, 0);
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printLongName(final ITextOutput textOut,
      final ETextCase textCase) {
    return this.__printName(textOut, textCase, 1);
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    return this.__printName(textOut, textCase, 2);
  }
}
