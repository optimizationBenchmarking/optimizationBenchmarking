package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.text.TextUtils;

/** an n-ary mathematical function */
public class MathNAryFunction extends MathFunction {

  /** the function name */
  private final String m_name;

  /** do we need to put a brace around the arguments? */
  private final boolean m_needsBrace;

  /** the brace index */
  private final int m_braceIndex;

  /**
   * Create a absolute value function
   *
   * @param owner
   *          the owning FSM
   * @param name
   *          the function name
   * @param minArity
   *          the minimum number of arguments
   * @param maxArity
   *          the maximum number of arguments
   */
  protected MathNAryFunction(final BasicMath owner, final String name,
      final int minArity, final int maxArity) {
    super(owner, minArity, maxArity);

    this.m_name = TextUtils.prepare(name);
    if (this.m_name == null) {
      throw new IllegalArgumentException(//
          "Name of n-ary mathematical must not be null, empty, or just consist of white space, but '" //$NON-NLS-1$
              + name + "' does.");//$NON-NLS-1$
    }

    this.m_needsBrace = (maxArity > 1);

    if (this.m_needsBrace) {
      this.m_braceIndex = MathInBraces._getBraceIndex(owner);
    } else {
      this.m_braceIndex = 0;
    }
  }

  /**
   * Get the name of the function
   *
   * @return the name of the function
   */
  protected final String getName() {
    return this.m_name;
  }

  /**
   * Get the brace index. This function will only return a valid value if
   * <code>{@link #needsBraces()}==true</code>.
   *
   * @return the brace index
   */
  protected final int getBraceIndex() {
    return this.m_braceIndex;
  }

  /**
   * Do we need to put braces around the function's arguments?
   *
   * @return {@code true} if we need to put braces around the arguments,
   *         {@code false} otherwise
   */
  protected final boolean needsBraces() {
    return this.m_needsBrace;
  }

}
