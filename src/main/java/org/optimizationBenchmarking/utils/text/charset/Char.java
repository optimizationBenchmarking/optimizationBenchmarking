package org.optimizationBenchmarking.utils.text.charset;

import java.io.Serializable;

import org.optimizationBenchmarking.utils.IImmutable;

/**
 * The base class for characters
 */
public class Char implements Serializable, Comparable<Char>, IImmutable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the character */
  final char m_char;

  /**
   * create
   *
   * @param chr
   *          the character
   */
  Char(final int chr) {
    super();
    this.m_char = ((char) chr);
  }

  /**
   * Get the escape sequence of this character, or {@code null} if none is
   * defined
   *
   * @return the escape sequence
   */
  public String getEscapeSequence() {
    return null;
  }

  /**
   * Get the character
   *
   * @return the character
   */
  public final char getChar() {
    return this.m_char;
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return this.m_char;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return (o == this);
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final Char o) {
    if (o == null) {
      return (-1);
    }
    return Character.compare(this.m_char, o.m_char);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return String.valueOf(this.m_char);
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  protected final Object writeReplace() {
    return Characters.CHARACTERS.getChar(this.m_char);
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  protected final Object readResolve() {
    return Characters.CHARACTERS.getChar(this.m_char);
  }
}
