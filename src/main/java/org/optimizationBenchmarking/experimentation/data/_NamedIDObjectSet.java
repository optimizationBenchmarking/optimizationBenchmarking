package org.optimizationBenchmarking.experimentation.data;

import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * An internal set of id objects.
 * 
 * @param <OT>
 *          the owner type
 * @param <DT>
 *          the type
 */
class _NamedIDObjectSet<OT, DT extends _IDObject<?>> extends
    _IDObjectSet<OT, DT> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** this object's name */
  final String m_name;
  /** the description of this property */
  final String m_description;

  /**
   * instantiate
   * 
   * @param name
   *          the name of the object
   * @param desc
   *          this object's description
   * @param data
   *          the data of the set
   * @param clone
   *          should we clone the data?
   * @param sort
   *          should we sort the data?
   * @param own
   *          should we make all elements our own?
   */
  _NamedIDObjectSet(final String name, final String desc, final DT[] data,
      final boolean clone, final boolean sort, final boolean own) {
    super(data, clone, sort, own);

    if ((this.m_name = TextUtils.normalize(name)) == null) {
      throw new IllegalArgumentException(//
          "Instances of " + TextUtils.className(this.getClass()) + //$NON-NLS-1$
              " must have a non-empty name, but name '" + //$NON-NLS-1$
              name + "' was provided."); //$NON-NLS-1$

    }
    this.m_description = TextUtils.normalize(desc);
  }

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    textOut.append(this.getClass().getSimpleName());
    textOut.append("(name='"); //$NON-NLS-1$
    textOut.append(this.m_name);
    textOut.append("',id="); //$NON-NLS-1$
    textOut.append(this.m_id);
    textOut.append(')');
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return this.m_name;
  }

  /** {@inheritDoc} */
  @Override
  public final String getDescription() {
    return this.m_description;
  }
}
