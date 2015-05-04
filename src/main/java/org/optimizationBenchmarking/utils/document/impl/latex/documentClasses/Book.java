package org.optimizationBenchmarking.utils.document.impl.latex.documentClasses;

import org.optimizationBenchmarking.utils.document.impl.latex.ELaTeXSection;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXDefaultFontPalette;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXDocumentClass;
import org.optimizationBenchmarking.utils.graphics.EPaperSize;
import org.optimizationBenchmarking.utils.math.units.ELength;

/** The {@code book} document class */
public final class Book extends LaTeXDocumentClass {

  /** the {@code book} document class */
  private static final Book INSTANCE = new Book();

  /** create */
  private Book() {
    super("book", //class //$NON-NLS-1$
        null,// parameters
        EPaperSize.LETTER,// paper size
        "unsrt",// bibtex style //$NON-NLS-1$
        345d,// width
        550d,// height
        1,// column count
        345d,// column width
        ELength.PT,// length unit
        LaTeXDefaultFontPalette.getInstance(),// fonts
        ELaTeXSection.CHAPTER,// highest supported section type
        ELaTeXSection.SUBPARAGRAPH// lowest supported section type
        );
  }

  /** {@inheritDoc} */
  @Override
  protected final String getSummaryBegin() {
    return "\\section*{Preface}"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  protected final String getSummaryEnd() {
    return null;
  }

  /**
   * get the globally shared instance of the {@code book} document class
   *
   * @return the {@code book} document class
   */
  public static final Book getInstance() {
    return Book.INSTANCE;
  }
}
