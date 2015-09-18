package test.junit.org.optimizationBenchmarking.utils.math.matrix.impl;

import java.util.Random;

import org.optimizationBenchmarking.utils.math.matrix.impl.IntMatrix1D;

import test.junit.org.optimizationBenchmarking.utils.math.matrix.MatrixTest;

/** test the int matrix */
public class IntMatrix1DTest extends MatrixTest<IntMatrix1D> {

  /** the constructor */
  public IntMatrix1DTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected IntMatrix1D getInstance() {
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

    return IntMatrix1DTest._create(i, j, r);
  }

  /**
   * Create the int matrix of the given dimensions
   *
   * @param i
   *          the number of rows
   * @param j
   *          the number of columns
   * @param r
   *          the random number generator
   * @return the metrix
   */
  static final IntMatrix1D _create(final int i, final int j, final Random r) {
    final int[] data;
    int k, l, type;

    type = r.nextInt(3);
    k = r.nextInt();
    l = (i * j);
    data = new int[l];
    for (; (--l) >= 0;) {
      k++;

      switch (type) {
        case 0: {
          data[l] = ((byte) k);
          break;
          /** byte */
        }
        case 1: {
          data[l] = ((short) k);
          break;
          /** short */
        }
        default: {
          data[l] = k;
          break;
          /** int */
        }
      }
    }

    return new IntMatrix1D(data, i, j);
  }
}
