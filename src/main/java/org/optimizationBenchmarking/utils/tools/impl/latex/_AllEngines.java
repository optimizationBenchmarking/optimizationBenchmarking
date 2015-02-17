package org.optimizationBenchmarking.utils.tools.impl.latex;

import java.util.Iterator;

import org.optimizationBenchmarking.utils.collections.iterators.BasicIterator;

/** the iterable which provides a list of all main engines */
final class _AllEngines implements Iterable<_LaTeXToolChainComponentDesc> {

  /** all the engines */
  static final _AllEngines ALL_ENGINES = new _AllEngines();

  /** create */
  private _AllEngines() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final Iterator<_LaTeXToolChainComponentDesc> iterator() {
    return new __Iterator();
  }

  /** the internal iterator implementation */
  private static final class __Iterator extends
      BasicIterator<_LaTeXToolChainComponentDesc> {

    /** the index */
    private int m_index;

    /** create */
    __Iterator() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final boolean hasNext() {
      return (this.m_index <= 5);
    }

    /** {@inheritDoc} */
    @Override
    public final _LaTeXToolChainComponentDesc next() {
      switch (this.m_index++) {
        case 0: {
          return _PdfLaTeX._getDescription();
        }
        case 1: {
          return _LaTeX._getDescription();
        }
        case 2: {
          return _PdfTeXAsPdfLaTeX._getDescription();
        }
        case 3: {
          return _PdfTeXAsLaTeX._getDescription();
        }
        case 4: {
          return _LaTeXAsPdfLaTeX._getDescription();
        }
        case 5: {
          return _PdfLaTeXAsLaTeX._getDescription();
        }
        default: {
          return super.next(); // no more elements
        }
      }
    }

  }
}
