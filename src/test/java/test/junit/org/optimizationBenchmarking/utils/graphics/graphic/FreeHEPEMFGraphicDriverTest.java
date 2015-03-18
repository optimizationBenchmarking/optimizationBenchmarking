package test.junit.org.optimizationBenchmarking.utils.graphics.graphic;

import org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP.FreeHEPEMFGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;

/** The FreeHEP EMF driver test */
public class FreeHEPEMFGraphicDriverTest extends GraphicDriverTest {

  /** create */
  public FreeHEPEMFGraphicDriverTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected IGraphicDriver getInstance() {
    return FreeHEPEMFGraphicDriver.getInstance();
  }
}
