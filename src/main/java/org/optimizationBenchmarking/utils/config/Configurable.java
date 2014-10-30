package org.optimizationBenchmarking.utils.config;

/**
 * <p>
 * A configurable object can be configured with an instance of
 * {@link org.optimizationBenchmarking.utils.config.Configuration}.
 * </p>
 */
public class Configurable extends _ConfigRoot {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** instantiate */
  protected Configurable() {
    super();
  }

  /**
   * Configure this object. This method accesses an instance of
   * {@link org.optimizationBenchmarking.utils.config.Configuration} to
   * initialize and set up the parameters of this object.
   * {@link org.optimizationBenchmarking.utils.config.Configuration
   * Configuration} is basically a fancy, typed, case-insensitive hash map.
   * It can be filled either manually/from your source code, or by parsing
   * the command line arguments, or from a configuration file, or all of
   * the above. Parameter names are strings and used as keys into this hash
   * map and the values are, well, the parameter values. When overriding
   * this method, you must always call the super method.
   * 
   * @param config
   *          the configuration to use
   */
  public void configure(final Configuration config) {
    //
  }

}
