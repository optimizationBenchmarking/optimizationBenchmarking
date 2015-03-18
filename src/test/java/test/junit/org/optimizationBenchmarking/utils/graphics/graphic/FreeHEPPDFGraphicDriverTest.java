package test.junit.org.optimizationBenchmarking.utils.graphics.graphic;

import org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP.FreeHEPPDFGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;

/** The FreeHEP PDF driver test */
public class FreeHEPPDFGraphicDriverTest extends GraphicDriverTest {

  /** create */
  public FreeHEPPDFGraphicDriverTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected IGraphicDriver getInstance() {
    return FreeHEPPDFGraphicDriver.getInstance();
  }
}
