package test.junit.org.optimizationBenchmarking.utils.document;

import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXConfiguration;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXConfigurationBuilder;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;

/** The LaTeX driver test using JPEG figures */
public class LaTeXDriverJPEGTest extends DocumentDriverTest {

  /** create the test */
  public LaTeXDriverJPEGTest() {
    super(__make());
  }

  /**
   * make the latex configuration
   * 
   * @return the latex configuration
   */
  private static final LaTeXConfiguration __make() {
    final LaTeXConfigurationBuilder builder;

    builder = new LaTeXConfigurationBuilder();
    builder.setGraphicDriver(EGraphicFormat.JPEG.getDefaultDriver());
    return builder.immutable();
  }
}
