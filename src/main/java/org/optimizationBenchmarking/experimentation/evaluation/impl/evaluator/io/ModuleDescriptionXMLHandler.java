package org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.io;

import org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data.ModuleDescriptionsBuilder;
import org.optimizationBenchmarking.utils.io.xml.DelegatingHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** a handler for module description xml data */
public final class ModuleDescriptionXMLHandler extends DelegatingHandler {

  /** the destination setup */
  private final ModuleDescriptionsBuilder m_dest;

  /**
   * Create
   *
   * @param owner
   *          the owning handler, or {@code null} if not used
   * @param dest
   *          the configuration instance to load the data into
   */
  public ModuleDescriptionXMLHandler(final DelegatingHandler owner,
      final ModuleDescriptionsBuilder dest) {
    super(owner);
    this.m_dest = dest;
  }

  /** {@inheritDoc} */
  @Override
  protected void doStartElement(final String uri, final String localName,
      final String qName, final Attributes attributes) throws SAXException {
    if ((uri == null)
        || (ModuleDescriptionXML.NAMESPACE.equalsIgnoreCase(uri))) {

      if (ModuleDescriptionXML.ELEMENT_MODULES.equalsIgnoreCase(localName)) {
        return;
      }

      if (ModuleDescriptionXML.ELEMENT_MODULE.equalsIgnoreCase(localName)) {
        this.m_dest.addModule(//
            DelegatingHandler.getAttributeNormalized(//
                attributes, ModuleDescriptionXML.NAMESPACE,//
                ModuleDescriptionXML.ATTRIBUTE_NAME),//
            DelegatingHandler.getAttributeNormalized(//
                attributes, ModuleDescriptionXML.NAMESPACE,//
                ModuleDescriptionXML.ATTRIBUTE_CLASS),//
            DelegatingHandler.getAttributeNormalized(//
                attributes, ModuleDescriptionXML.NAMESPACE,//
                ModuleDescriptionXML.ATTRIBUTE_DESCRIPTION));//
        return;
      }
    }

    super.doStartElement(uri, localName, qName, attributes);
  }

  /** {@inheritDoc} */
  @Override
  protected void doEndElement(final String uri, final String localName,
      final String qName) throws SAXException {

    if ((uri == null)
        || (ModuleDescriptionXML.NAMESPACE.equalsIgnoreCase(uri))) {

      if (ModuleDescriptionXML.ELEMENT_MODULES.equalsIgnoreCase(localName)) {

        this.close();
        return;
      }
    }

    super.doEndElement(uri, localName, qName);
  }
}
