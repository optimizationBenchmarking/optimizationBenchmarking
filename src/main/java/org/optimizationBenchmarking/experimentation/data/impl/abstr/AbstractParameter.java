package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.IParameter;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterSet;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterValue;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IParameter}
 * interface.
 */
public class AbstractParameter extends AbstractProperty implements
    IParameter {

  /** the owner */
  IParameterSet m_owner;

  /**
   * Create the abstract parameter. If {@code owner==null}, you must later
   * set it via
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractParameterSet#own(AbstractParameter)}
   * .
   * 
   * @param owner
   *          the owner
   */
  protected AbstractParameter(final IParameterSet owner) {
    super();
    this.m_owner = owner;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public ArrayListView<? extends IParameterValue> getData() {
    return ((ArrayListView) (ArraySetView.EMPTY_SET_VIEW));
  }

  /** {@inheritDoc} */
  @Override
  public IParameterValue findValue(final Object value) {
    IParameterValue pvalue;

    pvalue = ((IParameterValue) (super.findValue(value)));
    if (pvalue == null) {
      pvalue = this.getUnspecified();
      if ((pvalue == null)
          || (!(EComparison.equals(pvalue.getValue(), value)))) {
        return null;
      }
    }
    return pvalue;
  }

  /** {@inheritDoc} */
  @Override
  public IParameterValue getUnspecified() {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public IParameterValue getGeneralized() {
    return ((IParameterValue) (super.getGeneralized()));
  }

  /** {@inheritDoc} */
  @Override
  public final IParameterSet getOwner() {
    return this.m_owner;
  }

}
