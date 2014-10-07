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

import javax.swing.JList;
import javax.swing.border.BevelBorder;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.document.object.IObjectListener;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.Graphic;
import org.optimizationBenchmarking.utils.graphics.graphic.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.drivers.freeHEP.FreeHEPEMFGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.drivers.freeHEP.FreeHEPEPSGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.drivers.freeHEP.FreeHEPPDFGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.drivers.freeHEP.FreeHEPSVGGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.drivers.imageioRaster.ImageIOGIFGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.drivers.imageioRaster.ImageIOJPEGGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.drivers.imageioRaster.ImageIOPNGGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.style.color.EColorModel;
import org.optimizationBenchmarking.utils.math.MathConstants;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.units.ELength;

/** An example for using the graphic output subsystem. */
public final class GraphicsExample {

  /** the list of graphics drivers */
  static final IGraphicDriver[] DRIVERS = {
      FreeHEPEPSGraphicDriver.INSTANCE, FreeHEPPDFGraphicDriver.INSTANCE,
      FreeHEPSVGGraphicDriver.INSTANCE, FreeHEPEMFGraphicDriver.INSTANCE,
      new ImageIOPNGGraphicDriver(EColorModel.RBGA_32_BIT, 256),
      new ImageIOJPEGGraphicDriver(EColorModel.RBGA_32_BIT, 333, 0.0f),
      new ImageIOGIFGraphicDriver(EColorModel.RBGA_32_BIT, 600),
      EGraphicFormat.NULL.getDefaultDriver() };

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
    final IObjectListener listener;

    if ((args != null) && (args.length > 0)) {
      dir = Paths.get(args[0]);
    } else {
      dir = Files.createTempDirectory("graphics"); //$NON-NLS-1$
    }

    listener = new FinishedPrinter();

    for (final IGraphicDriver driver : GraphicsExample.DRIVERS) {
      try (final Graphic g = driver.createGraphic(dir,
          GraphicsExample.class.getSimpleName(), GraphicsExample.SIZE,
          listener)) {
        for (int i = 1; i < 50; i++) {
          GraphicsExample.__paint(g);
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

    g.translate((r.getX() + x.compute(40d)), //
        (r.getY() + x.compute(25d)));
    jcb.setBounds(0, 0, x.compute(50), x.compute(30));

    jcb.paint(g);
  }

  /**
   * paint the graphic
   * 
   * @param g
   *          the graphic
   */
  private static final void __paint(final Graphic g) {
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
