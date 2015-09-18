package test.junit.org.optimizationBenchmarking.utils.math.matrix.impl;

import java.util.Random;

import org.optimizationBenchmarking.utils.math.matrix.impl.DoubleMatrix1D;

import test.junit.org.optimizationBenchmarking.utils.math.matrix.MatrixTest;

/** test the double matrix */
public class DoubleMatrix1DTest extends MatrixTest<DoubleMatrix1D> {

  /** the constructor */
  public DoubleMatrix1DTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected DoubleMatrix1D getInstance() {
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

    return DoubleMatrix1DTest._create(i, j, r);
  }

  /**
   * Create the double matrix of the given dimensions
   *
   * @param i
   *          the number of rows
   * @param j
   *          the number of columns
   * @param r
   *          the random number generator
   * @return the metrix
   */
  static final DoubleMatrix1D _create(final int i, final int j,
      final Random r) {
    final double[] data;
    int k, l, type;

    k = r.nextInt();
    l = (i * j);
    type = r.nextInt(6);

    data = new double[l];
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
        case 2: {
          data[l] = k;
          break;
          /** int */
        }
        case 3: {
          data[l] = (((((((long) k) * k) * k) * k) * k) * k);
          break;
          /** long */
        }
        case 4: {
          data[l] = Math.sin(k);
          break;
          /** double */
        }
        default: {
          data[l] = ((float) ((Math.tan(k))));
          break;
          /** float */
        }
      }
    }

    return new DoubleMatrix1D(data, i, j);
  }
}
