package test.junit.org.optimizationBenchmarking.utils.graphics.graphic;

import org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP.FreeHEPEPSGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;

/** The FreeHEP EPS driver test */
public class FreeHEPEPSGraphicDriverTest extends GraphicDriverTest {

  /** create */
  public FreeHEPEPSGraphicDriverTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected IGraphicDriver getInstance() {
    return FreeHEPEPSGraphicDriver.getInstance();
  }
}
