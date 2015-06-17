package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterSetting;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.ETextCase;
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
    String name;
    int size;

    mine = this.getData();
    yours = shadow.getData();

    size = mine.size();
    if (size != yours.size()) {
      return false;
    }

    outer: for (final IInstanceRuns runs : mine) {
      delegate = ((ShadowInstanceRuns) runs)._getAttributeDelegate();

      if (delegate == null) {
        return false;
      }

      if (yours.contains(delegate)) {
        continue outer;
      }

      name = delegate.getInstance().getName();

      for (final IInstanceRuns other : yours) {
        if (EComparison.equals(name, other.getInstance().getName())) {
          // If the other experiment has instance runs for the same
          // instance, let's check if they are compatible.
          if (other == delegate) {
            // would be odd, since !yours.contains(delegate)
            // but let's check it anyway
            continue outer;
          }
          if (other == mine) {
            continue outer;
          }
          if (other instanceof ShadowInstanceRuns) {
            if ((((ShadowInstanceRuns) other)._getAttributeDelegate()) == delegate) {
              continue outer;
            }
          }
          return false;
        }
      }

      return false;
    }

    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(ITextOutput out, IParameterRenderer renderer) {
    this.m_shadowUnpacked.mathRender(out, renderer);
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(IMath out, IParameterRenderer renderer) {
    this.m_shadowUnpacked.mathRender(out, renderer);
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase appendName(final ITextOutput textOut,
      final ETextCase textCase) {
    return this.m_shadowUnpacked.appendName(textOut, textCase);
  }

  /** {@inheritDoc} */
  @Override
  public final String getPathComponentSuggestion() {
    return this.m_shadowUnpacked.getPathComponentSuggestion();
  }
}
