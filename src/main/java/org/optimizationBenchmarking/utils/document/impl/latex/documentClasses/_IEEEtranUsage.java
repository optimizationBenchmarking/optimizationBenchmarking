package org.optimizationBenchmarking.utils.document.impl.latex.documentClasses;

import org.optimizationBenchmarking.utils.document.impl.latex.ELaTeXSection;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXDocument;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXDocumentClass;
import org.optimizationBenchmarking.utils.graphics.EPaperSize;
import org.optimizationBenchmarking.utils.math.units.ELength;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** The {@code IEEEtran} document class */
abstract class _IEEEtranUsage extends LaTeXDocumentClass {

  /**
   * create
   * 
   * @param parameters
   *          the parameter
   * @param height
   *          the height
   */
  _IEEEtranUsage(final String[] parameters, final double height) {
    super("IEEEtran", //class //$NON-NLS-1$
        parameters,// parameters
        EPaperSize.LETTER,// paper size
        "IEEEtran",// bibtex style //$NON-NLS-1$
        516d,// width
        height,// height
        2,// column count
        252d,// column width
        ELength.PT,// length unit
        IEEEFontPalette.getIEEEFontPalette(),// fonts
        ELaTeXSection.SECTION,// highest supported section type
        ELaTeXSection.PARAGRAPH// lowest supported section type
    );
  }

  /** {@inheritDoc} */
  @Override
  protected final void setup(final LaTeXDocument doc,
      final ITextOutput setupPackage) {
    super.setup(doc, setupPackage);
    this.requireResources(doc,
        new String[] { "IEEEtran.cls", "IEEEtran.bst" }, //$NON-NLS-1$ //$NON-NLS-2$
        "IEEEtran.cls 2014/09/17 version V1.8a: This is the IEEEtran LaTeX class for authors of the Institute of Electrical and Electronics Engineers (IEEE) Transactions journals and conferences.\nSupport sites: http://www.michaelshell.org/tex/ieeetran/ http://www.ctan.org/tex-archive/macros/latex/contrib/IEEEtran/ and http://www.ieee.org/,\nBased on the original 1993 IEEEtran.cls, but with many bug fixes and enhancements (from both JVH and MDS) over the 1996/7 version.\nContributors: Gerry Murray (1993), Silvano Balemi (1993), Jon Dixon (1996), Peter N\u00fcchter (1996), Juergen von Hagen (2000), and Michael Shell (2001-2014).\nCopyright (c) 1993-2000 by Gerry Murray, Silvano Balemi, Jon Dixon, Peter N\u00fcchter, Juergen von Hagen and Copyright (c) 2001-2014 by Michael Shell.\nCurrent maintainer (V1.3 to V1.8a): Michael Shell See: http://www.michaelshell.org/ for current contact information.\nSpecial thanks to Peter Wilson (CUA) and Donald Arseneau for allowing the inclusion of the \\@ifmtarg command from their ifmtarg LaTeX package. \nLegal Notice: This code is offered as-is without any warranty either expressed or implied; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE! User assumes all risk. In no event shall IEEE or any contributor to this code be liable for any damages or losses, including, but not limited to, incidental, consequential, or any other damages, resulting from the use or misuse of any information contained here.\nAll comments are the opinions of their respective authors and are not necessarily endorsed by the IEEE.\nThis work is distributed under the LaTeX Project Public License (LPPL) ( http://www.latex-project.org/ ) version 1.3, and may be freely used, distributed and modified. A copy of the LPPL, version 1.3, is included in the base LaTeX documentation of all distributions of LaTeX released 2003/12/01 or later. Retain all contribution notices and credits. Modified files should be clearly indicated as such, including renaming them and changing author support contact information. \nFile list of work: IEEEtran.cls, IEEEtran_HOWTO.pdf, bare_adv.tex, bare_conf.tex, bare_jrnl.tex, bare_conf_compsoc.tex, bare_jrnl_compsoc.tex.\nMajor changes to the user interface should be indicated by an increase in the version numbers. If a version is a beta, it will be indicated with a BETA suffix, i.e., 1.4 BETA. Small changes can be indicated by appending letters to the version such as \"IEEEtran_v14a.cls\". In all cases, \\Providesclass, any \\typeout messages to the user, \\IEEEtransversionmajor and \\IEEEtransversionminor must reflect the correct version information. The changes should also be documented via source comments.\nIEEEtran.bst: BibTeX Bibliography Style file for IEEE Journals and Conferences (unsorted) Version 1.13 (2008/09/30).\nCopyright (c) 2003-2008 Michael Shell.\nOriginal starting code base and algorithms obtained from the output of Patrick W. Daly's makebst package as well as from prior versions of IEEE BibTeX styles:\n1. Howard Trickey and Oren Patashnik's ieeetr.bst (1985/1988)\n2. Silvano Balemi and Richard H. Roy's IEEEbib.bst (1993).\nSupport sites: http://www.michaelshell.org/tex/ieeetran/ http://www.ctan.org/tex-archive/macros/latex/contrib/IEEEtran/ and/or http://www.ieee.org/.\nFor use with BibTeX version 0.99a or later.\nThis is a numerical citation style.\nLegal Notice: This code is offered as-is without any warranty either expressed or implied; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE! User assumes all risk. In no event shall IEEE or any contributor to this code be liable for any damages or losses, including, but not limited to, incidental, consequential, or any other damages, resulting from the use or misuse of any information contained here.\nAll comments are the opinions of their respective authors and are not necessarily endorsed by the IEEE.\nThis work is distributed under the LaTeX Project Public License (LPPL) ( http://www.latex-project.org/ ) version 1.3, and may be freely used, distributed and modified. A copy of the LPPL, version 1.3, is included in the base LaTeX documentation of all distributions of LaTeX released 2003/12/01 or later. Retain all contribution notices and credits. Modified files should be clearly indicated as such, including renaming them and changing author support contact information. \nFile list of work: IEEEabrv.bib, IEEEfull.bib, IEEEexample.bib, IEEEtran.bst, IEEEtranS.bst, IEEEtranSA.bst, IEEEtranN.bst, IEEEtranSN.bst, IEEEtran_bst_HOWTO.pdf."//$NON-NLS-1$ 
    );
  }

}
