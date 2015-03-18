package test.junit.org.optimizationBenchmarking.utils.graphics.graphic;

import org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP.FreeHEPSVGZGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;

/** The FreeHEP SVGZ driver test */
public class FreeHEPSVGZGraphicDriverTest extends GraphicDriverTest {

  /** create */
  public FreeHEPSVGZGraphicDriverTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected IGraphicDriver getInstance() {
    return FreeHEPSVGZGraphicDriver.getInstance();
  }
}
