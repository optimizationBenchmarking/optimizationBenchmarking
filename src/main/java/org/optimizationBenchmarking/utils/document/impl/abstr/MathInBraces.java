package org.optimizationBenchmarking.utils.document.impl.abstr;

/**
 * A math class for braces text. Different from the
 * {@link org.optimizationBenchmarking.utils.document.impl.abstr.InBraces
 * text braces}, this class does not print the braces &nbsp; you need to
 * implement that by yourself.
 */
public class MathInBraces extends BasicMath {

  /** the marks */
  private final int m_braceIndex;

  /**
   * Create a text.
   *
   * @param owner
   *          the owning FSM
   */
  protected MathInBraces(final BasicMath owner) {
    super(owner);

    this.m_braceIndex = MathInBraces._getBraceIndex(owner);
  }

  /**
   * Get the brace index
   *
   * @param owner
   *          the owning document element
   * @return the brace index
   */
  static final int _getBraceIndex(final DocumentElement owner) {
    DocumentElement o;
    MathNAryFunction f;

    for (o = owner; o != null; o = o._owner()) {

      if (o instanceof MathInBraces) {
        return (((MathInBraces) o).m_braceIndex + 1);
      }
      if (o instanceof MathNAryFunction) {
        f = ((MathNAryFunction) o);
        if (f.needsBraces()) {
          return (f.getBraceIndex() + 1);
        }
      }
    }

    return 0;
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
    return this.m_braceIndex;
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
