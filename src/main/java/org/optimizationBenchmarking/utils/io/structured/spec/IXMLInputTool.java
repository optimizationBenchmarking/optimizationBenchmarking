package org.optimizationBenchmarking.utils.io.structured.spec;

/**
 * A tool for reading XML-based input
 *
 * @param <S>
 *          the destination data type
 */
public interface IXMLInputTool<S> extends ITextInputTool<S> {

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<S> use();
}
