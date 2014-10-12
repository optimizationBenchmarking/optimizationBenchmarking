package test.junit.org.optimizationBenchmarking.utils.document;

import org.optimizationBenchmarking.utils.document.impl.xhtml10.XHTML10Driver;
import org.optimizationBenchmarking.utils.graphics.EScreenSize;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;

/** The XHTML 1.0 driver with high-quality JPEGs test */
public class XHTML10HQJPEGDriverTest extends DocumentDriverTest {

  /** create the test */
  public XHTML10HQJPEGDriverTest() {
    super(new XHTML10Driver(EGraphicFormat.JPEG.getDefaultDriver(),
        EScreenSize.WQXGA.getPhysicalSize(120), null));
  }
}
