package org.optimizationBenchmarking.utils.document.spec;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A mathematics output device. The difference the the normal
 * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
 * is that you are only allowed to choose one output method: Either you
 * write some text, a number, or perform a mathematical operation.
 */
public interface IMath extends IDocumentPart, ITextOutput {

  /**
   * Write some mathematical content in braces. The underlying
   * implementation will select the right brace marks and may deal with
   * nested braces. It will return an instance of
   * {@link org.optimizationBenchmarking.utils.document.spec.IMath} to
   * which output can be written. This output will appear surrounded by the
   * correct brace characters in the underlying stream.
   * 
   * @return an instance of
   *         {@link org.optimizationBenchmarking.utils.document.spec.IText}
   *         whose text will appear in braces in the underlying stream
   */
  public IMath inBraces();

  /**
   * Invoke a math macro
   * 
   * @param macro
   *          the macro to invoke
   * @return the macro invocation
   */
  public abstract IMathMacroInvocation invoke(final MathMacro macro);

  /**
   * An
   * {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   * instance representing the addition ({@code +}) with &ge; 2 arguments.
   * 
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   *         for {@code +}
   */
  public abstract IMathFunction add();

  /**
   * An
   * {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   * instance representing subtraction ({@code -}) with &ge; 2 arguments.
   * 
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   *         for {@code -}
   */
  public abstract IMathFunction sub();

  /**
   * An
   * {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   * instance representing the multiplication ({@code *}) with &ge; 2
   * arguments.
   * 
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   *         for {@code *}
   */
  public abstract IMathFunction mul();

  /**
   * An
   * {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   * instance representing the division ({@code /}) with &ge; 2 arguments.
   * 
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   *         for {@code /}
   */
  public abstract IMathFunction div();

  /**
   * An
   * {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   * instance representing the modulo division ({@code mod}) with exactly 2
   * arguments.
   * 
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   *         for {@code mod}
   */
  public abstract IMathFunction mod();

  /**
   * An
   * {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   * instance representing the general logarithm ({@code log}) with exactly
   * 2 arguments (base and value).
   * 
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   *         for {@code log}
   */
  public abstract IMathFunction log();

  /**
   * An
   * {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   * instance representing the natural logarithm ({@code ln}) with exactly
   * 1 argument (and base {@link java.lang.Math#E}).
   * 
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   *         for {@code ln}
   */
  public abstract IMathFunction ln();

  /**
   * An
   * {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   * instance representing the logarithm dualis ({@code ld}) with exactly 1
   * argument (and base {@code 2}).
   * 
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   *         for {@code ld}
   */
  public abstract IMathFunction ld();

  /**
   * An
   * {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   * instance representing the logarithm gesimalis ({@code lg}) with
   * exactly 1 argument (base {@code 10}).
   * 
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   *         for {@code lg}
   */
  public abstract IMathFunction lg();

  /**
   * An
   * {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   * instance representing the power ({@code a^b}) with exactly 2 arguments
   * (base and power).
   * 
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   *         for {@code pow}
   */
  public abstract IMathFunction pow();

  /**
   * An
   * {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   * instance representing the root ({@code a^(1/b)}) with exactly 2
   * arguments (base and root).
   * 
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   *         for {@code root}
   */
  public abstract IMathFunction root();

  /**
   * An
   * {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   * instance representing the square root ({@code sqrt(a)}) with exactly 1
   * argument (the number).
   * 
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   *         for {@code sqrt}
   */
  public abstract IMathFunction sqrt();

  /**
   * An
   * {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   * instance representing a comparison ({@code a =/!=/</>... b}) with
   * exactly 2 arguments (the two values to be compared).
   * 
   * @param cmp
   *          the comparator
   * @return {@link org.optimizationBenchmarking.utils.document.spec.IMathFunction}
   *         for comparisons
   */
  public abstract IMathFunction compare(final EComparison cmp);

  /**
   * A subscript
   * 
   * @return a mathematical subscript
   */
  public abstract IMath subscript();

  /**
   * A superscript
   * 
   * @return a mathematical superscript
   */
  public abstract IMath superscript();

}
