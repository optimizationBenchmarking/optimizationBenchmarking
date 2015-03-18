package test.junit.org.optimizationBenchmarking.utils.graphics.graphic;

import org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster.ImageIOGIFGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;

/** The image IO GIF driver test */
public class ImageIOGIFGraphicDriverTest extends GraphicDriverTest {

  /** create */
  public ImageIOGIFGraphicDriverTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected IGraphicDriver getInstance() {
    return ImageIOGIFGraphicDriver.getInstance();
  }
}
