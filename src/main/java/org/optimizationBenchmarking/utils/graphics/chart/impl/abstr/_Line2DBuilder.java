package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import org.optimizationBenchmarking.utils.graphics.chart.spec.ELineType;
import org.optimizationBenchmarking.utils.graphics.chart.spec.ILine2D;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The base class for all 2-dimensional lines
 */
final class _Line2DBuilder extends _DataSeries2DBuilder implements ILine2D {

  /** the type has been set */
  static final int FLAG_HAS_TYPE = (_DataSeries2DBuilder.FLAG_HAS_END << 1);

  /** the line type */
  ELineType m_type;

  /**
   * create the chart item
   * 
   * @param owner
   *          the owner
   */
  _Line2DBuilder(final _LineChartBuilder owner) {
    super(owner);

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
    this.fsmStateAssert(_ChartElementBuilder.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        _Line2DBuilder.FLAG_HAS_TYPE, _Line2DBuilder.FLAG_HAS_TYPE,
        FSM.FLAG_NOTHING);
    Line2D._assertType(type);
    this.m_type = type;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  protected synchronized final void onClose() {
    final HierarchicalFSM owner;

    super.onClose();

    owner = this.getOwner();
    if (owner instanceof _LineChartBuilder) {
      ((_LineChartBuilder) owner)._addLine(new Line2D(this.m_title,
          this.m_color, this.m_stroke, this.m_data, this.m_hasStart,
          this.m_startX, this.m_startY, this.m_hasEnd, this.m_endX,
          this.m_endY, this.m_type));
    }
  }
}
