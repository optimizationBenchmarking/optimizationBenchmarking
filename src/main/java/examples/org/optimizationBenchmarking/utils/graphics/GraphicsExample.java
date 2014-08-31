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

import org.optimizationBenchmarking.utils.graphics.DoubleDimension;
import org.optimizationBenchmarking.utils.graphics.EColorModel;
import org.optimizationBenchmarking.utils.graphics.Graphic;
import org.optimizationBenchmarking.utils.graphics.GraphicID;
import org.optimizationBenchmarking.utils.graphics.IGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.IGraphicListener;
import org.optimizationBenchmarking.utils.graphics.drivers.freeHEP.FreeHEPEMFGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.drivers.freeHEP.FreeHEPEPSGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.drivers.freeHEP.FreeHEPPDFGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.drivers.freeHEP.FreeHEPSVGGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.drivers.imageioRaster.ImageIOGIFGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.drivers.imageioRaster.ImageIOJPEGGraphicDriver;
import org.optimizationBenchmarking.utils.graphics.drivers.imageioRaster.ImageIOPNGGraphicDriver;
import org.optimizationBenchmarking.utils.math.MathConstants;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.units.ELength;

/** An example for using the graphic output subsystem. */
public final class GraphicsExample implements IGraphicListener {

  /** the list of graphics drivers */
  private static final IGraphicDriver[] DRIVERS = {
      FreeHEPEPSGraphicDriver.INSTANCE, FreeHEPPDFGraphicDriver.INSTANCE,
      FreeHEPSVGGraphicDriver.INSTANCE, FreeHEPEMFGraphicDriver.INSTANCE,
      new ImageIOPNGGraphicDriver(EColorModel.RBGA_32_BIT, 256) ,
      new ImageIOJPEGGraphicDriver(EColorModel.RBGA_32_BIT, 333,0.0f),
      new ImageIOGIFGraphicDriver(EColorModel.RBGA_32_BIT, 256) ,
      };

  /** the width in mm */
  private static final double WIDTH_MM = 100d;
  /** the height in mm */
  private static final double HEIGHT_MM = (MathConstants.GOLDEN_RATIO * WIDTH_MM);

  /**
   * run the example: there are problems with the pdf output
   * 
   * @param args
   *          the arguments
   * @throws IOException
   *           if i/o fails
   */
  public static final void main(final String[] args) throws IOException {
    final Path dir, fileBlueprint;
    final DoubleDimension dim;
    final IGraphicListener listener;

    if ((args != null) && (args.length > 0)) {
      dir = Paths.get(args[0]);
    } else {
      dir = Files.createTempDirectory("graphics"); //$NON-NLS-1$
    }

    fileBlueprint = dir.resolve("example"); //$NON-NLS-1$    
    listener = new GraphicsExample();
    dim = new DoubleDimension(WIDTH_MM, HEIGHT_MM);

    for (IGraphicDriver driver : DRIVERS) {
      try (final Graphic g = driver
          .createGraphic(driver.createGraphicID(fileBlueprint), dim,
              ELength.MM, listener)) {
        __paint(g);
        __paintComponent(g);
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
    final UnaryFunction mmToDev;
    int i;
    Rectangle2D r;
    Stroke s;

    bounds = g.getBounds();
    g.setColor(Color.red);
    g.fill(bounds);

    mmToDev = ELength.MM.getConversion(ELength.PT);

    r = new Rectangle2D.Double();

    for (i = 0; i < 10; i++) {
      r.setRect(bounds.getX() + mmToDev.compute((WIDTH_MM / 10d) * i),//
          bounds.getY(), //
          mmToDev.compute(WIDTH_MM / 10d),//
          mmToDev.compute((WIDTH_MM / 10d)));
      g.setColor(new Color(0f, ((i + 1) / 12f), 0f));
      g.fill(r);

      r.setRect(bounds.getX(),//
          bounds.getY() + mmToDev.compute((HEIGHT_MM / 10d) * i), //
          mmToDev.compute(HEIGHT_MM / 10d),//
          mmToDev.compute((HEIGHT_MM / 10d)));
      g.setColor(new Color(0f, 0f, (((9 - i) + 1) / 12f)));
      g.fill(r);
    }

    for (i = 0; i < 10; i++) {
      r.setRect(bounds.getX() + mmToDev.compute((WIDTH_MM / 10d) * i),//
          bounds.getY(), //
          mmToDev.compute(WIDTH_MM / 10d),//
          mmToDev.compute((WIDTH_MM / 10d)));
      g.setColor(new Color(((i + 1) / 12f), (((9 - i) + 1) / 12f), 0f));
      g.draw(r);

      r.setRect(bounds.getX(),//
          bounds.getY() + mmToDev.compute((HEIGHT_MM / 10d) * i), //
          mmToDev.compute(HEIGHT_MM / 10d),//
          mmToDev.compute((HEIGHT_MM / 10d)));
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
        bounds.getX() + mmToDev.compute(WIDTH_MM),//
        bounds.getY() + mmToDev.compute(HEIGHT_MM));
    g.setStroke(s);

    g.setFont(g.createFont("Courier New", //$NON-NLS-1$
        Font.PLAIN, (HEIGHT_MM / 10d), ELength.MM));
    g.setColor(Color.YELLOW);
    g.drawString("Text with 1/10th of a line hight",//$NON-NLS-1$
        (0.5d + (bounds.getX() + (mmToDev.compute(HEIGHT_MM / 10)))),//
        (0.5d + (bounds.getY() + (mmToDev.compute(3d * HEIGHT_MM / 10d)))));

    g.setFont(g.createFont("Arial", //$NON-NLS-1$
        Font.PLAIN, 18, ELength.PT));
    g.setColor(Color.YELLOW);
    g.drawString("Font: 18pt",//$NON-NLS-1$
        (0.5d + (bounds.getX() + (mmToDev.compute(HEIGHT_MM / 10)))),//
        (0.5d + (bounds.getY() + (mmToDev.compute(5d * HEIGHT_MM / 10d)))));

    g.setFont(g.createFont("Times New Roman", //$NON-NLS-1$
        Font.PLAIN, 16, ELength.PT));
    g.setColor(Color.YELLOW);
    g.drawString("Font: 16pt",//$NON-NLS-1$
        (0.5d + (bounds.getX() + (mmToDev.compute(HEIGHT_MM / 10)))),//
        (0.5d + (bounds.getY() + (mmToDev.compute(7d * HEIGHT_MM / 10d)))));

    g.setFont(g.createFont("Dialog", //$NON-NLS-1$
        Font.PLAIN, 14, ELength.PT));
    g.setColor(Color.YELLOW);
    g.drawString("Font: 14pt",//$NON-NLS-1$
        (0.5d + (bounds.getX() + (mmToDev.compute(HEIGHT_MM / 10)))),//
        (0.5d + (bounds.getY() + (mmToDev.compute(9d * HEIGHT_MM / 10d)))));
  }

  /** the forbidden constructor */
  private GraphicsExample() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final void onGraphicClosed(GraphicID id) {
    System.out.print("Finished painting "); //$NON-NLS-1$
    System.out.println(id.getPath());
  }

}
