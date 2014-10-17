package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import org.optimizationBenchmarking.utils.graphics.chart.spec.ITitledElement;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The base class for all chart items.
 */
class _TitledElementBuilder extends _ChartElementBuilder implements
    ITitledElement {
  /** the title has been set */
  static final int FLAG_HAS_TITLE = 1;

  /** the title of this element */
  String m_title;

  /**
   * create the chart item
   * 
   * @param owner
   *          the owner
   */
  protected _TitledElementBuilder(final _ChartElementBuilder owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_HAS_TITLE: {
        append.append("titleSet");break;} //$NON-NLS-1$      
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void setTitle(final String title) {
    this.fsmStateAssert(_ChartElementBuilder.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        _TitledElementBuilder.FLAG_HAS_TITLE,
        _TitledElementBuilder.FLAG_HAS_TITLE, FSM.FLAG_NOTHING);
    this.m_title = TextUtils.normalize(title);
  }

}
