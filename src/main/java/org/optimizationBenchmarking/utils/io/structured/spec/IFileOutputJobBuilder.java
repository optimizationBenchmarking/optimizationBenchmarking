package org.optimizationBenchmarking.utils.io.structured.spec;

import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;

/**
 * A file output job builder.
 * 
 * @param <D>
 *          the data type which can be stored
 */
public interface IFileOutputJobBuilder<D> extends IIOJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IFileOutputJobBuilder<D> setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract IIOJob create();

  /**
   * Set the source data object, i.e., the object to be stored
   * 
   * @param source
   *          the source data object
   * @return this builder
   */
  public abstract IFileOutputJobBuilder<D> setSource(final D source);

  /**
   * Set the path to the output
   * 
   * @param path
   *          the path to write to
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param zipCompress
   *          Should the output be compressed into a single ZIP archive?
   * @return this builder
   */
  public abstract IFileOutputJobBuilder<D> setPath(final Path path,
      final StreamEncoding<?, ?> encoding, final boolean zipCompress);

  /**
   * Set the path to the output, using the default encoding and no ZIP
   * compression
   * 
   * @param path
   *          the path to write to
   * @return this builder
   */
  public abstract IFileOutputJobBuilder<D> setPath(final Path path);

  /**
   * Set the file to the output
   * 
   * @param file
   *          the file to write to
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param zipCompress
   *          Should the output be compressed into a single ZIP archive?
   * @return this builder
   */
  public abstract IFileOutputJobBuilder<D> setFile(final File file,
      final StreamEncoding<?, ?> encoding, final boolean zipCompress);

  /**
   * Set a file to the output, using the default encoding and no ZIP
   * compression
   * 
   * @param file
   *          the file to write to
   * @return this builder
   */
  public abstract IFileOutputJobBuilder<D> setFile(final File file);

  /**
   * Set the path to the output
   * 
   * @param path
   *          the path to write to
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param zipCompress
   *          Should the output be compressed into a single ZIP archive?
   * @return this builder
   */
  public abstract IFileOutputJobBuilder<D> setPath(final String path,
      final StreamEncoding<?, ?> encoding, final boolean zipCompress);

  /**
   * Set the path to the output, using the default encoding and no ZIP
   * compression
   * 
   * @param path
   *          the path to write to
   * @return this builder
   */
  public abstract IFileOutputJobBuilder<D> setPath(final String path);

  /**
   * Set the file to the output
   * 
   * @param file
   *          the file to write to
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param zipCompress
   *          Should the output be compressed into a single ZIP archive?
   * @return this builder
   */
  public abstract IFileOutputJobBuilder<D> setFile(final String file,
      final StreamEncoding<?, ?> encoding, final boolean zipCompress);

  /**
   * Set a file to the output, using the default encoding and no ZIP
   * compression
   * 
   * @param file
   *          the file to write to
   * @return this builder
   */
  public abstract IFileOutputJobBuilder<D> setFile(final String file);

}
