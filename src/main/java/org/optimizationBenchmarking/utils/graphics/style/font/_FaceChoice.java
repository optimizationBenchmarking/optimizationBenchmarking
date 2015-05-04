package org.optimizationBenchmarking.utils.graphics.style.font;

import org.optimizationBenchmarking.utils.graphics.EFontType;

/** a face choice */
final class _FaceChoice {

  /** the font name */
  final String m_name;

  /** the resource to load the font from */
  final String m_resource;

  /** the font type */
  final EFontType m_type;

  /**
   * Create the face choice
   *
   * @param name
   *          the face choice
   * @param resource
   *          the resource to load the font from
   * @param type
   *          the font type
   */
  _FaceChoice(final String name, final String resource,
      final EFontType type) {
    super();

    if (name == null) {
      throw new IllegalArgumentException(//
          "Font face choice name must not be empty."); //$NON-NLS-1$
    }

    if ((resource != null) ^ (type != null)) {
      throw new IllegalArgumentException(((//
          "If font face choice resource is not null, the font type must not be null either, and vice versa, but you only specified one for face '" //$NON-NLS-1$
          + name) + '\'') + '.');
    }

    this.m_name = name;
    this.m_resource = resource;
    this.m_type = type;
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return this.m_name.hashCode();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (o instanceof _FaceChoice) {
      return ((_FaceChoice) o).equals(this.m_name);
    }
    return false;
  }
}
