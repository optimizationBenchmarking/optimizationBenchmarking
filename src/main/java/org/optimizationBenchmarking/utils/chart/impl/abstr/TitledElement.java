package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.awt.Font;

import org.optimizationBenchmarking.utils.chart.spec.ITitledElement;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The base class for all chart items.
 */
public class TitledElement extends ChartElement implements ITitledElement {
  /** the title has been set */
  private static final int FLAG_HAS_TITLE = 1;
  /** the title font has been set */
  private static final int FLAG_HAS_TITLE_FONT = (TitledElement.FLAG_HAS_TITLE << 1);
  /** the maximum allocated flags */
  static final int FLAG_TITLED_ELEMENT_BUILDER_MAX = TitledElement.FLAG_HAS_TITLE_FONT;

  /** the title of this element */
  String m_title;
  /** the title font of this element */
  Font m_titleFont;

  /**
   * create the chart item
   *
   * @param owner
   *          the owner
   */
  protected TitledElement(final ChartElement owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_HAS_TITLE: {
        append.append("titleSet");break;} //$NON-NLS-1$
      case FLAG_HAS_TITLE_FONT: {
        append.append("titleFontSet");break;} //$NON-NLS-1$
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void setTitle(final String title) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        TitledElement.FLAG_HAS_TITLE, TitledElement.FLAG_HAS_TITLE,
        FSM.FLAG_NOTHING);
    this.m_title = TextUtils.normalize(title);
    if (this.m_title == null) {
      throw new IllegalArgumentException(//
          "Title cannot be set to null or empty string, but was set to '"//$NON-NLS-1$
              + title + //
              "'. If you don't want to specify a title, don't set it."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void setTitleFont(final Font titleFont) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(TitledElement.FLAG_HAS_TITLE,
        TitledElement.FLAG_HAS_TITLE_FONT,
        TitledElement.FLAG_HAS_TITLE_FONT, FSM.FLAG_NOTHING);
    if (titleFont == null) {
      throw new IllegalArgumentException(//
          "Title font cannot be set to null. If you don't want to specify a title font, don't set it."); //$NON-NLS-1$
    }
    this.m_titleFont = titleFont;
  }

}
