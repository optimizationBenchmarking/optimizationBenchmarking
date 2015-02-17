package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.io.IFileType;

/** A component of a tool chain */
abstract class _LaTeXToolChainComponent {

  /** create the tool chain component */
  _LaTeXToolChainComponent() {
    super();
  }

  /**
   * what kind of file does this component produce?
   * 
   * @return the kind of file that the component produces
   */
  abstract ELaTeXFileType _produces();

  /**
   * Can this component be used?
   * 
   * @return {@code true} if and only if the component can be used
   */
  abstract boolean _canUse();

  /**
   * use this component
   * 
   * @param job
   *          the main job
   * @throws IOException
   *           if I/O fails
   */
  abstract void _use(final _LaTeXMainJob job) throws IOException;

  /**
   * require a path from the job
   * 
   * @param job
   *          the job
   * @param type
   *          the type
   * @param mustExist
   *          must the file exist?
   * @param message
   *          a potential additional message
   * @return the path
   */
  final Path _getFile(final _LaTeXMainJob job, final ELaTeXFileType type,
      final boolean mustExist, final String message) {
    final Logger logger;
    final Path path;

    if (type == null) {
      throw new IllegalArgumentException("Cannot require null type."); //$NON-NLS-1$
    }

    path = job._getFile(type);
    if (path == null) {
      logger = job._getLogger();
      if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
        logger.warning(this.getClass().getSimpleName()
            + " required file of type " + //$NON-NLS-1$
            type + " but it was specified as null for job '" + //$NON-NLS-1$
            job._getFile(ELaTeXFileType.TEX) + '\'' + '.'
            + ((message != null) ? message//
                : " This should never happen."));//$NON-NLS-1$
      }
      return null;
    }

    if (mustExist) {
      if (Files.exists(path)) {
        return path;
      }

      logger = job._getLogger();
      if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
        logger.warning(this.getClass().getSimpleName()
            + " required that file of type " + //$NON-NLS-1$
            type + " should exist, but '" + path//$NON-NLS-1$
            + "' does not exist for job '" + //$NON-NLS-1$
            job._getFile(ELaTeXFileType.TEX) + '\'' + '.'
            + ((message != null) ? message//
                : " This should never happen."));//$NON-NLS-1$
      }

      return null;
    }

    return path;
  }

  /**
   * Check whether two file types equal
   * 
   * @param type1
   *          the first type
   * @param type2
   *          the second type
   * @return {@code true} if the two equal, {@code false} otherwise
   */
  static final boolean _equals(final IFileType type1, final IFileType type2) {
    String string1, string2;

    if (type1 == null) {
      return (type2 == null);
    }
    if (type2 == null) {
      return false;
    }
    if (type1.equals(type2) || type2.equals(type1)) {
      return true;
    }

    string1 = type1.getMIMEType();
    string2 = type2.getMIMEType();
    if (string1 == null) {
      return (string2 == null);
    }
    if (string2 == null) {
      return false;
    }
    return (string1.equalsIgnoreCase(string2));
  }

  /**
   * get the argument fitting to a given pattern
   * 
   * @param pattern
   *          the pattern
   * @param line
   *          the line
   * @return the argument, or {@code null}
   */
  static final String _getArg(final String pattern, final String line) {
    final int patternLength, lineLength;
    int i, j;

    if ((pattern == null) || (line == null)) {
      return null;
    }

    if ((patternLength = pattern.length()) >= (lineLength = line.length())) {
      return null;
    }

    for (i = 0; i < lineLength; i++) {
      if (line.charAt(i) != '-') {
        break;
      }
    }

    if ((i + patternLength) > lineLength) {
      return null;
    }

    j = (i + patternLength);
    if (pattern.equalsIgnoreCase(line.substring(i, j))) {
      return line.substring(0, j);
    }

    return null;
  }
}
