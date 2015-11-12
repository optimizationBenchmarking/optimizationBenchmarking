package org.optimizationBenchmarking.experimentation.attributes.statistics.propertyExtremals;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IFeature;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureValue;
import org.optimizationBenchmarking.experimentation.data.spec.IParameter;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterValue;
import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertyValue;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/**
 * This class is able to find the extremal property values.
 *
 * @param <PT>
 *          the property type
 * @param <PVT>
 *          the property value tyoe
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public final class ExtremalPropertyValuesGetter<PT extends IProperty, PVT extends IPropertyValue>
    extends Attribute<PT, ExtremalPropertyValues<PVT>> {

  /**
   * This value getter provides us with the extremal values of a feature
   */
  public static final ExtremalPropertyValuesGetter<IFeature, IFeatureValue> EXTREMAL_FEATURE_VALUES;

  /**
   * This value getter provides us with the extremal values of a parameter
   */
  public static final ExtremalPropertyValuesGetter<IParameter, IParameterValue> EXTREMAL_PARAMETER_VALUES;

  static {
    final ExtremalPropertyValuesGetter g = new ExtremalPropertyValuesGetter();
    EXTREMAL_FEATURE_VALUES = g;
    EXTREMAL_PARAMETER_VALUES = g;
  }

  /** create */
  private ExtremalPropertyValuesGetter() {
    super(EAttributeType.TEMPORARILY_STORED);
  }

  /** {@inheritDoc} */
  @Override
  protected final ExtremalPropertyValues<PVT> compute(final PT data,
      final Logger logger) {
    final EPrimitiveType type;
    long minLong, maxLong, curLong;
    double minDouble, maxDouble, curDouble;
    IPropertyValue min, max;

    type = data.getPrimitiveType();
    min = max = null;

    error: {
      if (type != null) {
        if (type.isInteger()) {
          minLong = Long.MAX_VALUE;
          maxLong = Long.MIN_VALUE;
          for (final IPropertyValue cur : data.getData()) {
            curLong = ((Number) (cur.getValue())).longValue();

            if ((min == null) || (curLong < minLong)) {
              min = cur;
              minLong = curLong;
            }
            if ((max == null) || (curLong > maxLong)) {
              max = cur;
              maxLong = curLong;
            }
          }
          break error;
        }
        if (type.isFloat()) {
          minDouble = Double.POSITIVE_INFINITY;
          maxDouble = Double.NEGATIVE_INFINITY;
          for (final IPropertyValue cur : data.getData()) {
            curDouble = ((Number) (cur.getValue())).doubleValue();

            if ((min == null) || (curDouble < minDouble)) {
              min = cur;
              minDouble = curDouble;
            }
            if ((max == null) || (curDouble > maxDouble)) {
              max = cur;
              maxDouble = curDouble;
            }
          }
          break error;
        }
      }

      throw new IllegalArgumentException(//
          "Extremal property values can only be computed for numerical properties, but property '" //$NON-NLS-1$
              + data + "' has type '" + type + '\'');//$NON-NLS-1$
    }

    return new ExtremalPropertyValues(min, max);
  }

}
