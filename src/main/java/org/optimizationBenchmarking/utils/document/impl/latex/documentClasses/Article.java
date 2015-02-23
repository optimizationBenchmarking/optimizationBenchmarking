package org.optimizationBenchmarking.utils.document.impl.latex.documentClasses;

import org.optimizationBenchmarking.utils.document.impl.latex.ELaTeXSection;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXDocumentClass;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXDriver;
import org.optimizationBenchmarking.utils.graphics.EPaperSize;
import org.optimizationBenchmarking.utils.math.units.ELength;

/** The {@code article} document class */
public final class Article extends LaTeXDocumentClass {

  /** the {@code article} document class */
  private static final Article INSTANCE = new Article();

  /** create */
  private Article() {
    super("article", //class //$NON-NLS-1$
        null,// parameters
        EPaperSize.LETTER,// paper size
        "unsrt",// bibtex style //$NON-NLS-1$
        345d,// width
        550d,// height
        1,// column count
        345d,// column width
        ELength.PT,// length unit
        LaTeXDriver.defaultFontPalette(),// fonts
        ELaTeXSection.SECTION,// highest supported section type
        ELaTeXSection.SUBPARAGRAPH// lowest supported section type
    );
  }

  /**
   * get the globally shared instance of the {@code article} document class
   * 
   * @return the {@code article} document class
   */
  public static final Article getInstance() {
    return Article.INSTANCE;
  }
}
