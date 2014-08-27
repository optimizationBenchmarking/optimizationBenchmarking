package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.IPlainText;

/** A text class for a code block */
public class CodeBody extends PlainText {

  /**
   * Create a code block text.
   * 
   * @param owner
   *          the owning FSM
   */
  public CodeBody(final Code owner) {
    super(owner, null);
  }

  @Override
  public IPlainText inBraces() {
    // TODO Auto-generated method stub
    return null;
  }
}
