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
    final double[][] data;
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

    k = r.nextInt();
    type = r.nextInt(6);
    data = new double[i][j];
    for (final double[] d : data) {
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
          case 3: {
            d[i] = (((((((long) k) * k) * k) * k) * k) * k);
            break;
            /** long */
          }
          case 4: {
            d[i] = Math.sin(k);
            break;
            /** double */
          }
          default: {
            d[i] = ((float) ((Math.tan(k))));
            break;
            /** float */
          }
        }
      }
    }

    return new DoubleMatrix2D(data);
  }

}
