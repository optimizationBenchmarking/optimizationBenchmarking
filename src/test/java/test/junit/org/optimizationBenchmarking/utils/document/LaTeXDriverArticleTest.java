package test.junit.org.optimizationBenchmarking.utils.document;

import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXConfiguration;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXConfigurationBuilder;
import org.optimizationBenchmarking.utils.document.impl.latex.documentClasses.Article;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.tools.impl.latex.ELaTeXFileType;

/** The LaTeX driver test using the article document class */
public class LaTeXDriverArticleTest extends DocumentDriverTest {

  /** create the test */
  public LaTeXDriverArticleTest() {
    super(LaTeXDriverArticleTest.__make());
  }

  /**
   * make the latex configuration
   *
   * @return the latex configuration
   */
  private static final LaTeXConfiguration __make() {
    final LaTeXConfigurationBuilder builder;

    builder = new LaTeXConfigurationBuilder();
    builder.setDocumentClass(Article.getInstance());
    return builder.immutable();
  }

  /** {@inheritDoc} */
  @Override
  protected final IFileType[] getRequiredTypes() {
    return new IFileType[] { ELaTeXFileType.TEX };
  }
}
