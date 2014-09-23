package org.optimizationBenchmarking.utils.graphics.style.color;

import java.awt.Color;
import java.awt.Graphics2D;

import org.optimizationBenchmarking.utils.graphics.style.IStyle;
import org.optimizationBenchmarking.utils.graphics.style.StyleApplication;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.optimizationBenchmarking.utils.text.transformations.NormalCharTransformer;

/** A color style is, well, a color with a name. */
public class ColorStyle extends Color implements IStyle {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the color name */
  private final String m_name;

  /** the color id */
  private final String m_id;

  /**
   * create the color style
   * 
   * @param rgb
   *          the rgb color value
   * @param name
   *          the color name
   */
  public ColorStyle(final int rgb, final String name) {
    this(TextUtils.normalize(name), rgb);
  }

  /**
   * create the color style
   * 
   * @param name
   *          the color name
   * @param rgb
   *          the rgb color value
   */
  ColorStyle(final String name, final int rgb) {
    super(rgb);

    final int s;
    final MemoryTextOutput nb;
    int i;
    char c;

    if (name == null) {
      throw new IllegalArgumentException(//
          "Name must not be empty or null, but is '" //$NON-NLS-1$
              + name + '\'');
    }
    this.m_name = name;

    s = name.length();
    nb = new MemoryTextOutput(s);
    for (i = 0; i < s; i++) {
      c = name.charAt(i);
      if (c > ' ') {
        nb.append(c);
      }
    }

    this.m_id = NormalCharTransformer.INSTANCE.transform(nb.toString(),
        TextUtils.DEFAULT_NORMALIZER_FORM);
  }

  /**
   * Get the color's name
   * 
   * @return the color's name
   */
  @Override
  public final String toString() {
    return this.m_name;
  }

  /** {@inheritDoc} */
  @Override
  public final StyleApplication applyTo(final Graphics2D g) {
    return new _ColorApplication(g, this);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean appendDescription(final ETextCase textCase,
      final ITextOutput dest, final boolean omitDefaults) {
    final ETextCase t;

    if (omitDefaults && (this.getRGB() == Color.BLACK.getRGB())) {
      return false;
    }
    t = ((textCase == null) ? ETextCase.IN_SENTENCE : textCase);
    t.appendWords(this.m_name, dest);
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final String getID() {
    return this.m_id;
  }
}
