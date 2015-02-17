package examples.org.optimizationBenchmarking.utils.bibliography.data;

import java.io.IOException;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;
import org.optimizationBenchmarking.utils.bibliography.io.BibTeXOutput;
import org.optimizationBenchmarking.utils.bibliography.io.BibliographyXMLOutput;
import org.optimizationBenchmarking.utils.io.paths.TempDir;
import org.optimizationBenchmarking.utils.parsers.LoggerParser;

import examples.org.optimizationBenchmarking.FinishedPrinter;

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
    Logger log;

    bib = this.createBibliography();
    log = LoggerParser.INSTANCE.parseObject("global;ALL"); //$NON-NLS-1$

    try {
      BibTeXOutput.getInstance().use().setStream(System.out)
          .setSource(bib).setLogger(log)
          .setFileProducerListener(new FinishedPrinter()).create().call();

      BibliographyExample.__flush();

      BibliographyXMLOutput.getInstance().use().setStream(System.out)
          .setSource(bib).setFileProducerListener(new FinishedPrinter())
          .setLogger(log).create().call();

      BibliographyExample.__flush();

      try (final TempDir temp = new TempDir()) {
        BibTeXOutput.getInstance().use().setPath(temp.getPath())
            .setSource(bib).setLogger(log)
            .setFileProducerListener(new FinishedPrinter()).create()
            .call();
      }

      BibliographyExample.__flush();

      try (final TempDir temp = new TempDir()) {
        BibliographyXMLOutput.getInstance().use().setPath(temp.getPath())
            .setSource(bib).setLogger(log)
            .setFileProducerListener(new FinishedPrinter()).create()
            .call();
      }

    } catch (final IOException ioe) {
      ioe.printStackTrace();
    }
  }

  /** flush */
  private static final void __flush() {
    synchronized (System.out) {
      System.out.flush();
      System.out.println();
      System.out.println();
      System.out.flush();
    }
    synchronized (System.err) {
      System.err.flush();
      System.err.println();
      System.err.println();
      System.err.flush();
    }
  }

}
