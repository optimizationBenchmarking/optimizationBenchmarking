package examples.snippets;

/**
 * Just a small program to double-check how and when adding of {@code long}
 * numbers overflows
 */
public final class LongAdditionTest {

  /**
   * The main routine printing long values which could be critical
   *
   * @param args
   *          ignored
   */
  public static void main(final String[] args) {
    long s;

    final long[] test = { Long.MIN_VALUE, (Long.MIN_VALUE + 1), -1L, 0,
        1L, (Long.MAX_VALUE - 1L), Long.MAX_VALUE };

    for (final long a : test) {
      for (final long b : test) {
        if (b == 0L) {
          continue;
        }
        System.out.print(a);
        System.out.print('\t');
        System.out.print(b);
        System.out.print('\t');

        s = (a + b);
        System.out.print(s);
        if (b < 0L) {
          if (s < a) {
            System.out.println("\taccept"); //$NON-NLS-1$
            continue;
          }
        } else {
          if (s > a) {
            System.out.println("\taccept"); //$NON-NLS-1$
            continue;
          }
        }

        System.out.println("\treject"); //$NON-NLS-1$
      }

    }

  }

}
