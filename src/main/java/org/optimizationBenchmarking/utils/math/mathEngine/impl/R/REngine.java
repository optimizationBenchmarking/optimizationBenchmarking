package org.optimizationBenchmarking.utils.math.mathEngine.impl.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.TempDir;
import org.optimizationBenchmarking.utils.math.mathEngine.impl.abstr.MathEngine;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.impl.MatrixBuilder;
import org.optimizationBenchmarking.utils.parsers.LooseBooleanParser;
import org.optimizationBenchmarking.utils.parsers.LooseDoubleParser;
import org.optimizationBenchmarking.utils.parsers.LooseLongParser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.numbers.SimpleNumberAppender;
import org.optimizationBenchmarking.utils.tools.impl.process.TextProcess;

/** The {@code R} Engine */
public final class REngine extends MathEngine {

  /** the engine ID */
  private static final AtomicLong ENGINE_ID = new AtomicLong();

  /** the instance of {@code R} */
  private TextProcess m_process;

  /** the temporary directory in which we run {@code R} */
  private TempDir m_temp;

  /** the id of the engine */
  private final String m_id;

  /** have we been closed? */
  private boolean m_closed;

  /** the {@code false} */
  private static final String FALSE = "FALSE"; //$NON-NLS-1$

  /** the nan */
  private static final String NAN = "NaN"; //$NON-NLS-1$

  /** the negative infinity */
  private static final String NEGATIVE_INFINITY = "-Inf"; //$NON-NLS-1$

  /** the positive infinity */
  private static final String POSITIVE_INFINITY = "Inf"; //$NON-NLS-1$

  /** the {@code true} */
  private static final String TRUE = "TRUE"; //$NON-NLS-1$

  /** the function to print a matrix */
  private static final String PRINT_FUNCTION_NAME = "rcommPrintMatrix";//$NON-NLS-1$

  /**
   * create
   *
   * @param process
   *          the process
   * @param temp
   *          the temporary directory
   * @param logger
   *          the logger, or {@code null} if none should be used
   * @throws IOException
   *           if it must
   */
  REngine(final TextProcess process, final TempDir temp,
      final Logger logger) throws IOException {
    super(logger);

    this.m_process = process;
    this.m_temp = temp;

    this.m_id = (('#') + //
    Long.toString(REngine.ENGINE_ID.incrementAndGet()));

    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info(this.m_id + " successfully started"); //$NON-NLS-1$
    }
  }

  /**
   * Check the engine's state.
   *
   * @throws IllegalStateException
   *           if the engine has already been closed
   */
  private final void __checkState() {
    if (this.m_closed) {
      throw new IllegalStateException("R engine "//$NON-NLS-1$
          + this.m_id + //
          " has already been closed."); //$NON-NLS-1$
    }
  }

  /**
   * create a temporary file
   *
   * @return the file
   * @throws IOException
   *           if it must
   */
  private final Path __tempFile() throws IOException {
    return PathUtils.normalize(Files.createTempFile(this.m_temp.getPath(),
        null, ".dat")); //$NON-NLS-1$
  }

  /**
   * Handle an error
   *
   * @param t
   *          the throwable
   */
  private final void __handleError(final Object t) {
    ErrorUtils.logError(this.getLogger(),
        ("Error during communication with R engine " + //$NON-NLS-1$
        this.m_id), t, true, RethrowMode.AS_RUNTIME_EXCEPTION);
  }

  /** {@inheritDoc} */
  @Override
  public final void close() {
    TextProcess proc;
    TempDir temp;

    this.m_closed = true;

    proc = this.m_process;
    this.m_process = null;
    temp = this.m_temp;
    this.m_temp = null;

    try {
      if (proc != null) {
        try {
          try {
            proc.waitFor();
          } finally {
            proc.close();
          }
        } catch (final Throwable error) {
          this.__handleError(error);
        }
        proc = null;
      }
    } finally {
      if (temp != null) {
        try {
          temp.close();
        } catch (final Throwable error) {
          this.__handleError(error);
        }
      }
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  public final void dispose(final String variable) {
    final BufferedWriter bw;

    this.__checkState();
    try {
      bw = this.m_process.getStdIn();
      bw.write("rm(\"");//$NON-NLS-1$
      bw.write(variable);
      bw.write("\");");//$NON-NLS-1$
      bw.flush();
    } catch (final Throwable error) {
      this.__handleError(error);
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  public IMatrix getMatrix(final String variable) {
    final BufferedWriter out;
    final BufferedReader in;
    final int m, n;
    String line, token;
    MatrixBuilder mb;
    int i, j, index, last;

    this.__checkState();

    try {
      out = this.m_process.getStdIn();
      out.write(REngine.PRINT_FUNCTION_NAME);
      out.write('(');
      out.write(variable);
      out.write(')');
      out.write(';');
      out.newLine();
      out.flush();

      mb = new MatrixBuilder(EPrimitiveType.BYTE);
      in = this.m_process.getStdOut();

      line = in.readLine();
      index = line.indexOf(' ');
      m = Integer.parseInt(line.substring(0, index));
      mb.setM(m);
      n = Integer.parseInt(line.substring(index + 1));
      mb.setN(n);

      for (i = m; (--i) >= 0;) {
        line = in.readLine();
        index = -1;
        iterateTokens: for (j = n; (--j) >= 0;) {
          last = (index + 1);
          if (j <= 0) {
            index = line.length();
          } else {
            index = line.indexOf(' ', last);
          }
          token = line.substring(last, index);

          if (REngine.NAN.equalsIgnoreCase(token)) {
            mb.append(Double.NaN);
            continue iterateTokens;
          }
          if (REngine.NEGATIVE_INFINITY.equalsIgnoreCase(token)) {
            mb.append(Double.NEGATIVE_INFINITY);
            continue iterateTokens;
          }
          if (REngine.POSITIVE_INFINITY.equalsIgnoreCase(token)) {
            mb.append(Double.POSITIVE_INFINITY);
            continue iterateTokens;
          }
          if (REngine.TRUE.equalsIgnoreCase(token)) {
            mb.append(1);
            continue iterateTokens;
          }
          if (REngine.FALSE.equalsIgnoreCase(token)) {
            mb.append(0);
            continue iterateTokens;
          }
          mb.append(token);
        }
        mb.setN(n);
      }
      return mb.make();
    } catch (final Throwable t) {
      this.__handleError(t);
    }
    return null;
  }

  /**
   * issue a command to get a scalar
   *
   * @param variable
   *          the variable
   * @return the string
   */
  @SuppressWarnings("resource")
  private final String __getScalar(final String variable) {
    final BufferedWriter out;

    this.__checkState();

    try {
      out = this.m_process.getStdIn();
      out.write("cat("); //$NON-NLS-1$
      out.write(variable);
      out.write(");cat('\n');"); //$NON-NLS-1$
      out.newLine();
      out.flush();
      return this.m_process.getStdOut().readLine();
    } catch (final Throwable ioe) {
      this.__handleError(ioe);
    }
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public final double getDouble(final String variable) {
    final String token;

    token = this.__getScalar(variable);
    if (REngine.NAN.equalsIgnoreCase(token)) {
      return Double.NaN;
    }
    if (REngine.NEGATIVE_INFINITY.equalsIgnoreCase(token)) {
      return Double.NEGATIVE_INFINITY;
    }
    if (REngine.POSITIVE_INFINITY.equalsIgnoreCase(token)) {
      return Double.POSITIVE_INFINITY;
    }
    if (REngine.TRUE.equalsIgnoreCase(token)) {
      return 1d;
    }
    if (REngine.FALSE.equalsIgnoreCase(token)) {
      return 0d;
    }
    return LooseDoubleParser.INSTANCE.parseDouble(token);
  }

  /** {@inheritDoc} */
  @Override
  public final long getLong(final String variable) {
    final String token;

    token = this.__getScalar(variable);
    if (REngine.TRUE.equalsIgnoreCase(token)) {
      return 1L;
    }
    if (REngine.FALSE.equalsIgnoreCase(token)) {
      return 0L;
    }

    try {
      return LooseLongParser.INSTANCE.parseLong(token);
    } catch (final Throwable error) {
      return ((long) (LooseDoubleParser.INSTANCE.parseDouble(token)));
    }
  }

  /** {@inheritDoc} */
  @Override
  public final boolean getBoolean(final String variable) {
    final String token;

    token = this.__getScalar(variable);
    if (REngine.TRUE.equalsIgnoreCase(token)) {
      return true;
    }
    if (REngine.FALSE.equalsIgnoreCase(token)) {
      return false;
    }
    return LooseBooleanParser.INSTANCE.parseBoolean(token);
  }

  /** {@inheritDoc} */
  @Override
  public final IMatrix getVector(final String variable) {
    final IMatrix res;

    res = this.getMatrix(variable);
    if ((res.n() == 1) || (res.m() == 1)) {
      return res;
    }
    this.__handleError(new IllegalStateException(//
        "Vector must have either one column or one row.")); //$NON-NLS-1$
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return this.m_id;
  }

  /**
   * Finalize an assignment.
   *
   * @param variable
   *          the variable name
   * @throws IOException
   *           if i/o fails
   */
  @SuppressWarnings("resource")
  private final void __assignmentEnd(final String variable)
      throws IOException {
    final BufferedWriter out;

    out = this.m_process.getStdIn();

    out.write(';');
    out.newLine();
    out.flush();

    // block until assignment is completed
    out.write("exists(\""); //$NON-NLS-1$
    out.write(variable);
    out.write("\");"); //$NON-NLS-1$
    out.newLine();
    out.flush();
    this.m_process.getStdOut().readLine();
  }

  /**
   * Initialize an assignment.
   *
   * @param variable
   *          the variable name
   * @throws IOException
   *           if i/o fails
   */
  @SuppressWarnings("resource")
  private final void __assignmentBegin(final String variable)
      throws IOException {
    final BufferedWriter out;

    out = this.m_process.getStdIn();

    out.write(variable);
    out.write('<');
    out.write('-');
  }

  /** {@inheritDoc} */
  @Override
  public final void setBoolean(final String variable, final boolean value) {
    this.__checkState();
    try {
      this.__assignmentBegin(variable);
      this.m_process.getStdIn()
          .write(value ? REngine.TRUE : REngine.FALSE);
      this.__assignmentEnd(variable);
    } catch (final Throwable error) {
      this.__handleError(error);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void setLong(final String variable, final long value) {
    this.__checkState();
    try {
      this.__assignmentBegin(variable);
      this.m_process.getStdIn().write(REngine.__longToString(value));
      this.__assignmentEnd(variable);
    } catch (final Throwable error) {
      this.__handleError(error);
    }
  }

  /**
   * convert a {@code double} to a string
   *
   * @param d
   *          the {@code double}
   * @return the string
   */
  private static final String __doubleToString(final double d) {
    if (d != d) {
      return REngine.NAN;
    }
    if (d <= Double.NEGATIVE_INFINITY) {
      return REngine.NEGATIVE_INFINITY;
    }
    if (d >= Double.POSITIVE_INFINITY) {
      return REngine.POSITIVE_INFINITY;
    }

    return SimpleNumberAppender.INSTANCE
        .toString(d, ETextCase.IN_SENTENCE);
  }

  /**
   * convert a {@code long} to a string
   *
   * @param d
   *          the {@code long}
   * @return the string
   */
  private static final String __longToString(final long d) {
    return Long.toString(d);
  }

  /** {@inheritDoc} */
  @Override
  public final void setDouble(final String variable, final double value) {
    this.__checkState();
    try {
      this.__assignmentBegin(variable);
      this.m_process.getStdIn().write(REngine.__doubleToString(value));
      this.__assignmentEnd(variable);
    } catch (final Throwable error) {
      this.__handleError(error);
    }
  }

  /**
   * write a vector
   *
   * @param matrix
   *          the matrix
   * @param m
   *          the m
   * @param n
   *          the n
   * @return the temp file to delete after usage, if any
   * @throws IOException
   *           if i/o fails
   */
  @SuppressWarnings("resource")
  private final Path __writeVector(final IMatrix matrix, final int m,
      final int n) throws IOException {
    final int size;
    final Path temp;
    final BufferedWriter dest, out;
    final char separator;
    int i, j;
    boolean first;

    size = (m * n);
    out = this.m_process.getStdIn();
    if (size > 20) {
      temp = this.__tempFile();
      dest = new BufferedWriter(new OutputStreamWriter(
          PathUtils.openOutputStream(temp)));
      out.write("scan(\"");//$NON-NLS-1$
      out.write(PathUtils.getPhysicalPath(temp, true));
      out.write("\",n=");//$NON-NLS-1$
      out.write(Integer.toString(size));
      out.write(",quiet=");//$NON-NLS-1$
      out.write(REngine.TRUE);
      separator = ' ';
    } else {
      temp = null;
      dest = out;
      out.write("c(");//$NON-NLS-1$
      separator = ',';
    }
    try {
      try {
        first = true;
        if (matrix.isIntegerMatrix()) {
          for (j = 0; j < n; j++) {
            for (i = 0; i < m; i++) {
              if (first) {
                first = false;
              } else {
                dest.write(separator);
              }
              dest.write(REngine.__longToString(matrix.getLong(i, j)));
            }
          }
        } else {
          for (j = 0; j < n; j++) {
            for (i = 0; i < m; i++) {
              if (first) {
                first = false;
              } else {
                dest.write(separator);
              }
              dest.write(REngine.__doubleToString(matrix.getDouble(i, j)));
            }
          }
        }
      } finally {
        if (dest != out) {
          dest.close();
        }
      }

      out.write(')');
    } catch (final Throwable error) {
      if (temp != null) {
        PathUtils.delete(temp);
      }
      this.__handleError(error);
    }

    return temp;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  public final void setMatrix(final String variable, final IMatrix value) {
    final BufferedWriter out;
    final int m, n;
    final Path path;

    if (value == null) {
      this.__handleError(//
      new IllegalArgumentException("Cannot load null matrix.")); //$NON-NLS-1$
    }

    m = value.m();
    n = value.n();
    out = this.m_process.getStdIn();
    try {
      this.__assignmentBegin(variable);
      out.write("matrix("); //$NON-NLS-1$
      path = this.__writeVector(value, m, n);
      try {
        out.write(",nrow=");//$NON-NLS-1$
        out.write(Integer.toString(m));
        out.write(",ncol=");//$NON-NLS-1$
        out.write(Integer.toString(n));
        out.write(",byrow="); //$NON-NLS-1$
        out.write(REngine.FALSE);
        out.write(')');
        this.__assignmentEnd(variable);
      } finally {
        if (path != null) {
          PathUtils.delete(path);
        }
      }
    } catch (final Throwable ioe) {
      this.__handleError(ioe);
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  public final void setVector(final String variable, final IMatrix value) {
    final BufferedWriter out;
    final int m, n;
    final Path path;

    if (value == null) {
      this.__handleError(new IllegalArgumentException(//
          "Cannot load null matrix as vector.")); //$NON-NLS-1$
    }

    m = value.m();
    n = value.n();
    if ((m != 1) && (n != 1)) {
      this.__handleError(new IllegalArgumentException(//
          ((("A vector must be a matrix with either only one row or only one column, but you passed in a "//$NON-NLS-1$
          + m) + 'x') + n)
              + " matrix."));//$NON-NLS-1$
    }

    out = this.m_process.getStdIn();
    try {
      this.__assignmentBegin(variable);
      out.write("matrix("); //$NON-NLS-1$
      path = this.__writeVector(value, m, n);
      try {
        out.write(",nrow=");//$NON-NLS-1$
        out.write(Integer.toString(m));
        out.write(",ncol=");//$NON-NLS-1$
        out.write(Integer.toString(n));
        out.write(",byrow="); //$NON-NLS-1$
        out.write(REngine.TRUE);
        out.write(')');
        this.__assignmentEnd(variable);
      } finally {
        if (path != null) {
          PathUtils.delete(path);
        }
      }
    } catch (final Throwable ioe) {
      this.__handleError(ioe);
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  public final void execute(final Iterable<String> script) {
    final BufferedWriter bw;

    bw = this.m_process.getStdIn();
    try {
      bw.newLine();
      for (final String line : script) {
        bw.write(line);
        bw.newLine();
      }
      bw.newLine();
    } catch (final Throwable error) {
      this.__handleError(error);
    }
  }
}
