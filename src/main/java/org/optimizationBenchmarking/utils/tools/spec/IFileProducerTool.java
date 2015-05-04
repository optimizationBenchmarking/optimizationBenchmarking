package org.optimizationBenchmarking.utils.tools.spec;

/** A tool which creates jobs that build files. */
public interface IFileProducerTool extends ITool {

  /**
   * Create the builder for producing files.
   *
   * @return the file producer builder
   */
  @Override
  public abstract IFileProducerJobBuilder use();
}
