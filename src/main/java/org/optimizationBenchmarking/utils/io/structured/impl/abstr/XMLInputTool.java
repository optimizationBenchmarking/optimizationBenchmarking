package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.SAXParserFactory;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.structured.spec.IXMLInputJobBuilder;
import org.optimizationBenchmarking.utils.io.structured.spec.IXMLInputTool;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A tool for reading XML input
 * 
 * @param <S>
 *          the source type
 */
public class XMLInputTool<S> extends TextInputTool<S> implements
    IXMLInputTool<S> {

  /** the xml version */
  protected static final String XML_VERSION = "1.0"; //$NON-NLS-1$

  /** the SAX parser factory to use */
  private final SAXParserFactory m_spf;

  /** create */
  protected XMLInputTool() {
    super();

    SAXParserFactory spf;
    Logger logger;

    spf = null;
    try {
      spf = SAXParserFactory.newInstance();
      this.configureSAXParserFactory(spf);
    } catch (final Throwable thrower) {
      spf = null;

      logger = Configuration.getRoot().getLogger(
          Configuration.PARAM_LOGGER, null);
      if ((logger != null) && (logger.isLoggable(Level.SEVERE))) {
        logger.log(Level.SEVERE,
            "Could not create and configure SAXParserFactory for " + //$NON-NLS-1$
                TextUtils.className(this.getClass()), thrower);
      }
    }

    this.m_spf = spf;
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
   * Wrap the loader context into an appropriate default handler
   * 
   * @param dataDestination
   *          the destination
   * @param log
   *          the log to use
   * @return the handler
   */
  protected DefaultHandler wrapDestination(final S dataDestination,
      final IOJobLog log) {
    return ((DefaultHandler) dataDestination);
  }

  /** {@inheritDoc} */
  @Override
  void _handle(final IOJobLog log, final S data, final _Location location)
      throws Throwable {
    if (location.m_location1 instanceof InputSource) {
      if (log.canLog()) {
        log.log("Beginning input from InputSource."); //$NON-NLS-1$
      }
      this.__xml(log, data, ((InputSource) (location.m_location1)));
      if (log.canLog()) {
        log.log("Finished input from InputSource."); //$NON-NLS-1$
      }
      return;
    }
    super._handle(log, data, location);
  }

  /**
   * Read the data from an XML document
   * 
   * @param log
   *          the log where logging info can be written
   * @param data
   *          the data to be filled with information
   * @param source
   *          the InputSource to read from
   * @throws Throwable
   */
  private final void __xml(final IOJobLog log, final S data,
      final InputSource source) throws Throwable {
    this.m_spf.newSAXParser().parse(source,
        this.wrapDestination(data, log));
  }

  /** {@inheritDoc} */
  @Override
  protected void reader(final IOJobLog log, final S data,
      final BufferedReader reader) throws Throwable {
    this.__xml(log, data, new InputSource(reader));
  }

  /** {@inheritDoc} */
  @Override
  protected void stream(final IOJobLog log, final S data,
      final InputStream stream, final StreamEncoding<?, ?> encoding)
      throws Throwable {
    this.m_spf.newSAXParser().parse(stream,
        this.wrapDestination(data, log));
  }

  /** {@inheritDoc} */
  @Override
  protected void file(final IOJobLog log, final S data, final Path path,
      final BasicFileAttributes attributes,
      final StreamEncoding<?, ?> encoding) throws Throwable {
    File file;

    try {
      file = path.toFile();
    } catch (final Throwable cannotMakeFile) {
      file = null;
    }

    if (file != null) {
      this.m_spf.newSAXParser().parse(file,
          this.wrapDestination(data, log));
    } else {
      super.file(log, data, path, attributes, encoding);
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public IXMLInputJobBuilder<S> use() {
    this.beforeUse();
    return new _XMLInputJobBuilder(this);
  }

  /** {@inheritDoc} */
  @Override
  public boolean canUse() {
    return (this.m_spf != null);
  }
}
