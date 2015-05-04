package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.structured.spec.IStreamInputJobBuilder;
import org.optimizationBenchmarking.utils.io.structured.spec.IStreamInputTool;

/**
 * A tool for reading stream input
 *
 * @param <S>
 *          the destination type
 */
public class StreamInputTool<S> extends FileInputTool<S> implements
    IStreamInputTool<S> {

  /** create */
  protected StreamInputTool() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public IStreamInputJobBuilder<S> use() {
    this.checkCanUse();
    return new _StreamInputJobBuilder(this);
  }

  /** {@inheritDoc} */
  @Override
  final void _checkRawStreams() {
    // nothing to do: we can process raw streams
  }

  /**
   * Handle a stream
   *
   * @param job
   *          the job where logging info can be written
   * @param data
   *          the data to be read
   * @param stream
   *          the stream
   * @param encoding
   *          the encoding
   * @throws Throwable
   *           if it must
   */
  @Override
  protected void stream(final IOJob job, final S data,
      final InputStream stream, final StreamEncoding<?, ?> encoding)
      throws Throwable {
    //
  }

  /** {@inheritDoc} */
  @Override
  protected void file(final IOJob job, final S data, final Path path,
      final BasicFileAttributes attributes,
      final StreamEncoding<?, ?> encoding) throws Throwable {
    final Object oldCur;

    oldCur = job.m_current;
    try {
      job.m_current = path;
      try (final InputStream input = PathUtils.openInputStream(path)) {
        this._stream(job, data, input, encoding, null);
      }
    } finally {
      job.m_current = oldCur;
    }
  }
}
