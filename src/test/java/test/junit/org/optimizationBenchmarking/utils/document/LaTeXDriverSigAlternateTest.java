package test.junit.org.optimizationBenchmarking.utils.document;

import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXConfiguration;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXConfigurationBuilder;
import org.optimizationBenchmarking.utils.document.impl.latex.documentClasses.SigAlternate;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.tools.impl.latex.ELaTeXFileType;

/** The LaTeX driver test using the sig-alternate document class */
public class LaTeXDriverSigAlternateTest extends DocumentDriverTest {

  /** create the test */
  public LaTeXDriverSigAlternateTest() {
    super(LaTeXDriverSigAlternateTest.__make());
  }

  /**
   * make the latex configuration
   *
   * @return the latex configuration
   */
  private static final LaTeXConfiguration __make() {
    final LaTeXConfigurationBuilder builder;

    builder = new LaTeXConfigurationBuilder();
    builder.setDocumentClass(SigAlternate.getInstance());
    return builder.immutable();
  }

  /** {@inheritDoc} */
  @Override
  protected final IFileType[] getRequiredTypes() {
    return new IFileType[] { ELaTeXFileType.TEX };
  }
}
