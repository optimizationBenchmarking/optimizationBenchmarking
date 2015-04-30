package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.Collection;

import org.optimizationBenchmarking.experimentation.data.impl.shadow.DataSelection._CompiledSelection;
import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IDimensionSet;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterSet;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * A shadow experiment set is basically a shadow of another experiment set
 * with a different owner and potentially different attributes. If all
 * associated data of this element is the same, it will delegate
 * attribute-based computations to that experiment set.
 * 
 * @param <OT>
 *          the owner type
 */
public class ShadowExperimentSet<OT extends IDataElement> extends //
    _ShadowNamedElementSet<OT, IExperimentSet, IExperiment> implements
    IExperimentSet {

  /** the dimensions */
  private IDimensionSet m_dimensions;

  /** the features */
  private IFeatureSet m_features;

  /** the instances */
  private IInstanceSet m_instances;

  /** the parameters */
  private IParameterSet m_parameters;

  /**
   * create the shadow experiment set
   * 
   * @param owner
   *          the owning element set
   * @param shadow
   *          the experiment set to shadow
   * @param experimentSelection
   *          the selection of experiments
   */
  public ShadowExperimentSet(final OT owner, final IExperimentSet shadow,
      final Collection<? extends IExperiment> experimentSelection) {
    super(owner, shadow, experimentSelection);
  }

  /**
   * create the shadow experiment set
   * 
   * @param owner
   *          the owning element set
   * @param shadow
   *          the experiment set to shadow
   * @param experimentSelection
   *          the selection of experiments
   * @param dimensions
   *          the dimension set to use (or {@code null} to create one on
   *          demand)
   * @param features
   *          the feature set to use (or {@code null} to create one on
   *          demand)
   * @param instances
   *          the instance set to use (or {@code null} to create one on
   *          demand)
   * @param parameters
   *          the parameter set to use (or {@code null} to create one on
   *          demand)
   */
  public ShadowExperimentSet(final OT owner, final IExperimentSet shadow,
      final Collection<? extends IExperiment> experimentSelection,
      final ShadowDimensionSet dimensions,
      final ShadowFeatureSet features, final ShadowInstanceSet instances,
      final ShadowParameterSet parameters) {
    super(owner, shadow, experimentSelection);

    if (dimensions != null) {
      dimensions.setOwner(this);
      this.m_dimensions = dimensions;
    }

    if (features != null) {
      synchronized (features) {
        features.setOwner(this);
        this.m_features = features;
      }
    }

    if (instances != null) {
      synchronized (instances) {
        instances.setOwner(this);
        this.m_instances = instances;
      }
    }

    if (parameters != null) {
      synchronized (parameters) {
        parameters.setOwner(this);
        this.m_parameters = parameters;
      }
    }

    this._checkDiscardOrig();
  }

  /**
   * Create an experiment set from a selection
   * 
   * @param owner
   *          the owner
   * @param selection
   *          the selection
   */
  private ShadowExperimentSet(final OT owner,
      final _CompiledSelection selection) {
    this(owner, selection.m_original, selection.m_experiments, null,
        selection.m_features, selection.m_instances,
        selection.m_parameters);
  }

  /**
   * Create an experiment set from a data selection
   * 
   * @param owner
   *          the owner
   * @param selection
   *          the selection
   */
  public ShadowExperimentSet(final OT owner, final DataSelection selection) {
    this(owner, selection._getSelection());
  }

  /** {@inheritDoc} */
  @Override
  final void _checkDiscardOrig() {
    if ((this.m_data != null) && //
        (this.m_dimensions != null) && //
        (this.m_features != null) && //
        (this.m_instances != null) && //
        (this.m_parameters != null)) {
      this.m_orig = null;
    }
  }

  /** {@inheritDoc} */
  @Override
  final IExperiment _shadow(final IExperiment element) {
    return new ShadowExperiment(this, element, null);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final IDimensionSet getDimensions() {
    if (this.m_dimensions == null) {
      this.m_dimensions = new ShadowDimensionSet(this,
          this.m_orig.getDimensions(), null);
      this._checkDiscardOrig();
    }
    return this.m_dimensions;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final IInstanceSet getInstances() {
    if (this.m_instances == null) {
      this.m_instances = new ShadowInstanceSet(this,
          this.m_orig.getInstances(), null);
      this._checkDiscardOrig();
    }
    return this.m_instances;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final IFeatureSet getFeatures() {
    if (this.m_features == null) {
      this.m_features = new ShadowFeatureSet(this,
          this.m_orig.getFeatures(), null);
      this._checkDiscardOrig();
    }
    return this.m_features;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final IParameterSet getParameters() {
    if (this.m_parameters == null) {
      this.m_parameters = new ShadowParameterSet(this,
          this.m_orig.getParameters(), null);
      this._checkDiscardOrig();
    }
    return this.m_parameters;
  }

  /** {@inheritDoc} */
  @Override
  final boolean _canDelegateAttributesTo(final IExperimentSet shadow) {
    final ArrayListView<? extends IExperiment> mine, yours;
    IExperiment delegate, other;
    int size;

    mine = this.getData();
    yours = shadow.getData();

    size = mine.size();
    if (size != yours.size()) {
      return false;
    }

    outer: for (final IExperiment experiment : mine) {
      delegate = ((ShadowExperiment) experiment)._getAttributeDelegate();

      if (delegate == null) {
        return false;
      }

      if (yours.contains(delegate)) {
        continue outer;
      }

      other = shadow.find(delegate.getName());
      if (other == null) {
        return false;
      }
      if (other == delegate) {
        continue outer;
      }
      if (other == mine) {
        continue outer;
      }
      if (other instanceof ShadowExperiment) {
        if ((((ShadowExperiment) other)._getAttributeDelegate()) == delegate) {
          continue outer;
        }
      }

      return false;
    }

    return true;
  }

}
