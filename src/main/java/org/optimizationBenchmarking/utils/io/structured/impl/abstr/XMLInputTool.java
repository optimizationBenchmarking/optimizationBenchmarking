package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Logger;

import javax.xml.parsers.SAXParserFactory;

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

  /** the cause why this tool cannot be used */
  private final Throwable m_cause;

  /** create */
  protected XMLInputTool() {
    super();

    SAXParserFactory spf;
    Throwable cause;

    spf = null;
    cause = null;
    try {
      spf = SAXParserFactory.newInstance();
      this.configureSAXParserFactory(spf);
    } catch (final Throwable thrower) {
      cause = thrower;
      spf = null;
    }

    this.m_cause = cause;
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
   * @param job
   *          the job to use
   * @return the handler
   * @throws Throwable
   *           if it fails
   */
  protected DefaultHandler wrapDestination(final S dataDestination,
      final IOJob job) throws Throwable {
    return ((DefaultHandler) dataDestination);
  }

  /** {@inheritDoc} */
  @Override
  void _handle(final IOJob job, final S data, final _Location location)
      throws Throwable {
    final Logger logger;

    if (location.m_location1 instanceof InputSource) {
      logger = job.getLogger();
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            ("Beginning input from InputSource.")); //$NON-NLS-1$
      }
      this.__xml(job, data, ((InputSource) (location.m_location1)));
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            ("Finished input from InputSource.")); //$NON-NLS-1$
      }
      return;
    }
    super._handle(job, data, location);
  }

  /**
   * Read the data from an XML document
   *
   * @param job
   *          the job where logging info can be written
   * @param data
   *          the data to be filled with information
   * @param source
   *          the InputSource to read from
   * @throws Throwable
   *           if I/O fails
   */
  private final void __xml(final IOJob job, final S data,
      final InputSource source) throws Throwable {
    this.m_spf.newSAXParser().parse(source,
        this.wrapDestination(data, job));
  }

  /** {@inheritDoc} */
  @Override
  protected void reader(final IOJob job, final S data,
      final BufferedReader reader) throws Throwable {
    this.__xml(job, data, new InputSource(reader));
  }

  /** {@inheritDoc} */
  @Override
  protected void stream(final IOJob job, final S data,
      final InputStream stream, final StreamEncoding<?, ?> encoding)
      throws Throwable {
    this.m_spf.newSAXParser().parse(stream,
        this.wrapDestination(data, job));
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unused")
  @Override
  protected void file(final IOJob job, final S data, final Path path,
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
          this.wrapDestination(data, job));
    } else {
      super.file(job, data, path, attributes, encoding);
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public IXMLInputJobBuilder<S> use() {
    this.checkCanUse();
    return new _XMLInputJobBuilder(this);
  }

  /** {@inheritDoc} */
  @Override
  public boolean canUse() {
    return ((this.m_spf != null) && (this.m_cause == null));
  }

  /** {@inheritDoc} */
  @Override
  public void checkCanUse() {
    if (this.m_cause != null) {
      throw new UnsupportedOperationException(//
          "Cannot use tool '" + //$NON-NLS-1$
              TextUtils.className(this.getClass())
              + " due to error in XML parser initialization.",//$NON-NLS-1$
          this.m_cause);
    }
    super.checkCanUse();
  }
}
