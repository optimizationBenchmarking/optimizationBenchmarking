package org.optimizationBenchmarking.utils.config;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/**
 * A basic definition which can be parsed
 *
 * @param <T>
 *          the type
 */
public class Parameter<T> extends ConfigurationDefinitionElement {

  /** the parser for the parameter */
  final Parser<T> m_parser;
  /** the default value */
  final Object m_default;

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
  Parameter(final String name, final String description,
      final Parser<T> parser, final Object def) {
    super(name, description);

    final Class<T> clazz;

    if (parser == null) {
      throw new IllegalArgumentException(//
          "Parser for parameter definition cannot be null."); //$NON-NLS-1$
    }
    if (def == null) {
      clazz = parser.getOutputClass();

      if (EPrimitiveType.getPrimitiveType(clazz) != null) {
        throw new IllegalArgumentException(//
            "Default value cannot be null for parameters of type " //$NON-NLS-1$
                + clazz);
      }
    }

    this.m_default = def;
    this.m_parser = parser;
  }

  /**
   * Validate a given value
   *
   * @param value
   *          the value to validate
   * @throws IllegalArgumentException
   *           if the value is not valid
   */
  public void validate(final Object value) {
    // must be implemented by sub-classes
  }

  /**
   * Get the parser
   *
   * @return the parser
   */
  public final Parser<T> getParser() {
    return this.m_parser;
  }

  /**
   * Get the default value
   *
   * @return the default value
   */
  public Object getDefault() {
    return this.m_default;
  }

  /**
   * Does this parameter equal to another parameter?
   *
   * @param param
   *          the other parameter
   * @return {@code true} if this is equal to the other parameter,
   *         {@code false} otherwise
   */
  final boolean _equalsPA(final Parameter<?> param) {
    return (this._equalsDE(param) && //
        EComparison.equals(this.m_default, param.m_default) && //
    EComparison.equals(this.m_parser, param.m_parser));
  }

  /** {@inheritDoc} */
  @Override
  protected int calcHashCode() {
    return HashUtils.combineHashes(//
        super.calcHashCode(),//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.m_default),//
            HashUtils.hashCode(this.m_parser)));
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  public boolean equals(final Object o) {
    return ((o == this) || //
    (((o != null) && (o.getClass() == this.getClass())) && //
    (this._equalsPA((Parameter) o))));
  }
}
