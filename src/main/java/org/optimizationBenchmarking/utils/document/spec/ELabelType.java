package org.optimizationBenchmarking.utils.document.spec;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * The possible types of a label
 */
public enum ELabelType {

  /** a label referencing a section */
  SECTION('s'),

  /** a label referencing a figure section */
  FIGURE('f'),

  /** a label referencing a sub-figure */
  SUBFIGURE('g'),

  /** a label referencing a table */
  TABLE('t'),

  /** an equation */
  EQUATION('e');

  /** the label prefix separator */
  public static final char LABEL_PREFIX_SEPARATOR = '_';

  /** the auto label */
  public static final ILabel AUTO = new _AutoLabel();

  /** the instances */
  public static final ArraySetView<ELabelType> INSTANCES = //
  new ArraySetView<>(ELabelType.values());

  /** the prefix */
  private final char m_prefix;

  /**
   * create the label type
   * 
   * @param p
   *          the prefix char
   */
  ELabelType(final char p) {
    this.m_prefix = p;
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
