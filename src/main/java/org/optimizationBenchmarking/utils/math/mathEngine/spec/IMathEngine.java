package org.optimizationBenchmarking.utils.math.mathEngine.spec;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.tools.spec.IToolJob;

/**
 * A math engine represents a closeable communication device to an external
 * mathematical engine.
 */
public interface IMathEngine extends IToolJob, IMathEngineScope {

  /**
   * Assign the value of a variable
   *
   * @param variable
   *          the variable
   * @return the assignment
   */
  public abstract IAssignment assign(final IVariable variable);

  /**
   * Create an assignment which automatically also creates the variable to
   * be assigned
   *
   * @return the assignment
   */
  public abstract IAssignment assign();

  /**
   * Dispose a variable, i.e., free the memory allocated to it
   *
   * @param var
   *          the variable to dispose of
   */
  public abstract void dispose(final IVariable var);

  /**
   * Read the value of a variable as matrix
   *
   * @param variable
   *          the variable
   * @return the value as matrix
   */
  public abstract IMatrix getAsMatrix(final IVariable variable);

  /**
   * Read the value of a variable as {@code double}
   *
   * @param variable
   *          the variable
   * @return the value as {@code double}
   */
  public abstract double getAsDouble(final IVariable variable);

  /**
   * Read the value of a variable as {@code long}
   *
   * @param variable
   *          the variable
   * @return the value as {@code long}
   */
  public abstract long getAsLong(final IVariable variable);

  /**
   * Read the value of a variable as {@code boolean}
   *
   * @param variable
   *          the variable
   * @return the value as {@code boolean}
   */
  public abstract boolean getAsBoolean(final IVariable variable);
}
