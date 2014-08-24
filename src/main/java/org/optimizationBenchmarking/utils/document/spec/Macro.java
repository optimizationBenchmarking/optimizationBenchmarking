package org.optimizationBenchmarking.utils.document.spec;

import java.io.Serializable;

import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.transformations.NormalCharTransformer;

/**
 * The macro descriptor. Documents may have macros, i.e., shortcuts that
 * represent document elements. These macros can later be invoked at
 * different places in the document. Two macro descriptors are equal if
 * their normalized macro names are equal. Documents cannot contain two
 * equal macros.
 * 
 * @param <BT>
 *          the body type
 */
public abstract class Macro<BT extends IMacroBody> implements Serializable {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the maximum allowed number of parameters for a macro */
  public static final int MAX_PARAMETERS = 9;

  /** the number of parameters */
  private final int m_paramCount;

  /** the name of this macro */
  private final String m_name;

  /**
   * Create a new macro descriptor.
   * 
   * @param name
   *          the macro's name
   * @param paramCount
   *          the parameter counter
   */
  Macro(final String name, final int paramCount) {
    super();
    String s;

    if ((paramCount < 0) || (paramCount > Macro.MAX_PARAMETERS)) {
      throw new IllegalArgumentException(//
          "The number of parameters of a macro is limited to 0 to MAX_PARAMETERS, but " + //$NON-NLS-1$
              paramCount + " was  supplied."); //$NON-NLS-1$
    }

    s = TextUtils.normalize(name);

    if (s == null) {
      throw new IllegalArgumentException(//
          "Macro name must not be empty."); //$NON-NLS-1$
    }

    s = TextUtils.prepare(NormalCharTransformer.INSTANCE
        .transform(s, null));
    if (s == null) {
      throw new IllegalArgumentException(//
          "Macro place must have a non-trivial normalized name."); //$NON-NLS-1$
    }

    this.m_paramCount = paramCount;
    this.m_name = s;
  }

  /**
   * Get the number of macro parameters
   * 
   * @return the number of macro parameters
   */
  public final int getParamCount() {
    return this.m_paramCount;
  }

  /**
   * Get the name of this macro
   * 
   * @return the name of this macro
   */
  public final String getName() {
    return this.m_name;
  }

  /**
   * Print the macro placeholder
   * 
   * @param textOutput
   *          the text output
   */
  public abstract void appendPlaceholderTo(final ITextOutput textOutput);

  /**
   * Define a macro
   * 
   * @param body
   *          the macro body to define the macro into
   */
  public abstract void define(final BT body);

}
