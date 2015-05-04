package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.document.impl.abstr.Label;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;

/** a description of a sub-figure */
final class _XHTML10SubFigureDesc {

  /** the caption */
  final char[] m_caption;

  /** the size */
  final PhysicalDimension m_size;

  /** the path */
  final Path m_path;

  /** the label */
  final Label m_label;

  /** the id */
  final String m_id;

  /**
   * Create the sub-figure descriptor
   *
   * @param caption
   *          the caption
   * @param size
   *          the size
   * @param path
   *          the path
   * @param label
   *          the label
   * @param id
   *          the figure id
   */
  _XHTML10SubFigureDesc(final char[] caption,
      final PhysicalDimension size, final Path path, final Label label,
      final String id) {
    super();
    this.m_caption = caption;
    this.m_size = size;
    this.m_path = path;
    this.m_label = label;
    this.m_id = id;
  }

}
