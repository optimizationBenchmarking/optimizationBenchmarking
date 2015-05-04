package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import org.optimizationBenchmarking.experimentation.evaluation.system.impl.EvaluationModuleParser;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationModule;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.config.ConfigurationBuilder;
import org.optimizationBenchmarking.utils.config.ConfigurationXML;
import org.optimizationBenchmarking.utils.config.ConfigurationXMLHandler;
import org.optimizationBenchmarking.utils.io.xml.DelegatingHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** a handler for evaluation configuration xml data */
final class _EvaluationXMLHandler extends DelegatingHandler {

  /** nothing is going on */
  private static final int STATE_NOTHING = 0;

  /** the evaluation is going on */
  private static final int STATE_IN_EVALUATION = (_EvaluationXMLHandler.STATE_NOTHING + 1);

  /** the module is being configured */
  private static final int STATE_IN_MODULE = (_EvaluationXMLHandler.STATE_IN_EVALUATION + 1);

  /** the XMLFileType processing is done */
  private static final int STATE_DONE = (_EvaluationXMLHandler.STATE_IN_MODULE + 1);

  /** the destination setup */
  private final _EvaluationSetup m_dest;

  /** the state */
  private int m_state;

  /** the next module to load */
  private String m_module;

  /** the current configuration builder */
  private ConfigurationBuilder m_configBuilder;

  /**
   * Create
   *
   * @param owner
   *          the owning handler, or {@code null} if not used
   * @param dest
   *          the configuration instance to load the data into
   */
  _EvaluationXMLHandler(final DelegatingHandler owner,
      final _EvaluationSetup dest) {
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

        switch (this.m_state) {
          case STATE_NOTHING: {
            this.m_state = _EvaluationXMLHandler.STATE_IN_EVALUATION;
            this.m_configBuilder = null;
            this.m_module = null;
            return;
          }
          case STATE_IN_EVALUATION: {
            throw new IllegalStateException(//
                "Cannot nest evaluation process into other evaluation process."); //$NON-NLS-1$
          }
          case STATE_IN_MODULE: {
            throw new IllegalStateException(//
                "Cannot nest evaluation process into module."); //$NON-NLS-1$
          }
          case STATE_DONE: {
            throw new IllegalStateException(//
                "Evaluation loading already completed, cannot open another evaluation process."); //$NON-NLS-1$
          }
          default: {
            throw new IllegalStateException(//
                "Inconsistent evaluation XMLFileType."); //$NON-NLS-1$
          }
        }
      }

      if (EvaluationXML.ELEMENT_MODULE.equalsIgnoreCase(localName)) {
        switch (this.m_state) {
          case STATE_NOTHING: {
            throw new IllegalStateException(//
                "Module setup cannot be the root element of evaluation process."); //$NON-NLS-1$
          }
          case STATE_IN_EVALUATION: {
            this.m_module = DelegatingHandler.getAttributeNormalized(
                attributes, EvaluationXML.NAMESPACE,
                EvaluationXML.ATTRIBUTE_CLASS);
            if (this.m_module == null) {
              throw new IllegalStateException(//
                  "Module definition must have class attribute.");//$NON-NLS-1$
            }
            this.m_state = _EvaluationXMLHandler.STATE_IN_MODULE;
            this.m_configBuilder = null;
            return;
          }
          case STATE_IN_MODULE: {
            throw new IllegalStateException(//
                "Cannot nest modules."); //$NON-NLS-1$
          }
          case STATE_DONE: {
            throw new IllegalStateException(//
                "Evaluation loading already completed, cannot open another module."); //$NON-NLS-1$
          }
          default: {
            throw new IllegalStateException(//
                "Inconsistent evaluation XMLFileType."); //$NON-NLS-1$
          }
        }
      }
    }

    if ((uri == null)
        || (ConfigurationXML.CONFIG_XML.getNamespace()
            .equalsIgnoreCase(uri))) {
      switch (this.m_state) {
        case STATE_NOTHING: {
          throw new IllegalStateException(//
              "Cannot put a configuration outside an evaluation process or module."); //$NON-NLS-1$
        }
        case STATE_IN_EVALUATION:
        case STATE_IN_MODULE: {
          this.m_configBuilder = new ConfigurationBuilder();
          this.m_configBuilder.setOwner(this.m_dest
              ._getConfiguration(null));
          new ConfigurationXMLHandler(this, this.m_configBuilder);
          return;
        }
        case STATE_DONE: {
          throw new IllegalStateException(//
              "Evaluation loading already completed, cannot open another configuration."); //$NON-NLS-1$
        }
        default: {
          throw new IllegalStateException(//
              "Inconsistent evaluation XMLFileType."); //$NON-NLS-1$
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doEndElement(final String uri, final String localName,
      final String qName) throws SAXException {
    IEvaluationModule module;
    Configuration config;

    if ((uri == null) || (EvaluationXML.NAMESPACE.equalsIgnoreCase(uri))) {

      if (EvaluationXML.ELEMENT_EVALUATION.equalsIgnoreCase(localName)) {

        switch (this.m_state) {
          case STATE_NOTHING: {
            throw new IllegalStateException(//
                "Cannot end evaluation process which has not yet been opened."); //$NON-NLS-1$
          }
          case STATE_IN_EVALUATION: {
            this.m_state = _EvaluationXMLHandler.STATE_DONE;
            this.m_configBuilder = null;
            this.m_module = null;
            this.close();
            return;
          }
          case STATE_IN_MODULE: {
            throw new IllegalStateException(//
                "Cannot end evaluation process inside module."); //$NON-NLS-1$
          }
          case STATE_DONE: {
            throw new IllegalStateException(//
                "Evaluation loading already completed, cannot close another evaluation process."); //$NON-NLS-1$
          }
          default: {
            throw new IllegalStateException(//
                "Inconsistent evaluation XMLFileType."); //$NON-NLS-1$
          }
        }
      }

      if (EvaluationXML.ELEMENT_MODULE.equalsIgnoreCase(localName)) {

        switch (this.m_state) {
          case STATE_NOTHING: {
            throw new IllegalStateException(//
                "Cannot end module if no evaluation process (let alone a module) has yet been opened."); //$NON-NLS-1$
          }
          case STATE_IN_EVALUATION: {
            throw new IllegalStateException(//
                "Cannot end module before opening one."); //$NON-NLS-1$
          }

          case STATE_IN_MODULE: {
            try {
              module = EvaluationModuleParser.getInstance().parseString(
                  this.m_module);
            } catch (final Exception roe) {
              throw new IllegalArgumentException(
                  (("Could not load module '" + this.m_module) + //$NON-NLS-1$
                      "' defined in evaluation configuration XMLFileType file."), //$NON-NLS-1$
                      roe);
            }
            this.m_module = null;

            if (this.m_configBuilder != null) {
              config = this.m_configBuilder.getResult();
              this.m_configBuilder = null;
            } else {
              config = null;
            }

            this.m_dest._addModule(module, config);
            this.m_state = _EvaluationXMLHandler.STATE_IN_EVALUATION;
            return;
          }

          case STATE_DONE: {
            throw new IllegalStateException(//
                "Evaluation loading already completed, cannot close another module."); //$NON-NLS-1$
          }
          default: {
            throw new IllegalStateException(//
                "Inconsistent evaluation XMLFileType."); //$NON-NLS-1$
          }
        }
      }

    }
  }

  /** {@inheritDoc} */
  @Override
  protected void onEndDelegate(final DelegatingHandler delegate) {//
    if (this.m_state == _EvaluationXMLHandler.STATE_IN_EVALUATION) {
      if (this.m_configBuilder != null) {
        this.m_dest
        ._setBaseConfiguration(this.m_configBuilder.getResult());
        this.m_configBuilder = null;
        return;
      }
    }
    super.onEndDelegate(delegate);
  }
}
