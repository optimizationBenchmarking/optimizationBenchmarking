package org.optimizationBenchmarking.experimentation.evaluation.attributes.basic;

import java.util.Arrays;
import java.util.HashSet;

import org.optimizationBenchmarking.experimentation.data.Attribute;
import org.optimizationBenchmarking.experimentation.data.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.Feature;
import org.optimizationBenchmarking.experimentation.data.Instance;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * An attribute for obtaining the feature values actually used.
 */
public final class UsedFeatureValues extends
    Attribute<Feature, ArraySetView<Object>> {

  /** the internal singleton instance */
  private static final UsedFeatureValues INSTANCE = new UsedFeatureValues();

  /** create */
  private UsedFeatureValues() {
    super(EAttributeType.TEMPORARILY_STORED);
  }

  /** {@inheritDoc} */
  @Override
  protected final ArraySetView<Object> compute(final Feature data) {
    final HashSet<Object> values;
    final Object[] result;
    final int goalCount;
    int count;
    Object o;

    goalCount = data.getData().size();
    if (goalCount <= 0) {
      throw new IllegalStateException("Feature '"//$NON-NLS-1$
          + data.getName() + //
          "' has no possible values.");//$NON-NLS-1$
    }

    values = new HashSet<>();
    count = 0;
    loop: for (final Instance instance : UsedInstances.getInstance().get(
        data.getOwner().getOwner())) {
      o = data.get(instance);
      if (o == null) {
        throw new IllegalStateException(//
            "Value of feature '" //$NON-NLS-1$
                + data.getName() + //
                "' for instance '" //$NON-NLS-1$
                + instance.getName() + //
                "' is null."); //$NON-NLS-1$
      }
      if (values.add(o)) {
        if ((++count) >= goalCount) {
          break loop;
        }
      }
    }

    if (count > 0) {
      result = values.toArray(new Object[count]);
      Arrays.sort(result);
      return new ArraySetView<>(result);
    }
    return ArraySetView.EMPTY_SET_VIEW;
  }

  /**
   * Get the globally shared instance of this attribute
   * 
   * @return the globally shared instance of this attribute
   */
  public static final UsedFeatureValues getInstance() {
    return UsedFeatureValues.INSTANCE;
  }

}
