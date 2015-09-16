package org.optimizationBenchmarking.utils.math.mathEngine.spec;

import java.io.Closeable;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.tools.spec.IToolJob;

/**
 * A math engine represents a closeable communication device to an external
 * mathematical engine.
 */
public interface IMathEngine extends IToolJob, Closeable {

  /**
   * Set a boolean variable value.
   *
   * @param variable
   *          the variable name
   * @param value
   *          the variable value
   */
  public abstract void setBoolean(final String variable,
      final boolean value);

  /**
   * Get a boolean variable value.
   *
   * @param variable
   *          the variable name
   * @return value the variable value
   */
  public abstract boolean getBoolean(final String variable);

  /**
   * Set a long variable value.
   *
   * @param variable
   *          the variable name
   * @param value
   *          the variable value
   */
  public abstract void setLong(final String variable, final long value);

  /**
   * Get a long variable value.
   *
   * @param variable
   *          the variable name
   * @return value the variable value
   */
  public abstract long getLong(final String variable);

  /**
   * Set a double variable value.
   *
   * @param variable
   *          the variable name
   * @param value
   *          the variable value
   */
  public abstract void setDouble(final String variable, final double value);

  /**
   * Get a double variable value.
   *
   * @param variable
   *          the variable name
   * @return value the variable value
   */
  public abstract double getDouble(final String variable);

  /**
   * Set a matrix variable value.
   *
   * @param variable
   *          the variable name
   * @param value
   *          the variable value
   */
  public abstract void setMatrix(final String variable, final IMatrix value);

  /**
   * Get a matrix variable value.
   *
   * @param variable
   *          the variable name
   * @return value the variable value
   */
  public abstract IMatrix getMatrix(final String variable);

  /**
   * Set a vector variable value. A vector is a matrix consisting either of
   * only one column or only one row.
   *
   * @param variable
   *          the variable name
   * @param value
   *          the variable value
   */
  public abstract void setVector(final String variable, final IMatrix value);

  /**
   * Get a vector variable value. A vector is a matrix consisting either of
   * only one column or only one row.
   *
   * @param variable
   *          the variable name
   * @return value the variable value
   */
  public abstract IMatrix getVector(final String variable);

  /**
   * Dispose a variable. This method will put best effort into freeing the
   * memory assigned to a variable. This will not change the variable's
   * type and may not change its value.
   *
   * @param variable
   *          the variable
   */
  public abstract void dispose(final String variable);

  /**
   * Execute the given script.
   *
   * @param script
   *          the script to execute.
   */
  public abstract void execute(final Iterable<String> script);

  /** {@inheritDoc} */
  @Override
  public abstract void close();
}
