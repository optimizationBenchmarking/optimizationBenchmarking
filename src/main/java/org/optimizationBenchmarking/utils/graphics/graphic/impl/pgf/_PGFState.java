package org.optimizationBenchmarking.utils.graphics.graphic.impl.pgf;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

/** the state of a PGF graphic */
final class _PGFState {

  /** the font */
  final Font m_font;

  /** the clip */
  final Shape m_clip;

  /** the transform */
  final AffineTransform m_transform;

  /** the foreground color */
  final Color m_foregroundColor;
  /** the foreground paint */
  final Paint m_foregroundPaint;
  /** the background color */
  final Color m_backgroundColor;
  /** the stroke */
  final Stroke m_stroke;

  /**
   * create a state copy of a given graphic
   *
   * @param font
   *          the font
   * @param clip
   *          the clip
   * @param transform
   *          the transform
   * @param foregroundColor
   *          the foreground color
   * @param foregroundPaint
   *          the foreground paint
   * @param backgroundColor
   *          the background color
   * @param stroke
   *          the stroke
   */
  _PGFState(final Font font, final Shape clip,
      final AffineTransform transform, final Color foregroundColor,
      final Paint foregroundPaint, final Color backgroundColor,
      final Stroke stroke) {
    super();
    this.m_font = font;
    this.m_clip = clip;
    this.m_transform = transform;
    this.m_foregroundColor = foregroundColor;
    this.m_foregroundPaint = foregroundPaint;
    this.m_backgroundColor = backgroundColor;
    this.m_stroke = stroke;
  }

}
