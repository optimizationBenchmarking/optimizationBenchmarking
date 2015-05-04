package org.optimizationBenchmarking.utils.io.structured.spec;

/**
 * A tool for generating text output
 *
 * @param <S>
 *          the source data type
 */
public interface ITextOutputTool<S> extends IStreamOutputTool<S> {

  /** {@inheritDoc} */
  @Override
  public abstract ITextOutputJobBuilder<S> use();
}
