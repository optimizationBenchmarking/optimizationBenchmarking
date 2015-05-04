package org.optimizationBenchmarking.utils.io.structured.spec;

/**
 * A tool for input
 *
 * @param <S>
 *          the destination data type
 */
public interface IInputTool<S> extends IIOTool {

  /** {@inheritDoc} */
  @Override
  public abstract IInputJobBuilder<S> use();
}
