package org.optimizationBenchmarking.utils.config;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.parsers.Parser;

/**
 * An instance parameter.
 *
 * @param <T>
 *          the instance parameter
 */
public final class InstanceParameter<T> extends Parameter<T> {

  /** the pre-defined choices */
  private final ArrayListView<DefinitionElement> m_choices;

  /** are more choices allowed */
  private final boolean m_allowsMore;

  /**
   * create an instance parameter definition
   *
   * @param name
   *          the name
   * @param description
   *          the description
   * @param parser
   *          the parser
   * @param def
   *          the default value
   * @param choices
   *          the default choices
   * @param allowsMore
   *          are more choices allowed?
   */
  InstanceParameter(final String name, final String description,
      final Parser<T> parser, final String def,
      final ArrayListView<DefinitionElement> choices,
      final boolean allowsMore) {
    super(name, description, parser, def);

    if (choices == null) {
      throw new IllegalArgumentException(//
          "The list of choices cannot be null."); //$NON-NLS-1$
    }

    this.m_choices = choices;
    this.m_allowsMore = allowsMore;

    if (this.m_default != null) {
      this.validate(this.m_default);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void validate(final Object value) {
    final String str;

    if (value instanceof String) {

      if (this.m_allowsMore) {
        return;
      }

      str = ((String) value);
      for (final DefinitionElement def : this.m_choices) {
        if (def.m_name.equalsIgnoreCase(str)) {
          return;
        }
      }

      throw new IllegalArgumentException("String '" + str + //$NON-NLS-1$
          "' matches none of the possible choices.");//$NON-NLS-1$
    }
    throw new IllegalArgumentException(//
        "Default value of instance parameter must be an instance of String, but "//$NON-NLS-1$
            + value + " is not."); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final String getDefault() {
    return ((String) (this.m_default));
  }

  /**
   * Get the choices.
   *
   * @return the choices
   */
  public final ArrayListView<DefinitionElement> getChoices() {
    return this.m_choices;
  }

  /**
   * Are more than the pre-defined choices allowed?
   *
   * @return {@code true} if more than the pre-defined choices are allowed,
   *         {@code false} otherwise
   */
  public final boolean allowsMore() {
    return this.m_allowsMore;
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(//
        super.calcHashCode(),//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.m_allowsMore),//
            HashUtils.hashCode(this.m_choices)));
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  public final boolean equals(final Object o) {
    final InstanceParameter<?> p;
    if (o == this) {
      return true;
    }
    if ((o != null) && (o.getClass() == this.getClass())) {
      p = ((InstanceParameter) o);
      return (this._equalsPA(p) && //
          (this.m_allowsMore == p.m_allowsMore) && //
      EComparison.equals(this.m_choices, p.m_choices));
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  final Object _formatForDump(final Object value) {
    Object use, parsed;
    String str;
    boolean ignoreCase;

    use = super._formatForDump(value);
    if (use == null) {
      return null;
    }

    if (use instanceof String) {
      str = ((String) use);
    } else {
      try {
        str = String.valueOf(use);
      } catch (final Throwable error) {//
        str = null;
      }
    }

    ignoreCase = false;

    for (;;) {
      if (str != null) {
        for (final DefinitionElement de : this.m_choices) {
          if (ignoreCase ? de.m_name.equalsIgnoreCase(str)//
              : de.m_name.equals(str)) {
            return de.m_name;
          }
        }
      }

      for (final DefinitionElement de : this.m_choices) {
        try {
          parsed = this.m_parser.parseString(de.m_name);
          if (EComparison.equals(parsed, use) || //
              ((str != null) && //
              (EComparison.equals(parsed, str) || //
                  ((parsed instanceof String) && //
                  (ignoreCase ? str.equalsIgnoreCase((String) parsed)//
                      : str.equals(parsed))) || //
              (ignoreCase ? str.equalsIgnoreCase(String.valueOf(parsed))
                    : str.equals(String.valueOf(parsed)))))) {
            return de.m_name;
          }
        } catch (final Throwable error) {
          // ignore
        }
      }

      if (ignoreCase) {
        break;
      }
      ignoreCase = true;
    }

    if (this.m_allowsMore) {
      return use;
    }
    return null;
  }
}
