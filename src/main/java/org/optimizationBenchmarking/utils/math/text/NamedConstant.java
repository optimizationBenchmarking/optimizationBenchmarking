package org.optimizationBenchmarking.utils.math.text;

import org.optimizationBenchmarking.utils.math.BasicNumber;

/**
 * A named constant is, well, a constant which can render itself to a maths
 * context.
 */
public abstract class NamedConstant extends BasicNumber implements
    IMathRenderable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** Create the named constant */
  protected NamedConstant() {
    super();
  }

}
