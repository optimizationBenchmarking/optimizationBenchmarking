package org.optimizationBenchmarking.utils.document.impl.latex.documentClasses;

import org.optimizationBenchmarking.utils.document.impl.latex.ELaTeXSection;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXDocument;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXDocumentClass;
import org.optimizationBenchmarking.utils.graphics.EPaperSize;
import org.optimizationBenchmarking.utils.math.units.ELength;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** The {@code sig-alternate} document class */
public final class SigAlternate extends LaTeXDocumentClass {

  /** the {@code sig-alternate} document class */
  private static final SigAlternate INSTANCE = new SigAlternate();

  /** create */
  private SigAlternate() {
    super("sig-alternate", //class //$NON-NLS-1$
        null,// parameters
        EPaperSize.LETTER,// paper size
        "abbrv",// bibtex style //$NON-NLS-1$
        504d,// width
        666d,// height
        2,// column count
        240d,// column width
        ELength.PT,// length unit
        SigAlternateFontPalette.getInstance(),// fonts
        ELaTeXSection.SECTION,// highest supported section type
        ELaTeXSection.PARAGRAPH// lowest supported section type
    );
  }

  /**
   * get the globally shared instance of the {@code sig-alternate} document
   * class
   * 
   * @return the {@code sig-alternate} document class
   */
  public static final SigAlternate getInstance() {
    return SigAlternate.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  protected final void setup(final LaTeXDocument doc,
      final ITextOutput setupPackage) {
    super.setup(doc, setupPackage);

    this.requireResources(doc,
        new String[] { "sig-alternate.cls" }, //$NON-NLS-1$ 
        "SIG-ALTERNATE DOCUMENT STYLE\nG.K.M. Tobin August-October 1999.\nAdapted from ARTICLE document style by Ken Traub, Olin Shivers, also using elements of esub2acm.cls.\nHEAVILY MODIFIED, SUBSEQUENTLY, BY GERRY MURRAY 2000.\nARTICLE DOCUMENT STYLE - Released 16 March 1988 for LaTeX version 2.09.\nCopyright (C) 1988 by Leslie Lamport.\nDocument Class 'sig-alternate' <23rd. May '12>.  Modified by G.K.M. Tobin/Gerry Murray.\nBased in part upon document Style `acmconf' <22 May 89>. Hacked 4/91 by shivers@cs.cmu.edu, 4/93 by theobald@cs.mcgill.ca.\nExcerpts were taken from (Journal Style) 'esub2acm.cls'.\nBugs/comments/suggestions/technicalities to Gerry Murray - murray@hq.acm.org.\nQuestions on the style, SIGS policies, etc. to Adrienne Griscti griscti@acm.org.\nIf you have LaTeX-specific questions please consider checking out the SIG FAQ FILE first.\nAll other questions can go to Adrienne Griscti at griscti@acm.org\nTECHNICAL SUPPORT\nShould you need technical help working with your LaTeX class files, please direct your query to:\nacmtexsupport@aptaracorp.com\nAll email queries will be responded to within 24 hours."//$NON-NLS-1$ 
    );

    LaTeXDocumentClass.endLine(setupPackage);
    LaTeXDocumentClass
        .commentLine(//
            "We need to protect \\guillemotleft and \\guillemotright, otherwise we get ugly boxes.",//$NON-NLS-1$
            setupPackage);
    setupPackage.append(//
        "\\gdef\\guillemotleft{\\mbox{\\textless\\hspace{-0.4em}\\textless}}");//$NON-NLS-1$
    LaTeXDocumentClass.endLine(setupPackage);
    setupPackage.append(//
        "\\gdef\\guillemotright{\\mbox{\\textgreater\\hspace{-0.4em}\\textgreater}}");//$NON-NLS-1$
    LaTeXDocumentClass.endLine(setupPackage);
  }
}
