package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import org.optimizationBenchmarking.utils.chart.spec.IDataScalar;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.hierarchy.FSM;

/** A scalar data element */
public class DataScalar extends DataElement implements IDataScalar {

  /** the number */
  private Number m_data;

  /**
   * create the scalar data
   * 
   * @param owner
   *          the owner
   * @param id
   *          the id
   */
  protected DataScalar(final Chart owner, final int id) {
    super(owner, id);
  }

  /** {@inheritDoc} */
  @Override
  public final void setData(final double value) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        DataElement.FLAG_HAS_DATA, DataElement.FLAG_HAS_DATA,
        FSM.FLAG_NOTHING);
    if (value != value) {
      throw new IllegalArgumentException(value
          + " is not a permitted scalar data value."); //$NON-NLS-1$
    }
    this.m_data = Double.valueOf(value);
  }

  /** {@inheritDoc} */
  @Override
  public final void setData(final long value) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        DataElement.FLAG_HAS_DATA, DataElement.FLAG_HAS_DATA,
        FSM.FLAG_NOTHING);
    this.m_data = Long.valueOf(value);
  }

  /** {@inheritDoc} */
  @Override
  protected final void process(final Chart owner,
      final ChartDriver driver, final StyleSet styles, final int id,
      final String title, final Font titleFont, final Color color,
      final Stroke stroke) {
    if (owner instanceof PieChart) {
      ((PieChart) owner)._addSlice(new CompiledDataScalar(id, title,
          titleFont, color, stroke, this.m_data));
    }
  }
}
