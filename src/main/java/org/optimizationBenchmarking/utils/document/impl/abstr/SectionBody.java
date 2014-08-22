package org.optimizationBenchmarking.utils.document.impl.abstr;

import java.net.URI;

import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.IFigure;
import org.optimizationBenchmarking.utils.document.spec.IFigureSeries;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
import org.optimizationBenchmarking.utils.document.spec.TableCellDef;

/**
 * A section body.
 */
public class SectionBody extends StructuredText implements ISectionBody {

  /** the sub-section counter */
  private int m_subsectionCount;

  /** the table counter */
  private int m_tableCount;

  /**
   * Create a section body
   * 
   * @param owner
   *          the owning FSM
   */
  protected SectionBody(final Section owner) {
    super(owner, DocumentPart._plain(owner));
  }

  /**
   * Get the owning section
   * 
   * @return the owning section
   */
  @Override
  protected Section getOwner() {
    return ((Section) (super.getOwner()));
  }

  /**
   * Create the section
   * 
   * @param useLabel
   *          the label to use
   * @param index
   *          the index
   * @return the section
   */
  protected Section createSection(final ILabel useLabel, final int index) {
    return new Section(this, useLabel, index);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Section section(final ILabel useLabel) {
    this.fsmFlagsAssertTrue(DocumentElement.STATE_ALIFE);
    return this.createSection(useLabel, (++this.m_subsectionCount));
  }

  /**
   * Create a table
   * 
   * @param useLabel
   *          the label to use
   * @param spansAllColumns
   *          does the table span all columns?
   * @param index
   *          the table's index
   * @param cells
   *          the cell definition
   * @return the table
   */
  protected Table createTable(final ILabel useLabel,
      final boolean spansAllColumns, final int index,
      final TableCellDef... cells) {
    return new Table(this, useLabel, spansAllColumns, index, cells);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Table table(final ILabel useLabel,
      final boolean spansAllColumns, final TableCellDef... cells) {
    this.fsmFlagsAssertTrue(DocumentElement.STATE_ALIFE);
    return this.createTable(useLabel, spansAllColumns,
        (++this.m_tableCount), cells);
  }

  @Override
  public IFigure figure(final ILabel useLabel, final EFigureSize size,
      final URI path) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IFigureSeries figureSeries(final ILabel useLabel,
      final EFigureSize size, final URI path) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IMath equation(final ILabel useLabel) {
    // TODO Auto-generated method stub
    return null;
  }
}
