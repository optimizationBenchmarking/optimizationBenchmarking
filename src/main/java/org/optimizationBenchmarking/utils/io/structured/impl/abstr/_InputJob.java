package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** the output job */
final class _InputJob extends _IOJob {

  /** the sources */
  private final _Location[] m_sources;

  /** the current source */
  private _Location m_currentSource;

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
   * @param sources
   *          the sources
   */
  _InputJob(final Logger logger, final FileInputTool<?> tool,
      final Object data, final Path basePath, final _Location[] sources) {
    super(logger, tool, basePath, data);
    if ((sources == null) || (sources.length <= 0)) {
      throw new IllegalArgumentException(
          "Source list must not be null or empty."); //$NON-NLS-1$
    }
    this.m_sources = sources;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  final void _doCall() throws Throwable {
    final FileInputTool tool;
    final _Location[] sources;
    final Logger logger;

    sources = this.m_sources;
    if (sources.length <= 0) {
      logger = null;
    } else {
      logger = this.getLogger();
    }

    if ((logger != null) && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
      logger.log(IOTool.DEFAULT_LOG_LEVEL,//
          ("Begin loading data from " + sources.length + //$NON-NLS-1$
          " sources."));//$NON-NLS-1$
    }

    tool = ((FileInputTool) (this.m_tool));
    for (final _Location source : sources) {
      this.m_id = null;
      this.m_currentSource = source;
      tool._handle(this, this.m_data, source);
    }
    this.m_currentSource = null;
    this.m_id = null;

    if ((logger != null) && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
      logger.log(IOTool.DEFAULT_LOG_LEVEL,//
          ("Finished loading data from " + sources.length + //$NON-NLS-1$
          " sources."));//$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  final void _appendID(final MemoryTextOutput textOut) {
    super._appendID(textOut);
    _Location._appendLocation(this.m_currentSource, textOut);
  }
}
