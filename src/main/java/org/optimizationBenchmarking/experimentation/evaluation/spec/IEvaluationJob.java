package org.optimizationBenchmarking.experimentation.evaluation.spec;

import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;
import org.optimizationBenchmarking.utils.tools.spec.IToolJob;

/**
 * A fully configured evaluation job.
 */
public interface IEvaluationJob extends IToolJob {
  /**
   * Initialize the document before any computation is performed. In this
   * method you can, for instance, allocate labels in the document.
   *
   * @param document
   *          the document
   */
  public abstract void initialize(final IDocument document);

  /**
   * Adds text into the document summary (i.e, the abstract), if any. This
   * happens before any calculation takes place. Any summary text should be
   * preceded by a space.
   *
   * @param summary
   *          the summary
   */
  public abstract void summary(final IPlainText summary);

  /**
   * Execute the main job. This will usually mean creating a new section in
   * the provided
   * {@link org.optimizationBenchmarking.utils.document.spec.ISectionContainer
   * sectionContainer} and writing some results into this section.
   *
   * @param sectionContainer
   *          the section container
   */
  public abstract void main(final ISectionContainer sectionContainer);

}
