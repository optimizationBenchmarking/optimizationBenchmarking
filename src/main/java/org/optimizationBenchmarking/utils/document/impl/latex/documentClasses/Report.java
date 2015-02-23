package org.optimizationBenchmarking.utils.document.impl.latex.documentClasses;

import org.optimizationBenchmarking.utils.document.impl.latex.ELaTeXSection;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXDocumentClass;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXDriver;
import org.optimizationBenchmarking.utils.graphics.EPaperSize;
import org.optimizationBenchmarking.utils.math.units.ELength;

/** The {@code report} document class */
public final class Report extends LaTeXDocumentClass {

  /** the {@code report} document class */
  private static final Report INSTANCE = new Report();

  /** create */
  private Report() {
    super("report", //class //$NON-NLS-1$
        null,// parameters
        EPaperSize.LETTER,// paper size
        "unsrt",// bibtex style //$NON-NLS-1$
        345d,// width
        550d,// height
        1,// column count
        345d,// column width
        ELength.PT,// length unit
        LaTeXDriver.defaultFontPalette(),// fonts
        ELaTeXSection.CHAPTER,// highest supported section type
        ELaTeXSection.SUBPARAGRAPH// lowest supported section type
    );
  }

  /**
   * get the globally shared instance of the {@code report} document class
   * 
   * @return the {@code report} document class
   */
  public static final Report getInstance() {
    return Report.INSTANCE;
  }
}
