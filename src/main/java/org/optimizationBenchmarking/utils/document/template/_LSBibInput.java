package org.optimizationBenchmarking.utils.document.template;

import java.io.InputStream;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.bibliography.io.BibliographyXMLConstants;
import org.optimizationBenchmarking.utils.io.xml.SimpleLSInput;

/** the local ls input implementation */
final class _LSBibInput extends SimpleLSInput {

  /**
   * create
   * 
   * @param publicId
   *          the public id
   * @param systemId
   *          the system id
   * @param baseURI
   *          the base uri
   */
  _LSBibInput(final String publicId, final String systemId,
      final String baseURI) {
    super(publicId, systemId, baseURI);
  }

  /** {@inheritDoc} */
  @Override
  protected final InputStream createInputStream() {
    try {
      return BibliographyXMLConstants.class
          .getResourceAsStream(BibliographyXMLConstants.SCHEMA);
    } catch (final Throwable tt) {
      try {
        return BibliographyXMLConstants.NAMESPACE_URI.toURL().openStream();
      } catch (final Throwable ttt) {
        ErrorUtils.throwAsRuntimeException(ttt);
        return null;
      }
    }
  }

}
