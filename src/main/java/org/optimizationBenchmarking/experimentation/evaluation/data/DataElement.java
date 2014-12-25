package org.optimizationBenchmarking.experimentation.evaluation.data;

import java.io.Serializable;
import java.util.HashMap;

import org.optimizationBenchmarking.utils.text.ITextable;

/**
 * A data element: the base-class for all elements of the experimental API.
 */
public abstract class DataElement implements Serializable, ITextable {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the attribute map */
  private HashMap<Attribute<?, ?>, Object> m_attributes;

  /** create */
  DataElement() {
    super();
  }

  /**
   * Obtain the set this object is part of
   * 
   * @return the set this object is part of
   */
  public abstract DataElement getOwner();

  /**
   * Get the value of a given attribute.
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
  final <XDT extends DataElement, RT> RT _getAttribute(
      final Attribute<XDT, RT> attribute) {
    final EAttributeType type;
    RT computed, ret;
    Object old;

    type = attribute.m_type;

    if (type.m_store) {
      // if the attribute can be stored, we first need to check if it has
      // already been computed and stored. We do this in a synchronized
      // way.
      synchronized (this) {
        if (this.m_attributes != null) {
          old = this.m_attributes.get(attribute);
          if (old != null) {
            ret = type.unpack(old);
            if (ret != null) {
              return ret;
            }
            // although ret may be a softref that points nowhere, we don't
            // need to delete it, since it will be overwritten soon
          }
        }
      }
    }

    // ok, the attribute either never is stored and needs to be computed
    // every
    // time or has been purged from the cache before
    computed = attribute.compute((XDT) this);
    if (computed == null) {
      throw new IllegalStateException(//
          "Computed attribute value must not be null."); //$NON-NLS-1$
    }

    if (type.m_store) {
      // Synchronized again: check if attribute has been stored in the mean
      // time, if not, set it and return it
      synchronized (this) {
        if (this.m_attributes != null) {
          old = this.m_attributes.get(attribute);
          if (old != null) {
            ret = type.unpack(old);
            if (ret != null) {
              // while we were computing, someone else put a value for the
              // property into the map
              return ret;
            }
            // although ret is a softref that points nowhere, we don't need
            // to
            // delete it, since it will be overwritten soon
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
