package org.optimizationBenchmarking.utils.document.impl.abstr;

/** A text class for a code block */
public class CodeBody extends Text {

  /**
   * Create a code block text.
   * 
   * @param owner
   *          the owning FSM
   */
  public CodeBody(final Code owner) {
    super(owner, null);
  }
}
