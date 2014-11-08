package examples.org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;
import org.optimizationBenchmarking.utils.bibliography.io.BibTeXOutput;
import org.optimizationBenchmarking.utils.bibliography.io.BibliographyXMLOutput;
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
    Bibliography bib;
    ITextOutput t;

    bib = this.createBibliography();

    t = AbstractTextOutput.wrap(System.out);
    BibTeXOutput.INSTANCE.storeText(bib, t);
    t.flush();

    t.appendLineBreak();
    t.appendLineBreak();
    t.appendLineBreak();
    t.appendLineBreak();
    BibliographyXMLOutput.INSTANCE.storeText(bib, t);
    t.flush();
  }

}
