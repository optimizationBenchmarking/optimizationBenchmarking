package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.Document;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentBuilder;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentDriver;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;

/**
 * The LaTeX document builder
 */
public final class LaTeXDocumentBuilder extends DocumentBuilder {

  /**
   * the LaTeX document builder
   * 
   * @param driver
   *          the driver to use
   */
  LaTeXDocumentBuilder(final LaTeXDriver driver) {
    super(driver);
  }

  /** {@inheritDoc} */
  @Override
  protected LaTeXConfigurationBuilder createConfigurationBuilder(
      final DocumentDriver owner) {
    return new LaTeXConfigurationBuilder((LaTeXDriver) owner);
  }

  /**
   * Get the document class
   * 
   * @return the document class
   */
  public final LaTeXDocumentClass getDocumentClass() {
    return ((LaTeXConfigurationBuilder) (this.getConfigurationBuilder()))
        .getDocumentClass();
  }

  /**
   * Set the document class
   * 
   * @param documentClass
   *          the document class
   */
  public final void setDocumentClass(final LaTeXDocumentClass documentClass) {
    ((LaTeXConfigurationBuilder) (this.getConfigurationBuilder()))
        .setDocumentClass(documentClass);
  }

  /** {@inheritDoc} */
  @Override
  protected final StyleSet createStyleSet() {
    return new StyleSet(this.getDocumentClass().getFontPalette(),//
        this.getColorModel().getDefaultPalette(),//
        this.getGraphicDriver().getStrokePalette());
  }

  /** {@inheritDoc} */
  @Override
  protected final Document doCreateDocument() {
    return new _LaTeXDocument(this);
  }

}
