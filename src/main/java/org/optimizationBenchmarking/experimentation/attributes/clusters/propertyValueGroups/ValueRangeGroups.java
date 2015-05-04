package org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** A set of property value range groups. */
public class ValueRangeGroups extends PropertyValueGroups {

  /** the grouping parameterrmation */
  private final Number m_parameter;

  /**
   * create the value range groups
   *
   * @param groups
   *          the groups
   * @param property
   *          the property
   * @param unspecified
   *          a group holding all elements for which the property value was
   *          unspecified, or {@code null} if no such elements exist
   * @param unspecifiedValue
   *          the unspecified value
   */
  ValueRangeGroups(final IProperty property, final _Groups groups,
      final DataSelection unspecified, final Object unspecifiedValue) {
    super(property, groups, unspecified, unspecifiedValue);

    if (groups.m_groupingParameter == null) {
      throw new IllegalArgumentException(//
          "Grouping parameter cannot be null."); //$NON-NLS-1$
    }
    this.m_parameter = groups.m_groupingParameter;
  }

  /** {@inheritDoc} */
  @Override
  final ValueRangeGroup _group(final _Group group) {
    return new ValueRangeGroup(this, group.m_selection,
        ((Number) (group.m_lower)), ((Number) (group.m_upper)),
        group.m_isUpperExclusive);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public final ArrayListView<ValueRangeGroup> getData() {
    return ((ArrayListView) (this.m_data));
  }

  /**
   * Get the grouping parameter.
   *
   * @return the grouping parameter.
   */
  public final Number getInfo() {
    return this.m_parameter;
  }

  /** {@inheritDoc} */
  @Override
  public final String getPathComponentSuggestion() {
    return (this.m_property.getName() + //
        "_grouped_by_" + //$NON-NLS-1$
        this.getGroupingMode().toString().toLowerCase() + //
        "_of_" + //$NON-NLS-1$
        this.m_parameter.toString());
  }

  /** {@inheritDoc} */
  @Override
  public final void appendName(final IMath math) {
    try (final IText text = math.text()) {
      this.appendName(text, ETextCase.IN_SENTENCE);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase appendName(final ITextOutput textOut,
      final ETextCase textCase) {
    ETextCase next;

    next = this.m_property.appendName(textOut, textCase);
    textOut.append(' ');
    if (next == null) {
      next = ETextCase.IN_SENTENCE;
    }
    next = next.appendWords("grouped by", textOut); //$NON-NLS-1$
    textOut.append(' ');
    next = next.appendWord(
        this.getGroupingMode().toString().toLowerCase(), textOut);
    textOut.append(' ');
    next = next.appendWord("of", textOut); //$NON-NLS-1$
    textOut.append(' ');
    PropertyValueGroup._appendNumber(this.m_parameter, textOut);
    return next.nextCase();
  }
}
