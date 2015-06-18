package org.optimizationBenchmarking.utils.document.spec;

/**
 * A mathematics output device. The difference the the normal
 * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
 * is that you are only allowed to choose one output method: Either you
 * write some text, a number, or perform a mathematical operation.
 */
public interface IMath extends IDocumentElement {

  /**
   * Write some mathematical content in braces. The underlying
   * implementation will select the right brace marks and may deal with
   * nested braces. It will return an instance of
   * {@link org.optimizationBenchmarking.utils.document.spec.IMath} to
   * which output can be written. This output will appear surrounded by the
   * correct brace characters in the underlying stream.
   *
   * @return an instance of
   *         {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         whose text will appear in braces in the underlying stream
   */
  public IMath inBraces();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing the addition ({@code +}) with &ge; 2 arguments.
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for {@code +}
   */
  public abstract IMath add();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing subtraction ({@code -}) with &ge; 2 arguments.
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for {@code -}
   */
  public abstract IMath sub();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing the multiplication ({@code *}) with &ge; 2
   * arguments.
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for {@code *}
   */
  public abstract IMath mul();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing the division ({@code /}) with &ge; 2 arguments.
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for {@code /}
   */
  public abstract IMath div();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing the in-line division ({@code /}) with &ge; 2
   * arguments.
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for in-line {@code /}
   */
  public abstract IMath divInline();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing the modulo division ({@code mod}) with exactly 2
   * arguments.
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for {@code mod}
   */
  public abstract IMath mod();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing the general logarithm ({@code log}) with exactly
   * 2 arguments (base and value).
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for {@code log}
   */
  public abstract IMath log();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing the natural logarithm ({@code ln}) with exactly
   * 1 argument (and base {@link java.lang.Math#E}).
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for {@code ln}
   */
  public abstract IMath ln();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing the logarithm dualis ({@code ld}) with exactly 1
   * argument (and base {@code 2}).
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for {@code ld}
   */
  public abstract IMath ld();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing the logarithm gesimalis ({@code lg}) with
   * exactly 1 argument (base {@code 10}).
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for {@code lg}
   */
  public abstract IMath lg();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing the power ({@code a^b}) with exactly 2 arguments
   * (base and power).
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for {@code pow}
   */
  public abstract IMath pow();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing the root ({@code a^(1/b)}) with exactly 2
   * arguments (base and root).
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for {@code root}
   */
  public abstract IMath root();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing the square root ({@code sqrt(a)}) with exactly 1
   * argument (the number).
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for {@code sqrt}
   */
  public abstract IMath sqrt();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing the cubic root ({@code cbrt(a)}) with exactly 1
   * argument (the number).
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for {@code cbrt}
   */
  public abstract IMath cbrt();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing a comparison ({@code a =/!=/</>... b}) with
   * exactly 2 arguments (the two values to be compared).
   *
   * @param cmp
   *          the comparator
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for comparisons
   */
  public abstract IMath compare(final EMathComparison cmp);

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing the negation of something ({@code -a}) with
   * exactly 1 argument.
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for {@code -}
   */
  public abstract IMath negate();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing the absolute of something ({@code |a|}) with
   * exactly 1 argument.
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for {@code |.|}
   */
  public abstract IMath abs();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing the factorial of something ({@code a!}) with
   * exactly 1 argument.
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for {@code !}
   */
  public abstract IMath factorial();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing the sine of something ({@code sin a}) with
   * exactly 1 argument.
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for {@code sin}
   */
  public abstract IMath sin();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing the cosine of something ({@code cos a}) with
   * exactly 1 argument.
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for {@code cos}
   */
  public abstract IMath cos();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing the tangent of something ({@code tan a}) with
   * exactly 1 argument.
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for {@code tan}
   */
  public abstract IMath tan();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing the exponent of something ({@code exp a}) with
   * exactly 1 argument.
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for {@code exp}
   */
  public abstract IMath exp();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing the square of something ({@code a²}) with
   * exactly 1 argument.
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for {@code sqr}
   */
  public abstract IMath sqr();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing the cube of something ({@code a³}) with exactly
   * 1 argument.
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for {@code cube}
   */
  public abstract IMath cube();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing the maximum of something (
   * <code>max{a,1,2}</code>) with at least 1 argument.
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for {@code max}
   */
  public abstract IMath max();

  /**
   * An {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * instance representing the minimum of something (
   * <code>min{a,1,2}</code>) with at least 1 argument.
   *
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   *         for {@code min}
   */
  public abstract IMath min();

  /**
   * Print an {@code n}-ary function
   *
   * @param name
   *          the name of the function
   * @param minArity
   *          the minimum number of arguments
   * @param maxArity
   *          the maximum number of arguments
   * @return the mathematical context allowing us to put the arguments
   */
  public abstract IMath nAryFunction(final String name,
      final int minArity, final int maxArity);

  /**
   * Write some plain text
   *
   * @return the mathematical text
   */
  public abstract IComplexText text();

  /**
   * Write the identifier of a function or variable or something
   *
   * @return the mathematical text
   */
  public abstract IMathName name();

  /**
   * Write a number. The text written to the returned context is rendered
   * in a numerical style.
   *
   * @return the mathematical number
   */
  public abstract IText number();

}
