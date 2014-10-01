package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.impl.object.PathEntry;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.predicates.IPredicate;

/** a figure file selector */
final class _FigureFileSelector implements IPredicate<PathEntry> {

  /** create */
  static final _FigureFileSelector INSTANCE = new _FigureFileSelector();

  /** {@inheritDoc} */
  @Override
  public final boolean check(final PathEntry object) {
    return (object.getKey() instanceof EGraphicFormat);
  }

}
