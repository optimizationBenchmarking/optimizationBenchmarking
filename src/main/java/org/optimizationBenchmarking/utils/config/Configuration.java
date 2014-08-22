package org.optimizationBenchmarking.utils.config;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.parsers.BooleanParser;
import org.optimizationBenchmarking.utils.parsers.BoundedByteParser;
import org.optimizationBenchmarking.utils.parsers.BoundedDoubleParser;
import org.optimizationBenchmarking.utils.parsers.BoundedFloatParser;
import org.optimizationBenchmarking.utils.parsers.BoundedIntParser;
import org.optimizationBenchmarking.utils.parsers.BoundedLongParser;
import org.optimizationBenchmarking.utils.parsers.BoundedShortParser;
import org.optimizationBenchmarking.utils.parsers.ClassParser;
import org.optimizationBenchmarking.utils.parsers.FileParser;
import org.optimizationBenchmarking.utils.parsers.ListParser;
import org.optimizationBenchmarking.utils.parsers.LoggerParser;
import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.parsers.SetParser;
import org.optimizationBenchmarking.utils.parsers.StringParser;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * <p>
 * A class representing command line parameters and configurations. This
 * class provides high-level access to command line parameters and allows
 * you to read different values from them.
 * </p>
 */
public final class Configuration extends _ConfigRoot {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the parameter identifying a configuration file: {@value} */
  public static final String PARAM_PROPERTY_FILE = "configFile"; //$NON-NLS-1$

  /** the configuration data */
  final _ConfigMap m_data;

  /** create */
  public Configuration() {
    this(null);
  }

  /**
   * Create a configuration containing the same entries as another map.
   * These entries are linked, i.e., any change to one of the entries in
   * this map will propagate to the copied map, any other copies of that
   * map, and any map that map was copied from (if they contain the same
   * entries).
   * 
   * @param copy
   *          the configuration to copy
   */
  public Configuration(final Configuration copy) {
    super("configuration"); //$NON-NLS-1$
    if (copy != null) {
      synchronized (copy.m_data) {
        this.m_data = ((_ConfigMap) (copy.m_data.clone()));
      }
    } else {
      this.m_data = new _ConfigMap();
    }
  }

  /**
   * Get an instance of the type {@code T} stored under the given
   * {@code key}. If such an instance does not exist, {@code default} will
   * be returned. <em>Any</em> value that is returned will always first be
   * piped through the {@code parser}'s
   * {@link org.optimizationBenchmarking.utils.parsers.Parser#validate(Object)
   * validate} method. <em>Any</em> returned object which is an instance of
   * {@link org.optimizationBenchmarking.utils.config.Configurable} will be
   * {@link org.optimizationBenchmarking.utils.config.Configurable#configure(Configuration)
   * configured} with {@code this} configuration before being returned for
   * the <em>first time</em> (and after being
   * {@link org.optimizationBenchmarking.utils.parsers.Parser#validate(Object)
   * validated}). Validation and configuration are also applied to the
   * {@code default} value. It is guaranteed that this method will
   * <em>always</em> return the same object instance for a specific key.
   * When this method is called for the first time with a given key, this
   * instance is created. If nothing is stored under {@code key} right now,
   * an immutable entry will be created. This method is thread safe.
   * 
   * @param key
   *          the key (case insensitive)
   * @param _default
   *          the default value to be returned (and stored) if no data is
   *          stored under the key
   * @param parser
   *          the parser used to convert whatever object is currently
   *          stored to the return value and to validate the return value
   * @return the result
   * @param <T>
   *          the type of the result
   * @throws RuntimeException
   *           if validation or parsing fails
   * @throws IllegalStateException
   *           if the key has already been
   *           {@link #get(String, Parser, Object) accessed} but with a
   *           different type
   */
  public final <T> T get(final String key, final Parser<T> parser,
      final T _default) {
    return this.__get(key, parser, _default, true);
  }

  /**
   * The internal variant of {@link #get(String, Parser, Object)}, with the
   * difference that the parameter {@code createIfNotExists} decides
   * whether an entry should be created if none exists for the key. If no
   * entry exists for a key and {@code createIfNotExists} is {@code true},
   * this method will behave exactly like
   * {@link #get(String, Parser, Object)}, i.e., an immutable entry will be
   * created. If {@code createIfNotExists} is {@code false}, it will
   * instead return {@code null} and not modify the data.
   * 
   * @param key
   *          the key (case insensitive)
   * @param _default
   *          the default value to be returned (and stored) if no data is
   *          stored under the key
   * @param parser
   *          the parser used to convert whatever object is currently
   *          stored to the return value and to validate the return value
   * @param createIfNotExists
   *          whether non-existing entries should be created and loaded
   *          with {@code _default} ({@code true}) or not and {@code null}
   *          should be returned ({@code false})
   * @return the result
   * @param <T>
   *          the type of the result
   * @throws RuntimeException
   *           if validation or parsing fails
   * @throws IllegalStateException
   *           if the key has already been
   *           {@link #get(String, Parser, Object) accessed} but with a
   *           different type
   */
  private final <T> T __get(final String key, final Parser<T> parser,
      final T _default, final boolean createIfNotExists) {
    final _ConfigMapEntry entry;
    final Class<T> clazz;
    final int state;
    T retVal;
    Object value;

    synchronized (this.m_data) {
      entry = ((_ConfigMapEntry) (this.m_data.getEntry(key,
          createIfNotExists)));
      if (entry == null) {
        return null;
      }
      state = entry.m_state;
      if (state <= 0) {
        entry.m_state = 1;
      }
    }

    clazz = parser.getOutputClass();
    retVal = null;

    try {

      if (state <= 0) {
        try {
          value = entry.getValue();
          if (value == null) {
            retVal = _default;
            parser.validate(retVal);
          } else {
            if (clazz.isInstance(value) && (!(value instanceof String))) {
              retVal = clazz.cast(value);
              parser.validate(retVal);
            } else {
              retVal = parser.parseObject(value);
            }
          }

          if (retVal instanceof Configurable) {
            ((Configurable) retVal).configure(this);
          }
        } finally {
          synchronized (entry) {
            entry.setValue(retVal);
            entry.m_state = 2;
            entry.notifyAll();
          }
        }

        return retVal;
      }

      aquire: for (;;) {
        synchronized (entry) {
          if (entry.m_state >= 2) {
            value = entry.getValue();
            break aquire;
          }
          entry.wait();
        }
      }

      if (clazz.isInstance(value)) {
        retVal = clazz.cast(entry.getValue());
        parser.validate(retVal);
        return retVal;
      }
    } catch (final RuntimeException re) {
      throw re;
    } catch (final Throwable tt) {
      throw new RuntimeException(tt);
    }

    throw new IllegalStateException(
        "The configuration value under key '" + key + //$NON-NLS-1$
            "' has already been accessed as " + //$NON-NLS-1$
            ((value != null) ? ("an instance of " + //$NON-NLS-1$
            TextUtils.className(value.getClass()))
                : "null") //$NON-NLS-1$
            + " and thus cannot be accessed as instance of " + //$NON-NLS-1$
            TextUtils.className(clazz));
  }

  /**
   * Get a parameter which is a class.
   * 
   * @param key
   *          the key
   * @param defClass
   *          the default class
   * @param baseClass
   *          the base class used to check type consistency
   * @return the class
   * @param <T>
   *          the base type
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public final <T> Class<T> getClass(final String key,
      final Class<T> baseClass, final Class<? extends T> defClass) {
    return this.get(key, new ClassParser(baseClass), defClass);
  }

  /**
   * <p>
   * Get a parameter value which is an instance of a class identified by
   * the parameter. It is assumed that the class identified by the
   * parameter has a default parameter-less constructor.
   * </p>
   * <p>
   * If a non-{@code null} value is returned, it will automatically be
   * checked if it is an instance of
   * {@link org.optimizationBenchmarking.utils.config.Configurable
   * Configurable} first. If so, its
   * {@link org.optimizationBenchmarking.utils.config.Configurable#configure(Configuration)
   * configure} method will be invoked with this
   * {@link org.optimizationBenchmarking.utils.config.Configuration
   * configuration} as parameter. This also holds if it is decided to
   * return {@code defInstance}.
   * </p>
   * 
   * @param key
   *          the key
   * @param defInstance
   *          the default instance, or {@code null}
   * @param baseClass
   *          the base class of the instance to return, used to check type
   *          consistency
   * @return the instance
   * @param <T>
   *          the type of object to return
   */
  public final <T> T getInstance(final String key,
      final Class<T> baseClass, final T defInstance) {
    return this.get(key, new _InstanceParser<>(baseClass), defInstance);
  }

  /**
   * <p>
   * Get the value of a public static constant. Constants will not be
   * configured automatically. They are static and should be considered as
   * immutable anyway.
   * </p>
   * 
   * @param key
   *          the key
   * @param defInstance
   *          the default instance, or {@code null}
   * @param owningClass
   *          the class which should contain the constant
   * @param baseClass
   *          the base class of the constant to return, used to check type
   *          consistency
   * @return the constant
   * @param <T>
   *          the type of object to return
   */
  public final <T> T getConstant(final String key,
      final Class<?> owningClass, final Class<T> baseClass,
      final T defInstance) {
    return this.get(key, new _ConstantParser<>(owningClass, baseClass),
        defInstance);
  }

  /**
   * Get a file parameter
   * 
   * @param key
   *          the key
   * @param def
   *          the default file
   * @return the canonical version thereof
   */
  public final File getFile(final String key, final File def) {
    return this.get(key, FileParser.INSTANCE, def);
  }

  /**
   * Get a 8 bit signed integer (byte) parameter.
   * 
   * @param key
   *          the key identifying the parameter
   * @param min
   *          the minimum allowed value, a
   *          {@link java.lang.IllegalArgumentException
   *          IllegalArgumentException} will be thrown if this constraint
   *          is violated
   * @param max
   *          the maximum allowed value, a
   *          {@link java.lang.IllegalArgumentException
   *          IllegalArgumentException} will be thrown if this constraint
   *          is violated
   * @param def
   *          the default value
   * @return the parameter value, a byte parsed from the configuration data
   */
  public final byte getByte(final String key, final byte min,
      final byte max, final byte def) {
    return this.get(key, new BoundedByteParser(min, max),//
        Byte.valueOf(def)).byteValue();
  }

  /**
   * Get a 16 bit signed integer (short) parameter.
   * 
   * @param key
   *          the key identifying the parameter
   * @param min
   *          the minimum allowed value, a
   *          {@link java.lang.IllegalArgumentException
   *          IllegalArgumentException} will be thrown if this constraint
   *          is violated
   * @param max
   *          the maximum allowed value, a
   *          {@link java.lang.IllegalArgumentException
   *          IllegalArgumentException} will be thrown if this constraint
   *          is violated
   * @param def
   *          the default value
   * @return the parameter value, a short parsed from the configuration
   *         data
   */
  public final short getShort(final String key, final short min,
      final short max, final short def) {
    return this.get(key, new BoundedShortParser(min, max),//
        Short.valueOf(def)).shortValue();
  }

  /**
   * Get a 32 bit signed integer (int) parameter.
   * 
   * @param key
   *          the key identifying the parameter
   * @param min
   *          the minimum allowed value, a
   *          {@link java.lang.IllegalArgumentException
   *          IllegalArgumentException} will be thrown if this constraint
   *          is violated
   * @param max
   *          the maximum allowed value, a
   *          {@link java.lang.IllegalArgumentException
   *          IllegalArgumentException} will be thrown if this constraint
   *          is violated
   * @param def
   *          the default value
   * @return the parameter value, a int parsed from the configuration data
   */
  public final int getInt(final String key, final int min, final int max,
      final int def) {
    return this.get(key, new BoundedIntParser(min, max),//
        Integer.valueOf(def)).intValue();
  }

  /**
   * Get a 64 bit signed integer (long) parameter.
   * 
   * @param key
   *          the key identifying the parameter
   * @param min
   *          the minimum allowed value, a
   *          {@link java.lang.IllegalArgumentException
   *          IllegalArgumentException} will be thrown if this constraint
   *          is violated
   * @param max
   *          the maximum allowed value, a
   *          {@link java.lang.IllegalArgumentException
   *          IllegalArgumentException} will be thrown if this constraint
   *          is violated
   * @param def
   *          the default value
   * @return the parameter value, a long parsed from the configuration data
   */
  public final long getLong(final String key, final long min,
      final long max, final long def) {
    return this.get(key, new BoundedLongParser(min, max),//
        Long.valueOf(def)).longValue();
  }

  /**
   * Get a single-precision (32-bit) floating point number (float)
   * parameter.
   * 
   * @param key
   *          the key identifying the parameter
   * @param min
   *          the minimum allowed value, a
   *          {@link java.lang.IllegalArgumentException
   *          IllegalArgumentException} will be thrown if this constraint
   *          is violated
   * @param max
   *          the maximum allowed value, a
   *          {@link java.lang.IllegalArgumentException
   *          IllegalArgumentException} will be thrown if this constraint
   *          is violated
   * @param def
   *          the default value
   * @return the parameter value, a float parsed from the configuration
   *         data
   */
  public final float getFloat(final String key, final float min,
      final float max, final float def) {
    return this.get(key, new BoundedFloatParser(min, max),//
        Float.valueOf(def)).floatValue();
  }

  /**
   * Get a double-precision (64-bit) floating point number (double)
   * parameter.
   * 
   * @param key
   *          the key identifying the parameter
   * @param min
   *          the minimum allowed value, a
   *          {@link java.lang.IllegalArgumentException
   *          IllegalArgumentException} will be thrown if this constraint
   *          is violated
   * @param max
   *          the maximum allowed value, a
   *          {@link java.lang.IllegalArgumentException
   *          IllegalArgumentException} will be thrown if this constraint
   *          is violated
   * @param def
   *          the default value
   * @return the parameter value, a double parsed from the configuration
   *         data
   */
  public final double getDouble(final String key, final double min,
      final double max, final double def) {
    return this.get(key, new BoundedDoubleParser(min, max),//
        Double.valueOf(def)).doubleValue();
  }

  /**
   * Get a Boolean parameter.
   * 
   * @param key
   *          the key identifying the parameter
   * @param def
   *          the default value
   * @return the parameter value, a Boolean parsed from the configuration
   *         data
   */
  public final boolean getBoolean(final String key, final boolean def) {
    return this.get(key, BooleanParser.INSTANCE, Boolean.valueOf(def))
        .booleanValue();
  }

  /**
   * Get a parameter which is a string.
   * 
   * @param key
   *          the key
   * @param def
   *          the default value
   * @return the string
   */
  public final String getString(final String key, final String def) {
    return this.get(key, StringParser.INSTANCE, def);
  }

  /**
   * Get a parameter which is a list of strings.
   * 
   * @param key
   *          the key
   * @param def
   *          the default value, or {@code null} for empty lists
   * @return the string
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public final ArrayListView<String> getStringList(final String key,
      final ArrayListView<String> def) {
    return this
        .get(key, ListParser.STRING_LIST_PARSER,
            (ArraySetView) ((def != null) ? def
                : ArraySetView.EMPTY_SET_VIEW));
  }

  /**
   * Get a parameter which is a list of strings.
   * 
   * @param key
   *          the key
   * @param def
   *          the default value, or {@code null} for empty sets
   * @return the string
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public final ArrayListView<String> getFileSet(final String key,
      final ArraySetView<File> def) {
    return this
        .get(key, SetParser.FILE_SET_PARSER,
            (ArraySetView) ((def != null) ? def
                : ArraySetView.EMPTY_SET_VIEW));
  }

  /**
   * get a logger
   * 
   * @param key
   *          the key
   * @param def
   *          the default logger
   * @return the log value
   */
  public final Logger getLogger(final String key, final Logger def) {
    return this.get(key, LoggerParser.INSTANCE, def);
  }

  // / functions to store configuration values

  // / functions to store configuration values

  /**
   * Put a value
   * 
   * @param key
   *          the key
   * @param value
   *          the value
   */
  public final void put(final String key, final Object value) {
    final _ConfigMapEntry entry;
    final int state;

    synchronized (this.m_data) {
      entry = ((_ConfigMapEntry) (this.m_data.getEntry(key, true)));
      state = entry.m_state;
      if (state <= 0) {
        entry.setValue(value);
      }
    }

    if (state > 0) {
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
  public final void putCommandLine(final String s) {
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
  public final void putCommandLine(final String... args) {
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
  public final void putMap(final Map<?, ?> map) {
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
  public final void putProperties(final Properties prop) {
    this.putMap(prop);
  }

  // /**
  // * Store some information from a properties set
  // *
  // * @param file
  // * the properties file
  // * @throws IOException
  // * if loading the file fails
  // */
  // public final void putPropertiesFromFile(final File file) throws
  // IOException {
  // Properties pr;
  //
  // pr = new Properties();
  // try (FileReader fr = new FileReader(new
  // CanonicalizeFile(file).call())) {
  // pr.load(fr);
  // }
  //
  // this.putProperties(pr);
  // }

  /**
   * Configure this configuration from a set of command line parameters
   * 
   * @param args
   *          the command line arguments
   * @throws IOException
   *           if io fails
   */
  public final void configure(final String[] args) throws IOException {
    final File f;

    this.putCommandLine(args);

    f = this.__get(Configuration.PARAM_PROPERTY_FILE, FileParser.INSTANCE,
        null, false);
    if (f != null) {
      ConfigurationPropertiesIO.INSTANCE.loadFile(this, f);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void printConfiguration(final PrintStream ps) {
    synchronized (this.m_data) {
      for (final Entry<String, Object> e : this.m_data.entries()) {
        Configurable.printKey(e.getKey(), ps);
        Configurable.printlnObject(e.getValue(), ps);
      }
    }
  }
}
