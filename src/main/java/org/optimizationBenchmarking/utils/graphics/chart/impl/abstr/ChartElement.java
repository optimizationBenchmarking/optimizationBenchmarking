package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import org.optimizationBenchmarking.utils.graphics.chart.spec.IChartElement;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The base class for all chart items.
 */
public class ChartElement extends HierarchicalFSM implements IChartElement {

  /** the state that the element is alive */
  static final int STATE_ALIVE = 0;
  /** the state that the element is dead */
  static final int STATE_DEAD = (ChartElement.STATE_ALIVE + 1);

  /**
   * create the chart item
   * 
   * @param owner
   *          the owner
   */
  protected ChartElement(final ChartElement owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onOpen() {
    super.onOpen();
    this.fsmStateSet(ChartElement.STATE_ALIVE);
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmStateAppendName(final int state,
      final MemoryTextOutput sb) {
    switch (state) {
      case STATE_ALIVE: {
        sb.append("alive");} //$NON-NLS-1$
      case STATE_DEAD: {
        sb.append("dead");} //$NON-NLS-1$
      default: {
        super.fsmStateAppendName(state, sb);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    this.fsmStateSet(ChartElement.STATE_DEAD);
    super.onClose();
  }
}
