package test.junit.org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;

import examples.org.optimizationBenchmarking.utils.bibliography.data.RandomBibliography;

/**
 * a test case to test generating a random bibliography with the
 * bibliography api
 */
public class RandomBibliographyTest extends BibliographyTest {

  /** create */
  public RandomBibliographyTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final Bibliography createBibliography() {
    return new RandomBibliography().createBibliography();
  }

}
