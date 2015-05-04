package org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** A builder for organization objects. */
public final class BibOrganizationBuilder extends
    BuilderFSM<BibOrganization> {

  /** the organization name has been set */
  private static final int FLAG_NAME_SET = (FSM.FLAG_NOTHING + 1);
  /** the organization address has been set */
  private static final int FLAG_ADDRESS_SET = (BibOrganizationBuilder.FLAG_NAME_SET << 1);
  /** the original spelling has been set */
  private static final int FLAG_ORIGINAL_SET = (BibOrganizationBuilder.FLAG_ADDRESS_SET << 1);

  /** the name */
  private String m_name;
  /** the address */
  private String m_address;
  /**
   * the full name of the author, in case that there is 1:1 English
   * transcript of the name
   */
  private String m_originalSpelling;

  /** the tag */
  final int m_tag;

  /** do we need a name? */
  private final boolean m_needsName;

  /**
   * create the organization builder
   *
   * @param owner
   *          the owner
   * @param tag
   *          the tag
   * @param needsName
   *          do we need a name?
   */
  BibOrganizationBuilder(final HierarchicalFSM owner, final int tag,
      final boolean needsName) {
    super(owner);
    this.m_tag = tag;
    this.m_needsName = needsName;
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_NAME_SET: {
        append.append("nameSet"); //$NON-NLS-1$
        return;
      }
      case FLAG_ADDRESS_SET: {
        append.append("addressSet"); //$NON-NLS-1$
        return;
      }
      case FLAG_ORIGINAL_SET: {
        append.append("originalSpellingSet"); //$NON-NLS-1$
        return;
      }
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
    }
  }

  /**
   * Set the name
   *
   * @param name
   *          the name
   */
  public synchronized final void setName(final String name) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibOrganizationBuilder.FLAG_NAME_SET),
        BibOrganizationBuilder.FLAG_NAME_SET, FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if ((this.m_name = this.normalize(name)) == null) {
      throw new IllegalArgumentException(//
          "Organization name cannot be set to empty or null, but '" //$NON-NLS-1$
              + name + "' is."); //$NON-NLS-1$
    }
  }

  /**
   * Set the original spelling
   *
   * @param orig
   *          the original spelling
   */
  public synchronized final void setOriginalSpelling(final String orig) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibOrganizationBuilder.FLAG_ORIGINAL_SET),
        BibOrganizationBuilder.FLAG_ORIGINAL_SET, FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if ((this.m_originalSpelling = this.normalize(orig)) == null) {
      throw new IllegalArgumentException(//
          "The original spelling of an organization name and address cannot be set to empty or null, but '" //$NON-NLS-1$
              + orig + "' is."); //$NON-NLS-1$
    }
  }

  /**
   * Set the address
   *
   * @param address
   *          the address
   */
  public synchronized final void setAddress(final String address) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibOrganizationBuilder.FLAG_ADDRESS_SET),
        BibOrganizationBuilder.FLAG_ADDRESS_SET, FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if ((this.m_address = this.normalize(address)) == null) {
      throw new IllegalArgumentException(//
          "Organization address cannot be set to empty or null, but '" //$NON-NLS-1$
              + address + "' is."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final BibOrganization compile() {
    return new BibOrganization(true, this.m_name, this.m_address,
        this.m_originalSpelling, this.m_needsName);
  }
}
