package org.optimizationBenchmarking.experimentation.io.edi;

import java.nio.file.Path;
import java.util.logging.Logger;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.io.structured.XMLInputDriver;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A driver for Experiment Data Interchange (EDI) input. EDI is our
 * default, canonical format for storing and exchanging
 * {@link org.optimizationBenchmarking.experimentation.data experiment data
 * structures}.
 */
public final class EDIInput extends XMLInputDriver<ExperimentSetContext> {

  /** create */
  EDIInput() {
    super();
  }

  /**
   * get the instance of the {@link EDIInput}
   * 
   * @return the instance of the {@link EDIInput}
   */
  public static final EDIInput getInstance() {
    return __EDIInputLoader.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  protected final void configureSAXParserFactory(final SAXParserFactory spf)
      throws Throwable {
    Throwable rec;
    SchemaFactory sf;
    Schema schema;

    rec = null;
    schema = null;
    try {
      sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      schema = sf.newSchema(//
          _EDIConstants.class
              .getResource("experimentDataInterchange.1.0.xsd")); //$NON-NLS-1$
    } catch (final Throwable a) {
      rec = a;
    } finally {
      sf = null;
    }

    try {
      spf.setNamespaceAware(true);
      if (schema != null) {
        spf.setValidating(false);
        spf.setSchema(schema);
      } else {
        spf.setValidating(false);
      }
    } catch (final Throwable b) {
      rec = ErrorUtils.aggregateError(rec, b);
    }

    if (rec != null) {
      ErrorUtils.throwAsIOException(rec);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isFileInDirectoryLoadable(
      final ExperimentSetContext loadContext, final Path file) {
    final String n;
    final char lm3, lm2, lm1, lm0;
    int len;

    if (super.isFileInDirectoryLoadable(loadContext, file)) {
      n = file.toString();
      len = n.length();
      if (len <= 4) {
        return false;
      }

      lm0 = (n.charAt(--len));
      lm1 = (n.charAt(--len));
      lm2 = (n.charAt(--len));
      lm3 = (n.charAt(--len));

      return ((lm3 == '.') && (//
      (((lm2 == 'x') || (lm2 == 'X')) && ((lm1 == 'm') || (lm1 == 'M')) && ((lm0 == 'l') || (lm0 == 'L'))) || //
      (((lm2 == 'e') || (lm2 == 'E')) && ((lm1 == 'd') || (lm1 == 'D')) && ((lm0 == 'i') || (lm0 == 'I')))));
    }

    return false;
  }

  /** {@inheritDoc} */
  @Override
  protected final DefaultHandler wrapLoadContext(
      final ExperimentSetContext loaderContext, final Logger logger) {
    return new _EDIContentHandler(null, loaderContext, logger);
  }

  /** the loader */
  private static final class __EDIInputLoader {
    /** create */
    static final EDIInput INSTANCE = new EDIInput();
  }
}
