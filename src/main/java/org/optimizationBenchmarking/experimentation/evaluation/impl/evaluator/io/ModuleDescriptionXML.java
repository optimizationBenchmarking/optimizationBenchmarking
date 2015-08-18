package org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.io;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import org.optimizationBenchmarking.utils.io.xml.IXMLFileType;
import org.optimizationBenchmarking.utils.io.xml.XMLFileType;

/** The module description XML constants and file format. */
public enum ModuleDescriptionXML implements IXMLFileType {

  /** the module description XML */
  MODULE_DESCRIPTION_XML;

  /** the namespace */
  static final URI NAMESPACE_URI = URI
      .create(
          "http://www.optimizationBenchmarking.org/formats/evaluationModules/evaluationModules.1.0.xsd").normalize(); //$NON-NLS-1$
  /** the namespace string */
  static final String NAMESPACE = ModuleDescriptionXML.NAMESPACE_URI
      .toString();

  /** the root element of the evaluation process */
  static final String ELEMENT_MODULES = "modules"; //$NON-NLS-1$

  /** the module element */
  static final String ELEMENT_MODULE = "module";//$NON-NLS-1$

  /** the module attribute class */
  static final String ATTRIBUTE_CLASS = "class";//$NON-NLS-1$

  /** the module attribute name */
  static final String ATTRIBUTE_NAME = "name";//$NON-NLS-1$

  /** the module attribute description */
  static final String ATTRIBUTE_DESCRIPTION = "description";//$NON-NLS-1$

  /** the module source prefix */
  static final String PARAM_MODULES_PREFIX = "module";//$NON-NLS-1$

  /** the module source suffix */
  static final String PARAM_MODULES_SUFFIX = "Descriptions"; //$NON-NLS-1$

  /** {@inheritDoc} */
  @Override
  public final String getDefaultSuffix() {
    return XMLFileType.XML.getDefaultSuffix();
  }

  /** {@inheritDoc} */
  @Override
  public final String getMIMEType() {
    return XMLFileType.XML.getMIMEType();
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return "Module Description XML"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final URI getNamespaceURI() {
    return ModuleDescriptionXML.NAMESPACE_URI;
  }

  /** {@inheritDoc} */
  @Override
  public final String getNamespace() {
    return ModuleDescriptionXML.NAMESPACE;
  }

  /** {@inheritDoc} */
  @Override
  public final URL getSchemaSource() throws IOException {
    final URL u;
    u = this
        .getClass()
        .getResource(//
            ModuleDescriptionXML.NAMESPACE
                .substring(ModuleDescriptionXML.NAMESPACE.lastIndexOf('/') + 1));
    if (u != null) {
      return u;
    }
    return ModuleDescriptionXML.NAMESPACE_URI.toURL();
  }
}
