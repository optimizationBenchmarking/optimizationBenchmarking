package org.optimizationBenchmarking.utils.config;

import org.optimizationBenchmarking.utils.collections.ImmutableAssociation;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/** A dump of the configuration. */
public final class ConfigurationDump {

  /** the known parameter values */
  private final ArrayListView<ImmutableAssociation<Parameter<?>, Object>> m_parameters;

  /** the additional elements */
  private final ArrayListView<ImmutableAssociation<String, Object>> m_more;

  /**
   * create the configuration dump
   *
   * @param parameters
   *          the known parameter values
   * @param more
   *          the additional elements
   */
  ConfigurationDump(
      final ArrayListView<ImmutableAssociation<Parameter<?>, Object>> parameters,
      final ArrayListView<ImmutableAssociation<String, Object>> more) {
    super();
    if (parameters == null) {
      throw new IllegalArgumentException(//
          "Known parameter values cannot be null."); //$NON-NLS-1$
    }
    if (more == null) {
      throw new IllegalArgumentException(//
          "Unknown parameter values cannot be null.");//$NON-NLS-1$
    }
    this.m_parameters = parameters;
    this.m_more = more;
  }

  /**
   * Get the known parameters.
   *
   * @return the list of known parameters and their values.
   */
  public final ArrayListView<ImmutableAssociation<Parameter<?>, Object>> getParameters() {
    return this.m_parameters;
  }

  /**
   * Get the unknown parameters.
   *
   * @return the list of known parameters and their values.
   */
  public final ArrayListView<ImmutableAssociation<String, Object>> getMoreParameters() {
    return this.m_more;
  }
}
