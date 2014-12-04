package org.optimizationBenchmarking.utils.math.mathEngine.spec;

import java.io.Closeable;
import java.io.IOException;

/** A scope inside the math engine */
public interface IMathEngineScope extends Closeable {

  /** {@inheritDoc} */
  @Override
  public abstract void close() throws IOException;

}
