package org.optimizationBenchmarking.utils.math.functions;

import org.apache.commons.math3.util.FastMath;
import org.optimizationBenchmarking.utils.error.ErrorUtils;

/**
 * A set of utility methods allowing us to check for the availability of
 * certain mathematics libraries. I think this <em>may</em> help us to
 * sometimes function even if a required class or library (such as
 * {@link org.apache.commons.math3.util.FastMath}) is not available in the
 * classpath. The idea is to move all calls to such a library, e.g., calls
 * to {@link org.apache.commons.math3.util.FastMath#asin(double)} into a
 * static private function and to only call this function if the
 * corresponding library is actually there, e.g., if {@link #HAS_FASTMATH}
 * is {@code true}, while otherwise calling other routines, e.g.,
 * {@link java.lang.Math#asin(double)}.
 */
public final class MathLibraries {

  /**
   * {@code true} if {@link org.apache.commons.math3.util.FastMath} can be
   * used, {@code false} otherwise.
   */
  public static final boolean HAS_FASTMATH = MathLibraries
      .__checkFastMath();

  /** not allowed */
  private MathLibraries() {
    ErrorUtils.doNotCall();
  }

  /**
   * Check whether {@link org.apache.commons.math3.util.FastMath} is in the
   * classpath.
   * 
   * @return {@code true} if {@link org.apache.commons.math3.util.FastMath}
   *         can be used, {@code false} otherwise.
   */
  private static final boolean __checkFastMath() {
    try {
      FastMath.abs(1d);
      return true;
    } catch (final Throwable error) {
      return false;
    }
  }

}
