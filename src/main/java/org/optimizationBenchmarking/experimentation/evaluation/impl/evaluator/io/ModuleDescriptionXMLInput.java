package org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.io;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data.ModuleDescriptionsBuilder;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.XMLInputTool;
import org.xml.sax.helpers.DefaultHandler;

/** the module description xml input */
public final class ModuleDescriptionXMLInput extends
    XMLInputTool<ModuleDescriptionsBuilder> {

  /** create */
  ModuleDescriptionXMLInput() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final String getSourcesParameterSuffix() {
    return ModuleDescriptionXML.PARAM_MODULES_SUFFIX;
  }

  /** {@inheritDoc} */
  @Override
  protected String getParameterPrefix() {
    return ModuleDescriptionXML.PARAM_MODULES_PREFIX;
  }

  /**
   * get the instance of the {@link ModuleDescriptionXMLInput}
   *
   * @return the instance of the {@link ModuleDescriptionXMLInput}
   */
  public static final ModuleDescriptionXMLInput getInstance() {
    return __ModuleDescriptionXMLInputLoader.INSTANCE;
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
      sf.setResourceResolver(new _LSResourceResolver(sf
          .getResourceResolver()));
      schema = sf.newSchema(//
          ModuleDescriptionXML.MODULE_DESCRIPTION_XML.getSchemaSource());
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
          "Error while loading XML Schema for module description XML.", //$NON-NLS-1$
          true, rec);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final DefaultHandler wrapDestination(
      final ModuleDescriptionsBuilder dataDestination, final IOJob job) {
    return new ModuleDescriptionXMLHandler(null, dataDestination);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Evaluation XML Input"; //$NON-NLS-1$
  }

  /** the loader */
  private static final class __ModuleDescriptionXMLInputLoader {
    /** the evaluation xml */
    static final ModuleDescriptionXMLInput INSTANCE = new ModuleDescriptionXMLInput();
  }
}
