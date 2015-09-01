package org.optimizationBenchmarking.experimentation.io.impl.edi;

import org.optimizationBenchmarking.experimentation.data.impl.flat.AbstractFlatExperimentSetContext;

/**
 * A driver for Experiment Data Interchange (EDI) input which loads data
 * into a
 * {@link org.optimizationBenchmarking.experimentation.data.impl.flat.AbstractFlatExperimentSetContext
 * flat experiment set context}. EDI is our default, canonical format for
 * storing and exchanging
 * {@link org.optimizationBenchmarking.experimentation.data experiment data
 * structures}.
 */
public final class FlatEDIInput extends
    EDIInputToolBase<AbstractFlatExperimentSetContext> {

  /** create */
  FlatEDIInput() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "EDI Experiment Data Input (Flat)"; //$NON-NLS-1$
  }

  /**
   * get the instance of the {@link FlatEDIInput}
   *
   * @return the instance of the {@link FlatEDIInput}
   */
  public static final FlatEDIInput getInstance() {
    return __EDIInputLoader.INSTANCE;
  }

  /** the loader */
  private static final class __EDIInputLoader {
    /** create */
    static final FlatEDIInput INSTANCE = new FlatEDIInput();
  }
}
