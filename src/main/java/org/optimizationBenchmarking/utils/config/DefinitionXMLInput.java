package org.optimizationBenchmarking.utils.config;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.optimizationBenchmarking.utils.collections.cache.MapCache;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.XMLInputTool;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** the configuration definition xml input */
public final class DefinitionXMLInput extends
    XMLInputTool<DefinitionBuilder> {

  /** The map with the references to the cached definitions */
  private final MapCache<Class<?>, Definition> m_cache;

  /** create */
  DefinitionXMLInput() {
    super();
    this.m_cache = new MapCache<>();
  }

  /** {@inheritDoc} */
  @Override
  protected final void configureSAXParserFactory(final SAXParserFactory spf)
      throws Throwable {
    Object rec;
    SchemaFactory sf;
    Schema schema;

    rec = null;
    schema = null;
    try {
      sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      schema = sf.newSchema(//
          DefinitionXML.CONFIG_DEFINITION_XML.getSchemaSource());
    } catch (final Throwable a) {
      rec = ErrorUtils.aggregateError(a, rec);
    } finally {
      sf = null;
    }

    try {
      spf.setNamespaceAware(true);
      if (schema != null) {
        spf.setValidating(false);
        spf.setSchema(schema);
      } else {
        spf.setValidating(false);
      }
    } catch (final Throwable b) {
      rec = ErrorUtils.aggregateError(b, rec);
    }

    if (rec != null) {
      RethrowMode.AS_IO_EXCEPTION
          .rethrow(//
              "Error while loading the XML Schema for ConfigurationDefinitionXML.", //$NON-NLS-1$
              true, rec);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final DefinitionXMLHandler wrapDestination(
      final DefinitionBuilder dataDestination, final IOJob job) {
    return new DefinitionXMLHandler(null, dataDestination);
  }

  /**
   * Load the configuration definition for a given class.
   *
   * @param clazz
   *          the class
   * @param logger
   *          a logger to use, or {@code null}
   * @return the corresponding definition
   */
  public final Definition forClass(final Class<?> clazz,
      final Logger logger) {
    final String name, msg;
    Definition def;

    this.checkCanUse();

    def = this.m_cache.get(clazz);
    if (def != null) {
      return def;
    }

    name = (clazz.getSimpleName() + "-params.xml"); //$NON-NLS-1$
    try (final DefinitionBuilder builder = new DefinitionBuilder()) {
      try {
        this.use().setLogger(logger).setDestination(builder)
            .addResource(clazz, name).create().call();
      } catch (final IOException ioe) {
        msg = "Could not load configuration resource '" + //$NON-NLS-1$
            name + "' for class " + TextUtils.className(clazz);//$NON-NLS-1$
        if ((logger != null) && (logger.isLoggable(Level.SEVERE))) {
          logger.log(Level.SEVERE, msg, ioe);
        }
        throw new IllegalStateException(msg, ioe);
      }
      def = builder.getResult();
      return this.m_cache.put(clazz, def, true);
    }
  }

  /**
   * Load the configuration definition for a given class.
   *
   * @param clazz
   *          the class
   * @param logger
   *          a logger to use, or {@code null}
   * @return the corresponding definition
   */
  public final Definition forClass(final String clazz, final Logger logger) {
    final Class<?> claz;
    final String msg;

    try {
      claz = ReflectionUtils.findClass(clazz, Object.class);
    } catch (LinkageError | ClassNotFoundException | ClassCastException error) {
      msg = "Cannot find class '" //$NON-NLS-1$
          + clazz + '\'' + '.';
      if ((logger != null) && (logger.isLoggable(Level.SEVERE))) {
        logger.log(Level.SEVERE, msg, error);
      }
      throw new IllegalArgumentException(msg, error);
    }
    return this.forClass(claz, logger);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Configuration Definition XML Input"; //$NON-NLS-1$
  }

  /**
   * get the instance of the {@link DefinitionXMLInput}
   *
   * @return the instance of the {@link DefinitionXMLInput}
   */
  public static final DefinitionXMLInput getInstance() {
    return __ConfigurationDefinitionXMLInputLoader.INSTANCE;
  }

  /** the loader */
  private static final class __ConfigurationDefinitionXMLInputLoader {
    /** the configuration xml */
    static final DefinitionXMLInput INSTANCE = new DefinitionXMLInput();
  }
}
