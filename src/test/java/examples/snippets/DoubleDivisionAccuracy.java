package examples.snippets;

import java.util.Random;

import org.optimizationBenchmarking.utils.math.functions.arithmetic.Div;

/**
 * A class checking whether the
 * {@link org.optimizationBenchmarking.utils.math.functions.arithmetic.Div}
 * division function for {@code long} numbers is more accurate than
 * converting the numbers to {@code double}s and simply dividing them
 * directly.
 */
public class DoubleDivisionAccuracy extends Thread {

  /** {@inheritDoc} */
  @Override
  public final void run() {
    final Random rand;
    long a, b;
    double quotientDouble, quotientDiv;

    rand = new Random();

    for (;;) {
      a = DoubleDivisionAccuracy.__make(rand);
      b = DoubleDivisionAccuracy.__make(rand);

      quotientDouble = (((double) a) / ((double) b));
      quotientDiv = Div.INSTANCE.computeAsDouble(a, b);

      if (quotientDouble != quotientDiv) {
        synchronized (System.out) {
          System.out.print(a);
          System.out.print('/');
          System.out.print(b);
          System.out.print('=');
          System.out.print(quotientDouble);
          System.out.print(" but DIV says ="); //$NON-NLS-1$
          System.out.println(quotientDiv);
        }
      }
    }
  }

  /**
   * make a random number
   *
   * @param rand
   *          the randomizer
   * @return the number
   */
  private static final long __make(final Random rand) {
    final long min;
    long a, b;

    a = b = 1L;
    if (rand.nextBoolean()) {
      min = (1L << 50L);
    } else {
      min = 1L;
    }
    for (;;) {
      b = a;
      a *= (1L + rand.nextInt(10));
      if (a < 1L) {
        return b;
      }
      if (a >= min) {
        if (rand.nextBoolean()) {
          return a;
        }
      }
    }
  }

  /**
   * Execute
   *
   * @param args
   *          ignored
   */
  public static final void main(final String[] args) {
    for (int i = Runtime.getRuntime().availableProcessors(); (--i) >= 0;) {
      new DoubleDivisionAccuracy().start();
    }
  }

}
