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
    final long[][] data;
    int i, j, k, type;

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

    k = r.nextInt(i * j * 10);
    data = new long[i][j];
    type = r.nextInt(4);
    for (final long[] d : data) {
      for (i = d.length; (--i) >= 0;) {
        k++;
        switch (type) {
          case 0: {
            d[i] = ((byte) k);
            break;
            /** byte */
          }
          case 1: {
            d[i] = ((short) k);
            break;
            /** short */
          }
          case 2: {
            d[i] = k;
            break;
            /** int */
          }
          default: {
            d[i] = (((((((long) k) * k) * k) * k) * k) * k);
            break;
            /** long */
          }
        }
      }
    }

    return new LongMatrix2D(data);
  }

}
