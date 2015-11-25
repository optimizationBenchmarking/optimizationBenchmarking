package org.optimizationBenchmarking.utils.math.functions.arithmetic;

import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;

/**
 * <p>
 * The {@code "+"} function for avariable number of {@code n} arguments.
 * This function's {@linkplain #destructiveSum(double[]) guts} are gleaned
 * from Python's {@code msum} method.
 * </p>
 * <h2>Inspiration</h2>
 * <p>
 * Python provides a function called {@code msum} whose {@code C} source
 * code is the inspiration of the {@link #destructiveSum(double[])} method
 * used internally here. (Well, I basically translated it to {@code Java}
 * and modified it a bit.)
 * </p>
 * <h2>Original Python Method</h2>
 * <p>
 * Python is open source and licensed under the <i>PYTHON SOFTWARE
 * FOUNDATION LICENSE VERSION 2</i>, PSF, which is GPL compatible.
 * </p>
 * <p>
 * Based on the source (https://www.python.org/getit/source/) of Python
 * 3.5.1rc1 - 2015-11-23, a stable sum of {@code n} numbers can be computed
 * as:
 * </p>
 *
 * <pre>
   def msum(iterable):
       partials = []  # sorted, non-overlapping partial sums
       for x in iterable:
           i = 0
           for y in partials:
               if abs(x) < abs(y):
                   x, y = y, x
               hi = x + y
               lo = y - (hi - x)
               if lo:
                   partials[i] = lo
                   i += 1
               x = hi
           partials[i:] = [x]
       return sum_exact(partials
 * </pre>
 * <p>
 * The {@code C} source code behind that method is:
 * </p>
 *
 * <pre>
static PyObject*
math_fsum(PyObject *self, PyObject *seq)
{
    PyObject *item, *iter, *sum = NULL;
    Py_ssize_t i, j, n = 0, m = NUM_PARTIALS;
    double x, y, t, ps[NUM_PARTIALS], *p = ps;
    double xsave, special_sum = 0.0, inf_sum = 0.0;
    volatile double hi, yr, lo;

    iter = PyObject_GetIter(seq);
    if (iter == NULL)
        return NULL;

    PyFPE_START_PROTECT("fsum", Py_DECREF(iter); return NULL)

    for(;;) {
        assert(0 <= n && n <= m);
        assert((m == NUM_PARTIALS && p == ps) ||
               (m >  NUM_PARTIALS && p != NULL));

        item = PyIter_Next(iter);
        if (item == NULL) {
            if (PyErr_Occurred())
                goto _fsum_error;
            break;
        }
        x = PyFloat_AsDouble(item);
        Py_DECREF(item);
        if (PyErr_Occurred())
            goto _fsum_error;

        xsave = x;
        for (i = j = 0; j < n; j++) {
            y = p[j];
            if (fabs(x) < fabs(y)) {
                t = x; x = y; y = t;
            }
            hi = x + y;
            yr = hi - x;
            lo = y - yr;
            if (lo != 0.0)
                p[i++] = lo;
            x = hi;
        }

        n = i;
        if (x != 0.0) {
            if (! Py_IS_FINITE(x)) {
                if (Py_IS_FINITE(xsave)) {
                    PyErr_SetString(PyExc_OverflowError,
                          "intermediate overflow in fsum");
                    goto _fsum_error;
                }
                if (Py_IS_INFINITY(xsave))
                    inf_sum += xsave;
                special_sum += xsave;
                n = 0;
            }
            else if (n >= m && _fsum_realloc(&p, n, ps, &m))
                goto _fsum_error;
            else
                p[n++] = x;
        }
    }

    if (special_sum != 0.0) {
        if (Py_IS_NAN(inf_sum))
            PyErr_SetString(PyExc_ValueError,
                            "-inf + inf in fsum");
        else
            sum = PyFloat_FromDouble(special_sum);
        goto _fsum_error;
    }

    hi = 0.0;
    if (n > 0) {
        hi = p[--n];
        while (n > 0) {
            x = hi;
            y = p[--n];
            assert(fabs(y) < fabs(x));
            hi = x + y;
            yr = hi - x;
            lo = y - yr;
            if (lo != 0.0)
                break;
        }
        if (n > 0 && ((lo < 0.0 && p[n-1] < 0.0) ||
                      (lo > 0.0 && p[n-1] > 0.0))) {
            y = lo * 2.0;
            x = hi + y;
            yr = x - hi;
            if (y == yr)
                hi = x;
        }
    }
    sum = PyFloat_FromDouble(hi);

_fsum_error:
    PyFPE_END_PROTECT(hi)
    Py_DECREF(iter);
    if (p != ps)
        PyMem_Free(p);
    return sum;
}
 * </pre>
 *
 * <h2>Modifications</h2>
 * <p>
 * I translated the above code to {@code Java} and applied a few changes,
 * namely:
 * </p>
 * <ol>
 * <li>The method {@link #destructiveSum(double[])} takes the summands to
 * be added as input array and overrides this array in the process of
 * summation with the compensation values. Since &ndash; differently from
 * the original method &ndash; it does not allocate any additional memory
 * on the heap, it may be useful for frequent calls in performance-critical
 * code. Moreover, since its input array does not escape the method and is
 * nowhere else stored, using it for quickly
 * {@linkplain org.optimizationBenchmarking.utils.math.functions.arithmetic.Add3
 * adding 3} or
 * {@linkplain org.optimizationBenchmarking.utils.math.functions.arithmetic.Add4
 * 4} numbers can be done by inline-allocation of arrays which may be
 * allocated on the stack by the JIT.</li>
 * <li>I did not understand how the original method handles infinities,
 * overflows, and NaNs. So I made own code there. I hope I did it right.
 * </li>
 * <li>The method can also be used to accurately add {@code long} values
 * into a {@code double} sum by splitting each {@code long} into two values
 * (in order to deal with the fact that {@code double} has a 52 bit
 * mantissa and thus can only represent a subset of the 64 bit long values
 * accurately).</li>
 * <h2>Seel Also</h2>
 * <ol>
 * <li>http://code.activestate.com/recipes/393090-binary-floating-point-
 * summation-accurate-to-full-p/</li>
 * <li>https://www.python.org/getit/source/ for Python 3.5.1rc1 -
 * 2015-11-23</li>
 * <li>http://stackoverflow.com/questions/33866563/</li>
 * </ol>
 */
public final class AddN extends MathematicalFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the precedence priority of the add operator */
  public static final int PRECEDENCE_PRIORITY = Add.PRECEDENCE_PRIORITY;

  /** the globally shared instance */
  public static final AddN INSTANCE = new AddN();

  /** instantiate */
  private AddN() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final int getPrecedencePriority() {
    return AddN.PRECEDENCE_PRIORITY;
  }

  /** {@inheritDoc} */
  @Override
  public final int getMinArity() {
    return 1;
  }

  /** {@inheritDoc} */
  @Override
  public final int getMaxArity() {
    return Integer.MAX_VALUE;
  }

  /**
   * Compute the exact sum of the values in the given array
   * {@code summands} while destroying the contents of said array.
   *
   * @param summands
   *          the summand array &ndash; will be summed up and destroyed
   * @return the accurate sum of the elements of {@code summands}
   */
  public static final double destructiveSum(final double... summands) {
    int i, j, n;
    double x, y, t, xsave, hi, yr, lo;
    boolean ninf, pinf;

    n = 0;
    lo = 0d;
    ninf = pinf = false;

    for (double summand : summands) {

      xsave = summand;
      for (i = j = 0; j < n; j++) {
        y = summands[j];
        if (Math.abs(summand) < Math.abs(y)) {
          t = summand;
          summand = y;
          y = t;
        }
        hi = summand + y;
        yr = hi - summand;
        lo = y - yr;
        if (lo != 0.0) {
          summands[i++] = lo;
        }
        summand = hi;
      }

      n = i; /* ps[i:] = [summand] */
      if (summand != 0d) {
        if ((summand > Double.NEGATIVE_INFINITY)
            && (summand < Double.POSITIVE_INFINITY)) {
          summands[n++] = summand;// all finite, good, continue
        } else {
          if (xsave <= Double.NEGATIVE_INFINITY) {
            if (pinf) {
              return Double.NaN;
            }
            ninf = true;
          } else {
            if (xsave >= Double.POSITIVE_INFINITY) {
              if (ninf) {
                return Double.NaN;
              }
              pinf = true;
            } else {
              return Double.NaN;
            }
          }

          n = 0;
        }
      }
    }

    hi = 0d;
    if (n > 0) {
      hi = summands[--n];
      /*
       * sum_exact(ps, hi) from the top, stop when the sum becomes inexact.
       */
      while (n > 0) {
        x = hi;
        y = summands[--n];
        hi = x + y;
        yr = hi - x;
        lo = y - yr;
        if (lo != 0d) {
          break;
        }
      }
      /*
       * Make half-even rounding work across multiple partials. Needed so
       * that sum([1e-16, 1, 1e16]) will round-up the last digit to two
       * instead of down to zero (the 1e-16 makes the 1 slightly closer to
       * two). With a potential 1 ULP rounding error fixed-up, math.fsum()
       * can guarantee commutativity.
       */
      if ((n > 0) && (((lo < 0d) && (summands[n - 1] < 0d)) || //
          ((lo > 0d) && (summands[n - 1] > 0d)))) {
        y = lo * 2d;
        x = hi + y;
        yr = x - hi;
        if (y == yr) {
          hi = x;
        }
      }
    }
    return hi;
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double... x) {
    return AddN.destructiveSum(x.clone());
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long... x) {
    double[] alloc;
    double lx;
    int i;

    alloc = new double[x.length << 1];
    i = 0;
    for (final long l : x) {
      lx = l;
      alloc[i++] = lx;
      alloc[i++] = (l - ((long) lx));
    }

    return AddN.destructiveSum(alloc);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final int... x) {
    long sum;
    sum = 0L;
    for (final int i : x) {
      sum += i;
    }
    return sum;
  }

  // default, automatic serialization replacement and resolve routines for
  // singletons
  //
  /**
   * Write replace: the instance this method is invoked on will be replaced
   * with the singleton instance {@link #INSTANCE} for serialization, i.e.,
   * when the instance is written with
   * {@link java.io.ObjectOutputStream#writeObject(Object)}.
   *
   * @return the replacement instance (always {@link #INSTANCE})
   */
  private final Object writeReplace() {
    return AddN.INSTANCE;
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
    return AddN.INSTANCE;
  }
}
