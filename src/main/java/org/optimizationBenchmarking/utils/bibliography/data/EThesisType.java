package org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/** An enumeration with the supported thesis types. */
public enum EThesisType {

  /** bachelor thesis */
  BACHELOR_THESIS("Bachelor's Thesis"), //$NON-NLS-1$

  /** master's thesis */
  MASTER_THESIS("Master's Thesis"), //$NON-NLS-1$

  /** phd thesis */
  PHD_THESIS("PhD Thesis"); //$NON-NLS-1$

  /** get the types */
  public static final ArraySetView<EThesisType> TYPES = new ArraySetView<>(
      EThesisType.values());

  /** the name */
  final String m_name;

  /**
   * Create the thesis
   *
   * @param name
   *          the name
   */
  private EThesisType(final String name) {
    this.m_name = name;
  }

  /**
   * Get the name of the thesis type
   *
   * @return the name of the thesis type
   */
  public final String getName() {
    return this.m_name;
  }
}
