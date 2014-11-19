package test.junit.org.optimizationBenchmarking.utils.document;

import org.optimizationBenchmarking.utils.document.impl.xhtml10.XHTML10Driver;
import org.optimizationBenchmarking.utils.graphics.EScreenSize;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;

/** The XHTML 1.0 driver with GIF test */
public class XHTML10GIFDriverTest extends DocumentDriverTest {

  /** create the test */
  public XHTML10GIFDriverTest() {
    super(XHTML10Driver.getInstance(EGraphicFormat.GIF.getDefaultDriver(),
        EScreenSize.SVGA.getPageSize(90), null));
  }
}
