package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
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

  /** the number of figures or figure series */
  private int m_figureCount;

  /** the code counter */
  private int m_codeCount;

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
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
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
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createTable(useLabel, spansAllColumns,
        (++this.m_tableCount), cells);
  }

  /**
   * create a new figure
   * 
   * @param useLabel
   *          the label to use
   * @param size
   *          the figure size
   * @param path
   *          a path where the figure should be stored
   * @param index
   *          the figure's index within the section
   * @return the figure
   */
  protected Figure createFigure(final ILabel useLabel,
      final EFigureSize size, final String path, final int index) {
    return new Figure(this, useLabel, size, path, index);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Figure figure(final ILabel useLabel,
      final EFigureSize size, final String path) {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createFigure(useLabel, size, path, (++this.m_figureCount));
  }

  /**
   * Create a figure series
   * 
   * @param useLabel
   *          the label to use, or {@code null} if none is needed
   * @param size
   *          the size for the figures
   * @param path
   *          the relative path, or {@code null} to use an automatically
   *          chosen path
   * @param index
   *          the figure series' index
   * @return the figure series
   */
  protected FigureSeries createFigureSeries(final ILabel useLabel,
      final EFigureSize size, final String path, final int index) {
    return new FigureSeries(this, useLabel, size, path, index);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final FigureSeries figureSeries(
      final ILabel useLabel, final EFigureSize size, final String path) {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createFigureSeries(useLabel, size, path,
        (++this.m_figureCount));
  }

  /**
   * Create a code block
   * 
   * @param useLabel
   *          the label to use, or {@code null} if none is needed
   * @param spansAllColumns
   *          does the table span all columns?
   * @param index
   *          the index of the code block
   * @return the code block
   */
  protected Code createCode(final ILabel useLabel,
      final boolean spansAllColumns, final int index) {
    return new Code(this, useLabel, spansAllColumns, index);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Code code(final ILabel useLabel,
      final boolean spansAllColumns) {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createCode(useLabel, spansAllColumns,//
        (++this.m_codeCount));
  }

  @Override
  public IMath equation(final ILabel useLabel) {
    // TODO Auto-generated method stub
    return null;
  }

}
