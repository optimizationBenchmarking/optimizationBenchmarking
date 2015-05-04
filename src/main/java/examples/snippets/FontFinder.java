package examples.snippets;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * <p>
 * With this program, I try to find which fonts I can locate via Java.
 * </p>
 */
public final class FontFinder {

  /**
   * List all the potential names of fonts available on the linux system
   *
   * @return the list of strings that may identify fonts
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private static final ArraySetView<String> __getFonts() {
    final HashSet<String> fonts;
    final ProcessBuilder pb;
    final String[] ss;
    final Process p;
    String s, t;
    int i, j, k;

    System.out.println("Trying to find the fonts on the system."); //$NON-NLS-1$
    fonts = new HashSet<>();

    pb = new ProcessBuilder();
    pb.command("fc-list"); //$NON-NLS-1$
    try {
      p = pb.start();
      try (final InputStream is = p.getInputStream()) {
        try (final InputStreamReader isr = new InputStreamReader(is,
            "UTF-8")) { //$NON-NLS-1$
          try (final BufferedReader br = new BufferedReader(isr)) {
            while ((s = br.readLine()) != null) {
              s = TextUtils.prepare(s);
              if (s != null) {
                i = s.lastIndexOf('/');
                if (i > 0) {
                  i++;
                  j = s.indexOf(':', i);
                  k = s.indexOf('.', i);
                  if (j < i) {
                    j = k;
                  }
                  if ((k < i) || (k > j)) {
                    k = j;
                  }
                  t = TextUtils.prepare(s.substring(i, k));
                  if (t != null) {
                    fonts.add(t);
                    j++;
                    k = s.indexOf(':', j);
                    if (k > j) {
                      t = TextUtils.prepare(s.substring(j, k));
                      if (t != null) {
                        fonts.add(t);
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }

      p.waitFor();
      p.destroy();

    } catch (final Throwable x) {
      x.printStackTrace();
      return ((ArraySetView) (ArraySetView.EMPTY_SET_VIEW));
    }

    ss = fonts.toArray(new String[fonts.size()]);
    Arrays.sort(ss);
    System.out.println("Found " + ss.length + //$NON-NLS-1$
        " font names on the system."); //$NON-NLS-1$
    return new ArraySetView<>(ss);
  }

  /**
   * This method tries to load every single font that it can
   *
   * @param fonts
   *          the fonts
   */
  private static final void __checkFonts(final ArrayListView<String> fonts) {
    Font load;
    String ff, lc;
    int size, style, found;

    System.out.println(//
        "Now checking which fonts can be accessed by Java."); //$NON-NLS-1$

    found = 0;
    outer: for (final String font : fonts) {
      lc = font.toLowerCase();
      for (size = 5; size < 16; size++) {
        for (style = 0; style <= 3; style++) {
          try {
            load = new Font(font, //
                (((style & 1) != 0) ? Font.BOLD : 0) | //
                    (((style & 2) != 0) ? Font.ITALIC : 0), size);

            ff = load.getFontName();
            ff = ff.toLowerCase();
            if (ff.equalsIgnoreCase(font) || ff.contains(lc)
                || lc.contains(ff)) {
              System.out.print('\'');
              System.out.print(font);
              System.out.print("' loaded as '"); //$NON-NLS-1$
              System.out.print(ff);
              System.out.print("' (ps: '"); //$NON-NLS-1$
              System.out.print(load.getPSName());
              System.out.print("') with "); //$NON-NLS-1$
              System.out.print(load.getSize2D());
              System.out.print("pt"); //$NON-NLS-1$
              if (load.isBold()) {
                System.out.print(" bold"); //$NON-NLS-1$
              }
              if (load.isItalic()) {
                System.out.print(" italic"); //$NON-NLS-1$
              }
              if (load.isPlain()) {
                System.out.print(" plain"); //$NON-NLS-1$
              }
              System.out.println();
              found++;
              continue outer;
            }
          } catch (final Throwable ttt) {
            ttt.printStackTrace();
          }
        }
      }
    }

    System.out.println("Java can access " + found + //$NON-NLS-1$
        " of the " + fonts.size() + //$NON-NLS-1$
        " font names."); //$NON-NLS-1$
  }

  /**
   * The main routine
   *
   * @param args
   *          ignored
   * @throws Throwable
   *           ignore
   */
  public static final void main(final String[] args) throws Throwable {
    FontFinder.__checkFonts(FontFinder.__getFonts());
  }

  /** the internal font size */
  private FontFinder() {
    ErrorUtils.doNotCall();
  }
}
