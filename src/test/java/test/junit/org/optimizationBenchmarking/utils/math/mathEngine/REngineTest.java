package test.junit.org.optimizationBenchmarking.utils.math.mathEngine;

import org.optimizationBenchmarking.utils.math.mathEngine.impl.R.R;
import org.optimizationBenchmarking.utils.math.mathEngine.spec.IMathEngineTool;

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
}
