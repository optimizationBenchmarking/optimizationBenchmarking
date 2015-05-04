package org.optimizationBenchmarking.utils.document.spec;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * The possible types of a label
 */
public enum ELabelType {

  /** a label referencing a section */
  SECTION('s', "Section"), //$NON-NLS-1$

  /** a label referencing a figure section */
  FIGURE('f', "Figure"), //$NON-NLS-1$,

  /** a label referencing a sub-figure */
  SUBFIGURE('g', "Figure"), //$NON-NLS-1$,

  /** a label referencing a table */
  TABLE('t', "Table"), //$NON-NLS-1$

  /** an equation */
  EQUATION('e', "Equation"), //$NON-NLS-1$

  /** a code */
  CODE('c', "Listing"); //$NON-NLS-1$

  /** the label prefix separator */
  public static final char LABEL_PREFIX_SEPARATOR = '_';

  /** the auto label */
  public static final ILabel AUTO = new _AutoLabel();

  /** the instances */
  public static final ArraySetView<ELabelType> INSTANCES = //
      new ArraySetView<>(ELabelType.values());

  /** the prefix */
  private final char m_prefix;

  /** the name */
  private final String m_name;

  /**
   * create the label type
   *
   * @param p
   *          the prefix char
   * @param name
   *          the name
   */
  ELabelType(final char p, final String name) {
    this.m_prefix = p;
    this.m_name = name;
  }

  /**
   * Get the name of the label type
   *
   * @return the name of the label type
   */
  public final String getName() {
    return this.m_name;
  }

  /**
   * Get the prefix character for labels
   *
   * @return the prefix character for labels
   */
  public final char getLabelPrefixChar() {
    return this.m_prefix;
  }
}
