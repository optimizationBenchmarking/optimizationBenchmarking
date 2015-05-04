package org.optimizationBenchmarking.utils.text.transformations;

import java.io.Serializable;
import java.util.Comparator;

/** an internal sorter class */
final class _CharTransformerSorter implements Comparator<char[]>,
Serializable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the sorter */
  static final _CharTransformerSorter SORTER = new _CharTransformerSorter();

  /** create */
  private _CharTransformerSorter() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final int compare(final char[] o1, final char[] o2) {
    return Integer.compare(o1[0], o2[0]);
  }

  // default, automatic serialization replacement and resolve routines for
  // singletons
  //
  /**
   * Write replace: the instance this method is invoked on will be replaced
   * with the singleton instance {@link #SORTER} for serialization, i.e.,
   * when the instance is written with
   * {@link java.io.ObjectOutputStream#writeObject(Object)}.
   *
   * @return the replacement instance (always {@link #SORTER})
   */
  private final Object writeReplace() {
    return _CharTransformerSorter.SORTER;
  }

  /**
   * Read resolve: The instance this method is invoked on will be replaced
   * with the singleton instance {@link #SORTER} after serialization, i.e.,
   * when the instance is read with
   * {@link java.io.ObjectInputStream#readObject()}.
   *
   * @return the replacement instance (always {@link #SORTER})
   */
  private final Object readResolve() {
    return _CharTransformerSorter.SORTER;
  }
}
