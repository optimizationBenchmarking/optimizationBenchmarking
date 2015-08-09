package org.optimizationBenchmarking.utils.tools.impl.browser;

import org.optimizationBenchmarking.utils.collections.iterators.IterableIterator;
import org.optimizationBenchmarking.utils.collections.maps.StringMapCI;

/** An iterator for browser descriptions */
final class _BrowserDescs extends
    IterableIterator<StringMapCI<_BrowserDesc>> {

  /** the index */
  private int m_index;

  /** the map */
  private final StringMapCI<_BrowserDesc> m_map;

  /** create */
  _BrowserDescs() {
    super();
    this.m_map = new StringMapCI<>();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean hasNext() {
    return (this.m_index <= 4);
  }

  /** {@inheritDoc} */
  @Override
  public final StringMapCI<_BrowserDesc> next() {
    final StringMapCI<_BrowserDesc> map;

    map = this.m_map;
    map.clear();

    switch (this.m_index) {

      case 0: {
        // Browsers which I can execute and wait for their termination
        // reliably.
        map.put("iexplore", //$NON-NLS-1$
            new _BrowserDesc(true, true,
                new String[] { "-noframemerging" })); //$NON-NLS-1$
        map.put("chromium", //$NON-NLS-1$
            new _BrowserDesc(true)); // not tested, probably same as below
        map.put("chromium-browser", //$NON-NLS-1$
            new _BrowserDesc(true));
        return map;
      }

      case 1: {
        // chrome under windows can be made reliable, if we make a batch
        // script - under linux i don't know, probably not?
        map.put("chrome", //$NON-NLS-1$
            new _BrowserDesc(true, true, null));
        return map;
      }

      case 2: {
        // Browsers which may terminated whenever they want, but at least I
        // know them.
        map.put("firefox", //$NON-NLS-1$
            new _BrowserDesc(false));
        map.put("opera", //$NON-NLS-1$
            new _BrowserDesc(false));
        return map;
      }

      case 3: {
        // Browsers I have never used, so I know nothing about them.
        map.put("edge", //$NON-NLS-1$
            new _BrowserDesc(false));
        map.put("spartan", //$NON-NLS-1$
            new _BrowserDesc(false));
        map.put("iceweasel", //$NON-NLS-1$
            new _BrowserDesc(false));
        map.put("safari", //$NON-NLS-1$
            new _BrowserDesc(false));
        map.put("netscape", //$NON-NLS-1$
            new _BrowserDesc(false));
        map.put("seamonkey", //$NON-NLS-1$
            new _BrowserDesc(false));
        map.put("konquerer", //$NON-NLS-1$
            new _BrowserDesc(false));
        map.put("kmelon", //$NON-NLS-1$
            new _BrowserDesc(false));
        map.put("konquerer", //$NON-NLS-1$
            new _BrowserDesc(false));
        map.put("lynx", //$NON-NLS-1$
            new _BrowserDesc(false));
        map.put("icecat", //$NON-NLS-1$
            new _BrowserDesc(false));
        map.put("galeon", //$NON-NLS-1$
            new _BrowserDesc(false));
        return map;
      }

      case 4: {
        // Programs which are not actual programs but instead run the
        // system default browser for us.
        map.put("xdg-open", //$NON-NLS-1$
            new _BrowserDesc(false));
        map.put("gnome-open", //$NON-NLS-1$
            new _BrowserDesc(false));
        map.put("cygstart", //$NON-NLS-1$
            new _BrowserDesc(false));
        map.put("explorer", //$NON-NLS-1$
            new _BrowserDesc(false));
        map.put("open", //$NON-NLS-1$
            new _BrowserDesc(false));
        return map;
      }

      default: {
        return super.next();
      }
    }
  }
}
