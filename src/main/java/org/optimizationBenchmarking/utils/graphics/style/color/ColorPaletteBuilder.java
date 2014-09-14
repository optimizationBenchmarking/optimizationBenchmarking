package org.optimizationBenchmarking.utils.graphics.style.color;

import java.util.ArrayList;

import org.optimizationBenchmarking.utils.graphics.style.PaletteBuilder;
import org.optimizationBenchmarking.utils.graphics.style.PaletteElementBuilder;

/**
 * A builder for color palettes.
 */
public class ColorPaletteBuilder extends
    PaletteBuilder<ColorStyle, ColorPalette> {

  /** create */
  public ColorPaletteBuilder() {
    super(null);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final PaletteElementBuilder<ColorStyle> createElementBuilder() {
    return new ColorStyleBuilder(this);
  }

  /** {@inheritDoc} */
  @Override
  protected ColorPalette createPalette(final ArrayList<ColorStyle> data) {
    return new ColorPalette(data.toArray(new ColorStyle[data.size()]));
  }

  /** {@inheritDoc} */
  @Override
  protected void beforeAdd(final ColorStyle element) {
    final int rgb;

    rgb = (element.getRGB() & 0xffffff);
    if ((rgb == 0) || (rgb == ColorPalette.BLACK.getRGB())) {
      throw new IllegalArgumentException(//
          "Cannot add black to the palette."); //$NON-NLS-1$
    }

    if ((rgb == 0xffffff) || (rgb == ColorPalette.WHITE.getRGB())) {
      throw new IllegalArgumentException(//
          "Cannot add white to the palette."); //$NON-NLS-1$
    }
  }
}
