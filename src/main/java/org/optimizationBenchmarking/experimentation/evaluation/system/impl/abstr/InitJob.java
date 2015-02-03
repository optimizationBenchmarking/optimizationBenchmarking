package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.utils.document.spec.IDocument;

/**
 * A job processing an experiment set and writing to a document. This is
 * the kind of job used in the initialization routines.
 * 
 * @param <M>
 *          the configuration type
 */
public abstract class InitJob<M extends ConfiguredModule> extends
    EvaluationJob<M> {

  /** the experiment set */
  protected final ExperimentSet m_experimentSet;

  /** the document */
  protected final IDocument m_document;

  /**
   * create
   * 
   * @param config
   *          the configuration
   * @param experimentSet
   *          the experiment set
   * @param document
   *          the document
   */
  protected InitJob(final M config, final ExperimentSet experimentSet,
      final IDocument document) {
    super(config);
    EvaluationJob._checkExperimentSet(experimentSet);
    EvaluationJob._checkDocument(document);
    this.m_experimentSet = experimentSet;
    this.m_document = document;
  }
}
