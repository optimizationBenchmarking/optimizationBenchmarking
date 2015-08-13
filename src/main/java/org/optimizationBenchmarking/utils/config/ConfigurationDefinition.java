package org.optimizationBenchmarking.utils.config;

import java.util.Collection;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashObject;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * A definition of configuration parameters.
 */
public final class ConfigurationDefinition extends HashObject {

  /** the defined parameters */
  private final ArrayListView<Parameter<?>> m_params;

  /** may there be additional parameters not specified here? */
  private final boolean m_allowsMore;

  /**
   * Create the parameter definition
   *
   * @param params
   *          the parameters
   * @param allowsMore
   *          may there be additional parameters not specified here?
   */
  ConfigurationDefinition(final Collection<Parameter<?>> params,
      final boolean allowsMore) {
    super();

    if (params == null) {
      throw new IllegalArgumentException("Parameters cannot be null."); //$NON-NLS-1$
    }
    this.m_params = ArrayListView.collectionToView(params);
    this.m_allowsMore = allowsMore;
  }

  /**
   * Get the list of defined parameters
   *
   * @return the list of defined parameters
   */
  public final ArrayListView<Parameter<?>> getParameters() {
    return this.m_params;
  }

  /**
   * Are parameters allowed to occur which have not been specified by this
   * definition?
   *
   * @return {@code true} if parameters allowed to occur which have not
   *         been specified by this definition, {@code false} otherwise
   */
  public final boolean allowsMore() {
    return this.m_allowsMore;
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(//
        HashUtils.hashCode(this.m_params),//
        HashUtils.hashCode(this.m_allowsMore));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object other) {
    ConfigurationDefinition def;
    if (other == this) {
      return true;
    }
    if (other instanceof ConfigurationDefinition) {
      def = ((ConfigurationDefinition) other);
      return ((this.m_allowsMore == def.m_allowsMore) && EComparison
          .equals(this.m_params, def.m_params));
    }
    return false;
  }
}
