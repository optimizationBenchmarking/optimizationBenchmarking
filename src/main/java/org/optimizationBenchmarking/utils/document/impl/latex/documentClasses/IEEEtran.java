package org.optimizationBenchmarking.utils.document.impl.latex.documentClasses;

import org.optimizationBenchmarking.utils.document.impl.latex.ELaTeXSection;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXDocument;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXDocumentClass;
import org.optimizationBenchmarking.utils.graphics.EPaperSize;
import org.optimizationBenchmarking.utils.math.units.ELength;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** The {@code IEEEtran} document class */
public final class IEEEtran extends LaTeXDocumentClass {

  /** the {@code IEEEtran} document class */
  private static final IEEEtran INSTANCE = new IEEEtran();

  /** create */
  private IEEEtran() {
    super("IEEEtran", //class //$NON-NLS-1$
        null,// parameters
        EPaperSize.LETTER,// paper size
        "IEEEtran",// bibtex style //$NON-NLS-1$
        516d,// width
        696d,// height
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
        "Contributors: Gerry Murray (1993), Silvano Balemi (1993), Jon Dixon (1996), Peter N\u00fcchter (1996), Juergen von Hagen (2000), and Michael Shell (2001-2014) Copyright (c) 1993-2000 by Gerry Murray, Silvano Balemi, Jon Dixon, Peter N\u00fcchter, Juergen von Hagen and Copyright (c) 2001-2014 by Michael Shell. Current maintainer (V1.3 to V1.8a): Michael Shell See: http://www.michaelshell.org/ for current contact information. Special thanks to Peter Wilson (CUA) and Donald Arseneau for allowing the inclusion of the \\@ifmtarg command from their ifmtarg LaTeX package. Legal Notice: This code is offered as-is without any warranty either expressed or implied; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE! User assumes all risk. In no event shall IEEE or any contributor to this code be liable for any damages or losses, including, but not limited to, incidental, consequential, or any other damages, resulting from the use or misuse of any information contained here. All comments are the opinions of their respective authors and are not necessarily endorsed by the IEEE. This work is distributed under the LaTeX Project Public License (LPPL) ( http://www.latex-project.org/ ) version 1.3, and may be freely used, distributed and modified. A copy of the LPPL, version 1.3, is included in the base LaTeX documentation of all distributions of LaTeX released 2003/12/01 or later. Retain all contribution notices and credits. Modified files should be clearly indicated as such, including renaming them and changing author support contact information."//$NON-NLS-1$ 
    );
  }

  /**
   * get the globally shared instance of the {@code IEEEtran} document
   * class
   * 
   * @return the {@code IEEEtran} document class
   */
  public static final IEEEtran getInstance() {
    return IEEEtran.INSTANCE;
  }
}
