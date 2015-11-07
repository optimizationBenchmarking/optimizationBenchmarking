package org.optimizationBenchmarking.utils.tools.impl.shell;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.predicates.CanExecutePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.FileNamePredicate;
import org.optimizationBenchmarking.utils.io.paths.predicates.IsFilePredicate;
import org.optimizationBenchmarking.utils.predicates.AndPredicate;
import org.optimizationBenchmarking.utils.tools.impl.abstr.Tool;
import org.optimizationBenchmarking.utils.tools.impl.process.TextProcessExecutor;

/** A tool allowing you to open a shell. */
public class ShellTool extends Tool {

  /** create the tool */
  ShellTool() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return (TextProcessExecutor.getInstance().canUse()//
        && (_ShellPath.PATH != null)//
    && (_ShellPath.TYPE != null));
  }

  /**
   * Get the type of the shell
   *
   * @return the type of the shell
   */
  public final EShellType getType() {
    return _ShellPath.TYPE;
  }

  /** {@inheritDoc} */
  @Override
  public final void checkCanUse() {
    TextProcessExecutor.getInstance().checkCanUse();
    if (_ShellPath.PATH == null) {
      throw new UnsupportedOperationException(//
          "No shell path detected."); //$NON-NLS-1$
    }
    if (_ShellPath.TYPE == null) {
      throw new UnsupportedOperationException(//
          "Shell type unknown."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public ShellBuilder use() {
    return new ShellBuilder();
  }

  /**
   * Get the globally shared instance of the shell tool
   *
   * @return the globally shared instance of the shell tool
   */
  public static final ShellTool getInstance() {
    return __ShellToolHolder.INSTANCE;
  }

  /** the holder class */
  private static final class __ShellToolHolder {
    /** the shared instance */
    static final ShellTool INSTANCE = new ShellTool();
  }

  /** the holder for the path to the shell binary */
  @SuppressWarnings("unused")
  static final class _ShellPath {

    /** the path to the shell */
    static final Path PATH;

    /** the shell type */
    static final EShellType TYPE;

    static {
      final Configuration root;
      Path test;
      LinkedHashSet<Path> set;
      String cmd1, cmd2, name;

      set = new LinkedHashSet<>();
      root = Configuration.getRoot();

      for (final String env : new String[] { "shell", //$NON-NLS-1$
          "comspec", //$NON-NLS-1$
          "%comspec%" }) {//$NON-NLS-1$
        try {
          test = root.getPath(env, null);
          if (test != null) {
            set.add(test);
          }
        } catch (final Throwable ignoreable) {
          // ignore
        }
      }

      set.add(Paths.get("/bin/bash"));//$NON-NLS-1$
      set.add(Paths.get("C:/Windows/system32/cmd.exe"));//$NON-NLS-1$

      PATH = PathUtils.findFirstInPath(new AndPredicate<>(
          new FileNamePredicate(true,//
              "bash",//$NON-NLS-1$
              "sh",//$NON-NLS-1$
              "dash",//$NON-NLS-1$
              "zsh",//$NON-NLS-1$
              "ksh",//$NON-NLS-1$
              "pdksh",//$NON-NLS-1$
              cmd1 = "cmd", //$NON-NLS-1$
              cmd2 = "command" //$NON-NLS-1$
          ), CanExecutePredicate.INSTANCE), IsFilePredicate.INSTANCE,//
          set.toArray(new Path[set.size()]));

      if (_ShellPath.PATH == null) {
        TYPE = null;
      } else {
        name = PathUtils.getFileNameWithoutExtension(_ShellPath.PATH);
        if (name == null) {
          TYPE = null;
        } else {
          if (cmd1.equalsIgnoreCase(name) || cmd2.equalsIgnoreCase(name)) {
            TYPE = EShellType.DOS;
          } else {
            TYPE = EShellType.BOURNE;
          }
        }
      }
    }
  }
}
