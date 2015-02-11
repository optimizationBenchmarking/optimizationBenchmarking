package org.optimizationBenchmarking.utils.config;

import java.security.PrivilegedAction;

import org.optimizationBenchmarking.utils.EmptyUtils;
import org.optimizationBenchmarking.utils.error.ErrorUtils;

/** the configuration loader */
final class _ConfigurationLoader implements
    PrivilegedAction<Configuration> {

  /** the command line arguments */
  private final String[] m_args;

  /**
   * create the configuration loader
   * 
   * @param args
   *          the loader
   */
  _ConfigurationLoader(final String[] args) {
    this.m_args = ((args != null) ? args : EmptyUtils.EMPTY_STRINGS);
  }

  /** {@inheritDoc} */
  @Override
  public final Configuration run() {
    final _ConfigMap cm;
    String q;
    char a, b;
    Object o;

    try (final ConfigurationBuilder cb = new ConfigurationBuilder(null,
        false)) {

      cb.putMap(System.getenv());
      cb.putMap(System.getProperties());

      try {
        cm = cb.m_data.m_data;

        findEnv: if (!(cm.containsKey(Configuration.PARAM_PATH))) {
          for (a = '!'; a <= '&'; a++) {
            for (b = '!'; b <= '&'; b++) {
              q = Configuration.PARAM_PATH;
              if (a != '"') {
                q = (a + q);
              }
              if (b != '"') {
                q = (q + b);
              }
              o = cm.remove(q);
              if (o != null) {
                cm.put(Configuration.PARAM_PATH, o);
                break findEnv;
              }
            }
          }
        }

        cb._configure(this.m_args);
      } catch (final Throwable tt) {
        ErrorUtils.logError(
            cb.m_data.getLogger(Configuration.PARAM_LOGGER, null),//
            "Severe error during setup.", //$NON-NLS-1$
            tt, false);
        ErrorUtils.throwAsRuntimeException(tt);
      }
      return cb.compile();
    }
  }
}
