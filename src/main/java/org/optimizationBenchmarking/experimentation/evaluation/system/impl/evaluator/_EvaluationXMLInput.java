package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.XMLInputTool;
import org.xml.sax.helpers.DefaultHandler;

/** the evaluation xml input */
final class _EvaluationXMLInput extends XMLInputTool<_EvaluationSetup> {

  /** create */
  _EvaluationXMLInput() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final String getSourcesParameterSuffix() {
    return _EvaluationXMLConstants.PARAM_EVALUATION_SUFFIX;
  }

  /** {@inheritDoc} */
  @Override
  protected String getParameterPrefix() {
    return _EvaluationXMLConstants.PARAM_EVALUATION_PREFIX;
  }

  /**
   * get the instance of the {@link _EvaluationXMLInput}
   * 
   * @return the instance of the {@link _EvaluationXMLInput}
   */
  public static final _EvaluationXMLInput getInstance() {
    return __EvaluationXMLInputLoader.INSTANCE;
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
      sf.setResourceResolver(new _LSResourceResolver(sf
          .getResourceResolver()));
      schema = sf.newSchema(//
          _EvaluationXMLConstants.class
              .getResource("evaluationConfiguration.1.0.xsd")); //$NON-NLS-1$
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
  protected final DefaultHandler wrapDestination(
      final _EvaluationSetup dataDestination, final IOJob job) {
    return new _EvaluationXMLHandler(null, dataDestination);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Evaluation XML Input"; //$NON-NLS-1$
  }

  /** the loader */
  private static final class __EvaluationXMLInputLoader {
    /** the evaluation xml */
    static final _EvaluationXMLInput INSTANCE = new _EvaluationXMLInput();
  }
}
