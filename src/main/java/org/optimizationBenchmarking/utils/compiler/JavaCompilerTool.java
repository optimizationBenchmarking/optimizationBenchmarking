package org.optimizationBenchmarking.utils.compiler;

import java.util.ArrayList;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.tools.impl.abstr.Tool;

/**
 * A tool for compiling and loading Java code on the fly. The jobs created
 * by this tool allow us to create new Java code in memory, to compile it,
 * and to load it into a {@link java.lang.ClassLoader}.
 */
public class JavaCompilerTool extends Tool {

  /** the java compiler */
  final JavaCompiler m_compiler;

  /** the options */
  final ArraySetView<String> m_options;

  /** create the tool */
  @SuppressWarnings("unused")
  JavaCompilerTool() {
    super();

    JavaCompiler compiler;
    ArrayList<String> options;
    String[] optArray;
    String option;
    int optCount;

    try {
      compiler = ToolProvider.getSystemJavaCompiler();
      if (compiler != null) {

        options = new ArrayList<>();
        option = "g:none"; //$NON-NLS-1$
        if (compiler.isSupportedOption(option) >= 0) {
          options.add(option);
        } else {
          option = ('-' + option);
          if (compiler.isSupportedOption(option) >= 0) {
            options.add(option);
          } else {
            option = "g"; //$NON-NLS-1$
            if (compiler.isSupportedOption(option) >= 0) {
              options.add(option + ":none"); //$NON-NLS-1$
            } else {
              option = ('-' + option);
              if (compiler.isSupportedOption(option) >= 0) {
                options.add(option + ":none"); //$NON-NLS-1$
              }
            }

          }
        }

        //        option = "nowarn"; //$NON-NLS-1$
        // if (compiler.isSupportedOption(option) >= 0) {
        // options.add(option);
        // } else {
        // option = ('-' + option);
        // if (compiler.isSupportedOption(option) >= 0) {
        // options.add(option);
        // }
        // }

        option = "O"; //$NON-NLS-1$
        if (compiler.isSupportedOption(option) >= 0) {
          options.add(option);
        } else {
          option = ('-' + option);
          if (compiler.isSupportedOption(option) >= 0) {
            options.add(option);
          }
        }
      } else {
        options = null;
      }
    } catch (final Throwable error) {
      compiler = null;
      options = null;
    }

    this.m_compiler = compiler;
    if ((compiler != null) && (options != null)
        && ((optCount = options.size()) > 0)) {
      optArray = options.toArray(new String[optCount]);
      Arrays.sort(optArray);
      this.m_options = new ArraySetView<>(optArray);
    } else {
      this.m_options = null;
    }
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return (this.m_compiler != null);
  }

  /** {@inheritDoc} */
  @Override
  public final JavaCompilerJobBuilder use() {
    this.checkCanUse();
    return new JavaCompilerJobBuilder();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Java Compiler"; //$NON-NLS-1$
  }

  /**
   * Get the globally shared instance of the java compiler tool
   *
   * @return the globally shared instance of the java compiler tool
   */
  public static final JavaCompilerTool getInstance() {
    return __JavaCompilerToolLoader.INSTANCE;
  }

  /** the internal loader class */
  private static final class __JavaCompilerToolLoader {

    /** the globally shared instance of the java compiler tool */
    static final JavaCompilerTool INSTANCE = new JavaCompilerTool();
  }

}
