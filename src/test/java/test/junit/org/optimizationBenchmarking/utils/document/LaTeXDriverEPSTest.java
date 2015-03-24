package test.junit.org.optimizationBenchmarking.utils.document;

import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXConfiguration;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXConfigurationBuilder;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;

/** The LaTeX driver test using EPS figures */
public class LaTeXDriverEPSTest extends DocumentDriverTest {

  /** create the test */
  public LaTeXDriverEPSTest() {
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
    builder.setGraphicDriver(EGraphicFormat.EPS.getDefaultDriver());
    return builder.immutable();
  }
}
