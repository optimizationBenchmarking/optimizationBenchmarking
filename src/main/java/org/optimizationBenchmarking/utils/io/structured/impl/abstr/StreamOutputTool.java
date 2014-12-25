package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.structured.spec.IStreamOutputJobBuilder;
import org.optimizationBenchmarking.utils.io.structured.spec.IStreamOutputTool;

/**
 * A tool for generating stream output
 * 
 * @param <S>
 *          the source type
 */
public class StreamOutputTool<S> extends FileOutputTool<S> implements
    IStreamOutputTool<S> {

  /** create */
  protected StreamOutputTool() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  void _handle(final IOJobLog log, final S data, final _Location location)
      throws Throwable {

    if (location.m_location1 instanceof OutputStream) {
      if (log.canLog()) {
        log.log("Beginning output to OutputStream."); //$NON-NLS-1$
      }
      this.__stream(log, data, ((OutputStream) (location.m_location1)),
          location.m_encoding, location.m_zipped);
      if (log.canLog()) {
        log.log("Finished output to OutputStream."); //$NON-NLS-1$
      }
      return;
    }

    super._handle(log, data, location);
  }

  /**
   * Obtain the default name for a plain file to write to if the
   * destination path specifies a directory or ZIP archive
   * 
   * @return the default file name
   */
  protected String getDefaultPlainOutputFileName() {
    return "output.file"; //$NON-NLS-1$
  }

  /**
   * Obtain the default name for a zipped file to write to if the
   * destination path specifies a directory
   * 
   * @return the default file name
   */
  protected String getDefaultZIPOutputFileName() {
    return "output.zip"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  protected void path(final IOJobLog log, final S data, final Path path,
      final BasicFileAttributes attributes,
      final StreamEncoding<?, ?> encoding, final boolean zipCompress)
      throws Throwable {
    Path file;

    if ((attributes != null) && (attributes.isDirectory())) {
      file = PathUtils.createPathInside(
          path,
          (zipCompress ? this.getDefaultZIPOutputFileName() : this
              .getDefaultPlainOutputFileName()));
      log.log("Path '" + path + //$NON-NLS-1$
          "' identifies a directory, creating file '" //$NON-NLS-1$
          + file + "' for output.");//$NON-NLS-1$
    } else {
      file = path;
    }

    try (final OutputStream fileOutput = PathUtils.openOutputStream(file)) {
      this.__stream(log, data, fileOutput, encoding, zipCompress);
    }
  }

  /**
   * Store the data element to a stream
   * 
   * @param log
   *          the log where logging info can be written
   * @param data
   *          the data to be written
   * @param stream
   *          the stream
   * @param encoding
   *          the encoding
   * @param zipCompress
   *          should we ZIP-compress?
   * @throws Throwable
   */
  private final void __stream(final IOJobLog log, final S data,
      final OutputStream stream, final StreamEncoding<?, ?> encoding,
      final boolean zipCompress) throws Throwable {
    String name;

    if (zipCompress) {
      name = this.getDefaultPlainOutputFileName();
      if (log.canLog()) {
        log.log("Creating ZIP-compressed output with file '" + name //$NON-NLS-1$
            + "' as the data (and only) file in the compressed stream."); //$NON-NLS-1$
      }
      try (final ZipOutputStream zipStream = new ZipOutputStream(stream)) {
        zipStream.setMethod(ZipOutputStream.DEFLATED);
        zipStream.setLevel(Deflater.BEST_COMPRESSION);
        zipStream.putNextEntry(new ZipEntry(name));
        this.__stream(log, data, zipStream, encoding);
        zipStream.closeEntry();
      }
    } else {
      this.__stream(log, data, stream, encoding);
    }
  }

  /**
   * Store the data element to a stream
   * 
   * @param log
   *          the log where logging info can be written
   * @param data
   *          the data to be written
   * @param stream
   *          the stream
   * @param encoding
   *          the encoding
   * @throws Throwable
   */
  private final void __stream(final IOJobLog log, final S data,
      final OutputStream stream, final StreamEncoding<?, ?> encoding)
      throws Throwable {
    Class<?> clazz;

    if ((encoding != null) && (encoding != StreamEncoding.UNKNOWN)
        && (encoding != StreamEncoding.TEXT)
        && (encoding != StreamEncoding.BINARY)
        && ((clazz = encoding.getOutputClass()) != null)
        && OutputStream.class.isAssignableFrom(clazz)) {
      if (log.canLog(IOTool.FINE_LOG_LEVEL)) {
        log.log(IOTool.FINE_LOG_LEVEL,
            "Using byte stream encoding " + encoding.name()); //$NON-NLS-1$
      }
      try (OutputStream encoded = ((OutputStream) (encoding
          .wrapOutputStream(stream)))) {
        this.stream(log, data, encoded, StreamEncoding.UNKNOWN);
      }
    } else {
      this.stream(log, data, stream, encoding);
    }
  }

  /**
   * Store the data element to a stream
   * 
   * @param log
   *          the log where logging info can be written
   * @param data
   *          the data to be written
   * @param stream
   *          the stream
   * @param encoding
   *          the encoding
   * @throws Throwable
   */
  protected void stream(final IOJobLog log, final S data,
      final OutputStream stream, final StreamEncoding<?, ?> encoding)
      throws Throwable {
    //
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public IStreamOutputJobBuilder<S> use() {
    this.beforeUse();
    return new _StreamOutputJobBuilder(this);
  }
}
