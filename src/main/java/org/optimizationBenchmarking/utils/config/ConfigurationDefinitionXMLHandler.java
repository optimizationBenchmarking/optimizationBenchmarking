package org.optimizationBenchmarking.utils.config;

import org.optimizationBenchmarking.utils.io.xml.DelegatingHandler;
import org.optimizationBenchmarking.utils.parsers.InstanceParser;
import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** A stack-able XMLFileType handler for configuration XMLFileType data */
public class ConfigurationDefinitionXMLHandler extends DelegatingHandler {

  /** the destination configuration builder */
  private ConfigurationDefinitionBuilder m_dest;

  /** the instance parameter builder */
  private InstanceParameterBuilder m_ipb;

  /**
   * Create
   *
   * @param owner
   *          the owning handler, or {@code null} if not used
   * @param dest
   *          the configuration builder to load the data into
   */
  public ConfigurationDefinitionXMLHandler(final DelegatingHandler owner,
      final ConfigurationDefinitionBuilder dest) {
    super(owner);
    this.m_dest = dest;
  }

  /** {@inheritDoc} */
  @Override
  protected final void doStartElement(final String uri,
      final String localName, final String qName,
      final Attributes attributes) throws SAXException {
    final String name, description, parser;
    Parser<?> xparser;
    Class<?> clazz;

    if ((uri == null)
        || (ConfigurationDefinitionXML.NAMESPACE.equalsIgnoreCase(uri))) {

      name = attributes
          .getValue(ConfigurationDefinitionXML.ATTRIBUTE_NAME);
      description = attributes
          .getValue(ConfigurationDefinitionXML.ATTRIBUTE_DESCRIPTION);

      switch (localName) {

        case ConfigurationDefinitionXML.ELEMENT_BOOLEAN: {
          this.m_dest.booleanParameter(name, description,//
              attributes.getValue(//
                  ConfigurationDefinitionXML.ATTRIBUTE_DEF));
          break;
        }

        case ConfigurationDefinitionXML.ELEMENT_STRING: {
          this.m_dest.stringParameter(name, description,//
              attributes.getValue(//
                  ConfigurationDefinitionXML.ATTRIBUTE_DEF));
          break;
        }

        case ConfigurationDefinitionXML.ELEMENT_BYTE: {
          this.m_dest.byteParameter(name, description,//
              attributes.getValue(//
                  ConfigurationDefinitionXML.ATTRIBUTE_MIN),//
              attributes.getValue(//
                  ConfigurationDefinitionXML.ATTRIBUTE_MAX),//
              attributes.getValue(//
                  ConfigurationDefinitionXML.ATTRIBUTE_DEF));
          break;
        }

        case ConfigurationDefinitionXML.ELEMENT_SHORT: {
          this.m_dest.shortParameter(name, description,//
              attributes.getValue(//
                  ConfigurationDefinitionXML.ATTRIBUTE_MIN),//
              attributes.getValue(//
                  ConfigurationDefinitionXML.ATTRIBUTE_MAX),//
              attributes.getValue(//
                  ConfigurationDefinitionXML.ATTRIBUTE_DEF));
          break;
        }

        case ConfigurationDefinitionXML.ELEMENT_INT: {
          this.m_dest.intParameter(name, description,//
              attributes.getValue(//
                  ConfigurationDefinitionXML.ATTRIBUTE_MIN),//
              attributes.getValue(//
                  ConfigurationDefinitionXML.ATTRIBUTE_MAX),//
              attributes.getValue(//
                  ConfigurationDefinitionXML.ATTRIBUTE_DEF));
          break;
        }

        case ConfigurationDefinitionXML.ELEMENT_LONG: {
          this.m_dest.longParameter(name, description,//
              attributes.getValue(//
                  ConfigurationDefinitionXML.ATTRIBUTE_MIN),//
              attributes.getValue(//
                  ConfigurationDefinitionXML.ATTRIBUTE_MAX),//
              attributes.getValue(//
                  ConfigurationDefinitionXML.ATTRIBUTE_DEF));
          break;
        }

        case ConfigurationDefinitionXML.ELEMENT_FLOAT: {
          this.m_dest.floatParameter(name, description,//
              attributes.getValue(//
                  ConfigurationDefinitionXML.ATTRIBUTE_MIN),//
              attributes.getValue(//
                  ConfigurationDefinitionXML.ATTRIBUTE_MAX),//
              attributes.getValue(//
                  ConfigurationDefinitionXML.ATTRIBUTE_DEF));
          break;
        }

        case ConfigurationDefinitionXML.ELEMENT_DOUBLE: {
          this.m_dest.doubleParameter(name, description,//
              attributes.getValue(//
                  ConfigurationDefinitionXML.ATTRIBUTE_MIN),//
              attributes.getValue(//
                  ConfigurationDefinitionXML.ATTRIBUTE_MAX),//
              attributes.getValue(//
                  ConfigurationDefinitionXML.ATTRIBUTE_DEF));
          break;
        }

        case ConfigurationDefinitionXML.ELEMENT_CHOICE: {
          if (this.m_ipb == null) {
            throw new IllegalStateException(
                "The '" + ConfigurationDefinitionXML.ELEMENT_CHOICE + //$NON-NLS-1$
                    "' element can only occur inside a '" //$NON-NLS-1$
                    + ConfigurationDefinitionXML.ELEMENT_DEFINITION
                    + "' element, but was encountered outside.");//$NON-NLS-1$
          }
          this.m_ipb.addChoice(name, description);
          break;
        }

        case ConfigurationDefinitionXML.ELEMENT_INSTANCE: {
          xparser = null;
          clazz = null;
          try {
            clazz = ReflectionUtils.findClass(attributes.getValue(//
                ConfigurationDefinitionXML.ATTRIBUTE_CLASS), Object.class);
            parser = attributes.getValue(//
                ConfigurationDefinitionXML.ATTRIBUTE_PARSER);
            if (parser != null) {
              xparser = ReflectionUtils.getInstanceByName(Parser.class,
                  parser);
            }
          } catch (final Throwable error) {
            throw new IllegalArgumentException(//
                "Error in attributes for instance element.",//$NON-NLS-1$
                error);
          }

          if (xparser == null) {
            xparser = new InstanceParser<>(clazz, null);
          } else {
            if (!(clazz.isAssignableFrom(xparser.getOutputClass()))) {
              throw new IllegalArgumentException(//
                  "Incompatible classes: "//$NON-NLS-1$
                      + clazz + " and " + //$NON-NLS-1$
                      xparser.getOutputClass());
            }
          }

          break;
        }

        case ConfigurationDefinitionXML.ELEMENT_DEFINITION: {
          break; // ignore
        }

        default: {
          throw new IllegalArgumentException("Invalid tag: " + localName); //$NON-NLS-1$
        }
      }
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("incomplete-switch")
  @Override
  protected final void doEndElement(final String uri,
      final String localName, final String qName) throws SAXException {

    if ((uri == null)
        || (ConfigurationDefinitionXML.NAMESPACE.equalsIgnoreCase(uri))) {
      switch (localName) {
        case ConfigurationDefinitionXML.ELEMENT_DEFINITION: {
          this.close();
          this.m_dest = null;
          break;
        }
        case ConfigurationDefinitionXML.ELEMENT_CHOICE: {
          if (this.m_ipb != null) {
            try {
              this.m_ipb.close();
            } finally {
              this.m_ipb = null;
            }
            break;
          }
          throw new IllegalStateException("Element '"//$NON-NLS-1$
              + localName + "' closed without being opened?"); //$NON-NLS-1$
        }

      }
    }
  }
}
