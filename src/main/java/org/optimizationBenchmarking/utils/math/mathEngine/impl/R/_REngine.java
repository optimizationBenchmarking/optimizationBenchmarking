package org.optimizationBenchmarking.utils.math.mathEngine.impl.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.io.encoding.TextEncoding;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.TempDir;
import org.optimizationBenchmarking.utils.math.mathEngine.impl.abstr.EDataType;
import org.optimizationBenchmarking.utils.math.mathEngine.impl.abstr.MathEngine;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IAssignment;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IVariable;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.impl.MatrixBuilder;
import org.optimizationBenchmarking.utils.parsers.BooleanParser;
import org.optimizationBenchmarking.utils.parsers.DoubleParser;
import org.optimizationBenchmarking.utils.parsers.LongParser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.tokenizers.WordBasedStringIterator;
import org.optimizationBenchmarking.utils.tools.impl.process.ExternalProcess;

/** The {@code R} Engine */
final class _REngine extends MathEngine {

  /** the instance of {@code R} */
  private final ExternalProcess m_process;

  /** the input read from the process */
  final BufferedReader m_in;

  /** the output to be written to the process */
  final BufferedWriter m_out;

  /** the logger to use */
  final Logger m_log;

  /** the temporary directory in which we run {@code R} */
  final TempDir m_temp;

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
  _REngine(final ExternalProcess process, final TempDir temp,
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

  /** {@inheritDoc} */
  @Override
  public final IAssignment assign(final IVariable variable) {
    return new _RAssignment(((variable != null) ? variable
        : this.variableCreate()), this);
  }

  /** {@inheritDoc} */
  @Override
  public final IAssignment assign() {
    return this.assign(null);
  }

  /** check whether the engine is OK */
  final void _checkState() {
    this.checkState();
  }

  /**
   * create a temporary file
   * 
   * @return the file
   * @throws IOException
   *           if it must
   */
  @SuppressWarnings("nls")
  final Path _tempFile() throws IOException {
    return PathUtils.normalize(Files.createTempFile(this.m_temp.getPath(),
        null, ".dat"));
  }

  /**
   * Handle an error
   * 
   * @param t
   *          the throwable
   */
  final void _handleError(final Throwable t) {
    if (t != null) {
      if ((this.m_log != null) && (this.m_log.isLoggable(Level.SEVERE))) {
        this.m_log.log(Level.SEVERE,
            "Error during communication with R.", t); //$NON-NLS-1$
      }
      ErrorUtils.throwAsRuntimeException(t);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doClose() throws IOException {

    Throwable error;

    error = null;
    try {
      this.m_out.write("q();"); //$NON-NLS-1$
      this.m_out.newLine();
      this.m_out.flush();
    } catch (final Throwable t) {
      error = ErrorUtils.aggregateError(error, t);
    }
    try {
      try {
        this.m_process.waitFor();
      } finally {
        this.m_process.close();
      }
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

    try {
      this.m_temp.close();
    } catch (final Throwable t) {
      error = ErrorUtils.aggregateError(error, t);
    }

    if (error != null) {
      if ((this.m_log != null) && (this.m_log.isLoggable(Level.SEVERE))) {
        this.m_log.log(Level.SEVERE, "Error during shutdown of R", error); //$NON-NLS-1$
      }
      ErrorUtils.throwAsIOException(error);
    } else {
      if ((this.m_log != null) && (this.m_log.isLoggable(Level.INFO))) {
        this.m_log.info("R engine shut down gracefully.");//$NON-NLS-1$
      }
    }
  }

  /**
   * Use the variable: get its name
   * 
   * @param variable
   *          the variable
   * @return the variable's name
   */
  final String _variableName(final IVariable variable) {
    return super.variableName(variable);
  }

  /**
   * Use the variable: get its type
   * 
   * @param variable
   *          the variable
   * @return the variable's type
   */
  final EDataType _variableDataType(final IVariable variable) {
    return super.variableDataType(variable);
  }

  /**
   * Set the type of a variable
   * 
   * @param variable
   *          the variable
   * @param type
   *          the type
   */
  final void _variableSetDataType(final IVariable variable,
      final EDataType type) {
    super.variableSetDataType(variable, type);
  }

  /**
   * Delete a variable
   * 
   * @param variable
   *          the variable
   */
  final void _variableDelete(final IVariable variable) {
    super.variableDelete(variable);
  }

  /** {@inheritDoc} */
  @Override
  public final void dispose(final IVariable var) {
    final BufferedWriter out;

    try {
      out = this.m_out;
      out.write("rm(\"");//$NON-NLS-1$
      out.write(this.variableName(var));
      out.write("\");"); //$NON-NLS-1$
      out.newLine();
      out.flush();
    } catch (final Throwable t) {
      this._handleError(t);
    }
  }

  /** {@inheritDoc} */
  @Override
  public IMatrix getAsMatrix(final IVariable variable) {
    final BufferedWriter out;
    final BufferedReader in;
    String line, token;
    MatrixBuilder mb;
    WordBasedStringIterator iterator;
    int n;

    try {
      out = this.m_out;
      out.write("write.table(");//$NON-NLS-1$
      out.write(this.variableName(variable));
      out.write(",row.names="); //$NON-NLS-1$
      out.write(_RExpression.FALSE);
      out.write(",col.names="); //$NON-NLS-1$
      out.write(_RExpression.FALSE);
      out.write(");"); //$NON-NLS-1$
      out.newLine();
      out.flush();
      out.write("print(\"XXX\");"); //$NON-NLS-1$
      out.newLine();
      out.flush();
      mb = new MatrixBuilder(EPrimitiveType.BYTE);
      in = this.m_in;

      iterateLines: while ((line = in.readLine()) != null) {
        if (line.contains("XXX")) {break iterateLines;} //$NON-NLS-1$
        n = 0;
        iterator = new WordBasedStringIterator(line);
        iterateTokens: while (iterator.hasNext()) {
          token = iterator.next();
          n++;
          if (_RExpression.NAN.equalsIgnoreCase(token)) {
            mb.append(Double.NaN);
            continue iterateTokens;
          }
          if (_RExpression.NEGATIVE_INFINITY.equalsIgnoreCase(token)) {
            mb.append(Double.NEGATIVE_INFINITY);
            continue iterateTokens;
          }
          if (_RExpression.POSITIVE_INFINITY.equalsIgnoreCase(token)) {
            mb.append(Double.POSITIVE_INFINITY);
            continue iterateTokens;
          }
          if (_RExpression.TRUE.equalsIgnoreCase(token)) {
            mb.append(1);
            continue iterateTokens;
          }
          if (_RExpression.FALSE.equalsIgnoreCase(token)) {
            mb.append(0);
            continue iterateTokens;
          }
          mb.append(token);
        }
        mb.setN(n);
      }
      return mb.make();
    } catch (final Throwable t) {
      this._handleError(t);
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
  @SuppressWarnings("incomplete-switch")
  private final String __getScalar(final IVariable variable) {
    final BufferedWriter out;
    String str;
    int idx;

    switch (this.variableDataType(variable)) {
      case VECTOR:
      case MATRIX: {
        throw new IllegalArgumentException(
            "Cannot query variable " + variable + //$NON-NLS-1$
                " as scalar."); //$NON-NLS-1$
      }
    }

    try {
      out = this.m_out;
      out.write(this.variableName(variable));
      out.write(';');
      out.newLine();
      out.flush();
      str = this.m_in.readLine();
      idx = str.lastIndexOf(']');
      if (idx > 0) {
        return TextUtils.prepare(str.substring(idx + 1));
      }
      return TextUtils.prepare(str);
    } catch (final IOException ioe) {
      this._handleError(ioe);
    }
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public final double getAsDouble(final IVariable variable) {
    final String token;

    token = this.__getScalar(variable);
    if (_RExpression.NAN.equalsIgnoreCase(token)) {
      return Double.NaN;
    }
    if (_RExpression.NEGATIVE_INFINITY.equalsIgnoreCase(token)) {
      return Double.NEGATIVE_INFINITY;
    }
    if (_RExpression.POSITIVE_INFINITY.equalsIgnoreCase(token)) {
      return Double.POSITIVE_INFINITY;
    }
    if (_RExpression.TRUE.equalsIgnoreCase(token)) {
      return 1d;
    }
    if (_RExpression.FALSE.equalsIgnoreCase(token)) {
      return 0d;
    }
    return DoubleParser.INSTANCE.parseDouble(token);
  }

  /** {@inheritDoc} */
  @Override
  public final long getAsLong(final IVariable variable) {
    final String token;

    token = this.__getScalar(variable);
    if (_RExpression.TRUE.equalsIgnoreCase(token)) {
      return 1L;
    }
    if (_RExpression.FALSE.equalsIgnoreCase(token)) {
      return 0L;
    }
    return LongParser.INSTANCE.parseLong(token);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean getAsBoolean(final IVariable variable) {
    final String token;

    token = this.__getScalar(variable);
    if (_RExpression.TRUE.equalsIgnoreCase(token)) {
      return true;
    }
    if (_RExpression.FALSE.equalsIgnoreCase(token)) {
      return false;
    }
    return BooleanParser.INSTANCE.parseBoolean(token);
  }
}
