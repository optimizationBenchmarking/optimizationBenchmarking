package examples.snippets;

import java.util.Random;

import org.optimizationBenchmarking.utils.comparison.EComparison;

/**
 * With this program, I am looking for inconsistencies when comparing
 * numbers of mixed types. I supposed that there may be situations where
 * {@code a=c} but {@code a<b} and {@code b<c} could happen.
 */
public class LongDoubleComparison {

  /**
   * the main method
   *
   * @param args
   *          the command line arguments
   */
  public static final void main(final String[] args) {
    final _Comparator[] comps;
    final Number[] numbers;
    final int[][] results;
    final Random rand;
    int i, j, k;
    long v;

    rand = new Random();
    numbers = new Number[3];
    results = new int[numbers.length][numbers.length];
    comps = new _Comparator[] { new _Long(), new _Double(), new _EC() };

    for (;;) {
      for (i = 0; i < numbers.length; i++) {

        switch (rand.nextInt((i > 0) ? 3 : 2)) {
          case 0: {
            switch (rand.nextInt(3)) {
              case 0: {
                v = rand.nextLong();
                break;
              }
              case 1: {
                v = (Long.MAX_VALUE - rand.nextInt(1024));
                break;
              }
              default: {
                v = (Long.MIN_VALUE + rand.nextInt(1024));
                break;
              }
            }
            if (rand.nextBoolean()) {
              numbers[i] = Long.valueOf(v);
            } else {
              numbers[i] = Double.valueOf(v);
            }
            break;
          }
          case 1: {
            numbers[i] = Double.valueOf(rand.nextLong()
                + rand.nextDouble());
            break;
          }
          default: {
            if (rand.nextBoolean()) {
              numbers[i] = Double.valueOf(numbers[i - 1].longValue()
                  + rand.nextDouble());
            } else {
              numbers[i] = Double.valueOf(numbers[i - 1].doubleValue()
                  + rand.nextDouble());
            }
          }
        }
      }

      for (final _Comparator comp : comps) {
        for (i = numbers.length; (--i) >= 0;) {
          for (j = numbers.length; (--j) >= 0;) {
            results[i][j] = comp._compare(numbers[i], numbers[j]);
          }
        }

        for (i = numbers.length; (--i) >= 0;) {
          if (results[i][i] != 0) {
            LongDoubleComparison.__printComp(numbers[i], numbers[i],
                results[i][i]);
            System.out.print('\t');
            System.out.println(comp.getClass().getSimpleName());
          }

          for (j = numbers.length; (--j) >= 0;) {
            if (results[i][j] != (-results[j][i])) {
              LongDoubleComparison.__printComp(numbers[i], numbers[j],
                  results[i][j]);
              System.out.print("\tbut\t"); //$NON-NLS-1$
              LongDoubleComparison.__printComp(numbers[j], numbers[i],
                  results[j][i]);
              System.out.print('\t');
              System.out.println(comp.getClass().getSimpleName());
            }

            for (k = numbers.length; (--k) >= 0;) {
              if (((results[i][j] == 0) && //
                  (results[j][k] == 0) && //
                  (results[k][i] != 0))
                  || //
                  ((results[i][j] < 0) && //
                      (results[j][k] < 0) && //
                  (results[k][i] <= 0))//
                  || //
                  ((results[i][j] > 0) && //
                      (results[j][k] > 0) && //
                  (results[k][i] >= 0))//
                  || //
                  ((results[i][j] == results[j][k]) && //
                  (results[k][i] != (-(results[i][j]))))) {
                LongDoubleComparison.__printComp(numbers[i], numbers[j],
                    results[i][j]);
                System.out.print("\tand\t"); //$NON-NLS-1$
                LongDoubleComparison.__printComp(numbers[j], numbers[k],
                    results[j][k]);
                System.out.print("\tbut\t"); //$NON-NLS-1$
                LongDoubleComparison.__printComp(numbers[k], numbers[i],
                    results[k][i]);
                System.out.print('\t');
                System.out.println(comp.getClass().getSimpleName());
              }
            }
          }
        }
      }
    }

  }

  /**
   * print a comparison result
   *
   * @param a
   *          a
   * @param b
   *          b
   * @param res
   *          res
   */
  private static final void __printComp(final Number a, final Number b,
      final int res) {
    if (a instanceof Long) {
      System.out.print("(long)"); //$NON-NLS-1$
    } else {
      System.out.print("(double)");//$NON-NLS-1$
    }
    System.out.print(a);
    System.out.print(' ');
    System.out.print((res < 0) ? '<' : ((res > 0) ? '>' : '='));
    System.out.print(' ');
    if (b instanceof Long) {
      System.out.print("(long)"); //$NON-NLS-1$
    } else {
      System.out.print("(double)");//$NON-NLS-1$
    }
    System.out.print(b);
  }

  /** a comparator */
  abstract static class _Comparator {
    /** create */
    _Comparator() {
      super();
    }

    /**
     * compare a and b
     *
     * @param a
     *          the first number
     * @param b
     *          the second number
     * @return the result
     */
    abstract int _compare(Number a, Number b);
  }

  /** long comparison */
  static final class _Long extends _Comparator {
    /** create */
    _Long() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    final int _compare(final Number a, final Number b) {
      return Long.compare(a.longValue(), b.longValue());
    }
  }

  /** double comparison */
  static final class _Double extends _Comparator {
    /** create */
    _Double() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    final int _compare(final Number a, final Number b) {
      return Double.compare(a.doubleValue(), b.doubleValue());
    }
  }

  /** EComparison-based comparison */
  static final class _EC extends _Comparator {
    /** create */
    _EC() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    final int _compare(final Number a, final Number b) {
      return EComparison.compareNumbers(a, b);
    }
  }
}
