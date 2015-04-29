package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterSetting;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A shadow experiment is basically a shadow of another experiment with a
 * different owner and potentially different attributes. If all associated
 * data of this element is the same, it will delegate attribute-based
 * computations to that experiment.
 */
public class ShadowExperiment extends //
    _ShadowElementSet<IExperimentSet, IExperiment, IInstanceRuns>
    implements IExperiment {

  /** the parameters */
  private IParameterSetting m_params;

  /**
   * create the shadow experiment
   * 
   * @param owner
   *          the owning element
   * @param shadow
   *          the experiment to shadow
   * @param selection
   *          the selection of instance runs
   */
  public ShadowExperiment(final IExperimentSet owner,
      final IExperiment shadow,
      final Collection<? extends IInstanceRuns> selection) {
    super(owner, shadow, selection);
  }

  /** {@inheritDoc} */
  @Override
  final IInstanceRuns _shadow(final IInstanceRuns element) {
    return new ShadowInstanceRuns(this, element, null);
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return this.m_shadowUnpacked.getName();
  }

  /** {@inheritDoc} */
  @Override
  public final String getDescription() {
    return this.m_shadowUnpacked.getDescription();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final IParameterSetting getParameterSetting() {
    if (this.m_params == null) {
      this.m_params = this
          .getOwner()
          .getParameters()
          .createSettingFromValues(
              this.m_shadowUnpacked.getParameterSetting());
    }
    return this.m_params;
  }

  /** {@inheritDoc} */
  @Override
  final boolean _canDelegateAttributesTo(final IExperiment shadow) {
    final ArrayListView<? extends IInstanceRuns> mine, yours;
    IInstanceRuns delegate;
    int size;

    mine = this.getData();
    yours = shadow.getData();

    size = mine.size();
    if (size != yours.size()) {
      return false;
    }

    for (final IInstanceRuns run : mine) {
      delegate = ((ShadowInstanceRuns) run)._getAttributeDelegate();
      if (delegate == null) {
        return false;
      }
      if (!(yours.contains(delegate))) {
        return false;
      }
      // TODO: deal with the case that shadow is already a shadow
    }

    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final void appendName(final IMath math) {
    this.m_shadowUnpacked.appendName(math);
  }

  /** {@inheritDoc} */
  @Override
  public final void appendName(final ITextOutput textOut) {
    this.m_shadowUnpacked.appendName(textOut);
  }

  /** {@inheritDoc} */
  @Override
  public final String getPathComponentSuggestion() {
    return this.m_shadowUnpacked.getPathComponentSuggestion();
  }
}
