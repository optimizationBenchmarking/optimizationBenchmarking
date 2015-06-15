package org.optimizationBenchmarking.utils.math.functions.compound;

import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;
import org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction;
import org.optimizationBenchmarking.utils.math.functions.TernaryFunction;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/**
 * This is the automatically generated code for a
 * {@link org.optimizationBenchmarking.utils.math.functions.compound
 * builder} of
 * {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
 * 2-ary} functions.
 */
public final class BinaryFunctionBuilder extends
FunctionBuilder<BinaryFunction> {
  /**
   * The globally shared instance of the
   * {@link org.optimizationBenchmarking.utils.math.functions.compound
   * builder} of
   * {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
   * 2-ary} functions.
   */
  private static final BinaryFunctionBuilder INSTANCE = new BinaryFunctionBuilder();

  /**
   * Create the
   * {@link org.optimizationBenchmarking.utils.math.functions.compound
   * builder} of
   * {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
   * 2-ary} functions.
   */
  private BinaryFunctionBuilder() {
    super();
  }

  /**
   * Get an instance of the
   * {@link org.optimizationBenchmarking.utils.math.functions.compound
   * builder} of
   * {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
   * 2-ary} functions.
   *
   * @return an instance of the
   *         {@link org.optimizationBenchmarking.utils.math.functions.compound
   *         builder} of
   *         {@link org.optimizationBenchmarking.utils.math.functions.BinaryFunction
   *         2-ary} functions.
   */
  public static final BinaryFunctionBuilder getInstance() {
    return BinaryFunctionBuilder.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final BinaryFunction compound(final UnaryFunction func,
      final BinaryFunction param1) {
    return new _Compound1x2(func, param1);
  }

  /** {@inheritDoc} */
  @Override
  public final BinaryFunction compound(final BinaryFunction func,
      final BinaryFunction param1, final BinaryFunction param2) {
    return new _Compound2x2(func, param1, param2);
  }

  /** {@inheritDoc} */
  @Override
  public final BinaryFunction compound(final TernaryFunction func,
      final BinaryFunction param1, final BinaryFunction param2,
      final BinaryFunction param3) {
    return new _Compound3x2(func, param1, param2, param3);
  }

  /** {@inheritDoc} */
  @Override
  public final BinaryFunction compound(final QuaternaryFunction func,
      final BinaryFunction param1, final BinaryFunction param2,
      final BinaryFunction param3, final BinaryFunction param4) {
    return new _Compound4x2(func, param1, param2, param3, param4);
  }

  /** {@inheritDoc} */
  @Override
  public final BinaryFunction constant(final Number value) {
    return new _Const2(value);
  }

  /** {@inheritDoc} */
  @Override
  public final BinaryFunction parameter(final int index) {
    switch (index) {
      case 0: {
        return _Select1of2.INSTANCE;
      }
      case 1: {
        return _Select2of2.INSTANCE;
      }
      default: {
        throw new IllegalArgumentException( //
            "The parameter index must be in 0..1, but " //$NON-NLS-1$
            + index + " was specified."); //$NON-NLS-1$
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final int getFunctionArity() {
    return 2;
  }

  /** {@inheritDoc} */
  @Override
  public final Class<BinaryFunction> getFunctionClass() {
    return BinaryFunction.class;
  }
}
