package org.optimizationBenchmarking.utils.config;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import org.optimizationBenchmarking.utils.io.xml.IXMLFileType;
import org.optimizationBenchmarking.utils.io.xml.XMLFileType;

/**
 * The class for the constants of the configuration definition
 * {@link org.optimizationBenchmarking.utils.io.xml.XMLFileType XML}
 * format.
 */
public enum DefinitionXML implements IXMLFileType {

  /** the globally shared instance of the configuration XML file type */
  CONFIG_DEFINITION_XML;

  /** the namespace */
  static final URI NAMESPACE_URI = //
  URI.create(//
      "http://www.optimizationBenchmarking.org/formats/configuration/configurationDef.1.0.xsd").normalize(); //$NON-NLS-1$
  /** the namespace string */
  static final String NAMESPACE = DefinitionXML.NAMESPACE_URI.toString();

  /** the name attribute */
  static final String ATTRIBUTE_NAME = "name"; //$NON-NLS-1$
  /** the description attribute */
  static final String ATTRIBUTE_DESCRIPTION = "description"; //$NON-NLS-1$
  /** the minimum attribute */
  static final String ATTRIBUTE_MIN = "min"; //$NON-NLS-1$
  /** the maximum attribute */
  static final String ATTRIBUTE_MAX = "max"; //$NON-NLS-1$
  /** the default attribute */
  static final String ATTRIBUTE_DEF = "default"; //$NON-NLS-1$
  /** the class attribute */
  static final String ATTRIBUTE_CLASS = "class"; //$NON-NLS-1$
  /** the parser attribute */
  static final String ATTRIBUTE_PARSER = "parser"; //$NON-NLS-1$
  /** the allows-more attribute */
  static final String ATTRIBUTE_ALLOWS_MORE = "allowsMore"; //$NON-NLS-1$

  /** the boolean element */
  static final String ELEMENT_BOOLEAN = "boolean"; //$NON-NLS-1$

  /** the byte element */
  static final String ELEMENT_BYTE = "byte"; //$NON-NLS-1$

  /** the short element */
  static final String ELEMENT_SHORT = "short"; //$NON-NLS-1$

  /** the int element */
  static final String ELEMENT_INT = "int"; //$NON-NLS-1$

  /** the long element */
  static final String ELEMENT_LONG = "long"; //$NON-NLS-1$

  /** the float element */
  static final String ELEMENT_FLOAT = "float"; //$NON-NLS-1$

  /** the double element */
  static final String ELEMENT_DOUBLE = "double"; //$NON-NLS-1$

  /** the string element */
  static final String ELEMENT_STRING = "string"; //$NON-NLS-1$

  /** the instance element */
  static final String ELEMENT_INSTANCE = "instance"; //$NON-NLS-1$

  /** the instance element */
  static final String ELEMENT_CHOICE = "choice"; //$NON-NLS-1$
  /** the definition root element */
  static final String ELEMENT_DEFINITION = "configurationDefinition"; //$NON-NLS-1$

  /** the inheritance element */
  static final String ELEMENT_INHERIT = "inherit"; //$NON-NLS-1$

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
    return "Configuration Definition XML File"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final URI getNamespaceURI() {
    return DefinitionXML.NAMESPACE_URI;
  }

  /** {@inheritDoc} */
  @Override
  public final String getNamespace() {
    return DefinitionXML.NAMESPACE;
  }

  /** {@inheritDoc} */
  @Override
  public final URL getSchemaSource() throws IOException {
    final URL u;
    u = this.getClass().getResource(//
        DefinitionXML.NAMESPACE.substring(DefinitionXML.NAMESPACE
            .lastIndexOf('/') + 1));
    if (u != null) {
      return u;
    }
    return DefinitionXML.NAMESPACE_URI.toURL();
  }
}