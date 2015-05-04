package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentConfiguration;
import org.optimizationBenchmarking.utils.document.spec.IDocumentBuilder;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** A configuration for LaTeX documents */
public class LaTeXConfiguration extends DocumentConfiguration {
  /** the document class */
  public static final String PARAM_DOCUMENT_CLASS = "documentClass"; //$NON-NLS-1$

  /** the document class */
  private final LaTeXDocumentClass m_documentClass;

  /**
   * create the XHTML 1.0 document configuration
   *
   * @param builder
   *          the builder
   */
  public LaTeXConfiguration(final LaTeXConfigurationBuilder builder) {
    super(builder);

    LaTeXConfiguration._checkDocumentClass(//
        this.m_documentClass = builder.getDocumentClass());
  }

  /**
   * Get the document class
   *
   * @return the document class
   */
  public final LaTeXDocumentClass getDocumentClass() {
    return this.m_documentClass;
  }

  /**
   * check the document class
   *
   * @param documentClass
   *          the document class
   */
  static final void _checkDocumentClass(
      final LaTeXDocumentClass documentClass) {
    if (documentClass == null) {
      throw new IllegalArgumentException("Document class cannot be null.");//$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return HashUtils.combineHashes(super.hashCode(),
        HashUtils.hashCode(this.getDocumentClass()));
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {

    if (o == this) {
      return true;
    }
    if (o instanceof LaTeXConfiguration) {
      if (super.equals(o)) {
        return (EComparison.equals(this.getDocumentClass(),
            ((LaTeXConfiguration) o).getDocumentClass()));
      }
    } else {
      if (o instanceof LaTeXConfigurationBuilder) {
        if (super.equals(o)) {
          return (EComparison.equals(this.getDocumentClass(),
              ((LaTeXConfigurationBuilder) o).getDocumentClass()));
        }
      }
    }

    return false;
  }

  /**
   * Store the ID of a document class to a text output
   *
   * @param documentClass
   *          the document class
   * @param textOut
   *          the text output
   */
  static final void _documentClass(final LaTeXDocumentClass documentClass,
      final ITextOutput textOut) {
    textOut.append(documentClass.getDocumentClass());
    textOut.append('[');
    textOut.append(documentClass.getBibliographyStyle());
    textOut.append(']');
  }

  /** to string */
  @Override
  public void toText(final ITextOutput textOut) {
    super.toText(textOut);
    if (this.m_documentClass != null) {
      textOut.append('@');
      LaTeXConfiguration._documentClass(this.m_documentClass, textOut);
    }
  }

  /**
   * Create a document builder with this object's settings
   *
   * @return the document builder
   */
  @Override
  public IDocumentBuilder createDocumentBuilder() {
    final LaTeXDocumentBuilder builder;

    builder = ((LaTeXDocumentBuilder) (super.createDocumentBuilder()));

    if (this.m_documentClass != null) {
      builder.setDocumentClass(this.m_documentClass);
    }

    return builder;
  }

}
