package org.optimizationBenchmarking.utils.io.structured.spec;

import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;

/**
 * A file input job builder.
 * 
 * @param <D>
 *          the data element which will be filled
 */
public interface IFileInputJobBuilder<D> extends IIOJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IFileInputJobBuilder<D> setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract IIOJob create();

  /**
   * Set the destination data object, i.e., the object to be filled with
   * the loaded data
   * 
   * @param destination
   *          the destination data object
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> setDestination(
      final D destination);

  /**
   * Add a path to an input source
   * 
   * @param path
   *          the path to read from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param isZipCompressed
   *          Is the input a compressed ZIP archive?
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addPath(final Path path,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /**
   * Add a path to an input source, using the default encoding and
   * expecting no ZIP compression
   * 
   * @param path
   *          the path to read from
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addPath(final Path path);

  /**
   * Add an input source file
   * 
   * @param file
   *          the file to read from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param isZipCompressed
   *          Is the input a compressed ZIP archive?
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addFile(final File file,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /**
   * Add an input source file , using the default encoding and expecting no
   * ZIP compression
   * 
   * @param file
   *          the file to read from
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addFile(final File file);

  /**
   * Add a path to an input source
   * 
   * @param path
   *          the path to read from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param isZipCompressed
   *          Is the input a compressed ZIP archive?
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addPath(final String path,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /**
   * Add a path to an input source, using the default encoding and
   * expecting no ZIP compression
   * 
   * @param path
   *          the path to read from
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addPath(final String path);

  /**
   * Add an input source file
   * 
   * @param file
   *          the file to read from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param isZipCompressed
   *          Is the input a compressed ZIP archive?
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addFile(final String file,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /**
   * Add an input source file , using the default encoding and expecting no
   * ZIP compression
   * 
   * @param file
   *          the file to read from
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addFile(final String file);
}
