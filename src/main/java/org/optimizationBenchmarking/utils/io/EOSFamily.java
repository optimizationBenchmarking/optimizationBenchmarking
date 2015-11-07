package org.optimizationBenchmarking.utils.io;

import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * An enum of common operating system families. The value of
 * {@link #DETECTED} corresponds to the family of the OS o the current
 * computer. This enum has been inspired by
 * http://stackoverflow.com/questions/228477 and may be refined and
 * extended in the future by taking into account the values at
 * http://www.osgi.org/Specifications/Reference.
 */
public enum EOSFamily {
  /** The Apply Mac OS */
  MacOS,
  /** Microsoft Windows */
  Windows,
  /** Linux */
  Linux,
  /** Generic/other/unknown OS: i.e., none of the above */
  Unknown;

  /** The detected operating system family */
  public static final EOSFamily DETECTED = EOSFamily.__detect();

  /**
   * detect the current operating system
   *
   * @return the operating system
   */
  @SuppressWarnings("unused")
  private static final EOSFamily __detect() {
    String name;

    try {
      name = System.getProperty("os.name"); //$NON-NLS-1$
      if (name != null) {
        name = TextUtils.toLowerCase(name);

        if (name.contains("mac") || name.contains("darwin")) {//$NON-NLS-1$//$NON-NLS-2$
          return MacOS;
        }
        if (name.contains("win")) {//$NON-NLS-1$
          return Windows;
        }
        if (name.contains("nux")) {//$NON-NLS-1$
          return Linux;
        }
      }
    } catch (final Throwable error) {
      // ignore
    }
    return Unknown;
  }
}
