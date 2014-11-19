package org.optimizationBenchmarking.utils.document.impl.abstr;

import java.nio.file.Path;
import java.util.Map;

import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.predicates.IPredicate;

/** a figure file selector */
final class _FigureFileSelector implements
    IPredicate<Map.Entry<Path, IFileType>> {

  /** create */
  static final _FigureFileSelector INSTANCE = new _FigureFileSelector();

  /** {@inheritDoc} */
  @Override
  public final boolean check(final Map.Entry<Path, IFileType> object) {
    return (object.getValue() instanceof EGraphicFormat);
  }

}
