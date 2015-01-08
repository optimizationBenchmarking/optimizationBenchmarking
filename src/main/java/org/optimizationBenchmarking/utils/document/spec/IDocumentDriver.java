package org.optimizationBenchmarking.utils.document.spec;

import org.optimizationBenchmarking.utils.tools.spec.IDocumentProducerTool;

/**
 * The entry interface to the document API: a document driver can create
 * documents.
 */
public interface IDocumentDriver extends IDocumentProducerTool {

  /** {@inheritDoc} */
  @Override
  public abstract IDocumentBuilder use();
}
