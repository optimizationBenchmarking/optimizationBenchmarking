package org.optimizationBenchmarking.experimentation.attributes;

import java.util.ArrayList;

import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IFeature;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureValue;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElement;
import org.optimizationBenchmarking.experimentation.data.spec.IParameter;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterValue;
import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertyValue;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * This attribute will obtain the instances belonging to a given feature
 * value or the experiments belonging to a given parameter value.
 *
 * @param <PT>
 *          the property type
 * @param <ET>
 *          the element type
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public final class PropertyValueElements<PT extends IPropertyValue, ET extends INamedElement>
    extends Attribute<PT, ArrayListView<ET>> {

  /**
   * the attribute providing the instances which share a given feature
   * value
   */
  public static final PropertyValueElements<IFeatureValue, IInstance> FEATURE_VALUE_INSTANCES;
  /**
   * the attribute providing the experiments which share a given parameter
   * value
   */
  public static final PropertyValueElements<IParameterValue, IExperiment> PARAMETER_VALUE_EXPERIMENT;

  static {
    final PropertyValueElements e = new PropertyValueElements();
    FEATURE_VALUE_INSTANCES = e;
    PARAMETER_VALUE_EXPERIMENT = e;
  }

  /** create the property value element */
  private PropertyValueElements() {
    super(EAttributeType.TEMPORARILY_STORED);
  }

  /** {@inheritDoc} */
  @Override
  protected final ArrayListView<ET> compute(final PT data) {
    final IProperty owner;
    final ArrayListView elements;
    final ArrayList adder;

    if (data == null) {
      throw new IllegalArgumentException(
          "Property value must not be null."); //$NON-NLS-1$
    }

    owner = data.getOwner();
    notFound: {

      if (owner instanceof IFeature) {
        elements = owner.getOwner().getOwner().getInstances().getData();
        if (owner.getData().size() <= 1) {
          return elements;
        }
        adder = new ArrayList(elements.size());
        for (final IInstance inst : ((ArrayListView<IInstance>) elements)) {
          if (inst.getFeatureSetting().contains(data)) {
            adder.add(inst);
          }
        }
        break notFound;
      }

      if (owner instanceof IParameter) {
        elements = owner.getOwner().getOwner().getData();
        if (owner.getData().size() <= 1) {
          return elements;
        }
        adder = new ArrayList(elements.size());
        for (final IExperiment exp : ((ArrayListView<IExperiment>) elements)) {
          if (exp.getParameterSetting().contains(data)) {
            adder.add(exp);
          }
        }
        break notFound;
      }

      throw new IllegalArgumentException("Property type " + //$NON-NLS-1$
          owner.getClass().getSimpleName() + " not supported.");//$NON-NLS-1$
    }

    if (adder.size() >= elements.size()) {
      return elements;
    }
    return ArrayListView.collectionToView(adder);

  }
}
