package org.optimizationBenchmarking.utils.document.impl.latex;

import java.util.LinkedHashSet;

import org.optimizationBenchmarking.utils.document.impl.latex.documentClasses.Article;
import org.optimizationBenchmarking.utils.document.impl.latex.documentClasses.Book;
import org.optimizationBenchmarking.utils.document.impl.latex.documentClasses.IEEEtran;
import org.optimizationBenchmarking.utils.document.impl.latex.documentClasses.LLNCS;
import org.optimizationBenchmarking.utils.document.impl.latex.documentClasses.Report;
import org.optimizationBenchmarking.utils.document.impl.latex.documentClasses.SigAlternate;
import org.optimizationBenchmarking.utils.parsers.InstanceParser;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;

/** A parser for LaTeX document classes */
public final class LaTeXDocumentClassParser extends
    InstanceParser<LaTeXDocumentClass> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  LaTeXDocumentClassParser() {
    super(LaTeXDocumentClass.class, LaTeXDocumentClassParser.__prefixes());
  }

  /**
   * get the prefixes
   *
   * @return the path prefixes
   */
  private static final String[] __prefixes() {
    final LinkedHashSet<String> paths;

    paths = new LinkedHashSet<>();

    ReflectionUtils.addPackageOfClassToPrefixList(Article.class, paths);
    ReflectionUtils.addPackageOfClassToPrefixList(
        LaTeXDocumentClass.class, paths);
    ReflectionUtils.addPackageOfClassToPrefixList(
        LaTeXDocumentClassParser.class, paths);
    return paths.toArray(new String[paths.size()]);
  }

  /** {@inheritDoc} */
  @Override
  public final LaTeXDocumentClass parseString(final String string) {
    if ("article".equalsIgnoreCase(string)) { //$NON-NLS-1$
      return Article.getInstance();
    }
    if ("book".equalsIgnoreCase(string)) { //$NON-NLS-1$
      return Book.getInstance();
    }
    if ("report".equalsIgnoreCase(string)) { //$NON-NLS-1$
      return Report.getInstance();
    }
    if ("ieeetran".equalsIgnoreCase(string) || //$NON-NLS-1$
        "ieee tran".equalsIgnoreCase(string) || //$NON-NLS-1$
        "ieee transactions".equalsIgnoreCase(string)) { //$NON-NLS-1$
      return IEEEtran.getInstance();
    }
    if ("llncs".equalsIgnoreCase(string) || //$NON-NLS-1$
        "lncs".equalsIgnoreCase(string)) { //$NON-NLS-1$
      return LLNCS.getInstance();
    }
    if ("sig-alternate".equalsIgnoreCase(string) || //$NON-NLS-1$
        "sigalternate".equalsIgnoreCase(string)) { //$NON-NLS-1$
      return SigAlternate.getInstance();
    }

    return super.parseString(string);
  }

  /**
   * Get the singleton instance of this parser
   *
   * @return the latex document class parser
   */
  public static final LaTeXDocumentClassParser getInstance() {
    return __LaTeXDocumentClassParserLoader.INSTANCE;
  }

  /** the instance loader */
  private static final class __LaTeXDocumentClassParserLoader {
    /** the instance */
    static final LaTeXDocumentClassParser INSTANCE = new LaTeXDocumentClassParser();
  }
}