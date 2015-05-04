package examples.snippets;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;

/**
 * A simple and quick test whether I can load type-1 fonts in {@code pfb}
 * format in Java. Turns out I can.
 */
public final class FontLoadingTest {

  /**
   * The main routine
   *
   * @param args
   *          ignored
   */
  public static final void main(final String[] args) {
    Font font;
    try (final InputStream is = ReflectionUtils
        .getResourceAsStream("org.optimizationBenchmarking.utils.document.impl.latex.documentClasses.IEEEtranFontPalette#nimbusMonoLBold.pfb")) { //$NON-NLS-1$
      font = Font.createFont(Font.TYPE1_FONT, is);
      GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
      System.out.println(font);
    } catch (final Throwable ttt) {
      ttt.printStackTrace(); // this should not happen
    }
  }

  /** the forbidden constructor */
  private FontLoadingTest() {
    ErrorUtils.doNotCall();
  }
}
