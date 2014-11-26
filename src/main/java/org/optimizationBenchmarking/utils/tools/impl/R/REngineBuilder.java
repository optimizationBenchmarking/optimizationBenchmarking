package org.optimizationBenchmarking.utils.tools.impl.R;

import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJobBuilder;

/**
 * The builder for an R engine.
 */
public final class REngineBuilder extends
    ToolJobBuilder<REngine, REngineBuilder> {

  /** create the R engine builder */
  REngineBuilder() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final REngine doCreate() {
    return new REngine();
  }

}
