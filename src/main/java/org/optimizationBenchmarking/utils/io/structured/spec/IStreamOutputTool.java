package org.optimizationBenchmarking.utils.io.structured.spec;

/**
 * A tool for generating stream output
 *
 * @param <S>
 *          the source data type
 */
public interface IStreamOutputTool<S> extends IFileOutputTool<S> {

  /** {@inheritDoc} */
  @Override
  public abstract IStreamOutputJobBuilder<S> use();
}
