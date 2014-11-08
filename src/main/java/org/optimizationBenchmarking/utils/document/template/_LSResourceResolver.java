package org.optimizationBenchmarking.utils.document.template;

import org.optimizationBenchmarking.utils.bibliography.io.BibliographyXMLConstants;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

/** the internal resource resolver */
final class _LSResourceResolver implements LSResourceResolver {

  /** the original resolver */
  private final LSResourceResolver m_delegate;

  /**
   * create
   * 
   * @param del
   *          the delegate
   */
  _LSResourceResolver(final LSResourceResolver del) {
    super();
    this.m_delegate = del;
  }

  /** {@inheritDoc} */
  @Override
  public final LSInput resolveResource(final String type,
      final String namespaceURI, final String publicId,
      final String systemId, final String baseURI) {

    if (BibliographyXMLConstants.NAMESPACE.equalsIgnoreCase(namespaceURI)) {
      return new _LSBibInput(publicId, systemId, baseURI);
    }

    return ((this.m_delegate != null) ? this.m_delegate.resolveResource(
        type, namespaceURI, publicId, systemId, baseURI) : null);
  }

}
