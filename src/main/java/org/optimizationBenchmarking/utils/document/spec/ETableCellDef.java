package org.optimizationBenchmarking.utils.document.spec;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/** A class defining table cells. */
public enum ETableCellDef {

  /** the table cell should be left-aligned */
  LEFT,

  /** the table cell should be centered */
  CENTER,

  /** the table cell should be right-aligned */
  RIGHT,

  /** a vertical separator */
  VERTICAL_SEPARATOR;

  /** the values */
  public static final ArraySetView<ETableCellDef> INSTANCES = new ArraySetView<>(
      ETableCellDef.values());
}
