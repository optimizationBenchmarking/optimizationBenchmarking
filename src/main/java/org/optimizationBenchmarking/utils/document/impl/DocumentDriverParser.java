package org.optimizationBenchmarking.utils.document.impl;

import java.util.LinkedHashSet;

import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXDriver;
import org.optimizationBenchmarking.utils.document.impl.xhtml10.XHTML10Driver;
import org.optimizationBenchmarking.utils.document.spec.IDocumentDriver;
import org.optimizationBenchmarking.utils.parsers.InstanceParser;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;

/** A parser for document drivers. */
public final class DocumentDriverParser extends
    InstanceParser<IDocumentDriver> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  DocumentDriverParser() {
    super(IDocumentDriver.class, DocumentDriverParser.__prefixes());
  }

  /**
   * get the prefixes
   * 
   * @return the path prefixes
   */
  private static final String[] __prefixes() {
    final LinkedHashSet<String> paths;

    paths = new LinkedHashSet<>();
    ReflectionUtils.addPackageOfClassToPrefixList(
        DocumentDriverParser.class, paths);
    ReflectionUtils.addPackageOfClassToPrefixList(//
        LaTeXDriver.class, paths);
    ReflectionUtils.addPackageOfClassToPrefixList(//
        XHTML10Driver.class, paths);
    return paths.toArray(new String[paths.size()]);
  }

  /** {@inheritDoc} */
  @Override
  public final IDocumentDriver parseString(final String string)
      throws Exception {
    if ("xhtml".equalsIgnoreCase(string) || //$NON-NLS-1$
        "xhtml 1.0".equalsIgnoreCase(string)) { //$NON-NLS-1$
      return XHTML10Driver.getInstance();
    }
    if ("latex".equalsIgnoreCase(string) || //$NON-NLS-1$
        "tex".equalsIgnoreCase(string) || //$NON-NLS-1$
        "xetex".equalsIgnoreCase(string) || //$NON-NLS-1$
        "pdflatex".equalsIgnoreCase(string)) { //$NON-NLS-1$
      return LaTeXDriver.getInstance();
    }

    return super.parseString(string);
  }

  /**
   * Get the singleton instance of this parser
   * 
   * @return the document driver parser
   */
  public static final DocumentDriverParser getInstance() {
    return __DocumentDriverParserLoader.INSTANCE;
  }

  /** the instance loader */
  private static final class __DocumentDriverParserLoader {
    /** the instance */
    static final DocumentDriverParser INSTANCE = new DocumentDriverParser();
  }
}
