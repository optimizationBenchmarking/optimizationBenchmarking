package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IParameter;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterSet;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterSetting;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IParameterSet}
 * interface.
 */
public class AbstractParameterSet extends
    _AbstractPropertySet<IParameterSetting> implements IParameterSet {

  /**
   * Create the abstract parameter set. If {@code owner==null}, you must
   * later set it via
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractExperimentSet#own(AbstractParameterSet)}
   * .
   * 
   * @param owner
   *          the owner
   */
  protected AbstractParameterSet(final IExperimentSet owner) {
    super(owner);
  }

  /**
   * Own an
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractParameter}
   * .
   * 
   * @param parameter
   *          the parameter set to own
   */
  protected final void own(final AbstractParameter parameter) {
    if (parameter == null) {
      throw new IllegalArgumentException(//
          "AbstractParameter to be owned by AbstractParameterSet cannot be null."); //$NON-NLS-1$
    }
    synchronized (parameter) {
      if (parameter.m_owner != null) {
        throw new IllegalArgumentException(//
            "AbstractParameter to be owned by AbstractParameterSet already owned.");//$NON-NLS-1$
      }
      parameter.m_owner = this;
    }
  }

  /** {@inheritDoc} */
  @Override
  final IParameterSetting _createEmpty() {
    return new AbstractParameterSetting();
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public ArrayListView<? extends IParameter> getData() {
    return ((ArraySetView) (ArraySetView.EMPTY_SET_VIEW));
  }

  /** {@inheritDoc} */
  @Override
  public IParameter find(final String name) {
    return ((IParameter) (super.find(name)));
  }
}
