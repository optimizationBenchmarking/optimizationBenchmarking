package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertySet;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertySetting;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertyValue;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * A shadow property set is basically a shadow of another property set with
 * a different owner and potentially different attributes. If all
 * associated data of this element is the same, it will delegate
 * attribute-based computations to that property set.
 * 
 * @param <ST>
 *          the shadow type
 * @param <PT>
 *          the property type
 * @param <PST>
 *          the property setting type
 */
abstract class _ShadowPropertySet<ST extends IPropertySet, //
PT extends IProperty, //
PST extends IPropertySetting> extends //
    _ShadowNamedElementSet<IExperimentSet, ST, PT> implements IPropertySet {

  /**
   * create the shadow property
   * 
   * @param owner
   *          the owning property set
   * @param shadow
   *          the property to shadow
   * @param selection
   *          the selection of properties
   */
  _ShadowPropertySet(final IExperimentSet owner, final ST shadow,
      final Collection<? extends PT> selection) {
    super(owner, shadow, selection);
  }

  /**
   * Create a setting from a list
   * 
   * @param values
   *          the list of values
   * @return the property setting
   */
  abstract PST _createSettingFromArray(final IPropertyValue[] values);

  /**
   * create a setting from an iterable
   * 
   * @param values
   *          the values
   * @return the setting
   */
  @SuppressWarnings("rawtypes")
  private final PST __createSettingFromIterable(final Iterable values) {
    final ArrayListView<? extends PT> data;
    final IPropertyValue[] array;
    ArrayList<IPropertyValue> list;
    HashSet<IProperty> done;
    PT prop;
    String name;
    Object value;
    IPropertyValue pv;
    Map.Entry mapEntry;

    list = new ArrayList<>();
    data = this.getData();
    done = new HashSet<>(data.size());

    for (final Object entry : values) {
      if (entry == null) {
        continue;
      }

      if (entry instanceof IPropertyValue) {
        pv = ((IPropertyValue) entry);
        name = pv.getOwner().getName();
        value = pv.getValue();
      } else {
        if (entry instanceof Map.Entry) {
          mapEntry = ((Map.Entry) entry);
          name = String.valueOf(mapEntry.getKey());
          value = mapEntry.getValue();
        } else {
          throw new IllegalArgumentException(//
              "Cannot process " + entry); //$NON-NLS-1$
        }
      }

      prop = this.find(name);
      if (prop == null) {
        throw new IllegalArgumentException(((//
            "Could not find any property with name '" //$NON-NLS-1$
            + name) + '\'') + '.');
      }

      if (done.add(prop)) {
        pv = prop.findValue(value);
        if (pv == null) {
          throw new IllegalArgumentException(((//
              "Could not find value '" //$NON-NLS-1$
                  + value + " of property '"//$NON-NLS-1$
              + name) + '\'') + '.');
        }
        list.add(pv);
      } else {
        throw new IllegalArgumentException(//
            "Property value for property '" //$NON-NLS-1$
                + name + "' defined more than once."); //$NON-NLS-1$
      }
    }

    for (final IProperty prope : data) {
      if (done.add(prope)) {
        list.add(prope.getGeneralized());
      }
    }
    done = null;

    array = list.toArray(new IPropertyValue[list.size()]);
    list = null;
    try {
      Arrays.sort(array);
    } catch (final Throwable tt) {
      // ignore
    }

    return this._createSettingFromArray(array);
  }

  /** {@inheritDoc} */
  @Override
  public final PST createSettingFromValues(
      final Iterable<? extends IPropertyValue> values) {
    return this.__createSettingFromIterable(values);
  }

  /** {@inheritDoc} */
  @Override
  public final PST createSettingFromMapping(
      final Map<String, Object> values) {
    return this.__createSettingFromIterable(values.entrySet());
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public final PST createSettingFromMapping(
      final Entry<String, Object>[] values) {
    return this.__createSettingFromIterable(new ArrayListView(values));
  }

  /** {@inheritDoc} */
  @Override
  public PST createSettingFromMapping(
      final Iterable<Entry<String, Object>> values) {
    return this.__createSettingFromIterable(values);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public final PST createSettingFromValues(final IPropertyValue... values) {
    return this.__createSettingFromIterable(new ArrayListView(values));
  }
}
