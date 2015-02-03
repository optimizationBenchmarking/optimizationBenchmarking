package org.optimizationBenchmarking.experimentation.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * This is the base class for classes that can build a set of properties.
 * 
 * @param <PT>
 *          the property type
 * @param <PVT>
 *          the property value
 * @param <PST>
 *          the property set type the property builder type
 */
abstract class _PropertyFSMPropertiesBuilder<PVT extends _PropertyValue<?>, PT extends _Property<?>, PST extends _PropertySet<?, ?, ?>>
    extends _PropertyFSM<_PropertyFSMPropertyRecord, PST, PST, _FSM>
    implements Comparator<_PropertyValue<?>> {

  /**
   * create
   * 
   * @param owner
   *          the owner
   */
  _PropertyFSMPropertiesBuilder(final _FSM owner) {
    super(owner);
    this._open();
  }

  /**
   * normalize the parameter description.
   * 
   * @param name
   *          the parameter name
   * @param desc
   *          the parameter description
   * @return the normalized description
   */
  private static final String __normalizeDesc(final String name,
      final String desc) {
    if (desc != null) {
      return desc;
    }
    if (Parameter.PARAMETER_ALGORITHM.equalsIgnoreCase(name)) {
      return "The optimization algorithm name and class."; //$NON-NLS-1$
    }
    if (Parameter.PARAMETER_ALGORITHM_CLASS.equalsIgnoreCase(name)) {
      return "The optimization algorithm class."; //$NON-NLS-1$
    }
    if (Parameter.PARAMETER_ALGORITHM_NAME.equalsIgnoreCase(name)) {
      return "The optimization algorithm name."; //$NON-NLS-1$
    }
    if (Parameter.PARAMETER_INITIALIZER.equalsIgnoreCase(name)) {
      return "The (deterministic) initialization algorithm name and class."; //$NON-NLS-1$
    }
    if (Parameter.PARAMETER_INITIALIZER_CLASS.equalsIgnoreCase(name)) {
      return "The (deterministic) initialization algorithm class."; //$NON-NLS-1$
    }
    if (Parameter.PARAMETER_INITIALIZER_NAME.equalsIgnoreCase(name)) {
      return "The (deterministic) initialization algorithm name."; //$NON-NLS-1$
    }
    return null;
  }

  /** {@inheritDoc} */
  @Override
  final _PropertyFSMPropertyRecord _createFSMRecord(
      final String propertyName, final String propertyDesc,
      final Object propertyValue, final String propertyValueDesc) {
    return new _PropertyFSMPropertyRecord(propertyName, propertyDesc,
        propertyValue, propertyValueDesc);
  }

  /**
   * Create a property value
   * 
   * @param name
   *          the value's name
   * @param value
   *          the value's value
   * @param desc
   *          the value's description
   * @return the value
   */
  abstract PVT _createPropertyValue(final String name, final String desc,
      final Object value);

  /**
   * create the property
   * 
   * @param name
   *          the property's name
   * @param values
   *          the property's values
   * @param primitiveType
   *          the type
   * @param desc
   *          the property's description
   * @param hasUnspecified
   *          has the property unspecified values?
   * @return the property
   */
  abstract PT _createProperty(final String name, final String desc,
      final EPrimitiveType primitiveType,
      final _PropertyValue<?>[] values, final boolean hasUnspecified);

  /**
   * create the property set
   * 
   * @param data
   *          the properties
   * @return the property set
   */
  abstract PST _createPropertySet(final _Property<?>[] data);

  /**
   * merge names
   * 
   * @param a
   *          names a
   * @param b
   *          names b
   * @return the merged result
   */
  private static final String __mergeNames(final String a, final String b) {
    final int len1, len2;

    if (a == null) {
      return b;
    }
    if (b == null) {
      return a;
    }

    if (a.equalsIgnoreCase(b)) {
      return a;
    }

    len1 = a.length();
    if (len1 <= 0) {
      return b;
    }

    len2 = b.length();
    if (len2 <= 0) {
      return a;
    }

    return ((len1 <= len2) ? a : b);
  }

  /**
   * Turn a property context into a property
   * 
   * @param rec
   *          the record
   * @param totalCount
   *          the total number of expected property values if no undefined
   *          records exist
   * @return the parameter record
   */
  private final PT __mapProperty(final _PropertyFSMPropertyRecord rec,
      final int totalCount) {
    final ArrayList<EPrimitiveType> primitiveTypes;
    final HashMap<Object, PVT> parsed;
    final HashMap<Object, String> descriptions;
    final Object[] values;
    final int refCount;
    final _PropertyValue<?>[] propertyValues;
    final String n;
    EPrimitiveType type;
    Object propertyValue;
    Iterator<EPrimitiveType> it;
    String stringValue;
    boolean strict;
    Parser<?> parser;
    int len, i;
    PVT q;

    refCount = rec.m_refCount;
    rec.m_refCount = Integer.MIN_VALUE;
    descriptions = rec.m_descriptions;
    rec.m_descriptions = null;

    if (rec.m_refCount > totalCount) {
      throw new IllegalStateException(//
          "Property '" + rec.m_propertyName + //$NON-NLS-1$
              "' used too often???"); //$NON-NLS-1$
    }

    i = descriptions.size();
    if (i > refCount) {
      throw new IllegalStateException(//
          "Property '" + rec.m_propertyName + //$NON-NLS-1$
              "' has more values than have been declared?"); //$NON-NLS-1$
    }

    // property was declared, but never used
    if (refCount <= 0) {
      return null;
    }

    // obtained all the values
    values = descriptions.keySet().toArray(new Object[i]);
    parsed = new HashMap<>();

    // now we try to parse them into primitive types
    primitiveTypes = new ArrayList<>(EPrimitiveType.TYPES.size());
    primitiveTypes.addAll(EPrimitiveType.TYPES);
    primitiveTypes.remove(EPrimitiveType.CHAR);
    primitiveTypes.add(EPrimitiveType.CHAR);

    // try to find whether we can parse the data
    strict = true;

    // first we try all strict parsers
    it = primitiveTypes.iterator();
    try {
      outerA: while (it.hasNext()) {
        parser = it.next().getStrictParser();
        try {
          for (final Object value : values) {
            if (parser.parseObject(value) == null) {
              it.remove();
              continue outerA;
            }
          }
        } catch (final Throwable t) {
          try {
            it.remove();
          } catch (final Throwable t2) {
            continue outerA;
          }
        }
      }
    } catch (final Throwable t3) {
      primitiveTypes.clear();
    }

    if (primitiveTypes.isEmpty()) {
      // strict parsing did not work, now try loose parsing
      strict = false;

      // but we try to avoid booleans and floats, since loose parsing them
      // may
      // lose us fidelity
      primitiveTypes.addAll(EPrimitiveType.TYPES);
      primitiveTypes.remove(EPrimitiveType.BOOLEAN);
      primitiveTypes.remove(EPrimitiveType.FLOAT);
      primitiveTypes.remove(EPrimitiveType.CHAR);
      primitiveTypes.add(EPrimitiveType.CHAR);

      it = primitiveTypes.iterator();
      try {
        outerB: while (it.hasNext()) {
          parser = it.next().getLooseParser();
          try {
            for (final Object v : values) {
              if (parser.parseObject(v) == null) {
                it.remove();
                continue outerB;
              }
            }
          } catch (final Throwable t) {
            try {
              it.remove();
            } catch (final Throwable t2) {
              continue outerB;
            }
          }
        }
      } catch (final Throwable t3) {
        primitiveTypes.clear();
      }
    }

    makePv: {

      parseA: {
        // did we succeed in parsing?
        if (primitiveTypes.isEmpty()) {
          break parseA;
        }

        // yes! cool, we found one or multiple parser that can translate
        // the
        // property values to primitive types. Let's take the "strictest"
        // one
        type = primitiveTypes.get(0);
        if (type == null) {
          break parseA;
        }

        parser = (strict ? type.getStrictParser() : type.getLooseParser());
        if (parser == null) {
          break parseA;
        }
        try {
          for (final Object value : values) {
            propertyValue = this.m_owner._normalize(parser
                .parseObject(value));
            if (propertyValue == null) {
              break parseA;
            }
            q = this._createPropertyValue(
                this.m_owner._normalize(_PropertyFSMPropertiesBuilder
                    .__mergeNames(String.valueOf(value),
                        String.valueOf(propertyValue))), this.m_owner
                    ._normalize(descriptions.get(value)), propertyValue);
            if (parsed.containsKey(q.m_name)) {
              break parseA; // uh??
            }
            parsed.put(q.m_name, q);
          }
        } catch (final Throwable vv) {
          break parseA;
        }
        break makePv;// success
      }

      // ok, parsing did not work out. let's check if the parameter is a
      // class
      // name parameter of the type "name (class)"
      parsed.clear();
      type = null;
      isClass: {
        for (final Object value : values) {
          if (!(value instanceof String)) {
            break isClass;
          }
          stringValue = this.m_owner._normalizeLocal((String) value);
          if (stringValue == null) {
            break isClass;
          }
          len = stringValue.length();
          if (len <= 2) {
            break isClass;
          }
          if (stringValue.charAt(len - 1) != ')') {
            break isClass;
          }
          i = stringValue.lastIndexOf('(');
          if ((i <= 0) || (i >= (len - 2))) {
            break isClass;
          }
          if (!(TextUtils.couldBeClassName(stringValue.substring(i + 1,
              len - 1)))) {
            break isClass;
          }
          propertyValue = this.m_owner._normalizeLocal(stringValue
              .substring(0, i));
          if (propertyValue == null) {
            break isClass;
          }

          q = this._createPropertyValue(
              this.m_owner._normalize(stringValue),
              this.m_owner._normalize(descriptions.get(value)),
              this.m_owner._normalize(propertyValue));

          if (parsed.containsKey(q.m_name)) {
            break isClass; // uh??
          }
          parsed.put(q.m_name, q);
        }
        break makePv; // success!
      }

      // ok, we can neither parse the parameter values to primitive types,
      // nor
      // are they class names. So let's use them as is.
      parsed.clear();
      for (final Object value : values) {
        propertyValue = this.m_owner._normalize(value);
        if (propertyValue instanceof String) {
          stringValue = ((String) propertyValue);
        } else {
          stringValue = this.m_owner._normalize(String
              .valueOf(propertyValue));
        }
        q = this._createPropertyValue(
            this.m_owner._normalize(stringValue),
            this.m_owner._normalize(descriptions.get(value)),
            this.m_owner._normalize(propertyValue));

        if (parsed.containsKey(q.m_name)) {
          throw new IllegalArgumentException(//
              "Property value name clash in property '" + //$NON-NLS-1$
                  rec.m_propertyName + "' for value '" //$NON-NLS-1$
                  + q.m_value + "' with name '" //$NON-NLS-1$
                  + q.m_name + "' and description '" + //$NON-NLS-1$
                  q.m_description + "' -- how the *** did that happen?"); //$NON-NLS-1$
        }

        parsed.put(q.m_name, q);
      }
    }

    propertyValues = parsed.values().toArray(
        new _PropertyValue[parsed.size()]);
    if (propertyValues.length > values.length) {
      throw new IllegalStateException(//
          "Property value set of property '" + //$NON-NLS-1$
              rec.m_propertyName + //
              "' is inconsistent."); //$NON-NLS-1$
    }
    Arrays.sort(propertyValues, this);

    n = this.m_owner._normalize(rec.m_propertyName);
    return this._createProperty(n, this.m_owner
        ._normalize(_PropertyFSMPropertiesBuilder.__normalizeDesc(n,
            rec.m_propertyDesc)), type, propertyValues,
        (refCount < totalCount));
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public final int compare(final _PropertyValue<?> o1,
      final _PropertyValue<?> o2) {
    Object v1, v2;

    if (o1 == o2) {
      return 0;
    }
    if (o1 == null) {
      return (1);
    }
    if (o2 == null) {
      return (-1);
    }

    if (o1.m_id < o2.m_id) {
      return (-1);
    }
    if (o1.m_id > o2.m_id) {
      return 1;
    }

    v1 = o1.m_value;
    v2 = o2.m_value;

    if (v1 == v2) {
      return 0;
    }
    if (v1 == null) {
      return (1);
    }
    if (v2 == null) {
      return (-1);
    }

    try {
      return ((Comparable) v1).compareTo(v2);
    } catch (final Throwable tt) {
      //
    }
    try {
      return (-(((Comparable) v2).compareTo(v1)));
    } catch (final Throwable tt) {
      //
    }

    return String.valueOf(v1).compareTo(String.valueOf(v2));
  }

  /** {@inheritDoc} */
  @Override
  final PST _doCompile(
      final HashMap<String, _PropertyFSMPropertyRecord> map,
      final int count) {
    int i;
    final ArrayList<PT> list;
    PT prop;

    i = map.size();
    list = new ArrayList<>(i);
    for (final _PropertyFSMPropertyRecord rec : map.values()) {
      synchronized (rec) {
        prop = this.__mapProperty(rec, count);
      }
      if (prop != null) {
        list.add(prop);
      }
    }

    i = list.size();
    if (i <= 0) {
      throw new IllegalStateException(//
          "No properties defined in map " //$NON-NLS-1$
              + map + '.');
    }

    return this._createPropertySet(list.toArray(new _Property[i]));
  }

  /** {@inheritDoc} */
  @Override
  final _PropertySet<?, ?, ?> _getPropertySet() {
    return this._getCompilationResult();
  }
}
