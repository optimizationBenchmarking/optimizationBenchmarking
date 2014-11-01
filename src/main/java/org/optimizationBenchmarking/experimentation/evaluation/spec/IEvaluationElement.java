package org.optimizationBenchmarking.experimentation.evaluation.spec;

import java.io.Closeable;

/**
 * A builder for an element of the evaluation API.
 */
public interface IEvaluationElement extends Closeable {

  /** Close the builder. */
  @Override
  public abstract void close();

}
