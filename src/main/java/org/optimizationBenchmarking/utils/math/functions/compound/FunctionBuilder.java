package org.optimizationBenchmarking.utils.math.functions.compound;

import java.util.Collection;
import java.util.Iterator;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;
import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;
import org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction;
import org.optimizationBenchmarking.utils.math.functions.TernaryFunction;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/**
 * A function builder helps us to create compound mathematical functions
 *
 * @param <T>
 *          the function type
 */
public abstract class FunctionBuilder<T extends MathematicalFunction> {

  /** Create the function builder */
  FunctionBuilder() {
    super();
  }

  /**
   * Apply a
   * {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   * unary function} {@code func} to the result of the function
   * {@code param1}.
   *
   * @param func
   *          the
   *          {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   *          unary function} function to be used for transforming results
   * @param param1
   *          the input function
   * @return a function whose result is the result of {@code func} applied
   *         to the result of {@code param1}.
   */
  public abstract T compound(final UnaryFunction func, final T param1);

  /**
   * Apply a
   * {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
   * binary function} {@code func} to the results of the functions
   * {@code param1} and {@code param2}.
   *
   * @param func
   *          the
   *          {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
   *          binary function} to be used for transforming results
   * @param param1
   *          the first input function
   * @param param2
   *          the second input function
   * @return a function whose result is the result of {@code func} applied
   *         to the results of {@code param1} and {@code param2}.
   */
  public abstract T compound(final BinaryFunction func, final T param1,
      final T param2);

  /**
   * Apply a
   * {@link org.optimizationBenchmarking.utils.math.functions.TernaryFunction
   * ternary function} {@code func} to the results of the functions
   * {@code param1}, {@code param2}, and {@code param3}.
   *
   * @param func
   *          the
   *          {@link org.optimizationBenchmarking.utils.math.functions.TernaryFunction
   *          ternary function} to be used for transforming results
   * @param param1
   *          the first input function
   * @param param2
   *          the second input function
   * @param param3
   *          the third input function
   * @return a function whose result is the result of {@code func} applied
   *         to the results of {@code param1}, {@code param2}, and
   *         {@code param3}.
   */
  public abstract T compound(final TernaryFunction func, final T param1,
      final T param2, final T param3);

  /**
   * Apply a
   * {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction
   * quaternary function} {@code func} to the results of the functions
   * {@code param1}, {@code param2}, {@code param3}, and {@code param4}.
   *
   * @param func
   *          the
   *          {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction
   *          quaternary function} to be used for transforming results
   * @param param1
   *          the first input function
   * @param param2
   *          the second input function
   * @param param3
   *          the third input function
   * @param param4
   *          the fourth input parameter
   * @return a function whose result is the result of {@code func} applied
   *         to the results of {@code param1}, {@code param2},
   *         {@code param3}, and {@code param4}.
   */
  public abstract T compound(final QuaternaryFunction func,
      final T param1, final T param2, final T param3, final T param4);

  /**
   * Create a compound function based on an arbitrary number of parameters
   *
   * @param func
   *          the function
   * @param params
   *          the parameters
   * @return the compound
   */
  @SafeVarargs
  public final T compound(final MathematicalFunction func,
      final T... params) {
    return this.compound(func, new ArrayListView<>(params));
  }

  /**
   * Create a compound function based on an arbitrary number of parameters
   *
   * @param func
   *          the function
   * @param params
   *          the parameters
   * @return the compound
   */
  public final T compound(final MathematicalFunction func,
      final Collection<T> params) {
    final int min, max, size;
    final Iterator<T> it;

    if (func == null) {
      throw new IllegalArgumentException(//
          "Mathematical function must not be null."); //$NON-NLS-1$
    }
    if (params == null) {
      throw new IllegalArgumentException(//
          "Parameter array for function " + func + //$NON-NLS-1$
              " must not be null."); //$NON-NLS-1$
    }

    min = func.getMinArity();
    max = func.getMaxArity();
    size = params.size();
    if ((size < min) || (size > max)) {
      throw new IllegalArgumentException(//
          "Function " + func + //$NON-NLS-1$
              " requires between " + min + //$NON-NLS-1$
              " and " + max + //$NON-NLS-1$
              " arguments, but you specified " + size); //$NON-NLS-1$
    }

    it = params.iterator();
    if (func instanceof UnaryFunction) {
      return this.compound(((UnaryFunction) func), it.next());
    }
    if (func instanceof BinaryFunction) {
      return this.compound(((BinaryFunction) func), it.next(), it.next());
    }
    if (func instanceof TernaryFunction) {
      return this.compound(((TernaryFunction) func), it.next(), it.next(),
          it.next());
    }
    if (func instanceof QuaternaryFunction) {
      return this.compound(((QuaternaryFunction) func), it.next(),
          it.next(), it.next(), it.next());
    }

    throw new IllegalArgumentException("Function " + func + //$NON-NLS-1$
        " is not supported.");//$NON-NLS-1$
  }

  /**
   * Choose {@code index}th parameter from the set of parameters
   *
   * @param index
   *          the {@code 0}-based index of the parameter to choose
   * @return a function returning this parameter
   */
  public abstract T parameter(final int index);

  /**
   * Create a {@code byte} constant of the given value
   *
   * @param value
   *          the {@code byte} value
   * @return the constant function, which will always return {@code value}
   */
  public final T constant(final byte value) {
    return this.constant(NumericalTypes.valueOf(value));
  }

  /**
   * Create a {@code int} constant of the given value
   *
   * @param value
   *          the {@code int} value
   * @return the constant function, which will always return {@code value}
   */
  public final T constant(final int value) {
    return this.constant(NumericalTypes.valueOf(value));
  }

  /**
   * Create a {@code short} constant of the given value
   *
   * @param value
   *          the {@code short} value
   * @return the constant function, which will always return {@code value}
   */
  public final T constant(final short value) {
    return this.constant(NumericalTypes.valueOf(value));
  }

  /**
   * Create a {@code long} constant of the given value
   *
   * @param value
   *          the {@code long} value
   * @return the constant function, which will always return {@code value}
   */
  public final T constant(final long value) {
    return this.constant(NumericalTypes.valueOf(value));
  }

  /**
   * Create a {@code float} constant of the given value
   *
   * @param value
   *          the {@code float} value
   * @return the constant function, which will always return {@code value}
   */
  public final T constant(final float value) {
    return this.constant(NumericalTypes.valueOf(value));
  }

  /**
   * Create a {@code double} constant of the given value
   *
   * @param value
   *          the {@code double} value
   * @return the constant function, which will always return {@code value}
   */
  public final T constant(final double value) {
    return this.constant(NumericalTypes.valueOf(value));
  }

  /**
   * Create a constant of the given {@link java.lang.Number} value
   *
   * @param value
   *          the {@link java.lang.Number} value
   * @return the constant function, which will always return {@code value}
   */
  public abstract T constant(final Number value);

  /**
   * Obtain the class of the functions which will be created by this
   * builder
   *
   * @return the class of the functions which will be created by this
   *         builder
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Class<T> getFunctionClass() {
    return ((Class) (MathematicalFunction.class));
  }

  /**
   * Get the number of arguments of the functions which will be created by
   * this builder
   *
   * @return the number of arguments of the functions which will be created
   *         by this builder
   */
  public abstract int getFunctionArity();

  /**
   * Obtain an instance of the function builder for functions of the given
   * arity.
   *
   * @param arity
   *          the arity
   * @return the builder
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static final FunctionBuilder<MathematicalFunction> getInstanceForArity(
      final int arity) {
    switch (arity) {
      case 1: {
        return ((FunctionBuilder) (UnaryFunctionBuilder.getInstance()));
      }
      case 2: {
        return ((FunctionBuilder) (BinaryFunctionBuilder.getInstance()));
      }
      case 3: {
        return ((FunctionBuilder) (TernaryFunctionBuilder.getInstance()));
      }
      case 4: {
        return ((FunctionBuilder) (QuaternaryFunctionBuilder.getInstance()));
      }
      default: {
        throw new IllegalArgumentException((//
            "Function builders only support arity 1 to 4, but you provided " //$NON-NLS-1$
            + arity) + '.');//
      }
    }
  }
}
