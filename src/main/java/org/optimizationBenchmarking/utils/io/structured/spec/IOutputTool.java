package org.optimizationBenchmarking.utils.io.structured.spec;

/**
 * A tool for generating output
 *
 * @param <S>
 *          the source data type
 */
public interface IOutputTool<S> extends IIOTool {

  /** {@inheritDoc} */
  @Override
  public abstract IOutputJobBuilder<S> use();
}
