package org.optimizationBenchmarking.utils.math.functions;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/**
 * <p>
 * The base class for unary functions, i.e., mathematical functions that
 * accept exactly 1 argument.
 * </p>
 */
public class UnaryFunction extends MathematicalFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** Instantiate the base class for unary functions */
  protected UnaryFunction() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final int getMinArity() {
    return 1;
  }

  /** {@inheritDoc} */
  @Override
  public final int getMaxArity() {
    return 1;
  }

  /**
   * Delegate the function call with open parameter count to the
   * corresponding function with exactly 1 parameters.
   * 
   * @param x
   *          the parameter array
   * @return the return value of this function, a byte
   */
  @Override
  public final byte compute(final byte... x) {
    this._checkArity(x.length);
    return this.compute(x[0]);
  }

  /**
   * Delegate the function call with open parameter count to the
   * corresponding function with exactly 1 parameters.
   * 
   * @param x
   *          the parameter array
   * @return the return value of this function, a short
   */
  @Override
  public final short compute(final short... x) {
    this._checkArity(x.length);
    return this.compute(x[0]);
  }

  /**
   * Delegate the function call with open parameter count to the
   * corresponding function with exactly 1 parameters.
   * 
   * @param x
   *          the parameter array
   * @return the return value of this function, a int
   */
  @Override
  public final int compute(final int... x) {
    this._checkArity(x.length);
    return this.compute(x[0]);
  }

  /**
   * Delegate the function call with open parameter count to the
   * corresponding function with exactly 1 parameters.
   * 
   * @param x
   *          the parameter array
   * @return the return value of this function, a long
   */
  @Override
  public final long compute(final long... x) {
    this._checkArity(x.length);
    return this.compute(x[0]);
  }

  /**
   * Delegate the function call with open parameter count to the
   * corresponding function with exactly 1 parameters.
   * 
   * @param x
   *          the parameter array
   * @return the return value of this function, a float
   */
  @Override
  public final float compute(final float... x) {
    this._checkArity(x.length);
    return this.compute(x[0]);
  }

  /**
   * Delegate the function call with open parameter count to the
   * corresponding function with exactly 1 parameters.
   * 
   * @param x
   *          the parameter array
   * @return the return value of this function, a double
   */
  @Override
  public final double compute(final double... x) {
    this._checkArity(x.length);
    return this.compute(x[0]);
  }

  /**
   * Compute the function value in the {@code byte} domain. This basic
   * function template delegates the computation to the {@code int} variant
   * of this function. The {@code int} result of that function is then
   * casted to {@code byte}.
   * 
   * @param x0
   *          the 1st byte argument of the function
   * @return the return value of this function, a {@code byte}
   */
  public byte compute(final byte x0) {
    final int r;

    r = Math.min(Byte.MAX_VALUE,
        Math.max(Byte.MIN_VALUE, this.compute(((int) (x0)))));

    return ((byte) (r));
  }

  /**
   * Compute the function value in the {@code short} domain. This basic
   * function template delegates the computation to the {@code int} variant
   * of this function. The {@code int} result of that function is then
   * casted to {@code short}.
   * 
   * @param x0
   *          the 1st short argument of the function
   * @return the return value of this function, a {@code short}
   */
  public short compute(final short x0) {
    final int r;

    r = Math.min(Short.MAX_VALUE,
        Math.max(Short.MIN_VALUE, this.compute(((int) (x0)))));

    return ((short) (r));
  }

  /**
   * Compute the function value in the {@code int} domain. This basic
   * function template delegates the computation to the {@code long}
   * variant of this function. The {@code long} result of that function is
   * then casted to {@code int}.
   * 
   * @param x0
   *          the 1st int argument of the function
   * @return the return value of this function, a {@code int}
   */
  public int compute(final int x0) {
    final long r;

    r = this.compute(((long) (x0)));

    return ((int) (Math.min(Integer.MAX_VALUE,
        Math.max(Integer.MIN_VALUE, r))));
  }

  /**
   * Compute the function value in the {@code long} domain. This basic
   * function template delegates the computation to the {@code double}
   * variant of this function. The {@code double} result of that function
   * is then casted to {@code long}.
   * 
   * @param x0
   *          the 1st long argument of the function
   * @return the return value of this function, a {@code long}
   */
  public long compute(final long x0) {
    final double r;

    r = this.compute(((double) (x0)));

    if (r <= (java.lang.Double.NEGATIVE_INFINITY)) {
      return (java.lang.Long.MIN_VALUE);
    }
    if (r >= (java.lang.Double.POSITIVE_INFINITY)) {
      return (java.lang.Long.MAX_VALUE);
    }
    if (java.lang.Double.isNaN(r)) {
      this._throwIllegalNaN();
    }
    return ((long) ((r)));
  }

  /**
   * Compute the function value in the {@code float} domain. This basic
   * function template delegates the computation to the {@code double}
   * variant of this function. The {@code double} result of that function
   * is then casted to {@code float}.
   * 
   * @param x0
   *          the 1st float argument of the function
   * @return the return value of this function, a {@code float}
   */
  public float compute(final float x0) {
    final double r;

    r = this.compute(((double) (x0)));

    return ((float) (r));
  }

  /**
   * Compute the function value in the {@code double} domain. Every
   * sub-class of this class must, at least, override this function.
   * 
   * @param x0
   *          the 1st {@code double} argument of the function
   * @return the return value of this function, a {@code double}
   */
  public double compute(final double x0) {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  @Override
  public UnaryFunction invertFor(final int index) {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public UnaryFunction integrateFor(final int index) {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public final double computeOverColumn(final IMatrix matrix,
      final int column) {
    this._checkArity(matrix.m());
    return this.compute(matrix.getDouble(0, column));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeOverRow(final IMatrix matrix, final int row) {
    this._checkArity(matrix.n());
    return this.compute(matrix.getDouble(row, 0));
  }
}
