package org.optimizationBenchmarking.utils.tools.impl.browser;

import java.nio.file.Path;
import java.util.ArrayList;

import org.optimizationBenchmarking.utils.collections.maps.StringMapCI;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;

/** the internal browser map class */
final class _BrowserMap extends StringMapCI<_BrowserDesc> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the paths to visit first */
  private final ArrayList<Path> m_visitFirst;

  /** create */
  _BrowserMap() {
    super();
    this.m_visitFirst = new ArrayList<>();
  }

  /**
   * Put a browser desc, together with paths to visit first
   *
   * @param name
   *          the browser name
   * @param desc
   *          the browser desc
   * @param visitFirst
   *          the paths to visit first
   */
  public final void put(final String name, final _BrowserDesc desc,
      final String[] visitFirst) {

    this.put(name, desc);
    if (visitFirst == null) {
      return;
    }
    for (final String path : visitFirst) {
      this.m_visitFirst.add(PathUtils.normalize(path));
    }
  }

  /**
   * get the paths to visit first
   *
   * @return the paths to visit first
   */
  final Path[] _getVisitFirst() {
    final int size;
    size = this.m_visitFirst.size();
    if (size <= 0) {
      return null;
    }
    return this.m_visitFirst.toArray(new Path[size]);
  }

  /** {@inheritDoc} */
  @Override
  public final void clear() {
    super.clear();
    this.m_visitFirst.clear();
  }
}
