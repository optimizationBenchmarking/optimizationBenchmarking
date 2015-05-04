package org.optimizationBenchmarking.utils.tools.spec;

import org.optimizationBenchmarking.utils.io.IFileType;

/**
 * A tool which can create jobs that produce one kind of file, i.e.,
 * documents.
 */
public interface IDocumentProducerTool extends IFileProducerTool {

  /**
   * Obtain the type of files this tool can create
   *
   * @return the type of files this tool can create
   */
  public abstract IFileType getFileType();

  /**
   * Create the builder for producing documents.
   *
   * @return the file producer builder
   */
  @Override
  public abstract IDocumentProducerJobBuilder use();
}
