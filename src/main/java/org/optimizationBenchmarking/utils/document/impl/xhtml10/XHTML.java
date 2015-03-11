package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import org.optimizationBenchmarking.utils.io.xml.IXMLFileType;

/** The XHTML file type */
public enum XHTML implements IXMLFileType {

  /** The XHTML 1.0 format */
  XHTML_1_0;

  /** the namespace URI */
  private static final URI NAMESPACE_URI = URI
      .create("http://www.w3.org/1999/xhtml"); //$NON-NLS-1$

  /** the namespace as string */
  private static final String NAMESPACE = XHTML.NAMESPACE_URI.toString();

  /** {@inheritDoc} */
  @Override
  public final String getDefaultSuffix() {
    return "xhtml";//$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final String getMIMEType() {
    return "application/xhtml+xml"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return "Extensible Hypertext Markup Language 1.0";//$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final URI getNamespaceURI() {
    return XHTML.NAMESPACE_URI;
  }

  /** {@inheritDoc} */
  @Override
  public final String getNamespace() {
    return XHTML.NAMESPACE;
  }

  /** {@inheritDoc} */
  @Override
  public final URL getSchemaSource() throws IOException {
    return new URL(//
        "http://www.w3.org/2002/08/xhtml/xhtml1-strict.xsd");//$NON-NLS-1$
  }
}
