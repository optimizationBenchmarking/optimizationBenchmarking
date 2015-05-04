package org.optimizationBenchmarking.utils.tools.impl.latex;

import org.optimizationBenchmarking.utils.io.IFileType;

/** the description of a tool chain component */
abstract class _LaTeXToolChainComponentDesc {

  /** create */
  _LaTeXToolChainComponentDesc() {
    super();
  }

  /**
   * get the component
   *
   * @return the component
   */
  abstract _LaTeXToolChainComponent _getComponent();

  /**
   * Does this component support the given file type as input?
   *
   * @param type
   *          the file type
   * @return {@code true} if the component supports the file type,
   *         {@code false} otherwise
   */
  boolean _supports(final IFileType type) {
    return false;
  }
}
