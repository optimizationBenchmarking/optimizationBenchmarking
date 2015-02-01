package org.optimizationBenchmarking.utils.graphics.style.color;

import java.awt.image.BufferedImage;

/** the type of image supported by the xhtml driver<@javaAuthorVersion/> */
public enum EColorModel {

  /** 8 bit gray scale */
  GRAY_8_BIT(BufferedImage.TYPE_BYTE_GRAY) {

    /** {@inheritDoc} */
    @Override
    public final ColorPalette getDefaultPalette() {
      return DefaultGrayPalette.getInstance();
    }

    /** {@inheritDoc} */
    @Override
    public final String toString() {
      return "8bit Grayscale"; //$NON-NLS-1$
    }

  },

  /** 15 bit rgb */
  RGB_15_BIT(BufferedImage.TYPE_USHORT_555_RGB) {

    /** {@inheritDoc} */
    @Override
    public final ColorPalette getDefaultPalette() {
      return DefaultColorPalette.getInstance();
    }

    /** {@inheritDoc} */
    @Override
    public final String toString() {
      return "15bit RGB"; //$NON-NLS-1$
    }
  },

  /** 16 bit gray scale */
  GRAY_16_BIT(BufferedImage.TYPE_USHORT_GRAY) {

    /** {@inheritDoc} */
    @Override
    public final ColorPalette getDefaultPalette() {
      return DefaultGrayPalette.getInstance();
    }

    /** {@inheritDoc} */
    @Override
    public final String toString() {
      return "16bit Grayscale"; //$NON-NLS-1$
    }
  },

  /** 16 bit rgb */
  RGB_16_BIT(BufferedImage.TYPE_USHORT_565_RGB) {

    /** {@inheritDoc} */
    @Override
    public final ColorPalette getDefaultPalette() {
      return DefaultColorPalette.getInstance();
    }

    /** {@inheritDoc} */
    @Override
    public final String toString() {
      return "16bit RGB"; //$NON-NLS-1$
    }
  },

  /** 24 bits for rbg */
  RGB_24_BIT(BufferedImage.TYPE_INT_RGB) {

    /** {@inheritDoc} */
    @Override
    public final ColorPalette getDefaultPalette() {
      return DefaultColorPalette.getInstance();
    }

    /** {@inheritDoc} */
    @Override
    public final String toString() {
      return "24bit RGB"; //$NON-NLS-1$
    }
  },

  /** 32 bits for RGB and alpha */
  ARGB_32_BIT(BufferedImage.TYPE_INT_ARGB) {

    /** {@inheritDoc} */
    @Override
    public final ColorPalette getDefaultPalette() {
      return DefaultColorPalette.getInstance();
    }

    /** {@inheritDoc} */
    @Override
    public final String toString() {
      return "32bit RGBA"; //$NON-NLS-1$
    }
  };

  /**
   * the buffered image type, for use with
   * {@link java.awt.image.BufferedImage}
   */
  private final int m_biType;

  /**
   * Create the image color model
   * 
   * @param biType
   *          the buffered image type, for use with
   *          {@link java.awt.image.BufferedImage}
   */
  private EColorModel(final int biType) {
    this.m_biType = biType;
  }

  /**
   * Get the color model type to be used when instantiating the
   * {@link java.awt.image.BufferedImage buffered image}
   * 
   * @return the color model type to be used when instantiating the
   *         {@link java.awt.image.BufferedImage buffered image}
   */
  public final int getBufferedImageType() {
    return this.m_biType;
  }

  /**
   * get the default palette for this graphics model
   * 
   * @return the default palette for this graphics model
   */
  public abstract ColorPalette getDefaultPalette();
}
