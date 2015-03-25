package test.junit.org.optimizationBenchmarking.utils.document;

import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXConfigurationBuilder;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.tools.impl.latex.ELaTeXFileType;

/** The default LaTeX driver test */
public class LaTeXDefaultDriverTest extends DocumentDriverTest {

  /** create the test */
  public LaTeXDefaultDriverTest() {
    super(new LaTeXConfigurationBuilder().immutable());
  }

  /** {@inheritDoc} */
  @Override
  protected final IFileType[] getRequiredTypes() {
    return new IFileType[] { ELaTeXFileType.TEX };
  }
}
