package org.optimizationBenchmarking.utils.math.functions.compound;

import org.optimizationBenchmarking.utils.math.text.AbstractParameterRenderer;

/** the base class for compound parameter renderers */
abstract class _CompoundParameterRendererBase extends
    AbstractParameterRenderer {

  /** if braces are not needed */
  boolean m_bracesNotNeeded;

  /** create */
  _CompoundParameterRendererBase() {
    super();
  }
}
