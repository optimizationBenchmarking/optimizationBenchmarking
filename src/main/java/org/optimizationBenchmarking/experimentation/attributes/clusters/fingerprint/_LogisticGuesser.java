package org.optimizationBenchmarking.experimentation.attributes.clusters.fingerprint;

import java.util.Random;

import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add3;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add4;
import org.optimizationBenchmarking.utils.math.functions.power.Exp;
import org.optimizationBenchmarking.utils.math.functions.power.Ln;
import org.optimizationBenchmarking.utils.math.functions.power.Pow;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/** A parameter guesser for the logistic models. */
final class _LogisticGuesser extends _BasicInternalGuesser {

  /**
   * create the guesser
   *
   * @param data
   *          the data
   */
  _LogisticGuesser(final IMatrix data) {
    super(data);
  }

  /**
   * compute the power
   *
   * @param a
   *          the base
   * @param b
   *          the power
   * @return the result
   */
  private static final double __pow(final double a, final double b) {
    return Pow.INSTANCE.computeAsDouble(a, b);
  }

  /**
   * compute the natural logarithm
   *
   * @param a
   *          the number
   * @return the logarithm
   */
  private static final double __log(final double a) {
    return Ln.INSTANCE.computeAsDouble(a);
  }

  /**
   * compute the exponent
   *
   * @param a
   *          the number
   * @return the exponent
   */
  private static final double __exp(final double a) {
    return Exp.INSTANCE.computeAsDouble(a);
  }

  /**
   * add three numbers
   *
   * @param a
   *          the first number
   * @param b
   *          the second number
   * @param c
   *          the third number
   * @return the sum
   */
  private static final double __add3(final double a, final double b,
      final double c) {
    return Add3.INSTANCE.computeAsDouble(a, b, c);
  }

  /**
   * add four numbers
   *
   * @param a
   *          the first number
   * @param b
   *          the second number
   * @param c
   *          the third number
   * @param d
   *          the fourth number
   * @return the sum
   */
  private static final double __add4(final double a, final double b,
      final double c, final double d) {
    return Add4.INSTANCE.computeAsDouble(a, b, c, d);
  }

  /**
   * compute the error of a given fitting
   *
   * @param x0
   *          the first x-coordinate
   * @param y0
   *          the first y-coordinate
   * @param x1
   *          the second x-coordinate
   * @param y1
   *          the second y-coordinate
   * @param x2
   *          the third x-coordinate
   * @param y2
   *          the third y-coordinate
   * @param a
   *          the first fitting parameter
   * @param b
   *          the second fitting parameter
   * @param c
   *          the first fitting parameter
   * @return the fitting error
   */
  private static final double __error(final double x0, final double y0,
      final double x1, final double y1, final double x2, final double y2,
      final double a, final double b, final double c) {
    return _LogisticGuesser.__add3(//
        _LogisticGuesser.__error(x0, y0, a, b, c), //
        _LogisticGuesser.__error(x1, y1, a, b, c), //
        _LogisticGuesser.__error(x2, y2, a, b, c));
  }

  /**
   * Compute the error of the fitting for a single point
   *
   * @param x
   *          the {@code x}-coordinate of the point
   * @param y
   *          the {@code y}-coordinate of the point
   * @param a
   *          the {@code a} value
   * @param b
   *          the {@code b} value
   * @param c
   *          the {@code c} value
   * @return the error
   */
  private static final double __error(final double x, final double y,
      final double a, final double b, final double c) {
    return Math.abs(y - (a / (1d + (b * _LogisticGuesser.__pow(x, c)))));
  }

  /**
   * the median of three numbers
   *
   * @param a
   *          the first number
   * @param b
   *          the second number
   * @param c
   *          the third number
   * @return the median
   */
  private static final double __med3(final double a, final double b,
      final double c) {
    double min, med, max;

    if (a > b) {
      if (a > c) {
        max = a;
        if (b > c) {
          med = b;
          min = c;
        } else {
          med = c;
          min = b;
        }
      } else {
        med = a;
        if (b > c) {
          max = b;
          min = c;
        } else {
          max = c;
          min = b;
        }
      }
    } else {
      if (b > c) {
        max = b;
        if (a > c) {
          med = a;
          min = c;
        } else {
          med = c;
          min = a;
        }
      } else {
        med = b;
        max = c;
        min = a;
      }
    }

    if (MathUtils.isFinite(med)) {
      return med;
    }
    if (MathUtils.isFinite(min)) {
      if (MathUtils.isFinite(max)) {
        med = (0.5d * (min + max));
        if (MathUtils.isFinite(med) && (min <= med) && (med <= max)) {
          return med;
        }
      }
      return min;
    }
    if (MathUtils.isFinite(max)) {
      return max;
    }
    return Double.NaN;
  }

  /**
   * Compute {@code a} from one point {@code (x,y)} and known {@code b} and
   * {@code c} values.
   *
   * @param x
   *          the {@code x}-coordinate of the point
   * @param y
   *          the {@code y}-coordinate of the point
   * @param b
   *          the {@code b} value
   * @param c
   *          the {@code c} value
   * @return the {@code a} value
   */
  private static final double __a_xybc(final double x, final double y,
      final double b, final double c) {
    return ((x <= 0d) ? y
        : (((b * _LogisticGuesser.__pow(x, c)) + 1d) * y));
  }

  /**
   * Compute {@code b} from one point {@code (x,y)} and known {@code a} and
   * {@code c} values.
   *
   * @param x
   *          the {@code x}-coordinate of the point
   * @param y
   *          the {@code y}-coordinate of the point
   * @param a
   *          the {@code a} value
   * @param c
   *          the {@code c} value
   * @return the {@code b} value
   */
  private static final double __b_xyac(final double x, final double y,
      final double a, final double c) {
    final double b1, b2, b3, xc, xcy, e1, e2, e3;

    xc = _LogisticGuesser.__pow(x, c);
    xcy = (xc * y);

    b1 = ((a - y) / xcy);
    b2 = ((a / xcy) - (1d / xc));

    if (b1 == b2) {
      return b1;
    }

    e1 = _LogisticGuesser.__error(x, y, a, b1, c);
    e2 = _LogisticGuesser.__error(x, y, a, b2, c);
    if (MathUtils.isFinite(e1)) {
      if (MathUtils.isFinite(e2)) {
        if (e1 == e2) {
          b3 = (0.5d * (b1 + b2));
          if ((b3 != b2) && (b3 != b1) && ((b1 < b3) || (b2 < b3))
              && ((b3 < b1) || (b3 < b2))) {
            e3 = _LogisticGuesser.__error(x, y, a, b3, c);
            if (MathUtils.isFinite(e3) && (e3 <= e1) && (e3 <= e2)) {
              return b3;
            }
          }
        }
        return ((e1 < e2) ? b1 : b2);
      }
      return b1;
    }
    if (MathUtils.isFinite(e2)) {
      return b2;
    }
    return ((e1 < e2) ? b1 : b2);
  }

  /**
   * Compute {@code c} from one point {@code (x,y)} and known {@code a} and
   * {@code b} values.
   *
   * @param x
   *          the {@code x}-coordinate of the point
   * @param y
   *          the {@code y}-coordinate of the point
   * @param a
   *          the {@code a} value
   * @param b
   *          the {@code b} value
   * @return the {@code c} value
   */
  private static final double __c_xyab(final double x, final double y,
      final double a, final double b) {
    final double c1, c2, c3, lx, by, e1, e2, e3;

    lx = _LogisticGuesser.__log(x);
    if ((b <= (-1d)) && (0d < x) && (x < 1d) && (Math.abs(a) <= 0d)
        && (Math.abs(y) <= 0d)) {
      return Math.nextUp(Math.nextUp(//
          _LogisticGuesser.__log(-1d / b) / lx));
    }

    by = b * y;
    c1 = _LogisticGuesser.__log((a / by) - (1 / b)) / lx;
    c2 = _LogisticGuesser.__log((a - y) / by) / lx;

    if (c1 == c2) {
      return c1;
    }

    e1 = _LogisticGuesser.__error(x, y, a, b, c1);
    e2 = _LogisticGuesser.__error(x, y, a, b, c2);
    if (MathUtils.isFinite(e1)) {
      if (MathUtils.isFinite(e2)) {
        if (e1 == e2) {
          c3 = (0.5d * (c1 + c2));
          if ((c3 != c2) && (c3 != c1) && ((c1 < c3) || (c2 < c3))
              && ((c3 < c1) || (c3 < c2))) {
            e3 = _LogisticGuesser.__error(x, y, a, b, c3);
            if (MathUtils.isFinite(e3) && (e3 <= e1) && (e3 <= e2)) {
              return c3;
            }
          }
        }
        return ((e1 < e2) ? c1 : c2);
      }
      return c1;
    }
    if (MathUtils.isFinite(e2)) {
      return c2;
    }
    return ((e1 < e2) ? c1 : c2);
  }

  /**
   * Compute {@code a} from two points {@code (x1,y1)} and {@code (x2,y2)}
   * and a known {@code c} value.
   *
   * @param x1
   *          the {@code x}-coordinate of the first point
   * @param y1
   *          the {@code y}-coordinate of the first point
   * @param x2
   *          the {@code x}-coordinate of the second point
   * @param y2
   *          the {@code y}-coordinate of the second point
   * @param c
   *          the {@code c} value
   * @return the {@code a} value
   */
  private static final double __a_x1y1x2y2c(final double x1,
      final double y1, final double x2, final double y2, final double c) {
    final double x2c, x1c;

    x1c = _LogisticGuesser.__pow(x1, c);
    x2c = _LogisticGuesser.__pow(x2, c);
    return (((x2c - x1c) * y1 * y2) / ((x2c * y2) - (x1c * y1)));
  }

  /**
   * Compute {@code b} from two points {@code (x1,y1)} and {@code (x2,y2)}
   * and a known {@code c} value.
   *
   * @param x1
   *          the {@code x}-coordinate of the first point
   * @param y1
   *          the {@code y}-coordinate of the first point
   * @param x2
   *          the {@code x}-coordinate of the second point
   * @param y2
   *          the {@code y}-coordinate of the second point
   * @param c
   *          the {@code c} value
   * @return the {@code b} value
   */
  private static final double __b_x1y1x2y2c(final double x1,
      final double y1, final double x2, final double y2, final double c) {
    return ((y1 - y2) / ((_LogisticGuesser.__pow(x2, c) * y2)
        - (_LogisticGuesser.__pow(x1, c) * y1)));
  }

  /**
   * Compute {@code b} from two points {@code (x1,y1)} and {@code (x2,y2)}
   * and a known {@code a} value.
   *
   * @param x1
   *          the {@code x}-coordinate of the first point
   * @param y1
   *          the {@code y}-coordinate of the first point
   * @param x2
   *          the {@code x}-coordinate of the second point
   * @param y2
   *          the {@code y}-coordinate of the second point
   * @param a
   *          the {@code a} value
   * @return the {@code b} value
   */
  private static final double __b_x1y1x2y2a(final double x1,
      final double y1, final double x2, final double y2, final double a) {
    final double lx1, lx2;

    lx1 = _LogisticGuesser.__log(x1);
    lx2 = _LogisticGuesser.__log(x2);
    return _LogisticGuesser.__exp(_LogisticGuesser.__add4(//
        (lx1 * _LogisticGuesser.__log(a - y2)), //
        -(lx2 * _LogisticGuesser.__log(a - y1)), //
        -(lx1 * _LogisticGuesser.__log(y2)), //
        (lx2 * _LogisticGuesser.__log(y1))) / (lx1 - lx2));
  }

  /**
   * Check an {@code a} value
   *
   * @param a
   *          the {@code a} value
   * @param maxY
   *          the maximum {@code y} coordinate
   * @return {@code true} if the {@code a} value is OK, {@code false}
   *         otherwise
   */
  private static final boolean __checkA(final double a,
      final double maxY) {
    final double abs;
    if (MathUtils.isFinite(a)) {
      abs = Math.abs(a);
      if (abs > 1e-14d) {
        if (abs < (1e7d * maxY)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Check an {@code b} value
   *
   * @param b
   *          the {@code b} value
   * @return {@code true} if the {@code b} value is OK, {@code false}
   *         otherwise
   */
  private static final boolean __checkB(final double b) {
    final double abs;
    if (MathUtils.isFinite(b)) {
      abs = Math.abs(b);
      if (abs > 1e-10d) {
        if (abs < 1e10d) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Check an {@code c} value
   *
   * @param c
   *          the {@code c} value
   * @return {@code true} if the {@code b} value is OK, {@code false}
   *         otherwise
   */
  private static final boolean __checkC(final double c) {
    return (MathUtils.isFinite(c) && (c > 1e-4d) && (c < 1e4d));
  }

  /**
   * Update a guess for {@code a}, {@code b}, and {@code c} by using median
   * results from all formulas
   *
   * @param x0
   *          the {@code x}-coordinate of the first point
   * @param y0
   *          the {@code y}-coordinate of the first point
   * @param x1
   *          the {@code x}-coordinate of the second point
   * @param y1
   *          the {@code y}-coordinate of the second point
   * @param x2
   *          the {@code x}-coordinate of the third point
   * @param y2
   *          the {@code y}-coordinate of the third point
   * @param maxY
   *          the maximum {@code y} coorrdinate
   * @param dest
   *          the destination array
   * @param bestError
   *          the best error so far
   * @return the new (or old) best error
   */
  private static final double __updateMed(final double x0, final double y0,
      final double x1, final double y1, final double x2, final double y2,
      final double maxY, final double[] dest, final double bestError) {
    double newA, newB, newC, error;
    boolean hasA, hasB, hasC, changed;

    newA = newB = newC = Double.NaN;
    hasA = hasB = hasC = false;

    changed = true;
    while (changed) {
      changed = false;

      if (!hasB) {
        // find B based on the existing or new A and C values
        newB = _LogisticGuesser.__med3(//
            _LogisticGuesser.__b_x1y1x2y2a(x0, y0, x1, y1,
                (hasA ? newA : dest[0])), //
            _LogisticGuesser.__b_x1y1x2y2a(x1, y1, x2, y2,
                (hasA ? newA : dest[0])), //
            _LogisticGuesser.__b_x1y1x2y2a(x2, y2, x0, y0,
                (hasA ? newA : dest[0])));

        if (_LogisticGuesser.__checkB(newB)) {
          changed = hasB = true;
        } else {
          newB = _LogisticGuesser.__med3(//
              _LogisticGuesser.__b_xyac(x0, y0, (hasA ? newA : dest[0]),
                  (hasC ? newC : dest[2])), //
              _LogisticGuesser.__b_xyac(x1, y1, (hasA ? newA : dest[0]),
                  (hasC ? newC : dest[2])), //
              _LogisticGuesser.__b_xyac(x2, y2, (hasA ? newA : dest[0]),
                  (hasC ? newC : dest[2])));

          if (_LogisticGuesser.__checkB(newB)) {
            changed = hasB = true;
          } else {
            newB = _LogisticGuesser.__med3(//
                _LogisticGuesser.__b_x1y1x2y2c(x0, y0, x1, y1,
                    (hasC ? newC : dest[2])), //
                _LogisticGuesser.__b_x1y1x2y2c(x1, y1, x2, y2,
                    (hasC ? newC : dest[2])), //
                _LogisticGuesser.__b_x1y1x2y2c(x2, y2, x0, y0,
                    (hasC ? newC : dest[2])));

            if (_LogisticGuesser.__checkB(newB)) {
              changed = hasB = true;
            }
          }
        }
      }

      if (!hasC) {
        // find C based on the existing or new A and B values
        newC = _LogisticGuesser.__med3(//
            _LogisticGuesser.__c_xyab(x0, y0, (hasA ? newA : dest[0]),
                (hasB ? newB : dest[1])), //
            _LogisticGuesser.__c_xyab(x1, y1, (hasA ? newA : dest[0]),
                (hasB ? newB : dest[1])), //
            _LogisticGuesser.__c_xyab(x2, y2, (hasA ? newA : dest[0]),
                (hasB ? newB : dest[1])));

        if (_LogisticGuesser.__checkC(newC)) {
          changed = hasC = true;
        }
      }

      if (!hasA) {
        findA: {
          // find A based on the existing or new B and C values
          if (hasB) {
            newA = _LogisticGuesser.__med3(//
                _LogisticGuesser.__a_xybc(x0, y0, newB,
                    (hasC ? newC : dest[2])), //
                _LogisticGuesser.__a_xybc(x1, y1, newB,
                    (hasC ? newC : dest[2])), //
                _LogisticGuesser.__a_xybc(x2, y2, newB,
                    (hasC ? newC : dest[2])));

            if (_LogisticGuesser.__checkA(newA, maxY)) {
              changed = hasA = true;
              break findA;
            }
          }

          newA = _LogisticGuesser.__med3(//
              _LogisticGuesser.__a_x1y1x2y2c(x0, y0, x1, y1,
                  (hasC ? newC : dest[2])), //
              _LogisticGuesser.__a_x1y1x2y2c(x1, y1, x2, y2,
                  (hasC ? newC : dest[2])), //
              _LogisticGuesser.__a_x1y1x2y2c(x2, y2, x0, y0,
                  (hasC ? newC : dest[2])));//

          if (_LogisticGuesser.__checkA(newA, maxY)) {
            changed = hasA = true;
            break findA;
          }

          if (!hasB) {
            newA = _LogisticGuesser.__med3(//
                _LogisticGuesser.__a_xybc(x0, y0, dest[1],
                    (hasC ? newC : dest[2])), //
                _LogisticGuesser.__a_xybc(x1, y1, dest[1],
                    (hasC ? newC : dest[2])), //
                _LogisticGuesser.__a_xybc(x2, y2, dest[1],
                    (hasC ? newC : dest[2])));

            if (_LogisticGuesser.__checkA(newA, maxY)) {
              changed = hasA = true;
              break findA;
            }
          }

          if (!changed) {
            if (Math.abs(x0) <= 0d) {
              newA = y0;
              changed = hasA = true;
              break findA;
            }
            if (Math.abs(x1) <= 0d) {
              newA = y1;
              changed = hasA = true;
              break findA;
            }
            if (Math.abs(x2) <= 0d) {
              newA = y2;
              changed = hasA = true;
              break findA;
            }
          }
        }
      }

      if (hasA && hasB && hasC) {
        error = _LogisticGuesser.__error(x0, y0, x1, y1, x2, y2, newA,
            newB, newC);
        if (MathUtils.isFinite(error) && (error < bestError)) {
          dest[0] = newA;
          dest[1] = newB;
          dest[2] = newC;
          return error;
        }

        return bestError;
      }
    }

    return bestError;
  }

  /**
   * Update a guess for {@code a}, {@code b}, and {@code c} by using the
   * first two points for calculating the new values (and the last one only
   * in the error computation)
   *
   * @param x0
   *          the {@code x}-coordinate of the first point
   * @param y0
   *          the {@code y}-coordinate of the first point
   * @param x1
   *          the {@code x}-coordinate of the second point
   * @param y1
   *          the {@code y}-coordinate of the second point
   * @param x2
   *          the {@code x}-coordinate of the third point
   * @param y2
   *          the {@code y}-coordinate of the third point
   * @param maxY
   *          the maximum {@code y} coordinate
   * @param dest
   *          the destination array
   * @param bestError
   *          the best error so far
   * @return the new (or old) best error
   */
  private static final double __update(final double x0, final double y0,
      final double x1, final double y1, final double x2, final double y2,
      final double maxY, final double[] dest, final double bestError) {
    double newA, newB, newC, error;
    boolean hasA, hasB, hasC, changed;

    newA = newB = newC = Double.NaN;
    hasA = hasB = hasC = false;

    changed = true;
    while (changed) {
      changed = false;

      if (!hasB) {
        // find B based on the existing or new A and C values
        newB = _LogisticGuesser.__b_x1y1x2y2a(x0, y0, x1, y1,
            (hasA ? newA : dest[0]));
        if (_LogisticGuesser.__checkB(newB)) {
          changed = hasB = true;
        } else {
          newB = _LogisticGuesser.__b_xyac(x0, y0, (hasA ? newA : dest[0]),
              (hasC ? newC : dest[2]));
          if (_LogisticGuesser.__checkB(newB)) {
            changed = hasB = true;
          } else {
            newB = _LogisticGuesser.__b_x1y1x2y2c(x0, y0, x1, y1,
                (hasC ? newC : dest[2]));
            if (_LogisticGuesser.__checkB(newB)) {
              changed = hasB = true;
            }
          }
        }
      }

      if (!hasC) {
        // find C based on the existing or new A and B values
        newC = _LogisticGuesser.__c_xyab(x0, y0, (hasA ? newA : dest[0]),
            (hasB ? newB : dest[1]));
        if (_LogisticGuesser.__checkC(newC)) {
          changed = hasC = true;
        }
      }

      if (!hasA) {
        findA: {
          // find A based on the existing or new B and C values
          if (hasB) {
            newA = _LogisticGuesser.__a_xybc(x0, y0, newB,
                (hasC ? newC : dest[2]));
            if (_LogisticGuesser.__checkA(newA, maxY)) {
              changed = hasA = true;
              break findA;
            }
          }

          newA = _LogisticGuesser.__a_x1y1x2y2c(x0, y0, x1, y1,
              (hasC ? newC : dest[2]));
          if (_LogisticGuesser.__checkA(newA, maxY)) {
            changed = hasA = true;
            break findA;
          }

          if (!hasB) {
            newA = _LogisticGuesser.__a_xybc(x0, y0, dest[1],
                (hasC ? newC : dest[2]));
            if (_LogisticGuesser.__checkA(newA, maxY)) {
              changed = hasA = true;
              break findA;
            }
          }

          if (!changed) {
            if (Math.abs(x0) <= 0d) {
              newA = y0;
              changed = hasA = true;
              break findA;
            }
            if (Math.abs(x1) <= 0d) {
              newA = y1;
              changed = hasA = true;
              break findA;
            }
            if (Math.abs(x2) <= 0d) {
              newA = y2;
              changed = hasA = true;
              break findA;
            }
          }
        }
      }

      if (hasA && hasB && hasC) {
        error = _LogisticGuesser.__error(x0, y0, x1, y1, x2, y2, newA,
            newB, newC);
        if (MathUtils.isFinite(error) && (error < bestError)) {
          dest[0] = newA;
          dest[1] = newB;
          dest[2] = newC;
          return error;
        }

        return bestError;
      }
    }

    return bestError;
  }

  /** {@inheritDoc} */
  @Override
  final boolean guessBasedOn3Points(final double x0, final double y0,
      final double x1, final double y1, final double x2, final double y2,
      final double[] dest, final Random random) {
    final double maxY;
    double oldError, newError;
    int steps;

    maxY = Math.max(y0, Math.max(y1, y2));
    steps = 100;
    newError = Double.POSITIVE_INFINITY;

    while ((--steps) > 0) {
      this._fallback(maxY, random, dest);
      for (;;) {
        oldError = newError;

        newError = _LogisticGuesser.__update(x0, y0, x1, y1, x2, y2, maxY,
            dest, oldError);
        newError = _LogisticGuesser.__update(x0, y0, x2, y2, x1, y1, maxY,
            dest, newError);
        newError = _LogisticGuesser.__update(x1, y1, x0, y0, x2, y2, maxY,
            dest, newError);
        newError = _LogisticGuesser.__update(x1, y1, x2, y2, x0, y0, maxY,
            dest, newError);
        newError = _LogisticGuesser.__update(x2, y2, x0, y0, x1, y1, maxY,
            dest, newError);
        newError = _LogisticGuesser.__update(x2, y2, x1, y1, x0, y0, maxY,
            dest, newError);

        newError = _LogisticGuesser.__updateMed(x0, y0, x1, y1, x2, y2,
            maxY, dest, newError);

        if ((--steps) <= 0) {
          return MathUtils.isFinite(newError);
        }
        if (newError >= oldError) {
          if (MathUtils.isFinite(newError)) {
            return true;
          }
        }
      }
    }

    return false;
  }

  /** {@inheritDoc} */
  @Override
  final void _fallback(final double maxY, final Random random,
      final double[] parameters) {
    double v;

    v = Math.abs(maxY);
    if (v <= 0d) {
      v = 1e-6d;
    }

    parameters[0] = v = Math.abs(v * (1d + //
        Math.abs(0.01d * random.nextGaussian())));
    parameters[1] = (-Math.abs(//
        v * (1d / (17d + (3d * random.nextGaussian())))));
    do {
      parameters[2] = (1d + random.nextGaussian());
    } while (parameters[2] <= 1e-7d);
  }
}
