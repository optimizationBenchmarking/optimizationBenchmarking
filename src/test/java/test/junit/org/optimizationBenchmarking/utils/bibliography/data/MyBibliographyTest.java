package test.junit.org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

import test.junit.org.optimizationBenchmarking.utils.collections.lists.ArrayListViewTest;
import examples.org.optimizationBenchmarking.utils.bibliography.data.MyBibliography;

/**
 * a test case to test generating some of my own papers with the
 * bibliography api
 */
public class MyBibliographyTest extends ArrayListViewTest {

  /** the bibliography */
  private Bibliography m_bib;

  /** create */
  public MyBibliographyTest() {
    super();
  }

  /**
   * create my bibliography
   * 
   * @return the bibliography
   */
  public synchronized Bibliography getBibliography() {
    if (this.m_bib == null) {
      this.m_bib = MyBibliography.createMyBibliography();
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
