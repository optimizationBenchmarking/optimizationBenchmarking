package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.IStyle;
import org.optimizationBenchmarking.utils.document.spec.IStyleContext;

/** A class which can manage styles */
public abstract class Styles implements IStyleContext {

  /** the empty styles */
  private static final IStyle[] EMPTY = new IStyle[0];

  /** the text styles */
  private IStyle[] m_text;

  /** the number of text styles */
  int m_textSize;

  /** the graphical styles */
  private IStyle[] m_graphical;

  /** the number of graphical styles */
  int m_graphicalSize;

  /** the plain style */
  private final IStyle m_plain;

  /** the emphasized style */
  private final IStyle m_emph;

  /** create */
  protected Styles() {
    super();
    this.m_text = Styles.EMPTY;
    this.m_graphical = Styles.EMPTY;
    this.m_plain = this.createPlainStyle();
    this.m_emph = this.createEmphStyle();
  }

  /**
   * Create the plain text style
   * 
   * @return the plain text style
   */
  protected IStyle createPlainStyle() {
    return EDefaultStyles.PLAIN;
  }

  /**
   * Create the emphasize style
   * 
   * @return the emphasize style
   */
  protected IStyle createEmphStyle() {
    return EDefaultStyles.ITALIC;
  }

  /**
   * Create a text style
   * 
   * @param index
   *          the index
   * @param styles
   *          an array of {@code index} elements (from index {@code 0} to
   *          {@code index-1}) of existing styles
   * @return the new text style
   */
  protected abstract IStyle createTextStyle(final int index,
      final IStyle[] styles);

  /**
   * Obtain a the text style at the given index
   * 
   * @param index
   *          the index
   * @return the text style
   */
  public synchronized IStyle getTextStyle(final int index) {
    IStyle[] data;
    int s;

    s = this.m_textSize;
    data = this.m_text;
    if (s > index) {
      return data[s];
    }

    if (index >= data.length) {
      data = new IStyle[(index + 1) << 1];
      System.arraycopy(this.m_text, 0, data, 0, s);
      this.m_text = data;
    }

    for (; s <= index; s++) {
      data[s] = this.createTextStyle(index, data);
    }
    this.m_textSize = s;
    return data[index];
  }

  /** {@inheritDoc} */
  @Override
  public final IStyle createTextStyle() {
    return this.getTextStyle(this.m_textSize + 1);
  }

  /**
   * Create a graphical style
   * 
   * @param index
   *          the index
   * @param styles
   *          an array of {@code index} elements (from index {@code 0} to
   *          {@code index-1}) of existing styles
   * @return the new graphical style
   */
  protected abstract IStyle createGraphicalStyle(final int index,
      final IStyle[] styles);

  /**
   * Obtain a the graphical style at the given index
   * 
   * @param index
   *          the index
   * @return the graphical style
   */
  public synchronized IStyle getGraphicalStyle(final int index) {
    IStyle[] data;
    int s;

    s = this.m_graphicalSize;
    data = this.m_graphical;
    if (s > index) {
      return data[s];
    }

    if (index >= data.length) {
      data = new IStyle[(index + 1) << 1];
      System.arraycopy(this.m_graphical, 0, data, 0, s);
      this.m_graphical = data;
    }

    for (; s <= index; s++) {
      data[s] = this.createGraphicalStyle(index, data);
    }
    this.m_graphicalSize = s;
    return data[index];
  }

  /** {@inheritDoc} */
  @Override
  public final IStyle createGraphicalStyle() {
    return this.getGraphicalStyle(this.m_graphicalSize + 1);
  }

  /** {@inheritDoc} */
  @Override
  public final IStyle emphasized() {
    return this.m_emph;
  }

  /** {@inheritDoc} */
  @Override
  public final IStyle plain() {
    return this.m_plain;
  }
}
