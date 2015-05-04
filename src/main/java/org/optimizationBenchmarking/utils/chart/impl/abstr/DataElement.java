package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.chart.spec.IDataElement;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The base class for all data elements
 */
public class DataElement extends TitledElement implements IDataElement {

  /** the color has been set */
  static final int FLAG_HAS_COLOR = (TitledElement.FLAG_TITLED_ELEMENT_BUILDER_MAX << 1);
  /** the stroke has been set */
  static final int FLAG_HAS_STROKE = (DataElement.FLAG_HAS_COLOR << 1);
  /** the data has been set */
  static final int FLAG_HAS_DATA = (DataElement.FLAG_HAS_STROKE << 1);

  /** the unique identifier of the data element */
  private final int m_id;

  /** the color of this element */
  private Color m_color;

  /** the stroke of this element */
  private Stroke m_stroke;

  /**
   * create the chart item
   *
   * @param owner
   *          the owner
   * @param id
   *          the id
   */
  protected DataElement(final Chart owner, final int id) {
    super(owner);

    final Logger logger;

    if (owner == null) {
      throw new IllegalArgumentException(//
          "Chart owning a data element cannot be null."); //$NON-NLS-1$
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
        DataElement.FLAG_HAS_COLOR, DataElement.FLAG_HAS_COLOR,
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
        DataElement.FLAG_HAS_STROKE, DataElement.FLAG_HAS_STROKE,
        FSM.FLAG_NOTHING);
    if (stroke == null) {
      throw new IllegalArgumentException(//
          "Cannot set stroke to null, if you dont want to set it, don't set it at all."); //$NON-NLS-1$
    }
    this.m_stroke = stroke;
  }

  /**
   * Process the data element
   *
   * @param owner
   *          the owning chart
   * @param driver
   *          the chart driver
   * @param styles
   *          the styles
   * @param id
   *          the id of this data element
   * @param title
   *          the title string
   * @param titleFont
   *          the title font ({@code null} if {@code title==null})
   * @param color
   *          the color
   * @param stroke
   *          the stroke
   */
  protected void process(final Chart owner, final ChartDriver driver,
      final StyleSet styles, final int id, final String title,
      final Font titleFont, final Color color, final Stroke stroke) {
    //
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  protected synchronized final void onClose() {
    final Chart owner;
    final ChartDriver driver;
    final StyleSet styles;
    final Logger logger;
    Font titleFont;
    Stroke stroke;

    this.fsmFlagsAssertTrue(DataElement.FLAG_HAS_COLOR);

    owner = this.getOwner();
    driver = owner.m_driver;
    styles = owner.m_styleSet;

    if (this.m_title != null) {
      titleFont = this.m_titleFont;
      if (titleFont == null) {
        titleFont = driver.getDefaultDataTitleFont(styles);
      } else {
        titleFont = driver.scaleDataTitleFont(titleFont);
      }
    } else {
      titleFont = null;
    }

    stroke = this.m_stroke;
    if (stroke == null) {
      stroke = driver.getDefaultDataStroke(styles);
    }

    this.process(owner, driver, styles, this.m_id, this.m_title,
        titleFont, this.m_color, stroke);

    logger = this.getLogger();
    if ((logger != null) && (logger.isLoggable(Level.FINEST))) {
      logger.finest("Finished to build " + //$NON-NLS-1$
          this.getClass().getSimpleName() + //
          " with id " + this.m_id + //$NON-NLS-1$
          " for " + this.getOwner()._id()); //$NON-NLS-1$
    }

    super.onClose();

  }

}
