package org.optimizationBenchmarking.utils.math.functions.compound;

import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;
import org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction;
import org.optimizationBenchmarking.utils.math.functions.TernaryFunction;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/**
 * This is the automatically generated code for a
 * {@link org.optimizationBenchmarking.utils.math.functions.compound
 * builder} of
 * {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction
 * 4-ary} functions.
 */
public final class QuaternaryFunctionBuilder extends
FunctionBuilder<QuaternaryFunction> {
  /**
   * The globally shared instance of the
   * {@link org.optimizationBenchmarking.utils.math.functions.compound
   * builder} of
   * {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction
   * 4-ary} functions.
   */
  private static final QuaternaryFunctionBuilder INSTANCE = new QuaternaryFunctionBuilder();

  /**
   * Create the
   * {@link org.optimizationBenchmarking.utils.math.functions.compound
   * builder} of
   * {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction
   * 4-ary} functions.
   */
  private QuaternaryFunctionBuilder() {
    super();
  }

  /**
   * Get an instance of the
   * {@link org.optimizationBenchmarking.utils.math.functions.compound
   * builder} of
   * {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction
   * 4-ary} functions.
   *
   * @return an instance of the
   *         {@link org.optimizationBenchmarking.utils.math.functions.compound
   *         builder} of
   *         {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction
   *         4-ary} functions.
   */
  public static final QuaternaryFunctionBuilder getInstance() {
    return QuaternaryFunctionBuilder.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final QuaternaryFunction compound(final UnaryFunction func,
      final QuaternaryFunction param1) {
    return new _Compound1x4(func, param1);
  }

  /** {@inheritDoc} */
  @Override
  public final QuaternaryFunction compound(final BinaryFunction func,
      final QuaternaryFunction param1, final QuaternaryFunction param2) {
    return new _Compound2x4(func, param1, param2);
  }

  /** {@inheritDoc} */
  @Override
  public final QuaternaryFunction compound(final TernaryFunction func,
      final QuaternaryFunction param1, final QuaternaryFunction param2,
      final QuaternaryFunction param3) {
    return new _Compound3x4(func, param1, param2, param3);
  }

  /** {@inheritDoc} */
  @Override
  public final QuaternaryFunction compound(final QuaternaryFunction func,
      final QuaternaryFunction param1, final QuaternaryFunction param2,
      final QuaternaryFunction param3, final QuaternaryFunction param4) {
    return new _Compound4x4(func, param1, param2, param3, param4);
  }

  /** {@inheritDoc} */
  @Override
  public final QuaternaryFunction constant(final Number value) {
    return new _Const4(value);
  }

  /** {@inheritDoc} */
  @Override
  public final QuaternaryFunction parameter(final int index) {
    switch (index) {
      case 0: {
        return _Select1of4.INSTANCE;
      }
      case 1: {
        return _Select2of4.INSTANCE;
      }
      case 2: {
        return _Select3of4.INSTANCE;
      }
      case 3: {
        return _Select4of4.INSTANCE;
      }
      default: {
        throw new IllegalArgumentException( //
            "The parameter index must be in 0..3, but " //$NON-NLS-1$
            + index + " was specified."); //$NON-NLS-1$
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final int getFunctionArity() {
    return 4;
  }

  /** {@inheritDoc} */
  @Override
  public final Class<QuaternaryFunction> getFunctionClass() {
    return QuaternaryFunction.class;
  }
}
