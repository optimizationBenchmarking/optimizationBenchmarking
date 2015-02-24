package org.optimizationBenchmarking.utils.document.impl.latex.documentClasses;

import org.optimizationBenchmarking.utils.document.impl.latex.ELaTeXSection;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXDocument;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXDocumentClass;
import org.optimizationBenchmarking.utils.graphics.EPaperSize;
import org.optimizationBenchmarking.utils.math.units.ELength;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** The {@code LLNCS} document class */
public final class LLNCS extends LaTeXDocumentClass {

  /** the {@code LLNCS} document class */
  private static final LLNCS INSTANCE = new LLNCS();

  /** create */
  private LLNCS() {
    super("llncs", //class //$NON-NLS-1$
        null,// parameters
        EPaperSize.A4,// paper size
        "splncs03",// bibtex style //$NON-NLS-1$
        12.2d,// width
        19.3d,// height
        1,// column count
        12.2d,// column width
        ELength.CM,// length unit
        SigAlternateFontPalette.getInstance(),// fonts
        ELaTeXSection.SECTION,// highest supported section type
        ELaTeXSection.PARAGRAPH// lowest supported section type
    );
  }

  /**
   * get the globally shared instance of the {@code LLNCS} document class
   * 
   * @return the {@code LLNCS} document class
   */
  public static final LLNCS getInstance() {
    return LLNCS.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  protected final void setup(final LaTeXDocument doc,
      final ITextOutput setupPackage) {
    super.setup(doc, setupPackage);
    this.requireResources(doc,
        new String[] { "llncs.cls", "splncs03.bst",//$NON-NLS-1$ //$NON-NLS-2$
            "sprmindx.sty" }, //$NON-NLS-1$ 
        "These files belong to the LaTeX2e package for Lecture Notes in Computer Science (LNCS) of Springer-Verlag.\nLLNCS DOCUMENT CLASS -- version 2.18 (27-Sep-2013). Springer Verlag LaTeX2e support for Lecture Notes in Computer Science.\nBibTeX bibliography style `splncs03'. This was file `titto-lncs-02.bst' produced on Wed Apr 1, 2009. Edited by hand by titto: Maurizio \"Titto\" Patrignani of Dipartimento di Informatica e Automazione Universita' Roma Tre. Copyright 1994-2007 Patrick W Daly."//$NON-NLS-1$ 
    );
    this.requireResources(doc,
        new String[] { "aliascnt.sty" }, //$NON-NLS-1$ 
        "Project: aliascnt. Version: 2009/09/08 v1.3. Copyright (C) 2006, 2009 by Heiko Oberdiek <heiko.oberdiek at googlemail.com>. This work may be distributed and/or modified under the conditions of the LaTeX Project Public License, either version 1.3c of this license or (at your option) any later version. This version of this license is in http://www.latex-project.org/lppl/lppl-1-3c.txt and the latest version of this license is in http://www.latex-project.org/lppl.txt and version 1.3 or later is part of all distributions of LaTeX version 2005/12/01 or later. This work has the LPPL maintenance status \"maintained\". This Current Maintainer of this work is Heiko Oberdiek."//$NON-NLS-1$ 
    );
    this.requireResources(doc,
        new String[] { "remreset.sty" }, //$NON-NLS-1$ 
        "Copyright 1997 David Carlisle. This file may be distributed under the terms of the LPPL. 1997/09/28 David Carlisle."//$NON-NLS-1$ 
    );
  }
}
