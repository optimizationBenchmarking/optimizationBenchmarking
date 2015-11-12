package org.optimizationBenchmarking.utils.config;

import java.io.Serializable;
import java.nio.file.Path;
import java.security.AccessController;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.parsers.BoundedLooseByteParser;
import org.optimizationBenchmarking.utils.parsers.BoundedLooseDoubleParser;
import org.optimizationBenchmarking.utils.parsers.BoundedLooseFloatParser;
import org.optimizationBenchmarking.utils.parsers.BoundedLooseIntParser;
import org.optimizationBenchmarking.utils.parsers.BoundedLooseLongParser;
import org.optimizationBenchmarking.utils.parsers.BoundedLooseShortParser;
import org.optimizationBenchmarking.utils.parsers.ClassParser;
import org.optimizationBenchmarking.utils.parsers.InstanceParser;
import org.optimizationBenchmarking.utils.parsers.ListParser;
import org.optimizationBenchmarking.utils.parsers.LoggerParser;
import org.optimizationBenchmarking.utils.parsers.LooseBooleanParser;
import org.optimizationBenchmarking.utils.parsers.LooseStringParser;
import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.parsers.PathListParser;
import org.optimizationBenchmarking.utils.parsers.PathParser;
import org.optimizationBenchmarking.utils.text.ITextable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * <p>
 * A class for representing parameters settings for an application or
 * application module. This class can, for instance, provide high-level
 * access to command line parameters and allow you to read different typed
 * values from them. There are three basic concepts in the design of
 * {@link #Configuration}: Typed parameters, default values, and
 * hierarchical setups.
 * </p>
 * <p>
 * Parameters may be loaded into a configuration as strings from the
 * command line. This means that parameter are, essentially, strings.
 * However, when you request the value of a parameter for the first time
 * with one of the various getter methods (such as
 * {@link #getBoolean(String, boolean)}, {@link #getPath(String, Path)}, or
 * {@link #getDouble(String, double, double, double)}, for instance), the
 * value and type of the parameter will be fixed. A parameter value once
 * accessed via {@link #getDouble(String, double, double, double)} will
 * forever remain only accessible as {@code double} value.
 * </p>
 * <p>
 * This introduces some concept of static, strict typing into the original
 * {@link java.lang.String}-based parameter world. This is achieved by
 * making use of the extensive
 * {@link org.optimizationBenchmarking.utils.parsers Parser API}. Since
 * this API allows for the creation of parsers with limited ranges, such as
 * {@link org.optimizationBenchmarking.utils.parsers.BoundedLooseLongParser}
 * , which throws an exception if the parsed {@code long} is outside of a
 * specific range, you can even make sure that certain parameters are
 * within certain ranges.
 * </p>
 * <p>
 * Whenever you request a parameter value, you need to provide a default
 * result in case that the parameter is undefined. If that happens (and no
 * hierarchically superior configuration exists), the default value will be
 * returned and stored as the parameter's value in the configuration (to
 * ensure that future accesses to the parameter will always yield the same
 * result).
 * </p>
 * <p>
 * Configurations can be nested hierarchically. If you request a parameter
 * value for a parameter which is not defined in a configuration, we check
 * if this configuration is subject of an &quot;owning&quot; configuration.
 * If so, this configuration is checked for the parameter recursively. If
 * the parameter's value is defined somewhere in the hierarchy, it will
 * then be accessed (which pins down its type if that did not happen yet)
 * and returned. If its value cannot be found, then the default value will
 * be stored in the configuration you called the getter method of.
 * </p>
 */
public final class Configuration implements Serializable, ITextable {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the parameter identifying a configuration property file: {@value} */
  public static final String PARAM_PROPERTY_FILE = "configFile"; //$NON-NLS-1$

  /** the parameter identifying a configuration XML file: {@value} */
  public static final String PARAM_XML_FILE = "configXML"; //$NON-NLS-1$

  /** the path parameter */
  public static final String PARAM_PATH = "path"; //$NON-NLS-1$

  /** the logger parameter */
  public static final String PARAM_LOGGER = "logger"; //$NON-NLS-1$

  /** the synchronizer */
  private static final Object SYNCH = new Object();
  /** the root configuration */
  private static Configuration s_root = null;

  /** the owner */
  Configuration m_owner;

  /** the configuration data */
  final _ConfigMap m_data;

  /** the path parser */
  PathParser m_pathParser;

  /** create a configuration within an owning scope */
  Configuration() {
    super();
    this.m_data = new _ConfigMap();
    this.m_pathParser = PathParser.INSTANCE;
  }

  /**
   * Get the base path against which the paths in this configuration are
   * resolved.
   *
   * @return the base path against which the paths in this configuration
   *         are resolved
   */
  public final Path getBasePath() {
    return this.m_pathParser.getBasePath();
  }

  /**
   * Get an instance of the type {@code T} stored under the given
   * {@code key}. If such an instance does not exist, {@code default} will
   * be returned. <em>Any</em> value that is returned will always first be
   * piped through the {@code parser}'s
   * {@link org.optimizationBenchmarking.utils.parsers.Parser#validate(Object)
   * validate} method. <em>Any</em> returned object which is an instance of
   * {@link org.optimizationBenchmarking.utils.config.IConfigurable} will
   * be
   * {@link org.optimizationBenchmarking.utils.config.IConfigurable#configure(Configuration)
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
    return this._get(key, parser, _default, true);
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
  final <T> T _get(final String key, final Parser<T> parser,
      final T _default, final boolean createIfNotExists) {
    _ConfigMapEntry entry;
    Class<T> clazz;
    boolean isLocked, needsCheck;
    T retVal;
    Object value;

    try {
      breakToCheckRetVal: {
        needsCheck = true;
        synchronized (this.m_data) {
          for (Configuration cfg = this; cfg != null; cfg = cfg.m_owner) {
            synchronized (cfg.m_data) {
              entry = ((_ConfigMapEntry) (cfg.m_data.getEntry(key,
                  false)));
              if (entry != null) {
                isLocked = entry.m_isLocked;
                entry.m_isLocked = true;

                value = entry.getValue();
                clazz = parser.getOutputClass();
                if (isLocked) {
                  retVal = clazz.cast(value);
                } else {
                  retVal = null;
                  try {
                    if (value == null) {
                      retVal = _default;
                    } else {
                      if (value instanceof String) {
                        retVal = parser.parseString((String) value);
                        needsCheck = false;
                      } else {
                        if (clazz.isInstance(value)) {
                          retVal = clazz.cast(value);
                        } else {
                          retVal = parser.parseObject(value);
                          needsCheck = false;
                        }
                      }
                    }
                  } finally {
                    entry.setValue(retVal);
                  }
                }

                break breakToCheckRetVal;
              }
            }
          }

          if (createIfNotExists) {
            entry = ((_ConfigMapEntry) (this.m_data.getEntry(key, true)));
            entry.m_isLocked = true;
            entry.setValue(_default);
            retVal = _default;
            break breakToCheckRetVal;
          }

          return null;
        }
      }

      if (needsCheck) {
        parser.parseObject(retVal);
      }
      return retVal;
    } catch (final Throwable tt) {
      RethrowMode.AS_ILLEGAL_STATE_EXCEPTION.rethrow((((((((//
      "Error while trying to obtain configuration key '" //$NON-NLS-1$
          + key) + "\' with parser '") //$NON-NLS-1$
          + parser) + "' and default '") //$NON-NLS-1$
          + _default) + '\'') + '.'), true, tt);
    }
    return null;
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
   * {@link org.optimizationBenchmarking.utils.config.IConfigurable
   * IConfigurable} first. If so, its
   * {@link org.optimizationBenchmarking.utils.config.IConfigurable#configure(Configuration)
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
    return this.get(key, new InstanceParser<>(baseClass, null),
        defInstance);
  }

  /**
   * Get a path (file or directory) parameter
   *
   * @param key
   *          the key
   * @param def
   *          the default path
   * @return the canonical path
   */
  public final Path getPath(final String key, final Path def) {
    return this.get(key, this.m_pathParser, def);
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
    return this.get(key, new BoundedLooseByteParser(min, max), //
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
    return this.get(key, new BoundedLooseShortParser(min, max), //
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
    return this.get(key, new BoundedLooseIntParser(min, max), //
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
    return this.get(key, new BoundedLooseLongParser(min, max), //
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
    return this.get(key, new BoundedLooseFloatParser(min, max), //
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
    return this.get(key, new BoundedLooseDoubleParser(min, max), //
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
    return this.get(key, LooseBooleanParser.INSTANCE, Boolean.valueOf(def))
        .booleanValue();
  }

  /**
   * Get a parameter which is a string.
   *
   * @param key
   *          the key
   * @param def
   *          the default value
   * @return the strings
   */
  public final String getString(final String key, final String def) {
    return this.get(key, LooseStringParser.INSTANCE, def);
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
    return this.get(key, //
        ListParser.STRING_LIST_PARSER, (ArraySetView) ((def != null) ? def
            : ArraySetView.EMPTY_SET_VIEW));
  }

  /**
   * Get a parameter which is a list of paths (to directories or files)
   *
   * @param key
   *          the key
   * @param def
   *          the default value, or {@code null} for empty sets
   * @return the paths
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public final ArrayListView<Path> getPathList(final String key,
      final ArrayListView<Path> def) {
    final PathParser parser;

    parser = this.m_pathParser;

    return this.get(key,
        ((parser.getBasePath() == null)//
            ? PathListParser.INSTANCE//
            : new PathListParser(parser)), //
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

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final Configuration conf;
    if (o == this) {
      return true;
    }
    if (o instanceof Configuration) {
      conf = ((Configuration) o);
      synchronized (this.m_data) {
        synchronized (conf.m_data) {
          if (!(this.m_data.equals(conf.m_data))) {
            return false;
          }
        }
      }

      return EComparison.equals(this.m_owner, conf.m_owner);
    }

    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    final int h;
    synchronized (this.m_data) {
      h = this.m_data.hashCode();
    }
    return HashUtils.combineHashes(HashUtils.hashCode(this.m_owner), h);
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    synchronized (this.m_data) {
      textOut.append(this.m_data);
    }
  }

  /**
   * Check whether this configuration is empty. This only checks the
   * current configuration, not its parent configurations.
   *
   * @return {@code true} if this configuration is empty, {@code false} if
   *         it contains at least one parameter
   */
  public final boolean isEmpty() {
    synchronized (this.m_data) {
      return this.m_data.isEmpty();
    }
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    synchronized (this.m_data) {
      return this.m_data.toString();
    }
  }

  /**
   * Get the globally used logger
   *
   * @return the logger
   */
  public static final Logger getGlobalLogger() {
    Logger logger;
    Configuration config;

    logger = _LoggerFilter.GLOBAL;
    config = Configuration.getRoot();
    if (config != null) {
      return config.getLogger(Configuration.PARAM_LOGGER, logger);
    }
    return logger;
  }

  /**
   * Get the root configuration
   *
   * @return the root configuration
   */
  public static final Configuration getRoot() {
    synchronized (Configuration.SYNCH) {
      if (Configuration.s_root == null) {
        Configuration.setup(null);
      }
      return Configuration.s_root;
    }
  }

  /**
   * Setup the root configuration from command line arguments and the
   * system environment. You must call this method at most once, and if you
   * call it, it must be the first call in your {@code main} routine.
   *
   * @param args
   *          the command line arguments, which were passed to the
   *          {@code main} routine
   */
  @SuppressWarnings("unused")
  public static final void setup(final String[] args) {
    final _ConfigurationLoader cb;

    synchronized (Configuration.SYNCH) {
      if (Configuration.s_root == null) {
        cb = new _ConfigurationLoader(args);
        try {
          Configuration.s_root = AccessController.doPrivileged(cb);
        } catch (final Throwable t) {
          Configuration.s_root = cb.run();
        }

        return;
      }
    }
    throw new IllegalStateException(//
        "The root configuration can only be setup at most once."); //$NON-NLS-1$
  }

  /**
   * Get an empty configuration.
   *
   * @return the empty configuration
   */
  public static final Configuration createEmpty() {
    return new Configuration();
  }
}
