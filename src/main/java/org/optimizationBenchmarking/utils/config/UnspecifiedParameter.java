package org.optimizationBenchmarking.utils.config;

import org.optimizationBenchmarking.utils.parsers.LooseStringParser;

/** An unspecified parameter added by the user. */
public final class UnspecifiedParameter extends Parameter<String> {

  /**
   * create a basic parameter definition
   *
   * @param name
   *          the name
   */
  UnspecifiedParameter(final String name) {
    super(name, "User-specified parameter.", //$NON-NLS-1$
        LooseStringParser.INSTANCE, null);
  }
}
