package org.optimizationBenchmarking.utils.tools.impl.shell;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.tools.impl.process.AbstractProcessBuilder;
import org.optimizationBenchmarking.utils.tools.impl.process.EProcessStream;
import org.optimizationBenchmarking.utils.tools.impl.process.TextProcessBuilder;
import org.optimizationBenchmarking.utils.tools.impl.process.TextProcessExecutor;

/** A builder for shells. */
public class ShellBuilder extends
    AbstractProcessBuilder<Shell, ShellBuilder> {

  /** the internal text process builder */
  private final TextProcessBuilder m_builder;

  /** create the process builder */
  ShellBuilder() {
    super();
    this.m_builder = TextProcessExecutor.getInstance().use();
  }

  /** {@inheritDoc} */
  @Override
  public final ShellBuilder setDirectory(final Path dir) {
    this.m_builder.setDirectory(dir);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final ShellBuilder setStdIn(final EProcessStream def) {
    this.m_builder.setStdIn(def);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final ShellBuilder readStdInFrom(final Path source) {
    this.m_builder.readStdInFrom(source);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final ShellBuilder setStdOut(final EProcessStream def) {
    this.m_builder.setStdOut(def);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final ShellBuilder writeStdOutTo(final Path dest,
      final boolean append) {
    this.m_builder.writeStdOutTo(dest, append);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final ShellBuilder setStdErr(final EProcessStream def) {
    this.m_builder.setStdErr(def);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final ShellBuilder writeStdErrTo(final Path dest,
      final boolean append) {
    this.m_builder.writeStdErrTo(dest, append);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final ShellBuilder setMergeStdOutAndStdErr(final boolean merge) {
    this.m_builder.setMergeStdOutAndStdErr(merge);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final Shell create() throws Exception {
    final Logger logger;
    this.m_builder.setProcessCloser(new _ShellCloser());
    this.m_builder.setExecutable(ShellTool._ShellPath.PATH);
    logger = this.getLogger();
    if (logger != null) {
      this.m_builder.setLogger(logger);
    }
    return new Shell(logger, this.m_builder.create());
  }
}
