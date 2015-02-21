package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.ETableCellDef;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;

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

  /** the equation counter */
  private int m_equationCount;

  /**
   * Create a section body
   * 
   * @param owner
   *          the owning FSM
   */
  protected SectionBody(final Section owner) {
    super(owner);
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

  /** {@inheritDoc} */
  @Override
  public synchronized final Section section(final ILabel useLabel) {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createSection(this, useLabel,
        (++this.m_subsectionCount));
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Table table(final ILabel useLabel,
      final boolean spansAllColumns, final ETableCellDef... cells) {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createTable(this, useLabel, spansAllColumns,
        (++this.m_tableCount), cells);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Figure figure(final ILabel useLabel,
      final EFigureSize size, final String path) {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createFigure(this, useLabel, size, path,
        (++this.m_figureCount));
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final FigureSeries figureSeries(
      final ILabel useLabel, final EFigureSize size, final String path) {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createFigureSeries(this, useLabel, size, path,
        (++this.m_figureCount));
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Code code(final ILabel useLabel,
      final boolean spansAllColumns) {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createCode(this, useLabel, spansAllColumns,//
        (++this.m_codeCount));
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Equation equation(final ILabel useLabel) {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createEquation(this, useLabel,
        (++this.m_equationCount));
  }
}
