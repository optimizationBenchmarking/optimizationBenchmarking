package org.optimizationBenchmarking.experimentation.evaluation.spec;

import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.document.object.IObjectListener;
import org.optimizationBenchmarking.utils.document.spec.IDocumentDriver;

/**
 * A builder for an evaluation job
 */
public interface IEvaluationBuilder extends IEvaluationElement {

  /**
   * Set the executor service to be used for performing the evaluation. If
   * no service is set, the evaluation will be performed in the calling
   * thread once the builder is {@link java.io.Closeable#close() closed}.
   * 
   * @param service
   *          the service to be used for performing the evaluation
   */
  public abstract void setExecutorService(final ExecutorService service);

  /**
   * Set the logger to use
   * 
   * @param logger
   *          the logger
   */
  public abstract void setLogger(final Logger logger);

  /**
   * Set the document driver to use for creating the output
   * 
   * @param driver
   *          the document driver
   */
  public abstract void setDocumentDriver(final IDocumentDriver driver);

  /**
   * Set the output folder to write the document into
   * 
   * @param folder
   *          the output folder
   */
  public abstract void setOutputFolder(final Path folder);

  /**
   * Set the listener to be notified once the output document has been
   * created.
   * 
   * @param listener
   *          the listener
   */
  public abstract void setObjectListener(final IObjectListener listener);

  /**
   * Set the data
   * 
   * @return the data builder
   */
  public abstract IDataBuilder setData();

  /**
   * Close the builder and perform the evaluation, either in the current
   * thread or by enqueuing it into an
   * {@link #setExecutorService(ExecutorService) ExecutorService}.
   */
  @Override
  public abstract void close();

}
