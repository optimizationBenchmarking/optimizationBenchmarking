package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.awt.Font;
import java.awt.Stroke;

import org.optimizationBenchmarking.utils.chart.spec.ELineType;
import org.optimizationBenchmarking.utils.chart.spec.ILine2D;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The base class for all 2-dimensional lines
 */
public class Line2D extends DataSeries2D implements ILine2D {

  /** the type has been set */
  static final int FLAG_HAS_TYPE = (DataSeries2D.FLAG_HAS_END << 1);

  /** the line type */
  ELineType m_type;

  /**
   * create the chart item
   * 
   * @param owner
   *          the owner
   * @param id
   *          the id
   */
  protected Line2D(final Chart owner, final int id) {
    super(owner, id);

    this.m_type = ELineType.DEFAULT;
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_HAS_TYPE: {
        append.append("typeSet");break;} //$NON-NLS-1$      
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void setType(final ELineType type) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING, Line2D.FLAG_HAS_TYPE,
        Line2D.FLAG_HAS_TYPE, FSM.FLAG_NOTHING);
    CompiledLine2D._assertType(type);
    this.m_type = type;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  protected synchronized final void onClose() {
    final HierarchicalFSM owner;
    final Chart cb;
    final Font titleFont;
    Stroke stroke;

    this.fsmFlagsAssertTrue(DataSeries.FLAG_HAS_COLOR);

    super.onClose();

    owner = this.getOwner();
    if (owner instanceof Chart) {
      cb = ((Chart) owner);
      if (this.m_title != null) {
        titleFont = ((this.m_titleFont != null) ? this.m_titleFont : //
            cb._getDefaultDataTitleFont());
      } else {
        titleFont = null;
      }
      stroke = this.m_stroke;
      if (stroke == null) {
        stroke = cb._getDefaultDataStroke();
      }

      if (owner instanceof LineChart) {
        ((LineChart) owner)._addLine(new CompiledLine2D(this.m_id,
            this.m_title, titleFont, this.m_color, stroke, this.m_data,
            this.m_hasStart, this.m_startX, this.m_startY, this.m_hasEnd,
            this.m_endX, this.m_endY, this.m_type));
      }
    }
  }
}
