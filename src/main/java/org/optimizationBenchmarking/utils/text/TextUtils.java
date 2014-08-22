package org.optimizationBenchmarking.utils.text;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.Normalizer;

/**
 * The text utilities class provides several methods that help us to
 * process {@link java.lang.String strings} or to transform data to textual
 * representations.
 */
public final class TextUtils {

  /** the null string */
  public static final String NULL_STRING = String.valueOf((Object) null);

  /** the default normalizer form */
  public static final Normalizer.Form DEFAULT_NORMALIZER_FORM = Normalizer.Form.NFKC;

  /**
   * the globally shared line separator: The value of this variable is
   * compatible to what {@link java.io.BufferedWriter} et al. are using
   */
  public static final String LINE_SEPARATOR = TextUtils.getLineSeparator();

  /**
   * obtain the line separator
   * 
   * @return the line separator
   */
  private static final String getLineSeparator() {
    final byte[] bs;
    final char[] cs;
    final int l;
    String r;
    int i;

    r = null;
    try {
      try (java.io.CharArrayWriter caw = new java.io.CharArrayWriter(2)) {
        try (java.io.BufferedWriter bw = new java.io.BufferedWriter(caw, 2)) {
          bw.newLine();
          bw.flush();
        }
        r = caw.toString();
        if ((r != null) && (r.length() > 0)) {
          return r;
        }
      }
    } catch (final Throwable tt) {
      //
    }

    try {
      try (java.io.CharArrayWriter caw = new java.io.CharArrayWriter(2)) {
        try (java.io.PrintWriter pw = new java.io.PrintWriter(caw)) {
          pw.println();
          pw.flush();
        }
        r = caw.toString();
        if ((r != null) && (r.length() > 0)) {
          return r;
        }
      }
    } catch (final Throwable tt) {
      //
    }

    try {
      try (java.io.ByteArrayOutputStream baos = //
      new java.io.ByteArrayOutputStream(2)) {
        try (java.io.PrintStream ps = new java.io.PrintStream(baos)) {
          ps.println();
          ps.flush();
        }
        bs = baos.toByteArray();
        if (bs != null) {
          l = bs.length;
          if (l > 0) {
            cs = new char[l];
            for (i = l; (--i) >= 0;) {
              cs[i] = ((char) (bs[i]));
              r = new String(cs, 0, l);
            }
          }
        }
      }
    } catch (final Throwable tt) {
      //
    }

    if ((r != null) && (r.length() > 0)) {
      return r;
    }
    return "\n"; //$NON-NLS-1$
  }

  /**
   * Prepare a string by trimming it and setting it to {@code null} if the
   * length is 0.
   * 
   * @param s
   *          the string
   * @return the prepared version of {@code s}, {@code null} if it was
   *         empty
   */
  public static final String prepare(final String s) {
    String t;

    if (s == null) {
      return null;
    }

    t = s.trim();
    if (t.length() <= 0) {
      return null;
    }

    return t;
  }

  /**
   * Normalize a string.
   * 
   * @param s
   *          the string
   * @return the normalized version
   */
  public static final String normalize(final String s) {
    String t, u;

    if (s == null) {
      return null;
    }

    t = s.trim();
    if (t.length() <= 0) {
      return null;
    }

    u = Normalizer.normalize(t, TextUtils.DEFAULT_NORMALIZER_FORM);
    if (u != t) {
      t = u.trim();

      if (t.length() <= 0) {
        return null;
      }
    }

    return (t.equals(s) ? s : t);
  }

  /**
   * Get an easy-to-use representation of a class.
   * 
   * @param c
   *          the class
   * @return the name
   */
  public static final String className(final Class<?> c) {
    String s;

    if (c == null) {
      return "No class specified."; //$NON-NLS-1$
    }

    s = c.getCanonicalName();
    if ((s != null) && (s.length() > 0)) {
      return s;
    }

    s = c.getName();
    if ((s != null) && (s.length() > 0)) {
      return s;
    }

    s = c.getSimpleName();
    if ((s != null) && (s.length() > 0)) {
      return s;
    }

    s = c.toString();
    if ((s != null) && (s.length() > 0)) {
      return s;
    }
    return "nameless"; //$NON-NLS-1$
  }

  /**
   * Translate a throwable to a string.
   * 
   * @param t
   *          the throwable
   * @return the string
   */
  public static final String throwableToString(final Throwable t) {
    try {
      try (StringWriter sw = new StringWriter()) {
        try (PrintWriter pw = new PrintWriter(sw)) {
          t.printStackTrace(pw);
        }
        return sw.toString();
      }
    } catch (final Throwable v) {
      throw new RuntimeException(v);
    }
  }

  /**
   * Could the given string be a class name?
   * 
   * @param s
   *          the string
   * @return {@code true} if {@code s} could be a class name, {@code false}
   *         otherwise
   */
  public static final boolean couldBeClassName(final String s) {
    final int l;
    boolean b;
    char ch;
    int i;

    if (s != null) {
      l = s.length();
      if (l > 0) {
        b = false;

        for (i = l; (--i) > 0;) {
          ch = s.charAt(i);
          if ((Character.isAlphabetic(ch) || Character.isDigit(ch) || (ch == '_'))) {
            b = true;
          } else {
            if (!(Character.isJavaIdentifierPart(ch))) {
              return false;
            }
          }
        }

        ch = s.charAt(0);
        if ((Character.isAlphabetic(ch) || Character.isDigit(ch) || (ch == '_'))) {
          return true;
        }
        return (b && Character.isJavaIdentifierStart(ch));
      }
    }

    return false;
  }
}