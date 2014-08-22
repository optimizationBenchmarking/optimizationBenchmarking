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
    final short[] data;
    int i, j, k, l, type;

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
