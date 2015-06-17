package org.optimizationBenchmarking.utils.math.functions.compound;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.math3.primes.Primes;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;
import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;
import org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction;
import org.optimizationBenchmarking.utils.math.functions.TernaryFunction;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Absolute;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Identity;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Negate;
import org.optimizationBenchmarking.utils.math.functions.combinatoric.Factorial;
import org.optimizationBenchmarking.utils.math.text.AbstractParameterRenderer;
import org.optimizationBenchmarking.utils.math.text.DefaultParameterRenderer;
import org.optimizationBenchmarking.utils.math.text.IMathRenderable;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * This is the internal class used to generate the code for the compound
 * functions.
 */
final class _CodeGenerator {

  /** the mathematical functions of a given arity */
  private static final Class<?>[] FUNCTIONS_OF_ARITY = {
      MathematicalFunction.class, UnaryFunction.class,
      BinaryFunction.class, TernaryFunction.class,
      QuaternaryFunction.class };

  /** the maximum arity */
  private static final int MAX_ARITY = (_CodeGenerator.FUNCTIONS_OF_ARITY.length - 1);

  /** position texts for given integers, only need {@link #MAX_ARITY} many */
  private static final String[] INDEXES = { null,//
      "first", //$NON-NLS-1$
      "second",//$NON-NLS-1$
      "third",//$NON-NLS-1$
      "fourth"//$NON-NLS-1$
  };

  /** the package name */
  private final String m_packageName;

  /** the package path */
  private final Path m_packagePath;

  /**
   * @param args
   *          the command line arguments
   * @throws IOException
   *           if i/o fails
   */
  private _CodeGenerator(final String[] args) throws IOException {
    super();
    Path packagePath;

    this.m_packageName = _CodeGenerator.class.getPackage().getName();

    if ((args != null) && (args.length > 0)) {
      packagePath = PathUtils.normalize(args[0]);
    } else {
      packagePath = PathUtils.getCurrentDir();
    }
    packagePath = PathUtils.createPathInside(packagePath,
        ("src/main/java/" + this.m_packageName.replace('.', '/')));//$NON-NLS-1$

    Files.createDirectories(packagePath);
    this.m_packagePath = packagePath;
  }

  /**
   * Generate a function node for {@code m}-ary functions which is a
   * compound of {@code n} single functions.
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param n
   *          the number of compound functions
   * @throws IOException
   *           if i/o fails
   */
  private final void __compound(final int m, final int n)
      throws IOException {
    final String name, mAryName, nAryName;
    final Path path;

    name = _CodeGenerator.__compound_name(m, n);
    path = PathUtils
        .createPathInside(this.m_packagePath, (name + ".java"));//$NON-NLS-1$
    System.out.println(((("Generating code for class '" //$NON-NLS-1$
        + name) + "' in file '") + path + '\'') + '.');//$NON-NLS-1$

    Files.deleteIfExists(path);

    mAryName = _CodeGenerator.FUNCTIONS_OF_ARITY[m].getCanonicalName();
    nAryName = _CodeGenerator.FUNCTIONS_OF_ARITY[n].getCanonicalName();

    try (final OutputStream os = PathUtils.openOutputStream(path)) {
      try (final OutputStreamWriter osw = new OutputStreamWriter(os)) {
        try (final PrintWriter bw = new PrintWriter(osw)) {

          bw.print("package ");//$NON-NLS-1$
          bw.print(this.m_packageName);
          bw.println(';');
          bw.println();

          bw.print("import ");//$NON-NLS-1$
          bw.print(HashUtils.class.getCanonicalName());
          bw.println(';');
          bw.print("import ");//$NON-NLS-1$
          bw.print(ITextOutput.class.getCanonicalName());
          bw.println(';');
          bw.print("import ");//$NON-NLS-1$
          bw.print(MemoryTextOutput.class.getCanonicalName());
          bw.println(';');
          bw.print("import ");//$NON-NLS-1$
          bw.print(DefaultParameterRenderer.class.getCanonicalName());
          bw.println(';');
          bw.print("import ");//$NON-NLS-1$
          bw.print(IMath.class.getCanonicalName());
          bw.println(';');
          bw.print("import ");//$NON-NLS-1$
          bw.print(AbstractParameterRenderer.class.getCanonicalName());
          bw.println(';');
          if (n == 1) {
            bw.print("import ");//$NON-NLS-1$
            bw.print(Absolute.class.getCanonicalName());
            bw.println(';');
          }

          bw.print("import ");//$NON-NLS-1$
          bw.print(mAryName);
          bw.println(';');
          if (m != n) {
            bw.print("import ");//$NON-NLS-1$
            bw.print(nAryName);
            bw.print(';');
            bw.println();
          }
          bw.print("import ");//$NON-NLS-1$
          bw.print(IParameterRenderer.class.getCanonicalName());
          bw.println(';');

          bw.println();

          bw.print("/** This is the automatically generated code for a {@link ");//$NON-NLS-1$
          bw.print(mAryName);
          bw.print(' ');
          bw.print(m);
          bw.print("-ary} which is composed of ");//$NON-NLS-1$
          bw.print(n);
          bw.print(" single functions joined with a {@link ");//$NON-NLS-1$
          bw.print(nAryName);
          bw.print(' ');
          bw.print(n);
          bw.println("-ary} function. */");//$NON-NLS-1$

          bw.print("final class ");//$NON-NLS-1$
          bw.print(name);
          bw.print(" extends ");//$NON-NLS-1$
          bw.print(_CodeGenerator.FUNCTIONS_OF_ARITY[m].getSimpleName());
          bw.println(" {");//$NON-NLS-1$

          bw.println();
          bw.println(" /** the serial version uid */");//$NON-NLS-1$
          bw.println(" private static final long serialVersionUID = 1L;");//$NON-NLS-1$

          this.___compound_makeMembersAndConstructor(m, n, bw);
          for (final Class<?> inType : new Class[] { byte.class,
              short.class, int.class, long.class, float.class,
              double.class }) {
            _CodeGenerator.___compound_makeCompute(m, n, inType, inType,
                bw);
          }

          for (final Class<?> inType : new Class[] { int.class,
              long.class, }) {
            _CodeGenerator.___compound_makeCompute(m, n, inType,
                double.class, bw);
          }

          _CodeGenerator.___compound_makeIsLongArithmeticAccurate(n, bw);
          _CodeGenerator.___compound_makePrecedencePriority(bw);
          _CodeGenerator.___compound_makeMathRender(m, n, bw);
          _CodeGenerator.___compound_makeHashCode(n, bw);
          _CodeGenerator.___compound_makeEquals(m, n, bw);
          _CodeGenerator.__makeToString(bw);

          this.__compound_renderer(m, n, bw);

          bw.println('}');
        }
      }
    }

    System.out.println((("Done with '" + name) + '\'') + '.');//$NON-NLS-1$
  }

  /**
   * Make the compute function for the given type
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param n
   *          the number of compound functions
   * @param inType
   *          the primitive input type
   * @param outType
   *          the primitive output type
   * @param bw
   *          the output writer
   */
  private static final void ___compound_makeCompute(final int m,
      final int n, final Class<?> inType, final Class<?> outType,
      final PrintWriter bw) {
    final String inTypeName, outTypeName, outTypeNameInName;
    int i, j;

    inTypeName = inType.getName();
    if (inType == outType) {
      outTypeName = inTypeName;
    } else {
      outTypeName = outType.getName();
    }
    bw.println();
    bw.println(" /** {@inheritDoc} */");//$NON-NLS-1$
    bw.println(" @Override");//$NON-NLS-1$
    bw.print(" public final ");//$NON-NLS-1$
    bw.print(outTypeName);
    bw.print(' ');
    outTypeNameInName = ("computeAs" + //$NON-NLS-1$
        Character.toUpperCase(outTypeName.charAt(0)) + outTypeName
        .substring(1));
    bw.print(outTypeNameInName);
    bw.print('(');
    for (i = 0; i < m; i++) {
      if (i > 0) {
        bw.print(',');
      }
      bw.print("final ");//$NON-NLS-1$
      bw.print(inTypeName);
      bw.print(' ');
      bw.print('x');
      bw.print(i);
    }
    bw.println(") {");//$NON-NLS-1$

    bw.print(" return this.m_result.");//$NON-NLS-1$
    bw.print(outTypeNameInName);
    bw.print('(');
    for (j = 1; j <= n; j++) {
      if (j > 1) {
        bw.print(',');
      }
      bw.print("this.m_child");//$NON-NLS-1$
      bw.print(j);
      bw.print('.');
      bw.print(outTypeNameInName);
      bw.print('(');
      for (i = 0; i < m; i++) {
        if (i > 0) {
          bw.print(',');
        }
        bw.print('x');
        bw.print(i);
      }
      bw.print(')');
    }

    bw.println(");");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the {@link MathematicalFunction#isLongArithmeticAccurate()}
   * method
   *
   * @param n
   *          the number of compound functions
   * @param bw
   *          the output writer
   */
  private static final void ___compound_makeIsLongArithmeticAccurate(
      final int n, final PrintWriter bw) {
    int j;
    bw.println();
    bw.println();
    bw.println(" /** {@inheritDoc} */");//$NON-NLS-1$
    bw.println(" @Override");//$NON-NLS-1$
    bw.println(" public final boolean isLongArithmeticAccurate() {");//$NON-NLS-1$
    bw.print(" return (this.m_result.isLongArithmeticAccurate()");//$NON-NLS-1$
    for (j = 1; j <= n; j++) {
      bw.print(" && this.m_child"); //$NON-NLS-1$
      bw.print(j);
      bw.print(".isLongArithmeticAccurate()");//$NON-NLS-1$
    }
    bw.println(");");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the {@link Object#hashCode()} method
   *
   * @param n
   *          the number of compound functions
   * @param bw
   *          the output writer
   */
  private static final void ___compound_makeHashCode(final int n,
      final PrintWriter bw) {
    int j;
    bw.println();
    bw.println();
    bw.println(" /** {@inheritDoc} */");//$NON-NLS-1$
    bw.println(" @Override");//$NON-NLS-1$
    bw.println(" public final int hashCode() {");//$NON-NLS-1$
    bw.print(" return ");//$NON-NLS-1$

    for (j = 0; j <= n; j++) {
      if (j > 0) {
        bw.print(',');
      }
      if (j < n) {
        bw.print(" HashUtils.combineHashes("); //$NON-NLS-1$
      }
      bw.print(" HashUtils.hashCode(this.m_"); //$NON-NLS-1$
      if (j <= 0) {
        bw.print("result"); //$NON-NLS-1$
      } else {
        bw.print("child"); //$NON-NLS-1$
        bw.print(j);
      }

      bw.print(')');
    }
    for (j = n; j >= 1; j--) {
      bw.print(')');
    }
    bw.println(";");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the {@link Object#equals(Object)} method
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param n
   *          the number of compound functions
   * @param bw
   *          the output writer
   */
  private static final void ___compound_makeEquals(final int m,
      final int n, final PrintWriter bw) {
    final String name;
    int j;
    bw.println();
    bw.println();
    bw.println(" /** {@inheritDoc} */");//$NON-NLS-1$
    bw.println(" @Override");//$NON-NLS-1$
    bw.println(" public final boolean equals(final Object o) {");//$NON-NLS-1$
    name = _CodeGenerator.__compound_name(m, n);
    bw.print(" final ");//$NON-NLS-1$
    bw.print(name);
    bw.println(" other;");//$NON-NLS-1$
    bw.println(" if (o == this) { return true; }");//$NON-NLS-1$
    bw.print(" if (o instanceof ");//$NON-NLS-1$
    bw.print(name);
    bw.println(") {");//$NON-NLS-1$
    bw.print(" other = ((");//$NON-NLS-1$
    bw.print(name);
    bw.println(") o);");//$NON-NLS-1$

    bw.print(" return (this.m_result.equals(other.m_result)");//$NON-NLS-1$

    for (j = 1; j <= n; j++) {
      bw.print(" && this.m_child"); //$NON-NLS-1$
      bw.print(j);
      bw.print(".equals(");//$NON-NLS-1$
      bw.print("other.m_child"); //$NON-NLS-1$
      bw.print(j);
      bw.print(')');
    }

    bw.println(");");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$

    bw.println(" return false;");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the
   * {@link org.optimizationBenchmarking.utils.math.functions.MathematicalFunction#getPrecedencePriority()}
   * method
   *
   * @param bw
   *          the output writer
   */
  private static final void ___compound_makePrecedencePriority(
      final PrintWriter bw) {
    bw.println();
    bw.println();
    bw.println("/** {@inheritDoc} */");//$NON-NLS-1$
    bw.println("@Override");//$NON-NLS-1$
    bw.println("public int getPrecedencePriority() {");//$NON-NLS-1$
    bw.println("return this.m_result.getPrecedencePriority();");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the member variables of the function node
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param n
   *          the number of compound functions
   * @param bw
   *          the output writer
   */
  private final void ___compound_makeMembersAndConstructor(final int m,
      final int n, final PrintWriter bw) {
    final String[] names;
    final String name, longName;
    int i;

    names = new String[n + 1];
    names[0] = (((((((((("The {@link " + //$NON-NLS-1$
    _CodeGenerator.FUNCTIONS_OF_ARITY[n].getCanonicalName()) + ' ') + n) + //
    "-ary} function used to compute this function's result based on the results of the ")//$NON-NLS-1$
    + n) + //
    " child {@link ") + //$NON-NLS-1$
    _CodeGenerator.FUNCTIONS_OF_ARITY[m].getCanonicalName()) + ' ') + n) + //
    "-ary} functions.");//$NON-NLS-1$

    bw.println();
    bw.print(" /** @serial ");//$NON-NLS-1$
    bw.print(names[0]);
    bw.println(" */");//$NON-NLS-1$
    bw.print(" final ");//$NON-NLS-1$
    bw.print(_CodeGenerator.FUNCTIONS_OF_ARITY[n].getSimpleName());
    bw.println(" m_result;");//$NON-NLS-1$

    for (i = 1; i <= n; i++) {
      names[i] = (((((("The " + _CodeGenerator.INDEXES[i]) + //$NON-NLS-1$
      " child {@link ") + //$NON-NLS-1$
      _CodeGenerator.FUNCTIONS_OF_ARITY[m].getCanonicalName()) + ' ') + m) + //
      "-ary} function which contributes to the result.");//$NON-NLS-1$
      bw.println();
      bw.print(" /** @serial ");//$NON-NLS-1$
      bw.print(names[i]);
      bw.println(" */");//$NON-NLS-1$
      bw.print(" final ");//$NON-NLS-1$
      bw.print(_CodeGenerator.FUNCTIONS_OF_ARITY[m].getSimpleName());
      bw.print(" m_child");//$NON-NLS-1$
      bw.print(i);
      bw.println(';');
    }

    bw.println();
    bw.println();

    name = _CodeGenerator.__compound_name(m, n);
    longName = (((((((((((((("{@link " + //$NON-NLS-1$
    this.m_packageName) + '.') + name) + "}, a function which combines the result of ") + //$NON-NLS-1$
    n) + " child {@link ") //$NON-NLS-1$
    + _CodeGenerator.FUNCTIONS_OF_ARITY[m].getCanonicalName()) + ' ') + m) + "-ary} functions by using an {@link ") + //$NON-NLS-1$
    _CodeGenerator.FUNCTIONS_OF_ARITY[n].getCanonicalName()) + ' ') + n) + "-ary} function");//$NON-NLS-1$

    bw.print(" /** Create the ");//$NON-NLS-1$
    bw.print(longName);
    bw.println('.');
    bw.print(" * @param result ");//$NON-NLS-1$
    bw.println(names[0]);
    for (i = 1; i <= n; i++) {
      bw.print(" * @param child");//$NON-NLS-1$
      bw.print(i);
      bw.print(' ');
      bw.println(names[i]);
    }
    bw.println(" * @throws IllegalArgumentException if any of the parameters is {@code null} */");//$NON-NLS-1$
    bw.print(_CodeGenerator.__compound_name(m, n));
    bw.print("(final ");//$NON-NLS-1$
    bw.print(_CodeGenerator.FUNCTIONS_OF_ARITY[n].getSimpleName());
    bw.print(" result");//$NON-NLS-1$
    for (i = 1; i <= n; i++) {
      bw.print(", final ");//$NON-NLS-1$
      bw.print(_CodeGenerator.FUNCTIONS_OF_ARITY[m].getSimpleName());
      bw.print(" child");//$NON-NLS-1$
      bw.print(i);
    }
    bw.println(") {");//$NON-NLS-1$
    bw.println(" super();");//$NON-NLS-1$

    bw.println(" if(result == null) {");//$NON-NLS-1$
    bw.println(" throw new IllegalArgumentException( //");//$NON-NLS-1$
    bw.print(" \"Result function of "); //$NON-NLS-1$
    bw.print(longName);
    bw.println(//
    ", cannot be null.\"); //$NON-NLS-1$");//$NON-NLS-1$

    bw.println("}");//$NON-NLS-1$

    bw.println(" this.m_result = result;");//$NON-NLS-1$

    for (i = 1; i <= n; i++) {
      bw.print(" if(child");//$NON-NLS-1$
      bw.print(i);
      bw.println(" == null) {");//$NON-NLS-1$
      bw.println(" throw new IllegalArgumentException( //");//$NON-NLS-1$
      bw.print(" \"Child function ");//$NON-NLS-1$
      bw.print(i);
      bw.print(" of "); //$NON-NLS-1$"
      bw.print(longName);
      bw.println(//
      ", cannot be null.\"); //$NON-NLS-1$");//$NON-NLS-1$
      bw.println("}");//$NON-NLS-1$
      bw.print(" this.m_child");//$NON-NLS-1$
      bw.print(i);
      bw.print(" = ");//$NON-NLS-1$
      bw.print(" child");//$NON-NLS-1$
      bw.print(i);
      bw.println(';');
    }

    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the render function for the given type
   *
   * @param m
   *          thefunction arity
   * @param n
   *          the selected index
   * @param bw
   *          the output writer
   */
  private static final void ___compound_makeMathRender(final int m,
      final int n, final PrintWriter bw) {

    bw.println("/** {@inheritDoc} */");//$NON-NLS-1$
    bw.println("@Override");//$NON-NLS-1$
    if (n <= 2) {
      bw.println("@SuppressWarnings(\"resource\")");//$NON-NLS-1$
    }
    bw.print("public final void mathRender(final ");//$NON-NLS-1$
    bw.print(IMath.class.getSimpleName());
    bw.print(" out, final ");//$NON-NLS-1$
    bw.print(IParameterRenderer.class.getSimpleName());
    bw.println(" renderer) {");//$NON-NLS-1$
    if (n <= 2) {
      bw.print("final ");//$NON-NLS-1$
      bw.print(_InternalMath.class.getSimpleName());
      bw.println(" internalMath;");//$NON-NLS-1$
      bw.print("internalMath = new ");//$NON-NLS-1$
      bw.print(_InternalMath.class.getSimpleName());
      bw.print("(out, new ");//$NON-NLS-1$
      bw.print(_CodeGenerator.__compound_renderer_name(m, n));
      bw.println("(renderer));");//$NON-NLS-1$
      bw.println("this.m_result.mathRender(internalMath, internalMath.m_renderer);");//$NON-NLS-1$
    } else {
      bw.println("this.m_result.mathRender(out, new ");//$NON-NLS-1$
      bw.print(_CodeGenerator.__compound_renderer_name(m, n));
      bw.println("(renderer));");//$NON-NLS-1$
    }
    bw.println('}');

    bw.println();
    bw.println();

    bw.println("/** {@inheritDoc} */");//$NON-NLS-1$
    bw.println("@Override");//$NON-NLS-1$
    bw.print("public final void mathRender(final ");//$NON-NLS-1$
    bw.print(ITextOutput.class.getSimpleName());
    bw.print(" out, final ");//$NON-NLS-1$
    bw.print(IParameterRenderer.class.getSimpleName());
    bw.println(" renderer) {");//$NON-NLS-1$

    bw.print("this.m_result.mathRender(out, new ");//$NON-NLS-1$
    bw.print(_CodeGenerator.__compound_renderer_name(m, n));
    bw.println("(renderer));");//$NON-NLS-1$
    bw.println('}');
  }

  /**
   * Get the name of the node representing {@code m}-ary functions which is
   * a compound of {@code n} single functions.
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param n
   *          the number of compound functions
   * @return the {@code n}-ary node name
   */
  private static final String __compound_name(final int m, final int n) {
    return ((("_Compound" + n) + 'x') + m); //$NON-NLS-1$
  }

  /**
   * Generate the parameter renderer of the function node for {@code m}-ary
   * functions which is a compound of {@code n} single functions.
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param n
   *          the number of compound functions
   * @param bw
   *          the writer
   * @throws IOException
   *           if i/o fails
   */
  private final void __compound_renderer(final int m, final int n,
      final PrintWriter bw) throws IOException {
    final String name;

    name = _CodeGenerator.__compound_renderer_name(m, n);

    bw.print("/** This is the automatically generated code of the {@link ");//$NON-NLS-1$
    bw.print(IParameterRenderer.class.getCanonicalName());
    bw.print(" parameter renderer} of the {@link ");//$NON-NLS-1$
    bw.print(_CodeGenerator.__compound_name(m, n));
    bw.println("}. */");//$NON-NLS-1$

    bw.print("private final class ");//$NON-NLS-1$
    bw.print(name);
    bw.print(" extends ");//$NON-NLS-1$
    if (n <= 2) {
      bw.print(_CompoundParameterRendererBase.class.getSimpleName());
    } else {
      bw.print(AbstractParameterRenderer.class.getSimpleName());
    }
    bw.println(" {");//$NON-NLS-1$

    this.___compound_renderer_makeConstructorAndMembers(m, n, bw);
    _CodeGenerator.___compound_renderer_renderParameterMath(m, n, bw);
    _CodeGenerator.___compound_renderer_renderParameterText(m, n, bw);
    _CodeGenerator.___compound_renderer_makeHashCode(m, n, bw);
    _CodeGenerator.___compound_renderer_makeEquals(m, n, bw);

    bw.println('}');

    System.out.println((("Done with '" + name) + '\'') + '.');//$NON-NLS-1$
  }

  /**
   * Make the member variables of the function node
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param n
   *          the number of sub-functions
   * @param bw
   *          the output writer
   */
  private final void ___compound_renderer_makeConstructorAndMembers(
      final int m, final int n, final PrintWriter bw) {
    String name;

    name = ("the {@link " + //$NON-NLS-1$
        IParameterRenderer.class.getCanonicalName() + //
    " parameter renderer} to bridge to");//$NON-NLS-1$
    bw.print("/** ");//$NON-NLS-1$
    bw.print(name);
    bw.println(" */");//$NON-NLS-1$
    bw.print("private final ");//$NON-NLS-1$
    bw.print(IParameterRenderer.class.getSimpleName());
    bw.println(" m_renderer;");//$NON-NLS-1$

    bw.println();
    bw.print(" /** Create the {@link ");//$NON-NLS-1$
    bw.print(IParameterRenderer.class.getCanonicalName());
    bw.print(" parameter renderer} of the {@link ");//$NON-NLS-1$
    bw.print(_CodeGenerator.__compound_name(m, n));
    bw.println('}');
    bw.print("@param renderer ");//$NON-NLS-1$
    bw.println(name);
    bw.println(" * @throws IllegalArgumentException if {@code renderer} is {@code null} */");//$NON-NLS-1$
    bw.print(_CodeGenerator.__compound_renderer_name(m, n));
    bw.print("(final ");//$NON-NLS-1$
    bw.print(IParameterRenderer.class.getSimpleName());
    bw.println(" renderer) {");//$NON-NLS-1$
    bw.println(" super();");//$NON-NLS-1$
    bw.println("if(renderer==null) {");//$NON-NLS-1$
    bw.println("throw new IllegalArgumentException( //");//$NON-NLS-1$
    bw.println("\"The parameter renderer to bridge to cannot be null.\"); //$NON-NLS-1$");//$NON-NLS-1$
    bw.println('}');
    bw.println("this.m_renderer = renderer;");//$NON-NLS-1$
    bw.println('}');
  }

  /**
   * Make the {@link Object#hashCode()} method
   *
   * @param m
   *          the arity of the function
   * @param n
   *          the number of functions
   * @param bw
   *          the output writer
   */
  private static final void ___compound_renderer_makeHashCode(final int m,
      final int n, final PrintWriter bw) {
    bw.println();
    bw.println();
    bw.println("/** {@inheritDoc} */");//$NON-NLS-1$
    bw.println("@Override");//$NON-NLS-1$
    bw.println("public final int hashCode() {");//$NON-NLS-1$

    bw.print(//
    "return HashUtils.combineHashes(HashUtils.hashCode(this.m_renderer), ");//$NON-NLS-1$
    bw.print(_CodeGenerator.__compound_name(m, n));
    bw.println(//
    ".this.hashCode());");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the {@link Object#equals(Object)} method
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param n
   *          the number of functions
   * @param bw
   *          the output writer
   */
  private static final void ___compound_renderer_makeEquals(final int m,
      final int n, final PrintWriter bw) {
    final String name, type;

    name = _CodeGenerator.__compound_name(m, n);
    type = _CodeGenerator.__compound_renderer_name(m, n);
    bw.println();
    bw.println();
    bw.println(" /** the internal owner getter");//$NON-NLS-1$
    bw.print("@return the owning {@link ");//$NON-NLS-1$
    bw.print(name);
    bw.println("} instance */");//$NON-NLS-1$
    bw.print("private final ");//$NON-NLS-1$
    bw.print(name);
    bw.println(" __getOwner() {");//$NON-NLS-1$
    bw.print("return ");//$NON-NLS-1$
    bw.print(name);
    bw.println(".this; }");//$NON-NLS-1$

    bw.println();
    bw.println();
    bw.println("/** {@inheritDoc} */");//$NON-NLS-1$
    bw.println("@Override");//$NON-NLS-1$
    bw.println("public final boolean equals(final Object o) {");//$NON-NLS-1$
    bw.print("final ");//$NON-NLS-1$
    bw.print(type);
    bw.println(" other;");//$NON-NLS-1$
    bw.print("if (o==this) { return true; }");//$NON-NLS-1$

    bw.print("if (o instanceof ");//$NON-NLS-1$
    bw.print(type);
    bw.println(") {");//$NON-NLS-1$

    bw.print("other = ((");//$NON-NLS-1$
    bw.print(type);
    bw.println(") o);");//$NON-NLS-1$

    bw.print("return ((this.m_renderer.equals(other.m_renderer)) && (");//$NON-NLS-1$
    bw.print(name);
    bw.println(".this.equals(other.__getOwner())));");//$NON-NLS-1$

    bw.println("} return false; }");//$NON-NLS-1$
  }

  /**
   * Make the render function for the given type
   *
   * @param m
   *          the arity
   * @param n
   *          the selected index
   * @param bw
   *          the output writer
   */
  private static final void ___compound_renderer_renderParameterMath(
      final int m, final int n, final PrintWriter bw) {
    int index;
    String name;

    bw.println("/** {@inheritDoc} */");//$NON-NLS-1$
    bw.println("@Override");//$NON-NLS-1$
    bw.print("public final void renderParameter(final int index, final ");//$NON-NLS-1$
    bw.print(IMath.class.getSimpleName());
    bw.println(" out) {");//$NON-NLS-1$

    bw.println("switch(index) {");//$NON-NLS-1$

    name = _CodeGenerator.__compound_name(m, n);

    for (index = 0; index < n; index++) {
      bw.print("case ");//$NON-NLS-1$
      bw.print(index);
      bw.println(": {");//$NON-NLS-1$

      if (n <= 2) {
        if (index <= 0) {
          bw.print("if (this.m_bracesNotNeeded || (");//$NON-NLS-1$
          bw.print(name);
          bw.print(".this.m_child");//$NON-NLS-1$
          bw.print(index + 1);
          bw.print(".getPrecedencePriority() > ");//$NON-NLS-1$
          bw.print(name);
          bw.println(".this.m_result.getPrecedencePriority()");//$NON-NLS-1$
          if (n == 1) {
            bw.print(") || (");//$NON-NLS-1$
            bw.print(name);
            bw.print(".this.m_result instanceof ");//$NON-NLS-1$
            bw.print(Absolute.class.getSimpleName());
          }
          bw.println(")) {");//$NON-NLS-1$

          bw.print(name);
          bw.print(".this.m_child");//$NON-NLS-1$
          bw.print(index + 1);
          bw.println(".mathRender(out, this.m_renderer);");//$NON-NLS-1$

          bw.println("} else {");//$NON-NLS-1$

          bw.print("try (final ");//$NON-NLS-1$
          bw.print(IMath.class.getSimpleName());
          bw.print(" braces = out.inBraces()) {");//$NON-NLS-1$

          bw.print(name);
          bw.print(".this.m_child");//$NON-NLS-1$
          bw.print(index + 1);
          bw.println(".mathRender(braces, this.m_renderer);");//$NON-NLS-1$
          bw.println('}');

          bw.println('}');
        } else {
          bw.print("if (this.m_bracesNotNeeded || (");//$NON-NLS-1$
          bw.print(name);
          bw.print(".this.m_child");//$NON-NLS-1$
          bw.print(index + 1);
          bw.print(".getPrecedencePriority() >= ");//$NON-NLS-1$
          bw.print(name);
          bw.println(".this.m_result.getPrecedencePriority())) {");//$NON-NLS-1$

          bw.print(name);
          bw.print(".this.m_child");//$NON-NLS-1$
          bw.print(index + 1);
          bw.println(".mathRender(out, this.m_renderer);");//$NON-NLS-1$

          bw.println("} else {");//$NON-NLS-1$

          bw.print("try (final ");//$NON-NLS-1$
          bw.print(IMath.class.getSimpleName());
          bw.print(" braces = out.inBraces()) {");//$NON-NLS-1$

          bw.print(name);
          bw.print(".this.m_child");//$NON-NLS-1$
          bw.print(index + 1);
          bw.println(".mathRender(braces, this.m_renderer);");//$NON-NLS-1$
          bw.println('}');

          bw.println('}');
        }
      } else {

        bw.print(name);
        bw.print(".this.m_child");//$NON-NLS-1$
        bw.print(index + 1);
        bw.println(".mathRender(out, this.m_renderer);");//$NON-NLS-1$
      }
      bw.println("return; }");//$NON-NLS-1$
    }

    bw.println("default: {");//$NON-NLS-1$
    bw.print(AbstractParameterRenderer.class.getSimpleName());
    bw.print(".throwInvalidParameterIndex(index, ");//$NON-NLS-1$
    bw.print(n - 1);
    bw.println(");");//$NON-NLS-1$
    bw.println('}');
    bw.println('}');
    bw.println('}');
  }

  /**
   * Make the render function for the given type
   *
   * @param m
   *          the arity
   * @param n
   *          the selected index
   * @param bw
   *          the output writer
   */
  private static final void ___compound_renderer_renderParameterText(
      final int m, final int n, final PrintWriter bw) {
    int index;
    String name;

    bw.println("/** {@inheritDoc} */");//$NON-NLS-1$
    bw.println("@Override");//$NON-NLS-1$
    bw.print("public final void renderParameter(final int index, final ");//$NON-NLS-1$
    bw.print(ITextOutput.class.getSimpleName());
    bw.println(" out) {");//$NON-NLS-1$

    if (n <= 2) {
      bw.println("final boolean braces;");//$NON-NLS-1$
    }

    bw.println("switch(index) {");//$NON-NLS-1$

    name = _CodeGenerator.__compound_name(m, n);

    for (index = 0; index < n; index++) {
      bw.print("case ");//$NON-NLS-1$
      bw.print(index);
      bw.println(": {");//$NON-NLS-1$

      if (n <= 2) {
        if (index <= 0) {
          bw.print("braces = (");//$NON-NLS-1$
          if (n == 1) {
            bw.print('(');
          }
          bw.print(name);
          bw.print(".this.m_child");//$NON-NLS-1$
          bw.print(index + 1);
          bw.print(".getPrecedencePriority() <= ");//$NON-NLS-1$
          bw.print(name);
          bw.println(".this.m_result.getPrecedencePriority())");//$NON-NLS-1$
          if (n == 1) {
            bw.print(" && (!(");//$NON-NLS-1$
            bw.print(name);
            bw.println(".this.m_result instanceof ");//$NON-NLS-1$
            bw.print(Absolute.class.getSimpleName());
            bw.print(")));");//$NON-NLS-1$
          } else {
            bw.print(';');
          }

          bw.println("if(braces) {");//$NON-NLS-1$
          bw.println("out.append('(');");//$NON-NLS-1$
          bw.println('}');
        } else {
          bw.print("braces = (");//$NON-NLS-1$
          bw.print(name);
          bw.print(".this.m_child");//$NON-NLS-1$
          bw.print(index + 1);
          bw.print(".getPrecedencePriority() < ");//$NON-NLS-1$
          bw.print(name);
          bw.println(".this.m_result.getPrecedencePriority());");//$NON-NLS-1$
          bw.println("if(braces) {");//$NON-NLS-1$
          bw.println("out.append('(');");//$NON-NLS-1$
          bw.println('}');
        }
      }

      bw.print(name);
      bw.print(".this.m_child");//$NON-NLS-1$
      bw.print(index + 1);
      bw.println(".mathRender(out, this.m_renderer);");//$NON-NLS-1$

      if (n <= 2) {
        bw.println("if(braces) { out.append(')'); }");//$NON-NLS-1$
      }

      bw.println("return; }");//$NON-NLS-1$
    }

    bw.println("default: {");//$NON-NLS-1$
    bw.print(AbstractParameterRenderer.class.getSimpleName());
    bw.print(".throwInvalidParameterIndex(index, ");//$NON-NLS-1$
    bw.print(n - 1);
    bw.println(");");//$NON-NLS-1$
    bw.println('}');
    bw.println('}');
    bw.println('}');
  }

  /**
   * Get the name of the parameter renderer for the node representing
   * {@code m}-ary functions which is a compound of {@code n} single
   * functions.
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param n
   *          the number of compound functions
   * @return the {@code n}-ary node name
   */
  private static final String __compound_renderer_name(final int m,
      final int n) {
    return (((("__Compound" + n) + 'x') + m) + //$NON-NLS-1$
    "ParameterRenderer"); //$NON-NLS-1$
  }

  /**
   * Generate a function which returns one of {@code m} values.
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param sel
   *          the selected value
   * @throws IOException
   *           if i/o fails
   */
  private final void __selection(final int m, final int sel)
      throws IOException {
    final String name, mAryName;
    final Path path;

    name = _CodeGenerator.__selection_name(m, sel);
    path = PathUtils
        .createPathInside(this.m_packagePath, (name + ".java"));//$NON-NLS-1$
    System.out.println(((("Generating code for class '" //$NON-NLS-1$
        + name) + "' in file '") + path + '\'') + '.');//$NON-NLS-1$

    Files.deleteIfExists(path);

    mAryName = _CodeGenerator.FUNCTIONS_OF_ARITY[m].getCanonicalName();

    try (final OutputStream os = PathUtils.openOutputStream(path)) {
      try (final OutputStreamWriter osw = new OutputStreamWriter(os)) {
        try (final PrintWriter bw = new PrintWriter(osw)) {

          bw.print("package ");//$NON-NLS-1$
          bw.print(this.m_packageName);
          bw.println(';');
          bw.print("import ");//$NON-NLS-1$
          bw.print(MemoryTextOutput.class.getCanonicalName());
          bw.println(';');
          bw.print("import ");//$NON-NLS-1$
          bw.print(DefaultParameterRenderer.class.getCanonicalName());
          bw.println(';');
          bw.print("import ");//$NON-NLS-1$
          bw.print(mAryName);
          bw.println(';');
          bw.print("import ");//$NON-NLS-1$
          bw.print(IMath.class.getCanonicalName());
          bw.println(';');
          bw.print("import ");//$NON-NLS-1$
          bw.print(ITextOutput.class.getCanonicalName());
          bw.println(';');
          bw.print("import ");//$NON-NLS-1$
          bw.print(IParameterRenderer.class.getCanonicalName());
          bw.println(';');

          bw.print("/** This is the automatically generated code for a {@link ");//$NON-NLS-1$
          bw.print(mAryName);
          bw.print(' ');
          bw.print(m);
          bw.print("-ary} which returns the ");//$NON-NLS-1$
          bw.print(_CodeGenerator.INDEXES[sel]);
          bw.println("of its input values. */");//$NON-NLS-1$

          bw.print("final class ");//$NON-NLS-1$
          bw.print(name);
          bw.print(" extends ");//$NON-NLS-1$
          bw.print(_CodeGenerator.FUNCTIONS_OF_ARITY[m].getSimpleName());
          bw.println(" {");//$NON-NLS-1$

          bw.println();
          bw.println(" /** the serial version uid */");//$NON-NLS-1$
          bw.println(" private static final long serialVersionUID = 1L;");//$NON-NLS-1$

          this.___selection_makeConstructorAndInstance(m, sel, bw);

          for (final Class<?> inType : new Class[] { byte.class,
              short.class, int.class, long.class, float.class,
              double.class }) {
            _CodeGenerator.___selection_makeCompute(m, sel, inType,
                inType, bw);
          }

          for (final Class<?> inType : new Class[] { int.class,
              long.class, }) {
            _CodeGenerator.___selection_makeCompute(m, sel, inType,
                double.class, bw);
          }

          _CodeGenerator.___selection_makeIsLongArithmeticAccurate(bw);
          _CodeGenerator.___selection_makePrecedencePriority(bw);
          _CodeGenerator.___selection_makeMathRender(sel, bw);
          _CodeGenerator.___selection_makeHashCode(m, sel, bw);
          _CodeGenerator.___selection_makeEquals(m, sel, bw);
          _CodeGenerator.___selection_makeSerial(m, sel, bw);
          _CodeGenerator.__makeToString(bw);

          bw.println('}');
        }
      }
    }

    System.out.println((("Done with '" + name) + '\'') + '.');//$NON-NLS-1$
  }

  /**
   * Make the compute function for the given type
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param sel
   *          the selection index
   * @param inType
   *          the primitive input type
   * @param outType
   *          the primitive output type
   * @param bw
   *          the output writer
   */
  private static final void ___selection_makeCompute(final int m,
      final int sel, final Class<?> inType, final Class<?> outType,
      final PrintWriter bw) {
    final String inTypeName, outTypeName, outTypeNameInName;
    int i;

    inTypeName = inType.getName();
    if (inType == outType) {
      outTypeName = inTypeName;
    } else {
      outTypeName = outType.getName();
    }
    bw.println();
    bw.println(" /** {@inheritDoc} */");//$NON-NLS-1$
    bw.println(" @Override");//$NON-NLS-1$
    bw.print(" public final ");//$NON-NLS-1$
    bw.print(outTypeName);
    bw.print(' ');
    outTypeNameInName = ("computeAs" + //$NON-NLS-1$
        Character.toUpperCase(outTypeName.charAt(0)) + outTypeName
        .substring(1));
    bw.print(outTypeNameInName);
    bw.print('(');
    for (i = 0; i < m; i++) {
      if (i > 0) {
        bw.print(',');
      }
      bw.print("final ");//$NON-NLS-1$
      bw.print(inTypeName);
      bw.print(' ');
      bw.print('x');
      bw.print(i);
    }
    bw.println(") {");//$NON-NLS-1$

    bw.print(" return x");//$NON-NLS-1$
    bw.print(sel - 1);

    bw.println(';');
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the {@link MathematicalFunction#isLongArithmeticAccurate()}
   * method
   *
   * @param bw
   *          the output writer
   */
  private static final void ___selection_makeIsLongArithmeticAccurate(
      final PrintWriter bw) {
    bw.println();
    bw.println();
    bw.println(" /** {@inheritDoc} */");//$NON-NLS-1$
    bw.println(" @Override");//$NON-NLS-1$
    bw.println(" public final boolean isLongArithmeticAccurate() {");//$NON-NLS-1$
    bw.println(" return true;");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the {@link Object#equals(Object)} method
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param sel
   *          the selected element
   * @param bw
   *          the output writer
   */
  private static final void ___selection_makeEquals(final int m,
      final int sel, final PrintWriter bw) {
    bw.println();
    bw.println();
    bw.println(" /** {@inheritDoc} */");//$NON-NLS-1$
    bw.println(" @Override");//$NON-NLS-1$
    bw.println(" public final boolean equals(final Object o) {");//$NON-NLS-1$
    bw.print(" return (o instanceof ");//$NON-NLS-1$
    bw.print(_CodeGenerator.__selection_name(m, sel));
    bw.println(");");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the serialization methods
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param sel
   *          the selected element
   * @param bw
   *          the output writer
   */
  private static final void ___selection_makeSerial(final int m,
      final int sel, final PrintWriter bw) {

    final String name;

    name = _CodeGenerator.__selection_name(m, sel);

    bw.println();
    bw.println();
    bw.println(//
    "  /** Write replace: the instance this method is invoked on will be replaced with the singleton instance {@link #INSTANCE} for serialization, i.e., when the instance is written with {@link java.io.ObjectOutputStream#writeObject(Object)}.");//$NON-NLS-1$
    bw.println(//
    "   * @return the replacement instance (always {@link #INSTANCE})");//$NON-NLS-1$
    bw.println(" */");//$NON-NLS-1$
    bw.println(" private final Object writeReplace() {");//$NON-NLS-1$
    bw.print(" return ");//$NON-NLS-1$
    bw.print(name);
    bw.println(".INSTANCE;");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
    bw.println();
    bw.println(//
    "  /** Read resolve: The instance this method is invoked on will be replaced with the singleton instance {@link #INSTANCE} after serialization, i.e., when the instance is read with {@link java.io.ObjectInputStream#readObject()}.");//$NON-NLS-1$
    bw.println(//
    "   * @return the replacement instance (always {@link #INSTANCE})");//$NON-NLS-1$
    bw.println(" */");//$NON-NLS-1$
    bw.println(" private final Object readResolve() {");//$NON-NLS-1$
    bw.print(" return ");//$NON-NLS-1$
    bw.print(name);
    bw.println(".INSTANCE;");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the {@link Object#hashCode()} method
   *
   * @param m
   *          the arity of the function
   * @param sel
   *          the selection index
   * @param bw
   *          the output writer
   */
  private static final void ___selection_makeHashCode(final int m,
      final int sel, final PrintWriter bw) {
    bw.println();
    bw.println();
    bw.println("/** {@inheritDoc} */");//$NON-NLS-1$
    bw.println("@Override");//$NON-NLS-1$
    bw.println("public final int hashCode() {");//$NON-NLS-1$

    bw.print("return ");//$NON-NLS-1$
    bw.print(Primes.nextPrime((int) (//
        (((long) (Integer.MAX_VALUE)) * ((((m - 1) * m) >>> 1) + (sel - 1))) / //
        ((((_CodeGenerator.MAX_ARITY * (_CodeGenerator.MAX_ARITY + 1)) >>> 1))))));

    bw.println(';');
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the
   * {@link org.optimizationBenchmarking.utils.math.functions.MathematicalFunction#getPrecedencePriority()}
   * method
   *
   * @param bw
   *          the output writer
   */
  private static final void ___selection_makePrecedencePriority(
      final PrintWriter bw) {
    bw.println();
    bw.println();
    bw.println("/** {@inheritDoc} */");//$NON-NLS-1$
    bw.println("@Override");//$NON-NLS-1$
    bw.println("public int getPrecedencePriority() {");//$NON-NLS-1$
    bw.println("return Integer.MAX_VALUE;");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the constructor and shared instance of the selection
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param sel
   *          the selection index
   * @param bw
   *          the output writer
   */
  private final void ___selection_makeConstructorAndInstance(final int m,
      final int sel, final PrintWriter bw) {
    final String name, longName;

    name = _CodeGenerator.__selection_name(m, sel);
    longName = (((((((((("{@link " + //$NON-NLS-1$
    this.m_packageName) + '.') + name) + "}, a {@link ") //$NON-NLS-1$
    + _CodeGenerator.FUNCTIONS_OF_ARITY[m].getCanonicalName()) + ' ') + m) + "-ary} function which returns the value of the ") //$NON-NLS-1$
    + _CodeGenerator.INDEXES[sel]) + " one of its parameters");//$NON-NLS-1$

    bw.print(" /** The globally shared instance of the ");//$NON-NLS-1$
    bw.print(longName);
    bw.println(" */");//$NON-NLS-1$
    bw.print("static final ");//$NON-NLS-1$
    bw.print(name);
    bw.print(" INSTANCE = new ");//$NON-NLS-1$
    bw.print(name);
    bw.println("();");//$NON-NLS-1$
    bw.println();

    bw.print(" /** Create the ");//$NON-NLS-1$
    bw.print(longName);
    bw.println(". */");//$NON-NLS-1$
    bw.print(" private ");//$NON-NLS-1$
    bw.print(name);
    bw.println("() {");//$NON-NLS-1$
    bw.println(" super();");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the render function for the given type
   *
   * @param n
   *          the selected index
   * @param bw
   *          the output writer
   */
  private static final void ___selection_makeMathRender(final int n,
      final PrintWriter bw) {

    bw.println("/** {@inheritDoc} */");//$NON-NLS-1$
    bw.println("@Override");//$NON-NLS-1$
    bw.print("public final void mathRender(final ");//$NON-NLS-1$
    bw.print(IMath.class.getSimpleName());
    bw.print(" out, final ");//$NON-NLS-1$
    bw.print(IParameterRenderer.class.getSimpleName());
    bw.println(" renderer) {");//$NON-NLS-1$
    bw.print("renderer.renderParameter(");//$NON-NLS-1$
    bw.print(n - 1);
    bw.println(", out);");//$NON-NLS-1$
    bw.println('}');

    bw.println();
    bw.println();

    bw.println("/** {@inheritDoc} */");//$NON-NLS-1$
    bw.println("@Override");//$NON-NLS-1$
    bw.print("public final void mathRender(final ");//$NON-NLS-1$
    bw.print(ITextOutput.class.getSimpleName());
    bw.print(" out, final ");//$NON-NLS-1$
    bw.print(IParameterRenderer.class.getSimpleName());
    bw.println(" renderer) {");//$NON-NLS-1$
    bw.print("renderer.renderParameter(");//$NON-NLS-1$
    bw.print(n - 1);
    bw.println(", out);");//$NON-NLS-1$
    bw.println('}');
  }

  /**
   * Get the name of the node representing {@code m}-ary functions which
   * selects one of its outputs.
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param sel
   *          the selected element
   * @return the {@code sel-of-m}-selection node name
   */
  private static final String __selection_name(final int m, final int sel) {
    if ((m <= 1) && (sel <= 1)) {
      return Identity.class.getSimpleName();
    }
    return ((("_Select" + sel) + "of") + m); //$NON-NLS-1$//$NON-NLS-2$
  }

  /**
   * Generate a function which returns a constant value
   *
   * @param m
   *          the number of parameters of the top-level function
   * @throws IOException
   *           if i/o fails
   */
  private final void __const(final int m) throws IOException {
    final String name, mAryName;
    final Path path;

    name = _CodeGenerator.__const_name(m);
    path = PathUtils
        .createPathInside(this.m_packagePath, (name + ".java"));//$NON-NLS-1$
    System.out.println(((("Generating code for class '" //$NON-NLS-1$
        + name) + "' in file '") + path + '\'') + '.');//$NON-NLS-1$

    Files.deleteIfExists(path);

    mAryName = _CodeGenerator.FUNCTIONS_OF_ARITY[m].getCanonicalName();

    try (final OutputStream os = PathUtils.openOutputStream(path)) {
      try (final OutputStreamWriter osw = new OutputStreamWriter(os)) {
        try (final PrintWriter bw = new PrintWriter(osw)) {

          bw.print("package ");//$NON-NLS-1$
          bw.print(this.m_packageName);
          bw.println(';');

          bw.print("import ");//$NON-NLS-1$
          bw.print(mAryName);
          bw.println(';');

          bw.print("import ");//$NON-NLS-1$
          bw.print(NumericalTypes.class.getCanonicalName());
          bw.println(';');
          bw.print("import ");//$NON-NLS-1$
          bw.print(IText.class.getCanonicalName());
          bw.println(';');
          bw.print("import ");//$NON-NLS-1$
          bw.print(IMath.class.getCanonicalName());
          bw.println(';');
          bw.print("import ");//$NON-NLS-1$
          bw.print(ITextOutput.class.getCanonicalName());
          bw.println(';');
          bw.print("import ");//$NON-NLS-1$
          bw.print(Negate.class.getCanonicalName());
          bw.println(';');
          bw.print("import ");//$NON-NLS-1$
          bw.print(MemoryTextOutput.class.getCanonicalName());
          bw.println(';');
          bw.print("import ");//$NON-NLS-1$
          bw.print(DefaultParameterRenderer.class.getCanonicalName());
          bw.println(';');

          bw.print("import ");//$NON-NLS-1$
          bw.print(IParameterRenderer.class.getCanonicalName());
          bw.println(';');
          bw.print("import ");//$NON-NLS-1$
          bw.print(IMathRenderable.class.getCanonicalName());
          bw.println(';');

          bw.print("/** This is the automatically generated code for a {@link ");//$NON-NLS-1$
          bw.print(mAryName);
          bw.print(' ');
          bw.print(m);
          bw.println("-ary} which returns a constant value. */");//$NON-NLS-1$

          bw.print("final class ");//$NON-NLS-1$
          bw.print(name);
          bw.print(" extends ");//$NON-NLS-1$
          bw.print(_CodeGenerator.FUNCTIONS_OF_ARITY[m].getSimpleName());
          bw.println(" {");//$NON-NLS-1$

          bw.println();
          bw.println(" /** the serial version uid */");//$NON-NLS-1$
          bw.println(" private static final long serialVersionUID = 1L;");//$NON-NLS-1$

          this.___const_makeMembersAndConstructor(m, bw);

          for (final Class<?> inType : new Class[] { byte.class,
              short.class, int.class, long.class, float.class,
              double.class }) {
            _CodeGenerator.___const_makeCompute(m, inType, inType, bw);
          }

          for (final Class<?> inType : new Class[] { int.class,
              long.class, }) {
            _CodeGenerator.___const_makeCompute(m, inType, double.class,
                bw);
          }

          _CodeGenerator.___const_makeIsLongArithmeticAccurate(bw);
          _CodeGenerator.___const_makePrecedencePriority(bw);
          _CodeGenerator.___const_makeMathRender(bw);
          _CodeGenerator.___const_makeHashCode(m, bw);
          _CodeGenerator.___const_makeEquals(m, bw);
          _CodeGenerator.__makeToString(bw);
          bw.println('}');
        }
      }
    }

    System.out.println((("Done with '" + name) + '\'') + '.');//$NON-NLS-1$
  }

  /**
   * Make the compute function for the given type
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param inType
   *          the primitive input type
   * @param outType
   *          the primitive output type
   * @param bw
   *          the output writer
   */
  private static final void ___const_makeCompute(final int m,
      final Class<?> inType, final Class<?> outType, final PrintWriter bw) {
    final String inTypeName, outTypeName, outTypeNameInName;
    int i;

    inTypeName = inType.getName();
    if (inType == outType) {
      outTypeName = inTypeName;
    } else {
      outTypeName = outType.getName();
    }
    bw.println();
    bw.println(" /** {@inheritDoc} */");//$NON-NLS-1$
    bw.println(" @Override");//$NON-NLS-1$
    bw.print(" public final ");//$NON-NLS-1$
    bw.print(outTypeName);
    bw.print(' ');
    outTypeNameInName = ("computeAs" + //$NON-NLS-1$
        Character.toUpperCase(outTypeName.charAt(0)) + outTypeName
        .substring(1));
    bw.print(outTypeNameInName);
    bw.print('(');
    for (i = 0; i < m; i++) {
      if (i > 0) {
        bw.print(',');
      }
      bw.print("final ");//$NON-NLS-1$
      bw.print(inTypeName);
      bw.print(' ');
      bw.print('x');
      bw.print(i);
    }
    bw.println(") {");//$NON-NLS-1$

    bw.print(" return this.m_const.");//$NON-NLS-1$
    bw.print(outTypeName);
    bw.println("Value();");//$NON-NLS-1$

    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the render function for the given type
   *
   * @param bw
   *          the output writer
   */
  private static final void ___const_makeMathRender(final PrintWriter bw) {

    bw.println("/** {@inheritDoc} */");//$NON-NLS-1$
    bw.println("@Override");//$NON-NLS-1$
    bw.print("public final void mathRender(final ");//$NON-NLS-1$
    bw.print(IMath.class.getSimpleName());
    bw.print(" out, final ");//$NON-NLS-1$
    bw.print(IParameterRenderer.class.getSimpleName());
    bw.println(" renderer) {");//$NON-NLS-1$
    bw.print("if(this.m_const instanceof ");//$NON-NLS-1$
    bw.print(IMathRenderable.class.getSimpleName());
    bw.println(") { // for named constants");//$NON-NLS-1$
    bw.print("((");//$NON-NLS-1$
    bw.print(IMathRenderable.class.getSimpleName());
    bw.println(")(this.m_const)).mathRender(out, renderer);");//$NON-NLS-1$
    bw.println("} else { // normal constants");//$NON-NLS-1$
    bw.print("try (final ");//$NON-NLS-1$
    bw.print(IText.class.getSimpleName());
    bw.println(" number = out.number()) {");//$NON-NLS-1$
    bw.println("this.mathRender(number, renderer);");//$NON-NLS-1$
    bw.println('}');
    bw.println('}');
    bw.println('}');

    bw.println();
    bw.println();

    bw.println("/** {@inheritDoc} */");//$NON-NLS-1$
    bw.println("@Override");//$NON-NLS-1$
    bw.print("public final void mathRender(final ");//$NON-NLS-1$
    bw.print(ITextOutput.class.getSimpleName());
    bw.print(" out, final ");//$NON-NLS-1$
    bw.print(IParameterRenderer.class.getSimpleName());
    bw.println(" renderer) {");//$NON-NLS-1$
    bw.print("if(this.m_const instanceof ");//$NON-NLS-1$
    bw.print(IMathRenderable.class.getSimpleName());
    bw.println(") { // for named constants");//$NON-NLS-1$
    bw.print("((");//$NON-NLS-1$
    bw.print(IMathRenderable.class.getSimpleName());
    bw.println(")(this.m_const)).mathRender(out, renderer);");//$NON-NLS-1$
    bw.println("} else { // normal constants");//$NON-NLS-1$
    bw.println("if (this.isLongArithmeticAccurate()) {");//$NON-NLS-1$
    bw.println("out.append(this.m_const.longValue());");//$NON-NLS-1$
    bw.println("} else {");//$NON-NLS-1$
    bw.println("out.append(this.m_const.doubleValue());");//$NON-NLS-1$
    bw.println('}');
    bw.println('}');
    bw.println('}');
  }

  /**
   * Make the {@link Object#hashCode()} method
   *
   * @param m
   *          the arity of the function
   * @param bw
   *          the output writer
   */
  private static final void ___const_makeHashCode(final int m,
      final PrintWriter bw) {
    bw.println();
    bw.println();
    bw.println(" /** {@inheritDoc} */");//$NON-NLS-1$
    bw.println(" @Override");//$NON-NLS-1$
    bw.println(" public final int hashCode() {");//$NON-NLS-1$

    bw.print(" return (");//$NON-NLS-1$
    bw.print(Primes.nextPrime(Primes.nextPrime(16384 * m) << (m + 1)));
    bw.println(" ^ this.m_const.hashCode());");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the
   * {@link org.optimizationBenchmarking.utils.math.functions.MathematicalFunction#getPrecedencePriority()}
   * method
   *
   * @param bw
   *          the output writer
   */
  private static final void ___const_makePrecedencePriority(
      final PrintWriter bw) {
    bw.println();
    bw.println();
    bw.println("/** {@inheritDoc} */");//$NON-NLS-1$
    bw.println("@Override");//$NON-NLS-1$
    bw.println("public int getPrecedencePriority() {");//$NON-NLS-1$
    bw.print("return ((this.m_const.doubleValue() >= 0d) ? Integer.MAX_VALUE : ");//$NON-NLS-1$
    bw.print(Negate.class.getSimpleName());
    bw.println(".PRECEDENCE_PRIORITY);");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the {@link Object#equals(Object)} method
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param bw
   *          the output writer
   */
  private static final void ___const_makeEquals(final int m,
      final PrintWriter bw) {
    final String name;

    name = _CodeGenerator.__const_name(m);
    bw.println();
    bw.println();
    bw.println(" /** {@inheritDoc} */");//$NON-NLS-1$
    bw.println(" @Override");//$NON-NLS-1$
    bw.println(" public final boolean equals(final Object o) {");//$NON-NLS-1$
    bw.print(" return ((o instanceof ");//$NON-NLS-1$
    bw.print(name);
    bw.print(") && (this.m_const.equals(((");//$NON-NLS-1$
    bw.print(name);
    bw.println(") o).m_const)));");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the {@link MathematicalFunction#isLongArithmeticAccurate()}
   * method
   *
   * @param bw
   *          the output writer
   */
  private static final void ___const_makeIsLongArithmeticAccurate(
      final PrintWriter bw) {
    bw.println();
    bw.println();
    bw.println(" /** {@inheritDoc} */");//$NON-NLS-1$
    bw.println(" @Override");//$NON-NLS-1$
    bw.println(" public final boolean isLongArithmeticAccurate() {");//$NON-NLS-1$
    bw.println(" return ((NumericalTypes.getTypes(this.m_const) & NumericalTypes.IS_LONG) != 0);");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the member variables of the function node
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param bw
   *          the output writer
   */
  private final void ___const_makeMembersAndConstructor(final int m,
      final PrintWriter bw) {
    final String name, longName, paramName;

    paramName = //
    "the instance of {@link java.lang.Number} holding the constant value returned by this function"; //$NON-NLS-1$

    bw.println();
    bw.print(" /** @serial ");//$NON-NLS-1$
    bw.print(paramName);
    bw.println(" */");//$NON-NLS-1$
    bw.print(" private final Number m_const;");//$NON-NLS-1$

    bw.println();
    bw.println();

    name = _CodeGenerator.__const_name(m);
    longName = (((("{@link " + //$NON-NLS-1$
    this.m_packageName) + '.') + name) + "}, a function which returns a constant value");//$NON-NLS-1$

    bw.print(" /** Create the ");//$NON-NLS-1$
    bw.print(longName);
    bw.println('.');
    bw.print(" * @param constant ");//$NON-NLS-1$
    bw.println(paramName);
    bw.println(" * @throws IllegalArgumentException if {@code constant} is {@code null} */");//$NON-NLS-1$
    bw.print(_CodeGenerator.__const_name(m));
    bw.println("(final Number constant) {");//$NON-NLS-1$
    bw.println(" super();");//$NON-NLS-1$

    bw.println(" if(constant == null) {");//$NON-NLS-1$
    bw.println(" throw new IllegalArgumentException( //");//$NON-NLS-1$
    bw.print(" \"Constant result of "); //$NON-NLS-1$
    bw.print(longName);
    bw.println(//
    ", cannot be null.\"); //$NON-NLS-1$");//$NON-NLS-1$

    bw.println("}");//$NON-NLS-1$

    bw.println(" this.m_const = constant;");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Get the name of the node representing {@code m}-ary functions which
   * returns a constant value.
   *
   * @param m
   *          the number of parameters of the top-level function
   * @return the constant node name
   */
  private static final String __const_name(final int m) {
    return ("_Const" + m); //$NON-NLS-1$
  }

  /**
   * Generate a function builder
   *
   * @param m
   *          the number of parameters of the top-level function
   * @throws IOException
   *           if i/o fails
   */
  private final void __builder(final int m) throws IOException {
    final String name, mAryName;
    final Path path;
    int i;

    name = _CodeGenerator.__builder_name(m);
    path = PathUtils
        .createPathInside(this.m_packagePath, (name + ".java"));//$NON-NLS-1$
    System.out.println(((("Generating code for class '" //$NON-NLS-1$
        + name) + "' in file '") + path + '\'') + '.');//$NON-NLS-1$

    Files.deleteIfExists(path);

    mAryName = _CodeGenerator.FUNCTIONS_OF_ARITY[m].getCanonicalName();

    try (final OutputStream os = PathUtils.openOutputStream(path)) {
      try (final OutputStreamWriter osw = new OutputStreamWriter(os)) {
        try (final PrintWriter bw = new PrintWriter(osw)) {

          bw.print("package ");//$NON-NLS-1$
          bw.print(this.m_packageName);
          bw.println(';');
          bw.println();

          for (i = 1; i <= _CodeGenerator.MAX_ARITY; i++) {
            bw.print("import ");//$NON-NLS-1$
            bw.print(_CodeGenerator.FUNCTIONS_OF_ARITY[i]
                .getCanonicalName());
            bw.println(';');
          }

          if (m <= 1) {
            bw.print("import ");//$NON-NLS-1$
            bw.print(Identity.class.getCanonicalName());
            bw.println(';');
          }

          bw.println();

          bw.print(//
          "/** This is the automatically generated code for a {@link org.optimizationBenchmarking.utils.math.functions.compound builder} of {@link ");//$NON-NLS-1$
          bw.print(mAryName);
          bw.print(' ');
          bw.print(m);
          bw.println("-ary} functions. */");//$NON-NLS-1$

          bw.print("public final class ");//$NON-NLS-1$
          bw.print(name);
          bw.print(" extends FunctionBuilder<");//$NON-NLS-1$
          bw.print(_CodeGenerator.FUNCTIONS_OF_ARITY[m].getSimpleName());
          bw.println("> {");//$NON-NLS-1$

          this.___builder_makeConstructorAndInstance(m, bw);

          for (i = 1; i <= _CodeGenerator.MAX_ARITY; i++) {
            _CodeGenerator.___builder_makeCompound(m, i, bw);
          }

          _CodeGenerator.___builder_makeConstant(m, bw);
          _CodeGenerator.___builder_makeSelection(m, bw);
          _CodeGenerator.___builder_makeFunctionArity(m, bw);
          _CodeGenerator.___builder_makeFunctionClass(m, bw);
          _CodeGenerator.___builder_makeEquals(m, bw);
          _CodeGenerator.___builder_makeHashCode(m, bw);
          bw.println('}');
        }
      }
    }

    System.out.println((("Done with '" + name) + '\'') + '.');//$NON-NLS-1$
  }

  /**
   * Make the compound function for the given type
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param n
   *          the number of compound functions
   * @param bw
   *          the output writer
   */
  private static final void ___builder_makeCompound(final int m,
      final int n, final PrintWriter bw) {
    final String inType, outType;
    int i;

    inType = _CodeGenerator.FUNCTIONS_OF_ARITY[n].getSimpleName();
    outType = _CodeGenerator.FUNCTIONS_OF_ARITY[m].getSimpleName();
    bw.println();
    bw.println(" /** {@inheritDoc} */");//$NON-NLS-1$
    bw.println(" @Override");//$NON-NLS-1$
    bw.print(" public final ");//$NON-NLS-1$
    bw.print(outType);
    bw.print(' ');
    bw.print("compound(final "); //$NON-NLS-1$
    bw.print(inType);
    bw.print(" func");//$NON-NLS-1$
    for (i = 1; i <= n; i++) {
      bw.print(", final ");//$NON-NLS-1$
      bw.print(outType);
      bw.print(" param");//$NON-NLS-1$
      bw.print(i);
    }
    bw.println(") {");//$NON-NLS-1$

    if ((n == 1) && (m == 1)) {
      bw.print("if(param1 instanceof ");//$NON-NLS-1$
      bw.print(Identity.class.getSimpleName());
      bw.println(") {");//$NON-NLS-1$
      bw.println("return func;");//$NON-NLS-1$
      bw.println("}");//$NON-NLS-1$
      bw.print("if(func instanceof ");//$NON-NLS-1$
      bw.print(Identity.class.getSimpleName());
      bw.println(") {");//$NON-NLS-1$
      bw.println("return param1;");//$NON-NLS-1$
      bw.println("}");//$NON-NLS-1$
    }

    bw.print("return new ");//$NON-NLS-1$

    bw.print(_CodeGenerator.__compound_name(m, n));
    bw.print("(func");//$NON-NLS-1$
    for (i = 1; i <= n; i++) {
      bw.print(", ");//$NON-NLS-1$
      bw.print(" param");//$NON-NLS-1$
      bw.print(i);
    }

    bw.println(");");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the constant function for the given type
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param bw
   *          the output writer
   */
  private static final void ___builder_makeConstant(final int m,
      final PrintWriter bw) {
    bw.println();
    bw.println(" /** {@inheritDoc} */");//$NON-NLS-1$
    bw.println(" @Override");//$NON-NLS-1$
    bw.print(" public final ");//$NON-NLS-1$
    bw.print(_CodeGenerator.FUNCTIONS_OF_ARITY[m].getSimpleName());
    bw.print(' ');
    bw.println("constant(final Number value) {"); //$NON-NLS-1$
    bw.print(" return new ");//$NON-NLS-1$
    bw.print(_CodeGenerator.__const_name(m));
    bw.println("(value);");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the getFunctionArity function for the given type
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param bw
   *          the output writer
   */
  private static final void ___builder_makeFunctionArity(final int m,
      final PrintWriter bw) {
    bw.println();
    bw.println(" /** {@inheritDoc} */");//$NON-NLS-1$
    bw.println(" @Override");//$NON-NLS-1$
    bw.println(" public final int getFunctionArity() {");//$NON-NLS-1$
    bw.print(" return ");//$NON-NLS-1$
    bw.print(m);
    bw.println(';');
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the getFunctionClass function for the given type
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param bw
   *          the output writer
   */
  private static final void ___builder_makeFunctionClass(final int m,
      final PrintWriter bw) {
    final String clazz;

    clazz = _CodeGenerator.FUNCTIONS_OF_ARITY[m].getSimpleName();
    bw.println();
    bw.println(" /** {@inheritDoc} */");//$NON-NLS-1$
    bw.println(" @Override");//$NON-NLS-1$
    bw.print(" public final Class<");//$NON-NLS-1$
    bw.print(clazz);
    bw.println("> getFunctionClass() {");//$NON-NLS-1$
    bw.print(" return ");//$NON-NLS-1$
    bw.print(clazz);
    bw.println(".class;");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the {@link java.lang.Object#toString()} method for the given type
   *
   * @param bw
   *          the output writer
   */
  private static final void __makeToString(final PrintWriter bw) {
    bw.println();
    bw.println("/** {@inheritDoc} */");//$NON-NLS-1$
    bw.println("@Override");//$NON-NLS-1$
    bw.print("public final String toString() {");//$NON-NLS-1$
    bw.print("final ");//$NON-NLS-1$
    bw.print(MemoryTextOutput.class.getSimpleName());
    bw.println(" output;");//$NON-NLS-1$

    bw.print("output = new ");//$NON-NLS-1$
    bw.print(MemoryTextOutput.class.getSimpleName());
    bw.println("();");//$NON-NLS-1$

    bw.print("this.mathRender(output, ");//$NON-NLS-1$
    bw.print(DefaultParameterRenderer.class.getSimpleName());
    bw.println(".INSTANCE);");//$NON-NLS-1$

    bw.println("return output.toString();");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the {@link Object#equals(Object)} method
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param bw
   *          the output writer
   */
  private static final void ___builder_makeEquals(final int m,
      final PrintWriter bw) {
    bw.println();
    bw.println();
    bw.println(" /** {@inheritDoc} */");//$NON-NLS-1$
    bw.println(" @Override");//$NON-NLS-1$
    bw.println(" public final boolean equals(final Object o) {");//$NON-NLS-1$
    bw.print(" return (o instanceof ");//$NON-NLS-1$
    bw.print(_CodeGenerator.__builder_name(m));
    bw.println(");");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the {@link Object#hashCode()} method
   *
   * @param m
   *          the arity of the function
   * @param bw
   *          the output writer
   */
  private static final void ___builder_makeHashCode(final int m,
      final PrintWriter bw) {
    bw.println();
    bw.println();
    bw.println(" /** {@inheritDoc} */");//$NON-NLS-1$
    bw.println(" @Override");//$NON-NLS-1$
    bw.println(" public final int hashCode() {");//$NON-NLS-1$

    bw.print(" return ");//$NON-NLS-1$
    bw.print(Primes.nextPrime(//
        Factorial.INSTANCE.computeAsInt(5 + m)));
    bw.println(';');
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the selection function for the given type
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param bw
   *          the output writer
   */
  private static final void ___builder_makeSelection(final int m,
      final PrintWriter bw) {
    int i;

    bw.println();
    bw.println(" /** {@inheritDoc} */");//$NON-NLS-1$
    bw.println(" @Override");//$NON-NLS-1$
    bw.print(" public final ");//$NON-NLS-1$
    bw.print(_CodeGenerator.FUNCTIONS_OF_ARITY[m].getSimpleName());
    bw.print(' ');
    bw.println("parameter(final int index) {"); //$NON-NLS-1$
    bw.println(" switch(index) {");//$NON-NLS-1$

    for (i = 1; i <= m; i++) {
      bw.print(" case ");//$NON-NLS-1$
      bw.print(i - 1);
      bw.print(": { return ");//$NON-NLS-1$
      bw.print(_CodeGenerator.__selection_name(m, i));
      bw.println(".INSTANCE; }");//$NON-NLS-1$
    }

    bw.println(" default: { ");//$NON-NLS-1$
    bw.println(" throw new IllegalArgumentException( //");//$NON-NLS-1$
    bw.print(" \"The parameter index must be in 0..");//$NON-NLS-1$
    bw.print(m - 1);
    bw.println(", but \" //$NON-NLS-1$");//$NON-NLS-1$
    bw.println(" + index + \" was specified.\"); //$NON-NLS-1$");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Make the constructor and shared instance of the builder
   *
   * @param m
   *          the number of parameters of the top-level function
   * @param bw
   *          the output writer
   */
  private final void ___builder_makeConstructorAndInstance(final int m,
      final PrintWriter bw) {
    final String name, longName;

    name = _CodeGenerator.__builder_name(m);
    longName = (((("{@link org.optimizationBenchmarking.utils.math.functions.compound builder} of {@link "//$NON-NLS-1$
    + _CodeGenerator.FUNCTIONS_OF_ARITY[m].getCanonicalName()) + ' ') + m) + "-ary} functions.");//$NON-NLS-1$

    bw.print(" /** The globally shared instance of the ");//$NON-NLS-1$
    bw.print(longName);
    bw.println(" */");//$NON-NLS-1$
    bw.print("private static final ");//$NON-NLS-1$
    bw.print(name);
    bw.print(" INSTANCE = new ");//$NON-NLS-1$
    bw.print(name);
    bw.println("();");//$NON-NLS-1$
    bw.println();

    bw.print(" /** Create the ");//$NON-NLS-1$
    bw.print(longName);
    bw.println(" */");//$NON-NLS-1$
    bw.print(" private ");//$NON-NLS-1$
    bw.print(name);
    bw.println("() {");//$NON-NLS-1$
    bw.println(" super();");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$

    bw.println();
    bw.print(" /** Get an instance of the ");//$NON-NLS-1$
    bw.println(longName);
    bw.print(" * @return an instance of the ");//$NON-NLS-1$
    bw.print(longName);
    bw.println(" */");//$NON-NLS-1$
    bw.print(" public static final ");//$NON-NLS-1$
    bw.print(name);
    bw.println(" getInstance() {");//$NON-NLS-1$
    bw.print(" return ");//$NON-NLS-1$
    bw.print(name);
    bw.println(".INSTANCE;");//$NON-NLS-1$
    bw.println("}");//$NON-NLS-1$
  }

  /**
   * Get the name of the node representing {@code m}-ary function builders
   *
   * @param m
   *          the number of parameters of the top-level function
   * @return the {@code m}-ary function builder name
   */
  private static final String __builder_name(final int m) {
    return (_CodeGenerator.FUNCTIONS_OF_ARITY[m].getSimpleName() + "Builder");//$NON-NLS-1$
  }

  /**
   * Generate the code
   *
   * @param args
   *          the command line arguments
   * @throws IOException
   *           if i/o fails
   */
  public static final void main(final String[] args) throws IOException {
    final _CodeGenerator gen;
    int m, n;

    gen = new _CodeGenerator(args);

    System.out.println((("Starting to generate code to folder '" //$NON-NLS-1$
        + gen.m_packagePath) + '\'') + '.');

    for (m = 1; m <= _CodeGenerator.MAX_ARITY; m++) {
      for (n = 1; n <= _CodeGenerator.MAX_ARITY; n++) {
        gen.__compound(m, n);

        if ((m > 1) && (n <= m)) { // for m==1, we can use Identity
          gen.__selection(m, n);
        }

      }

      gen.__const(m);
      gen.__builder(m);
    }

    System.out.println("Done.");//$NON-NLS-1$
  }
}
