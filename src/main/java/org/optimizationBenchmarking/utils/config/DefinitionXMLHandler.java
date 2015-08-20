package org.optimizationBenchmarking.utils.config;

import org.optimizationBenchmarking.utils.io.xml.DelegatingHandler;
import org.optimizationBenchmarking.utils.parsers.InstanceParser;
import org.optimizationBenchmarking.utils.parsers.LooseBooleanParser;
import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** A stack-able XML handler for definition XML data */
public class DefinitionXMLHandler extends DelegatingHandler {

  /** the destination configuration builder */
  private DefinitionBuilder m_dest;

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
  public DefinitionXMLHandler(final DelegatingHandler owner,
      final DefinitionBuilder dest) {
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

    if ((uri == null) || (DefinitionXML.NAMESPACE.equalsIgnoreCase(uri))) {

      name = attributes.getValue(DefinitionXML.ATTRIBUTE_NAME);
      description = attributes
          .getValue(DefinitionXML.ATTRIBUTE_DESCRIPTION);

      switch (localName) {

        case DefinitionXML.ELEMENT_BOOLEAN: {
          this.m_dest.booleanParameter(name, description,//
              DelegatingHandler.getAttribute(attributes,
                  DefinitionXML.NAMESPACE, DefinitionXML.ATTRIBUTE_DEF));
          break;
        }

        case DefinitionXML.ELEMENT_STRING: {
          this.m_dest.stringParameter(name, description,//
              DelegatingHandler.getAttribute(attributes,
                  DefinitionXML.NAMESPACE, DefinitionXML.ATTRIBUTE_DEF));
          break;
        }

        case DefinitionXML.ELEMENT_BYTE: {
          this.m_dest.byteParameter(name, description,//
              DelegatingHandler.getAttribute(attributes,
                  DefinitionXML.NAMESPACE, DefinitionXML.ATTRIBUTE_MIN),//
              DelegatingHandler.getAttribute(attributes,
                  DefinitionXML.NAMESPACE, DefinitionXML.ATTRIBUTE_MAX),//
              DelegatingHandler.getAttribute(attributes,
                  DefinitionXML.NAMESPACE, DefinitionXML.ATTRIBUTE_DEF));
          break;
        }

        case DefinitionXML.ELEMENT_SHORT: {
          this.m_dest.shortParameter(name, description,//
              DelegatingHandler.getAttribute(attributes,
                  DefinitionXML.NAMESPACE, DefinitionXML.ATTRIBUTE_MIN),//
              DelegatingHandler.getAttribute(attributes,
                  DefinitionXML.NAMESPACE, DefinitionXML.ATTRIBUTE_MAX),//
              DelegatingHandler.getAttribute(attributes,
                  DefinitionXML.NAMESPACE, DefinitionXML.ATTRIBUTE_DEF));
          break;
        }

        case DefinitionXML.ELEMENT_INT: {
          this.m_dest.intParameter(name, description,//
              DelegatingHandler.getAttribute(attributes,
                  DefinitionXML.NAMESPACE, DefinitionXML.ATTRIBUTE_MIN),//
              DelegatingHandler.getAttribute(attributes,
                  DefinitionXML.NAMESPACE, DefinitionXML.ATTRIBUTE_MAX),//
              DelegatingHandler.getAttribute(attributes,
                  DefinitionXML.NAMESPACE, DefinitionXML.ATTRIBUTE_DEF));
          break;
        }

        case DefinitionXML.ELEMENT_LONG: {
          this.m_dest.longParameter(name, description,//
              DelegatingHandler.getAttribute(attributes,
                  DefinitionXML.NAMESPACE, DefinitionXML.ATTRIBUTE_MIN),//
              DelegatingHandler.getAttribute(attributes,
                  DefinitionXML.NAMESPACE, DefinitionXML.ATTRIBUTE_MAX),//
              DelegatingHandler.getAttribute(attributes,
                  DefinitionXML.NAMESPACE, DefinitionXML.ATTRIBUTE_DEF));
          break;
        }

        case DefinitionXML.ELEMENT_FLOAT: {
          this.m_dest.floatParameter(name, description,//
              DelegatingHandler.getAttribute(attributes,
                  DefinitionXML.NAMESPACE, DefinitionXML.ATTRIBUTE_MIN),//
              DelegatingHandler.getAttribute(attributes,
                  DefinitionXML.NAMESPACE, DefinitionXML.ATTRIBUTE_MAX),//
              DelegatingHandler.getAttribute(attributes,
                  DefinitionXML.NAMESPACE, DefinitionXML.ATTRIBUTE_DEF));
          break;
        }

        case DefinitionXML.ELEMENT_DOUBLE: {
          this.m_dest.doubleParameter(name, description,//
              DelegatingHandler.getAttribute(attributes,
                  DefinitionXML.NAMESPACE, DefinitionXML.ATTRIBUTE_MIN),//
              DelegatingHandler.getAttribute(attributes,
                  DefinitionXML.NAMESPACE, DefinitionXML.ATTRIBUTE_MAX),//
              DelegatingHandler.getAttribute(attributes,
                  DefinitionXML.NAMESPACE, DefinitionXML.ATTRIBUTE_DEF));
          break;
        }

        case DefinitionXML.ELEMENT_CHOICE: {
          if (this.m_ipb == null) {
            throw new IllegalStateException(
                "The '" + DefinitionXML.ELEMENT_CHOICE + //$NON-NLS-1$
                    "' element can only occur inside a '" //$NON-NLS-1$
                    + DefinitionXML.ELEMENT_INSTANCE
                    + "' element, but was encountered outside.");//$NON-NLS-1$
          }
          this.m_ipb.addChoice(name, description);
          break;
        }

        case DefinitionXML.ELEMENT_INSTANCE: {
          xparser = null;
          clazz = null;
          try {
            clazz = ReflectionUtils.findClass(DelegatingHandler
                .getAttribute(attributes, DefinitionXML.NAMESPACE,
                    DefinitionXML.ATTRIBUTE_CLASS), Object.class);
            parser = DelegatingHandler.getAttribute(attributes,
                DefinitionXML.NAMESPACE, DefinitionXML.ATTRIBUTE_PARSER);
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

          this.m_ipb = this.m_dest.instanceParameter(name, description,
              xparser, DelegatingHandler.getAttribute(attributes,
                  DefinitionXML.NAMESPACE, DefinitionXML.ATTRIBUTE_DEF),
              LooseBooleanParser.INSTANCE.parseBoolean(DelegatingHandler
                  .getAttribute(attributes, DefinitionXML.NAMESPACE,
                      DefinitionXML.ATTRIBUTE_ALLOWS_MORE)));

          break;
        }

        case DefinitionXML.ELEMENT_INHERIT: {
          this.m_dest.inherit(DelegatingHandler.getAttribute(attributes,
              DefinitionXML.NAMESPACE, DefinitionXML.ATTRIBUTE_CLASS));
          break;
        }

        case DefinitionXML.ELEMENT_DEFINITION: {
          this.m_dest.setAllowsMore(//
              LooseBooleanParser.INSTANCE.parseBoolean(DelegatingHandler
                  .getAttribute(attributes, DefinitionXML.NAMESPACE,
                      DefinitionXML.ATTRIBUTE_ALLOWS_MORE)));
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

    if ((uri == null) || (DefinitionXML.NAMESPACE.equalsIgnoreCase(uri))) {
      switch (localName) {
        case DefinitionXML.ELEMENT_DEFINITION: {
          this.close();
          this.m_dest = null;
          break;
        }
        case DefinitionXML.ELEMENT_INSTANCE: {
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
