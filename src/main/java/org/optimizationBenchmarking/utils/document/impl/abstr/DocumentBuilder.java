package org.optimizationBenchmarking.utils.document.impl.abstr;

import java.util.logging.Level;

import org.optimizationBenchmarking.utils.document.spec.IDocumentBuilder;
import org.optimizationBenchmarking.utils.tools.impl.abstr.FileProducerBuilder;

/** The class for document builders */
public class DocumentBuilder extends
    FileProducerBuilder<Document, DocumentBuilder> implements
    IDocumentBuilder {

  /** the owning builder */
  protected final DocumentDriver m_owner;

  /**
   * create the tool job builder
   * 
   * @param owner
   *          the owning document builder
   */
  protected DocumentBuilder(final DocumentDriver owner) {
    super();
    this.m_owner = owner;
  }

  @Override
  protected Document doCreate() {
    if ((this.m_logger != null) && (this.m_logger.isLoggable(Level.FINE))) {
      this.m_logger.fine(//
          "Begin creation of document '" + this.m_mainDocumentNameSuggestion + //$NON-NLS-1$
              "' in folder '" + this.m_basePath + '\''); //$NON-NLS-1$
    }
    return this.m_owner.doCreateDocument(this.m_logger, this.m_listener,
        this.m_basePath, this.m_mainDocumentNameSuggestion);
  }
}
