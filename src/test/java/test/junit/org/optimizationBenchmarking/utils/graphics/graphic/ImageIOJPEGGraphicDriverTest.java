package test.junit.org.optimizationBenchmarking.utils.graphics.graphic;

import org.optimizationBenchmarking.utils.graphics.graphic.impl.imageioRaster.ImageIOJPEGGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.IGraphicDriver;

/** The image IO JPG driver test */
public class ImageIOJPEGGraphicDriverTest extends GraphicDriverTest {

  /** create */
  public ImageIOJPEGGraphicDriverTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected IGraphicDriver getInstance() {
    return ImageIOJPEGGraphicDriver.getInstance();
  }
}
