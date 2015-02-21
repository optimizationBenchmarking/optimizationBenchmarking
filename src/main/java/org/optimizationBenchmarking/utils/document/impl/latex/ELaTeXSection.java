package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/** The LaTeX sections */
public enum ELaTeXSection {

  /** the part */
  PART,
  /** the chapter */
  CHAPTER,
  /** the section */
  SECTION,
  /** the sub-section */
  SUBSECTION,
  /** the sub-sub-section */
  SUBSUBSECTION,
  /** the paragraph */
  PARAGRAPH,
  /** the sub-paragraph */
  SUBPARAGRAPH;

  /** the instances */
  public static final ArraySetView<ELaTeXSection> INSTANCES = new ArraySetView<>(
      ELaTeXSection.values());
}
