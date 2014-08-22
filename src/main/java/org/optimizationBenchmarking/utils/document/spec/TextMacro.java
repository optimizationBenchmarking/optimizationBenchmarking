package org.optimizationBenchmarking.utils.document.spec;

/**
 * A descriptor for text macros
 */
public abstract class TextMacro extends Macro<ITextMacroBody> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * Create a new text macro descriptor.
   * 
   * @param name
   *          the macro's name
   * @param paramCount
   *          the parameter counter
   */
  protected TextMacro(final String name, final int paramCount) {
    super(name, paramCount);
  }

}
