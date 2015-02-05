package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.io.InputStream;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.config.ConfigurationXMLConstants;
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
      return ConfigurationXMLConstants.class
          .getResourceAsStream(ConfigurationXMLConstants.SCHEMA);
    } catch (final Throwable tt) {
      try {
        return ConfigurationXMLConstants.NAMESPACE_URI.toURL()
            .openStream();
      } catch (final Throwable ttt) {
        ErrorUtils.throwAsRuntimeException(ttt);
        return null;
      }
    }
  }

}
