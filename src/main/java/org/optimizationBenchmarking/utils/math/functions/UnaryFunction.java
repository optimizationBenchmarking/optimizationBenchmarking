package org.optimizationBenchmarking.utils.math.functions;

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
   * corresponding function with exactly 1 parameter.
   *
   * @param x
   *          the parameter array
   * @return the return value of this function, a byte
   */
  @Override
  public final byte computeAsByte(final byte... x) {
    this._checkArity(x.length);
    return this.computeAsByte(x[0]);
  }

  /**
   * Delegate the function call with open parameter count to the
   * corresponding function with exactly 1 parameter.
   *
   * @param x
   *          the parameter array
   * @return the return value of this function, a short
   */
  @Override
  public final short computeAsShort(final short... x) {
    this._checkArity(x.length);
    return this.computeAsShort(x[0]);
  }

  /**
   * Delegate the function call with open parameter count to the
   * corresponding function with exactly 1 parameter.
   *
   * @param x
   *          the parameter array
   * @return the return value of this function, a int
   */
  @Override
  public final int computeAsInt(final int... x) {
    this._checkArity(x.length);
    return this.computeAsInt(x[0]);
  }

  /**
   * Delegate the function call with open parameter count to the
   * corresponding function with exactly 1 parameter.
   *
   * @param x
   *          the parameter array
   * @return the return value of this function, a long
   */
  @Override
  public final long computeAsLong(final long... x) {
    this._checkArity(x.length);
    return this.computeAsLong(x[0]);
  }

  /**
   * Delegate the function call with open parameter count to the
   * corresponding function with exactly 1 parameter.
   *
   * @param x
   *          the parameter array
   * @return the return value of this function, a float
   */
  @Override
  public final float computeAsFloat(final float... x) {
    this._checkArity(x.length);
    return this.computeAsFloat(x[0]);
  }

  /**
   * Delegate the function call with open parameter count to the
   * corresponding function with exactly 1 parameter.
   *
   * @param x
   *          the parameter array
   * @return the return value of this function, a double
   */
  @Override
  public final double computeAsDouble(final double... x) {
    this._checkArity(x.length);
    return this.computeAsDouble(x[0]);
  }

  /**
   * Compute the function value in the {@code byte} domain. This basic
   * function template delegates the computation to the {@code int} variant
   * of this function. The {@code int} result of that function is then
   * casted to {@code byte}.
   *
   * @param x0
   *          the byte argument of the function
   * @return the return value of this function, a {@code byte}
   */
  public byte computeAsByte(final byte x0) {
    final int r;

    r = Math.min(Byte.MAX_VALUE,
        Math.max(Byte.MIN_VALUE, this.computeAsInt(x0)));

    return ((byte) (r));
  }

  /**
   * Compute the function value in the {@code short} domain. This basic
   * function template delegates the computation to the {@code int} variant
   * of this function. The {@code int} result of that function is then
   * casted to {@code short}.
   *
   * @param x0
   *          the short argument of the function
   * @return the return value of this function, a {@code short}
   */
  public short computeAsShort(final short x0) {
    final int r;

    r = Math.min(Short.MAX_VALUE,
        Math.max(Short.MIN_VALUE, this.computeAsInt(x0)));

    return ((short) (r));
  }

  /**
   * Compute the function value in the {@code int} domain. This basic
   * function template delegates the computation to the {@code long}
   * variant of this function. The {@code long} result of that function is
   * then casted to {@code int}.
   *
   * @param x0
   *          the int argument of the function
   * @return the return value of this function, a {@code int}
   */
  public int computeAsInt(final int x0) {
    final long r;

    r = this.computeAsLong(x0);

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
   *          the long argument of the function
   * @return the return value of this function, a {@code long}
   */
  public long computeAsLong(final long x0) {
    final double r;

    r = this.computeAsDouble(x0);

    if (r <= java.lang.Long.MIN_VALUE) {
      return java.lang.Long.MIN_VALUE;
    }
    if (r >= java.lang.Long.MAX_VALUE) {
      return java.lang.Long.MAX_VALUE;
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
   *          the float argument of the function
   * @return the return value of this function, a {@code float}
   */
  public float computeAsFloat(final float x0) {
    final double r;

    r = this.computeAsDouble(x0);

    return ((float) (r));
  }

  /**
   * Compute the function value in the {@code double} domain. Every
   * sub-class of this class must, at least, override this function.
   *
   * @param x0
   *          the {@code double} argument of the function
   * @return the return value of this function, a {@code double}
   */
  public double computeAsDouble(final double x0) {
    throw new UnsupportedOperationException();
  }

  /**
   * Compute the function result as {@code double} based on an {@code long}
   * input
   *
   * @param x0
   *          the {@code long} argument of the function
   * @return the return value of this function, a {@code double}
   */
  public double computeAsDouble(final long x0) {
    return this.computeAsDouble((double) x0);
  }

  /**
   * Compute the function result as {@code double} based on an {@code int}
   * input
   *
   * @param x0
   *          the {@code int} argument of the function
   * @return the return value of this function, a {@code double}
   */
  public double computeAsDouble(final int x0) {
    return this.computeAsDouble((long) x0);
  }

  /**
   * Delegate the function call with open parameter count to the
   * corresponding function with exactly 1 parameter.
   *
   * @param x
   *          the parameter array
   * @return the return value of this function, a double
   */
  @Override
  public final double computeAsDouble(final long... x) {
    this._checkArity(x.length);
    return this.computeAsDouble(x[0]);
  }

  /**
   * Delegate the function call with open parameter count to the
   * corresponding function with exactly 1 parameter.
   *
   * @param x
   *          the parameter array
   * @return the return value of this function, a double
   */
  @Override
  public final double computeAsDouble(final int... x) {
    this._checkArity(x.length);
    return this.computeAsDouble(x[0]);
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
}
