package org.optimizationBenchmarking.utils.math.functions.arithmetic;

import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.functions.TernaryFunction;

/**
 * The code of an addition ({@code +}) of 3 arguments designed for
 * highest-precision output.
 */
public final class Add3 extends TernaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the precedence priority of this high-precision addition operator */
  public static final int PRECEDENCE_PRIORITY = Add.PRECEDENCE_PRIORITY;

  /**
   * the globally shared instance of the high-precision addition operator
   */
  public static final Add3 INSTANCE = new Add3();

  /** hidden constructor, use {@link #INSTANCE} */
  private Add3() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final byte computeAsByte(final byte x0, final byte x1,
      final byte x2) {
    return (byte) (x0 + x1 + x2);
  }

  /** {@inheritDoc} */
  @Override
  public final short computeAsShort(final short x0, final short x1,
      final short x2) {
    return (short) (x0 + x1 + x2);
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x0, final int x1, final int x2) {
    return (x0 + x1 + x2);
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0, final long x1,
      final long x2) {
    return (x0 + x1 + x2);
  }

  /** {@inheritDoc} */
  @Override
  public final int getPrecedencePriority() {
    return Add3.PRECEDENCE_PRIORITY;
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
    return Add3.INSTANCE;
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
    return Add3.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final int x0, final int x1,
      final int x2) {
    return (((((long) x0)) + x1) + x2);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long x0, final long x1,
      final long x2) {
    boolean isCurrentSumLong;
    long currentSumLong;
    double t, y, currentSum, bestSum, currentCompensation,
        bestCompensation, z0, z1, z2;
    z0 = x0;
    z1 = x1;
    z2 = x2;

    // now testing summation x0 + x1 + x2

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

    // now testing summation x0 + x2 + x1

    currentSum = currentCompensation = 0d;
    currentSumLong = x0;
    isCurrentSumLong = true;
    compute3: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x2) == 0) {
          currentSumLong += x2;
          break compute3;
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

    // now testing summation x2 + x0 + x1

    currentSum = currentCompensation = 0d;
    currentSumLong = x2;
    isCurrentSumLong = true;
    compute5: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x0) == 0) {
          currentSumLong += x0;
          break compute5;
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
    compute6: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x1) == 0) {
          currentSumLong += x1;
          break compute6;
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

    // now testing summation x2 + x1 + x0

    currentSum = currentCompensation = 0d;
    currentSumLong = x2;
    isCurrentSumLong = true;
    compute7: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x1) == 0) {
          currentSumLong += x1;
          break compute7;
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
    compute8: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x0) == 0) {
          currentSumLong += x0;
          break compute8;
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

    // now testing summation x1 + x2 + x0

    currentSum = currentCompensation = 0d;
    currentSumLong = x1;
    isCurrentSumLong = true;
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

    // now testing summation x1 + x0 + x2

    currentSum = currentCompensation = 0d;
    currentSumLong = x1;
    isCurrentSumLong = true;
    compute11: {
      if (isCurrentSumLong) {
        if (SaturatingAdd.getOverflowType(currentSumLong, x0) == 0) {
          currentSumLong += x0;
          break compute11;
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
    return bestSum;
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0, final double x1,
      final double x2) {
    boolean isCurrentSumLong, pInf, nInf, onlyOverflow, isLong0, isLong1,
        isLong2;
    long currentSumLong, l0, l1, l2;
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
    if (pInf) {
      return Double.POSITIVE_INFINITY;
    }
    if (nInf) {
      return Double.NEGATIVE_INFINITY;
    }
    onlyOverflow = true;

    // now testing summation x0 + x1 + x2

    summation4: {
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
      compute5: {
        if (isCurrentSumLong) {
          if (isLong1 && (SaturatingAdd.getOverflowType(currentSumLong,
              l1) == 0)) {
            currentSumLong += l1;
            break compute5;
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
          break summation4;
        }
        if (bestSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation4;
        }
        if ((bestCompensation == 0d) && (NumericalTypes.isLong(bestSum))) {
          currentSumLong = ((long) bestSum);
          isCurrentSumLong = true;
        }
      }
      compute6: {
        if (isCurrentSumLong) {
          if (isLong2 && (SaturatingAdd.getOverflowType(currentSumLong,
              l2) == 0)) {
            currentSumLong += l2;
            break compute6;
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
          break summation4;
        }
        if (bestSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation4;
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

    // now testing summation x0 + x2 + x1

    summation7: {
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
      compute8: {
        if (isCurrentSumLong) {
          if (isLong2 && (SaturatingAdd.getOverflowType(currentSumLong,
              l2) == 0)) {
            currentSumLong += l2;
            break compute8;
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
          break summation7;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation7;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute9: {
        if (isCurrentSumLong) {
          if (isLong1 && (SaturatingAdd.getOverflowType(currentSumLong,
              l1) == 0)) {
            currentSumLong += l1;
            break compute9;
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
          break summation7;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation7;
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

    // now testing summation x2 + x0 + x1

    summation10: {
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
      compute11: {
        if (isCurrentSumLong) {
          if (isLong0 && (SaturatingAdd.getOverflowType(currentSumLong,
              l0) == 0)) {
            currentSumLong += l0;
            break compute11;
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
          break summation10;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation10;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute12: {
        if (isCurrentSumLong) {
          if (isLong1 && (SaturatingAdd.getOverflowType(currentSumLong,
              l1) == 0)) {
            currentSumLong += l1;
            break compute12;
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
          break summation10;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation10;
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

    // now testing summation x2 + x1 + x0

    summation13: {
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
      compute14: {
        if (isCurrentSumLong) {
          if (isLong1 && (SaturatingAdd.getOverflowType(currentSumLong,
              l1) == 0)) {
            currentSumLong += l1;
            break compute14;
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
      compute15: {
        if (isCurrentSumLong) {
          if (isLong0 && (SaturatingAdd.getOverflowType(currentSumLong,
              l0) == 0)) {
            currentSumLong += l0;
            break compute15;
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

    // now testing summation x1 + x2 + x0

    summation16: {
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
      compute17: {
        if (isCurrentSumLong) {
          if (isLong2 && (SaturatingAdd.getOverflowType(currentSumLong,
              l2) == 0)) {
            currentSumLong += l2;
            break compute17;
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
          break summation16;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation16;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
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
          break summation16;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation16;
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

    // now testing summation x1 + x0 + x2

    summation19: {
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
      compute20: {
        if (isCurrentSumLong) {
          if (isLong0 && (SaturatingAdd.getOverflowType(currentSumLong,
              l0) == 0)) {
            currentSumLong += l0;
            break compute20;
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
          break summation19;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation19;
        }
        if ((currentCompensation == 0d)
            && (NumericalTypes.isLong(currentSum))) {
          currentSumLong = ((long) currentSum);
          isCurrentSumLong = true;
        }
      }
      compute21: {
        if (isCurrentSumLong) {
          if (isLong2 && (SaturatingAdd.getOverflowType(currentSumLong,
              l2) == 0)) {
            currentSumLong += l2;
            break compute21;
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
          break summation19;
        }
        if (currentSum >= Double.POSITIVE_INFINITY) {
          pInf = true;
          break summation19;
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