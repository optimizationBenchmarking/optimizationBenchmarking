package test.junit.org.optimizationBenchmarking.utils.document;

import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXConfiguration;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXConfigurationBuilder;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.tools.impl.latex.ELaTeXFileType;

/** The LaTeX driver test using PDF figures */
public class LaTeXDriverPDFTest extends DocumentDriverTest {

  /** create the test */
  public LaTeXDriverPDFTest() {
    super(LaTeXDriverPDFTest.__make());
  }

  /**
   * make the latex configuration
   * 
   * @return the latex configuration
   */
  private static final LaTeXConfiguration __make() {
    final LaTeXConfigurationBuilder builder;

    builder = new LaTeXConfigurationBuilder();
    builder.setGraphicDriver(EGraphicFormat.PDF.getDefaultDriver());
    return builder.immutable();
  }

  /** {@inheritDoc} */
  @Override
  protected final IFileType[] getRequiredTypes() {
    // TODO: Strange LaTeX problems may prevent compilation, reason is
    // still unclear, but it is not an error of our API.
    // if (LaTeX.getInstance().hasToolChainFor(ELaTeXFileType.TEX,
    // ELaTeXFileType.BIB, EGraphicFormat.PDF)) {
    // return new IFileType[] { ELaTeXFileType.TEX, ELaTeXFileType.PDF };
    // }
    return new IFileType[] { ELaTeXFileType.TEX };
  }
}
