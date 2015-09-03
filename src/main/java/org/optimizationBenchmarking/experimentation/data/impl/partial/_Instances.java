package org.optimizationBenchmarking.experimentation.data.impl.partial;

import java.util.ArrayList;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractInstanceSet;
import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractNamedElement;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/** the basic instance set */
final class _Instances extends AbstractInstanceSet {

  /** the internal list view with the instances */
  private ArrayListView<IInstance> m_instances;
  /** the list of instances */
  private final ArrayList<_Instance> m_instanceList;
  /** do we need a new instance? */
  boolean m_needsNew;

  /**
   * create
   *
   * @param owner
   *          the owner
   */
  _Instances(final _Experiments owner) {
    super(owner);
    this.m_instanceList = new ArrayList<>();
    this.m_needsNew = true;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public final ArrayListView<IInstance> getData() {
    if (this.m_instances == null) {
      this.m_instances = ((ArrayListView) (ArrayListView
          .collectionToView(this.m_instanceList)));
    }
    return this.m_instances;
  }

  /**
   * get the instance
   *
   * @param forceNew
   *          do we need a new one
   * @return the instance
   */
  final _Instance _getInstance(final boolean forceNew) {
    final _Instance instance;
    final int size;

    size = this.m_instanceList.size();
    if (forceNew || this.m_needsNew || (size <= 0)) {
      instance = new _Instance(this);
      this.m_instanceList.add(instance);
      this.m_needsNew = false;
      this.m_instances = null;
      return instance;
    }
    return this.m_instanceList.get(size - 1);
  }

  /**
   * Get a instance of the given name
   *
   * @param name
   *          the name
   * @return the instance
   */
  final _Instance _getInstanceForName(final String name) {
    final String useName;
    _Instance ninstance;

    useName = AbstractNamedElement.formatName(name);
    for (final _Instance instance : this.m_instanceList) {
      if (EComparison.equals(instance.m_name, useName)) {
        return instance;
      }
    }

    ninstance = this._getInstance(false);
    if (ninstance.m_name != null) {
      ninstance = this._getInstance(true);
    }
    ninstance.m_name = useName;
    return ninstance;
  }
}
