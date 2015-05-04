package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

import org.optimizationBenchmarking.utils.document.spec.IDocument;

/**
 * The evaluation output.
 */
public interface IEvaluationOutput {

  /**
   * Get the document to write the output to. This method must only be
   * called at most once, otherwise it may throw an
   * {@link java.lang.IllegalStateException}.
   *
   * @return the document to write the output to
   * @throws Exception
   *           if something goes wrong
   */
  public abstract IDocument getDocument() throws Exception;

}
