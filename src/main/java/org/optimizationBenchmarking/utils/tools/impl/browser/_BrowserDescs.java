package org.optimizationBenchmarking.utils.tools.impl.browser;

import org.optimizationBenchmarking.utils.collections.iterators.IterableIterator;
import org.optimizationBenchmarking.utils.io.EOSFamily;

/** An iterator for browser descriptions */
final class _BrowserDescs extends IterableIterator<_BrowserMap> {

  /** the index */
  private int m_index;

  /** the map */
  private final _BrowserMap m_map;

  /** the unreliable browser desc */
  private _BrowserDesc m_unreliable;

  /** create */
  _BrowserDescs() {
    super();
    this.m_map = new _BrowserMap();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean hasNext() {
    return (this.m_index <= 3);
  }

  /** {@inheritDoc} */
  @Override
  public final _BrowserMap next() {
    final _BrowserMap map;

    map = this.m_map;
    map.clear();

    switch (this.m_index++) {

      case 0: {
        // Browsers which I can execute and wait for their termination
        // reliably.
        if (EOSFamily.DETECTED == EOSFamily.Windows) {
          map.put("iexplore", //$NON-NLS-1$
              new _BrowserDesc(true, true,
                  new String[] { "-noframemerging" }), //$NON-NLS-1$
              new String[] {//
              "C:/Program Files/Internet Explorer/iexplore.exe"//$NON-NLS-1$
              });
        }
        return map;
      }

      case 1: {
        // Browsers which may terminated whenever they want, but at least I
        // know them.
        this.m_unreliable = new _BrowserDesc(false);
        map.put("firefox", this.m_unreliable, //$NON-NLS-1$
            (EOSFamily.DETECTED == EOSFamily.Windows)//
            ? new String[] {//
            "C:/Program Files/Mozilla Firefox/",//$NON-NLS-1$
                "C:/Program Files (x86)/Mozilla Firefox/",//$NON-NLS-1$
                "C:/Program Files/mozilla.org/Mozilla/",//$NON-NLS-1$
            }
                : new String[] {//
                "/usr/bin/firefox"//$NON-NLS-1$
                });

        map.put("opera", this.m_unreliable, //$NON-NLS-1$
            (EOSFamily.DETECTED == EOSFamily.Windows)//
            ? new String[] {//
            "C:/Program Files (x86)/Opera",//$NON-NLS-1$
                "C:/Program Files/Opera"//$NON-NLS-1$
            }
                : new String[] {//
                "/usr/bin/opera",//$NON-NLS-1$
                });
        map.put("chromium", this.m_unreliable); //$NON-NLS-1$ // not tested, probably same as below
        map.put("chromium-browser", this.m_unreliable, //$NON-NLS-1$
            new String[] {//
            "/usr/bin/chromium-browser",//$NON-NLS-1$
            });
        map.put("chrome", this.m_unreliable); //$NON-NLS-1$
        return map;
      }

      case 2: {
        // Browsers I have never used, so I know nothing about them.

        if (EOSFamily.DETECTED == EOSFamily.Windows) {
          map.put("edge", this.m_unreliable); //$NON-NLS-1$
          map.put("spartan", this.m_unreliable); //$NON-NLS-1$
        }
        map.put("iceweasel", this.m_unreliable); //$NON-NLS-1$
        map.put("safari", this.m_unreliable); //$NON-NLS-1$
        map.put("netscape", this.m_unreliable); //$NON-NLS-1$
        map.put("seamonkey", this.m_unreliable, //$NON-NLS-1$
            (EOSFamily.DETECTED == EOSFamily.Windows)//
            ? new String[] {//
            "C:/Program Files/mozilla.org/SeaMonkey/",//$NON-NLS-1$
                "C:/Program Files/SeaMonkey/"//$NON-NLS-1$
            }
                : null);
        map.put("konqueror", this.m_unreliable); //$NON-NLS-1$
        map.put("kmelon", this.m_unreliable); //$NON-NLS-1$
        map.put("konquerer", this.m_unreliable); //$NON-NLS-1$
        map.put("lynx", this.m_unreliable); //$NON-NLS-1$
        map.put("icecat", this.m_unreliable); //$NON-NLS-1$
        map.put("galeon", this.m_unreliable); //$NON-NLS-1$
        return map;
      }

      case 3: {
        // Programs which are not actual browsers but instead run the
        // system default browser for us.
        map.put("xdg-open", this.m_unreliable); //$NON-NLS-1$
        map.put("gnome-open", this.m_unreliable); //$NON-NLS-1$
        map.put("cygstart", this.m_unreliable); //$NON-NLS-1$
        if (EOSFamily.DETECTED == EOSFamily.Windows) {
          map.put("explorer", this.m_unreliable); //$NON-NLS-1$
        }
        map.put("open", this.m_unreliable); //$NON-NLS-1$
        return map;
      }

      default: {
        return super.next();
      }
    }
  }
}
