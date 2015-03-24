package test.junit.org.optimizationBenchmarking.utils.document;

import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXConfigurationBuilder;

/** The default LaTeX driver test */
public class LaTeXDefaultDriverTest extends DocumentDriverTest {

  /** create the test */
  public LaTeXDefaultDriverTest() {
    super(new LaTeXConfigurationBuilder().immutable());
  }
}
