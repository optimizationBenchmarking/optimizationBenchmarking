package org.optimizationBenchmarking.utils.io.xml;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

/** A simple, internal implementation of a resource resolver. */
public class SimpleLSResourceResolver implements LSResourceResolver {

  /** the original resolver */
  private final LSResourceResolver m_delegate;

  /**
   * create
   *
   * @param del
   *          the delegate
   */
  protected SimpleLSResourceResolver(final LSResourceResolver del) {
    super();
    this.m_delegate = del;
  }

  /** {@inheritDoc} */
  @Override
  public LSInput resolveResource(final String type,
      final String namespaceURI, final String publicId,
      final String systemId, final String baseURI) {
    return ((this.m_delegate != null) ? this.m_delegate.resolveResource(
        type, namespaceURI, publicId, systemId, baseURI) : null);
  }

}
