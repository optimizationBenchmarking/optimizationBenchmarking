package org.optimizationBenchmarking.utils.math.functions.compound;

import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;
import org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction;
import org.optimizationBenchmarking.utils.math.functions.TernaryFunction;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Identity;

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
    return new _Compound1x1(func, param1);
  }

  /** {@inheritDoc} */
  @Override
  public final UnaryFunction compound(final BinaryFunction func,
      final UnaryFunction param1, final UnaryFunction param2) {
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
}
