package org.optimizationBenchmarking.utils.config;

import org.optimizationBenchmarking.utils.io.xml.DelegatingHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** A stack-able XML handler for configuration XML data */
public class ConfigurationXMLHandler extends DelegatingHandler {

  /** the destination configuration builder */
  private final ConfigurationBuilder m_dest;

  /**
   * Create
   *
   * @param owner
   *          the owning handler, or {@code null} if not used
   * @param dest
   *          the configuration builder to load the data into
   */
  public ConfigurationXMLHandler(final DelegatingHandler owner,
      final ConfigurationBuilder dest) {
    super(owner);
    this.m_dest = dest;
  }

  /** {@inheritDoc} */
  @Override
  protected void doStartElement(final String uri, final String localName,
      final String qName, final Attributes attributes) throws SAXException {
    String a, b;
    if ((uri == null)
        || (ConfigurationXML.NAMESPACE.equalsIgnoreCase(uri))) {

      if (ConfigurationXML.ELEMENT_CONFIGURATION_PARAMETER
          .equalsIgnoreCase(localName)) {

        a = DelegatingHandler.getAttribute(attributes,
            ConfigurationXML.NAMESPACE,
            ConfigurationXML.ATTRIBUTE_CONFIGURATION_PARAMETER_NAME);
        b = DelegatingHandler.getAttribute(attributes,
            ConfigurationXML.NAMESPACE,
            ConfigurationXML.ATTRIBUTE_CONFIGURATION_PARAMETER_VALUE);

        this.m_dest.put(a, b);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doEndElement(final String uri, final String localName,
      final String qName) throws SAXException {
    if ((uri == null)
        || (ConfigurationXML.NAMESPACE.equalsIgnoreCase(uri))) {
      if (ConfigurationXML.ELEMENT_CONFIGURATION_ROOT
          .equalsIgnoreCase(localName)) {
        this.close();
      }
    }
  }
}
