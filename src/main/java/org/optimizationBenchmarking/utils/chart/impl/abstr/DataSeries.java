package org.optimizationBenchmarking.utils.chart.impl.abstr;

import org.optimizationBenchmarking.utils.chart.spec.IDataSeries;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/**
 * The base class for all data series
 */
public class DataSeries extends DataElement implements IDataSeries {

  /** the data matrix */
  IMatrix m_data;

  /**
   * create the data series
   *
   * @param owner
   *          the owner
   * @param id
   *          the id
   */
  protected DataSeries(final Chart owner, final int id) {
    super(owner, id);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void setData(final IMatrix matrix) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        DataElement.FLAG_HAS_DATA, DataElement.FLAG_HAS_DATA,
        FSM.FLAG_NOTHING);
    this._checkMatrix(matrix);
    this.m_data = matrix;
  }

  /**
   * check the matrix
   *
   * @param matrix
   *          the matrix
   */
  void _checkMatrix(final IMatrix matrix) {
    CompiledDataSeries._checkMatrix(matrix);
  }

}
