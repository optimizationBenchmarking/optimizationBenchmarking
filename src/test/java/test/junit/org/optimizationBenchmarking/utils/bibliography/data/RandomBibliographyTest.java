package test.junit.org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

import test.junit.org.optimizationBenchmarking.utils.collections.lists.ArrayListViewTest;
import examples.org.optimizationBenchmarking.utils.bibliography.data.RandomBibliography;

/**
 * a test case to test generating a random bibliography with the
 * bibliography api
 */
public class RandomBibliographyTest extends ArrayListViewTest {

  /** the bibliography */
  private Bibliography m_bib;

  /** create */
  public RandomBibliographyTest() {
    super();
  }

  /**
   * create my bibliography
   * 
   * @return the bibliography
   */
  public synchronized Bibliography getBibliography() {
    if (this.m_bib == null) {
      this.m_bib = new RandomBibliography().createBibliography();
    }
    return this.m_bib;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public ArrayListView<Object> getInstance() {
    return ((ArrayListView) (this.getBibliography()));
  }
}
