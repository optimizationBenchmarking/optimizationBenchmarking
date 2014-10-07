package org.optimizationBenchmarking.utils.document.impl.abstr;

/**
 * A math class for braces text. Different from the
 * {@link org.optimizationBenchmarking.utils.document.impl.abstr.InBraces
 * text braces}, this class does not print the braces &nbsp; you need to
 * implement that by yourself.
 */
public class MathInBraces extends BasicMath {

  /** the marks */
  final int m_braces;

  /**
   * Create a text.
   * 
   * @param owner
   *          the owning FSM
   */
  @SuppressWarnings("resource")
  protected MathInBraces(final BasicMath owner) {
    super(owner);

    DocumentElement o;

    for (o = owner; o != null; o = o._owner()) {

      if (o instanceof MathInBraces) {
        this.m_braces = (((MathInBraces) o).m_braces + 1);
        return;
      }
    }

    this.m_braces = 0;
  }

  /** {@inheritDoc} */
  @Override
  protected BasicMath getOwner() {
    return ((BasicMath) (super.getOwner()));
  }

  /**
   * Get the brace index
   * 
   * @return the brace index
   */
  protected final int getBraceIndex() {
    return this.m_braces;
  }

  /** {@inheritDoc} */
  @Override
  final int minArgs() {
    return 1;
  }

  /** {@inheritDoc} */
  @Override
  final int maxArgs() {
    return 1;
  }

}
