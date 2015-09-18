package test.junit.org.optimizationBenchmarking.utils.math.matrix.impl;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.processing.ConcatenatedMatrix;

import test.junit.org.optimizationBenchmarking.utils.math.matrix.MatrixTest;

/** test the concatenated matrix */
public class ConcatenatedMatrixTest extends MatrixTest<ConcatenatedMatrix> {

  /** the constructor */
  public ConcatenatedMatrixTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected ConcatenatedMatrix getInstance() {
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
        i = (1 + r.nextInt(10));
        j = (1 + r.nextInt(10));
      }
    }

    return new ConcatenatedMatrix(ConcatenatedMatrixTest._create(i, j, r));
  }

  /** test the matrix values */
  @Test(timeout = 3600000)
  public void testMatrixValues() {
    final Random r;
    int z;
    IMatrix[][] matrices;
    ConcatenatedMatrix matrix;
    int i, j, k, l, startRow, endRow, startCol, endCol;

    r = new Random();

    for (z = 10; (--z) >= 0;) {
      matrices = ConcatenatedMatrixTest._create((1 + r.nextInt(10)),
          (1 + r.nextInt(10)), r);
      matrix = new ConcatenatedMatrix(matrices);

      endRow = 0;
      for (i = 0; i < matrices.length; i++) {
        startRow = endRow;
        endRow = (startRow + matrices[i][0].m());
        endCol = 0;
        for (j = 0; j < matrices[i].length; j++) {
          startCol = endCol;
          endCol = (startCol + matrices[i][j].n());
          for (k = startRow; k < endRow; k++) {
            for (l = startCol; l < endCol; l++) {
              Assert.assertEquals(//
                  (matrices[i][j].getLong((k - startRow),//
                      (l - startCol))),//
                  matrix.getLong(k, l));
              Assert.assertEquals((matrices[i][j].getDouble(
                  (k - startRow), (l - startCol))),//
                  matrix.getDouble(k, l), Double.MIN_VALUE);
            }
          }
        }
      }
    }

  }

  /**
   * Create a concatenated matrix
   *
   * @param i
   *          the number of matrix rows
   * @param j
   *          the number of matrix columns
   * @param r
   *          the random number generator
   * @return the set of matrixes
   */
  private static final IMatrix[][] _create(final int i, final int j,
      final Random r) {
    final IMatrix[][] res;
    final int[] rows, cols;
    int k, l;

    rows = new int[i];
    for (k = i; (--k) >= 0;) {
      rows[k] = (r.nextInt(5) + 1);
    }
    cols = new int[j];
    for (l = j; (--l) >= 0;) {
      cols[l] = (r.nextInt(5) + 1);
    }

    res = new IMatrix[i][j];
    for (k = i; (--k) >= 0;) {
      for (l = j; (--l) >= 0;) {
        switch (r.nextInt(8)) {
          case 0: {
            res[k][l] = ByteMatrix1DTest._create(rows[k], cols[l], r);
            break;
          }
          case 1: {
            res[k][l] = DoubleMatrix1DTest._create(rows[k], cols[l], r);
            break;
          }
          case 2: {
            res[k][l] = DoubleMatrix2DTest._create(rows[k], cols[l], r);
            break;
          }
          case 3: {
            res[k][l] = FloatMatrix1DTest._create(rows[k], cols[l], r);
            break;
          }
          case 4: {
            res[k][l] = IntMatrix1DTest._create(rows[k], cols[l], r);
            break;
          }
          case 5: {
            res[k][l] = LongMatrix1DTest._create(rows[k], cols[l], r);
            break;
          }
          default: {
            res[k][l] = LongMatrix2DTest._create(rows[k], cols[l], r);
            break;
          }
        }
      }
    }
    return res;
  }
}
