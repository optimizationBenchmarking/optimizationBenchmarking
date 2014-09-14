package org.optimizationBenchmarking.utils.compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import org.optimizationBenchmarking.utils.tasks.Task;

/** A compiler task. */
public class JavaCompilerTask extends Task<ClassLoader> {

  /** the sources */
  private final List<JavaFileObject> m_sources;

  /**
   * create the compiler task
   * 
   * @param data
   *          the data
   */
  public JavaCompilerTask(final Collection<JavaFileObject> data) {
    this(JavaCompilerTask.__l(data));
  }

  /**
   * create the compiler task
   * 
   * @param data
   *          the data
   */
  public JavaCompilerTask(final JavaFileObject... data) {
    this(Arrays.asList(data.clone()));
  }

  /**
   * Transform a collection to a list
   * 
   * @param l
   *          the collection
   * @return the list
   */
  private static final List<JavaFileObject> __l(
      final Collection<JavaFileObject> l) {
    final ArrayList<JavaFileObject> x;

    x = new ArrayList<>(l.size());
    x.addAll(l);
    return x;
  }

  /**
   * create
   * 
   * @param data
   *          the data
   */
  private JavaCompilerTask(final List<JavaFileObject> data) {
    super();
    this.m_sources = data;
  }

  /** {@inheritDoc} */
  @Override
  public final ClassLoader call() {
    final JavaCompiler compiler;
    final _ClassFileManager fileManager;
    final ArrayList<String> options;
    String s;

    compiler = ToolProvider.getSystemJavaCompiler();
    fileManager = new _ClassFileManager(//
        compiler.getStandardFileManager(null, null, null));

    options = new ArrayList<>();
    s = "g:none"; //$NON-NLS-1$
    if (compiler.isSupportedOption(s) >= 0) {
      options.add(s);
    } else {
      s = ('-' + s);
      if (compiler.isSupportedOption(s) >= 0) {
        options.add(s);
      } else {
        s = "g"; //$NON-NLS-1$
        if (compiler.isSupportedOption(s) >= 0) {
          options.add(s + ":none"); //$NON-NLS-1$
        } else {
          s = ('-' + s);
          if (compiler.isSupportedOption(s) >= 0) {
            options.add(s + ":none"); //$NON-NLS-1$
          }
        }

      }
    }

    s = "nowarn"; //$NON-NLS-1$
    if (compiler.isSupportedOption(s) >= 0) {
      options.add(s);
    } else {
      s = ('-' + s);
      if (compiler.isSupportedOption(s) >= 0) {
        options.add(s);
      }
    }

    s = "O"; //$NON-NLS-1$
    if (compiler.isSupportedOption(s) >= 0) {
      options.add(s);
    } else {
      s = ('-' + s);
      if (compiler.isSupportedOption(s) >= 0) {
        options.add(s);
      }
    }

    if (!(compiler.getTask(null, fileManager, null,
        ((options.size() > 0) ? options : null), null, this.m_sources)
        .call().booleanValue())) {
      throw new IllegalArgumentException(//
          "Compiling did not work out."); //$NON-NLS-1$
    }

    return fileManager.loadClasses();
  }
}
