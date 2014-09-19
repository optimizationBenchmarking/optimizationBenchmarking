package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.text.charset.Braces;

/**
 * A math class for braces text. Different from the
 * {@link org.optimizationBenchmarking.utils.document.impl.abstr.InBraces
 * text braces}, this class does not print the braces &nbsp; you need to
 * implement that by yourself.
 */
public class MathInBraces extends BasicMath {

  /** the marks */
  private static final Braces[] BRACES = { Braces.PARENTHESES,
      Braces.BRACKETS };

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

  /**
   * Get the brace marks
   * 
   * @return the brace marks
   */
  public Braces getBraces() {
    return MathInBraces.BRACES[this.m_braces];
  }

  /**
   * Write the starting brace
   */
  protected void writeBegin() {
    this.m_encoded.append(this.getBraces().getBeginChar());
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void onOpen() {
    super.onOpen();
    this.writeBegin();
  }

  /**
   * Write the ending brace
   */
  protected void writeEnd() {
    this.m_encoded.append(this.getBraces().getEndChar());
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void onClose() {
    this.writeEnd();
    super.onClose();
  }
}
