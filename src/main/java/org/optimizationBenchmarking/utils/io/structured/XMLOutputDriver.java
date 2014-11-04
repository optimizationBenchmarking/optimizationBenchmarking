package org.optimizationBenchmarking.utils.io.structured;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.SAXParserFactory;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.io.xml.XMLBase;
import org.optimizationBenchmarking.utils.io.xml.XMLDocument;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A base class for xml-based output
 * 
 * @param <S>
 *          the store context
 */
public abstract class XMLOutputDriver<S> extends TextOutputDriver<S> {

  /** the xml version */
  protected static final String XML_VERSION = XMLInputDriver.XML_VERSION;

  /** create */
  protected XMLOutputDriver() {
    super();
  }

  /**
   * Configure the SAX parser factory
   * 
   * @param spf
   *          the sax parser factory to be used by this driver
   * @throws Throwable
   *           if something fails
   */
  protected void configureSAXParserFactory(final SAXParserFactory spf)
      throws Throwable {
    spf.setNamespaceAware(true);
  }

  /**
   * Store an object into an XML document.
   * 
   * @param data
   *          the data to be stored
   * @param dest
   *          the destination writer
   * @param logger
   *          the logger to use
   */
  protected void doStoreXML(final S data, final XMLBase dest,
      final Logger logger) {
    //
  }

  /**
   * Store an object into an XML document.
   * 
   * @param data
   *          the data to be stored
   * @param dest
   *          the destination writer
   * @param logger
   *          the logger to use
   * @throws IOException
   *           if I/O fails
   */
  public final void storeXML(final S data, final XMLBase dest,
      final Logger logger) throws IOException {
    Throwable e;

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer("Beginning XML output."); //$NON-NLS-1$
    }

    e = null;
    try {
      this.doStoreXML(data, dest, logger);
    } catch (final Throwable t) {
      e = t;
    }
    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer("Finished XML output."); //$NON-NLS-1$
    }

    if (e != null) {
      ErrorUtils.throwAsIOException(e);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final void doStoreText(final S data, final ITextOutput dest,
      final Logger logger) {
    Throwable e;

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer("Beginning XML output."); //$NON-NLS-1$
    }

    e = null;
    try (final XMLDocument doc = new XMLDocument(dest)) {
      this.doStoreXML(data, doc, logger);
    } catch (final Throwable ttt) {
      e = ttt;
    }

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer("Finished XML output."); //$NON-NLS-1$
    }

    if (e != null) {
      ErrorUtils.throwAsRuntimeException(e);
    }
  }

}
