package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import java.awt.Color;
import java.awt.Stroke;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.graphics.chart.spec.IDataSeries;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The base class for all data series
 */
public class DataSeries extends TitledElement implements IDataSeries {

  /** the color has been set */
  static final int FLAG_HAS_COLOR = (TitledElement.FLAG_TITLED_ELEMENT_BUILDER_MAX << 1);
  /** the stroke has been set */
  static final int FLAG_HAS_STROKE = (DataSeries.FLAG_HAS_COLOR << 1);
  /** the data has been set */
  static final int FLAG_HAS_DATA = (DataSeries.FLAG_HAS_STROKE << 1);

  /** the unique identifier of the series */
  final int m_id;

  /** the color of this element */
  Color m_color;

  /** the stroke of this element */
  Stroke m_stroke;

  /** the data matrix */
  IMatrix m_data;

  /**
   * create the chart item
   * 
   * @param owner
   *          the owner
   * @param id
   *          the id
   */
  protected DataSeries(final Chart owner, final int id) {
    super(owner);

    final Logger logger;

    if (owner == null) {
      throw new IllegalArgumentException(//
          "Chart owning a data series cannot be null."); //$NON-NLS-1$
    }
    this.m_id = id;

    logger = this.getLogger();
    if ((logger != null) && (logger.isLoggable(Level.FINEST))) {
      logger.finest("Beginning to build " + //$NON-NLS-1$
          this.getClass().getSimpleName() + //
          " with id " + id + //$NON-NLS-1$
          " for " + owner._id()); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    final Logger logger;

    logger = this.getLogger();
    if ((logger != null) && (logger.isLoggable(Level.FINEST))) {
      logger.finest("Finished to build " + //$NON-NLS-1$
          this.getClass().getSimpleName() + //
          " with id " + this.m_id + //$NON-NLS-1$
          " for " + this.getOwner()._id()); //$NON-NLS-1$
    }

    super.onClose();
  }

  /** {@inheritDoc} */
  @Override
  protected final Logger getLogger() {
    return this.getOwner().getLogger();
  }

  /** {@inheritDoc} */
  @Override
  protected final Chart getOwner() {
    return ((Chart) (super.getOwner()));
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_HAS_COLOR: {
        append.append("colorSet");break;} //$NON-NLS-1$
      case FLAG_HAS_STROKE: {
        append.append("strokeSet");break;} //$NON-NLS-1$
      case FLAG_HAS_DATA: {
        append.append("dataSet");break;} //$NON-NLS-1$      
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void setColor(final Color color) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        DataSeries.FLAG_HAS_COLOR, DataSeries.FLAG_HAS_COLOR,
        FSM.FLAG_NOTHING);
    if (color == null) {
      throw new IllegalArgumentException(//
          "Cannot set color to null, if you dont want to set it, don't set it at all."); //$NON-NLS-1$
    }
    this.m_color = color;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void setStroke(final Stroke stroke) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        DataSeries.FLAG_HAS_STROKE, DataSeries.FLAG_HAS_STROKE,
        FSM.FLAG_NOTHING);
    if (stroke == null) {
      throw new IllegalArgumentException(//
          "Cannot set stroke to null, if you dont want to set it, don't set it at all."); //$NON-NLS-1$
    }
    this.m_stroke = stroke;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void setData(final IMatrix matrix) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        DataSeries.FLAG_HAS_DATA, DataSeries.FLAG_HAS_DATA,
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
