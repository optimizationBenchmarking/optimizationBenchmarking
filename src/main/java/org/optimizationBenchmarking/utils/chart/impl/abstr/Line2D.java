package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import org.optimizationBenchmarking.utils.chart.spec.ELineType;
import org.optimizationBenchmarking.utils.chart.spec.ILine2D;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
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
  @Override
  protected final void process(final Chart owner,
      final ChartDriver driver, final StyleSet styles, final int id,
      final String title, final Font titleFont, final Color color,
      final Stroke stroke) {
    if (owner instanceof LineChart2D) {
      ((LineChart2D) owner)._addLine(new CompiledLine2D(id, title,
          titleFont, color, stroke, this.m_data, this.m_hasStart,
          this.m_startX, this.m_startY, this.m_hasEnd, this.m_endX,
          this.m_endY, this.m_type));
    }
  }

}
