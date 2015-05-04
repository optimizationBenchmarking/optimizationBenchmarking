package org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A bibliographic record which holds organization information.
 * Organizations can be publishers, conference locations, or schools.
 */
public class BibOrganization extends _BibElement<BibOrganization> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * the organization's name
   *
   * @serial the field
   */
  private final String m_name;
  /**
   * the organization's address
   *
   * @serial serial field
   */
  final String m_address;
  /**
   * the full name and address of the organization, in case that there is
   * 1:1 English transcript of the name
   *
   * @serial a string
   */
  private final String m_originalSpelling;

  /**
   * Create the organization record
   *
   * @param name
   *          the name
   * @param address
   *          the address
   * @param originalSpelling
   *          the full name and address of the organization, in case that
   *          there is 1:1 English transcript of the name
   */
  public BibOrganization(final String name, final String address,
      final String originalSpelling) {
    this(false, name, address, originalSpelling, false);
  }

  /**
   * Create the organization record
   *
   * @param direct
   *          direct?
   * @param name
   *          the name
   * @param address
   *          the address
   * @param needsName
   *          is a name needed?
   * @param originalSpelling
   *          the full name and address of the organization, in case that
   *          there is 1:1 English transcript of the name
   */
  BibOrganization(final boolean direct, final String name,
      final String address, final String originalSpelling,
      final boolean needsName) {

    super();

    this.m_name = (direct ? name : TextUtils.normalize(name));
    this.m_address = (direct ? address : TextUtils.normalize(address));

    if (this.m_name == null) {
      if (this.m_address == null) {
        throw new IllegalArgumentException(//
            "Either the name or address of an organization must not be empty or null."); //$NON-NLS-1$
      }
    } else {
      if (this.m_address == null) {
        throw new IllegalArgumentException(//
            "If an organization's name is specified, the address must not be empty."); //$NON-NLS-1$
      }
    }

    this.m_originalSpelling = (direct ? originalSpelling : TextUtils
        .normalize(originalSpelling));
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(HashUtils.hashCode(this.m_name),
        HashUtils.combineHashes(HashUtils.hashCode(this.m_address),
            HashUtils.hashCode(this.m_originalSpelling)));
  }

  /**
   * Get the organization's name
   *
   * @return the organization's name
   */
  public final String getName() {
    return this.m_name;
  }

  /**
   * Get the organization's address
   *
   * @return the organization's address
   */
  public final String getAddress() {
    return this.m_address;
  }

  /**
   * Get the full name and address of the organization, in case that there
   * is 1:1 English transcript of the name
   *
   * @return the full name and address of the organization, in case that
   *         there is 1:1 English transcript of the name
   */
  public final String getOriginalSpelling() {
    return this.m_originalSpelling;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object r) {
    final BibOrganization x;

    if (r == this) {
      return true;
    }
    if (r instanceof BibOrganization) {
      x = ((BibOrganization) r);

      return (EComparison.equals(this.m_name, x.m_name) && //
          EComparison.equals(this.m_address, x.m_address));

    }

    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final BibOrganization o) {
    int r;

    if (o == this) {
      return 0;
    }
    if (o == null) {
      return (-1);
    }
    r = EComparison.compareObjects(this.m_name, o.m_name);
    if (r != 0) {
      return r;
    }
    r = EComparison.compareObjects(this.m_address, o.m_address);
    if (r != 0) {
      return r;
    }
    return EComparison.compareObjects(this.m_originalSpelling,
        o.m_originalSpelling);
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    final boolean ha, hn;

    ha = (this.m_address != null);
    hn = (this.m_name != null);
    if (ha) {
      textOut.append(this.m_address);
      if (hn) {
        textOut.append(':');
        textOut.append(' ');
      }
    }
    if (hn) {
      textOut.append(this.m_name);
    }
    if (this.m_originalSpelling != null) {
      if (ha || hn) {
        textOut.append(' ');
        textOut.append('[');
      }
      textOut.append(this.m_originalSpelling);
      textOut.append(']');
    }
  }
}
