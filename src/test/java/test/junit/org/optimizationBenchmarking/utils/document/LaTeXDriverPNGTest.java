package test.junit.org.optimizationBenchmarking.utils.document;

import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXConfiguration;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXConfigurationBuilder;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;

/** The LaTeX driver test using PNG figures*/
public class LaTeXDriverPNGTest extends DocumentDriverTest {

  /** create the test */
  public LaTeXDriverPNGTest() {
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
    builder.setGraphicDriver(EGraphicFormat.PNG.getDefaultDriver());
    return builder.immutable();
  }
}
