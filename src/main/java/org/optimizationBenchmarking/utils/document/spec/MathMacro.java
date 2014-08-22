package org.optimizationBenchmarking.utils.document.spec;

/**
 * A descriptor for math macros
 */
public abstract class MathMacro extends Macro<IMathMacroBody> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * Create a new math macro descriptor.
   * 
   * @param name
   *          the macro's name
   * @param paramCount
   *          the parameter counter
   */
  protected MathMacro(final String name, final int paramCount) {
    super(name, paramCount);
  }

}
