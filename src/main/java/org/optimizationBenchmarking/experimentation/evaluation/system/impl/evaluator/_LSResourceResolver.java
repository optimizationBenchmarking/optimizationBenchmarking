package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import org.optimizationBenchmarking.utils.config.ConfigurationXML;
import org.optimizationBenchmarking.utils.io.xml.SimpleLSResourceResolver;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

/** the internal resource resolver */
final class _LSResourceResolver extends SimpleLSResourceResolver {

  /**
   * create
   *
   * @param del
   *          the delegate
   */
  _LSResourceResolver(final LSResourceResolver del) {
    super(del);
  }

  /** {@inheritDoc} */
  @Override
  public final LSInput resolveResource(final String type,
      final String namespaceURI, final String publicId,
      final String systemId, final String baseURI) {

    if (ConfigurationXML.CONFIG_XML.getNamespace().equalsIgnoreCase(
        namespaceURI)) {
      return new _LSConfigurationXMLInput(publicId, systemId, baseURI);
    }

    return super.resolveResource(type, namespaceURI, publicId, systemId,
        baseURI);
  }

}
