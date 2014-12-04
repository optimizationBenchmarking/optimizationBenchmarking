package org.optimizationBenchmarking.utils.math.mathEngine.spec;

import java.io.IOException;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.tools.spec.IToolJobBuilder;

/**
 * A builder to make a math engine accessible
 */
public interface IMathEngineBuilder extends IToolJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IMathEngineBuilder setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract IMathEngine create() throws IOException;
}
