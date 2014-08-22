package org.optimizationBenchmarking.utils.io.structured;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.SAXParserFactory;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.io.xml.XMLBase;
import org.optimizationBenchmarking.utils.io.xml.XMLDocument;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A base class for reader-based experiment I/O
 * 
 * @param <S>
 *          the store context
 * @param <L>
 *          the load context
 */
public abstract class XMLIODriver<S, L> extends TextIODriver<S, L> {

  /** the xml version */
  protected static final String XML_VERSION = "1.0"; //$NON-NLS-1$

  /** the synchronizer */
  private final Object m_synch;

  /** the SAX parser factory to use */
  private volatile SAXParserFactory m_spf;

  /** the SAX parser factory configuration error */
  private volatile Throwable m_spfConfig;

  /** create */
  protected XMLIODriver() {
    super();
    this.m_synch = new Object();
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
   * Get the SAX parser factory
   * 
   * @return the SAX parser factory
   * @throws IOException
   *           if something goes wrong
   */
  private final SAXParserFactory __getSAXParserFactory()
      throws IOException {
    SAXParserFactory spf;

    if ((spf = this.m_spf) != null) {
      return spf;
    }
    synchronized (this.m_synch) {
      if ((spf = this.m_spf) != null) {
        return spf;
      }
      try {
        this.m_spf = spf = SAXParserFactory.newInstance();
      } catch (final Throwable t) {
        ErrorUtils.throwAsIOException(t);
      }
      try {
        this.configureSAXParserFactory(spf);
      } catch (final Throwable t) {
        this.m_spfConfig = t;
      }
    }

    return spf;
  }

  /**
   * Wrap the loader context into an appropriate default handler
   * 
   * @param loaderContext
   *          the loader context
   * @param logger
   *          the logger to use
   * @return the handler
   */
  protected DefaultHandler wrapLoadContext(final L loaderContext,
      final Logger logger) {
    return ((DefaultHandler) loaderContext);
  }

  /**
   * Load data from a given input source.
   * 
   * @param loaderContext
   *          the loader context
   * @param inputSource
   *          the input source
   * @param logger
   *          the logger
   * @throws IOException
   *           if i/o fails
   */
  public final void loadInputSource(final L loaderContext,
      final InputSource inputSource, final Logger logger)
      throws IOException {
    final SAXParserFactory spf;
    final DefaultHandler h;
    Throwable sideError;

    spf = this.__getSAXParserFactory();
    sideError = this.m_spfConfig;
    if ((sideError != null)
        && ((logger != null) && (logger.isLoggable(Level.WARNING)))) {
      logger.log(Level.WARNING,
          "Recoverable error when setting up XML parser factory.", //$NON-NLS-1$
          sideError);
    }

    h = this.wrapLoadContext(loaderContext, logger);

    try {
      spf.newSAXParser().parse(inputSource, h);
    } catch (final Throwable c) {
      if ((logger != null) && (logger.isLoggable(Level.SEVERE))) {
        logger.log(Level.SEVERE,
            "Unrecoverable error during XML parsing.", c); //$NON-NLS-1$
      }
      ErrorUtils.throwAsIOException(c, sideError);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doLoadReader(final L loaderContext,
      final BufferedReader reader, final Logger logger) throws IOException {
    this.loadInputSource(loaderContext, new InputSource(reader), logger);
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
