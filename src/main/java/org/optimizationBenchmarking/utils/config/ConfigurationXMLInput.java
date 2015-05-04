package org.optimizationBenchmarking.utils.config;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.XMLInputTool;

/** the configuration xml input */
public final class ConfigurationXMLInput extends
XMLInputTool<ConfigurationBuilder> {

  /** create */
  ConfigurationXMLInput() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final void configureSAXParserFactory(final SAXParserFactory spf)
      throws Throwable {
    Object rec;
    SchemaFactory sf;
    Schema schema;

    rec = null;
    schema = null;
    try {
      sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      schema = sf.newSchema(ConfigurationXML.CONFIG_XML.getSchemaSource());
    } catch (final Throwable a) {
      rec = ErrorUtils.aggregateError(a, rec);
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
      rec = ErrorUtils.aggregateError(b, rec);
    }

    if (rec != null) {
      RethrowMode.AS_IO_EXCEPTION.rethrow(//
          "Error while loading the XML Schema for ConfigurationXML.", //$NON-NLS-1$
          true, rec);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final ConfigurationXMLHandler wrapDestination(
      final ConfigurationBuilder dataDestination, final IOJob job) {
    return new ConfigurationXMLHandler(null, dataDestination);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Configuration XMLFileType Input"; //$NON-NLS-1$
  }

  /**
   * get the instance of the {@link ConfigurationXMLInput}
   *
   * @return the instance of the {@link ConfigurationXMLInput}
   */
  public static final ConfigurationXMLInput getInstance() {
    return __ConfigurationXMLInputLoader.INSTANCE;
  }

  /** the loader */
  private static final class __ConfigurationXMLInputLoader {
    /** the configuration xml */
    static final ConfigurationXMLInput INSTANCE = new ConfigurationXMLInput();
  }
}
