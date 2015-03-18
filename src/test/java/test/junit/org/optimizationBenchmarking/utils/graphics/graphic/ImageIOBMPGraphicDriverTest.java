package test.junit.org.optimizationBenchmarking.utils.graphics.graphic;

import org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster.ImageIOBMPGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;

/** The image IO BMP driver test */
public class ImageIOBMPGraphicDriverTest extends GraphicDriverTest {

  /** create */
  public ImageIOBMPGraphicDriverTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected IGraphicDriver getInstance() {
    return ImageIOBMPGraphicDriver.getInstance();
  }
}
