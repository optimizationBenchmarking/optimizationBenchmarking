package org.optimizationBenchmarking.utils.document.spec;

import java.io.Serializable;

import org.optimizationBenchmarking.utils.text.ITextable;

/** A class defining table cells. */
public abstract class TableCellDef implements Serializable, ITextable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the table cell should be left-aligned */
  public static final TableCellDef LEFT = new _TableCellLeft();

  /** the table cell should be centered */
  public static final TableCellDef CENTER = new _TableCellCentered();

  /** the table cell should be right-aligned */
  public static final TableCellDef RIGHT = new _TableCellRight();

  /** a vertical separator */
  public static final TableCellDef VERTICAL_SEPARATOR = new _TableCellVSep();

  /** create a table cell definition */
  TableCellDef() {
    super();
  }

}
