package test.junit.org.optimizationBenchmarking.utils.graphics.graphic;

import org.optimizationBenchmarking.utils.graphics.graphic.impl.freeHEP.FreeHEPSVGGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;

/** The FreeHEP SVG driver test */
public class FreeHEPSVGGraphicDriverTest extends GraphicDriverTest {

  /** create */
  public FreeHEPSVGGraphicDriverTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected IGraphicDriver getInstance() {
    return FreeHEPSVGGraphicDriver.getInstance();
  }
}
