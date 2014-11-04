package org.optimizationBenchmarking.utils.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;

import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.parsers.PathParser;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** An API to build configuration objects. */
public class ConfigurationBuilder extends BuilderFSM<Configuration> {

  /** the owner has been set */
  private static final int FLAG_OWNER_HAS_BEEN_SET = (FSM.FLAG_NOTHING + 1);
  /** the data has been set */
  private static final int FLAG_DATA_HAS_BEEN_SET = (ConfigurationBuilder.FLAG_OWNER_HAS_BEEN_SET << 1);
  /** the data has been configured */
  private static final int FLAG_HAS_BEEN_CONFIGURED = (ConfigurationBuilder.FLAG_DATA_HAS_BEEN_SET << 1);

  /** the configuration being built */
  private Configuration m_data;

  /** Create the configuration builder */
  public ConfigurationBuilder() {
    this(null);
  }

  /**
   * Create the configuration builder
   * 
   * @param owner
   *          the owning fsm
   */
  public ConfigurationBuilder(final HierarchicalFSM owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_OWNER_HAS_BEEN_SET: {
        append.append("ownerHasBeenSet");return; //$NON-NLS-1$
      }
      case FLAG_DATA_HAS_BEEN_SET: {
        append.append("dataHasBeenSet");return; //$NON-NLS-1$
      }
      case FLAG_HAS_BEEN_CONFIGURED: {
        append.append("hasBeenConfigured");return; //$NON-NLS-1$
      }
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
    }
  }

  /**
   * get the configuration
   * 
   * @return the configuration
   */
  private final Configuration __get() {
    if (this.m_data == null) {
      this.m_data = new Configuration(null);
    }
    return this.m_data;
  }

  /**
   * Set the owner of the configuration to be built
   * 
   * @param owner
   *          the owner
   */
  public synchronized final void setOwner(final Configuration owner) {
    if (owner == null) {
      throw new IllegalArgumentException(//
          "Cannot set owning configuration to null - if you want a configuration without owner, then don't set it."); //$NON-NLS-1$
    }

    this.fsmFlagsAssertAndUpdate(
        FSM.FLAG_NOTHING,
        (ConfigurationBuilder.FLAG_DATA_HAS_BEEN_SET | ConfigurationBuilder.FLAG_OWNER_HAS_BEEN_SET),
        ConfigurationBuilder.FLAG_OWNER_HAS_BEEN_SET, FSM.FLAG_NOTHING);

    if (this.m_data != null) {
      throw new IllegalStateException(//
          "Cannot set owner of configuration twice or after a configuration value has been set."); //$NON-NLS-1$
    }

    this.m_data = new Configuration(owner);
  }

  /**
   * Put a value
   * 
   * @param key
   *          the key
   * @param value
   *          the value
   */
  public synchronized final void put(final String key, final Object value) {
    final _ConfigMapEntry entry;
    final boolean isLocked;
    final _ConfigMap map;

    map = this.__get().m_data;
    this.fsmFlagsSet(ConfigurationBuilder.FLAG_DATA_HAS_BEEN_SET);
    synchronized (map) {
      entry = ((_ConfigMapEntry) (map.getEntry(key, true)));
      isLocked = entry.m_isLocked;
      if (!isLocked) {
        entry.setValue(value);
      }
    }

    if (isLocked) {
      throw new IllegalStateException(//
          "There has already been a read access to '" + key + //$NON-NLS-1$
              "', so it cannot be changed anymore."); //$NON-NLS-1$
    }
  }

  /**
   * Put a string to the map. The string is considered to be in the form
   * {@code key=value} or {@code key:value} and may be preceded by any
   * number of {@code -} or {@code /}-es. If the value part is missing
   * {@code "true"} is used as value.
   * 
   * @param s
   *          the string
   */
  public synchronized final void putCommandLine(final String s) {
    String t;
    int i, j;
    final int len;
    char ch;
    boolean canUseSlash;

    if (s == null) {
      return;
    }

    t = s.trim();
    len = t.length();
    if (len <= 0) {
      return;
    }

    canUseSlash = (File.separatorChar != '/');

    for (i = 0; i < len; i++) {
      ch = t.charAt(i);
      if ((ch == '-') || (canUseSlash && (ch == '/')) || (ch <= 32)) {
        continue;
      }

      for (j = i + 1; j < len; j++) {
        ch = t.charAt(j);
        if ((ch == ':') || (ch == '=')) {
          this.put(t.substring(i, j), t.substring(j + 1).trim());
          return;
        }
      }

      this.put(t.substring(i), Boolean.TRUE.toString());

      return;
    }
  }

  /**
   * Load command line arguments into a map
   * 
   * @param args
   *          the arguments
   */
  public synchronized final void putCommandLine(final String... args) {
    if (args != null) {
      for (final String s : args) {
        this.putCommandLine(s);
      }
    }
  }

  /**
   * Store some information from a map
   * 
   * @param map
   *          the map
   */
  public synchronized final void putMap(final Map<?, ?> map) {
    if (map != null) {
      for (final Map.Entry<?, ?> e : map.entrySet()) {
        this.put(String.valueOf(e.getKey()), e.getValue());
      }
    }
  }

  /**
   * Store some information from a properties set
   * 
   * @param prop
   *          the properties
   */
  public synchronized final void putProperties(final Properties prop) {
    this.putMap(prop);
  }

  /**
   * Configure this configuration from a set of command line parameters
   * 
   * @param args
   *          the command line arguments
   * @throws IOException
   *           if io fails
   */
  public synchronized final void configure(final String[] args)
      throws IOException {
    final Path f;

    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        ConfigurationBuilder.FLAG_HAS_BEEN_CONFIGURED,
        ConfigurationBuilder.FLAG_HAS_BEEN_CONFIGURED, FSM.FLAG_NOTHING);

    this.putCommandLine(args);

    f = this.__get()._get(Configuration.PARAM_PROPERTY_FILE,
        PathParser.INSTANCE, null, false);
    if (f != null) {
      ConfigurationPropertiesInput.INSTANCE.loadPath(this, f);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final Configuration compile() {
    this.fsmFlagsAssert(ConfigurationBuilder.FLAG_DATA_HAS_BEEN_SET,
        FSM.FLAG_NOTHING);
    if (this.m_data == null) {
      throw new IllegalStateException("No information has been set."); //$NON-NLS-1$
    }
    return this.m_data;
  }
}
