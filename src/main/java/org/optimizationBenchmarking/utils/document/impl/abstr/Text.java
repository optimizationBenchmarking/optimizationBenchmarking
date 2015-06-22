package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.numbers.SimpleNumberAppender;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** The base class for text and math content */
public abstract class Text extends DocumentPart implements IText {

  /** the encoded text output */
  ITextOutput m_encoded;

  /**
   * Create a text.
   *
   * @param owner
   *          the owning FSM
   */
  Text(final DocumentElement owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  protected synchronized void onOpen() {
    final ITextOutput to;
    DocumentElement fsm;
    Text pt;

    super.onOpen();

    to = this.getTextOutput();

    // We try to find if a shared encoded stream can be used. If any owner
    // is a Text and has the same raw text output as this object, then
    // we can simply use that one as well. Otherwise, we need to invoke
    // Document.encode.
    looper: for (fsm = this._owner(); fsm != null; fsm = fsm._owner()) {
      if (fsm instanceof Text) {
        pt = ((Text) fsm);
        if (pt.isTextOutputSame(to)) {
          this.m_encoded = pt.m_encoded;
          return;
        }
        break looper;
      }
    }

    this.m_encoded = this.m_driver.encode(to);
  }

  /**
   * Obtain the text output destination to write to
   *
   * @return the text output destination to write to
   */
  protected final ITextOutput encodedText() {
    return this.m_encoded;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized ITextOutput append(final CharSequence csq) {
    this.assertNoChildren();
    this.m_encoded.append(csq);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized ITextOutput append(final CharSequence csq,
      final int start, final int end) {
    this.assertNoChildren();
    this.m_encoded.append(csq, start, end);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized ITextOutput append(final char c) {
    this.assertNoChildren();
    this.m_encoded.append(c);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void append(final String s) {
    this.assertNoChildren();
    this.m_encoded.append(s);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void append(final String s, final int start,
      final int end) {
    this.assertNoChildren();
    this.m_encoded.append(s, start, end);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void append(final char[] chars) {
    this.assertNoChildren();
    this.m_encoded.append(chars);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void append(final char[] chars, final int start,
      final int end) {
    this.assertNoChildren();
    this.m_encoded.append(chars, start, end);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void append(final byte v) {
    this.assertNoChildren();
    this.m_encoded.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void append(final short v) {
    this.assertNoChildren();
    this.m_encoded.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void append(final int v) {
    this.assertNoChildren();
    this.m_encoded.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void append(final long v) {
    this.assertNoChildren();
    this.m_encoded.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void append(final float v) {
    this.assertNoChildren();
    SimpleNumberAppender.INSTANCE.appendTo(v, ETextCase.IN_SENTENCE,
        this.m_encoded);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void append(final double v) {
    this.assertNoChildren();
    SimpleNumberAppender.INSTANCE.appendTo(v, ETextCase.IN_SENTENCE,
        this.m_encoded);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void append(final boolean v) {
    this.assertNoChildren();
    this.m_encoded.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void append(final Object o) {
    this.assertNoChildren();

    if (o instanceof Integer) {
      this.append(((Integer) o).intValue());
      return;
    }
    if (o instanceof Long) {
      this.append(((Long) o).longValue());
      return;
    }
    if (o instanceof Double) {
      this.append(((Double) o).doubleValue());
      return;
    }
    if (o instanceof Float) {
      this.append(((Float) o).floatValue());
      return;
    }
    if (o instanceof Short) {
      this.append(((Short) o).shortValue());
      return;
    }
    if (o instanceof Byte) {
      this.append(((Byte) o).byteValue());
      return;
    }

    this.m_encoded.append(o);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void appendLineBreak() {
    this.assertNoChildren();
    this.m_encoded.appendLineBreak();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void appendNonBreakingSpace() {
    this.assertNoChildren();
    this.m_encoded.appendNonBreakingSpace();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void flush() {
    this.m_encoded.flush();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    this.fsmStateAssertAndSet(DocumentElement.STATE_ALIFE,
        DocumentElement.STATE_DEAD);
    super.onClose();
  }
}
