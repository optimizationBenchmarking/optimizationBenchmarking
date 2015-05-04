package org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.utils.document.spec.EMathComparison;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IMathName;
import org.optimizationBenchmarking.utils.document.spec.IText;
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
  public final void appendName(final IMath math) {
    try (final IMath compare = math.compare(EMathComparison.EQUAL)) {
      this.getOwner().m_property.appendName(compare);
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
  final ETextCase _appendName(final ITextOutput textOut,
      final ETextCase textCase) {
    final ETextCase next;

    if (textOut instanceof IComplexText) {
      try (final IMath math = ((IComplexText) textOut).inlineMath()) {
        this.appendName(math);
      }
      next = textCase;
    } else {
      next = this.getOwner().m_property.appendName(textOut, textCase);
      textOut.append('=');
      if (this.m_value instanceof Number) {
        PropertyValueGroup._appendNumber(((Number) (this.m_value)),
            textOut);
      } else {
        textOut.append(this.m_value);
      }
    }

    return ((next != null) ? next : ETextCase.IN_SENTENCE);
  }
}
