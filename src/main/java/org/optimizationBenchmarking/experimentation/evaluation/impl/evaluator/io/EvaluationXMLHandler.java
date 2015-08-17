package org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.io;

import org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data.EvaluationModulesBuilder;
import org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator.data.ModuleEntryBuilder;
import org.optimizationBenchmarking.utils.config.ConfigurationBuilder;
import org.optimizationBenchmarking.utils.config.ConfigurationXML;
import org.optimizationBenchmarking.utils.config.ConfigurationXMLHandler;
import org.optimizationBenchmarking.utils.io.xml.DelegatingHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** a handler for evaluation configuration xml data */
public final class EvaluationXMLHandler extends DelegatingHandler {

  /** the destination setup */
  private final EvaluationModulesBuilder m_dest;

  /** the entry builder */
  private ModuleEntryBuilder m_entry;

  /** the configuration builder */
  private ConfigurationBuilder m_config;

  /**
   * Create
   *
   * @param owner
   *          the owning handler, or {@code null} if not used
   * @param dest
   *          the configuration instance to load the data into
   */
  public EvaluationXMLHandler(final DelegatingHandler owner,
      final EvaluationModulesBuilder dest) {
    super(owner);
    this.m_dest = dest;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unused")
  @Override
  protected void doStartElement(final String uri, final String localName,
      final String qName, final Attributes attributes) throws SAXException {
    if ((uri == null) || (EvaluationXML.NAMESPACE.equalsIgnoreCase(uri))) {

      if (EvaluationXML.ELEMENT_EVALUATION.equalsIgnoreCase(localName)) {
        if (this.m_entry != null) {
          throw new IllegalStateException(//
              "Cannot start a new evaluation inside an evaluation module."); //$NON-NLS-1$
        }
        if (this.m_config != null) {
          throw new IllegalStateException(//
              "Cannot start a new evaluation inside a configuration."); //$NON-NLS-1$
        }
        return;
      }

      if (EvaluationXML.ELEMENT_MODULE.equalsIgnoreCase(localName)) {
        if (this.m_entry != null) {
          throw new IllegalStateException(//
              "Cannot start a new evaluation module inside an evaluation module."); //$NON-NLS-1$
        }
        if (this.m_config != null) {
          throw new IllegalStateException(//
              "Cannot start a new evaluation module inside a configuration."); //$NON-NLS-1$
        }
        this.m_entry = this.m_dest.addModule();
        this.m_entry.setModule(DelegatingHandler.getAttributeNormalized(
            attributes, EvaluationXML.NAMESPACE,
            EvaluationXML.ATTRIBUTE_CLASS));
        return;
      }
    }

    if ((uri == null)
        || (ConfigurationXML.CONFIG_XML.getNamespace()
            .equalsIgnoreCase(uri))) {

      if (ConfigurationXML.ELEMENT_CONFIGURATION_ROOT
          .equalsIgnoreCase(localName)) {
        if (this.m_config != null) {
          throw new IllegalStateException(//
              "Cannot start a new configuration inside a configuration."); //$NON-NLS-1$
        }

        if (this.m_entry == null) {
          this.m_config = this.m_dest.setConfiguration();
        } else {
          this.m_config = this.m_entry.setConfiguration();
        }

        new ConfigurationXMLHandler(this, this.m_config);
        return;
      }
    }

    super.doStartElement(uri, localName, qName, attributes);
  }

  /** {@inheritDoc} */
  @Override
  protected void doEndElement(final String uri, final String localName,
      final String qName) throws SAXException {

    if ((uri == null) || (EvaluationXML.NAMESPACE.equalsIgnoreCase(uri))) {

      if (EvaluationXML.ELEMENT_EVALUATION.equalsIgnoreCase(localName)) {
        if (this.m_entry != null) {
          throw new IllegalStateException(//
              "Cannot end evaluation process inside module."); //$NON-NLS-1$
        }
        if (this.m_config != null) {
          throw new IllegalStateException(//
              "Cannot end evaluation process inside configuration."); //$NON-NLS-1$
        }

        this.close();
        return;
      }

      if (EvaluationXML.ELEMENT_MODULE.equalsIgnoreCase(localName)) {

        if (this.m_entry == null) {
          throw new IllegalStateException(//
              "Cannot end module before opening it."); //$NON-NLS-1$
        }
        if (this.m_config != null) {
          throw new IllegalStateException(//
              "Cannot end module inside configuration."); //$NON-NLS-1$
        }
        try {
          this.m_entry.close();
        } finally {
          this.m_entry = null;
        }
        return;
      }
    }

    super.doEndElement(uri, localName, qName);
  }

  /** {@inheritDoc} */
  @Override
  protected final void onEndDelegate(final DelegatingHandler delegate) {//
    if (delegate instanceof ConfigurationXMLHandler) {
      if (this.m_config == null) {
        throw new IllegalStateException(//
            "Cannot close configuration builder if no configuration builder is open."); //$NON-NLS-1$
      }
      try {
        this.m_config.close();
      } finally {
        this.m_config = null;
      }
    }
    super.onEndDelegate(delegate);
  }
}
