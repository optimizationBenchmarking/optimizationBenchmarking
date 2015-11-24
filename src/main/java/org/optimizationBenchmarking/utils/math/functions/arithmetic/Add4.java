package org.optimizationBenchmarking.utils.math.functions.arithmetic;

import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction;

/**
 * The code of an addition ({@code +}) of 4 arguments designed for
 * highest-precision output.
 */
public final class Add4 extends QuaternaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the precedence priority of this high-precision addition operator */
  public static final int PRECEDENCE_PRIORITY = Add.PRECEDENCE_PRIORITY;

  /**
   * the globally shared instance of the high-precision addition operator
   */
  public static final Add4 INSTANCE = new Add4();

  /** hidden constructor, use {@link #INSTANCE} */
  private Add4() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final byte computeAsByte(final byte x0, final byte x1,
      final byte x2, final byte x3) {
    return (byte) (x0 + x1 + x2 + x3);
  }

  /** {@inheritDoc} */
  @Override
  public final short computeAsShort(final short x0, final short x1,
      final short x2, final short x3) {
    return (short) (x0 + x1 + x2 + x3);
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x0, final int x1, final int x2,
      final int x3) {
    return (x0 + x1 + x2 + x3);
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0, final long x1,
      final long x2, final long x3) {
    return (x0 + x1 + x2 + x3);
  }

  /** {@inheritDoc} */
  @Override
  public final int getPrecedencePriority() {
    return Add4.PRECEDENCE_PRIORITY;
  }

  /**
   * Write replace: the instance this method is invoked on will be replaced
   * with the singleton instance {@link #INSTANCE} for serialization, i.e.,
   * when the instance is written with
   * {@link java.io.ObjectOutputStream#writeObject(Object)}.
   *
   * @return the replacement instance (always {@link #INSTANCE})
   */
  private final Object writeReplace() {
    return Add4.INSTANCE;
  }

  /**
   * Read resolve: The instance this method is invoked on will be replaced
   * with the singleton instance {@link #INSTANCE} after serialization,
   * i.e., when the instance is read with
   * {@link java.io.ObjectInputStream#readObject()}.
   *
   * @return the replacement instance (always {@link #INSTANCE})
   */
  private final Object readResolve() {
    return Add4.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final int x0, final int x1,
      final int x2, final int x3) {
    return ((((((long) x0)) + x1) + x2) + x3);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long x0, final long x1,
      final long x2, final long x3) {
    boolean isCurrentSumLong;
    long currentSumLong;
    double t, y, currentSum, bestSum, currentCompensation,
        bestCompensation, z0, z1, z2, z3;
    z0 = x0;
    z1 = x1;
    z2 = x2;
    z3 = x3;

    // now testing summation x0 + x1 + x2 + x3

    bestSum = bestCompensation = 0d;
    currentSumLong = x0;
    isCurrentSumLong = true;
    compute1: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x1) == 0) {
          currentSumLong += x1;
          break compute1;
        }
        bestSum = currentSumLong;
        bestCompensation = (((long) bestSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z1 - bestCompensation;
      t = bestSum + y;
      bestCompensation = ((t - bestSum) - y);
      bestSum = t;
      if ((bestCompensation == 0d) && (NumericalTypes.isLong(bestSum))) {
        currentSumLong = ((long) bestSum);
        isCurrentSumLong = true;
      }
    }
    compute2: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x2) == 0) {
          currentSumLong += x2;
          break compute2;
        }
        bestSum = currentSumLong;
        bestCompensation = (((long) bestSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z2 - bestCompensation;
      t = bestSum + y;
      bestCompensation = ((t - bestSum) - y);
      bestSum = t;
      if ((bestCompensation == 0d) && (NumericalTypes.isLong(bestSum))) {
        currentSumLong = ((long) bestSum);
        isCurrentSumLong = true;
      }
    }
    compute3: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x3) == 0) {
          currentSumLong += x3;
          break compute3;
        }
        bestSum = currentSumLong;
        bestCompensation = (((long) bestSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z3 - bestCompensation;
      t = bestSum + y;
      bestCompensation = ((t - bestSum) - y);
      bestSum = t;
      if ((bestCompensation == 0d) && (NumericalTypes.isLong(bestSum))) {
        currentSumLong = ((long) bestSum);
        isCurrentSumLong = true;
      }
    }
    if (isCurrentSumLong) {
      return currentSumLong;
    }
    if (bestCompensation < 0d) {
      bestCompensation = (-bestCompensation);
    } else {
      if (bestCompensation <= 0d) {
        return bestSum;
      }
    }

    // now testing summation x0 + x1 + x3 + x2

    currentSum = currentCompensation = 0d;
    currentSumLong = x0;
    isCurrentSumLong = true;
    compute4: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x1) == 0) {
          currentSumLong += x1;
          break compute4;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z1 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute5: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x3) == 0) {
          currentSumLong += x3;
          break compute5;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z3 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute6: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x2) == 0) {
          currentSumLong += x2;
          break compute6;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z2 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    if (isCurrentSumLong) {
      return currentSumLong;
    }
    currentCompensation = Math.abs(currentCompensation);
    if (currentCompensation < bestCompensation) {
      if (currentCompensation <= 0d) {
        return currentSum;
      }
      bestCompensation = currentCompensation;
      bestSum = currentSum;
    }

    // now testing summation x0 + x3 + x1 + x2

    currentSum = currentCompensation = 0d;
    currentSumLong = x0;
    isCurrentSumLong = true;
    compute7: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x3) == 0) {
          currentSumLong += x3;
          break compute7;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z3 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute8: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x1) == 0) {
          currentSumLong += x1;
          break compute8;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z1 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute9: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x2) == 0) {
          currentSumLong += x2;
          break compute9;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z2 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    if (isCurrentSumLong) {
      return currentSumLong;
    }
    currentCompensation = Math.abs(currentCompensation);
    if (currentCompensation < bestCompensation) {
      if (currentCompensation <= 0d) {
        return currentSum;
      }
      bestCompensation = currentCompensation;
      bestSum = currentSum;
    }

    // now testing summation x3 + x0 + x1 + x2

    currentSum = currentCompensation = 0d;
    currentSumLong = x3;
    isCurrentSumLong = true;
    compute10: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x0) == 0) {
          currentSumLong += x0;
          break compute10;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z0 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute11: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x1) == 0) {
          currentSumLong += x1;
          break compute11;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z1 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute12: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x2) == 0) {
          currentSumLong += x2;
          break compute12;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z2 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    if (isCurrentSumLong) {
      return currentSumLong;
    }
    currentCompensation = Math.abs(currentCompensation);
    if (currentCompensation < bestCompensation) {
      if (currentCompensation <= 0d) {
        return currentSum;
      }
      bestCompensation = currentCompensation;
      bestSum = currentSum;
    }

    // now testing summation x3 + x0 + x2 + x1

    currentSum = currentCompensation = 0d;
    currentSumLong = x3;
    isCurrentSumLong = true;
    compute13: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x0) == 0) {
          currentSumLong += x0;
          break compute13;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z0 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute14: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x2) == 0) {
          currentSumLong += x2;
          break compute14;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z2 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute15: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x1) == 0) {
          currentSumLong += x1;
          break compute15;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z1 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    if (isCurrentSumLong) {
      return currentSumLong;
    }
    currentCompensation = Math.abs(currentCompensation);
    if (currentCompensation < bestCompensation) {
      if (currentCompensation <= 0d) {
        return currentSum;
      }
      bestCompensation = currentCompensation;
      bestSum = currentSum;
    }

    // now testing summation x0 + x3 + x2 + x1

    currentSum = currentCompensation = 0d;
    currentSumLong = x0;
    isCurrentSumLong = true;
    compute16: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x3) == 0) {
          currentSumLong += x3;
          break compute16;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z3 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute17: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x2) == 0) {
          currentSumLong += x2;
          break compute17;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z2 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute18: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x1) == 0) {
          currentSumLong += x1;
          break compute18;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z1 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    if (isCurrentSumLong) {
      return currentSumLong;
    }
    currentCompensation = Math.abs(currentCompensation);
    if (currentCompensation < bestCompensation) {
      if (currentCompensation <= 0d) {
        return currentSum;
      }
      bestCompensation = currentCompensation;
      bestSum = currentSum;
    }

    // now testing summation x0 + x2 + x3 + x1

    currentSum = currentCompensation = 0d;
    currentSumLong = x0;
    isCurrentSumLong = true;
    compute19: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x2) == 0) {
          currentSumLong += x2;
          break compute19;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z2 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute20: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x3) == 0) {
          currentSumLong += x3;
          break compute20;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z3 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute21: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x1) == 0) {
          currentSumLong += x1;
          break compute21;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z1 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    if (isCurrentSumLong) {
      return currentSumLong;
    }
    currentCompensation = Math.abs(currentCompensation);
    if (currentCompensation < bestCompensation) {
      if (currentCompensation <= 0d) {
        return currentSum;
      }
      bestCompensation = currentCompensation;
      bestSum = currentSum;
    }

    // now testing summation x0 + x2 + x1 + x3

    currentSum = currentCompensation = 0d;
    currentSumLong = x0;
    isCurrentSumLong = true;
    compute22: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x2) == 0) {
          currentSumLong += x2;
          break compute22;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z2 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute23: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x1) == 0) {
          currentSumLong += x1;
          break compute23;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z1 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute24: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x3) == 0) {
          currentSumLong += x3;
          break compute24;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z3 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    if (isCurrentSumLong) {
      return currentSumLong;
    }
    currentCompensation = Math.abs(currentCompensation);
    if (currentCompensation < bestCompensation) {
      if (currentCompensation <= 0d) {
        return currentSum;
      }
      bestCompensation = currentCompensation;
      bestSum = currentSum;
    }

    // now testing summation x2 + x0 + x1 + x3

    currentSum = currentCompensation = 0d;
    currentSumLong = x2;
    isCurrentSumLong = true;
    compute25: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x0) == 0) {
          currentSumLong += x0;
          break compute25;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z0 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute26: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x1) == 0) {
          currentSumLong += x1;
          break compute26;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z1 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute27: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x3) == 0) {
          currentSumLong += x3;
          break compute27;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z3 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    if (isCurrentSumLong) {
      return currentSumLong;
    }
    currentCompensation = Math.abs(currentCompensation);
    if (currentCompensation < bestCompensation) {
      if (currentCompensation <= 0d) {
        return currentSum;
      }
      bestCompensation = currentCompensation;
      bestSum = currentSum;
    }

    // now testing summation x2 + x0 + x3 + x1

    currentSum = currentCompensation = 0d;
    currentSumLong = x2;
    isCurrentSumLong = true;
    compute28: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x0) == 0) {
          currentSumLong += x0;
          break compute28;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z0 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute29: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x3) == 0) {
          currentSumLong += x3;
          break compute29;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z3 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute30: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x1) == 0) {
          currentSumLong += x1;
          break compute30;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z1 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    if (isCurrentSumLong) {
      return currentSumLong;
    }
    currentCompensation = Math.abs(currentCompensation);
    if (currentCompensation < bestCompensation) {
      if (currentCompensation <= 0d) {
        return currentSum;
      }
      bestCompensation = currentCompensation;
      bestSum = currentSum;
    }

    // now testing summation x2 + x3 + x0 + x1

    currentSum = currentCompensation = 0d;
    currentSumLong = x2;
    isCurrentSumLong = true;
    compute31: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x3) == 0) {
          currentSumLong += x3;
          break compute31;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z3 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute32: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x0) == 0) {
          currentSumLong += x0;
          break compute32;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z0 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute33: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x1) == 0) {
          currentSumLong += x1;
          break compute33;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z1 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    if (isCurrentSumLong) {
      return currentSumLong;
    }
    currentCompensation = Math.abs(currentCompensation);
    if (currentCompensation < bestCompensation) {
      if (currentCompensation <= 0d) {
        return currentSum;
      }
      bestCompensation = currentCompensation;
      bestSum = currentSum;
    }

    // now testing summation x3 + x2 + x0 + x1

    currentSum = currentCompensation = 0d;
    currentSumLong = x3;
    isCurrentSumLong = true;
    compute34: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x2) == 0) {
          currentSumLong += x2;
          break compute34;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z2 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute35: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x0) == 0) {
          currentSumLong += x0;
          break compute35;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z0 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute36: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x1) == 0) {
          currentSumLong += x1;
          break compute36;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z1 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    if (isCurrentSumLong) {
      return currentSumLong;
    }
    currentCompensation = Math.abs(currentCompensation);
    if (currentCompensation < bestCompensation) {
      if (currentCompensation <= 0d) {
        return currentSum;
      }
      bestCompensation = currentCompensation;
      bestSum = currentSum;
    }

    // now testing summation x3 + x2 + x1 + x0

    currentSum = currentCompensation = 0d;
    currentSumLong = x3;
    isCurrentSumLong = true;
    compute37: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x2) == 0) {
          currentSumLong += x2;
          break compute37;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z2 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute38: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x1) == 0) {
          currentSumLong += x1;
          break compute38;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z1 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute39: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x0) == 0) {
          currentSumLong += x0;
          break compute39;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z0 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    if (isCurrentSumLong) {
      return currentSumLong;
    }
    currentCompensation = Math.abs(currentCompensation);
    if (currentCompensation < bestCompensation) {
      if (currentCompensation <= 0d) {
        return currentSum;
      }
      bestCompensation = currentCompensation;
      bestSum = currentSum;
    }

    // now testing summation x2 + x3 + x1 + x0

    currentSum = currentCompensation = 0d;
    currentSumLong = x2;
    isCurrentSumLong = true;
    compute40: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x3) == 0) {
          currentSumLong += x3;
          break compute40;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z3 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute41: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x1) == 0) {
          currentSumLong += x1;
          break compute41;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z1 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute42: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x0) == 0) {
          currentSumLong += x0;
          break compute42;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z0 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    if (isCurrentSumLong) {
      return currentSumLong;
    }
    currentCompensation = Math.abs(currentCompensation);
    if (currentCompensation < bestCompensation) {
      if (currentCompensation <= 0d) {
        return currentSum;
      }
      bestCompensation = currentCompensation;
      bestSum = currentSum;
    }

    // now testing summation x2 + x1 + x3 + x0

    currentSum = currentCompensation = 0d;
    currentSumLong = x2;
    isCurrentSumLong = true;
    compute43: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x1) == 0) {
          currentSumLong += x1;
          break compute43;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z1 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute44: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x3) == 0) {
          currentSumLong += x3;
          break compute44;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z3 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute45: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x0) == 0) {
          currentSumLong += x0;
          break compute45;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z0 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    if (isCurrentSumLong) {
      return currentSumLong;
    }
    currentCompensation = Math.abs(currentCompensation);
    if (currentCompensation < bestCompensation) {
      if (currentCompensation <= 0d) {
        return currentSum;
      }
      bestCompensation = currentCompensation;
      bestSum = currentSum;
    }

    // now testing summation x2 + x1 + x0 + x3

    currentSum = currentCompensation = 0d;
    currentSumLong = x2;
    isCurrentSumLong = true;
    compute46: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x1) == 0) {
          currentSumLong += x1;
          break compute46;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z1 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute47: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x0) == 0) {
          currentSumLong += x0;
          break compute47;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z0 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute48: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x3) == 0) {
          currentSumLong += x3;
          break compute48;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z3 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    if (isCurrentSumLong) {
      return currentSumLong;
    }
    currentCompensation = Math.abs(currentCompensation);
    if (currentCompensation < bestCompensation) {
      if (currentCompensation <= 0d) {
        return currentSum;
      }
      bestCompensation = currentCompensation;
      bestSum = currentSum;
    }

    // now testing summation x1 + x2 + x0 + x3

    currentSum = currentCompensation = 0d;
    currentSumLong = x1;
    isCurrentSumLong = true;
    compute49: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x2) == 0) {
          currentSumLong += x2;
          break compute49;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z2 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute50: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x0) == 0) {
          currentSumLong += x0;
          break compute50;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z0 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute51: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x3) == 0) {
          currentSumLong += x3;
          break compute51;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z3 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    if (isCurrentSumLong) {
      return currentSumLong;
    }
    currentCompensation = Math.abs(currentCompensation);
    if (currentCompensation < bestCompensation) {
      if (currentCompensation <= 0d) {
        return currentSum;
      }
      bestCompensation = currentCompensation;
      bestSum = currentSum;
    }

    // now testing summation x1 + x2 + x3 + x0

    currentSum = currentCompensation = 0d;
    currentSumLong = x1;
    isCurrentSumLong = true;
    compute52: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x2) == 0) {
          currentSumLong += x2;
          break compute52;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z2 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute53: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x3) == 0) {
          currentSumLong += x3;
          break compute53;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z3 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute54: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x0) == 0) {
          currentSumLong += x0;
          break compute54;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z0 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    if (isCurrentSumLong) {
      return currentSumLong;
    }
    currentCompensation = Math.abs(currentCompensation);
    if (currentCompensation < bestCompensation) {
      if (currentCompensation <= 0d) {
        return currentSum;
      }
      bestCompensation = currentCompensation;
      bestSum = currentSum;
    }

    // now testing summation x1 + x3 + x2 + x0

    currentSum = currentCompensation = 0d;
    currentSumLong = x1;
    isCurrentSumLong = true;
    compute55: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x3) == 0) {
          currentSumLong += x3;
          break compute55;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z3 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute56: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x2) == 0) {
          currentSumLong += x2;
          break compute56;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z2 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute57: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x0) == 0) {
          currentSumLong += x0;
          break compute57;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z0 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    if (isCurrentSumLong) {
      return currentSumLong;
    }
    currentCompensation = Math.abs(currentCompensation);
    if (currentCompensation < bestCompensation) {
      if (currentCompensation <= 0d) {
        return currentSum;
      }
      bestCompensation = currentCompensation;
      bestSum = currentSum;
    }

    // now testing summation x3 + x1 + x2 + x0

    currentSum = currentCompensation = 0d;
    currentSumLong = x3;
    isCurrentSumLong = true;
    compute58: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x1) == 0) {
          currentSumLong += x1;
          break compute58;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z1 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute59: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x2) == 0) {
          currentSumLong += x2;
          break compute59;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z2 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute60: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x0) == 0) {
          currentSumLong += x0;
          break compute60;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z0 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    if (isCurrentSumLong) {
      return currentSumLong;
    }
    currentCompensation = Math.abs(currentCompensation);
    if (currentCompensation < bestCompensation) {
      if (currentCompensation <= 0d) {
        return currentSum;
      }
      bestCompensation = currentCompensation;
      bestSum = currentSum;
    }

    // now testing summation x3 + x1 + x0 + x2

    currentSum = currentCompensation = 0d;
    currentSumLong = x3;
    isCurrentSumLong = true;
    compute61: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x1) == 0) {
          currentSumLong += x1;
          break compute61;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z1 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute62: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x0) == 0) {
          currentSumLong += x0;
          break compute62;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z0 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute63: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x2) == 0) {
          currentSumLong += x2;
          break compute63;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z2 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    if (isCurrentSumLong) {
      return currentSumLong;
    }
    currentCompensation = Math.abs(currentCompensation);
    if (currentCompensation < bestCompensation) {
      if (currentCompensation <= 0d) {
        return currentSum;
      }
      bestCompensation = currentCompensation;
      bestSum = currentSum;
    }

    // now testing summation x1 + x3 + x0 + x2

    currentSum = currentCompensation = 0d;
    currentSumLong = x1;
    isCurrentSumLong = true;
    compute64: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x3) == 0) {
          currentSumLong += x3;
          break compute64;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z3 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute65: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x0) == 0) {
          currentSumLong += x0;
          break compute65;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z0 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute66: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x2) == 0) {
          currentSumLong += x2;
          break compute66;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z2 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    if (isCurrentSumLong) {
      return currentSumLong;
    }
    currentCompensation = Math.abs(currentCompensation);
    if (currentCompensation < bestCompensation) {
      if (currentCompensation <= 0d) {
        return currentSum;
      }
      bestCompensation = currentCompensation;
      bestSum = currentSum;
    }

    // now testing summation x1 + x0 + x3 + x2

    currentSum = currentCompensation = 0d;
    currentSumLong = x1;
    isCurrentSumLong = true;
    compute67: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x0) == 0) {
          currentSumLong += x0;
          break compute67;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z0 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute68: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x3) == 0) {
          currentSumLong += x3;
          break compute68;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z3 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute69: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x2) == 0) {
          currentSumLong += x2;
          break compute69;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z2 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    if (isCurrentSumLong) {
      return currentSumLong;
    }
    currentCompensation = Math.abs(currentCompensation);
    if (currentCompensation < bestCompensation) {
      if (currentCompensation <= 0d) {
        return currentSum;
      }
      bestCompensation = currentCompensation;
      bestSum = currentSum;
    }

    // now testing summation x1 + x0 + x2 + x3

    currentSum = currentCompensation = 0d;
    currentSumLong = x1;
    isCurrentSumLong = true;
    compute70: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x0) == 0) {
          currentSumLong += x0;
          break compute70;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z0 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute71: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x2) == 0) {
          currentSumLong += x2;
          break compute71;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z2 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    compute72: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x3) == 0) {
          currentSumLong += x3;
          break compute72;
        }
        currentSum = currentSumLong;
        currentCompensation = (((long) currentSum) - currentSumLong);
        isCurrentSumLong = false;
      }
      y = z3 - currentCompensation;
      t = currentSum + y;
      currentCompensation = ((t - currentSum) - y);
      currentSum = t;
      if ((currentCompensation == 0d)
          && (NumericalTypes.isLong(currentSum))) {
        currentSumLong = ((long) currentSum);
        isCurrentSumLong = true;
      }
    }
    if (isCurrentSumLong) {
      return currentSumLong;
    }
    currentCompensation = Math.abs(currentCompensation);
    if (currentCompensation < bestCompensation) {
      if (currentCompensation <= 0d) {
        return currentSum;
      }
      bestCompensation = currentCompensation;
      bestSum = currentSum;
    }
    return bestSum;
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0, final double x1,
      final double x2, final double x3) {
    boolean isCurrentSumLong, pInf, nInf, onlyOverflow, isLong0, isLong1,
        isLong2, isLong3;
    long currentSumLong, l0, l1, l2, l3;
    double t, y, currentSum, bestSum, currentCompensation,
        bestCompensation;
    pInf = nInf = false;
    checkIsNotLong1: {
      if (x0 >= Double.POSITIVE_INFINITY) {
        pInf = true;
      } else {
        if (x0 <= Double.NEGATIVE_INFINITY) {
          nInf = true;
        } else {
          if (x0 != x0) {
            return Double.NaN;
          }
          if (NumericalTypes.isLong(x0)) {
            isLong0 = true;
            l0 = ((long) x0);
            break checkIsNotLong1;
          }
        }
      }
      isLong0 = false;
      l0 = 0L;
    }
    checkIsNotLong2: {
      if (x1 >= Double.POSITIVE_INFINITY) {
        if (nInf) {
          return Double.NaN;
        }
        pInf = true;
      } else {
        if (x1 <= Double.NEGATIVE_INFINITY) {
          if (pInf) {
            return Double.NaN;
          }
          nInf = true;
        } else {
          if (x1 != x1) {
            return Double.NaN;
          }
          if (NumericalTypes.isLong(x1)) {
            isLong1 = true;
            l1 = ((long) x1);
            break checkIsNotLong2;
          }
        }
      }
      isLong1 = false;
      l1 = 0L;
    }
    checkIsNotLong3: {
      if (x2 >= Double.POSITIVE_INFINITY) {
        if (nInf) {
          return Double.NaN;
        }
        pInf = true;
      } else {
        if (x2 <= Double.NEGATIVE_INFINITY) {
          if (pInf) {
            return Double.NaN;
          }
          nInf = true;
        } else {
          if (x2 != x2) {
            return Double.NaN;
          }
          if (NumericalTypes.isLong(x2)) {
            isLong2 = true;
            l2 = ((long) x2);
            break checkIsNotLong3;
          }
        }
      }
      isLong2 = false;
      l2 = 0L;
    }
    checkIsNotLong4: {
      if (x3 >= Double.POSITIVE_INFINITY) {
        if (nInf) {
          return Double.NaN;
        }
        pInf = true;
      } else {
        if (x3 <= Double.NEGATIVE_INFINITY) {
          if (pInf) {
            return Double.NaN;
          }
          nInf = true;
        } else {
          if (x3 != x3) {
            return Double.NaN;
          }
          if (NumericalTypes.isLong(x3)) {
            isLong3 = true;
            l3 = ((long) x3);
            break checkIsNotLong4;
          }
        }
      }
      isLong3 = false;
      l3 = 0L;
    }
    if (pInf) {
      return Double.POSITIVE_INFINITY;
    }
    if (nInf) {
      return Double.NEGATIVE_INFINITY;
    }
    onlyOverflow = true;

    // now testing summation x0 + x1 + x2 + x3

    summation5: {
      if (isLong0) {
        currentSumLong = l0;
        isCurrentSumLong = true;
        bestSum = bestCompensation = 0d;
      } else {
        bestSum = x0;
        bestCompensation = 0d;
        isCurrentSumLong = false;
        currentSumLong = 0L;
      }
      compute6: {
        if (isCurrentSumLong) {
          if (isLong1 && (SaturatingAdd.getOverflowType(currentSumLong,
              l1) == 0)) {
            currentSumLong += l1;
            break compute6;
          }
          bestSum = currentSumLong;
          bestCompensation = (((long) bestSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x1 - bestCompensation;
        t = bestSum + y;
        bestCompensation = ((t - bestSum) - y);
        bestSum = t;
        if (bestSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation5;
        }
        if (bestSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation5;
        }
        if ((bestCompensation == 0d) && (NumericalTypes.isLong(bestSum))) {
          currentSumLong = ((long) bestSum);
          isCurrentSumLong = true;
        }
      }
      compute7: {
        if (isCurrentSumLong) {
          if (isLong2 && (SaturatingAdd.getOverflowType(currentSumLong,
              l2) == 0)) {
            currentSumLong += l2;
            break compute7;
          }
          bestSum = currentSumLong;
          bestCompensation = (((long) bestSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x2 - bestCompensation;
        t = bestSum + y;
        bestCompensation = ((t - bestSum) - y);
        bestSum = t;
        if (bestSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation5;
        }
        if (bestSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation5;
        }
        if ((bestCompensation == 0d) && (NumericalTypes.isLong(bestSum))) {
          currentSumLong = ((long) bestSum);
          isCurrentSumLong = true;
        }
      }
      compute8: {
        if (isCurrentSumLong) {
          if (isLong3 && (SaturatingAdd.getOverflowType(currentSumLong,
              l3) == 0)) {
            currentSumLong += l3;
            break compute8;
          }
          bestSum = currentSumLong;
          bestCompensation = (((long) bestSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x3 - bestCompensation;
        t = bestSum + y;
        bestCompensation = ((t - bestSum) - y);
        bestSum = t;
        if (bestSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation5;
        }
        if (bestSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation5;
        }
        if ((bestCompensation == 0d) && (NumericalTypes.isLong(bestSum))) {
          currentSumLong = ((long) bestSum);
          isCurrentSumLong = true;
        }
      }
      if (isCurrentSumLong) {
        return currentSumLong;
      }
      onlyOverflow = false;
      if (bestCompensation < 0d) {
        bestCompensation = (-bestCompensation);
      } else {
        if (bestCompensation <= 0d) {
          return bestSum;
        }
      }
    }

    // now testing summation x0 + x1 + x3 + x2

    summation9: {
      if (isLong0) {
        currentSumLong = l0;
        isCurrentSumLong = true;
        currentSum = currentCompensation = 0d;
      } else {
        currentSum = x0;
        currentCompensation = 0d;
        isCurrentSumLong = false;
        currentSumLong = 0L;
      }
      compute10: {
        if (isCurrentSumLong) {
          if (isLong1 && (SaturatingAdd.getOverflowType(currentSumLong,
              l1) == 0)) {
            currentSumLong += l1;
            break compute10;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x1 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation9;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation9;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute11: {
        if (isCurrentSumLong) {
          if (isLong3 && (SaturatingAdd.getOverflowType(currentSumLong,
              l3) == 0)) {
            currentSumLong += l3;
            break compute11;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x3 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation9;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation9;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute12: {
        if (isCurrentSumLong) {
          if (isLong2 && (SaturatingAdd.getOverflowType(currentSumLong,
              l2) == 0)) {
            currentSumLong += l2;
            break compute12;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x2 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation9;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation9;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      if (isCurrentSumLong) {
        return currentSumLong;
      }
      onlyOverflow = false;
      currentCompensation = Math.abs(currentCompensation);
      if (onlyOverflow || (currentCompensation < bestCompensation)) {
        if (currentCompensation <= 0d) {
          return currentSum;
        }
        bestCompensation = currentCompensation;
        bestSum = currentSum;
      }
    }

    // now testing summation x0 + x3 + x1 + x2

    summation13: {
      if (isLong0) {
        currentSumLong = l0;
        isCurrentSumLong = true;
        currentSum = currentCompensation = 0d;
      } else {
        currentSum = x0;
        currentCompensation = 0d;
        isCurrentSumLong = false;
        currentSumLong = 0L;
      }
      compute14: {
        if (isCurrentSumLong) {
          if (isLong3 && (SaturatingAdd.getOverflowType(currentSumLong,
              l3) == 0)) {
            currentSumLong += l3;
            break compute14;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x3 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation13;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation13;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute15: {
        if (isCurrentSumLong) {
          if (isLong1 && (SaturatingAdd.getOverflowType(currentSumLong,
              l1) == 0)) {
            currentSumLong += l1;
            break compute15;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x1 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation13;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation13;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute16: {
        if (isCurrentSumLong) {
          if (isLong2 && (SaturatingAdd.getOverflowType(currentSumLong,
              l2) == 0)) {
            currentSumLong += l2;
            break compute16;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x2 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation13;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation13;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      if (isCurrentSumLong) {
        return currentSumLong;
      }
      onlyOverflow = false;
      currentCompensation = Math.abs(currentCompensation);
      if (onlyOverflow || (currentCompensation < bestCompensation)) {
        if (currentCompensation <= 0d) {
          return currentSum;
        }
        bestCompensation = currentCompensation;
        bestSum = currentSum;
      }
    }

    // now testing summation x3 + x0 + x1 + x2

    summation17: {
      if (isLong3) {
        currentSumLong = l3;
        isCurrentSumLong = true;
        currentSum = currentCompensation = 0d;
      } else {
        currentSum = x3;
        currentCompensation = 0d;
        isCurrentSumLong = false;
        currentSumLong = 0L;
      }
      compute18: {
        if (isCurrentSumLong) {
          if (isLong0 && (SaturatingAdd.getOverflowType(currentSumLong,
              l0) == 0)) {
            currentSumLong += l0;
            break compute18;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x0 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation17;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation17;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute19: {
        if (isCurrentSumLong) {
          if (isLong1 && (SaturatingAdd.getOverflowType(currentSumLong,
              l1) == 0)) {
            currentSumLong += l1;
            break compute19;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x1 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation17;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation17;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute20: {
        if (isCurrentSumLong) {
          if (isLong2 && (SaturatingAdd.getOverflowType(currentSumLong,
              l2) == 0)) {
            currentSumLong += l2;
            break compute20;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x2 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation17;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation17;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      if (isCurrentSumLong) {
        return currentSumLong;
      }
      onlyOverflow = false;
      currentCompensation = Math.abs(currentCompensation);
      if (onlyOverflow || (currentCompensation < bestCompensation)) {
        if (currentCompensation <= 0d) {
          return currentSum;
        }
        bestCompensation = currentCompensation;
        bestSum = currentSum;
      }
    }

    // now testing summation x3 + x0 + x2 + x1

    summation21: {
      if (isLong3) {
        currentSumLong = l3;
        isCurrentSumLong = true;
        currentSum = currentCompensation = 0d;
      } else {
        currentSum = x3;
        currentCompensation = 0d;
        isCurrentSumLong = false;
        currentSumLong = 0L;
      }
      compute22: {
        if (isCurrentSumLong) {
          if (isLong0 && (SaturatingAdd.getOverflowType(currentSumLong,
              l0) == 0)) {
            currentSumLong += l0;
            break compute22;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x0 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation21;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation21;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute23: {
        if (isCurrentSumLong) {
          if (isLong2 && (SaturatingAdd.getOverflowType(currentSumLong,
              l2) == 0)) {
            currentSumLong += l2;
            break compute23;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x2 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation21;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation21;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute24: {
        if (isCurrentSumLong) {
          if (isLong1 && (SaturatingAdd.getOverflowType(currentSumLong,
              l1) == 0)) {
            currentSumLong += l1;
            break compute24;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x1 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation21;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation21;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      if (isCurrentSumLong) {
        return currentSumLong;
      }
      onlyOverflow = false;
      currentCompensation = Math.abs(currentCompensation);
      if (onlyOverflow || (currentCompensation < bestCompensation)) {
        if (currentCompensation <= 0d) {
          return currentSum;
        }
        bestCompensation = currentCompensation;
        bestSum = currentSum;
      }
    }

    // now testing summation x0 + x3 + x2 + x1

    summation25: {
      if (isLong0) {
        currentSumLong = l0;
        isCurrentSumLong = true;
        currentSum = currentCompensation = 0d;
      } else {
        currentSum = x0;
        currentCompensation = 0d;
        isCurrentSumLong = false;
        currentSumLong = 0L;
      }
      compute26: {
        if (isCurrentSumLong) {
          if (isLong3 && (SaturatingAdd.getOverflowType(currentSumLong,
              l3) == 0)) {
            currentSumLong += l3;
            break compute26;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x3 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation25;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation25;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute27: {
        if (isCurrentSumLong) {
          if (isLong2 && (SaturatingAdd.getOverflowType(currentSumLong,
              l2) == 0)) {
            currentSumLong += l2;
            break compute27;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x2 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation25;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation25;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute28: {
        if (isCurrentSumLong) {
          if (isLong1 && (SaturatingAdd.getOverflowType(currentSumLong,
              l1) == 0)) {
            currentSumLong += l1;
            break compute28;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x1 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation25;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation25;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      if (isCurrentSumLong) {
        return currentSumLong;
      }
      onlyOverflow = false;
      currentCompensation = Math.abs(currentCompensation);
      if (onlyOverflow || (currentCompensation < bestCompensation)) {
        if (currentCompensation <= 0d) {
          return currentSum;
        }
        bestCompensation = currentCompensation;
        bestSum = currentSum;
      }
    }

    // now testing summation x0 + x2 + x3 + x1

    summation29: {
      if (isLong0) {
        currentSumLong = l0;
        isCurrentSumLong = true;
        currentSum = currentCompensation = 0d;
      } else {
        currentSum = x0;
        currentCompensation = 0d;
        isCurrentSumLong = false;
        currentSumLong = 0L;
      }
      compute30: {
        if (isCurrentSumLong) {
          if (isLong2 && (SaturatingAdd.getOverflowType(currentSumLong,
              l2) == 0)) {
            currentSumLong += l2;
            break compute30;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x2 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation29;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation29;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute31: {
        if (isCurrentSumLong) {
          if (isLong3 && (SaturatingAdd.getOverflowType(currentSumLong,
              l3) == 0)) {
            currentSumLong += l3;
            break compute31;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x3 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation29;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation29;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute32: {
        if (isCurrentSumLong) {
          if (isLong1 && (SaturatingAdd.getOverflowType(currentSumLong,
              l1) == 0)) {
            currentSumLong += l1;
            break compute32;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x1 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation29;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation29;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      if (isCurrentSumLong) {
        return currentSumLong;
      }
      onlyOverflow = false;
      currentCompensation = Math.abs(currentCompensation);
      if (onlyOverflow || (currentCompensation < bestCompensation)) {
        if (currentCompensation <= 0d) {
          return currentSum;
        }
        bestCompensation = currentCompensation;
        bestSum = currentSum;
      }
    }

    // now testing summation x0 + x2 + x1 + x3

    summation33: {
      if (isLong0) {
        currentSumLong = l0;
        isCurrentSumLong = true;
        currentSum = currentCompensation = 0d;
      } else {
        currentSum = x0;
        currentCompensation = 0d;
        isCurrentSumLong = false;
        currentSumLong = 0L;
      }
      compute34: {
        if (isCurrentSumLong) {
          if (isLong2 && (SaturatingAdd.getOverflowType(currentSumLong,
              l2) == 0)) {
            currentSumLong += l2;
            break compute34;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x2 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation33;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation33;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute35: {
        if (isCurrentSumLong) {
          if (isLong1 && (SaturatingAdd.getOverflowType(currentSumLong,
              l1) == 0)) {
            currentSumLong += l1;
            break compute35;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x1 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation33;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation33;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute36: {
        if (isCurrentSumLong) {
          if (isLong3 && (SaturatingAdd.getOverflowType(currentSumLong,
              l3) == 0)) {
            currentSumLong += l3;
            break compute36;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x3 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation33;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation33;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      if (isCurrentSumLong) {
        return currentSumLong;
      }
      onlyOverflow = false;
      currentCompensation = Math.abs(currentCompensation);
      if (onlyOverflow || (currentCompensation < bestCompensation)) {
        if (currentCompensation <= 0d) {
          return currentSum;
        }
        bestCompensation = currentCompensation;
        bestSum = currentSum;
      }
    }

    // now testing summation x2 + x0 + x1 + x3

    summation37: {
      if (isLong2) {
        currentSumLong = l2;
        isCurrentSumLong = true;
        currentSum = currentCompensation = 0d;
      } else {
        currentSum = x2;
        currentCompensation = 0d;
        isCurrentSumLong = false;
        currentSumLong = 0L;
      }
      compute38: {
        if (isCurrentSumLong) {
          if (isLong0 && (SaturatingAdd.getOverflowType(currentSumLong,
              l0) == 0)) {
            currentSumLong += l0;
            break compute38;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x0 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation37;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation37;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute39: {
        if (isCurrentSumLong) {
          if (isLong1 && (SaturatingAdd.getOverflowType(currentSumLong,
              l1) == 0)) {
            currentSumLong += l1;
            break compute39;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x1 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation37;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation37;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute40: {
        if (isCurrentSumLong) {
          if (isLong3 && (SaturatingAdd.getOverflowType(currentSumLong,
              l3) == 0)) {
            currentSumLong += l3;
            break compute40;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x3 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation37;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation37;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      if (isCurrentSumLong) {
        return currentSumLong;
      }
      onlyOverflow = false;
      currentCompensation = Math.abs(currentCompensation);
      if (onlyOverflow || (currentCompensation < bestCompensation)) {
        if (currentCompensation <= 0d) {
          return currentSum;
        }
        bestCompensation = currentCompensation;
        bestSum = currentSum;
      }
    }

    // now testing summation x2 + x0 + x3 + x1

    summation41: {
      if (isLong2) {
        currentSumLong = l2;
        isCurrentSumLong = true;
        currentSum = currentCompensation = 0d;
      } else {
        currentSum = x2;
        currentCompensation = 0d;
        isCurrentSumLong = false;
        currentSumLong = 0L;
      }
      compute42: {
        if (isCurrentSumLong) {
          if (isLong0 && (SaturatingAdd.getOverflowType(currentSumLong,
              l0) == 0)) {
            currentSumLong += l0;
            break compute42;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x0 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation41;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation41;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute43: {
        if (isCurrentSumLong) {
          if (isLong3 && (SaturatingAdd.getOverflowType(currentSumLong,
              l3) == 0)) {
            currentSumLong += l3;
            break compute43;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x3 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation41;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation41;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute44: {
        if (isCurrentSumLong) {
          if (isLong1 && (SaturatingAdd.getOverflowType(currentSumLong,
              l1) == 0)) {
            currentSumLong += l1;
            break compute44;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x1 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation41;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation41;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      if (isCurrentSumLong) {
        return currentSumLong;
      }
      onlyOverflow = false;
      currentCompensation = Math.abs(currentCompensation);
      if (onlyOverflow || (currentCompensation < bestCompensation)) {
        if (currentCompensation <= 0d) {
          return currentSum;
        }
        bestCompensation = currentCompensation;
        bestSum = currentSum;
      }
    }

    // now testing summation x2 + x3 + x0 + x1

    summation45: {
      if (isLong2) {
        currentSumLong = l2;
        isCurrentSumLong = true;
        currentSum = currentCompensation = 0d;
      } else {
        currentSum = x2;
        currentCompensation = 0d;
        isCurrentSumLong = false;
        currentSumLong = 0L;
      }
      compute46: {
        if (isCurrentSumLong) {
          if (isLong3 && (SaturatingAdd.getOverflowType(currentSumLong,
              l3) == 0)) {
            currentSumLong += l3;
            break compute46;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x3 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation45;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation45;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute47: {
        if (isCurrentSumLong) {
          if (isLong0 && (SaturatingAdd.getOverflowType(currentSumLong,
              l0) == 0)) {
            currentSumLong += l0;
            break compute47;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x0 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation45;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation45;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute48: {
        if (isCurrentSumLong) {
          if (isLong1 && (SaturatingAdd.getOverflowType(currentSumLong,
              l1) == 0)) {
            currentSumLong += l1;
            break compute48;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x1 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation45;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation45;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      if (isCurrentSumLong) {
        return currentSumLong;
      }
      onlyOverflow = false;
      currentCompensation = Math.abs(currentCompensation);
      if (onlyOverflow || (currentCompensation < bestCompensation)) {
        if (currentCompensation <= 0d) {
          return currentSum;
        }
        bestCompensation = currentCompensation;
        bestSum = currentSum;
      }
    }

    // now testing summation x3 + x2 + x0 + x1

    summation49: {
      if (isLong3) {
        currentSumLong = l3;
        isCurrentSumLong = true;
        currentSum = currentCompensation = 0d;
      } else {
        currentSum = x3;
        currentCompensation = 0d;
        isCurrentSumLong = false;
        currentSumLong = 0L;
      }
      compute50: {
        if (isCurrentSumLong) {
          if (isLong2 && (SaturatingAdd.getOverflowType(currentSumLong,
              l2) == 0)) {
            currentSumLong += l2;
            break compute50;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x2 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation49;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation49;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute51: {
        if (isCurrentSumLong) {
          if (isLong0 && (SaturatingAdd.getOverflowType(currentSumLong,
              l0) == 0)) {
            currentSumLong += l0;
            break compute51;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x0 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation49;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation49;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute52: {
        if (isCurrentSumLong) {
          if (isLong1 && (SaturatingAdd.getOverflowType(currentSumLong,
              l1) == 0)) {
            currentSumLong += l1;
            break compute52;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x1 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation49;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation49;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      if (isCurrentSumLong) {
        return currentSumLong;
      }
      onlyOverflow = false;
      currentCompensation = Math.abs(currentCompensation);
      if (onlyOverflow || (currentCompensation < bestCompensation)) {
        if (currentCompensation <= 0d) {
          return currentSum;
        }
        bestCompensation = currentCompensation;
        bestSum = currentSum;
      }
    }

    // now testing summation x3 + x2 + x1 + x0

    summation53: {
      if (isLong3) {
        currentSumLong = l3;
        isCurrentSumLong = true;
        currentSum = currentCompensation = 0d;
      } else {
        currentSum = x3;
        currentCompensation = 0d;
        isCurrentSumLong = false;
        currentSumLong = 0L;
      }
      compute54: {
        if (isCurrentSumLong) {
          if (isLong2 && (SaturatingAdd.getOverflowType(currentSumLong,
              l2) == 0)) {
            currentSumLong += l2;
            break compute54;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x2 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation53;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation53;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute55: {
        if (isCurrentSumLong) {
          if (isLong1 && (SaturatingAdd.getOverflowType(currentSumLong,
              l1) == 0)) {
            currentSumLong += l1;
            break compute55;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x1 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation53;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation53;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute56: {
        if (isCurrentSumLong) {
          if (isLong0 && (SaturatingAdd.getOverflowType(currentSumLong,
              l0) == 0)) {
            currentSumLong += l0;
            break compute56;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x0 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation53;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation53;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      if (isCurrentSumLong) {
        return currentSumLong;
      }
      onlyOverflow = false;
      currentCompensation = Math.abs(currentCompensation);
      if (onlyOverflow || (currentCompensation < bestCompensation)) {
        if (currentCompensation <= 0d) {
          return currentSum;
        }
        bestCompensation = currentCompensation;
        bestSum = currentSum;
      }
    }

    // now testing summation x2 + x3 + x1 + x0

    summation57: {
      if (isLong2) {
        currentSumLong = l2;
        isCurrentSumLong = true;
        currentSum = currentCompensation = 0d;
      } else {
        currentSum = x2;
        currentCompensation = 0d;
        isCurrentSumLong = false;
        currentSumLong = 0L;
      }
      compute58: {
        if (isCurrentSumLong) {
          if (isLong3 && (SaturatingAdd.getOverflowType(currentSumLong,
              l3) == 0)) {
            currentSumLong += l3;
            break compute58;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x3 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation57;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation57;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute59: {
        if (isCurrentSumLong) {
          if (isLong1 && (SaturatingAdd.getOverflowType(currentSumLong,
              l1) == 0)) {
            currentSumLong += l1;
            break compute59;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x1 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation57;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation57;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute60: {
        if (isCurrentSumLong) {
          if (isLong0 && (SaturatingAdd.getOverflowType(currentSumLong,
              l0) == 0)) {
            currentSumLong += l0;
            break compute60;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x0 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation57;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation57;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      if (isCurrentSumLong) {
        return currentSumLong;
      }
      onlyOverflow = false;
      currentCompensation = Math.abs(currentCompensation);
      if (onlyOverflow || (currentCompensation < bestCompensation)) {
        if (currentCompensation <= 0d) {
          return currentSum;
        }
        bestCompensation = currentCompensation;
        bestSum = currentSum;
      }
    }

    // now testing summation x2 + x1 + x3 + x0

    summation61: {
      if (isLong2) {
        currentSumLong = l2;
        isCurrentSumLong = true;
        currentSum = currentCompensation = 0d;
      } else {
        currentSum = x2;
        currentCompensation = 0d;
        isCurrentSumLong = false;
        currentSumLong = 0L;
      }
      compute62: {
        if (isCurrentSumLong) {
          if (isLong1 && (SaturatingAdd.getOverflowType(currentSumLong,
              l1) == 0)) {
            currentSumLong += l1;
            break compute62;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x1 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation61;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation61;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute63: {
        if (isCurrentSumLong) {
          if (isLong3 && (SaturatingAdd.getOverflowType(currentSumLong,
              l3) == 0)) {
            currentSumLong += l3;
            break compute63;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x3 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation61;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation61;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute64: {
        if (isCurrentSumLong) {
          if (isLong0 && (SaturatingAdd.getOverflowType(currentSumLong,
              l0) == 0)) {
            currentSumLong += l0;
            break compute64;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x0 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation61;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation61;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      if (isCurrentSumLong) {
        return currentSumLong;
      }
      onlyOverflow = false;
      currentCompensation = Math.abs(currentCompensation);
      if (onlyOverflow || (currentCompensation < bestCompensation)) {
        if (currentCompensation <= 0d) {
          return currentSum;
        }
        bestCompensation = currentCompensation;
        bestSum = currentSum;
      }
    }

    // now testing summation x2 + x1 + x0 + x3

    summation65: {
      if (isLong2) {
        currentSumLong = l2;
        isCurrentSumLong = true;
        currentSum = currentCompensation = 0d;
      } else {
        currentSum = x2;
        currentCompensation = 0d;
        isCurrentSumLong = false;
        currentSumLong = 0L;
      }
      compute66: {
        if (isCurrentSumLong) {
          if (isLong1 && (SaturatingAdd.getOverflowType(currentSumLong,
              l1) == 0)) {
            currentSumLong += l1;
            break compute66;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x1 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation65;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation65;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute67: {
        if (isCurrentSumLong) {
          if (isLong0 && (SaturatingAdd.getOverflowType(currentSumLong,
              l0) == 0)) {
            currentSumLong += l0;
            break compute67;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x0 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation65;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation65;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute68: {
        if (isCurrentSumLong) {
          if (isLong3 && (SaturatingAdd.getOverflowType(currentSumLong,
              l3) == 0)) {
            currentSumLong += l3;
            break compute68;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x3 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation65;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation65;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      if (isCurrentSumLong) {
        return currentSumLong;
      }
      onlyOverflow = false;
      currentCompensation = Math.abs(currentCompensation);
      if (onlyOverflow || (currentCompensation < bestCompensation)) {
        if (currentCompensation <= 0d) {
          return currentSum;
        }
        bestCompensation = currentCompensation;
        bestSum = currentSum;
      }
    }

    // now testing summation x1 + x2 + x0 + x3

    summation69: {
      if (isLong1) {
        currentSumLong = l1;
        isCurrentSumLong = true;
        currentSum = currentCompensation = 0d;
      } else {
        currentSum = x1;
        currentCompensation = 0d;
        isCurrentSumLong = false;
        currentSumLong = 0L;
      }
      compute70: {
        if (isCurrentSumLong) {
          if (isLong2 && (SaturatingAdd.getOverflowType(currentSumLong,
              l2) == 0)) {
            currentSumLong += l2;
            break compute70;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x2 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation69;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation69;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute71: {
        if (isCurrentSumLong) {
          if (isLong0 && (SaturatingAdd.getOverflowType(currentSumLong,
              l0) == 0)) {
            currentSumLong += l0;
            break compute71;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x0 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation69;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation69;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute72: {
        if (isCurrentSumLong) {
          if (isLong3 && (SaturatingAdd.getOverflowType(currentSumLong,
              l3) == 0)) {
            currentSumLong += l3;
            break compute72;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x3 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation69;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation69;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      if (isCurrentSumLong) {
        return currentSumLong;
      }
      onlyOverflow = false;
      currentCompensation = Math.abs(currentCompensation);
      if (onlyOverflow || (currentCompensation < bestCompensation)) {
        if (currentCompensation <= 0d) {
          return currentSum;
        }
        bestCompensation = currentCompensation;
        bestSum = currentSum;
      }
    }

    // now testing summation x1 + x2 + x3 + x0

    summation73: {
      if (isLong1) {
        currentSumLong = l1;
        isCurrentSumLong = true;
        currentSum = currentCompensation = 0d;
      } else {
        currentSum = x1;
        currentCompensation = 0d;
        isCurrentSumLong = false;
        currentSumLong = 0L;
      }
      compute74: {
        if (isCurrentSumLong) {
          if (isLong2 && (SaturatingAdd.getOverflowType(currentSumLong,
              l2) == 0)) {
            currentSumLong += l2;
            break compute74;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x2 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation73;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation73;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute75: {
        if (isCurrentSumLong) {
          if (isLong3 && (SaturatingAdd.getOverflowType(currentSumLong,
              l3) == 0)) {
            currentSumLong += l3;
            break compute75;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x3 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation73;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation73;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute76: {
        if (isCurrentSumLong) {
          if (isLong0 && (SaturatingAdd.getOverflowType(currentSumLong,
              l0) == 0)) {
            currentSumLong += l0;
            break compute76;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x0 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation73;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation73;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      if (isCurrentSumLong) {
        return currentSumLong;
      }
      onlyOverflow = false;
      currentCompensation = Math.abs(currentCompensation);
      if (onlyOverflow || (currentCompensation < bestCompensation)) {
        if (currentCompensation <= 0d) {
          return currentSum;
        }
        bestCompensation = currentCompensation;
        bestSum = currentSum;
      }
    }

    // now testing summation x1 + x3 + x2 + x0

    summation77: {
      if (isLong1) {
        currentSumLong = l1;
        isCurrentSumLong = true;
        currentSum = currentCompensation = 0d;
      } else {
        currentSum = x1;
        currentCompensation = 0d;
        isCurrentSumLong = false;
        currentSumLong = 0L;
      }
      compute78: {
        if (isCurrentSumLong) {
          if (isLong3 && (SaturatingAdd.getOverflowType(currentSumLong,
              l3) == 0)) {
            currentSumLong += l3;
            break compute78;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x3 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation77;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation77;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute79: {
        if (isCurrentSumLong) {
          if (isLong2 && (SaturatingAdd.getOverflowType(currentSumLong,
              l2) == 0)) {
            currentSumLong += l2;
            break compute79;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x2 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation77;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation77;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute80: {
        if (isCurrentSumLong) {
          if (isLong0 && (SaturatingAdd.getOverflowType(currentSumLong,
              l0) == 0)) {
            currentSumLong += l0;
            break compute80;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x0 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation77;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation77;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      if (isCurrentSumLong) {
        return currentSumLong;
      }
      onlyOverflow = false;
      currentCompensation = Math.abs(currentCompensation);
      if (onlyOverflow || (currentCompensation < bestCompensation)) {
        if (currentCompensation <= 0d) {
          return currentSum;
        }
        bestCompensation = currentCompensation;
        bestSum = currentSum;
      }
    }

    // now testing summation x3 + x1 + x2 + x0

    summation81: {
      if (isLong3) {
        currentSumLong = l3;
        isCurrentSumLong = true;
        currentSum = currentCompensation = 0d;
      } else {
        currentSum = x3;
        currentCompensation = 0d;
        isCurrentSumLong = false;
        currentSumLong = 0L;
      }
      compute82: {
        if (isCurrentSumLong) {
          if (isLong1 && (SaturatingAdd.getOverflowType(currentSumLong,
              l1) == 0)) {
            currentSumLong += l1;
            break compute82;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x1 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation81;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation81;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute83: {
        if (isCurrentSumLong) {
          if (isLong2 && (SaturatingAdd.getOverflowType(currentSumLong,
              l2) == 0)) {
            currentSumLong += l2;
            break compute83;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x2 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation81;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation81;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute84: {
        if (isCurrentSumLong) {
          if (isLong0 && (SaturatingAdd.getOverflowType(currentSumLong,
              l0) == 0)) {
            currentSumLong += l0;
            break compute84;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x0 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation81;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation81;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      if (isCurrentSumLong) {
        return currentSumLong;
      }
      onlyOverflow = false;
      currentCompensation = Math.abs(currentCompensation);
      if (onlyOverflow || (currentCompensation < bestCompensation)) {
        if (currentCompensation <= 0d) {
          return currentSum;
        }
        bestCompensation = currentCompensation;
        bestSum = currentSum;
      }
    }

    // now testing summation x3 + x1 + x0 + x2

    summation85: {
      if (isLong3) {
        currentSumLong = l3;
        isCurrentSumLong = true;
        currentSum = currentCompensation = 0d;
      } else {
        currentSum = x3;
        currentCompensation = 0d;
        isCurrentSumLong = false;
        currentSumLong = 0L;
      }
      compute86: {
        if (isCurrentSumLong) {
          if (isLong1 && (SaturatingAdd.getOverflowType(currentSumLong,
              l1) == 0)) {
            currentSumLong += l1;
            break compute86;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x1 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation85;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation85;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute87: {
        if (isCurrentSumLong) {
          if (isLong0 && (SaturatingAdd.getOverflowType(currentSumLong,
              l0) == 0)) {
            currentSumLong += l0;
            break compute87;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x0 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation85;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation85;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute88: {
        if (isCurrentSumLong) {
          if (isLong2 && (SaturatingAdd.getOverflowType(currentSumLong,
              l2) == 0)) {
            currentSumLong += l2;
            break compute88;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x2 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation85;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation85;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      if (isCurrentSumLong) {
        return currentSumLong;
      }
      onlyOverflow = false;
      currentCompensation = Math.abs(currentCompensation);
      if (onlyOverflow || (currentCompensation < bestCompensation)) {
        if (currentCompensation <= 0d) {
          return currentSum;
        }
        bestCompensation = currentCompensation;
        bestSum = currentSum;
      }
    }

    // now testing summation x1 + x3 + x0 + x2

    summation89: {
      if (isLong1) {
        currentSumLong = l1;
        isCurrentSumLong = true;
        currentSum = currentCompensation = 0d;
      } else {
        currentSum = x1;
        currentCompensation = 0d;
        isCurrentSumLong = false;
        currentSumLong = 0L;
      }
      compute90: {
        if (isCurrentSumLong) {
          if (isLong3 && (SaturatingAdd.getOverflowType(currentSumLong,
              l3) == 0)) {
            currentSumLong += l3;
            break compute90;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x3 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation89;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation89;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute91: {
        if (isCurrentSumLong) {
          if (isLong0 && (SaturatingAdd.getOverflowType(currentSumLong,
              l0) == 0)) {
            currentSumLong += l0;
            break compute91;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x0 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation89;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation89;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute92: {
        if (isCurrentSumLong) {
          if (isLong2 && (SaturatingAdd.getOverflowType(currentSumLong,
              l2) == 0)) {
            currentSumLong += l2;
            break compute92;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x2 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation89;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation89;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      if (isCurrentSumLong) {
        return currentSumLong;
      }
      onlyOverflow = false;
      currentCompensation = Math.abs(currentCompensation);
      if (onlyOverflow || (currentCompensation < bestCompensation)) {
        if (currentCompensation <= 0d) {
          return currentSum;
        }
        bestCompensation = currentCompensation;
        bestSum = currentSum;
      }
    }

    // now testing summation x1 + x0 + x3 + x2

    summation93: {
      if (isLong1) {
        currentSumLong = l1;
        isCurrentSumLong = true;
        currentSum = currentCompensation = 0d;
      } else {
        currentSum = x1;
        currentCompensation = 0d;
        isCurrentSumLong = false;
        currentSumLong = 0L;
      }
      compute94: {
        if (isCurrentSumLong) {
          if (isLong0 && (SaturatingAdd.getOverflowType(currentSumLong,
              l0) == 0)) {
            currentSumLong += l0;
            break compute94;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x0 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation93;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation93;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute95: {
        if (isCurrentSumLong) {
          if (isLong3 && (SaturatingAdd.getOverflowType(currentSumLong,
              l3) == 0)) {
            currentSumLong += l3;
            break compute95;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x3 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation93;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation93;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute96: {
        if (isCurrentSumLong) {
          if (isLong2 && (SaturatingAdd.getOverflowType(currentSumLong,
              l2) == 0)) {
            currentSumLong += l2;
            break compute96;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x2 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation93;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation93;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      if (isCurrentSumLong) {
        return currentSumLong;
      }
      onlyOverflow = false;
      currentCompensation = Math.abs(currentCompensation);
      if (onlyOverflow || (currentCompensation < bestCompensation)) {
        if (currentCompensation <= 0d) {
          return currentSum;
        }
        bestCompensation = currentCompensation;
        bestSum = currentSum;
      }
    }

    // now testing summation x1 + x0 + x2 + x3

    summation97: {
      if (isLong1) {
        currentSumLong = l1;
        isCurrentSumLong = true;
        currentSum = currentCompensation = 0d;
      } else {
        currentSum = x1;
        currentCompensation = 0d;
        isCurrentSumLong = false;
        currentSumLong = 0L;
      }
      compute98: {
        if (isCurrentSumLong) {
          if (isLong0 && (SaturatingAdd.getOverflowType(currentSumLong,
              l0) == 0)) {
            currentSumLong += l0;
            break compute98;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x0 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation97;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation97;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute99: {
        if (isCurrentSumLong) {
          if (isLong2 && (SaturatingAdd.getOverflowType(currentSumLong,
              l2) == 0)) {
            currentSumLong += l2;
            break compute99;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x2 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation97;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation97;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute100: {
        if (isCurrentSumLong) {
          if (isLong3 && (SaturatingAdd.getOverflowType(currentSumLong,
              l3) == 0)) {
            currentSumLong += l3;
            break compute100;
          }
          currentSum = currentSumLong;
          currentCompensation = (((long) currentSum) - currentSumLong);
          isCurrentSumLong = false;
        }
        y = x3 - currentCompensation;
        t = currentSum + y;
        currentCompensation = ((t - currentSum) - y);
        currentSum = t;
        if (currentSum <= Double.NEGATIVE_INFINITY) {
          nInf = true;
          break summation97;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation97;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      if (isCurrentSumLong) {
        return currentSumLong;
      }
      onlyOverflow = false;
      currentCompensation = Math.abs(currentCompensation);
      if (onlyOverflow || (currentCompensation < bestCompensation)) {
        if (currentCompensation <= 0d) {
          return currentSum;
        }
        bestCompensation = currentCompensation;
        bestSum = currentSum;
      }
    }
    if (onlyOverflow) {
      if (pInf || nInf) {
        return Double.NaN;
      }
      if (pInf) {
        return Double.POSITIVE_INFINITY;
      }
      return Double.NEGATIVE_INFINITY;
    }
    return bestSum;
  }
}