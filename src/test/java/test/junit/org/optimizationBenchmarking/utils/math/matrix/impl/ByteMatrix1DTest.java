package test.junit.org.optimizationBenchmarking.utils.math.matrix.impl;

import java.util.Random;

import org.optimizationBenchmarking.utils.math.matrix.impl.ByteMatrix1D;

import test.junit.org.optimizationBenchmarking.utils.math.matrix.MatrixTest;

/** test the int matrix */
public class ByteMatrix1DTest extends MatrixTest<ByteMatrix1D> {

  /** the constructor */
  public ByteMatrix1DTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected ByteMatrix1D getInstance() {
    final Random r;
    final byte[] data;
    int i, j, k, l;

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
    data = new byte[l];
    for (; (--l) >= 0;) {
      data[l] = ((byte) (k++));
    }

    return new ByteMatrix1D(data, i, j);
  }

}
