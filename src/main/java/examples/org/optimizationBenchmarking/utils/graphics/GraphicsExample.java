package examples.org.optimizationBenchmarking.utils.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javax.swing.JList;
import javax.swing.border.BevelBorder;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.impl.abstr.GraphicConfiguration;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.math.MathConstants;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.units.ELength;

import examples.org.optimizationBenchmarking.FinishedPrinter;

/** An example for using the graphic output subsystem. */
public final class GraphicsExample {

  /** the size */
  private static final PhysicalDimension SIZE = new PhysicalDimension(
      100d, (MathConstants.GOLDEN_RATIO * 100d), ELength.MM);

  /**
   * run the example: there are problems with the pdf output
   *
   * @param args
   *          the arguments
   * @throws IOException
   *           if i/o fails
   */
  public static final void main(final String[] args) throws IOException {
    final Path dir;
    int z;
    if ((args != null) && (args.length > 0)) {
      dir = Paths.get(args[0]);
    } else {
      dir = Files.createTempDirectory("graphics"); //$NON-NLS-1$
    }

    z = 0;
    for (final GraphicConfiguration driver : ExampleGraphicConfigurations.CONFIGURATIONS) {
      try (final Graphic g = driver
          .createGraphic(
              dir,
              (((((GraphicsExample.class.getSimpleName() + '_') + (++z)) + '_') + driver
                  .toString())), GraphicsExample.SIZE,
              new FinishedPrinter(driver), Logger.getGlobal())) {
        for (int i = 1; i < 50; i++) {
          GraphicsExample.paint(g);
          GraphicsExample.__paintComponent(g);
          g.translate(-75, -25);
          g.scale(0.55d, 0.5d);
          g.rotate(1d / 3d);
          g.clip(g.getBounds());
        }
      }
    }
  }

  /**
   * paint a small component
   *
   * @param g
   *          the graphic
   */
  private static final void __paintComponent(final Graphic g) {
    JList<String> jcb;
    UnaryFunction x;
    Rectangle2D r;

    jcb = new JList<>(new String[] { "item 1",//$NON-NLS-1$
        "item 2", //$NON-NLS-1$
        "item 3", //$NON-NLS-1$
        "item 4" });//$NON-NLS-1$

    jcb.setSelectedIndices(new int[] { 0, 2 });
    jcb.setBorder(new BevelBorder(BevelBorder.LOWERED));

    x = ELength.MM.getConversion(ELength.PT);

    r = g.getBounds();

    g.translate((r.getX() + x.computeAsDouble(40d)), //
        (r.getY() + x.computeAsDouble(25d)));
    jcb.setBounds(0, 0, x.computeAsInt(50), x.computeAsInt(30));

    jcb.paint(g);
  }

  /**
   * paint the graphic
   *
   * @param g
   *          the graphic
   */
  public static final void paint(final Graphic g) {
    final Rectangle2D bounds;
    int i;
    Rectangle2D r;
    Stroke s;

    bounds = g.getBounds();
    g.setColor(Color.red);
    g.fill(bounds);
    g.setColor(Color.CYAN);
    g.draw(bounds);

    r = new Rectangle2D.Double();

    for (i = 0; i < 10; i++) {
      r.setRect(bounds.getX() + ((bounds.getWidth() / 10d) * i),//
          bounds.getY(), //
          (bounds.getWidth() / 10d),//
          ((bounds.getWidth() / 10d)));
      g.setColor(new Color(0f, ((i + 1) / 12f), 0f));
      g.fill(r);

      r.setRect(bounds.getX(),//
          bounds.getY() + ((bounds.getHeight() / 10d) * i), //
          (bounds.getHeight() / 10d),//
          ((bounds.getHeight() / 10d)));
      g.setColor(new Color(0f, 0f, (((9 - i) + 1) / 12f)));
      g.fill(r);
    }

    for (i = 0; i < 10; i++) {
      r.setRect(bounds.getX() + ((bounds.getWidth() / 10d) * i),//
          bounds.getY(), //
          (bounds.getWidth() / 10d),//
          ((bounds.getWidth() / 10d)));
      g.setColor(new Color(((i + 1) / 12f), (((9 - i) + 1) / 12f), 0f));
      g.draw(r);

      r.setRect(bounds.getX(),//
          bounds.getY() + ((bounds.getHeight() / 10d) * i), //
          (bounds.getHeight() / 10d),//
          ((bounds.getHeight() / 10d)));
      g.setColor(new Color((((9 - i) + 1) / 12f), 0f, ((i + 1) / 12f)));
      g.draw(r);
    }

    g.setColor(Color.white);
    g.drawLine(bounds.getX(), bounds.getY(),//
        bounds.getMaxX(), bounds.getMaxY());

    g.setColor(Color.black);
    s = g.getStroke();
    if (s instanceof BasicStroke) {
      g.setStroke(new BasicStroke(((BasicStroke) s).getLineWidth() * 0.5f));
    }
    g.drawLine(bounds.getX(), bounds.getY(),//
        bounds.getX() + (bounds.getWidth()),//
        bounds.getY() + (bounds.getHeight()));
    g.setStroke(s);

    g.setFont(new Font("Courier New", //$NON-NLS-1$
        Font.PLAIN, ((int) (0.5d + ELength.MM.convertTo(
            (bounds.getHeight() / 10d), ELength.MM)))));
    g.setColor(Color.YELLOW);
    g.drawString("PlainText with 1/10th of a line hight",//$NON-NLS-1$
        (0.5d + (bounds.getX() + ((bounds.getHeight() / 10)))),//
        (0.5d + (bounds.getY() + (((3d * bounds.getHeight()) / 10d)))));

    g.setFont(new Font("Arial", //$NON-NLS-1$
        Font.PLAIN, 18));
    g.setColor(Color.YELLOW);
    g.drawString("Font: 18pt",//$NON-NLS-1$
        (0.5d + (bounds.getX() + ((bounds.getHeight() / 10)))),//
        (0.5d + (bounds.getY() + (((5d * bounds.getHeight()) / 10d)))));

    g.setFont(new Font("Times New Roman", //$NON-NLS-1$
        Font.PLAIN, 16));
    g.setColor(Color.YELLOW);
    g.drawString("Font: 16pt",//$NON-NLS-1$
        (0.5d + (bounds.getX() + ((bounds.getHeight() / 10)))),//
        (0.5d + (bounds.getY() + (((7d * bounds.getHeight()) / 10d)))));

    g.setFont(new Font("Dialog", //$NON-NLS-1$
        Font.PLAIN, 14));
    g.setColor(Color.YELLOW);
    g.drawString("Font: 14pt",//$NON-NLS-1$
        (0.5d + (bounds.getX() + ((bounds.getHeight() / 10)))),//
        (0.5d + (bounds.getY() + (((9d * bounds.getHeight()) / 10d)))));
  }

  /** the forbidden constructor */
  private GraphicsExample() {
    ErrorUtils.doNotCall();
  }

}
