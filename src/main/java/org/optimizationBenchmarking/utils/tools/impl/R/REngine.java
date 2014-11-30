package org.optimizationBenchmarking.utils.tools.impl.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.io.encoding.TextEncoding;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.TempDir;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.numbers.AlphabeticNumberAppender;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJob;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcess;

/** The {@code R} Engine */
public final class REngine extends ToolJob implements Closeable {

  /** the instance of {@code R} */
  private final ExternalProcess m_process;

  /** the input read from the process */
  private final BufferedReader m_in;

  /** the output to be written to the process */
  private final BufferedWriter m_out;

  /** the logger to use */
  private final Logger m_log;

  /** the temporary directory in which we run {@code R} */
  private final TempDir m_temp;

  /** the id counter */
  private int m_idCounter;

  /**
   * create
   * 
   * @param process
   *          the process
   * @param temp
   *          the temporary directory
   * @param logger
   *          the logger
   * @throws IOException
   *           if it must
   */
  REngine(final ExternalProcess process, final TempDir temp,
      final Logger logger) throws IOException {
    super();

    final TextEncoding e;

    this.m_process = process;
    this.m_log = logger;
    this.m_temp = temp;

    e = R._encoding();
    this.m_in = e.wrapInputStream(process.getStdOut());
    this.m_out = e.wrapOutputStream(process.getStdIn());

    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info("R engine successfully started"); //$NON-NLS-1$
    }
  }

  /**
   * create the next ID
   * 
   * @return the next id
   */
  private final RID __nextID() {
    return new RID(AlphabeticNumberAppender.LOWER_CASE_INSTANCE.toString(
        (this.m_idCounter++), ETextCase.IN_SENTENCE));
  }

  /**
   * create a temporary file
   * 
   * @return the file
   * @throws IOException
   *           if it must
   */
  @SuppressWarnings("nls")
  private final Path __tempFile() throws IOException {
    return PathUtils.normalize(Files.createTempFile(this.m_temp.getPath(),
        null, ".dat"));
  }

  /**
   * Handle an error
   * 
   * @param t
   *          the throwable
   */
  private final void __handleError(final Throwable t) {
    if (t != null) {
      if ((this.m_log != null) && (this.m_log.isLoggable(Level.SEVERE))) {
        this.m_log.log(Level.SEVERE,
            "Error during communication with R.", t); //$NON-NLS-1$
      }
      ErrorUtils.throwAsRuntimeException(t);
    }
  }

  /**
   * convert a long to a string
   * 
   * @param l
   *          the long
   * @return the string
   */
  private static final String __toString(final long l) {
    return Long.toString(l);
  }

  /**
   * convert a double to a string
   * 
   * @param d
   *          the double
   * @return the string
   */
  private static final String __toString(final double d) {
    long l;

    if ((d >= Long.MIN_VALUE) && (d <= Long.MAX_VALUE)) {
      l = ((long) d);
      if (l == d) {
        return REngine.__toString(l);
      }
    }
    return Double.toString(d);
  }

  /**
   * Load a given matrix into the {@code R}-Engine
   * 
   * @param matrix
   *          the matrix to load
   * @return the ID of the matrix
   */
  public final RID loadMatrix(final IMatrix matrix) {
    final int size, m, n;
    final BufferedWriter out, dest;
    final RID id;
    final Path temp;
    final char separator;
    int i, j;
    boolean first;

    if (matrix == null) {
      throw new IllegalArgumentException("Cannot load null matrix."); //$NON-NLS-1$
    }

    id = this.__nextID();
    out = this.m_out;// new BufferedWriter(new
                     // OutputStreamWriter(System.out));//this.m_out;
    try {
      out.write(id.m_id);
      out.write("=matrix("); //$NON-NLS-1$

      m = matrix.m();
      n = matrix.n();
      size = (m * n);
      if (size > 200) {
        temp = this.__tempFile();
        dest = new BufferedWriter(new OutputStreamWriter(
            PathUtils.openOutputStream(temp)));
        out.write("scan(\"");//$NON-NLS-1$
        out.write(temp.toString());
        out.write("\",n=");//$NON-NLS-1$
        out.write(Integer.toString(size));
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
            for (i = 0; i < m; i++) {
              for (j = 0; j < n; j++) {
                if (first) {
                  first = false;
                } else {
                  dest.write(separator);
                }
                dest.write(REngine.__toString(matrix.getLong(i, j)));
              }
            }
          } else {
            for (i = 0; i < m; i++) {
              for (j = 0; j < n; j++) {
                if (first) {
                  first = false;
                } else {
                  dest.write(separator);
                }
                dest.write(REngine.__toString(matrix.getDouble(i, j)));
              }
            }
          }
        } finally {
          if (dest != out) {
            dest.close();
          }
        }

        out.write("),nrow=");//$NON-NLS-1$
        out.write(Integer.toString(m));
        out.write(",ncol=");//$NON-NLS-1$
        out.write(Integer.toString(n));
        out.write(",byrow=TRUE);"); //$NON-NLS-1$
        out.newLine();
        out.flush();
        out.write("length(");//$NON-NLS-1$
        out.write(id.m_id);
        out.write(");");//$NON-NLS-1$
        out.newLine();
        out.flush();

        this.m_in.readLine();
      } finally {
        if (temp != null) {
          PathUtils.delete(temp);
        }
      }
    } catch (final Throwable t) {
      this.__handleError(t);
    }
    return id;
  }

  /** {@inheritDoc} */
  @Override
  public final void close() throws IOException {
    Throwable error;

    error = null;
    try {
      try {
        try {
          this.m_out.append("q();"); //$NON-NLS-1$
        } catch (final Throwable t) {
          error = ErrorUtils.aggregateError(error, t);
        }
        try {
          this.m_out.newLine();
        } catch (final Throwable t) {
          error = ErrorUtils.aggregateError(error, t);
        }

        try {
          this.m_out.flush();
        } catch (final Throwable t) {
          error = ErrorUtils.aggregateError(error, t);
        }

        try {
          this.m_process.waitFor();
        } catch (final Throwable t) {
          error = ErrorUtils.aggregateError(error, t);
        }

        try {
          this.m_out.close();
        } catch (final Throwable t) {
          error = ErrorUtils.aggregateError(error, t);
        }

        try {
          this.m_in.close();
        } catch (final Throwable t) {
          error = ErrorUtils.aggregateError(error, t);
        }
      } finally {
        try {
          this.m_process.close();
        } catch (final Throwable t) {
          error = ErrorUtils.aggregateError(error, t);
        }
      }
    } finally {
      try {
        this.m_temp.close();
      } catch (final Throwable t) {
        error = ErrorUtils.aggregateError(error, t);
      }
    }
    if (error != null) {
      if ((this.m_log != null) && (this.m_log.isLoggable(Level.SEVERE))) {
        this.m_log.log(Level.SEVERE, "Error while shutting down R.", //$NON-NLS-1$
            error);
      }
      ErrorUtils.throwAsIOException(error);
    } else {
      if ((this.m_log != null) && (this.m_log.isLoggable(Level.INFO))) {
        this.m_log.info("R engine has been shut down gracefully."); //$NON-NLS-1$
      }
    }

  }
}
