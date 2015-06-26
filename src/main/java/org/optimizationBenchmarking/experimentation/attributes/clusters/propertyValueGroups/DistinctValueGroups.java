package org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.experimentation.data.spec.IFeature;
import org.optimizationBenchmarking.experimentation.data.spec.IParameter;
import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.impl.SemanticComponentUtils;
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

  /**
   * Print the rest of the name
   *
   * @param textOut
   *          the destination
   * @param textCase
   *          the text case
   * @return the next case
   */
  private final ETextCase __printNameRest(final ITextOutput textOut,
      final ETextCase textCase) {
    ETextCase next;

    textOut.append(' ');
    next = ETextCase.ensure(ETextCase.IN_SENTENCE).appendWords(
        "grouped by", textOut); //$NON-NLS-1$
    textOut.append(' ');
    return next.appendWord("distinct values", textOut); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printLongName(final ITextOutput textOut,
      final ETextCase textCase) {
    return this.__printNameRest(textOut,
        this.m_property.printLongName(textOut, textCase));
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase printShortName(final ITextOutput textOut,
      final ETextCase textCase) {
    return this.__printNameRest(textOut,
        this.m_property.printShortName(textOut, textCase));
  }

  /** {@inheritDoc} */
  @Override
  public ETextCase printDescription(final ITextOutput textOut,
      final ETextCase textCase) {
    ETextCase next;

    next = ETextCase.ensure(textCase);

    if (this.m_property instanceof IParameter) {
      textOut.append(' ');
      next = next
          .appendWords(//
              "the experiments with the sane value of the algorithm parameter are",//$NON-NLS-1$
              textOut);
    } else {
      if (this.m_property instanceof IFeature) {
        textOut.append(' ');
        next = next
            .appendWords(//
                "the instance run sets belonging to instances with the same value of the feature",//$NON-NLS-1$
                textOut);
      } else {
        next = ETextCase.ensure(textCase).appendWords(//
            "the data with the same values of", textOut); //$NON-NLS-1$
      }
    }
    textOut.append(' ');
    next = SemanticComponentUtils.printLongAndShortNameIfDifferent(
        this.m_property, textOut, next);

    textOut.append(' ');
    return ETextCase.ensure(next).appendWords("grouped together.", //$NON-NLS-1$
        textOut).nextAfterSentenceEnd();
  }
}
