package org.optimizationBenchmarking.utils.config;

import org.optimizationBenchmarking.utils.parsers.Parser;

/**
 * A basic definition which can be parsed
 *
 * @param <T>
 *          the type
 */
public final class SimpleParameter<T> extends Parameter<T> {

  /**
   * create a basic parameter definition
   *
   * @param name
   *          the name
   * @param description
   *          the description
   * @param parser
   *          the parser
   * @param def
   *          the default value
   */
  SimpleParameter(final String name, final String description,
      final Parser<T> parser, final T def) {
    super(name, description, parser, def);
    if (this.m_default != null) {
      this.validate(this.m_default);
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final void validate(final Object value) {
    final T type;
    try {
      type = ((T) value);
    } catch (final ClassCastException cce) {
      throw new IllegalArgumentException("Wrong type of value " + //$NON-NLS-1$
          value + " for parameter " + this.m_name + '.', //$NON-NLS-1$
          cce);
    }
    this.m_parser.validate(type);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final T getDefault() {
    return ((T) (this.m_default));
  }
}
