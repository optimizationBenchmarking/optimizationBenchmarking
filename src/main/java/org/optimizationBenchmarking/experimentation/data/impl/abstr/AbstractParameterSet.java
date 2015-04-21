package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IParameter;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterSet;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterSetting;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IParameterSet}
 * interface.
 */
public class AbstractParameterSet extends
    _AbstractPropertySet<IParameterSetting> implements IParameterSet {

  /**
   * create the abstract parameter set
   * 
   * @param owner
   *          the owner
   */
  protected AbstractParameterSet(final IExperimentSet owner) {
    super(owner);
  }

  /**
   * Create an abstract parameter set without an owner. You must later set
   * the owner via
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractExperimentSet#own(AbstractParameterSet)}
   * .
   */
  protected AbstractParameterSet() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  final IParameterSetting _createEmpty() {
    return new AbstractParameterSetting();
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public ArraySetView<? extends IParameter> getData() {
    return ((ArraySetView) (ArraySetView.EMPTY_SET_VIEW));
  }

  /** {@inheritDoc} */
  @Override
  public IParameter find(final String name) {
    return ((IParameter) (super.find(name)));
  }
}
