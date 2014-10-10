package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.ILabeledObject;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/**
 * The base class for sections, tables, and figures
 */
public abstract class ComplexObject extends DocumentPart implements
    ILabeledObject {

  /** the index in the owning context */
  final int m_index;

  /** the full id of the object, globally identifying it */
  final String m_globalID;

  /** the short id of the object, only identifying it in the owning context */
  final String m_localID;

  /** the label of the object */
  final Label m_label;

  /**
   * Create a complex object
   * 
   * @param owner
   *          the owning section body
   * @param index
   *          the table index in the owning section
   * @param useLabel
   *          the label to use
   */
  ComplexObject(final DocumentPart owner, final ILabel useLabel,
      final int index) {
    super(owner);

    final String oid;

    if (index <= 0) {
      throw new IllegalArgumentException(//
          "Object index must be greater than 0, but is " //$NON-NLS-1$
              + index);
    }

    this._constructorHook(owner);
    this.m_index = index;
    this.m_localID = this.renderIndex(index);

    oid = ComplexObject._getOwnerID(owner);
    if (oid != null) {
      this.m_globalID = (oid + this.m_localID);
    } else {
      this.m_globalID = this.m_localID;
    }

    if (useLabel != null) {
      this.m_label = this.m_doc.m_manager._getLabel(this._labelType(),
          useLabel, this.m_globalID);
    } else {
      this.m_label = null;
    }
  }

  /**
   * An internal hook used in the constructor, needed to implement the
   * behavior of sections correctly. This method is called before
   * initializing the members of this class
   * 
   * @param owner
   *          the owning object
   */
  void _constructorHook(final DocumentPart owner) {
    //
  }

  /**
   * Get the label type
   * 
   * @return the label type
   */
  abstract ELabelType _labelType();

  /**
   * Get the owner id
   * 
   * @param owner
   *          the owner
   * @return the id of the owner
   */
  @SuppressWarnings("resource")
  static final String _getOwnerID(final DocumentElement owner) {
    HierarchicalFSM h;

    h = owner;
    while (h != null) {
      if (h instanceof DocumentElement) {
        if (h instanceof ComplexObject) {
          return ((ComplexObject) h).m_globalID;
        }
        h = ((DocumentElement) h)._owner();
      } else {
        break;
      }
    }

    return null;
  }

  /**
   * Render a string to an index
   * 
   * @param index
   *          the index to render
   * @return a string
   */
  protected String renderIndex(final int index) {
    return (Integer.toString(index) + '.');
  }

  /**
   * Get the label of this object
   * 
   * @return the label of this object, or {@code null} if it has none
   */
  @Override
  public final Label getLabel() {
    return this.m_label;
  }

  /**
   * Get the index of the object within the owning context (starts at
   * {@code 1})
   * 
   * @return the index of the object within the owning section
   */
  public final int getIndex() {
    return this.m_index;
  }

  /**
   * Get the global id, which identifies this object globally unique in the
   * document. This id is composed of the rendered indexes through the
   * complete element hierarchy.
   * 
   * @return the globally unique id of this object
   */
  public final String getGlobalID() {
    return this.m_globalID;
  }

  /**
   * Get the local id, which identifies this object within its owning
   * context. It corresponds to a {@link #renderIndex(int) rendered}
   * version of {@link #getIndex()}.
   * 
   * @return the locally unique id of this object within the owning context
   */
  public final String getLocalID() {
    return this.m_localID;
  }
}
