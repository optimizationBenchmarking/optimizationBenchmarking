package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.io.IOException;

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
