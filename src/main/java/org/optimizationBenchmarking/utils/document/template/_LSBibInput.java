package org.optimizationBenchmarking.utils.document.template;

import java.io.InputStream;

import org.optimizationBenchmarking.utils.bibliography.io.BibliographyXML;
import org.optimizationBenchmarking.utils.error.RethrowMode;
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
      return BibliographyXML.BIBLIOGRAPHY_XML.getSchemaSource()
          .openStream();
    } catch (final Throwable tt) {
      RethrowMode.AS_RUNTIME_EXCEPTION.rethrow(//
          "Error while opening input stream to bibliography XML Schema.", //$NON-NLS-1$
          true, tt);
      return null;
    }
  }

}
