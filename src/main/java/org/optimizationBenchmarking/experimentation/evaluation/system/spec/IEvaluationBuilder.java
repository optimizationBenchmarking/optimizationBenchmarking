package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.evaluation.data.ExperimentSet;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthors;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.tools.spec.IConfigurableToolJobBuilder;

/**
 * A builder for an evaluation job
 */
public interface IEvaluationBuilder extends IConfigurableToolJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IEvaluationBuilder setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract IEvaluationBuilder configure(final Configuration config);

  /**
   * Set the base configuration to which the module configurations should
   * delegate or which is to be used by modules without configuration. Here
   * you can place globally shared parameters. This method must be called
   * before {@link #configure(Configuration)}, because
   * {@link #configure(Configuration)} may override it. It also must be
   * called before any invocation of
   * {@link #addModule(IEvaluationModule, Configuration)}.
   * 
   * @param config
   *          the basic configuration to use
   * @return this evaluation builder
   * @throws IllegalStateException
   *           if this method is called after
   *           {@link #configure(Configuration)} or after
   *           {@link #addModule(IEvaluationModule, Configuration)} or
   *           twice
   */
  public abstract IEvaluationBuilder setBaseConfiguration(
      final Configuration config);

  /**
   * Add an evaluator module. We will resolve all the module's dependencies
   * and configure the module with the provided configuration context. If
   * no configuration context is provided, the module will be configured
   * with the {@link #configure(Configuration) context} used for this
   * evaluation builder.
   * 
   * @param module
   *          the evaluator module to add
   * @param config
   *          a configuration context to be used for the module
   * @return this builder
   */
  public abstract IEvaluationBuilder addModule(
      final IEvaluationModule module, final Configuration config);

  /**
   * Set the data of this evaluation process.
   * 
   * @param data
   *          the data
   * @return this builder
   */
  public abstract IEvaluationBuilder setData(final ExperimentSet data);

  /**
   * Set the document to which the evaluation result should be written
   * 
   * @param doc
   *          the document
   * @return this builder
   */
  public abstract IEvaluationBuilder setDocument(final IDocument doc);

  /**
   * Set the authors of the output document
   * 
   * @param authors
   *          the authors of the output document
   * @return this builder
   */
  public abstract IEvaluationBuilder setAuthors(final BibAuthors authors);

  /** {@inheritDoc} */
  @Override
  public abstract IEvaluation create();
}
