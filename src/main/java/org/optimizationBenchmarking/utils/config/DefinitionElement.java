package org.optimizationBenchmarking.utils.config;

import org.optimizationBenchmarking.utils.hash.HashObject;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** A basic definition */
public class DefinitionElement extends HashObject {

  /** the parameter name */
  final String m_name;

  /** the parameter's description */
  final String m_description;

  /**
   * create the base definition
   *
   * @param name
   *          the name
   * @param description
   *          the description
   */
  protected DefinitionElement(final String name, final String description) {
    super();
    this.m_name = DefinitionElement._makeName(name);
    this.m_description = DefinitionElement._makeDescription(description);
  }

  /**
   * Make the name
   *
   * @param name
   *          the name
   * @return the result
   */
  static final String _makeName(final String name) {
    final String res;

    res = TextUtils.prepare(name);
    if (res == null) {
      throw new IllegalArgumentException(//
          "Parameter name must not be null, empty, or just consist of white space, but '" //$NON-NLS-1$
              + name + "' does.");//$NON-NLS-1$
    }
    return res;
  }

  /**
   * Make the description
   *
   * @param description
   *          the description
   * @return the result
   */
  static final String _makeDescription(final String description) {
    final String res;

    res = TextUtils.prepare(description);
    if (res == null) {
      throw new IllegalArgumentException(//
          "Parameter description must not be null, empty, or just consist of white space, but '" //$NON-NLS-1$
              + description + "' does.");//$NON-NLS-1$
    }
    return res;
  }

  /**
   * Get the name of the parameter
   *
   * @return the name of the parameter
   */
  public final String getName() {
    return this.m_name;
  }

  /**
   * Get the description of the parameter
   *
   * @return the description of the parameter
   */
  public final String getDescription() {
    return this.m_description;
  }

  /** {@inheritDoc} */
  @Override
  protected int calcHashCode() {
    return HashUtils.combineHashes(HashUtils.hashCode(this.m_name),
        HashUtils.hashCode(this.m_description));
  }

  /**
   * compare to another base definition
   *
   * @param other
   *          the other base definition to compare with
   * @return {@code true} if the names and descriptions of this and the
   *         other definition are the same, {@code false} otherwise.
   */
  final boolean _equalsDE(final DefinitionElement other) {
    return (this.m_name.equals(other.m_name) && //
    this.m_description.equals(other.m_description));
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {
    return ((o == this) || //
    (((o != null) && (o.getClass() == this.getClass())) && //
    (this._equalsDE((DefinitionElement) o))));
  }
}
