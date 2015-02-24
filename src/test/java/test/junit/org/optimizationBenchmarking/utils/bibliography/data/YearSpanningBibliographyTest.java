package test.junit.org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;

import examples.org.optimizationBenchmarking.utils.bibliography.data.YearSpanningTestBibliography;

/**
 * a test case to test generating a paper whose date spans multiple years
 */
public class YearSpanningBibliographyTest extends BibliographyTest {

  /** create */
  public YearSpanningBibliographyTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final Bibliography createBibliography() {
    return new YearSpanningTestBibliography().createBibliography();
  }
}
