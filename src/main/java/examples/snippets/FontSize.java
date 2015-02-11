package examples.snippets;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.units.ELength;

/**
 * <p>
 * In this example snippet, I try to find the relationship between the font
 * size in pt and the actual font size.
 * </p>
 * <p>
 * As basis, I create fonts with size 32pt. Since 1 inch = 72 pt, and
 * rastered graphics should default(?) come at 72 dots per inch, I would
 * expect that text written in this font should occupy about 32 pixels in
 * height. Oddly enough, the font and line metrics state differently:
 * </p>
 * <p>
 * For Courier New, the bounding rectangle comes as 36.25 pixel height. Out
 * of these, in a stored image, the text actually occupies 26 pixels in
 * height, which roughly equals its ascent (26.640625) in the line metrics.
 * However, this space also contains the stuff between the baseline, i.e.,
 * the descent. This is more than odd. The ascent above the baseline in the
 * image is 20 pixels, which equals none of the line or font metrics.
 * </p>
 * <p>
 * If a 32pt font occupies 36.25 pixels, then this means that there are
 * 1.1328125 pixels per pt, i.e., 81.5625 dots per inch. If it is the
 * ascent of 26.640625 which counts, that would make 0.832519531 pixel per
 * pt, which equals 59.94140625 dpi. If 20 pixel equal 32 pt, then we would
 * have 45 dpi. Neither of these values even remotely equals something
 * common (say, 72dpi, 96dpi, ...), but 45 seems to be at least a "round"
 * number.
 * </p>
 * <p>
 * For the other fonts, the situation is roughly similar: A 32pt font has a
 * bounding box of a height between 36 and 38. To test whether a logical
 * point also comes out as a pixel, I paint a 144px long rectangle on the
 * Graphics context which really comes out as 144px long in the images.
 * </p>
 */
public final class FontSize {

  /**
   * The main routine
   * 
   * @param args
   *          ignored
   * @throws Throwable
   *           ignore
   */
  public static final void main(final String[] args) throws Throwable {
    Graphics2D g;
    Font f;
    LineMetrics lm;
    FontMetrics fm;
    BufferedImage im;
    String s;
    Rectangle2D b;

    for (final int scale : new int[] { 1, 2 }) {
      for (final String fontName : new String[] { "Courier New", //$NON-NLS-1$
          "Arial", //$NON-NLS-1$
          "Times New Roman" }) {//$NON-NLS-1$
        System.out.println("============ " + fontName + //$NON-NLS-1$
            " ===============");//$NON-NLS-1$
        im = new BufferedImage(500, 200, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) im.getGraphics();
        f = new Font(fontName, Font.PLAIN, 32);
        s = "ABC.J.Q.Zabc.g.j.pq.t.yz"; //$NON-NLS-1$
        g.setFont(f);
        lm = f.getLineMetrics(s, g.getFontRenderContext());
        fm = g.getFontMetrics(f);

        System.out.println(lm.getHeight() + " -- " + fm.getHeight());//$NON-NLS-1$
        System.out.println(lm.getAscent() + " -- " + fm.getAscent());//$NON-NLS-1$
        System.out.println(lm.getDescent() + " -- " + fm.getDescent());//$NON-NLS-1$
        System.out.println(lm.getLeading() + " -- " + fm.getLeading());//$NON-NLS-1$    
        b = fm.getStringBounds(s, g);
        System.out.println(b);

        System.out.println(ELength.IN.convertTo(11d / 72d, ELength.PT));

        g.setColor(Color.white);
        g.fillRect(0, 0, 1000, 1000);

        g.setColor(Color.green);
        g.fillRect(1, 1, 144, 20);

        g.translate(25, 120);
        g.setColor(Color.blue);
        g.fill(b);
        g.setColor(Color.red);
        g.scale(scale, scale);
        g.drawString(s, 0, 0);

        ImageIO.write(im,
            "png", //$NON-NLS-1$
            new File(PathUtils.getTempDir().toFile(), (fontName.replace(
                ' ', '_') + '_' + (scale + ".png")))); //$NON-NLS-1$
      }
    }
  }

  /** the forbidden constructor */
  private FontSize() {
    ErrorUtils.doNotCall();
  }
}
