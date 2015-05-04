package org.optimizationBenchmarking.utils.graphics.style.color;

import java.util.Iterator;

import org.optimizationBenchmarking.utils.graphics.style.PaletteElementBuilder;
import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;

/**
 * A builder for a color style
 */
public class ColorStyleBuilder extends PaletteElementBuilder<ColorStyle> {

  /** the name of the palette entry */
  private String m_name;

  /** the rgb value */
  private int m_rgb;

  /**
   * create
   *
   * @param owner
   *          the owning palette builder
   */
  protected ColorStyleBuilder(final ColorPaletteBuilder owner) {
    super(owner);
    this.open();
  }

  /** create! */
  public ColorStyleBuilder() {
    this(null);
  }

  /**
   * Set the name of the color
   *
   * @param name
   *          the name of the color
   */
  public synchronized final void setName(final String name) {
    final String s;

    this.fsmStateAssert(BuilderFSM.STATE_OPEN);

    s = this.normalize(name);
    if (s != null) {
      this.m_name = name;
    } else {
      throw new IllegalArgumentException(//
          "Name must not be null or empty, but is '" //$NON-NLS-1$
          + name + '\'');
    }
  }

  /**
   * Set the rgb value of this color builder
   *
   * @param rgb
   *          the rgb value
   */
  public synchronized final void setRGB(final int rgb) {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    this.m_rgb = ColorStyleBuilder.__checkRGB(rgb);
  }

  /**
   * check an rgb value
   *
   * @param rgb
   *          the rgb value
   * @return the check result
   */
  private static final int __checkRGB(final int rgb) {
    final int r;
    r = (rgb & 0xffffff);
    if (r == 0) {
      throw new IllegalArgumentException("Cannot build black.");//$NON-NLS-1$
    }
    if (r == 0xffffff) {
      throw new IllegalArgumentException("Cannot build white.");//$NON-NLS-1$
    }
    return r;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void fromStrings(final Iterator<String> strings) {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    try {
      this.setRGB(Integer.parseInt(strings.next(), 16));
      this.setName(strings.next());
    } catch (final Throwable t) {
      throw new IllegalArgumentException(('\'' + strings.toString())
          + "' is not a valid color.", t); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final ColorStyle compile() {
    return new ColorStyle(this.m_name,
        ColorStyleBuilder.__checkRGB(this.m_rgb));
  }

}
