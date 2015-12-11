package org.optimizationBenchmarking.utils.collections.maps;

import java.io.Serializable;
import java.util.Map;

import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.Textable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A basic implementation of the {@link java.util.Map.Entry} interface
 * which leaves all value-related information abstract but provides methods
 * for hash codes and equality checking.
 *
 * @param <K>
 *          the key type
 * @param <V>
 *          the value type
 */
public abstract class BasicMapEntry<K, V> extends Textable
    implements Map.Entry<K, V>, Serializable, Cloneable {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  protected BasicMapEntry() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public K getKey() {
    return ((K) this);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public V getValue() {
    return ((V) this);
  }

  /** {@inheritDoc} */
  @Override
  public V setValue(final V value) {
    throw new UnsupportedOperationException();
  }

  /**
   * Set the key of this association
   *
   * @param key
   *          the key
   */
  protected void setKey(final K key) {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return (HashUtils.hashCode(this.getKey()) ^ //
        HashUtils.hashCode(this.getValue()));
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  public boolean equals(final Object o) {
    final Map.Entry e;
    Object o1;

    if (o == this) {
      return true;
    }

    if (o instanceof Map.Entry) {
      e = ((Map.Entry) o);

      return ((((o1 = this.getKey()) == null)//
          ? (e.getKey() == null)//
          : (o1.equals(e.getKey()))) && //
          (((o1 = this.getValue()) == null)//
              ? (e.getValue() == null)//
              : (o1.equals(e.getValue()))));
    }

    return false;
  }

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    textOut.append(this.getKey());
    textOut.append('=');
    textOut.append(this.getValue());
  }

  /** {@inheritDoc} */
  @Override
  protected Object clone() {
    try {
      return super.clone();
    } catch (final CloneNotSupportedException t) {
      RethrowMode.AS_RUNTIME_EXCEPTION.rethrow(//
          "Error while cloning BasicMapEntry.", //$NON-NLS-1$
          true, t);
      return null;// we will never get here
    }
  }
}
