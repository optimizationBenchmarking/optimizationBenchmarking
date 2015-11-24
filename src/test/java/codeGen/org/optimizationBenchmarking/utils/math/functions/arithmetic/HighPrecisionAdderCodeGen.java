package codeGen.org.optimizationBenchmarking.utils.math.functions.arithmetic;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.combinatorics.PermutationIterator;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.SaturatingAdd;

import codeGen.CodeGeneratorBase;

/**
 * <p>
 * A generator for high-precision adders of three or more {@code double}
 * numbers. It generates code based of Kahan sums, but does not just apply
 * the algorithm once: It applies them to all permutations of the input
 * numbers and returns the sum with the smallest remaining compensation. I
 * am not entirely sure whether this overhead will pay off, but I can
 * produce slightly different results by using it, so it may be worthwhile
 * doing it.
 * </p>
 * <p>
 * One problem persists: If I add {@link Double#MAX_VALUE}, {@code 1}, and
 * <code>-{@link Double#MAX_VALUE}</code>, I get {@code 0} instead of
 * {@code 1}. Hence, the summation method may need to be improved in the
 * future.
 * </p>
 */
public final class HighPrecisionAdderCodeGen extends CodeGeneratorBase {

  /**
   * Generate the code generator
   *
   * @param args
   *          the command line arguments
   * @throws IOException
   *           if i/o fails
   */
  protected HighPrecisionAdderCodeGen(final String[] args)
      throws IOException {
    super(args);
  }

  /**
   * Write the function of the given arity
   *
   * @param arity
   *          the arity
   * @param clazz
   *          the class name
   * @throws IOException
   *           if i/o fails
   */
  private final void __doArity(final int arity, final String clazz)
      throws IOException {
    final Path path;

    path = PathUtils.createPathInside(this.getPackagePath(),
        clazz + ".java"); //$NON-NLS-1$
    try (final OutputStream os = PathUtils.openOutputStream(path)) {
      try (final OutputStreamWriter osw = new OutputStreamWriter(os)) {
        try (final PrintWriter dest = new PrintWriter(osw)) {

          CodeGeneratorBase.writePackage(this.getPackageName(), dest);

          CodeGeneratorBase.importClass(
              CodeGeneratorBase.getMathematicalFunctionClassOfArity(arity),
              dest);
          CodeGeneratorBase
              .importClass(NumericalTypes.class.getCanonicalName(), dest);

          dest.println();
          dest.print("/** The code of an addition ({@code +}) of ");//$NON-NLS-1$
          dest.print(arity);
          dest.print(
              " arguments designed for highest-precision output. */"); //$NON-NLS-1$
          dest.println();
          dest.print("public final class "); //$NON-NLS-1$
          dest.print(clazz);
          dest.print(" extends "); //$NON-NLS-1$
          dest.print(CodeGeneratorBase
              .getMathematicalFunctionClassOfArity(arity).getSimpleName());
          dest.print(" {"); //$NON-NLS-1$
          dest.println();

          dest.println();
          dest.print("/** the serial version uid */"); //$NON-NLS-1$
          dest.println();
          dest.print("private static final long serialVersionUID = 1L;"); //$NON-NLS-1$
          dest.println();

          dest.println();
          dest.print(
              "/** the precedence priority of this high-precision addition operator */"); //$NON-NLS-1$
          dest.println();
          dest.print(
              "public static final int PRECEDENCE_PRIORITY = Add.PRECEDENCE_PRIORITY;"); //$NON-NLS-1$
          dest.println();

          dest.println();
          dest.print(
              "/**  the globally shared instance of the high-precision addition operator */"); //$NON-NLS-1$
          dest.println();
          dest.print("public static final "); //$NON-NLS-1$
          dest.print(clazz);
          dest.print(" INSTANCE = new "); //$NON-NLS-1$
          dest.print(clazz);
          dest.print("();"); //$NON-NLS-1$
          dest.println();

          dest.println();
          dest.print("/**  hidden constructor, use {@link #INSTANCE} */"); //$NON-NLS-1$
          dest.println();
          dest.print("private "); //$NON-NLS-1$
          dest.print(clazz);
          dest.print("() {"); //$NON-NLS-1$
          dest.println();
          dest.print("super();"); //$NON-NLS-1$
          dest.println();
          dest.print("}"); //$NON-NLS-1$
          dest.println();

          HighPrecisionAdderCodeGen.__doIntegerPrimitives(arity, clazz,
              dest);
          HighPrecisionAdderCodeGen.__doDefaults(clazz, dest);

          HighPrecisionAdderCodeGen.__computeAsDouble(arity, clazz, dest);

          dest.println();
          dest.print("}"); //$NON-NLS-1$

        }
      }
    }
  }

  /**
   * Write the small fries
   *
   * @param arity
   *          the arity
   * @param clazz
   *          the class name
   * @param dest
   *          the destination
   */
  private static final void __doIntegerPrimitives(final int arity,
      final String clazz, final PrintWriter dest) {
    String name;
    int i;

    for (final Class<?> type : new Class<?>[] { byte.class, short.class,
        int.class, long.class }) {
      name = type.getName();

      dest.println();
      dest.print("/** {@inheritDoc} */");//$NON-NLS-1$
      dest.println();
      dest.print("@Override");//$NON-NLS-1$
      dest.println();
      dest.print("public final ");//$NON-NLS-1$
      dest.print(type.getName());
      dest.print(" computeAs");//$NON-NLS-1$
      dest.print(Character.toUpperCase(name.charAt(0)));
      dest.print(name.substring(1));
      dest.print('(');
      for (i = 0; i < arity; i++) {
        if (i > 0) {
          dest.print(", ");//$NON-NLS-1$
        }
        dest.print("final ");//$NON-NLS-1$
        dest.print(name);
        dest.print(" x");//$NON-NLS-1$
        dest.print(i);
      }
      dest.print(") {");//$NON-NLS-1$
      dest.println();
      dest.print("return ");//$NON-NLS-1$
      if ((type == byte.class) || (type == short.class)) {
        dest.print('(');
        dest.print(name);
        dest.print(')');
      }
      dest.print('(');
      for (i = 0; i < arity; i++) {
        if (i > 0) {
          dest.print('+');
        }
        dest.print('x');
        dest.print(i);
      }
      dest.print(");");//$NON-NLS-1$
      dest.println();
      dest.print('}');
      dest.println();
    }
  }

  /**
   * Write the compute-as-double
   *
   * @param arity
   *          the arity
   * @param clazz
   *          the class name
   * @param dest
   *          the destination
   */
  private static final void __computeAsDouble(final int arity,
      final String clazz, final PrintWriter dest) {
    String name;
    int i;

    for (final Class<?> type : new Class<?>[] { int.class, long.class,
        double.class }) {
      name = type.getName();

      dest.println();
      dest.print("/** {@inheritDoc} */");//$NON-NLS-1$
      dest.println();
      dest.print("@Override");//$NON-NLS-1$
      dest.println();
      dest.print("public final double computeAsDouble(");//$NON-NLS-1$
      for (i = 0; i < arity; i++) {
        if (i > 0) {
          dest.print(", ");//$NON-NLS-1$
        }
        dest.print("final ");//$NON-NLS-1$
        dest.print(name);
        dest.print(" x");//$NON-NLS-1$
        dest.print(i);
      }
      dest.print(") {");//$NON-NLS-1$
      dest.println();

      if (type == double.class) {
        HighPrecisionAdderCodeGen.__doubleComputeAsDouble(arity, dest);
      } else {
        if (type == long.class) {
          HighPrecisionAdderCodeGen.__longComputeAsDouble(arity, dest);
        } else {
          dest.print("return ");//$NON-NLS-1$
          for (i = 0; i < arity; i++) {
            dest.print('(');
          }
          for (i = 0; i < arity; i++) {
            if (i > 0) {
              dest.print(") + ");//$NON-NLS-1$
            }

            if (i > 0) {
              dest.print('x');
              dest.print(i);
            } else {
              dest.print("((long) x");//$NON-NLS-1$
              dest.print(i);
              dest.print(')');
            }
          }
          dest.print(')');
          dest.println(';');
        }
      }
      dest.print('}');
      dest.println();
    }
  }

  /**
   * Write the default functions
   *
   * @param clazz
   *          the class name
   * @param dest
   *          the destination
   */
  private static final void __doDefaults(final String clazz,
      final PrintWriter dest) {
    dest.println();
    dest.print("/** {@inheritDoc} */");//$NON-NLS-1$
    dest.println();
    dest.print("@Override");//$NON-NLS-1$
    dest.println();
    dest.print("public final int getPrecedencePriority() {");//$NON-NLS-1$
    dest.println();
    dest.print("return ");//$NON-NLS-1$
    dest.print(clazz);
    dest.print(".PRECEDENCE_PRIORITY");//$NON-NLS-1$
    dest.print(';');
    dest.println();
    dest.print('}');

    dest.println();
    dest.print(//
        "/** Write replace: the instance this method is invoked on will be replaced with the singleton instance {@link #INSTANCE} for serialization, i.e., when the instance is written with {@link java.io.ObjectOutputStream#writeObject(Object)}.");//$NON-NLS-1$
    dest.println();
    dest.print(
        "* @return the replacement instance (always {@link #INSTANCE}) */");//$NON-NLS-1$
    dest.println();
    dest.print("private final Object writeReplace() {");//$NON-NLS-1$
    dest.println();
    dest.print("return ");//$NON-NLS-1$
    dest.print(clazz);
    dest.print(".INSTANCE;");//$NON-NLS-1$
    dest.println();
    dest.print('}');
    dest.println();

    dest.println();
    dest.print(//
        "/** Read resolve: The instance this method is invoked on will be replaced with the singleton instance {@link #INSTANCE} after serialization, i.e., when the instance is read with {@link java.io.ObjectInputStream#readObject()}.");//$NON-NLS-1$
    dest.println();
    dest.print(
        "* @return the replacement instance (always {@link #INSTANCE}) */");//$NON-NLS-1$
    dest.println();
    dest.print("private final Object readResolve() {");//$NON-NLS-1$
    dest.println();
    dest.print("return ");//$NON-NLS-1$
    dest.print(clazz);
    dest.print(".INSTANCE;");//$NON-NLS-1$
    dest.println();
    dest.print('}');
    dest.println();
  }

  /**
   * Compute as double
   *
   * @param arity
   *          the arity
   * @param dest
   *          the destination
   */
  private static final void __doubleComputeAsDouble(final int arity,
      final PrintWriter dest) {
    int i, label, sumLabel;
    boolean first, first2;
    String sum, comp;

    // are the values longs?
    dest.print("boolean isCurrentSumLong, pInf, nInf, onlyOverflow");//$NON-NLS-1$
    for (i = 0; i < arity; i++) {
      dest.print(", ");//$NON-NLS-1$
      dest.print("isLong");//$NON-NLS-1$
      dest.print(i);
    }
    dest.println(';');
    dest.print("long currentSumLong");//$NON-NLS-1$
    for (i = 0; i < arity; i++) {
      dest.print(", ");//$NON-NLS-1$
      dest.print('l');
      dest.print(i);
    }
    dest.println(';');

    dest.println(//
        "double t, y, currentSum, bestSum, currentCompensation, bestCompensation;");//$NON-NLS-1$

    dest.println(//
        "pInf = nInf = false;");//$NON-NLS-1$
    label = 0;

    for (i = 0; i < arity; i++) {
      label++;
      dest.print("checkIsNotLong");//$NON-NLS-1$
      dest.print(label);
      dest.println(": {");//$NON-NLS-1$

      dest.print("if(x");//$NON-NLS-1$
      dest.print(i);
      dest.println(" >= Double.POSITIVE_INFINITY) {");//$NON-NLS-1$

      if (i > 0) {
        dest.println("if(nInf) { return Double.NaN; }");//$NON-NLS-1$
      }
      dest.println("pInf = true;");//$NON-NLS-1$

      dest.println("} else {");//$NON-NLS-1$

      dest.print("if(x");//$NON-NLS-1$
      dest.print(i);
      dest.println(" <= Double.NEGATIVE_INFINITY) {");//$NON-NLS-1$

      if (i > 0) {
        dest.println("if(pInf) { return Double.NaN; }");//$NON-NLS-1$
      }
      dest.println("nInf = true;");//$NON-NLS-1$

      dest.println("} else {");//$NON-NLS-1$

      dest.print("if(x");//$NON-NLS-1$
      dest.print(i);
      dest.print(" != x");//$NON-NLS-1$
      dest.print(i);
      dest.println(") { return Double.NaN; }");//$NON-NLS-1$

      dest.print("if(");//$NON-NLS-1$
      dest.print(NumericalTypes.class.getSimpleName());
      dest.print(".isLong(x");//$NON-NLS-1$
      dest.print(i);
      dest.println(")) {");//$NON-NLS-1$
      dest.print("isLong");//$NON-NLS-1$
      dest.print(i);
      dest.println("=true;");//$NON-NLS-1$
      dest.print('l');
      dest.print(i);
      dest.print("=((long)x");//$NON-NLS-1$
      dest.print(i);
      dest.print(')');
      dest.println(';');
      dest.print("break checkIsNotLong");//$NON-NLS-1$
      dest.print(label);
      dest.println(';');
      dest.println('}');
      dest.println('}');
      dest.println('}');
      dest.print("isLong");//$NON-NLS-1$
      dest.print(i);
      dest.println("=false;");//$NON-NLS-1$
      dest.print('l');
      dest.print(i);
      dest.println("=0L;");//$NON-NLS-1$
      dest.println('}');
    }

    dest.println("if(pInf) { return Double.POSITIVE_INFINITY; }");//$NON-NLS-1$
    dest.println("if(nInf) { return Double.NEGATIVE_INFINITY; }");//$NON-NLS-1$

    dest.println("onlyOverflow = true;");//$NON-NLS-1$

    sum = "bestSum";//$NON-NLS-1$
    comp = "bestCompensation";//$NON-NLS-1$
    first = true;
    for (final int[] perm : new PermutationIterator(arity)) {
      dest.println();
      dest.print("// now testing summation ");//$NON-NLS-1$
      first2 = true;
      for (final int xi : perm) {
        if (first2) {
          first2 = false;
        } else {
          dest.print(" + "); //$NON-NLS-1$
        }
        dest.print('x');
        dest.print(xi - 1);
      }
      dest.println();
      dest.println();

      sumLabel = (++label);

      dest.print("summation");//$NON-NLS-1$
      dest.print(sumLabel);
      dest.print(':');
      dest.println('{');

      dest.print("if(isLong");//$NON-NLS-1$
      dest.print(perm[0] - 1);
      dest.println(") {");//$NON-NLS-1$
      dest.print("currentSumLong=l");//$NON-NLS-1$
      dest.print(perm[0] - 1);
      dest.println(';');
      dest.println("isCurrentSumLong=true;");//$NON-NLS-1$

      dest.print(sum);
      dest.print('=');
      dest.print(comp);
      dest.println("=0d;");//$NON-NLS-1$

      dest.println("} else {");//$NON-NLS-1$
      dest.print(sum);
      dest.print("=x");//$NON-NLS-1$
      dest.print(perm[0] - 1);
      dest.println(';');
      dest.print(comp);
      dest.println("=0d;");//$NON-NLS-1$
      dest.println("isCurrentSumLong=false;");//$NON-NLS-1$
      dest.println("currentSumLong=0L;");//$NON-NLS-1$
      dest.println('}');

      for (i = 1; i < perm.length; i++) {
        ++label;
        dest.print("compute");//$NON-NLS-1$
        dest.print(label);
        dest.print(':');
        dest.println('{');
        dest.println("if (isCurrentSumLong) {");//$NON-NLS-1$
        dest.print("if (isLong");//$NON-NLS-1$
        dest.print(perm[i] - 1);
        dest.print(" && (");//$NON-NLS-1$
        dest.print(SaturatingAdd.class.getSimpleName());
        dest.print(".getOverflowType(currentSumLong, l");//$NON-NLS-1$
        dest.print(perm[i] - 1);
        dest.println(") == 0)) {");//$NON-NLS-1$
        dest.print("currentSumLong += l");//$NON-NLS-1$
        dest.print(perm[i] - 1);
        dest.println(';');
        dest.print("break compute");//$NON-NLS-1$
        dest.print(label);
        dest.println(';');
        dest.println('}');
        dest.print(sum);
        dest.println(" = currentSumLong;");//$NON-NLS-1$
        dest.print(comp);
        dest.print(" = (((long)");//$NON-NLS-1$
        dest.print(sum);
        dest.println(") - currentSumLong);");//$NON-NLS-1$
        dest.println("isCurrentSumLong = false;");//$NON-NLS-1$
        dest.println('}');

        dest.print("y = x");//$NON-NLS-1$
        dest.print(perm[i] - 1);
        dest.print('-');
        dest.print(comp);
        dest.println(';');
        dest.print("t = ");//$NON-NLS-1$
        dest.print(sum);
        dest.println(" + y;");//$NON-NLS-1$
        dest.print(comp);
        dest.print(" = ((t - ");//$NON-NLS-1$
        dest.print(sum);
        dest.println(") - y);");//$NON-NLS-1$
        dest.print(sum);
        dest.println(" = t;");//$NON-NLS-1$

        dest.print("if( ");//$NON-NLS-1$
        dest.print(sum);
        dest.println(" <= Double.NEGATIVE_INFINITY) {");//$NON-NLS-1$
        dest.println("nInf = true;");//$NON-NLS-1$
        dest.print("break summation");//$NON-NLS-1$
        dest.print(sumLabel);
        dest.println(';');
        dest.println('}');
        dest.print("if( ");//$NON-NLS-1$
        dest.print(sum);
        dest.println(" >= Double.POSITIVE_INFINITY) {");//$NON-NLS-1$
        dest.println("pInf = true;");//$NON-NLS-1$
        dest.print("break summation");//$NON-NLS-1$
        dest.print(sumLabel);
        dest.println(';');
        dest.println('}');

        dest.print("if((");//$NON-NLS-1$
        dest.print(comp);
        dest.print(" == 0d) && (");//$NON-NLS-1$
        dest.print(NumericalTypes.class.getSimpleName());
        dest.print(".isLong(");//$NON-NLS-1$
        dest.print(sum);
        dest.println("))) {");//$NON-NLS-1$
        dest.print("currentSumLong = ((long) ");//$NON-NLS-1$
        dest.print(sum);
        dest.println(");");//$NON-NLS-1$
        dest.println("isCurrentSumLong = true;");//$NON-NLS-1$
        dest.println('}');
        dest.println('}');
      }

      dest.println("if(isCurrentSumLong) {");//$NON-NLS-1$
      dest.println("return currentSumLong;");//$NON-NLS-1$
      dest.println('}');

      if (first) {
        first = false;
        dest.println("if(bestCompensation<0d) {");//$NON-NLS-1$
        dest.println("bestCompensation=(-bestCompensation);");//$NON-NLS-1$
        dest.println("} else {");//$NON-NLS-1$
        dest.println("if(bestCompensation<=0d) {");//$NON-NLS-1$
        dest.println("return bestSum;");//$NON-NLS-1$
        dest.println('}');
        dest.println('}');
      } else {
        dest.println("currentCompensation=Math.abs(currentCompensation);");//$NON-NLS-1$
        dest.println(
            "if(onlyOverflow || (currentCompensation<bestCompensation)){");//$NON-NLS-1$
        dest.println("if(currentCompensation<=0d){");//$NON-NLS-1$
        dest.println("return currentSum;");//$NON-NLS-1$
        dest.println('}');
        dest.println("bestCompensation=currentCompensation;");//$NON-NLS-1$
        dest.println("bestSum=currentSum;");//$NON-NLS-1$
        dest.println('}');
      }

      dest.println("onlyOverflow = false;");//$NON-NLS-1$

      dest.println('}');

      sum = "currentSum";//$NON-NLS-1$
      comp = "currentCompensation";//$NON-NLS-1$
    }

    dest.println("if(onlyOverflow) {");//$NON-NLS-1$
    dest.println("if(pInf || nInf) { return Double.NaN; }");//$NON-NLS-1$
    dest.println("if(pInf) { return Double.POSITIVE_INFINITY; }");//$NON-NLS-1$
    dest.println("return Double.NEGATIVE_INFINITY;");//$NON-NLS-1$
    dest.println('}');

    dest.println("return bestSum;");//$NON-NLS-1$
  }

  // /**
  // * the method we want to generate
  // *
  // * @param x0
  // * the first number
  // * @param x1
  // * the second number
  // * @param x2
  // * the third number
  // * @return the result
  // */
  // static final double _add3(final double x0, final double x1,
  // final double x2) {
  // boolean isCurrentSumLong, isLong0, isLong1, isLong2, isLong3;
  // long currentSumLong, l0, l1, l2, l3;
  // double currentSumDouble, bestSum, currentCompensation,
  // bestCompensation, t, y;
  //
  // isLong0 = NumericalTypes.isLong(x0);
  // if (isLong0) {
  // l0 = ((long) x0);
  // } else {
  // l0 = 0L;
  // }
  // isLong1 = NumericalTypes.isLong(x1);
  // if (isLong1) {
  // l1 = ((long) x1);
  // } else {
  // l1 = 0L;
  // }
  // isLong2 = NumericalTypes.isLong(x2);
  // if (isLong2) {
  // l2 = ((long) x2);
  // } else {
  // l2 = 0L;
  // }
  //
  // currentSumLong = 0L;
  // currentSumDouble = currentCompensation = 0d;
  //
  // if (isLong0) {
  // currentSumLong = l0;
  // isCurrentSumLong = true;
  // } else {
  // currentSumDouble = x0;
  // currentCompensation = 0d;
  // isCurrentSumLong = false;
  // }
  //
  // compute1: {
  // if (isCurrentSumLong) {
  // if (isLong1
  // && (SaturatingAdd.getOverflowType(currentSumLong, l1) == 0)) {
  // currentSumLong += l1;
  // break compute1;
  // }
  // currentSumDouble = currentSumLong;
  // currentCompensation = (((long) currentSumDouble) - currentSumLong);
  // isCurrentSumLong = false;
  // }
  //
  // y = x1 - currentCompensation;
  // t = currentSumDouble + y;
  // currentCompensation = ((t - currentSumDouble) - y);
  // currentSumDouble = t;
  //
  // if ((currentCompensation == 0d)
  // && NumericalTypes.isLong(currentSumDouble)) {
  // currentSumLong = ((long) currentSumDouble);
  // isCurrentSumLong = true;
  // }
  // }
  //
  // }

  /**
   * Compute as long
   *
   * @param arity
   *          the arity
   * @param dest
   *          the destination
   */
  private static final void __longComputeAsDouble(final int arity,
      final PrintWriter dest) {
    int i, label;
    boolean first, first2;
    String sum, comp;

    // are the values longs?
    dest.println("boolean isCurrentSumLong;");//$NON-NLS-1$
    dest.println("long currentSumLong;");//$NON-NLS-1$

    dest.print(//
        "double t, y, currentSum, bestSum, currentCompensation, bestCompensation");//$NON-NLS-1$
    for (i = 0; i < arity; i++) {
      dest.print(", ");//$NON-NLS-1$
      dest.print('z');
      dest.print(i);
    }
    dest.println(';');

    for (i = 0; i < arity; i++) {
      dest.print('z');
      dest.print(i);
      dest.print('=');
      dest.print('x');
      dest.print(i);
      dest.println(';');
    }

    label = 0;
    sum = "bestSum";//$NON-NLS-1$
    comp = "bestCompensation";//$NON-NLS-1$
    first = true;
    for (final int[] perm : new PermutationIterator(arity)) {
      dest.println();
      dest.print("// now testing summation ");//$NON-NLS-1$
      first2 = true;
      for (final int xi : perm) {
        if (first2) {
          first2 = false;
        } else {
          dest.print(" + "); //$NON-NLS-1$
        }
        dest.print('x');
        dest.print(xi - 1);
      }
      dest.println();
      dest.println();
      dest.print(sum);
      dest.print('=');
      dest.print(comp);
      dest.println("=0d;");//$NON-NLS-1$
      dest.print("currentSumLong=x");//$NON-NLS-1$
      dest.print(perm[0] - 1);
      dest.println(';');
      dest.println("isCurrentSumLong=true;");//$NON-NLS-1$

      for (i = 1; i < perm.length; i++) {
        ++label;
        dest.print("compute");//$NON-NLS-1$
        dest.print(label);
        dest.print(':');
        dest.println('{');
        dest.println("if (isCurrentSumLong) {");//$NON-NLS-1$
        dest.print("if (");//$NON-NLS-1$
        dest.print(SaturatingAdd.class.getSimpleName());
        dest.print(".getOverflowType(currentSumLong, x");//$NON-NLS-1$
        dest.print(perm[i] - 1);
        dest.println(") == 0) {");//$NON-NLS-1$
        dest.print("currentSumLong += x");//$NON-NLS-1$
        dest.print(perm[i] - 1);
        dest.println(';');
        dest.print("break compute");//$NON-NLS-1$
        dest.print(label);
        dest.println(';');
        dest.println('}');
        dest.print(sum);
        dest.println(" = currentSumLong;");//$NON-NLS-1$
        dest.print(comp);
        dest.print(" = (((long)");//$NON-NLS-1$
        dest.print(sum);
        dest.println(") - currentSumLong);");//$NON-NLS-1$
        dest.println("isCurrentSumLong = false;");//$NON-NLS-1$
        dest.println('}');

        dest.print("y = z");//$NON-NLS-1$
        dest.print(perm[i] - 1);
        dest.print('-');
        dest.print(comp);
        dest.println(';');
        dest.print("t = ");//$NON-NLS-1$
        dest.print(sum);
        dest.println(" + y;");//$NON-NLS-1$
        dest.print(comp);
        dest.print(" = ((t - ");//$NON-NLS-1$
        dest.print(sum);
        dest.println(") - y);");//$NON-NLS-1$
        dest.print(sum);
        dest.println(" = t;");//$NON-NLS-1$

        dest.print("if((");//$NON-NLS-1$
        dest.print(comp);
        dest.print(" == 0d) && (");//$NON-NLS-1$
        dest.print(NumericalTypes.class.getSimpleName());
        dest.print(".isLong(");//$NON-NLS-1$
        dest.print(sum);
        dest.println("))) {");//$NON-NLS-1$
        dest.print("currentSumLong = ((long) ");//$NON-NLS-1$
        dest.print(sum);
        dest.println(");");//$NON-NLS-1$
        dest.println("isCurrentSumLong = true;");//$NON-NLS-1$
        dest.println('}');
        dest.println('}');
      }

      dest.println("if(isCurrentSumLong) {");//$NON-NLS-1$
      dest.println("return currentSumLong;");//$NON-NLS-1$
      dest.println('}');

      if (first) {
        first = false;
        dest.println("if(bestCompensation<0d) {");//$NON-NLS-1$
        dest.println("bestCompensation=(-bestCompensation);");//$NON-NLS-1$
        dest.println("} else {");//$NON-NLS-1$
        dest.println("if(bestCompensation<=0d) {");//$NON-NLS-1$
        dest.println("return bestSum;");//$NON-NLS-1$
        dest.println('}');
        dest.println('}');
      } else {
        dest.println("currentCompensation=Math.abs(currentCompensation);");//$NON-NLS-1$
        dest.println("if(currentCompensation<bestCompensation){");//$NON-NLS-1$
        dest.println("if(currentCompensation<=0d){");//$NON-NLS-1$
        dest.println("return currentSum;");//$NON-NLS-1$
        dest.println('}');
        dest.println("bestCompensation=currentCompensation;");//$NON-NLS-1$
        dest.println("bestSum=currentSum;");//$NON-NLS-1$
        dest.println('}');
      }

      sum = "currentSum";//$NON-NLS-1$
      comp = "currentCompensation";//$NON-NLS-1$
    }

    dest.println("return bestSum;");//$NON-NLS-1$
  }

  /**
   * the main method
   *
   * @param args
   *          the arguments
   * @throws IOException
   *           if I/O fails
   */
  public static final void main(final String[] args) throws IOException {
    final HighPrecisionAdderCodeGen gen;
    String clazz;

    gen = new HighPrecisionAdderCodeGen(args);

    for (int arity = 3; arity <= CodeGeneratorBase.MAX_FUNCTION_ARITY; arity++) {
      System.out.println("Arity: " + arity);//$NON-NLS-1$
      clazz = "Add" + arity;//$NON-NLS-1$
      System.out.println("Class: " + clazz);//$NON-NLS-1$
      gen.__doArity(arity, clazz);
    }
  }
}
