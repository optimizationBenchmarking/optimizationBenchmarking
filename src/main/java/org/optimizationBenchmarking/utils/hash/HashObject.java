package org.optimizationBenchmarking.utils.hash;

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
    int dhc;

    if (this.m_hc != 0) {
      return this.m_hc;
    }

    dhc = this.calcHashCode();
    if (dhc == 0) {
      dhc = 0x4C2F3A75;
    }
    this.m_hc = dhc;
    return dhc;
  }
}
