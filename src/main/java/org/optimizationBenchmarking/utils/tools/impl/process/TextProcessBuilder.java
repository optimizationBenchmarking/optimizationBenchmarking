package org.optimizationBenchmarking.utils.tools.impl.process;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * A builder for text processes.
 */
public final class TextProcessBuilder extends
    _BasicProcessBuilder<TextProcess, TextProcessBuilder> {

  /** the external process builder */
  private final ExternalProcessBuilder m_builder;

  /** create the process builder */
  TextProcessBuilder() {
    super();

    this.m_builder = ExternalProcessExecutor.getInstance().use();
  }

  /** {@inheritDoc} */
  @Override
  public final TextProcessBuilder setExecutable(final Path path) {
    this.m_builder.setExecutable(path);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final TextProcessBuilder addStringArgument(final String s) {
    this.m_builder.addStringArgument(s);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final TextProcessBuilder addPathArgument(final Path path) {
    this.m_builder.addPathArgument(path);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final TextProcessBuilder putEnvironmentString(final String key,
      final String value) {
    this.m_builder.putEnvironmentString(key, value);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final TextProcessBuilder putEnvironmentPath(final String key,
      final Path value) {
    this.m_builder.putEnvironmentPath(key, value);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final TextProcessBuilder removeEnvironmentVar(final String key) {
    this.m_builder.removeEnvironmentVar(key);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final TextProcessBuilder clearEnvironment() {
    this.m_builder.clearEnvironment();
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final TextProcessBuilder setDirectory(final Path dir) {
    this.m_builder.setDirectory(dir);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final TextProcessBuilder setStdIn(final EProcessStream def) {
    this.m_builder.setStdIn(def);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final TextProcessBuilder readStdInFrom(final Path source) {
    this.m_builder.readStdInFrom(source);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final TextProcessBuilder setStdOut(final EProcessStream def) {
    this.m_builder.setStdOut(def);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final TextProcessBuilder writeStdOutTo(final Path dest,
      final boolean append) {
    this.m_builder.writeStdOutTo(dest, append);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final TextProcessBuilder setStdErr(final EProcessStream def) {
    this.m_builder.setStdErr(def);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final TextProcessBuilder writeStdErrTo(final Path dest,
      final boolean append) {
    this.m_builder.writeStdErrTo(dest, append);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final TextProcessBuilder setMergeStdOutAndStdErr(
      final boolean merge) {
    this.m_builder.setMergeStdOutAndStdErr(merge);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  protected final void validate() {
    super.validate();
    this.m_builder.validate();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  public final TextProcess create() throws IOException {
    final _TextProcessBridge bridge;
    final ExternalProcess proc;
    final TextProcess tp;
    final Logger logger;

    bridge = new _TextProcessBridge();
    this.m_builder.setProcessCloser(bridge);
    logger = this.getLogger();
    if (logger != null) {
      this.m_builder.setLogger(logger);
    }

    proc = this.m_builder.create();
    tp = new TextProcess(proc, this.m_closer);
    bridge.m_target = tp;
    return tp;
  }
}
