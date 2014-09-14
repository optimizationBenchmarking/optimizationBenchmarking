package org.optimizationBenchmarking.utils.graphics.style;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.io.structured.TextIODriver;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** An I/O driver which can load stream contents to a style palette */
public final class PaletteIODriver extends
    TextIODriver<Void, PaletteBuilder<?, ?>> {

  /** the globally shared instance of the palette I/O driver */
  public static final PaletteIODriver INSTANCE = new PaletteIODriver();

  /** create */
  private PaletteIODriver() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected void doLoadReader(final PaletteBuilder<?, ?> loadContext,
      final BufferedReader reader, final Logger logger) throws IOException {
    String s;

    loadContext.processHeader(reader);
    while ((s = reader.readLine()) != null) {
      s = TextUtils.prepare(s);
      if (s != null) {
        try (final PaletteElementBuilder<?> peb = loadContext.add()) {
          peb.fromStrings(loadContext.iterate(s));
        }
      }
    }
  }
}
