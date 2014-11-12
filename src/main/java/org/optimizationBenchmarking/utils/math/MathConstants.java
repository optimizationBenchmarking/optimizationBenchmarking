package org.optimizationBenchmarking.utils.math;

/** A set of common mathematical constants */
public final class MathConstants {

  /** the zero double */
  public static final Double DOUBLE_ZERO = Double.valueOf(0d);

  /** the one double */
  public static final Double DOUBLE_ONE = Double.valueOf(1d);

  /** the nan */
  public static final Double DOUBLE_NAN = Double.valueOf(Double.NaN);

  /**
   * two pi:
   * <code>{@value} = {@link java.lang.Math#PI Math.PI}+{@link java.lang.Math#PI Math.PI}</code>
   */
  public static final double TWO_PI = 6.283185307179586476925286766559d;
  // (Math.PI + Math.PI);

  /**
   * The natural logarithm of 10, i.e.,
   * <code>{@value} = {@link java.lang.Math#log(double) Math.log(10d)}</code>
   */
  public static final double LN_10 = 2.3025850929940456840179914546844d;

  /**
   * The natural logarithm of 2, i.e.,
   * <code>{@value} = {@link java.lang.Math#log(double) Math.log(2)}</code>
   */
  public static final double LN_2 = 0.69314718055994530941723212145818d;

  /**
   * The square root of pi, i.e.,
   * <code>{@value} = {@link java.lang.Math#sqrt(double) Math.sqrt(Math.PI)}</code>
   */
  public static final double SQRT_PI = 1.7724538509055160272981674833411d;

  /**
   * The natural logarithm of the square root of pi, i.e.,
   * <code>{@value} = {@link java.lang.Math#log(double) Math.log}({@link #SQRT_PI})</code>
   */
  public static final double LOG_SQRT_PI = 0.57236494292470008707171367567653d;

  /**
   * 1.0 / square root of pi, i.e.,
   * <code>{@value} = 1.0d / {@link #SQRT_PI}</code>
   */
  public static final double INV_SQRT_PI = 0.56418958354775628694807945156077d;

  /**
   * The square root of 2.0, i.e.,
   * <code>{@value} = {@link java.lang.Math#sqrt(double) Math.sqrt(2.0d)}</code>
   */
  public static final double SQRT_2 = 1.4142135623730950488016887242097d;

  /**
   * The square root of 2*pi, i.e.,
   * <code>{@value} = {@link #SQRT_2}*{@link #SQRT_PI}</code>
   */
  public static final double SQRT_2_PI = 2.506628274631000502415765284811d;

  /**
   * The natural logarithm of the square root of pi, i.e.,
   * <code>{@value} = {@link java.lang.Math#log(double) Math.log}({@link #SQRT_PI})</code>
   */
  public static final double LN_SQRT_PI = 0.57236494292470008707171367567653d;

  /** Euler's (Mascheroni's) constant, i.e., {@value} */
  public static final double EULER_CONSTANT = 0.57721566490153286060651209008240243104215933593992359880576723488486772677766467093694706329174674951463144724980708248096050401448654283622417399764492353625350033374293733773767394279259525824709491600873520394816567d;

  /**
   * The smallest difference of two numbers, if they differ smaller, they
   * are considered as equal
   */
  public static final double EPS = Math.pow(2.0d, -52.0d);

  /** The natural logarithm of EPS */
  public static final double LN_EPS = Math.log(MathConstants.EPS);

  /**
   * The natural logarithm of Double.MAX_VALUE, or, in other words, the
   * biggest value where <code>Math.exp(x)</code> produces an exact result.
   * 
   * @see Double#MAX_VALUE
   */
  public static final double LN_MAX_DOUBLE = Math.log(Double.MAX_VALUE);

  /**
   * The natural logarithm of Double.MIN_VALUE, or, in other words, the
   * smallest value where <code>Math.exp(x)</code> produces an exact
   * result.
   * 
   * @see Double#MIN_VALUE
   */
  public static final double LN_MIN_DOUBLE = Math.log(Double.MIN_VALUE);

  /**
   * multiplying a number {@code s} by {@value #GOLDEN_RATIO} will give you
   * the longer part of it if it is cut in the golden ratio:
   * <code>{@value} = (0.5d * ({@link java.lang.Math#sqrt(double) Math.sqrt(5d)} - 1d))</code>
   */
  public static final double GOLDEN_RATIO = 0.61803398874989484820458683436564d;

  /** forbidden */
  private MathConstants() {
    throw new UnsupportedOperationException();
  }
}
