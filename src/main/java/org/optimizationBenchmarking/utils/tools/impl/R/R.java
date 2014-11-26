package org.optimizationBenchmarking.utils.tools.impl.R;

import org.optimizationBenchmarking.utils.tools.impl.abstr.Tool;

/** The entry point for using {@code R} */
public final class R extends Tool<REngineBuilder> {

  /** create */
  R() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  protected final REngineBuilder createBuilder() {
    return new REngineBuilder();
  }

  /**
   * Get the globally shared instance of {@code R}
   * 
   * @return the globally shared instance of {@code R}
   */
  public static final R getInstance() {
    return __REngineLoader.INSTANCE;
  }

  /** the R engine loader */
  static final class __REngineLoader {
    /** the shared instance */
    static final R INSTANCE = new R();
  }
}
