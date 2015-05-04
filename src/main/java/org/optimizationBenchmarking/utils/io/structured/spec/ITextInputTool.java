package org.optimizationBenchmarking.utils.io.structured.spec;

/**
 * A tool for reading text-based input
 *
 * @param <S>
 *          the destination data type
 */
public interface ITextInputTool<S> extends IStreamInputTool<S> {

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<S> use();
}
