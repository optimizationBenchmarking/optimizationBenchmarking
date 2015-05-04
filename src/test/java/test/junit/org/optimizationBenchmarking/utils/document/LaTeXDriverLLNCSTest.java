package test.junit.org.optimizationBenchmarking.utils.document;

import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXConfiguration;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXConfigurationBuilder;
import org.optimizationBenchmarking.utils.document.impl.latex.documentClasses.LLNCS;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.tools.impl.latex.ELaTeXFileType;

/** The LaTeX driver test using the LLNCS document class */
public class LaTeXDriverLLNCSTest extends DocumentDriverTest {

  /** create the test */
  public LaTeXDriverLLNCSTest() {
    super(LaTeXDriverLLNCSTest.__make());
  }

  /**
   * make the latex configuration
   *
   * @return the latex configuration
   */
  private static final LaTeXConfiguration __make() {
    final LaTeXConfigurationBuilder builder;

    builder = new LaTeXConfigurationBuilder();
    builder.setDocumentClass(LLNCS.getInstance());
    return builder.immutable();
  }

  /** {@inheritDoc} */
  @Override
  protected final IFileType[] getRequiredTypes() {
    return new IFileType[] { ELaTeXFileType.TEX };
  }
}
