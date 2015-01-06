package org.optimizationBenchmarking.utils.graphics.style;

import java.io.BufferedReader;

import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.TextInputTool;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** An I/O driver which can load stream contents to a style palette */
public final class PaletteInputDriver extends
    TextInputTool<PaletteBuilder<?, ?>> {

  /** create */
  PaletteInputDriver() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected void reader(final IOJob job, final PaletteBuilder<?, ?> data,
      final BufferedReader reader) throws Throwable {
    String s;

    data.processHeader(reader);
    while ((s = reader.readLine()) != null) {
      s = TextUtils.prepare(s);
      if ((s != null) && (s.charAt(0) != '#')) {
        try (final PaletteElementBuilder<?> peb = data.add()) {
          peb.fromStrings(data.iterate(s));
        }
      }
    }
  }

  /**
   * Get the instance of the PaletteInputDriver
   * 
   * @return the instance
   */
  public static final PaletteInputDriver getInstance() {
    return __PaletteInputDriverLoader.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Style Palette Input"; //$NON-NLS-1$
  }

  /** the internal palette driver loader */
  private static final class __PaletteInputDriverLoader {

    /** the globally shared instance of the palette I/O driver */
    static final PaletteInputDriver INSTANCE = new PaletteInputDriver();

  }
}
