package org.optimizationBenchmarking.utils.chart.impl.abstr;

import org.optimizationBenchmarking.utils.chart.spec.IDataSeries2D;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The base class for all 2-dimensional data series
 */
public class DataSeries2D extends DataSeries implements IDataSeries2D {
  /** the start has been set */
  static final int FLAG_HAS_START = (DataElement.FLAG_HAS_DATA << 1);
  /** the end has been set */
  static final int FLAG_HAS_END = (DataSeries2D.FLAG_HAS_START << 1);

  /** do we have a starting point? */
  boolean m_hasStart;
  /** the start x */
  double m_startX;
  /** the start y */
  double m_startY;

  /** do we have a ending point? */
  boolean m_hasEnd;
  /** the end x */
  double m_endX;
  /** the end y */
  double m_endY;

  /**
   * create the 2-dimensional data
   * 
   * @param owner
   *          the owner
   * @param id
   *          the id
   */
  protected DataSeries2D(final Chart owner, final int id) {
    super(owner, id);
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_HAS_START: {
        append.append("startSet");break;} //$NON-NLS-1$
      case FLAG_HAS_END: {
        append.append("endSet");break;} //$NON-NLS-1$      
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  final void _checkMatrix(final IMatrix matrix) {
    CompiledDataSeries2D._checkMatrix2D(matrix);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void setStart(final double x, final double y) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        DataSeries2D.FLAG_HAS_START, DataSeries2D.FLAG_HAS_START,
        FSM.FLAG_NOTHING);
    this.m_hasStart = true;
    _WrappedMatrix2D._assertCoordinate(x);
    _WrappedMatrix2D._assertCoordinate(y);
    this.m_startX = x;
    this.m_startY = y;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void setEnd(final double x, final double y) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        DataSeries2D.FLAG_HAS_END, DataSeries2D.FLAG_HAS_END,
        FSM.FLAG_NOTHING);
    this.m_hasEnd = true;
    _WrappedMatrix2D._assertCoordinate(x);
    _WrappedMatrix2D._assertCoordinate(y);
    this.m_endX = x;
    this.m_endY = y;
  }
}
