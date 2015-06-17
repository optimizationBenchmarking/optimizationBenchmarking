package org.optimizationBenchmarking.utils.math.functions;

import java.io.Serializable;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.math.text.IMathRenderable;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * <p>
 * The base class for mathematical functions. It is our goal to implement
 * many mathematical functions in a way that can
 * </p>
 * <ol>
 * <li>make use of inheritance and overriding in order to allow for a great
 * versatility and provide the chance to construct more complex functions
 * and operations</li>
 * <li>is very fast and efficient to access, i.e., has routines that can
 * efficiently inlined by a compiler</li>
 * </ol>
 */
public abstract class MathematicalFunction implements Serializable,
    IMathRenderable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * The default precedence priority: it should be somewhere between an
   * addition and a multiplication.
   */
  protected static final int DEFAULT_PRECEDENCE_PRIORITY = 0;

  /**
   * Instantiate the basic function class.
   */
  protected MathematicalFunction() {
    super();
  }

  /**
   * Get the minimum arity of this function, i.e., the number of arguments
   * it requires at least.
   *
   * @return the number of arguments that this function needs at least
   * @see #getMaxArity()
   */
  public abstract int getMinArity();

  /**
   * Get the maximum arity of this function, i.e., the number of arguments
   * it permits at most.
   *
   * @return the number of arguments that this function needs accepts at
   *         most
   * @see #getMinArity()
   */
  public abstract int getMaxArity();

  /**
   * throws the illegal argument exception caused by a NaN return value for
   * an integer function.
   *
   * @throws IllegalArgumentException
   *           always
   */
  final void _throwIllegalNaN() {
    throw new IllegalArgumentException(//
        "The integer-based '" + this.toString() + //$NON-NLS-1$
            "' delegate to a real-valued calculation has returned NaN."); //$NON-NLS-1$
  }

  /**
   * throws the illegal argument exception caused by an illegal arity for
   * an integer function.
   *
   * @param length
   *          the dimension of the argument
   * @throws IllegalArgumentException
   *           always
   */
  final void _checkArity(final int length) {
    int x;

    x = this.getMinArity();
    if (length < x) {
      throw new IllegalArgumentException(//
          "The function '" + this.toString() + //$NON-NLS-1$
              "' requires at least " + x + //$NON-NLS-1$
              " parameters, but was invoked with " + length + //$NON-NLS-1$
              " arguments."); //$NON-NLS-1$
    }
    x = this.getMaxArity();
    if (length < x) {
      throw new IllegalArgumentException(//
          "The function '" + this.toString() + //$NON-NLS-1$
              "' allows at most " + x + //$NON-NLS-1$
              " parameters, but was invoked with " + length + //$NON-NLS-1$
              " arguments."); //$NON-NLS-1$
    }
  }

  /**
   * Compute the result of this function when all parameters are
   * {@code double} valued.
   *
   * @param x
   *          the vector of parameters, which must contain (at least)
   *          {@link #getMinArity()} and at most {@link #getMaxArity()}
   *          {@code double} values
   * @return the result of this function, computed in {@code double}
   *         precision
   */
  public double computeAsDouble(final double... x) {
    throw new UnsupportedOperationException();
  }

  /**
   * Compute the result of this function as {@code double} when all
   * parameters are {@code long} valued.
   *
   * @param x
   *          the vector of parameters, which must contain (at least)
   *          {@link #getMinArity()} and at most {@link #getMaxArity()}
   *          {@code long} values
   * @return the result of this function, computed in {@code double}
   *         precision
   */
  public double computeAsDouble(final long... x) {
    final double[] a;
    int i;

    i = x.length;
    this._checkArity(i);

    a = new double[i];
    for (; (--i) >= 0;) {
      a[i] = (x[i]);
    }

    return this.computeAsDouble(a);
  }

  /**
   * Compute the result of this function as {@code double} when all
   * parameters are {@code int} valued.
   *
   * @param x
   *          the vector of parameters, which must contain (at least)
   *          {@link #getMinArity()} and at most {@link #getMaxArity()}
   *          {@code int} values
   * @return the result of this function, computed in {@code double}
   *         precision
   */
  public double computeAsDouble(final int... x) {
    final long[] a;
    int i;

    i = x.length;
    this._checkArity(i);

    a = new long[i];
    for (; (--i) >= 0;) {
      a[i] = (x[i]);
    }

    return this.computeAsDouble(a);
  }

  /**
   * Compute the function value in the {@code byte} domain. This basic
   * function template delegates the computation to the {@code int} variant
   * of this function. The {@code int} result of that function is then
   * casted to {@code byte}.
   *
   * @param x
   *          the vector of parameters, which must contain at least
   *          {@link #getMinArity()} and at most {@link #getMaxArity()}
   *          {@code byte} values
   * @return the return value of this function, a byte
   * @throws IllegalArgumentException
   *           if a floating point delegate in the delegation chain returns
   *           {@code Double.NaN}.
   */
  public byte computeAsByte(final byte... x) {
    final int[] a;
    int i;
    final int r;

    i = x.length;
    this._checkArity(i);

    a = new int[i];
    for (; (--i) >= 0;) {
      a[i] = (x[i]);
    }

    r = Math.min(Byte.MAX_VALUE,
        Math.max(Byte.MIN_VALUE, this.computeAsInt(a)));
    return ((byte) (r));
  }

  /**
   * Compute the function value in the {@code short} domain. This basic
   * function template delegates the computation to the {@code int} variant
   * of this function. The {@code int} result of that function is then
   * casted to {@code short}.
   *
   * @param x
   *          the vector of parameters, which must contain at least
   *          {@link #getMinArity()} and at most {@link #getMaxArity()}
   *          {@code short} values
   * @return the return value of this function, a short
   * @throws IllegalArgumentException
   *           if a floating point delegate in the delegation chain returns
   *           {@code Double.NaN}.
   */
  public short computeAsShort(final short... x) {
    final int[] a;
    int i;
    final int r;

    i = x.length;
    this._checkArity(i);

    a = new int[i];
    for (; (--i) >= 0;) {
      a[i] = (x[i]);
    }

    r = Math.min(Short.MAX_VALUE,
        Math.max(Short.MIN_VALUE, this.computeAsInt(a)));
    return ((short) (r));
  }

  /**
   * Compute the function value in the {@code int} domain. This basic
   * function template delegates the computation to the {@code long}
   * variant of this function. The {@code long} result of that function is
   * then casted to {@code int}.
   *
   * @param x
   *          the vector of parameters, which must contain at least
   *          {@link #getMinArity()} and at most {@link #getMaxArity()}
   *          {@code int} values
   * @return the return value of this function, a int
   * @throws IllegalArgumentException
   *           if a floating point delegate in the delegation chain returns
   *           {@code Double.NaN}.
   */
  public int computeAsInt(final int... x) {
    final long[] a;
    int i;
    final long r;

    i = x.length;
    this._checkArity(i);

    a = new long[i];
    for (; (--i) >= 0;) {
      a[i] = (x[i]);
    }

    r = Math.min(Integer.MAX_VALUE,
        Math.max(Integer.MIN_VALUE, this.computeAsLong(a)));
    return ((int) (r));
  }

  /**
   * Compute the function value in the {@code long} domain. This basic
   * function template delegates the computation to the {@code double}
   * variant of this function. The {@code double} result of that function
   * is then casted to {@code long}.
   *
   * @param x
   *          the vector of parameters, which must contain at least
   *          {@link #getMinArity()} and at most {@link #getMaxArity()}
   *          {@code long} values
   * @return the return value of this function, a long
   * @throws IllegalArgumentException
   *           if a floating point delegate in the delegation chain returns
   *           {@code Double.NaN}.
   */
  public long computeAsLong(final long... x) {
    final double[] a;
    int i;
    final double r;

    i = x.length;
    this._checkArity(i);

    a = new double[i];
    for (; (--i) >= 0;) {
      a[i] = (x[i]);
    }

    r = this.computeAsDouble(a);
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
   * @param x
   *          the vector of parameters, which must contain at least
   *          {@link #getMinArity()} and at most {@link #getMaxArity()}
   *          {@code float} values
   * @return the return value of this function, a float
   */
  public float computeAsFloat(final float... x) {
    final double[] a;
    int i;
    final double r;

    i = x.length;
    this._checkArity(i);

    a = new double[i];
    for (; (--i) >= 0;) {
      a[i] = (x[i]);
    }

    r = this.computeAsDouble(a);
    return ((float) (r));
  }

  /**
   * <p>
   * Invert the function for the parameter at index {@code index}. If an
   * inverse function exists for the parameter at index {@code index}, the
   * result will a pointer to the object implementing said inverse
   * (otherwise it will be {@code null}).
   * </p>
   * <p>
   * If this function here is a n-ary function like
   * {@code f(x0, x1, x2, ...) = r}, then {@code f.invertFor(1)} should
   * return a function {@code g} such that
   * {@code g(x0, f(x0, x1, x2, ...), x1, x2, ...) = x1}, i.e., the result
   * of the original function takes the place of the {@code (index+1)}
   * <sup>th</sup> argument.
   * </p>
   * <p>
   * Notice that the existence of an inverse function for a given parameter
   * {@code index} does not mean that it is defined for all parameters. For
   * example, multiplication is an inverse for division, but a division by
   * zero may not be invertible.
   * </p>
   *
   * @param index
   *          the index of the parameter with
   *          <code>0&le;index&lt;{@link #getMaxArity()}</code>
   * @return the inverse function, if it exists, {@code null} otherwise
   */
  public MathematicalFunction invertFor(final int index) {
    return null;
  }

  /**
   * <p>
   * Get the derivative of the function for the parameter at index
   * {@code index}. If a derivative function exists for the parameter at
   * index {@code index}, the result will a pointer to the object
   * implementing said derivative (otherwise it will be {@code null}).
   * </p>
   * <p>
   * If this function here is an {@code n}-ary function, then the
   * derivative will either also be an {@code n}-ary function with the same
   * parameters as the original function, or it will be an {@code n-1}-ary
   * function where the parameter at index {@code index} is left out (if it
   * disappeared during the differentiation procedure).
   * </p>
   *
   * @param index
   *          the index of the parameter with
   *          <code>0&le;index&lt;{@link #getMaxArity()}</code>
   * @return the derivative function, if it exists, {@code null} otherwise
   */
  public MathematicalFunction derivativeFor(final int index) {
    return null;
  }

  /**
   * <p>
   * Get the integral of the function for the parameter at index
   * {@code index}. If an integral function exists for the parameter at
   * index {@code index}, the result will a pointer to the object
   * implementing said integral (otherwise it will be {@code null}). Of
   * course, all integrals could have an added constant {@code c}. We
   * assume that {@code c=0} here.
   * </p>
   *
   * @param index
   *          the index of the parameter with
   *          <code>0&le;index&lt;{@link #getMaxArity()}</code>
   * @return the integral function, if it exists, {@code null} otherwise
   */
  public MathematicalFunction integrateFor(final int index) {
    return null;
  }

  /**
   * This instance equals to any object of the same class. The reason is
   * that mathematical functions are usually singletons.
   *
   * @param o
   *          the object
   * @return {@code true} if {@code o} is an instance of the same class,
   *         {@code false} otherwise
   */
  @Override
  public boolean equals(final Object o) {
    return ((o == this) || //
    ((o != null) && (this.getClass() == o.getClass())));
  }

  /**
   * The hash code here is set to the hash code of the class, since
   * mathematical functions are usually singletons.
   *
   * @return the hash code
   */
  @Override
  public int hashCode() {
    return this.getClass().hashCode();
  }

  /**
   * Is a calculation in {@code long} arithmetic accurate? Some
   * mathematical functions will always return the correct result for
   * {@link #computeAsLong(long...)}, for example min, max, round, etc.
   * Others will not (such as {@code +} (due to possible overflows) and
   * {@code sin}).
   *
   * @return {@code true} if calculating values in {@code long} arithmetic
   *         will always return accurate results, {@code false} otherwise
   */
  public boolean isLongArithmeticAccurate() {
    return false;
  }

  /**
   * Render this mathematical function to the given text output device
   *
   * @param out
   *          the text output device
   * @param renderer
   *          the parameter renderer
   */
  @Override
  public void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    final int arity;
    char separator;
    int index;

    out.append(this.toString());
    separator = '(';
    arity = this.getMinArity();

    for (index = 0; index < arity; index++) {
      out.append(separator);
      separator = ',';
      renderer.renderParameter(index, out);
    }

    out.append(')');
  }

  /**
   * Render this mathematical function to the given math output device
   *
   * @param out
   *          the math output device
   * @param renderer
   *          the parameter renderer
   */
  @Override
  public void mathRender(final IMath out, final IParameterRenderer renderer) {
    final int arity;
    int index;

    arity = this.getMinArity();
    try (final IMath math = out.nAryFunction(//
        this.toString(), arity, arity)) {
      for (index = 0; index < arity; index++) {
        renderer.renderParameter(index, math);
      }
    }
  }

  /**
   * Obtain the <a
   * href="https://en.wikipedia.org/wiki/Order_of_operations">precedence
   * priority</a> of this operator. The higher the returned value is, the
   * more "binding" it is to its arguments.
   *
   * @return the precedence priority
   */
  public int getPrecedencePriority() {
    return MathematicalFunction.DEFAULT_PRECEDENCE_PRIORITY;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return this.getClass().getSimpleName().toLowerCase();
  }
}
