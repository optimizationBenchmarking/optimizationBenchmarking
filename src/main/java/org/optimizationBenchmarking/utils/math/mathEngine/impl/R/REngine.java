package org.optimizationBenchmarking.utils.math.mathEngine.impl.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
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
   * @param logger
   *          the logger, or {@code null} if none should be used
   * @throws IOException
   *           if it must
   */
  REngine(final TextProcess process, final Logger logger)
      throws IOException {
    super(logger);

    this.m_process = process;

    this.m_id = (("REngine #") + //$NON-NLS-1$
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

    this.m_closed = true;

    proc = this.m_process;
    this.m_process = null;

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

  /**
   * Find the next non-empty line
   * 
   * @return the line
   * @throws IOException
   *           if i/o fails
   */
  @SuppressWarnings("resource")
  private final String __nextLine() throws IOException {
    final BufferedReader reader;
    String line;
    int i, size;

    this.__checkState();
    reader = this.m_process.getStdOut();
    for (;;) {
      line = reader.readLine();
      if (line == null) {
        throw new IOException(
            "Prematurely Reached end of output stream of the REngine."); //$NON-NLS-1$
      }

      size = line.length();
      for (i = 0; i < size; i++) {
        if (line.charAt(i) > ' ') {
          return line;
        }
      }
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

      line = this.__nextLine();
      index = line.indexOf(' ');
      m = Integer.parseInt(line.substring(0, index));
      mb.setM(m);
      n = Integer.parseInt(line.substring(index + 1));
      mb.setN(n);
      in = this.m_process.getStdOut();

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
      return this.__nextLine();
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
      REngine.__writeLong(value, this.m_process.getStdIn());
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
   * @param dest
   *          the destination writer
   * @throws IOException
   *           if i/o failse
   */
  private static final void __writeDouble(final double d,
      final BufferedWriter dest) throws IOException {
    if (d != d) {
      dest.write(REngine.NAN);
      return;
    }
    if (d <= Double.NEGATIVE_INFINITY) {
      dest.write(REngine.NEGATIVE_INFINITY);
      return;
    }
    if (d >= Double.POSITIVE_INFINITY) {
      dest.write(REngine.POSITIVE_INFINITY);
      return;
    }
    dest.write(SimpleNumberAppender.INSTANCE.toString(d,
        ETextCase.IN_SENTENCE));
  }

  /**
   * convert a {@code long} to a string
   *
   * @param d
   *          the {@code long}
   * @param dest
   *          the writer
   * @throws IOException
   *           if i/o fails
   */
  private static final void __writeLong(final long d,
      final BufferedWriter dest) throws IOException {
    if ((d >= Integer.MIN_VALUE) && (d <= Integer.MAX_VALUE)) {
      dest.write("as.integer("); //$NON-NLS-1$
      dest.write(Integer.toString((int) d));
      dest.write(')');
    } else {
      dest.write(Long.toString(d));
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void setDouble(final String variable, final double value) {
    this.__checkState();
    try {
      this.__assignmentBegin(variable);
      REngine.__writeDouble(value, this.m_process.getStdIn());
      this.__assignmentEnd(variable);
    } catch (final Throwable error) {
      this.__handleError(error);
    }
  }

  /**
   * Set a matrix or vector
   *
   * @param variable
   *          the variable
   * @param value
   *          the value
   * @param isVector
   *          is the value a vector ({@code true}) or a matrix (
   *          {@code false})?
   */
  @SuppressWarnings("resource")
  private final void __setMatrix(final String variable,
      final IMatrix value, final boolean isVector) {

    final BufferedWriter out;
    final int m, n;
    int i, j, q;
    boolean first;

    if (value == null) {
      this.__handleError(//
      new IllegalArgumentException("Cannot load null matrix.")); //$NON-NLS-1$
      return;
    }
    m = value.m();
    n = value.n();
    if (isVector && ((m != 1) && (n != 1))) {
      this.__handleError(new IllegalArgumentException(//
          ((("A vector must be a matrix with either only one row or only one column, but you passed in a "//$NON-NLS-1$
          + m) + 'x') + n)
              + " matrix."));//$NON-NLS-1$
    }

    out = this.m_process.getStdIn();
    try {
      this.__assignmentBegin(variable);
      out.write("matrix(c("); //$NON-NLS-1$

      first = true;
      q = 0;
      if (value.isIntegerMatrix()) {
        for (j = 0; j < n; j++) {
          for (i = 0; i < m; i++) {
            if (first) {
              first = false;
            } else {
              out.write(',');
              if (((++q) % 20) == 0) {
                out.newLine();
              }
            }
            REngine.__writeLong(value.getLong(i, j), out);
          }
        }
      } else {
        for (j = 0; j < n; j++) {
          for (i = 0; i < m; i++) {
            if (first) {
              first = false;
            } else {
              out.write(',');
              if (((++q) % 20) == 0) {
                out.newLine();
              }
            }
            REngine.__writeDouble(value.getDouble(i, j), out);
          }
        }
      }

      out.write("),nrow=");//$NON-NLS-1$
      out.write(Integer.toString(m));
      out.write(",ncol=");//$NON-NLS-1$
      out.write(Integer.toString(n));
      out.write(",byrow="); //$NON-NLS-1$
      out.write(REngine.FALSE);
      out.write(')');
      this.__assignmentEnd(variable);

    } catch (final Throwable ioe) {
      this.__handleError(ioe);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void setMatrix(final String variable, final IMatrix value) {
    this.__setMatrix(variable, value, false);
  }

  /** {@inheritDoc} */
  @Override
  public final void setVector(final String variable, final IMatrix value) {
    this.__setMatrix(variable, value, true);
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
      bw.flush();
    } catch (final Throwable error) {
      this.__handleError(error);
    }
  }
}
