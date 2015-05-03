package org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A set of distinct property value groups.
 */
public final class DistinctValueGroups extends PropertyValueGroups {

  /**
   * create the distinct value groups
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
  DistinctValueGroups(final IProperty property, final _Groups groups,
      final DataSelection unspecified, final Object unspecifiedValue) {
    super(property, groups, unspecified, unspecifiedValue);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public final ArrayListView<DistinctValueGroup> getData() {
    return ((ArrayListView) (this.m_data));
  }

  /** {@inheritDoc} */
  @Override
  final DistinctValueGroup _group(final _Group group) {
    return new DistinctValueGroup(this, group.m_selection, group.m_lower);
  }

  /** {@inheritDoc} */
  @Override
  public final String getPathComponentSuggestion() {
    return ("distinct_" + this.m_property.getName()); //$NON-NLS-1$
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
    if (next == null) {
      next = ETextCase.IN_SENTENCE;
    }

    textOut.append(' ');

    return next.appendWords("grouped by distinct values", //$NON-NLS-1$
        textOut);
  }
}
