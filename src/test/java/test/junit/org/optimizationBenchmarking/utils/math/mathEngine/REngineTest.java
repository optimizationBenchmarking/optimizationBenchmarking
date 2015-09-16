package test.junit.org.optimizationBenchmarking.utils.math.mathEngine;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.io.StreamLineIterator;
import org.optimizationBenchmarking.utils.math.mathEngine.impl.R.R;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IMathEngine;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IMathEngineTool;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.impl.LongMatrix1D;

/** A test for the R engine */
public class REngineTest extends MathEngineTest {

  /** create the test */
  public REngineTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final IMathEngineTool getTool() {
    return R.getInstance();
  }

  /**
   * test whether we can execute scripts as wanted
   *
   * @throws IOException
   *           if i/o fails
   */
  @Test(timeout = 3600000)
  public void testScript() throws IOException {
    final IMathEngineTool tool;
    final IMatrix matrixA, matrixAInverse;

    tool = this.getTool();
    Assert.assertNotNull(tool);

    if (!(tool.canUse())) {
      return;
    }

    matrixA = new LongMatrix1D(new long[] { 1, 3, 3, 1, 4, 3, 1, 3, 4 },
        3, 3);
    matrixAInverse = new LongMatrix1D(new long[] { 7, -3, -3, -1, 1, 0,
        -1, 0, 1 }, 3, 3);

    try (final IMathEngine engine = tool.use().create()) {
      engine.setMatrix("a", matrixA); //$NON-NLS-1$
      try (final StreamLineIterator script = new StreamLineIterator(
          REngineTest.class,//
          "RTestScript.txt")) { //$NON-NLS-1$
        engine.execute(script);
      }

      MathEngineTest._compareMatrices(matrixAInverse,
          engine.getMatrix("aInverse")); //$NON-NLS-1$
    }
  }
}
