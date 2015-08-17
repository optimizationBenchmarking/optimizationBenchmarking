package org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.io;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import org.optimizationBenchmarking.utils.io.xml.IXMLFileType;
import org.optimizationBenchmarking.utils.io.xml.XMLFileType;

/** The evaluation XML constants and file format. */
public enum EvaluationXML implements IXMLFileType {

  /** the evaluation XML */
  EVALUATION_XML;

  /** the namespace */
  static final URI NAMESPACE_URI = URI
      .create(
          "http://www.optimizationBenchmarking.org/formats/evaluationConfiguration/evaluationConfiguration.1.0.xsd").normalize(); //$NON-NLS-1$
  /** the namespace string */
  static final String NAMESPACE = EvaluationXML.NAMESPACE_URI.toString();

  /** the root element of the evaluation process */
  static final String ELEMENT_EVALUATION = "evaluation"; //$NON-NLS-1$

  /** the module element */
  static final String ELEMENT_MODULE = "module";//$NON-NLS-1$

  /** the module attribute class */
  static final String ATTRIBUTE_CLASS = "class";//$NON-NLS-1$

  /** the evaluation source prefix */
  public static final String PARAM_EVALUATION_PREFIX = "evaluation";//$NON-NLS-1$

  /** the evaluation source suffix */
  public static final String PARAM_EVALUATION_SUFFIX = "Setup"; //$NON-NLS-1$

  /** {@inheritDoc} */
  @Override
  public final String getDefaultSuffix() {
    return "xml"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final String getMIMEType() {
    return XMLFileType.XML.getMIMEType();
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return "Evaluation XML"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final URI getNamespaceURI() {
    return EvaluationXML.NAMESPACE_URI;
  }

  /** {@inheritDoc} */
  @Override
  public final String getNamespace() {
    return EvaluationXML.NAMESPACE;
  }

  /** {@inheritDoc} */
  @Override
  public final URL getSchemaSource() throws IOException {
    final URL u;
    u = this.getClass().getResource(//
        EvaluationXML.NAMESPACE.substring(EvaluationXML.NAMESPACE
            .lastIndexOf('/') + 1));
    if (u != null) {
      return u;
    }
    return EvaluationXML.NAMESPACE_URI.toURL();
  }
}
