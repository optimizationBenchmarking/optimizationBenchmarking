package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

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
   * @param data
   *          the source data
   * @param sources
   *          the sources
   */
  _InputJob(final Logger logger, final FileInputTool<?> tool,
      final Object data, final _Location[] sources) {
    super(logger, tool, data);
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

    tool = ((FileInputTool) (this.m_tool));
    tool.before(this, this.m_data);

    sources = this.m_sources;
    if ((sources.length > 0) && (this.canLog())) {
      this.log("Begin loading data from " + sources.length + //$NON-NLS-1$
          " sources.");//$NON-NLS-1$
    }

    for (final _Location source : sources) {
      this.m_id = null;
      this.m_currentSource = source;
      tool._handle(this, this.m_data, source);
    }
    this.m_currentSource = null;
    this.m_id = null;

    if ((sources.length > 0) && (this.canLog())) {
      this.log("Finished loading data from " + sources.length + //$NON-NLS-1$
          " sources.");//$NON-NLS-1$
    }

    tool.after(this, this.m_data);
  }

  /** {@inheritDoc} */
  @Override
  final void _appendID(final MemoryTextOutput textOut) {
    super._appendID(textOut);
    _Location._appendLocation(this.m_currentSource, textOut);
  }
}
