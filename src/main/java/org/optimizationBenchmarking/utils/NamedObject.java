package org.optimizationBenchmarking.utils;

import java.io.Serializable;

import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * This is the base class for all named objects. Classes which are
 * configurable and/or algorithm or benchmark classes should inherit from
 * this class. All instances of this class have a unique identifiable name.
 * Also, they are cloneable by default.
 */
public class NamedObject implements Serializable, Cloneable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the name */
  private final String m_name;

  /**
   * instantiate
   * 
   * @param name
   *          the name
   */
  public NamedObject(final String name) {
    super();
    final String nam;

    nam = TextUtils.prepare(name);
    if (nam == null) {
      throw new IllegalArgumentException("Name must not be null."); //$NON-NLS-1$
    }
    this.m_name = nam.intern();
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return this.m_name;
  }

  /**
   * get the name
   * 
   * @return the name
   */
  public final String name() {
    return this.m_name;
  }

  /** {@inheritDoc} */
  @Override
  public NamedObject clone() {
    try {
      return ((NamedObject) (super.clone()));
    } catch (final Throwable t) {
      throw new RuntimeException(t);
    }
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return this.m_name.hashCode();
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof NamedObject) {
      return this.m_name.equals(((NamedObject) o).m_name);
    }
    return false;
  }
}
