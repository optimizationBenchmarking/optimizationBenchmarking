package test.junit.org.optimizationBenchmarking.utils.math.matrix.impl;

import java.util.Random;

import org.optimizationBenchmarking.utils.math.matrix.impl.ShortMatrix1D;

import test.junit.org.optimizationBenchmarking.utils.math.matrix.MatrixTest;

/** test the int matrix */
public class ShortMatrix1DTest extends MatrixTest<ShortMatrix1D> {

  /** the constructor */
  public ShortMatrix1DTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected ShortMatrix1D getInstance() {
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

    return ShortMatrix1DTest._create(i, j, r);
  }

  /**
   * Create the short matrix of the given dimensions
   *
   * @param i
   *          the number of rows
   * @param j
   *          the number of columns
   * @param r
   *          the random number generator
   * @return the metrix
   */
  static final ShortMatrix1D _create(final int i, final int j,
      final Random r) {
    final short[] data;
    int k, l, type;

    k = r.nextInt();
    l = (i * j);
    type = r.nextInt(2);
    data = new short[l];
    for (; (--l) >= 0;) {
      k++;

      switch (type) {
        case 0: {
          data[l] = ((short) k);
          break;
        }
        default: {
          data[l] = ((byte) k);
          break;
        }
      }
    }

    return new ShortMatrix1D(data, i, j);
  }
}
