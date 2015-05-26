package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.CodeCaption;

/** the caption of code object in an export document */
final class _ExportCodeCaption extends CodeCaption {

  /**
   * create the code caption
   *
   * @param owner
   *          the owning FSM
   */
  _ExportCodeCaption(final _ExportCode owner) {
    super(owner);
    this.open();
  }
}
