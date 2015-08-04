package org.optimizationBenchmarking.utils.io.xml;

import java.net.URI;

import org.optimizationBenchmarking.utils.text.transformations.XMLCharTransformer;

/** a namespace record */
final class _Namespace {

  /** the uri */
  final URI m_uri;

  /** the prefix */
  final char[] m_prefixChars;

  /** the normalized uri string */
  final char[] m_uriChars;

  /**
   * Create a new namespace record
   *
   * @param uri
   *          the uri
   * @param prefixString
   *          the prefix string
   */
  _Namespace(final URI uri, final String prefixString) {
    super();
    this.m_uri = uri;
    this.m_uriChars = XMLCharTransformer.getInstance()
        .transform(uri.toString()).toCharArray();
    this.m_prefixChars = prefixString.toCharArray();
  }
}
