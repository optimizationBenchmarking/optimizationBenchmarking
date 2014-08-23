package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/** A text class */
public class Text extends BasicText implements IText {

  /**
   * Create a text.
   * 
   * @param owner
   *          the owning FSM
   * @param out
   *          the output destination
   */
  public Text(final HierarchicalFSM owner, final Appendable out) {
    super(owner, out);
  }

  /**
   * Create an in-quotes object
   * 
   * @param marks
   *          the marks counter
   * @return the in-quotes object
   */
  protected InQuotes createInQuotes(final int marks) {
    return new InQuotes(this, marks);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public final synchronized InQuotes inQuotes() {
    HierarchicalFSM o;
    int c;

    this.fsmStateAssert(DocumentElement.STATE_ALIFE);

    c = 0;
    o = this;
    for (;;) {

      if (o instanceof DocumentElement) {
        if (o instanceof InQuotes) {
          c = (((InQuotes) o).m_marks + 1);
          break;
        }

        o = ((DocumentElement) o)._owner();
        continue;
      }
      break;
    }

    return this.createInQuotes(c);
  }

  /**
   * Create an in-braces object
   * 
   * @param braces
   *          the braces counter
   * @return the in-braces object
   */
  protected InBraces createInBraces(final int braces) {
    return new InBraces(this, braces);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public final synchronized InBraces inBraces() {
    HierarchicalFSM o;
    int c;

    this.fsmStateAssert(DocumentElement.STATE_ALIFE);

    c = 0;
    o = this;
    for (;;) {

      if (o instanceof DocumentElement) {
        if (o instanceof InBraces) {
          c = (((InBraces) o).m_braces + 1);
          break;
        }

        o = ((DocumentElement) o)._owner();
        continue;
      }
      break;
    }

    return this.createInBraces(c);
  }
}
