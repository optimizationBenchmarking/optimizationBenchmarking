package org.optimizationBenchmarking.utils.config;

import org.optimizationBenchmarking.utils.io.xml.DelegatingHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** a handler for configuration xml data */
public class ConfigurationXMLHandler extends DelegatingHandler {

  /** the destination configuration */
  private final ConfigurationBuilder m_dest;

  /**
   * Create
   * 
   * @param owner
   *          the owning handler, or {@code null} if not used
   * @param dest
   *          the configuration instance to load the data into
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
        || (_ConfigXMLConstants.NAMESPACE.equalsIgnoreCase(uri))) {
      if (_ConfigXMLConstants.ELEMENT_CONFIGURATION_PARAMETER
          .equalsIgnoreCase(localName)) {
        a = attributes.getValue(_ConfigXMLConstants.NAMESPACE,
            _ConfigXMLConstants.ATTRIBUTE_CONFIGURATION_PARAMETER_NAME);
        if (a == null) {
          a = attributes
              .getValue(_ConfigXMLConstants.ATTRIBUTE_CONFIGURATION_PARAMETER_NAME);
        }
        b = attributes.getValue(_ConfigXMLConstants.NAMESPACE,
            _ConfigXMLConstants.ATTRIBUTE_CONFIGURATION_PARAMETER_VALUE);
        if (b == null) {
          b = attributes
              .getValue(_ConfigXMLConstants.ATTRIBUTE_CONFIGURATION_PARAMETER_VALUE);
        }

        this.m_dest.put(a, b);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doEndElement(final String uri, final String localName,
      final String qName) throws SAXException {
    if ((uri == null)
        || (_ConfigXMLConstants.NAMESPACE.equalsIgnoreCase(uri))) {
      if (_ConfigXMLConstants.ELEMENT_CONFIGURATION_ROOT
          .equalsIgnoreCase(localName)) {
        this.close();
      }
    }
  }
}
