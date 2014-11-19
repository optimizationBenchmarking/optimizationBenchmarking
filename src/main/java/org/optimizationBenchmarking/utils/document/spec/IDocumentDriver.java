package org.optimizationBenchmarking.utils.document.spec;

import org.optimizationBenchmarking.utils.tools.spec.IFileProducerTool;

/**
 * The entry interface to the document API: a document driver can create
 * documents.
 */
public interface IDocumentDriver extends IFileProducerTool {

  /** {@inheritDoc} */
  @Override
  public abstract IDocumentBuilder use();
}
