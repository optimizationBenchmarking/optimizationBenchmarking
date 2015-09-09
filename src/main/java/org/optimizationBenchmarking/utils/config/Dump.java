package org.optimizationBenchmarking.utils.config;

import java.util.Map;

/**
 * A configuration dump based on a given definition. This immutable object
 * holds the values of a set of parameters, extracted from a configuration.
 */
public final class Dump extends _DefSet<Map.Entry<Parameter<?>, Object>> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * Create
   *
   * @param data
   *          the data
   * @param allowsMore
   *          are more parameters allowed than defined here?
   */
  Dump(final Map.Entry<Parameter<?>, Object>[] data,
      final boolean allowsMore) {
    super(data, allowsMore);
  }
}
