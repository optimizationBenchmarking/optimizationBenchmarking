package org.optimizationBenchmarking.utils.io.structured.spec;

/**
 * A tool for generating file output
 * 
 * @param <S>
 *          the source data type
 */
public interface IFileOutputTool<S> extends IIOTool {

  /** {@inheritDoc} */
  @Override
  public abstract IFileOutputJobBuilder<S> use();
}
