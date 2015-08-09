package org.optimizationBenchmarking.utils.text.tokenizers;

import java.util.NoSuchElementException;

import org.optimizationBenchmarking.utils.collections.iterators.IterableIterator;

/** An internal base class for string tokenizers . */
class _StringIterator extends IterableIterator<String> {

  /** the string */
  final String m_string;

  /** the current substring's end */
  int m_curEnd;

  /** the next string */
  String m_next;

  /**
   * Create the CSV iterator
   *
   * @param string
   *          the string to iterate over
   */
  _StringIterator(final String string) {
    super();

    this.m_string = string;
    this.m_curEnd = (-1);
  }

  /** {@inheritDoc} */
  @Override
  public String next() {
    throw new NoSuchElementException(//
        "Finished iterating over string '" //$NON-NLS-1$
            + this.m_string + "'.");//$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return this.m_string;
  }

  /** {@inheritDoc} */
  @Override
  public final void remove() {
    throw new UnsupportedOperationException(//
        "Cannot remove elements from a string iteration."); //$NON-NLS-1$
  }
}
