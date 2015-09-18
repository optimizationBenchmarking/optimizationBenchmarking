package test.junit.org.optimizationBenchmarking.utils.math.matrix.impl;

import java.util.Random;

import org.optimizationBenchmarking.utils.math.matrix.impl.DoubleMatrix2D;

import test.junit.org.optimizationBenchmarking.utils.math.matrix.MatrixTest;

/** test the double matrix */
public class DoubleMatrix2DTest extends MatrixTest<DoubleMatrix2D> {

  /** the constructor */
  public DoubleMatrix2DTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected DoubleMatrix2D getInstance() {
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

    return DoubleMatrix2DTest._create(i, j, r);
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
  static final DoubleMatrix2D _create(final int i, final int j,
      final Random r) {
    final double[][] data;
    int k, z, type;

    k = r.nextInt();
    type = r.nextInt(6);
    data = new double[i][j];
    for (final double[] d : data) {
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
          case 3: {
            d[z] = (((((((long) k) * k) * k) * k) * k) * k);
            break;
            /** long */
          }
          case 4: {
            d[z] = Math.sin(k);
            break;
            /** double */
          }
          default: {
            d[z] = ((float) ((Math.tan(k))));
            break;
            /** float */
          }
        }
      }
    }

    return new DoubleMatrix2D(data);
  }

}
