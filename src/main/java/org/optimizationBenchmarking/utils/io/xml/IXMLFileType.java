package org.optimizationBenchmarking.utils.io.xml;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import org.optimizationBenchmarking.utils.io.IFileType;

/**
 * A file type for XML data.
 */
public interface IXMLFileType extends IFileType {

  /**
   * Return the namespace {@link java.net.URI URI}
   *
   * @return the namespace {@link java.net.URI URI}
   * @see #getNamespace()
   * @see #getSchemaSource()
   */
  public abstract URI getNamespaceURI();

  /**
   * Get the namespace as string
   *
   * @return the namespace as string
   * @see #getNamespaceURI()
   */
  public abstract String getNamespace();

  /**
   * Get an {@link java.net.URL URL} for accessing the XSD document
   * specifying schema belonging this file type. This URL could be
   * identical to the {@link #getNamespaceURI() namespace URI} of this file
   * type. However, if the schema is present as resource, the URL could
   * also point to the resource holding the schema. Then the schema can be
   * loaded without requiring an internet connection.
   *
   * @return the schema URL
   * @throws IOException
   *           if i/o fails
   * @see #getNamespaceURI()
   */
  public abstract URL getSchemaSource() throws IOException;
}
