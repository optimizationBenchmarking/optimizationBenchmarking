package org.optimizationBenchmarking.utils.math.mathEngine.spec;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/**
 * An expression allows you to transmit a value to a math engine or to
 * compute a function. You can only transmit a single value or compute a
 * single function value.
 */
public interface IExpression extends IMathEngineScope {

  /**
   * The expression has a matrix value.
   * 
   * @param matrix
   *          the matrix
   */
  public abstract void matrixValue(final IMatrix matrix);

  /**
   * The expression has a vector value. A vector is a matrix with either
   * only a single column or only a single row.
   * 
   * @param vector
   *          the vector
   */
  public abstract void vectorValue(final IMatrix vector);

  /**
   * The expression has a {@code double} value
   * 
   * @param d
   *          the {@code double} value
   */
  public abstract void doubleValue(final double d);

  /**
   * The expression has a {@code long} value
   * 
   * @param d
   *          the {@code long} value
   */
  public abstract void longValue(final long d);

  /**
   * The expression has a {@code boolean} value
   * 
   * @param d
   *          the {@code boolean} value
   */
  public abstract void booleanValue(final boolean d);

  /**
   * The expression has the value of a given {@code variable}
   * 
   * @param variable
   *          the variable
   */
  public abstract void variableValue(final IVariable variable);

  /**
   * The expression has the value of a function result
   * 
   * @param name
   *          the name of the function
   * @return the scope to fill in the function parameters
   */
  public abstract IFunction functionResult(final String name);

  /**
   * The determinant of a matrix
   * 
   * @return the matrix determinant
   */
  public abstract IFunction determinant();

  /**
   * The result of a multiplication
   * 
   * @return the result of a multiplication
   */
  public abstract IFunction mul();

  /**
   * The result of a division
   * 
   * @return the result of a division
   */
  public abstract IFunction div();

  /**
   * The result of a addition
   * 
   * @return the result of a addition
   */
  public abstract IFunction add();

  /**
   * The result of a subtraction
   * 
   * @return the result of a subtraction
   */
  public abstract IFunction sub();
}
