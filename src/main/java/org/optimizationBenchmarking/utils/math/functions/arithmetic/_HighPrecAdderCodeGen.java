package org.optimizationBenchmarking.utils.math.functions.arithmetic;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction;
import org.optimizationBenchmarking.utils.math.functions.TernaryFunction;
import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** ... work in progress ... */
final class _HighPrecAdderCodeGen {

  /** the arities */
  private static final int[] ARITY = { 3, 4 };

  /** the base functions */
  private static final Class<?>[] BASE = { null, null, null, //
      TernaryFunction.class, //
      QuaternaryFunction.class };

  /**
   * Write the function of the given arity
   *
   * @param arity
   *          the arity
   * @param clazz
   *          the class name
   * @param dest
   *          the destination
   */
  private static final void __doArity(final int arity, final String clazz,
      final ITextOutput dest) {
    dest.append("package "); //$NON-NLS-1$
    dest.append(Add.class.getPackage().getName());
    dest.append(';');
    dest.appendLineBreak();

    dest.appendLineBreak();
    dest.append("import "); //$NON-NLS-1$
    dest.append(_HighPrecAdderCodeGen.BASE[arity].getCanonicalName());
    dest.append(';');
    dest.appendLineBreak();
    dest.append("import "); //$NON-NLS-1$
    dest.append(NumericalTypes.class.getCanonicalName());
    dest.append(';');
    dest.appendLineBreak();

    dest.appendLineBreak();
    dest.append("/** The code of an addition ({@code +}) of ");//$NON-NLS-1$
    dest.append(arity);
    dest.append(" arguments designed for highest-precision output. */"); //$NON-NLS-1$
    dest.appendLineBreak();
    dest.append("public final class "); //$NON-NLS-1$
    dest.append(clazz);
    dest.append(" extends "); //$NON-NLS-1$
    dest.append(_HighPrecAdderCodeGen.BASE[arity].getSimpleName());
    dest.append(" {"); //$NON-NLS-1$
    dest.appendLineBreak();

    dest.appendLineBreak();
    dest.append("/** the serial version uid */"); //$NON-NLS-1$
    dest.appendLineBreak();
    dest.append("private static final long serialVersionUID = 1L;"); //$NON-NLS-1$
    dest.appendLineBreak();

    dest.appendLineBreak();
    dest.append(
        "/** the precedence priority of this high-precision addition operator */"); //$NON-NLS-1$
    dest.appendLineBreak();
    dest.append(
        "public static final int PRECEDENCE_PRIORITY = Add.PRECEDENCE_PRIORITY;"); //$NON-NLS-1$
    dest.appendLineBreak();

    dest.appendLineBreak();
    dest.append(
        "/**  the globally shared instance of the high-precision addition operator */"); //$NON-NLS-1$
    dest.appendLineBreak();
    dest.append("public static final "); //$NON-NLS-1$
    dest.append(clazz);
    dest.append(" INSTANCE = new "); //$NON-NLS-1$
    dest.append(clazz);
    dest.append("();"); //$NON-NLS-1$
    dest.appendLineBreak();

    dest.appendLineBreak();
    dest.append("/**  hidden constructor, use {@link #INSTANCE} */"); //$NON-NLS-1$
    dest.appendLineBreak();
    dest.append("private "); //$NON-NLS-1$
    dest.append(clazz);
    dest.append("() {"); //$NON-NLS-1$
    dest.appendLineBreak();
    dest.append("super();"); //$NON-NLS-1$
    dest.appendLineBreak();
    dest.append("}"); //$NON-NLS-1$
    dest.appendLineBreak();

    _HighPrecAdderCodeGen.__doIntegerPrimitives(arity, clazz, dest);
    _HighPrecAdderCodeGen.__doDefaults(clazz, dest);

    _HighPrecAdderCodeGen.__computeAsDouble(arity, clazz, dest);
    _HighPrecAdderCodeGen.__longComputeAsDouble(arity, clazz, dest);
    _HighPrecAdderCodeGen.__doubleComputeAsDouble(arity, clazz, dest);

    dest.appendLineBreak();
    dest.append("}"); //$NON-NLS-1$
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
      final String clazz, final ITextOutput dest) {
    String name;
    int i;

    for (final Class<?> type : new Class<?>[] { byte.class, short.class,
        int.class, long.class }) {
      name = type.getName();

      dest.appendLineBreak();
      dest.append("/** {@inheritDoc} */");//$NON-NLS-1$
      dest.appendLineBreak();
      dest.append("@Override");//$NON-NLS-1$
      dest.appendLineBreak();
      dest.append("public final ");//$NON-NLS-1$
      dest.append(type.getName());
      dest.append(" computeAs");//$NON-NLS-1$
      dest.append(Character.toUpperCase(name.charAt(0)));
      dest.append(name.substring(1));
      dest.append('(');
      for (i = 0; i < arity; i++) {
        if (i > 0) {
          dest.append(", ");//$NON-NLS-1$
        }
        dest.append("final ");//$NON-NLS-1$
        dest.append(name);
        dest.append(" x");//$NON-NLS-1$
        dest.append(i);
      }
      dest.append(") {");//$NON-NLS-1$
      dest.appendLineBreak();
      dest.append("return ");//$NON-NLS-1$
      if ((type == byte.class) || (type == short.class)) {
        dest.append('(');
        dest.append(name);
        dest.append(')');
      }
      dest.append('(');
      for (i = 0; i < arity; i++) {
        if (i > 0) {
          dest.append('+');
        }
        dest.append('x');
        dest.append(i);
      }
      dest.append(");");//$NON-NLS-1$
      dest.appendLineBreak();
      dest.append('}');
      dest.appendLineBreak();
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
      final String clazz, final ITextOutput dest) {
    final String numt;
    String name;
    int i;

    numt = NumericalTypes.class.getSimpleName();

    for (final Class<?> type : new Class<?>[] { long.class,
        double.class }) {
      name = type.getName();

      dest.appendLineBreak();
      dest.append("/** {@inheritDoc} */");//$NON-NLS-1$
      dest.appendLineBreak();
      dest.append("@Override");//$NON-NLS-1$
      dest.appendLineBreak();
      dest.append("public final double computeAsDouble(");//$NON-NLS-1$
      for (i = 0; i < arity; i++) {
        if (i > 0) {
          dest.append(", ");//$NON-NLS-1$
        }
        dest.append("final ");//$NON-NLS-1$
        dest.append(name);
        dest.append(" x");//$NON-NLS-1$
        dest.append(i);
      }
      dest.append(") {");//$NON-NLS-1$
      dest.appendLineBreak();

      if (type == double.class) {
        dest.append("if(");//$NON-NLS-1$

        for (i = 0; i < arity; i++) {
          if (i > 0) {
            dest.append(" && ");//$NON-NLS-1$
          }
          dest.append('(');
          dest.append(numt);
          dest.append(".isLong(x");//$NON-NLS-1$
          dest.append(i);
          dest.append(')');
        }
        dest.append(") {");//$NON-NLS-1$
        dest.appendLineBreak();

        dest.append("return ");//$NON-NLS-1$
        dest.append(clazz);
        dest.append(".__longComputeAsDouble(");//$NON-NLS-1$
        for (i = 0; i < arity; i++) {
          if (i > 0) {
            dest.append(", ");//$NON-NLS-1$
          }
          dest.append("((long)");//$NON-NLS-1$
          dest.append(" x");//$NON-NLS-1$
          dest.append(i);
          dest.append(')');
        }
        dest.append(");");//$NON-NLS-1$
        dest.appendLineBreak();
        dest.append('}');
        dest.appendLineBreak();
      }

      dest.append("return ");//$NON-NLS-1$
      dest.append(clazz);
      dest.append(".__");//$NON-NLS-1$
      dest.append(name);
      dest.append("ComputeAsDouble(");//$NON-NLS-1$
      for (i = 0; i < arity; i++) {
        if (i > 0) {
          dest.append(", ");//$NON-NLS-1$
        }
        dest.append(" x");//$NON-NLS-1$
        dest.append(i);
      }
      dest.append(");");//$NON-NLS-1$
      dest.appendLineBreak();
      dest.append('}');
      dest.appendLineBreak();
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
      final ITextOutput dest) {
    dest.appendLineBreak();
    dest.append("/** {@inheritDoc} */");//$NON-NLS-1$
    dest.appendLineBreak();
    dest.append("@Override");//$NON-NLS-1$
    dest.appendLineBreak();
    dest.append("public final int getPrecedencePriority() {");//$NON-NLS-1$
    dest.appendLineBreak();
    dest.append("return ");//$NON-NLS-1$
    dest.append(clazz);
    dest.append(".PRECEDENCE_PRIORITY");//$NON-NLS-1$
    dest.append(';');
    dest.appendLineBreak();
    dest.append('}');

    dest.appendLineBreak();
    dest.append(//
        "/** Write replace: the instance this method is invoked on will be replaced with the singleton instance {@link #INSTANCE} for serialization, i.e., when the instance is written with {@link java.io.ObjectOutputStream#writeObject(Object)}.");//$NON-NLS-1$
    dest.appendLineBreak();
    dest.append(
        "* @return the replacement instance (always {@link #INSTANCE}) */");//$NON-NLS-1$
    dest.appendLineBreak();
    dest.append("private final Object writeReplace() {");//$NON-NLS-1$
    dest.appendLineBreak();
    dest.append("return ");//$NON-NLS-1$
    dest.append(clazz);
    dest.append(".INSTANCE;");//$NON-NLS-1$
    dest.appendLineBreak();
    dest.append('}');
    dest.appendLineBreak();

    dest.appendLineBreak();
    dest.append(//
        "/** Read resolve: The instance this method is invoked on will be replaced with the singleton instance {@link #INSTANCE} after serialization, i.e., when the instance is read with {@link java.io.ObjectInputStream#readObject()}.");//$NON-NLS-1$
    dest.appendLineBreak();
    dest.append(
        "* @return the replacement instance (always {@link #INSTANCE}) */");//$NON-NLS-1$
    dest.appendLineBreak();
    dest.append("private final Object readResolve() {");//$NON-NLS-1$
    dest.appendLineBreak();
    dest.append("return ");//$NON-NLS-1$
    dest.append(clazz);
    dest.append(".INSTANCE;");//$NON-NLS-1$
    dest.appendLineBreak();
    dest.append('}');
    dest.appendLineBreak();
  }

  /**
   * Compute as double
   *
   * @param arity
   *          the arity
   * @param clazz
   *          the class name
   * @param dest
   *          the destination
   */
  private static final void __doubleComputeAsDouble(final int arity,
      final String clazz, final ITextOutput dest) {
    int i;

    dest.appendLineBreak();
    dest.append("/** Compute the value of the addition of ");//$NON-NLS-1$
    dest.append(arity);
    dest.append(" {@code double} numbers.");//$NON-NLS-1$
    dest.appendLineBreak();
    for (i = 0; i < arity; i++) {
      dest.appendLineBreak();
      dest.append("@param x");//$NON-NLS-1$
      dest.append(i);
      dest.append(" the ");//$NON-NLS-1$
      dest.append(i + 1);
      dest.append(" number to be added");//$NON-NLS-1$
    }
    dest.appendLineBreak();
    dest.append("@return the sum of the numbers */");//$NON-NLS-1$

    dest.appendLineBreak();
    dest.append("private static final double __doubleComputeAsDouble(");//$NON-NLS-1$
    for (i = 0; i < arity; i++) {
      if (i > 0) {
        dest.append(", ");//$NON-NLS-1$
      }
      dest.append("final double x");//$NON-NLS-1$
      dest.append(i);
    }
    dest.append(") {");//$NON-NLS-1$
    dest.appendLineBreak();

    dest.appendLineBreak();
    dest.append("return 0;");//$NON-NLS-1$

    dest.appendLineBreak();
    dest.append('}');
  }

  /**
   * Compute as long
   *
   * @param arity
   *          the arity
   * @param clazz
   *          the class name
   * @param dest
   *          the destination
   */
  private static final void __longComputeAsDouble(final int arity,
      final String clazz, final ITextOutput dest) {
    int i;

    dest.appendLineBreak();
    dest.append("/** Compute the value of the addition of ");//$NON-NLS-1$
    dest.append(arity);
    dest.append(" {@code double} numbers.");//$NON-NLS-1$
    dest.appendLineBreak();
    for (i = 0; i < arity; i++) {
      dest.appendLineBreak();
      dest.append("@param x");//$NON-NLS-1$
      dest.append(i);
      dest.append(" the ");//$NON-NLS-1$
      dest.append(i + 1);
      dest.append(" number to be added");//$NON-NLS-1$
    }
    dest.appendLineBreak();
    dest.append("@return the sum of the numbers */");//$NON-NLS-1$

    dest.appendLineBreak();
    dest.append("private static final double __longComputeAsDouble(");//$NON-NLS-1$
    for (i = 0; i < arity; i++) {
      if (i > 0) {
        dest.append(", ");//$NON-NLS-1$
      }
      dest.append("final long x");//$NON-NLS-1$
      dest.append(i);
    }
    dest.append(") {");//$NON-NLS-1$
    dest.appendLineBreak();

    dest.appendLineBreak();
    dest.append("return 0;");//$NON-NLS-1$

    dest.appendLineBreak();
    dest.append('}');
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
    final Path dest;
    String clazz;

    dest = ((args.length > 0) ? PathUtils.normalize(args[0])
        : PathUtils.getTempDir());
    System.out.println("Path: " + dest); //$NON-NLS-1$

    for (final int arity : _HighPrecAdderCodeGen.ARITY) {
      System.out.println("Arity: " + arity);//$NON-NLS-1$
      clazz = "Add" + arity;//$NON-NLS-1$
      System.out.println("Class: " + clazz);//$NON-NLS-1$
      try (final OutputStream file = PathUtils
          .openOutputStream(dest.resolve(clazz + ".java"))) {//$NON-NLS-1$
        try (final OutputStreamWriter osw = new OutputStreamWriter(file)) {
          try (final BufferedWriter bw = new BufferedWriter(osw)) {
            _HighPrecAdderCodeGen.__doArity(arity, clazz,
                AbstractTextOutput.wrap(bw));
          }
        }
      }
    }
  }
}
