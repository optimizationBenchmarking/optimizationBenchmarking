package test.junit.org.optimizationBenchmarking.utils.math.matrix.impl;

import java.util.Random;

import org.optimizationBenchmarking.utils.math.matrix.impl.LongMatrix2D;

import test.junit.org.optimizationBenchmarking.utils.math.matrix.MatrixTest;

/** test the long matrix */
public class LongMatrix2DTest extends MatrixTest<LongMatrix2D> {

  /** the constructor */
  public LongMatrix2DTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected LongMatrix2D getInstance() {
    final Random r;
    int i, j;

    r = new Random();

    switch (r.nextInt(9)) {
      case 0: {
        i = 1;
        j = 1;
        break;
      }
      case 1: {
        i = 1;
        j = 2;
        break;
      }
      case 2: {
        i = 2;
        j = 1;
        break;
      }
      case 3: {
        i = 3;
        j = 1;
        break;
      }
      case 4: {
        i = 3;
        j = 2;
        break;
      }
      case 5: {
        i = 3;
        j = 3;
        break;
      }
      case 6: {
        i = 2;
        j = 3;
        break;
      }
      case 7: {
        i = 1;
        j = 3;
        break;
      }
      default: {
        i = (1 + r.nextInt(100));
        j = (1 + r.nextInt(100));
      }
    }

    return LongMatrix2DTest._create(i, j, r);
  }

  /**
   * Create the long matrix of the given dimensions
   *
   * @param i
   *          the number of rows
   * @param j
   *          the number of columns
   * @param r
   *          the random number generator
   * @return the metrix
   */
  static final LongMatrix2D _create(final int i, final int j,
      final Random r) {
    final long[][] data;
    int k, z, type;

    k = r.nextInt(i * j * 10);
    data = new long[i][j];
    type = r.nextInt(4);
    for (final long[] d : data) {
      for (z = d.length; (--z) >= 0;) {
        k++;
        switch (type) {
          case 0: {
            d[z] = ((byte) k);
            break;
            /** byte */
          }
          case 1: {
            d[z] = ((short) k);
            break;
            /** short */
          }
          case 2: {
            d[z] = k;
            break;
            /** int */
          }
          default: {
            d[z] = (((((((long) k) * k) * k) * k) * k) * k);
            break;
            /** long */
          }
        }
      }
    }

    return new LongMatrix2D(data);
  }

}
