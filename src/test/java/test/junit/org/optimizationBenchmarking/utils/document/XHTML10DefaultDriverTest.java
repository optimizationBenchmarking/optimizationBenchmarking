package test.junit.org.optimizationBenchmarking.utils.document;

import org.optimizationBenchmarking.utils.document.impl.xhtml10.XHTML10Driver;

/** The default XHTML 1.0 driver test */
public class XHTML10DefaultDriverTest extends DocumentDriverTest {

  /** create the test */
  public XHTML10DefaultDriverTest() {
    super(XHTML10Driver.getDefaultDriver());
  }
}
