package org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection;
import org.optimizationBenchmarking.experimentation.data.spec.IFeature;
import org.optimizationBenchmarking.experimentation.data.spec.IParameter;
import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.impl.SemanticComponentUtils;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.TextUtils;
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
        TextUtils.toLowerCase(this.getGroupingMode().toString()) + //
        "_of_" + //$NON-NLS-1$
    this.m_parameter.toString());
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
    next = ETextCase.IN_SENTENCE.appendWords("grouped by", textOut); //$NON-NLS-1$
    textOut.append(' ');
    next = next.appendWord(this.getGroupingMode().toString(), textOut);
    textOut.append(' ');
    next = next.appendWord("of", textOut); //$NON-NLS-1$
    textOut.append(' ');
    PropertyValueGroup._appendNumber(this.m_parameter, textOut);
    return next.nextCase();
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

    next = textCase;

    if (this.m_property instanceof IParameter) {
      textOut.append(' ');
      next = next.appendWords(//
          "all experiments whose value of parameter",//$NON-NLS-1$
          textOut);
    } else {
      if (this.m_property instanceof IFeature) {
        textOut.append(' ');
        next = next
            .appendWords(//
                "all instance run sets belonging to an instance whose value of feature",//$NON-NLS-1$
                textOut);
      } else {
        next = textCase.appendWords(//
            "the data whose", textOut); //$NON-NLS-1$
      }
    }
    textOut.append(' ');
    next = SemanticComponentUtils.printLongAndShortNameIfDifferent(
        this.m_property, textOut, next);
    textOut.append(' ');
    if (next == null) {
      next = ETextCase.IN_SENTENCE;
    }
    next = next.appendWords("fall into the range of the same", textOut); //$NON-NLS-1$
    textOut.append(' ');
    next = next.appendWord(this.getGroupingMode().toString(), textOut);
    textOut.append(' ');
    next = next.appendWord("of", textOut); //$NON-NLS-1$
    textOut.append(' ');
    PropertyValueGroup._appendNumber(this.m_parameter, textOut);
    textOut.append(' ');
    next = next.appendWords("are grouped together.", textOut); //$NON-NLS-1$

    return next.nextAfterSentenceEnd();
  }
}
