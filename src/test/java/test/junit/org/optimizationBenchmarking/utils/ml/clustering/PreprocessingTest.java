package test.junit.org.optimizationBenchmarking.utils.ml.clustering;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.impl.DoubleMatrix1D;
import org.optimizationBenchmarking.utils.math.matrix.impl.DoubleMatrix2D;
import org.optimizationBenchmarking.utils.ml.clustering.impl.abstr.ClusteringTools;

import test.junit.TestBase;

/**
 * The pre-processing of the clustering job.
 */
public class PreprocessingTest extends TestBase {

  /** create */
  public PreprocessingTest() {
    super();
  }

  /** Test the pre-processing of a simple matrix */
  @Test(timeout = 3600000)
  public void testPreprocess1() {
    final IMatrix a, b;

    a = new DoubleMatrix2D(new double[][] { //
        { 1, 2, 3 }, //
        { 2, 2, 5 }, //
        { 7, 3, 1 }//
    });

    b = new DoubleMatrix1D(new double[] { //
        0d, 0, 0.5d, //
        (1d / 6d), 0, 1, //
        1d, 1, 0//
    }, 3, 3);

    Assert.assertEquals(b, ClusteringTools.preprocessDataMatrix(a));
  }

  /** Test the pre-processing of a simple matrix */
  @Test(timeout = 3600000)
  public void testPreprocess2() {
    final IMatrix a, b;

    a = new DoubleMatrix2D(new double[][] { //
        { 1, 2, 3 }, //
        { 2, 2, 5 }, //
        { 7, 2, 1 }//
    });

    b = new DoubleMatrix1D(new double[] { //
        0d, 0.5d, //
        (1d / 6d), 1, //
        1d, 0//
    }, 3, 2);

    Assert.assertEquals(b, ClusteringTools.preprocessDataMatrix(a));
  }

  /** Test the pre-processing of a simple matrix */
  @Test(timeout = 3600000)
  public void testPreprocess3() {
    final IMatrix a, b;

    a = new DoubleMatrix2D(new double[][] { //
        { 1, 10, 3 }, //
        { 2, 20, 5 }, //
        { 7, 70, 1 }//
    });

    b = new DoubleMatrix1D(new double[] { //
        0d, 0.5d, //
        (1d / 6d), 1, //
        1d, 0//
    }, 3, 2);

    Assert.assertEquals(b, ClusteringTools.preprocessDataMatrix(a));
  }

  /** Test the pre-processing of a simple matrix */
  @Test(timeout = 3600000)
  public void testPreprocess4() {
    final IMatrix a, b;

    a = new DoubleMatrix2D(new double[][] { //
        { 1, 10, 1 }, //
        { 2, 20, 2 }, //
        { 7, 70, 7 }//
    });

    b = new DoubleMatrix1D(new double[] { //
        0d, //
        (1d / 6d), //
        1d, //
    }, 3, 1);

    Assert.assertEquals(b, ClusteringTools.preprocessDataMatrix(a));
  }

  /** Test the pre-processing of a simple matrix */
  @Test(timeout = 3600000)
  public void testPreprocess5() {
    final IMatrix a, b;

    a = new DoubleMatrix2D(new double[][] { //
        { 1, 10, Math.nextUp(1d) }, //
        { 2, 20, 2 }, //
        { 7, 70, Math.nextAfter(7d, Double.NEGATIVE_INFINITY) }//
    });

    b = new DoubleMatrix1D(new double[] { //
        0d, //
        (1d / 6d), //
        1d, //
    }, 3, 1);

    Assert.assertEquals(b, ClusteringTools.preprocessDataMatrix(a));
  }

  /** Test the pre-processing of a simple matrix */
  @Test(timeout = 3600000)
  public void testPreprocess6() {
    final IMatrix a, b;

    a = new DoubleMatrix2D(new double[][] { //
        { 1, 33, 10, Math.nextAfter(330, Double.NEGATIVE_INFINITY),
            Math.nextUp(1d) }, //
        { 2, 34, 20, Math.nextAfter(340, Double.NEGATIVE_INFINITY), 2 }, //
        { 7, 35, 70, Math.nextAfter(350, Double.NEGATIVE_INFINITY),
            Math.nextAfter(7d, Double.NEGATIVE_INFINITY) }//
    });

    b = new DoubleMatrix1D(new double[] { //
        0d, 0d, //
        (1d / 6d), (1d / 2d), //
        1d, 1d,//
    }, 3, 2);

    Assert.assertEquals(b, ClusteringTools.preprocessDataMatrix(a));
  }

}
