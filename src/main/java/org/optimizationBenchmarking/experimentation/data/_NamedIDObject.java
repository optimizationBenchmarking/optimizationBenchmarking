package org.optimizationBenchmarking.experimentation.data;

import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * An internal set of id objects.
 * 
 * @param <OT>
 *          the owner type
 */
class _NamedIDObject<OT> extends _IDObject<OT> {
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
   *          the description
   */
  _NamedIDObject(final String name, final String desc) {
    super();

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
