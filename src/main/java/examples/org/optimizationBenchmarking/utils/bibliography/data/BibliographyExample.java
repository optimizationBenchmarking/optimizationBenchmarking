package examples.org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;
import org.optimizationBenchmarking.utils.bibliography.io.BibTeXDriver;
import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A class to test generating some of bibliographic data records with the
 * {@link org.optimizationBenchmarking.utils.bibliography.data bibliography
 * API}.
 */
public abstract class BibliographyExample implements Runnable {

  /** the constructor */
  protected BibliographyExample() {
    super();
  }

  /**
   * create my bibliography
   * 
   * @return the bibliography
   */
  public abstract Bibliography createBibliography();

  /** {@inheritDoc} */
  @Override
  public final void run() {
    ITextOutput t;

    t = AbstractTextOutput.wrap(System.out);
    BibTeXDriver.INSTANCE.storeText(this.createBibliography(), t);
    t.flush();
  }

}
