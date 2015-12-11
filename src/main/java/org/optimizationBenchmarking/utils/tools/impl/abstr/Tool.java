package org.optimizationBenchmarking.utils.tools.impl.abstr;

import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.Textable;
import org.optimizationBenchmarking.utils.tools.spec.ITool;

/**
 * A base class to derive tools from.
 */
public abstract class Tool extends Textable implements ITool {

  /** create */
  protected Tool() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public boolean canUse() {
    return false;
  }

  /**
   * Check if this tool can be used an throw a descriptive
   * {@link java.lang.UnsupportedOperationException} otherwise. In other
   * words: if {@link #canUse()} returns {@code false}, this method throws
   * an exception. If {@link #canUse()} returns {@code true}, this method
   * does nothing. This method should be called in {@link #use()} as well.
   *
   * @throws UnsupportedOperationException
   *           if {@link #canUse()} returns {@code false}.
   */
  @Override
  public void checkCanUse() {
    if (!(this.canUse())) {
      throw new UnsupportedOperationException("Tool '" + //$NON-NLS-1$
          TextUtils.className(this.getClass()) + "' cannot be used."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {
    return ((o == this) || //
        ((o != null) && (o.getClass() == this.getClass())));
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return ((5693 + this.getClass().hashCode()) * 7717);
  }
}
