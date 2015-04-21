package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import java.util.Arrays;

import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.IParameter;
import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/**
 * A set of property values.
 * 
 * @param <VT>
 *          the value type
 */
final class _PropertyValueSet<VT> {

  /** the property values and their corresponding instances */
  final _PropertyValueInstances<VT>[] m_values;

  /** the unspecified value and its corresponding instances */
  final _PropertyValueInstances<Object> m_unspecified;
  /**
   * the value: could be {@link java.lang.Object} or any wrapper class for
   * any primitive type, or {@link java.lang.String}
   */
  final Class<VT> m_valueClass;

  /** the property in question */
  final IProperty m_property;

  /** Are the values integers? */
  final boolean m_areValuesIntegers;

  /** Are the values doubles? */
  final boolean m_areValuesDoubles;

  /**
   * Create the property value set
   * 
   * @param property
   *          the property
   * @param elements
   *          the elements
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  _PropertyValueSet(final IProperty property, final ArrayListView elements) {
    super();

    final int elementSize, valueSize;
    final _PropertyValueInstances[] collected;
    _PropertyValueInstances unspecified, added;
    int collectedCount, collectedElements;
    Object value;
    boolean canInt, canDouble, isUnspecified;
    Class allClazz, curClazz;

    if (property == null) {
      throw new IllegalArgumentException(//
          "The property must not be null."); //$NON-NLS-1$
    }
    if ((valueSize = property.getData().size()) <= 0) {
      throw new IllegalArgumentException(//
          "The number of property values must be greater than 0."); //$NON-NLS-1$
    }
    if (elements == null) {
      throw new IllegalArgumentException(//
          "The set of elements must not be null."); //$NON-NLS-1$
    }
    if ((elementSize = elements.size()) <= 0) {
      throw new IllegalArgumentException(//
          "The number of elements must be greater than 0."); //$NON-NLS-1$
    }

    collected = new _PropertyValueInstances[valueSize];
    unspecified = null;
    collectedElements = collectedCount = 0;
    allClazz = null;
    canInt = canDouble = true;

    mainLoop: for (final IDataElement element : ((ArraySetView<IDataElement>) (elements))) {
      collectedElements++;

      if (element == null) {
        throw new IllegalStateException(//
            "Element cannot be null."); //$NON-NLS-1$
      }

      isUnspecified = false;
      if (element instanceof IExperiment) {
        value = ((IExperiment) element).getParameterSetting()
            .get(property);
        if (property instanceof IParameter) {
          isUnspecified = ((IParameter) property).findValue(value)
              .isUnspecified();
        }
      } else {
        if (element instanceof IInstance) {
          value = ((IInstance) element).getFeatureSetting().get(property);
        } else {
          throw new IllegalArgumentException(//
              element + //
                  " is neither a instance of IExperiment nor IInstance, so it cannot have properties.");//$NON-NLS-1$
        }
      }
      if (value == null) {
        throw new IllegalStateException(//
            "Property value cannot be null."); //$NON-NLS-1$
      }

      // is the value unspecified?
      if (isUnspecified) {
        if (unspecified == null) {
          unspecified = new _PropertyValueInstances<>(value,
              ((elementSize - collectedElements) + 1));
        }
        unspecified.m_elements[unspecified.m_size++] = element;
        continue mainLoop;
      }

      // do we already have this value?
      for (final _PropertyValueInstances known : collected) {
        if (known == null) {
          break;
        }
        if (EComparison.equals(known.m_value, value)) {
          known.m_elements[known.m_size++] = element;
          continue mainLoop;
        }
      }

      // ok, the value is new - first check whether it changes anything
      curClazz = value.getClass();
      if (allClazz == null) {
        allClazz = curClazz;
      } else {

        classFinder: for (;;) {
          if (allClazz.isAssignableFrom(curClazz)) {
            break classFinder;
          }
          allClazz = allClazz.getSuperclass();
          if (allClazz == null) {
            throw new IllegalStateException(//
                "Classes cannot be entirely incompatible."); //$NON-NLS-1$
          }
        }
      }

      if (canInt) {
        canInt &= ((Byte.class.isAssignableFrom(curClazz)) || //
            (Short.class.isAssignableFrom(curClazz)) || //
            (Integer.class.isAssignableFrom(curClazz)) || //
        (Long.class.isAssignableFrom(curClazz)));
      }

      if (canDouble && (!(canInt))) {
        canDouble &= ((Float.class.isAssignableFrom(curClazz)) || //
        (Double.class.isAssignableFrom(curClazz)));
      }

      collected[collectedCount++] = added = new _PropertyValueInstances<>(
          value, ((elementSize - collectedElements) + 1));
      added.m_elements[added.m_size++] = element;
    }

    if (collectedCount <= 0) {
      throw new IllegalArgumentException(//
          "It is not possible that all elements do not have property '"//$NON-NLS-1$
              + property.getName() + //
              "' specified."); //$NON-NLS-1$          
    }
    if (allClazz == null) {
      throw new IllegalStateException(//
          "It is impossible that the class to which all values are compatible is null.");//$NON-NLS-1$
    }

    this.m_unspecified = unspecified;
    if (unspecified != null) {
      Arrays.sort(unspecified.m_elements, 0, unspecified.m_size);
    }

    this.m_values = new _PropertyValueInstances[collectedCount];
    System.arraycopy(collected, 0, this.m_values, 0, collectedCount);
    Arrays.sort(this.m_values);
    for (final _PropertyValueInstances pvi : this.m_values) {
      Arrays.sort(pvi.m_elements, 0, pvi.m_size);
    }

    this.m_areValuesIntegers = canInt;
    this.m_areValuesDoubles = canDouble;
    this.m_property = property;
    this.m_valueClass = allClazz;
  }

}
