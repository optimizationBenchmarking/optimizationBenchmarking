package org.optimizationBenchmarking.utils.math.functions.logic;

import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;

/**
 * A binary boolean function
 * 
 * @author <em><a href="http://www.it-weise.de/">Thomas Weise</a></em>,
 *         Email:&nbsp;<a
 *         href="mailto:tweise@ustc.edu.cn">tweise@ustc.edu.cn</a>,
 *         Tel.:&nbsp;<a href="tel:+86 187 551 228 41">+86 187 551 228
 *         41</a>; <a href="http://www.ustc.edu.cn/">University of Science
 *         and Technology of China (USTC)</a>
 *         [&#x4E2D;&#x56FD;&#x79D1;&#x5B66
 *         ;&#x6280;&#x672F;&#x5927;&#x5B66;], (<a href=
 *         "https://en.wikipedia.org/wiki/University_of_Science_and_Technology_of_China"
 *         >wikipedia</a>); <a href="http://cs.ustc.edu.cn/">School of
 *         Computer Science and Technology (SCST)</a>
 *         [&#x8BA1;&#x7B97;&#x673A;&#x79D1;&#
 *         x5B66;&#x4E0E;&#x6280;&#x672F;&#x5B66;&#x9662;]; <a
 *         href="http://ubri.ustc.edu.cn/">USTC-Birmingham Joint Research
 *         Institute in Intelligent Computation and Its Applications
 *         (UBRI)</a>; West Campus [&#x897F;&#x533A;]; Crossroad of
 *         Huangshan Road and Feixi Road
 *         [&#x9EC4;&#x5C71;&#x8DEF;/&#x80A5;&#x897F;&#x8DEF
 *         ;&#x5341;&#x5B57;&#x8DEF;&#x53E3;]; <a
 *         href="https://en.wikipedia.org/wiki/Hefei">Hefei</a>
 *         [&#x5408;&#x80A5;&#x5E02;] 230027; <a
 *         href="https://en.wikipedia.org/wiki/Anhui">Anhui</a>
 *         [&#x5B89;&#x5FBD;&#x7701;]; <a
 *         href="https://en.wikipedia.org/wiki/People%27s_Republic_of_China"
 *         >China</a> [&#x4E2D;&#x56FD;]
 * @version 
 *          version:0.9.8/file:2013-08-12T06:19:03_101+0800/build:2014-02-22
 *          T09: 28:19_300+0800/platform:Java_1.7
 */
public abstract class BinaryBooleanFunction extends BinaryFunction {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  BinaryBooleanFunction() {
    super();
  }

  /**
   * Compute the function value
   * 
   * @param x1
   *          the first boolean value
   * @param x2
   *          the second boolean value
   * @return the result
   */
  public abstract boolean compute(final boolean x1, final boolean x2);

  /**
   * Check if <a href="http://en.wikipedia.org/wiki/Lazy_eval">lazy
   * evaluation</a> is possible: In some cases, it may be possible to know
   * the result of a {@link #compute(boolean, boolean) binary Boolean
   * expression} even if only its first argument ({@code x1}) is known. In
   * that case, the value of the second argument ({@code x2}) is not
   * needed. Matter of fact, if that second argument is actually an
   * expression, it is not even necessary to evaluate that expression,
   * since we don't care about {@code x2}. Thus, lazy evaluation can safe a
   * lot of runtime.
   * 
   * @param x1
   *          the first argument of the expression
   * @return <ol>
   *         <li>{@code -1} if the result of the expression will be
   *         {@code false}, regardless what value {@code x2} would take on.
   *         </li>
   *         <li>{@code 1} if the result of the expression will be
   *         {@code true} , regardless what value {@code x2} would take on.
   *         </li>
   *         <li>{@code 0} if the value of {@code x2} is absolutely
   *         necessary to find the result of this expression.</li>
   *         </ol>
   */
  public abstract int lazyResultForFirstParam(final boolean x1);

  /** {@inheritDoc} */
  @Override
  public final byte compute(final byte x1, final byte x2) {
    return NumericalLogic.boolean2byte(this.compute(//
        NumericalLogic.byte2boolean(x1),//
        NumericalLogic.byte2boolean(x2)));
  }

  /** {@inheritDoc} */
  @Override
  public final short compute(final short x1, final short x2) {
    return NumericalLogic.boolean2short(this.compute(//
        NumericalLogic.short2boolean(x1),//
        NumericalLogic.short2boolean(x2)));
  }

  /** {@inheritDoc} */
  @Override
  public final int compute(final int x1, final int x2) {
    return NumericalLogic.boolean2int(this.compute(//
        NumericalLogic.int2boolean(x1),//
        NumericalLogic.int2boolean(x2)));
  }

  /** {@inheritDoc} */
  @Override
  public final long compute(final long x1, final long x2) {
    return NumericalLogic.boolean2long(this.compute(//
        NumericalLogic.long2boolean(x1),//
        NumericalLogic.long2boolean(x2)));
  }

  /** {@inheritDoc} */
  @Override
  public final float compute(final float x1, final float x2) {
    return NumericalLogic.boolean2float(this.compute(//
        NumericalLogic.float2boolean(x1),//
        NumericalLogic.float2boolean(x2)));
  }

  /** {@inheritDoc} */
  @Override
  public final double compute(final double x1, final double x2) {
    return NumericalLogic.boolean2double(this.compute(//
        NumericalLogic.double2boolean(x1),//
        NumericalLogic.double2boolean(x2)));
  }
}
