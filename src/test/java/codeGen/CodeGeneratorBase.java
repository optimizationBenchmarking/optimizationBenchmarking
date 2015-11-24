package codeGen;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;
import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;
import org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction;
import org.optimizationBenchmarking.utils.math.functions.TernaryFunction;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/**
 * This is the internal base class for code generators.
 */
public class CodeGeneratorBase {

  /** the mathematical functions of a given arity */
  @SuppressWarnings("rawtypes")
  private static final Class[] FUNCTIONS_OF_ARITY = {
      MathematicalFunction.class, UnaryFunction.class,
      BinaryFunction.class, TernaryFunction.class,
      QuaternaryFunction.class };

  /** the maximum arity */
  protected static final int MAX_FUNCTION_ARITY = (CodeGeneratorBase.FUNCTIONS_OF_ARITY.length
      - 1);

  /** position texts for given integers */
  private static final String[] INDEXES = { //
      "zeroth", //$NON-NLS-1$
      "first", //$NON-NLS-1$
      "second", //$NON-NLS-1$
      "third", //$NON-NLS-1$
      "fourth", //$NON-NLS-1$
      "fifth", //$NON-NLS-1$
      "sixth", //$NON-NLS-1$
      "seventh", //$NON-NLS-1$
      "eighth", //$NON-NLS-1$
      "ninth", //$NON-NLS-1$
      "tenth", //$NON-NLS-1$
      "eleventh", //$NON-NLS-1$
      "twelfth",//$NON-NLS-1$
  };

  /** the package name */
  private final String m_packageName;

  /** the package path */
  private final Path m_packagePath;

  /**
   * Generate the code generator base class
   *
   * @param args
   *          the command line arguments
   * @throws IOException
   *           if i/o fails
   */
  protected CodeGeneratorBase(final String[] args) throws IOException {
    super();

    final String name, qn;
    Path packagePath;

    name = this.getClass().getPackage().getName();
    qn = CodeGeneratorBase.class.getPackage().getName();
    if (name.startsWith(qn)) {
      this.m_packageName = name.substring(qn.length() + 1);
    } else {
      this.m_packageName = name;
    }

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
   * Get the path to the package.
   *
   * @return the path to the package.
   */
  public final Path getPackagePath() {
    return this.m_packagePath;
  }

  /**
   * Get the name of the package
   *
   * @return the name of the package
   */
  public final String getPackageName() {
    return this.m_packageName;
  }

  /**
   * Get the mathematical function class of the given arity
   *
   * @param arity
   *          the arity
   * @return the type of mathematical function fitting to the arity
   */
  @SuppressWarnings("unchecked")
  public static final Class<? extends MathematicalFunction> getMathematicalFunctionClassOfArity(
      final int arity) {
    return (arity < CodeGeneratorBase.FUNCTIONS_OF_ARITY.length)
        ? CodeGeneratorBase.FUNCTIONS_OF_ARITY[arity]
        : MathematicalFunction.class;
  }

  /**
   * Get the name of a given position index
   *
   * @param index
   *          the position index
   * @return the name
   */
  public static final String getPositionIndexName(final int index) {
    return CodeGeneratorBase.INDEXES[index];
  }

  /**
   * import a given class
   *
   * @param clazz
   *          the class to import
   * @param dest
   *          the destination writer
   */
  public static final void importClass(final String clazz,
      final PrintWriter dest) {
    dest.print("import ");//$NON-NLS-1$
    dest.print(clazz);
    dest.println(';');
  }

  /**
   * import a given class
   *
   * @param clazz
   *          the class to import
   * @param dest
   *          the destination writer
   */
  public static final void importClass(final Class<?> clazz,
      final PrintWriter dest) {
    CodeGeneratorBase.importClass(clazz.getCanonicalName(), dest);
  }

  /**
   * Write a package name
   *
   * @param pack
   *          the package name
   * @param dest
   *          the destination
   */
  public static final void writePackage(final String pack,
      final PrintWriter dest) {
    dest.print("package ");//$NON-NLS-1$
    dest.print(pack);
    dest.println(';');

  }
}
