package org.optimizationBenchmarking.experimentation.data.impl.ref;

import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.parsers.LooseByteParser;
import org.optimizationBenchmarking.utils.parsers.LooseDoubleParser;
import org.optimizationBenchmarking.utils.parsers.LooseFloatParser;
import org.optimizationBenchmarking.utils.parsers.LooseIntParser;
import org.optimizationBenchmarking.utils.parsers.LooseLongParser;
import org.optimizationBenchmarking.utils.parsers.LooseShortParser;
import org.optimizationBenchmarking.utils.parsers.StrictByteParser;
import org.optimizationBenchmarking.utils.parsers.StrictDoubleParser;
import org.optimizationBenchmarking.utils.parsers.StrictFloatParser;
import org.optimizationBenchmarking.utils.parsers.StrictIntParser;
import org.optimizationBenchmarking.utils.parsers.StrictLongParser;
import org.optimizationBenchmarking.utils.parsers.StrictShortParser;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** A builder for benchmark instances. */
public final class InstanceContext extends _NamedContext<Instance> {

  /** the properties */
  private volatile _PropertyFSMSettingBuilder<FeatureSetting> m_props;

  /** the lower boundaries */
  private volatile Number[] m_lower;

  /** the upper boundaries */
  private volatile Number[] m_upper;

  /**
   * create
   *
   * @param element
   *          the owning element
   * @param builder
   *          the features builder
   */
  InstanceContext(final _InstanceSetContext element,
      final _FeaturesBuilder builder) {
    super(element);

    final int i;

    i = this._getDimensionSet().m_data.size();
    this.m_lower = new Number[i];
    this.m_upper = new Number[i];
    (this.m_props = new _PropertyFSMSettingBuilder<>(builder))._begin();

    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final _InstanceSetContext getOwner() {
    return ((_InstanceSetContext) (super.getOwner()));
  }

  /**
   * Get the experiment set builder owning this dimension context
   *
   * @return the experiment set builder owning this dimension context
   */
  public final ExperimentSetContext getBuilder() {
    return this.getOwner().getOwner();
  }

  /**
   * Validate a given boundary.
   *
   * @param bound
   *          the boundary
   * @param name
   *          the name of the instance
   * @param isUpper
   *          is this an upper bound?
   * @param dim
   *          the dimension
   */
  static final void _validateBoundary(final Number bound,
      final String name, final boolean isUpper, final Dimension dim) {
    final long l1, l2;
    final double d1, d2;

    if (bound == null) {
      throw new IllegalArgumentException(//
          "Boundaries must not be null, but instance '" + name + //$NON-NLS-1$
              "' has at least one null boundary."); //$NON-NLS-1$
    }

    if (dim != null) {
      try {
        dim.m_parser.validate(bound);
      } catch (final Throwable t) {
        throw new RuntimeException(("Illegal boundary: " + bound), t); //$NON-NLS-1$
      }
    }

    if ((bound instanceof Long) || (bound instanceof Integer)
        || (bound instanceof Short) || (bound instanceof Byte)) {

      l1 = bound.longValue();
      if (isUpper) {
        if (l1 <= Long.MIN_VALUE) {
          throw new IllegalArgumentException(//
              "Integer upper boundaries cannot be " + //$NON-NLS-1$
                  Long.MIN_VALUE);
        }
      } else {
        if (l1 >= Long.MAX_VALUE) {
          throw new IllegalArgumentException(//
              "Integer lower boundaries cannot be " + //$NON-NLS-1$
                  Long.MAX_VALUE);
        }
      }

      if (dim != null) {
        try {
          l2 = dim.m_parser.parseObject(bound).longValue();
        } catch (final Throwable t) {
          throw new RuntimeException(//
              "Error when re-parsing boundary " + bound); //$NON-NLS-1$
        }
        if (l2 != l1) {
          throw new IllegalArgumentException(((((//
              "Loss of fidelity with boundary '" + //$NON-NLS-1$
              l1) + "': parsed to '") + l2) + '\'') + '.'); //$NON-NLS-1$
        }
      }

      return;
    }

    if ((bound instanceof Double) || (bound instanceof Float)) {
      d1 = bound.doubleValue();

      if (isUpper) {
        if ((d1 <= Double.NEGATIVE_INFINITY) || (d1 != d1)) {
          throw new IllegalArgumentException(//
              "Float upper boundaries cannot be NaN or negative infinity, but " + //$NON-NLS-1$
                  d1 + " was provided."); //$NON-NLS-1$
        }
      } else {
        if ((d1 >= Double.POSITIVE_INFINITY) || (d1 != d1)) {
          throw new IllegalArgumentException(//
              "Float lower boundaries cannot be NaN or positive infinity, but " + //$NON-NLS-1$
                  d1 + " was provided."); //$NON-NLS-1$
        }
      }

      if (dim != null) {
        try {
          d2 = dim.m_parser.parseObject(bound).doubleValue();
        } catch (final Throwable t) {
          throw new RuntimeException(//
              "Error when re-parsing boundary " + bound); //$NON-NLS-1$
        }
        if (d2 != d1) {
          throw new IllegalArgumentException(((((//
              "Loss of fidelity with boundary '" + //$NON-NLS-1$
              d1) + "': parsed to '") + d2) + '\'') + '.'); //$NON-NLS-1$
        }
      }

      return;
    }

    throw new IllegalArgumentException(//
        "Class " + TextUtils.className(bound.getClass()) + //$NON-NLS-1$
            " is not allowed for a boundary, but '" + bound + //$NON-NLS-1$
            "', which is an instance of that class, was provided."); //$NON-NLS-1$

  }

  /**
   * get the dimension set
   *
   * @return the dimension set
   */
  final DimensionSet _getDimensionSet() {
    return this.getOwner()._getDimensionSet();
  }

  /**
   * Define a feature with a given name and description
   *
   * @param name
   *          the feature name
   * @param desc
   *          the feature's description
   */
  public synchronized final void declareFeature(final String name,
      final String desc) {
    this.fsmStateAssert(_FSM.STATE_OPEN);
    this.m_props._declareProperty(name, desc);
  }

  /**
   * Set a feature value.
   *
   * @param featureName
   *          the feature name
   * @param featureDescription
   *          the feature description
   * @param featureValue
   *          the feature value
   * @param featureValueDescription
   *          the feature value description
   */
  public synchronized final void setFeatureValue(final String featureName,
      final String featureDescription, final Object featureValue,
      final String featureValueDescription) {
    this.fsmStateAssert(_FSM.STATE_OPEN);
    this.m_props._setPropertyValue(featureName, featureDescription,
        featureValue, featureValueDescription);
  }

  /**
   * Set a feature value.
   *
   * @param featureName
   *          the feature name
   * @param featureValue
   *          the feature value
   * @param featureValueDescription
   *          the feature value description
   */
  public final void setFeatureValue(final String featureName,
      final Object featureValue, final String featureValueDescription) {
    this.setFeatureValue(featureName, null, featureValue,
        featureValueDescription);
  }

  /**
   * Set a feature value.
   *
   * @param featureName
   *          the feature name
   * @param featureValue
   *          the feature value
   */
  public final void setFeatureValue(final String featureName,
      final Object featureValue) {
    this.setFeatureValue(featureName, featureValue, null);
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void onClose() {
    super.onClose();
    this.m_props._end();
    this.m_props._compile();
  }

  /**
   * extract and verify a dimension index
   *
   * @param dim
   *          the dimension
   * @return the index
   */
  private final int __dimIdx(final Dimension dim) {
    final int idx;

    final DimensionSet dims;
    final Dimension exp;

    if (((idx = dim.m_id) >= (dims = this._getDimensionSet()).m_data
        .size()) || (idx < 0)) {
      throw new IllegalArgumentException(((((((//
          "Index of dimension '" + dim)//$NON-NLS-1$
          + "' is not valid in dimension set ") + dims) + //$NON-NLS-1$
          " in instance '") + //$NON-NLS-1$
          this.getName()) + '\'') + '.');
    }

    if (dim == (exp = dims.m_data.get(idx))) {
      if ((idx < 0) || (idx >= this.m_lower.length)) {
        throw new IllegalStateException((((((//
            "Dimension set has changed, index ") + //$NON-NLS-1$
            idx) + " is no longer valid in instance '") + //$NON-NLS-1$
            this.getName()) + '\'') + '.');
      }
      return idx;
    }

    throw new IllegalArgumentException(((((((((("Dimension '" + dim)//$NON-NLS-1$
        + "' is not in the current current dimension set) '" //$NON-NLS-1$
    + dims) + "' where dimension '") //$NON-NLS-1$
        + exp) + "' is at position ")//$NON-NLS-1$
        + idx) + " instead in instance '") + //$NON-NLS-1$
        this.getName()) + '\'') + '.');

  }

  /**
   * Parse a boundary to a number
   *
   * @param bound
   *          the boundary
   * @return the number
   */
  private final Number __parse(final Object bound) {
    Throwable error, tmp;
    Number n1, n2;

    error = null;
    n1 = n2 = null;
    try {
      n1 = StrictLongParser.INSTANCE.parseObject(bound);
      try {
        n2 = StrictIntParser.INSTANCE.parseObject(bound);
        if (n2 != null) {
          n1 = n2;
        }
        n2 = StrictShortParser.INSTANCE.parseObject(bound);
        if (n2 != null) {
          n1 = n2;
        }
        n2 = StrictByteParser.INSTANCE.parseObject(bound);
        if (n2 != null) {
          return n2;
        }
      } catch (final Throwable b) {
        error = b;
      }
      if (n1 != null) {
        return n1;
      }
    } catch (final Throwable a) {
      if (error == null) {
        error = a;
      } else {
        tmp = error;
        error = a;
        error.addSuppressed(tmp);
      }
    }

    n1 = n2 = null;
    try {
      n1 = StrictDoubleParser.INSTANCE.parseObject(bound);
      try {
        n2 = StrictFloatParser.INSTANCE.parseObject(bound);
        if (n2 != null) {
          return n2;
        }
      } catch (final Throwable b) {
        error = b;
      }
      if (n1 != null) {
        return n1;
      }
    } catch (final Throwable a) {
      if (error == null) {
        error = a;
      } else {
        tmp = error;
        error = a;
        error.addSuppressed(tmp);
      }
    }

    n1 = n2 = null;
    try {
      n1 = LooseLongParser.INSTANCE.parseObject(bound);
      try {
        n2 = LooseIntParser.INSTANCE.parseObject(bound);
        if (n2 != null) {
          n1 = n2;
        }
        n2 = LooseShortParser.INSTANCE.parseObject(bound);
        if (n2 != null) {
          n1 = n2;
        }
        n2 = LooseByteParser.INSTANCE.parseObject(bound);
        if (n2 != null) {
          return n2;
        }
      } catch (final Throwable b) {
        error = b;
      }
      if (n1 != null) {
        return n1;
      }
    } catch (final Throwable a) {
      if (error == null) {
        error = a;
      } else {
        tmp = error;
        error = a;
        error.addSuppressed(tmp);
      }
    }

    n1 = n2 = null;
    try {
      n1 = LooseDoubleParser.INSTANCE.parseObject(bound);
      try {
        n2 = LooseFloatParser.INSTANCE.parseObject(bound);
        if (n2 != null) {
          return n2;
        }
      } catch (final Throwable b) {
        error = b;
      }
      if (n1 != null) {
        return n1;
      }
    } catch (final Throwable a) {
      if (error == null) {
        error = a;
      } else {
        tmp = error;
        error = a;
        error.addSuppressed(tmp);
      }
    }

    throw new IllegalArgumentException(((((//
        "Boundary value '" + bound) + //$NON-NLS-1$
        "' cannot be parsed for instance '") + this.getName()) + //$NON-NLS-1$
        '\'') + '.');
  }

  /**
   * Get the lower boundary for the given dimension
   *
   * @param dim
   *          the dimension
   * @return the lower bound
   */
  public synchronized final Number getLowerBound(final Dimension dim) {
    return this.m_lower[this.__dimIdx(dim)];
  }

  /**
   * Set the lower boundary for the given dimension
   *
   * @param dim
   *          the dimension
   * @param bound
   *          the lower bound
   */
  public synchronized final void setLowerBound(final IDimension dim,
      final Number bound) {
    final int index;
    final Number n;
    final Number[] bounds;

    index = this.__dimIdx((Dimension) dim);
    if ((n = (bounds = this.m_lower)[index]) != null) {
      if (n.equals(bound)) {
        return;
      }
      throw new IllegalStateException(((((((((//
          "Lower bound of dimension '" + dim) //$NON-NLS-1$
          + "' has already been set to ") + n) + //$NON-NLS-1$
          " and cannot be set to ") + bound)//$NON-NLS-1$
          + " anymore in instance '") + //$NON-NLS-1$
          this.getName()) + '\'') + '.');
    }
    this.fsmStateAssert(_FSM.STATE_OPEN);
    bounds[index] = bound;
  }

  /**
   * Set the lower boundary for the given dimension
   *
   * @param dim
   *          the dimension
   * @param bound
   *          the lower bound
   */
  public synchronized final void setLowerBound(final Object dim,
      final Object bound) {
    final Dimension d;

    this.fsmStateAssert(_FSM.STATE_OPEN);

    if (dim instanceof Dimension) {
      d = ((Dimension) dim);
    } else {
      d = this._getDimensionSet().find(String.valueOf(dim));
    }
    if (d == null) {
      throw new IllegalArgumentException((((//
          "Dimension '" + dim) + //$NON-NLS-1$
          "' is unknown in instance '" + //$NON-NLS-1$
          this.getName()) + '\'') + '.');
    }

    this.setLowerBound(d, this.__parse(bound));
  }

  /**
   * Get the upper boundary for the given dimension
   *
   * @param dim
   *          the dimension
   * @return the upper bound
   */
  public synchronized final Number getUpperBound(final IDimension dim) {
    return this.m_upper[this.__dimIdx((Dimension) dim)];
  }

  /**
   * Set the upper boundary for the given dimension
   *
   * @param dim
   *          the dimension
   * @param bound
   *          the upper bound
   */
  public synchronized final void setUpperBound(final Dimension dim,
      final Number bound) {
    final int index;
    final Number n;
    final Number[] bounds;

    index = this.__dimIdx(dim);
    if ((n = (bounds = this.m_upper)[index]) != null) {
      if (n.equals(bound)) {
        return;
      }
      throw new IllegalStateException(((((((((//
          "Upper bound of dimension '" + dim) //$NON-NLS-1$
          + "' has already been set to ") + n) + //$NON-NLS-1$
          " and cannot be set to ") + bound)//$NON-NLS-1$
          + " anymore in instance '") + //$NON-NLS-1$
          this.getName()) + '\'') + '.');
    }

    this.fsmStateAssert(_FSM.STATE_OPEN);
    bounds[index] = bound;
  }

  /**
   * Set the upper boundary for the given dimension
   *
   * @param dim
   *          the dimension
   * @param bound
   *          the upper bound
   */
  public synchronized final void setUpperBound(final Object dim,
      final Object bound) {
    final Dimension d;

    this.fsmStateAssert(_FSM.STATE_OPEN);

    if (dim instanceof Dimension) {
      d = ((Dimension) dim);
    } else {
      d = this._getDimensionSet().find(String.valueOf(dim));
    }
    if (d == null) {
      throw new IllegalArgumentException((((//
          "Dimension '" + dim) + //$NON-NLS-1$
          "' is unknown in instance '" + //$NON-NLS-1$
          this.getName()) + '\'') + '.');
    }

    this.setUpperBound(d, this.__parse(bound));
  }

  /** {@inheritDoc} */
  @Override
  synchronized final Instance _doCompile(final String name,
      final String description) {
    final Number[] lower, upper;
    final DimensionSet dims;
    final _PropertyFSMSettingBuilder<FeatureSetting> f;
    final FeatureSetting fs;
    int i;

    lower = this.m_lower;
    this.m_lower = null;
    upper = this.m_upper;
    this.m_upper = null;
    f = this.m_props;
    this.m_props = null;

    dims = this._getDimensionSet();
    for (i = lower.length; (--i) >= 0;) {
      if (lower[i] == null) {
        lower[i] = this.normalize(dims.m_data.get(i).m_defaultLower);
      }
      if (upper[i] == null) {
        upper[i] = this.normalize(dims.m_data.get(i).m_defaultUpper);
      }
    }

    fs = f._finalize();

    return new Instance(name, description, fs, this.normalize(lower),
        this.normalize(upper));
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {
    super.beforeChildOpens(child, hasOtherChildren);
    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {
    super.afterChildOpened(child, hasOtherChildren);
    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {
    super.afterChildClosed(child);
    this.throwChildNotAllowed(child);
  }
}
