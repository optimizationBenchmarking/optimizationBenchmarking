package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.CodeBody;

/** the body of code object in an export document */
final class _ExportCodeBody extends CodeBody {

  /**
   * create the code body
   *
   * @param owner
   *          the owning FSM
   */
  _ExportCodeBody(final _ExportCode owner) {
    super(owner);
    this.open();
  }
}
