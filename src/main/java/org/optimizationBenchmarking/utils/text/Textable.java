package org.optimizationBenchmarking.utils.text;

import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * A base class implementing {@link ITextable}. Classes overriding this
 * class must implement either {@link #toText(ITextOutput)} or
 * {@link #toString()}.
 */
public class Textable implements ITextable {

  /** create */
  protected Textable() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    textOut.append(this.toString());
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    final MemoryTextOutput mto;
    mto = new MemoryTextOutput();
    this.toText(mto);
    return mto.toString();
  }
}
