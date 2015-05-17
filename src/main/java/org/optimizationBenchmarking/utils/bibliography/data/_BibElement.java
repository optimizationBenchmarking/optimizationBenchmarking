package org.optimizationBenchmarking.utils.bibliography.data;

import java.io.Serializable;

import org.optimizationBenchmarking.utils.IImmutable;
import org.optimizationBenchmarking.utils.hash.HashObject;
import org.optimizationBenchmarking.utils.text.ITextable;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The internal, abstract base class for bibliography elements.
 *
 * @param <T>
 *          the comparable type
 */
abstract class _BibElement<T extends _BibElement<T>> extends HashObject
    implements Serializable, Comparable<T>, ITextable, IImmutable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  _BibElement() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    final MemoryTextOutput sb;
    sb = new MemoryTextOutput();
    this.toText(sb);
    return sb.toString();
  }
}
