package org.optimizationBenchmarking.utils.math.functions;

import java.util.LinkedHashSet;

import org.optimizationBenchmarking.utils.math.functions.arithmetic.Absolute;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Div;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Max;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Min;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.ModuloDivisorSign;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Mul;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Sub;
import org.optimizationBenchmarking.utils.math.functions.binary.BAnd;
import org.optimizationBenchmarking.utils.math.functions.combinatoric.Factorial;
import org.optimizationBenchmarking.utils.math.functions.hyperbolic.ACosh;
import org.optimizationBenchmarking.utils.math.functions.hyperbolic.ASinh;
import org.optimizationBenchmarking.utils.math.functions.hyperbolic.ATanh;
import org.optimizationBenchmarking.utils.math.functions.hyperbolic.Cosh;
import org.optimizationBenchmarking.utils.math.functions.hyperbolic.Sinh;
import org.optimizationBenchmarking.utils.math.functions.hyperbolic.Tanh;
import org.optimizationBenchmarking.utils.math.functions.logic.LAnd;
import org.optimizationBenchmarking.utils.math.functions.numeric.Ceil;
import org.optimizationBenchmarking.utils.math.functions.numeric.Floor;
import org.optimizationBenchmarking.utils.math.functions.numeric.Round;
import org.optimizationBenchmarking.utils.math.functions.power.Cbrt;
import org.optimizationBenchmarking.utils.math.functions.power.Cube;
import org.optimizationBenchmarking.utils.math.functions.power.Ld;
import org.optimizationBenchmarking.utils.math.functions.power.Lg;
import org.optimizationBenchmarking.utils.math.functions.power.Ln;
import org.optimizationBenchmarking.utils.math.functions.power.Log;
import org.optimizationBenchmarking.utils.math.functions.power.Pow;
import org.optimizationBenchmarking.utils.math.functions.power.Pow10;
import org.optimizationBenchmarking.utils.math.functions.power.Pow2;
import org.optimizationBenchmarking.utils.math.functions.power.Sqr;
import org.optimizationBenchmarking.utils.math.functions.power.Sqrt;
import org.optimizationBenchmarking.utils.math.functions.special.Beta;
import org.optimizationBenchmarking.utils.math.functions.stochastic.NormalCDF;
import org.optimizationBenchmarking.utils.math.functions.trigonometric.ACos;
import org.optimizationBenchmarking.utils.math.functions.trigonometric.ASin;
import org.optimizationBenchmarking.utils.math.functions.trigonometric.ATan;
import org.optimizationBenchmarking.utils.math.functions.trigonometric.Cos;
import org.optimizationBenchmarking.utils.math.functions.trigonometric.Sin;
import org.optimizationBenchmarking.utils.math.functions.trigonometric.Tan;
import org.optimizationBenchmarking.utils.parsers.InstanceParser;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;

/** A parser for mathematical functionsdrivers. */
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
  public final MathematicalFunction parseString(final String string)
      throws Exception {

    switch (string.toLowerCase()) {
      case "+": {return Add.INSTANCE;} //$NON-NLS-1$
      case "-": {return Sub.INSTANCE;} //$NON-NLS-1$
      case "*": {return Mul.INSTANCE;} //$NON-NLS-1$
      case "**": {return Pow.INSTANCE;} //$NON-NLS-1$
      case "**10": {return Pow10.INSTANCE;} //$NON-NLS-1$
      case "**2": {return Pow2.INSTANCE;} //$NON-NLS-1$
      case "/": {return Div.INSTANCE;} //$NON-NLS-1$
      case "%": {return ModuloDivisorSign.INSTANCE;} //$NON-NLS-1$
      case "^": {return Pow.INSTANCE;} //$NON-NLS-1$
      case "^10": {return Pow10.INSTANCE;} //$NON-NLS-1$
      case "^2": {return Pow2.INSTANCE;} //$NON-NLS-1$
      case "\u00b2": {return Sqr.INSTANCE;} //$NON-NLS-1$
      case "\u00b3": {return Cube.INSTANCE;} //$NON-NLS-1$
      case "\u221a": {return Sqrt.INSTANCE;} //$NON-NLS-1$
      case "\u221b": {return Cbrt.INSTANCE;} //$NON-NLS-1$
      case "abs": {return Absolute.INSTANCE;} //$NON-NLS-1$
      case "absolute": {return Absolute.INSTANCE;} //$NON-NLS-1$
      case "acos": {return ACos.INSTANCE;} //$NON-NLS-1$$
      case "acosh": {return ACosh.INSTANCE;} //$NON-NLS-1$
      case "asin": {return ASin.INSTANCE;} //$NON-NLS-1$
      case "asinh": {return ASinh.INSTANCE;} //$NON-NLS-1$
      case "atan": {return ATan.INSTANCE;} //$NON-NLS-1$
      case "atanh": {return ATanh.INSTANCE;} //$NON-NLS-1$
      case "cbrt": {return Cbrt.INSTANCE;} //$NON-NLS-1$
      case "ceil": {return Ceil.INSTANCE;} //$NON-NLS-1$
      case "cos": {return Cos.INSTANCE;} //$NON-NLS-1$
      case "cosh": {return Cosh.INSTANCE;} //$NON-NLS-1$
      case "cube": {return Cube.INSTANCE;} //$NON-NLS-1$
      case "floor": {return Floor.INSTANCE;} //$NON-NLS-1$
      case "ld": {return Ld.INSTANCE;} //$NON-NLS-1$
      case "ln": {return Ln.INSTANCE;} //$NON-NLS-1$
      case "lg": {return Lg.INSTANCE;} //$NON-NLS-1$
      case "log": {return Log.INSTANCE;} //$NON-NLS-1$
      case "log10": {return Lg.INSTANCE;} //$NON-NLS-1$
      case "log2": {return Ld.INSTANCE;} //$NON-NLS-1$
      case "log_10": {return Lg.INSTANCE;} //$NON-NLS-1$
      case "log_2": {return Ld.INSTANCE;} //$NON-NLS-1$
      case "min": {return Min.INSTANCE;} //$NON-NLS-1$
      case "max": {return Max.INSTANCE;} //$NON-NLS-1$
      case "mod": {return ModuloDivisorSign.INSTANCE;} //$NON-NLS-1$
      case "modulo": {return ModuloDivisorSign.INSTANCE;} //$NON-NLS-1$
      case "round": {return Round.INSTANCE;} //$NON-NLS-1$
      case "sin": {return Sin.INSTANCE;} //$NON-NLS-1$
      case "sinh": {return Sinh.INSTANCE;} //$NON-NLS-1$
      case "sqr": {return Sqr.INSTANCE;} //$NON-NLS-1$
      case "sqrt": {return Sqrt.INSTANCE;} //$NON-NLS-1$
      case "tan": {return Tan.INSTANCE;} //$NON-NLS-1$
      case "tanh": {return Tanh.INSTANCE;} //$NON-NLS-1$
      default: {
        return super.parseString(string);
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
    return __DocumentDriverParserLoader.INSTANCE;
  }

  /** the instance loader */
  private static final class __DocumentDriverParserLoader {
    /** the instance */
    static final MathematicalFunctionParser INSTANCE = new MathematicalFunctionParser();
  }
}
