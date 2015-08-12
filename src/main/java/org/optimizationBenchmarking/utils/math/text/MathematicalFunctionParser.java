package org.optimizationBenchmarking.utils.math.text;

import java.util.LinkedHashSet;

import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Absolute;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.ModuloDivisorSign;
import org.optimizationBenchmarking.utils.math.functions.binary.BAnd;
import org.optimizationBenchmarking.utils.math.functions.combinatoric.Factorial;
import org.optimizationBenchmarking.utils.math.functions.hyperbolic.ACosh;
import org.optimizationBenchmarking.utils.math.functions.hyperbolic.ASinh;
import org.optimizationBenchmarking.utils.math.functions.hyperbolic.ATanh;
import org.optimizationBenchmarking.utils.math.functions.logic.LAnd;
import org.optimizationBenchmarking.utils.math.functions.numeric.Ceil;
import org.optimizationBenchmarking.utils.math.functions.power.Ld;
import org.optimizationBenchmarking.utils.math.functions.power.Lg;
import org.optimizationBenchmarking.utils.math.functions.power.Log;
import org.optimizationBenchmarking.utils.math.functions.power.Pow;
import org.optimizationBenchmarking.utils.math.functions.power.Sqrt;
import org.optimizationBenchmarking.utils.math.functions.special.Beta;
import org.optimizationBenchmarking.utils.math.functions.special.ErrorFunction;
import org.optimizationBenchmarking.utils.math.functions.stochastic.NormalCDF;
import org.optimizationBenchmarking.utils.math.functions.trigonometric.ACos;
import org.optimizationBenchmarking.utils.math.functions.trigonometric.ASin;
import org.optimizationBenchmarking.utils.math.functions.trigonometric.ATan;
import org.optimizationBenchmarking.utils.math.functions.trigonometric.Cos;
import org.optimizationBenchmarking.utils.math.functions.trigonometric.Sin;
import org.optimizationBenchmarking.utils.math.functions.trigonometric.Tan;
import org.optimizationBenchmarking.utils.parsers.InstanceParser;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A parser for singular instances of
 * {@link org.optimizationBenchmarking.utils.math.functions.MathematicalFunction}
 * , such as simple addition or subtraction. If you are looking for
 * compound functions such as {@code 4+5*7*x}, use
 * {@link org.optimizationBenchmarking.utils.math.text.CompoundFunctionParser}
 * .
 */
public final class MathematicalFunctionParser extends
    InstanceParser<MathematicalFunction> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  MathematicalFunctionParser() {
    super(MathematicalFunction.class, MathematicalFunctionParser
        .__prefixes());
  }

  /**
   * get the prefixes
   *
   * @return the path prefixes
   */
  private static final String[] __prefixes() {
    final LinkedHashSet<String> paths;

    paths = new LinkedHashSet<>();
    ReflectionUtils.addPackageOfClassToPrefixList(
        MathematicalFunctionParser.class, paths);
    ReflectionUtils.addPackageOfClassToPrefixList(//
        Add.class, paths);
    ReflectionUtils.addPackageOfClassToPrefixList(//
        BAnd.class, paths);
    ReflectionUtils.addPackageOfClassToPrefixList(//
        Factorial.class, paths);
    ReflectionUtils.addPackageOfClassToPrefixList(//
        ACosh.class, paths);
    ReflectionUtils.addPackageOfClassToPrefixList(//
        LAnd.class, paths);
    ReflectionUtils.addPackageOfClassToPrefixList(//
        Ceil.class, paths);
    ReflectionUtils.addPackageOfClassToPrefixList(//
        Sqrt.class, paths);
    ReflectionUtils.addPackageOfClassToPrefixList(//
        Beta.class, paths);
    ReflectionUtils.addPackageOfClassToPrefixList(//
        NormalCDF.class, paths);
    ReflectionUtils.addPackageOfClassToPrefixList(//
        Sin.class, paths);
    return paths.toArray(new String[paths.size()]);
  }

  /** {@inheritDoc} */
  @Override
  public final MathematicalFunction parseString(final String string) {
    _ParsedFunction func;

    func = this._parseString(string);
    if (func != null) {
      return func.m_func;
    }
    throw new IllegalStateException("No function parsed from '" //$NON-NLS-1$
        + string + '\'');
  }

  /**
   * Parse a mathematical function string
   *
   * @param string
   *          the string
   * @return the function
   */
  @SuppressWarnings("incomplete-switch")
  final _ParsedFunction _parseString(final String string) {
    final String preProcessed, lowerCase;
    final char first;
    _ParsedFunction parsed;
    MathematicalFunction func;
    Throwable error;

    preProcessed = TextUtils.prepare(string);
    if (string == null) {
      throw new IllegalArgumentException(((//
          "String to be parsed to a mathematical function cannot be empty, null, or just consist of white spaces, but you supplied '"//$NON-NLS-1$
          + string) + '\'') + '.');
    }

    first = preProcessed.charAt(0);
    if (preProcessed.length() == 1) {
      parsed = MathematicalFunctionParser._parseChar(preProcessed
          .charAt(0));
      if (parsed != null) {
        return parsed;
      }
    }

    lowerCase = TextUtils.toLowerCase(preProcessed);
    func = null;
    error = null;
    findFunc: {
      switch (lowerCase) {
        case "^2": {return _ParsedFunction.SQR_POSTFIX;} //$NON-NLS-1$
        case "^3": {return _ParsedFunction.CUBE_POSTFIX;} //$NON-NLS-1$
        case "**": {return _ParsedFunction.POW_INFIX;} //$NON-NLS-1$
        case "10**": {return _ParsedFunction.POW10_PREFIX;} //$NON-NLS-1$
        case "2**": {return _ParsedFunction.POW2_PREFIX;} //$NON-NLS-1$
        case "10^": {return _ParsedFunction.POW10_PREFIX;} //$NON-NLS-1$
        case "2^": {return _ParsedFunction.POW2_PREFIX;} //$NON-NLS-1$
        case "abs"://$NON-NLS-1$
        case "absolute": {func = Absolute.INSTANCE;break findFunc;}//$NON-NLS-1$
        case "acos": {func = ACos.INSTANCE;break findFunc;}//$NON-NLS-1$
        case "acosh": {func = ACosh.INSTANCE;break findFunc;}//$NON-NLS-1$
        case "asin": {func = ASin.INSTANCE;break findFunc;} //$NON-NLS-1$
        case "asinh": {func = ASinh.INSTANCE;break findFunc;} //$NON-NLS-1$
        case "atan": {func = ATan.INSTANCE;break findFunc;} //$NON-NLS-1$
        case "atanh": {func = ATanh.INSTANCE;break findFunc;} //$NON-NLS-1$
        case "cosine": {func = Cos.INSTANCE;break findFunc;} //$NON-NLS-1$
        case "e^": {return _ParsedFunction.EXP_PREFIX;} //$NON-NLS-1$
        case "erf": {func = ErrorFunction.INSTANCE;break findFunc;} //$NON-NLS-1$
        case "log10": {func = Lg.INSTANCE;break findFunc;} //$NON-NLS-1$
        case "log2": {func = Ld.INSTANCE;break findFunc;} //$NON-NLS-1$
        case "log_10": {func = Lg.INSTANCE;break findFunc;} //$NON-NLS-1$
        case "log_2": {func = Ld.INSTANCE;break findFunc;} //$NON-NLS-1$
        case "logarithm": {func = Log.INSTANCE;break findFunc;} //$NON-NLS-1$
        case "mod": {func = ModuloDivisorSign.INSTANCE;break findFunc;} //$NON-NLS-1$
        case "modulo": {func = ModuloDivisorSign.INSTANCE;break findFunc;} //$NON-NLS-1$
        case "power": //$NON-NLS-1$
        case "pwr": //$NON-NLS-1$
        case "pow": {func = Pow.INSTANCE;break findFunc;} //$NON-NLS-1$
        case "sine": {func = Sin.INSTANCE;break findFunc;} //$NON-NLS-1$
        case "tangent": {func = Tan.INSTANCE;break findFunc;} //$NON-NLS-1$
      }

      try {
        func = super.parseString(preProcessed);
        if (func != null) {
          break findFunc;
        }
      } catch (final Throwable t) {
        error = t;
        func = null;
      }

      if (preProcessed.lastIndexOf('.') < 0) {
        // if no '.' is contained in the function name, we can try
        // different cases
        try {
          func = super.parseString(TextUtils.toUpperCase(first)
              + preProcessed.substring(1));
          if (func != null) {
            break findFunc;
          }
        } catch (final Throwable t) {
          if (error == null) {
            error = t;
          }
          func = null;
        }

        try {
          func = super.parseString(TextUtils.toUpperCase(first)
              + lowerCase.substring(1));
          if (func != null) {
            break findFunc;
          }
        } catch (final Throwable t) {
          if (error == null) {
            error = t;
          }
          func = null;
        }

        try {
          func = super.parseString(lowerCase);
          if (func != null) {
            break findFunc;
          }
        } catch (final Throwable t) {
          if (error == null) {
            error = t;
          }
          func = null;
        }

        try {
          func = super.parseString(preProcessed.toUpperCase());
          if (func != null) {
            break findFunc;
          }
        } catch (final Throwable t) {
          if (error == null) {
            error = t;
          }
          func = null;
        }
      }
    }

    if (func != null) {
      return new _ParsedFunction(func, _ParsedFunction.MODE_CALL);
    }

    throw new IllegalArgumentException(("The string '" //$NON-NLS-1$
        + string + //
        "' cannot be parsed to a mathematical function."),//$NON-NLS-1$
        error);
  }

  /**
   * Parse the given character to a function
   *
   * @param currentChar
   *          the current character
   * @return the function representing the character, or {@code null} if
   *         none was found
   */
  static final _ParsedFunction _parseChar(final char currentChar) {
    switch (currentChar) {
      case 0x21: { // '!'
        return _ParsedFunction.FACTORIAL_POSTFIX;
      }
      case 0x25: { // ':'
        return _ParsedFunction.MOD_INFIX;
      }
      case 0x26: { // '&'
        return _ParsedFunction.BAND_INFIX;
      }
      case 0x2a: { // '*'
        return _ParsedFunction.MUL_INFIX;
      }
      case 0x2b: { // '+'
        return _ParsedFunction.ADD_INFIX;
      }
      case 0x2d: { // '-'
        return _ParsedFunction.SUB_INFIX;
      }
      case 0x2f: // '/'
      case 0x3a: {// ':'
        return _ParsedFunction.DIV_INFIX;
      }
      case 0x5e: { // '^'
        return _ParsedFunction.POW_INFIX;
      }
      case 0x7e: {// '~'
        return _ParsedFunction.BNOT_PREFIX;
      }
      case 0xac: { // logical not character
        return _ParsedFunction.LNOT_PREFIX;
      }
      case 0xb2: { // '^2'
        return _ParsedFunction.SQR_POSTFIX;
      }
      case 0xb3: { // '^3'
        return _ParsedFunction.CUBE_POSTFIX;
      }
      case 0x221a: { // sqrt character
        return _ParsedFunction.SQRT_PREFIX;
      }
      case 0x221b: { // cubic root character
        return _ParsedFunction.CBRT_PREFIX;
      }
      default: {
        return null;
      }
    }
  }

  // default, automatic serialization replacement and resolve routines for
  // singletons
  //
  /**
   * Write replace: the instance this method is invoked on will be replaced
   * with the singleton instance {@link #getInstance()} for serialization,
   * i.e., when the instance is written with
   * {@link java.io.ObjectOutputStream#writeObject(Object)}.
   *
   * @return the replacement instance (always {@link #getInstance()})
   */
  private final Object writeReplace() {
    return MathematicalFunctionParser.getInstance();
  }

  /**
   * Read resolve: The instance this method is invoked on will be replaced
   * with the singleton instance {@link #getInstance()} after
   * serialization, i.e., when the instance is read with
   * {@link java.io.ObjectInputStream#readObject()}.
   *
   * @return the replacement instance (always {@link #getInstance()})
   */
  private final Object readResolve() {
    return MathematicalFunctionParser.getInstance();
  }

  /**
   * Get the singleton instance of this parser
   *
   * @return the document driver parser
   */
  public static final MathematicalFunctionParser getInstance() {
    return __MathematicalFunctionParserLoader.INSTANCE;
  }

  /** the instance loader */
  private static final class __MathematicalFunctionParserLoader {
    /** the instance */
    static final MathematicalFunctionParser INSTANCE = new MathematicalFunctionParser();
  }

}
