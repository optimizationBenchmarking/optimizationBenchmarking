package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.evaluation.data.ExperimentSet;
import org.optimizationBenchmarking.utils.document.spec.IDocumentDriver;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;
import org.optimizationBenchmarking.utils.tools.spec.IToolJobBuilder;

/**
 * A builder for an evaluation job
 */
public interface IEvaluationBuilder extends IToolJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IEvaluationBuilder setLogger(final Logger logger);

  /**
   * Set the document driver to use for creating the output
   * 
   * @param driver
   *          the document driver
   * @return this builder
   */
  public abstract IEvaluationBuilder setDocumentDriver(
      final IDocumentDriver driver);

  /**
   * Set the output folder to write the document into
   * 
   * @param folder
   *          the output folder
   * @return this builder
   */
  public abstract IEvaluationBuilder setOutputFolder(final Path folder);

  /**
   * Set the listener to be notified about all the output documents and
   * files generated during the evaluation process.
   * 
   * @param listener
   *          the listener
   * @return this builder
   */
  public abstract IEvaluationBuilder setFileProducerListener(
      final IFileProducerListener listener);

  /**
   * Set the data to be used for evaluation.
   * 
   * @param data
   *          the data to evaluate
   * @return this builder
   */
  public abstract IEvaluationBuilder setData(final ExperimentSet data);

}
