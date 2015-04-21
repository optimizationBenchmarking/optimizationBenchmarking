package org.optimizationBenchmarking.experimentation.data.spec;

import java.util.HashMap;

/**
 * A data element: the base-class for all elements of the experimental API.
 */
public abstract class DataElement implements IDataElement {

  /** the attribute map */
  private HashMap<Attribute<?, ?>, Object> m_attributes;

  /** create */
  protected DataElement() {
    super();
  }

  /**
   * Get the value of a given attribute. This is an internal method you
   * should better leave your fingers away from...
   * 
   * @param attribute
   *          the attribute
   * @return the attribute
   * @param <XDT>
   *          the data set type
   * @param <RT>
   *          the property type
   */
  @SuppressWarnings("unchecked")
  protected <XDT extends IDataElement, RT> RT getAttribute(
      final Attribute<XDT, RT> attribute) {
    final EAttributeType type;
    RT computed, ret;
    Object old;

    type = attribute.m_type;

    if (type.m_store) {
      // If the attribute can be stored, we first need to check if it has
      // already been computed and stored. We do this in a synchronized
      // way. We synchronize here just to check if the value is already
      // cached. If yes, we return it. If not, we compute it in an
      // unsynchronized fashion. Then we synchronize again and check
      // whether the attribute has been computed in the meantime. If so, we
      // discard our newly computed value and use the existing one.
      // Otherwise, we cache the new value. We synchronize two times
      // because the actual computation may cost a lot of time and we do
      // not want to block other, unrelated attributes to be computed.
      synchronized (this) {
        if (this.m_attributes != null) {
          old = this.m_attributes.get(attribute);
          if (old != null) {
            ret = type.unpack(old);
            if (ret != null) {
              return ret;
            }
            // Although ret may be a softref that points nowhere, we don't
            // need to delete it, since it will be overwritten soon.
          }
        }
      }
    }

    // OK, the attribute either never is stored and needs to be computed
    // every time or has been purged from the cache before.
    computed = attribute.compute((XDT) this);
    if (computed == null) {
      throw new IllegalStateException(//
          "Computed attribute value must not be null."); //$NON-NLS-1$
    }

    if (type.m_store) {
      // Synchronized again: check if attribute has been stored in the mean
      // time, if not, set it and return it.
      synchronized (this) {
        if (this.m_attributes != null) {
          old = this.m_attributes.get(attribute);
          if (old != null) {
            ret = type.unpack(old);
            if (ret != null) {
              // While we were computing, someone else put a value for the
              // property into the map.
              return ret;
            }
            // Although ret is a softref that points nowhere, we don't need
            // to delete it, since it will be overwritten soon.
          }
        } else {
          this.m_attributes = new HashMap<>();
        }
        this.m_attributes.put(attribute, type.pack(computed));
      }
    }

    return computed;
  }
}
