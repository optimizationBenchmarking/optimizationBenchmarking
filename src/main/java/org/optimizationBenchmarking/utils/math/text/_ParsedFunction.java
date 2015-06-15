package org.optimizationBenchmarking.utils.math.text;

import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Div;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.ModuloDivisorSign;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Mul;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Negate;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Sub;
import org.optimizationBenchmarking.utils.math.functions.binary.BAnd;
import org.optimizationBenchmarking.utils.math.functions.binary.BNot;
import org.optimizationBenchmarking.utils.math.functions.combinatoric.Factorial;
import org.optimizationBenchmarking.utils.math.functions.power.Cbrt;
import org.optimizationBenchmarking.utils.math.functions.power.Cube;
import org.optimizationBenchmarking.utils.math.functions.power.Exp;
import org.optimizationBenchmarking.utils.math.functions.power.Pow;
import org.optimizationBenchmarking.utils.math.functions.power.Pow10;
import org.optimizationBenchmarking.utils.math.functions.power.Pow2;
import org.optimizationBenchmarking.utils.math.functions.power.Sqr;

/** A function has been parsed. */
final class _ParsedFunction {

  /** a symbol is a prefix */
  static final int MODE_PREFIX = 0;
  /** a symbol is an infix */
  static final int MODE_INFIX = (_ParsedFunction.MODE_PREFIX + 1);
  /** a symbol is an postfix */
  static final int MODE_POSTFIX = (_ParsedFunction.MODE_INFIX + 1);
  /** a symbol is a function call */
  static final int MODE_CALL = (_ParsedFunction.MODE_POSTFIX + 1);

  /** the add in infix mode */
  static final _ParsedFunction ADD_INFIX = new _ParsedFunction(
      Add.INSTANCE, _ParsedFunction.MODE_INFIX);

  /** the sub in infix mode */
  static final _ParsedFunction SUB_INFIX = new _ParsedFunction(
      Sub.INSTANCE, _ParsedFunction.MODE_INFIX);
  /** the negate in prefix mode */
  static final _ParsedFunction NEGATE_PREFIX = new _ParsedFunction(
      Negate.INSTANCE, _ParsedFunction.MODE_PREFIX);

  /** the mul in infix mode */
  static final _ParsedFunction MUL_INFIX = new _ParsedFunction(
      Mul.INSTANCE, _ParsedFunction.MODE_INFIX);

  /** the div in infix mode */
  static final _ParsedFunction DIV_INFIX = new _ParsedFunction(
      Div.INSTANCE, _ParsedFunction.MODE_INFIX);

  /** the mod in infix mode */
  static final _ParsedFunction MOD_INFIX = new _ParsedFunction(
      ModuloDivisorSign.INSTANCE, _ParsedFunction.MODE_INFIX);

  /** the bit-wise and in infix mode */
  static final _ParsedFunction BAND_INFIX = new _ParsedFunction(
      BAnd.INSTANCE, _ParsedFunction.MODE_INFIX);

  /** the bit-wise in prefix mode */
  static final _ParsedFunction BNOT_PREFIX = new _ParsedFunction(
      BNot.INSTANCE, _ParsedFunction.MODE_PREFIX);

  /** the factorial in postfix mode */
  static final _ParsedFunction FACTORIAL_POSTFIX = new _ParsedFunction(
      Factorial.INSTANCE, _ParsedFunction.MODE_POSTFIX);

  /** the pow in infix mode */
  static final _ParsedFunction POW_INFIX = new _ParsedFunction(
      Pow.INSTANCE, _ParsedFunction.MODE_INFIX);

  /** the sqr in postfix mode */
  static final _ParsedFunction SQR_POSTFIX = new _ParsedFunction(
      Sqr.INSTANCE, _ParsedFunction.MODE_POSTFIX);

  /** the cube in postfix mode */
  static final _ParsedFunction CUBE_POSTFIX = new _ParsedFunction(
      Cube.INSTANCE, _ParsedFunction.MODE_POSTFIX);

  /** the sqrt in prefix mode */
  static final _ParsedFunction SQRT_PREFIX = new _ParsedFunction(
      Cube.INSTANCE, _ParsedFunction.MODE_PREFIX);

  /** the cbrt in prefix mode */
  static final _ParsedFunction CBRT_PREFIX = new _ParsedFunction(
      Cbrt.INSTANCE, _ParsedFunction.MODE_PREFIX);

  /** the 10^ in prefix mode */
  static final _ParsedFunction POW10_PREFIX = new _ParsedFunction(
      Pow10.INSTANCE, _ParsedFunction.MODE_PREFIX);
  /** the 2^ in prefix mode */
  static final _ParsedFunction POW2_PREFIX = new _ParsedFunction(
      Pow2.INSTANCE, _ParsedFunction.MODE_PREFIX);
  /** the e^ in prefix mode */
  static final _ParsedFunction EXP_PREFIX = new _ParsedFunction(
      Exp.INSTANCE, _ParsedFunction.MODE_PREFIX);

  /** the function */
  final MathematicalFunction m_func;
  /** the parse mode */
  int m_mode;

  /**
   * create the parsed function
   *
   * @param func
   *          the function
   * @param mode
   *          the mode
   */
  _ParsedFunction(final MathematicalFunction func, final int mode) {
    super();

    if (func == null) {
      throw new IllegalArgumentException("Function cannot be null."); //$NON-NLS-1$
    }
    this.m_func = func;
    this.m_mode = mode;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    final String string;
    string = this.m_func.getClass().getSimpleName();
    switch (this.m_mode) {
      case MODE_PREFIX: {
        return ("prefix_" + string);}//$NON-NLS-1$
      case MODE_INFIX: {
        return ("infix_" + string);}//$NON-NLS-1$
      case MODE_POSTFIX: {
        return ("postfix_" + string);}//$NON-NLS-1$
      default: {
        return ("call_" + string);}//$NON-NLS-1$
    }
  }
}