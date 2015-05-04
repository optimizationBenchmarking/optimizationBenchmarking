package org.optimizationBenchmarking.utils.io.structured.spec;

/**
 * A tool for generating xml output
 *
 * @param <S>
 *          the source data type
 */
public interface IXMLOutputTool<S> extends ITextOutputTool<S> {

  /** {@inheritDoc} */
  @Override
  public abstract IXMLOutputJobBuilder<S> use();
}
