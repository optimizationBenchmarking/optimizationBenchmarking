package org.optimizationBenchmarking.utils.tools.spec;

import org.optimizationBenchmarking.utils.io.IFileType;

/** A tool which can create one kind of file. */
public interface IFileProducerTool extends ITool {

  /**
   * Obtain the type of files this tool can create
   * 
   * @return the type of files this tool can create
   */
  public abstract IFileType getFileType();

  /**
   * Create the builder for producing files.
   * 
   * @return the file producer builder
   */
  @Override
  public abstract IFileProducerBuilder use();
}
