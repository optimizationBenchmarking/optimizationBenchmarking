package test.junit.org.optimizationBenchmarking.utils.document;

import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXConfiguration;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXConfigurationBuilder;
import org.optimizationBenchmarking.utils.document.impl.latex.documentClasses.IEEEtran;

/** The LaTeX driver test using the IEEEtran document class */
public class LaTeXDriverIEEEtranTest extends DocumentDriverTest {

  /** create the test */
  public LaTeXDriverIEEEtranTest() {
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
    builder.setDocumentClass(IEEEtran.getInstance());
    return builder.immutable();
  }
}
