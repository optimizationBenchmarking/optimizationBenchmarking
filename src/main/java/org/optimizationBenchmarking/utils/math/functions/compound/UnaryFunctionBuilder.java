package org.optimizationBenchmarking.utils.math.functions.compound;

import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;
import org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction;
import org.optimizationBenchmarking.utils.math.functions.TernaryFunction;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Identity;
import org.optimizationBenchmarking.utils.math.functions.power.Cbrt;
import org.optimizationBenchmarking.utils.math.functions.power.Cube;
import org.optimizationBenchmarking.utils.math.functions.power.Exp;
import org.optimizationBenchmarking.utils.math.functions.power.Pow;
import org.optimizationBenchmarking.utils.math.functions.power.Pow10;
import org.optimizationBenchmarking.utils.math.functions.power.Pow2;
import org.optimizationBenchmarking.utils.math.functions.power.Sqr;
import org.optimizationBenchmarking.utils.math.functions.power.Sqrt;

/**
 * This is the automatically generated code for a
 * {@link org.optimizationBenchmarking.utils.math.functions.compound
 * builder} of
 * {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
 * 1-ary} functions.
 */
public final class UnaryFunctionBuilder extends
FunctionBuilder<UnaryFunction> {
  /**
   * The globally shared instance of the
   * {@link org.optimizationBenchmarking.utils.math.functions.compound
   * builder} of
   * {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   * 1-ary} functions.
   */
  private static final UnaryFunctionBuilder INSTANCE = new UnaryFunctionBuilder();

  /**
   * Create the
   * {@link org.optimizationBenchmarking.utils.math.functions.compound
   * builder} of
   * {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   * 1-ary} functions.
   */
  private UnaryFunctionBuilder() {
    super();
  }

  /**
   * Get an instance of the
   * {@link org.optimizationBenchmarking.utils.math.functions.compound
   * builder} of
   * {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   * 1-ary} functions.
   *
   * @return an instance of the
   *         {@link org.optimizationBenchmarking.utils.math.functions.compound
   *         builder} of
   *         {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
   *         1-ary} functions.
   */
  public static final UnaryFunctionBuilder getInstance() {
    return UnaryFunctionBuilder.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction compound(final UnaryFunction func,
      final UnaryFunction param1) {
    if (param1 instanceof Identity) {
      return func;
    }
    if (func instanceof Identity) {
      return param1;
    }
    return new _Compound1x1(func, param1);
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction compound(final BinaryFunction func,
      final UnaryFunction param1, final UnaryFunction param2) {
    double constant;
    if (func instanceof Pow) {
      if (param1 instanceof _Const1) {
        constant = ((_Const1) param1).m_const.doubleValue();
        if (constant == 2d) {
          return this.compound(Pow2.INSTANCE, param2);
        }
        if (constant == 10d) {
          return this.compound(Pow10.INSTANCE, param2);
        }
        if (constant == Math.E) {
          return this.compound(Exp.INSTANCE, param2);
        }
      }
      if (param2 instanceof _Const1) {
        constant = ((_Const1) param2).m_const.doubleValue();
        if (constant == 2d) {
          return this.compound(Sqr.INSTANCE, param1);
        }
        if (constant == 3d) {
          return this.compound(Cube.INSTANCE, param1);
        }
        if (constant == 0.5d) {
          return this.compound(Sqrt.INSTANCE, param1);
        }
        if (constant == (1d / 3d)) {
          return this.compound(Cbrt.INSTANCE, param1);
        }
      }
    }
    return new _Compound2x1(func, param1, param2);
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction compound(final TernaryFunction func,
      final UnaryFunction param1, final UnaryFunction param2,
      final UnaryFunction param3) {
    return new _Compound3x1(func, param1, param2, param3);
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction compound(final QuaternaryFunction func,
      final UnaryFunction param1, final UnaryFunction param2,
      final UnaryFunction param3, final UnaryFunction param4) {
    return new _Compound4x1(func, param1, param2, param3, param4);
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction constant(final Number value) {
    return new _Const1(value);
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction parameter(final int index) {
    switch (index) {
      case 0: {
        return Identity.INSTANCE;
      }
      default: {
        throw new IllegalArgumentException( //
            "The parameter index must be in 0..0, but " //$NON-NLS-1$
            + index + " was specified."); //$NON-NLS-1$
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final int getFunctionArity() {
    return 1;
  }

  /** {@inheritDoc} */
  @Override
  public final Class<UnaryFunction> getFunctionClass() {
    return UnaryFunction.class;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return (o instanceof UnaryFunctionBuilder);
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return 727;
  }
}
