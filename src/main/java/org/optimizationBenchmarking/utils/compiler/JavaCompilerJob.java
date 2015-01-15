package org.optimizationBenchmarking.utils.compiler;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJob;

/**
 * A job which compiles a set of java sources and returns a
 * {@link java.lang.ClassLoader} holding the compiled classes.
 */
public final class JavaCompilerJob extends ToolJob implements
    Callable<ClassLoader> {

  /** the sources */
  private final ArrayListView<JavaFileObject> m_sources;

  /**
   * create the compiler job
   * 
   * @param logger
   *          the logger, or {@code null} if no log is used
   * @param sources
   *          the sources
   */
  JavaCompilerJob(final Logger logger,
      final Collection<JavaFileObject> sources) {
    super(logger);

    if ((sources == null) || (sources.isEmpty())) {
      throw new IllegalArgumentException(
          "Java sources cannot be null or empty."); //$NON-NLS-1$
    }
    this.m_sources = ArrayListView.collectionToView(sources, false);
    if (this.m_sources.isEmpty()) {
      throw new IllegalStateException(//
          "Sources have concurrently been emptied."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final ClassLoader call() {
    final JavaCompilerTool tool;
    final JavaCompiler compiler;
    final _ClassFileManager fileManager;

    tool = JavaCompilerTool.getInstance();

    compiler = tool.m_compiler;
    if (compiler == null) {
      throw new IllegalStateException(//
          "Cannot compile since there is no compiler."); //$NON-NLS-1$
    }

    try (final _DiagnosticListener listener = new _DiagnosticListener(
        this.m_logger)) {

      fileManager = new _ClassFileManager(//
          compiler.getStandardFileManager(listener, null, null));

      if (!(compiler.getTask(listener, fileManager, listener,
          tool.m_options, null, this.m_sources).call().booleanValue())) {
        throw new IllegalArgumentException(//
            "Compiling did not work out."); //$NON-NLS-1$
      }

      return fileManager.loadClasses();
    }
  }

}
