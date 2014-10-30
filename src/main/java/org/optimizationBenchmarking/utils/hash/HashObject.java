package org.optimizationBenchmarking.utils.hash;

import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A base class for objects that cache their hash codes. Such an object is
 * guaranteed to have a non-0 hash code. Also, the hash code of such an
 * object will depend on the hash code of its class.
 */
public abstract class HashObject {

  /** the hash code */
  private transient int m_hc;

  /** create */
  protected HashObject() {
    super();
  }

  /**
   * calculate the hash code
   * 
   * @return the hash code
   */
  protected int calcHashCode() {
    return 0;
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    final int chc, dhc;
    final Class<?> clazz;
    int hc;

    if (this.m_hc != 0) {
      return this.m_hc;
    }

    chc = HashUtils.hashCode(clazz = this.getClass());
    dhc = this.calcHashCode();
    hc = HashUtils.combineHashes(chc, dhc);

    if (hc != 0) {
      return (this.m_hc = hc);
    }
    if (chc != 0) {
      return (this.m_hc = chc);
    }
    if (dhc != 0) {
      return (this.m_hc = dhc);
    }
    hc = HashUtils.hashCode(TextUtils.className(clazz));
    if (hc != 0) {
      return (this.m_hc = hc);
    }
    return (this.m_hc = 0x4C2F3A75);
  }
}
