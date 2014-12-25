package org.optimizationBenchmarking.utils.tools.impl.abstr;

import org.optimizationBenchmarking.utils.hash.HashObject;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.optimizationBenchmarking.utils.tools.spec.ITool;

/**
 * A base class to derive tools from.
 */
public abstract class Tool extends HashObject implements ITool {

  /** create */
  protected Tool() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public boolean canUse() {
    return false;
  }

  /** Call this method in {@link #use()} */
  protected void beforeUse() {
    if (!(this.canUse())) {
      throw new UnsupportedOperationException("Tool '" + //$NON-NLS-1$
          TextUtils.className(this.getClass()) + "' cannot be used."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    final MemoryTextOutput mo;

    mo = new MemoryTextOutput(256);
    this.toText(mo);
    return mo.toString();
  }

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    textOut.append(this.toString());
  }

}
