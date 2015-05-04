package org.optimizationBenchmarking.utils.io.structured.spec;

/**
 * A tool for reading stream-based input
 *
 * @param <S>
 *          the destination data type
 */
public interface IStreamInputTool<S> extends IFileInputTool<S> {

  /** {@inheritDoc} */
  @Override
  public abstract IStreamInputJobBuilder<S> use();
}
