package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.optimizationBenchmarking.utils.tools.impl.abstr.FileProducerSupport;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/** the output job */
final class _OutputJob extends _IOJob {

  /** the destination */
  final _Location m_dest;

  /** the file producer support */
  final FileProducerSupport m_support;

  /**
   * create the _IOJob
   *
   * @param logger
   *          the logger
   * @param tool
   *          the owning tool
   * @param basePath
   *          the base path used for path and file resolution
   * @param data
   *          the source data
   * @param dest
   *          the destination
   * @param listener
   *          the listener
   */
  _OutputJob(final Logger logger, final FileOutputTool<?> tool,
      final Path basePath, final Object data, final _Location dest,
      final IFileProducerListener listener) {
    super(logger, tool, basePath, data);

    if (dest == null) {
      throw new IllegalArgumentException("Destination must not be null."); //$NON-NLS-1$
    }
    this.m_dest = dest;

    if (listener != null) {
      this.m_support = new FileProducerSupport(listener);
    } else {
      this.m_support = null;
    }

  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  final void _doCall() throws Throwable {
    ((FileOutputTool) (this.m_tool))._handle(this, this.m_data,
        this.m_dest);
  }

  /** {@inheritDoc} */
  @Override
  final void _appendID(final MemoryTextOutput textOut) {
    super._appendID(textOut);
    _Location._appendLocation(this.m_dest, textOut);
  }

  /** {@inheritDoc} */
  @Override
  final void _last() {
    if (this.m_support != null) {
      this.m_support.close();
    }
  }
}
