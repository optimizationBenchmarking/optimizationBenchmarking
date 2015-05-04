package org.optimizationBenchmarking.utils.io.structured.spec;

import org.optimizationBenchmarking.utils.tools.spec.IFileProducerTool;

/**
 * A tool for generating file output
 *
 * @param <S>
 *          the source data type
 */
public interface IFileOutputTool<S> extends IOutputTool<S>,
    IFileProducerTool {

  /** {@inheritDoc} */
  @Override
  public abstract IFileOutputJobBuilder<S> use();
}
