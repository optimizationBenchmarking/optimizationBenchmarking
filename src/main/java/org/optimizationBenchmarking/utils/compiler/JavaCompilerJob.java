package org.optimizationBenchmarking.utils.compiler;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
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
    final ClassLoader result;
    final Logger logger;
    MemoryTextOutput memory;
    int i;

    logger = this.getLogger();
    try {
      tool = JavaCompilerTool.getInstance();

      compiler = tool.m_compiler;
      if (compiler == null) {
        throw new IllegalStateException(//
            "Cannot compile since there is no compiler."); //$NON-NLS-1$
      }

      if ((logger != null) && (logger.isLoggable(Level.INFO))) {
        synchronized (logger) {
          logger.info(((((//
              "Now compiling " + this.m_sources.size()) + //$NON-NLS-1$
              " classes with compiler '") + compiler) + //$NON-NLS-1$
              '\'') + '.');
        }
        if (logger.isLoggable(Level.FINEST)) {
          memory = new MemoryTextOutput();
          memory.append(//
              "The source objects of the classes to be compiled are:");//$NON-NLS-1$
          i = 1;
          for (final JavaFileObject obj : this.m_sources) {
            memory.appendLineBreak();
            memory.appendLineBreak();
            memory.append(i++);
            memory.append('.');
            memory.append(' ');
            memory.append(obj);
          }
          synchronized (logger) {
            logger.finest(memory.toString());
          }
          memory.clear();
          memory = null;
        }
      }

      try (final _DiagnosticListener listener = new _DiagnosticListener(
          logger)) {

        fileManager = new _ClassFileManager(//
            compiler.getStandardFileManager(listener, null, null));

        if (!(compiler.getTask(listener, fileManager, listener,
            tool.m_options, null, this.m_sources).call().booleanValue())) {
          throw new IllegalArgumentException(//
              "Compiling did not work out."); //$NON-NLS-1$
        }

        if ((logger != null) && (logger.isLoggable(Level.FINE))) {
          synchronized (logger) {
            logger.fine(//
                "Compilation successful, now loading classes.");//$NON-NLS-1$
          }
        }

        result = fileManager.loadClasses();

        if ((logger != null) && (logger.isLoggable(Level.FINER))) {
          synchronized (logger) {
            logger.finer(//
                "Classes succesfully loaded.");//$NON-NLS-1$
          }
        }

        return result;
      }
    } catch (final Throwable t) {
      ErrorUtils.logError(logger,
          "Unrecoverable error during on-the-fly compilation.",//$NON-NLS-1$
          t, false, RethrowMode.AS_RUNTIME_EXCEPTION);
      return null;// will never be reached
    }
  }

}
