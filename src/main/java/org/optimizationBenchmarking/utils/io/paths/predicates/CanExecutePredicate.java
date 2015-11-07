package org.optimizationBenchmarking.utils.io.paths.predicates;

import java.io.File;
import java.io.Serializable;
import java.nio.file.AccessMode;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.spi.FileSystemProvider;
import java.util.Arrays;
import java.util.HashSet;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.predicates.IPredicate;
import org.optimizationBenchmarking.utils.text.predicates.StringInListIgnoreCase;

/**
 * This predicate is {@code true} if the file can be executed.
 */
public final class CanExecutePredicate implements IPredicate<Path>,
    Serializable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final CanExecutePredicate INSTANCE = new CanExecutePredicate();

  /** create */
  private CanExecutePredicate() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unused")
  @Override
  public final boolean check(final Path file) {
    final BasicFileAttributes bfa;
    final FileSystemProvider fsp;
    final File ff;

    if (file == null) {
      return false;
    }

    try {
      bfa = java.nio.file.Files.readAttributes(file,
          BasicFileAttributes.class);
      if (!(bfa.isRegularFile())) {
        return false;
      }
    } catch (final Throwable t) {
      return false; // file does not exist
    }

    fsp = PathUtils.getFileSystemProvider(file);
    try {
      fsp.checkAccess(file, AccessMode.EXECUTE);
    } catch (final Throwable t) {
      return false;
    }

    try {
      ff = file.toFile();
      if (!(ff.canExecute())) {
        return false;
      }
    } catch (final Throwable t) {
      return false;
    }

    if (File.separatorChar != '\\') {
      // Hack for Windows/DOS checking: under windows, many files are
      // executable, so if we are in one of Microsoft systems, return now.
      return true;
    }

    return CanExecutePredicate.__ExtensionsLoader.EXTENSIONS.check(//
        PathUtils.getFileExtension(file));
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return CanExecutePredicate.INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return CanExecutePredicate.INSTANCE;
  }

  /** the extensions loader */
  private static final class __ExtensionsLoader {

    /** extensions for executables */
    static StringInListIgnoreCase<String> EXTENSIONS;

    static {
      HashSet<String> extensions;
      ArrayListView<String> lst;
      String[] data;
      int i;

      extensions = new HashSet<>();
      extensions.add("exe"); //$NON-NLS-1$
      extensions.add("bat"); //$NON-NLS-1$
      extensions.add("com"); //$NON-NLS-1$
      extensions.add("cmd"); //$NON-NLS-1$
      extensions.add("sh"); //$NON-NLS-1$

      lst = Configuration.getRoot().getStringList("pathext", null); //$NON-NLS-1$
      if (lst != null) {
        for (String s : lst) {
          if ((s != null) && ((i = s.length()) > 0)) {
            if (s.charAt(0) == '.') {
              if (i <= 1) {
                continue;
              }
              s = s.substring(1);
            }
            extensions.add(s);
          }
        }
      }

      data = extensions.toArray(new String[extensions.size()]);
      extensions = null;
      Arrays.sort(data);
      __ExtensionsLoader.EXTENSIONS = new StringInListIgnoreCase<>(data);
    }
  }
}
