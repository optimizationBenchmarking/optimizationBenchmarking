package org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator;

import java.io.InputStream;

import org.optimizationBenchmarking.utils.config.ConfigurationXML;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.io.xml.SimpleLSInput;

/** the local ls input implementation */
final class _LSConfigurationXMLInput extends SimpleLSInput {

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
  _LSConfigurationXMLInput(final String publicId, final String systemId,
      final String baseURI) {
    super(publicId, systemId, baseURI);
  }

  /** {@inheritDoc} */
  @Override
  protected final InputStream createInputStream() {
    try {
      return ConfigurationXML.CONFIG_XML.getSchemaSource().openStream();
    } catch (final Throwable tt) {
      RethrowMode.AS_RUNTIME_EXCEPTION
          .rethrow(//
              "Error while creating input stream to Configuration XML Schema.", //$NON-NLS-1$
              true, tt);
      return null;
    }
  }

}
