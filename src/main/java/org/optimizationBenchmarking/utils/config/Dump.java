package org.optimizationBenchmarking.utils.config;

import java.util.Map;

/**
 * A configuration definition.
 */
@SuppressWarnings("unchecked")
public final class Dump extends _DefSet<Map.Entry<Parameter<?>, Object>> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the empty dump which allows more elements */
  private static final Dump EMPTY_ALLOWS_MORE;
  /** the empty dump which does not allow more elements */
  private static final Dump EMPTY_DOES_NOT_ALLOW_MORE;

  static {
    final Map.Entry<Parameter<?>, Object>[] list;

    list = new Map.Entry[0];
    EMPTY_ALLOWS_MORE = new Dump(list, true);
    EMPTY_DOES_NOT_ALLOW_MORE = new Dump(list, false);
  }

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

  /**
   * Create an empty dump for a given definition
   *
   * @param def
   *          the definition
   * @return the empty dump
   */
  public static final Dump emptyDumpForDefinition(final Definition def) {
    if (def == null) {
      throw new IllegalArgumentException(//
          "Definition to create an empty dump for cannot be null."); //$NON-NLS-1$
    }
    return (def.m_allowsMore ? Dump.EMPTY_ALLOWS_MORE
        : Dump.EMPTY_DOES_NOT_ALLOW_MORE);
  }
}
