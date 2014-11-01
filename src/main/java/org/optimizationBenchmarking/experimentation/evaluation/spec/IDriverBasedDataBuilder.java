package org.optimizationBenchmarking.experimentation.evaluation.spec;

import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.structured.FileIODriver;

/**
 * Build a data source.
 */
public interface IDriverBasedDataBuilder extends IEvaluationElement {

  /**
   * Set the driver of this data source
   * 
   * @param driver
   *          the driver of this data source
   */
  public abstract void setDriver(
      final FileIODriver<?, ? super ExperimentSetContext> driver);

  /**
   * Set the stream encoding to be used to load this data source
   * 
   * @param encoding
   *          the stream encoding to be used to load this data source
   */
  public abstract void setStreamEncoding(
      final StreamEncoding<?, ?> encoding);

  /**
   * Add a given path
   * 
   * @param path
   *          the path to add
   */
  public abstract void addPath(final Path path);

  /**
   * Add a uri
   * 
   * @param uri
   *          the uri
   */
  public abstract void addURI(final URI uri);

  /**
   * Add a url
   * 
   * @param url
   *          the url
   */
  public abstract void addURL(final URL url);
}
