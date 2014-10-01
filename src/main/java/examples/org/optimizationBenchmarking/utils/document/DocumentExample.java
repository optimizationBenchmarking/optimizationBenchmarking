package examples.org.optimizationBenchmarking.utils.document;

import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.optimizationBenchmarking.utils.RandomUtils;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibDateBuilder;
import org.optimizationBenchmarking.utils.document.impl.xhtml10.XHTML10Driver;
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IDocumentBody;
import org.optimizationBenchmarking.utils.document.spec.IDocumentDriver;
import org.optimizationBenchmarking.utils.document.spec.IDocumentHeader;
import org.optimizationBenchmarking.utils.document.spec.IFigure;
import org.optimizationBenchmarking.utils.document.spec.IList;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;
import org.optimizationBenchmarking.utils.document.spec.IStructuredText;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.graphics.EScreenSize;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.graphics.graphic.Graphic;
import org.optimizationBenchmarking.utils.graphics.style.color.ColorPalette;
import org.optimizationBenchmarking.utils.graphics.style.color.DefaultColorPalette;
import org.optimizationBenchmarking.utils.math.random.Randomizer;

import examples.org.optimizationBenchmarking.LoremIpsum;
import examples.org.optimizationBenchmarking.utils.graphics.FinishedPrinter;

/**
 * An example used to illustrate how documents can be created with the
 * document output API.
 */
public class DocumentExample {

  /** the graphic driver to use */
  private static final IDocumentDriver[] DRIVERS = {
      XHTML10Driver.getDefaultDriver(),
      new XHTML10Driver(EGraphicFormat.JPEG.getDefaultDriver(),
          EScreenSize.WQXGA.getPhysicalSize(120), null) };

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
    final Randomizer rand;
    String last, cur;
    int i;

    if ((args != null) && (args.length > 0)) {
      dir = Paths.get(args[0]);
    } else {
      dir = Files.createTempDirectory("document"); //$NON-NLS-1$
    }

    i = 0;
    cur = null;
    rand = new Randomizer();
    for (final IDocumentDriver driver : DocumentExample.DRIVERS) {//
      last = cur;
      cur = driver.getClass().getSimpleName();

      if (!(cur.equals(last))) {
        i = 0;
      }
      i++;

      try (final IDocument doc = driver.createDocument(
          dir.resolve((cur + '_') + i), "report",//$NON-NLS-1$ 
          new FinishedPrinter())) {
        DocumentExample.__createDocument(doc, rand);
      } catch (final Throwable t) {
        t.printStackTrace();
      }
    }
  }

  /**
   * create a document
   * 
   * @param doc
   *          the document
   * @param rand
   *          the randomizer
   */
  private static final void __createDocument(final IDocument doc,
      final Randomizer rand) {
    try (final IDocumentHeader h = doc.header()) {
      DocumentExample.__createHeader(h, rand);
    }
    try (final IDocumentBody b = doc.body()) {
      DocumentExample.__createBody(b, rand);
    }
    try (final IDocumentBody f = doc.footer()) {
      DocumentExample.__createFooter(f, rand);
    }
  }

  /**
   * create the header
   * 
   * @param header
   *          the header
   * @param rand
   *          the randomizer
   */
  private static final void __createHeader(final IDocumentHeader header,
      final Randomizer rand) {

    try (final IText t = header.title()) {
      t.append("Report Created by "); //$NON-NLS-1$
      t.append(header.getDocument().getClass().getSimpleName());
    }

    try (final BibAuthorsBuilder babs = header.authors()) {
      try (final BibAuthorBuilder bab = babs.addAuthor()) {
        bab.setFamilyName("Weise");//$NON-NLS-1$
        bab.setPersonalName("Thomas");//$NON-NLS-1$
      }
      try (final BibAuthorBuilder bab = babs.addAuthor()) {
        bab.setFamilyName("Other");//$NON-NLS-1$
        bab.setPersonalName("Someone");//$NON-NLS-1$
      }
    }

    try (final BibDateBuilder bd = header.date()) {
      bd.fromNow();
    }

    try (final IText t = header.summary()) {
      LoremIpsum.appendLoremIpsum(t, rand, null);
    }
  }

  /**
   * Create a section in a section container
   * 
   * @param sc
   *          the section container
   * @param rand
   *          the randomizer
   */
  private static final void __createSection(final ISectionContainer sc,
      final Randomizer rand) {
    boolean nb, hs;

    try (final ISection section = sc.section(null)) {
      try (final IText title = section.title()) {
        title.append("The ");//$NON-NLS-1$

        do {
          title.append((char) (rand.nextInt(26) + 'A'));
        } while (rand.nextBoolean());

        title.append(" Section");//$NON-NLS-1$
      }

      try (final ISectionBody b = section.body()) {
        nb = false;
        hs = false;

        do {

          if (!hs) {
            if (rand.nextBoolean()) {
              LoremIpsum.appendLoremIpsum(b, rand, null);
              while (rand.nextBoolean()) {
                b.appendLineBreak();
                LoremIpsum.appendLoremIpsum(b, rand, null);
              }
              nb = true;
            }
          }

          if (rand.nextBoolean()) {
            DocumentExample.__createSection(b, rand);
            nb = false;
            hs = true;
          }

          if (!hs) {
            if (rand.nextBoolean()) {
              if (nb) {
                b.appendLineBreak();
              }
              try (final IText c = b.inlineCode()) {
                c.append("This is an example for "); //$NON-NLS-1$
                try (final IText ob = c.inBraces()) {
                  ob.append("inline"); //$NON-NLS-1$
                }
                c.append(" code!"); //$NON-NLS-1$
              }
              nb = true;
            }

            if (rand.nextBoolean()) {
              DocumentExample.__createList(b, rand);
              nb = false;
            }

            if (rand.nextBoolean()) {
              if (nb) {
                b.appendLineBreak();
              }
              LoremIpsum.appendLoremIpsum(b, rand, null);
            }

            if (rand.nextBoolean()) {
              DocumentExample.__createFigure(b, rand);
              nb = false;
            }

            if (rand.nextBoolean()) {
              if (nb) {
                b.appendLineBreak();
              }
              try (final IText e = b.emphasize()) {
                e.append("This text, on the other hand, is emphasized. "); //$NON-NLS-1$
                try (final IText iq = e.inQuotes()) {
                  iq.append("This text is in quotes. And this one: "); //$NON-NLS-1$
                  try (final IText iq2 = iq.inQuotes()) {
                    iq2.append("even in nested quotes."); //$NON-NLS-1$
                  }
                }
                e.append(" End of emphasized."); //$NON-NLS-1$
              }
              nb = true;
            }

            if (nb) {
              b.appendLineBreak();
            }
            LoremIpsum.appendLoremIpsum(b, rand, null);
          }
        } while (rand.nextBoolean());
      }
    }
  }

  /** the figure counter */
  private static volatile long figureCount = 0;

  /**
   * create a figure
   * 
   * @param sb
   *          the section body
   * @param rand
   *          the randomizer
   */
  private static final void __createFigure(final ISectionBody sb,
      final Randomizer rand) {
    final EFigureSize[] s;
    final EFigureSize v;
    final Rectangle2D r;
    final ColorPalette p;
    final ArrayList<AffineTransform> at;
    final ArrayList<Stroke> ss;
    int k;

    s = EFigureSize.values();
    try (final IFigure fig = sb.figure(null,
        (v = s[rand.nextInt(s.length)]),
        RandomUtils.longToString(null, DocumentExample.figureCount++))) {

      try (final IText cap = fig.caption()) {
        cap.append(v.toString());
        cap.append(' ');
        LoremIpsum.appendLoremIpsum(cap, rand, v.toString());
      }

      at = new ArrayList<>();
      ss = new ArrayList<>();
      try (final Graphic g = fig.body()) {
        r = g.getBounds();
        p = DefaultColorPalette.INSTANCE;
        at.add(g.getTransform());
        ss.add(g.getStroke());
        k = 0;

        do {
          g.setColor(p.get(rand.nextInt(p.size())));

          switch (rand.nextInt(9)) {
            case 0: {
              g.drawLine((r.getX() + (rand.nextDouble() * r.getWidth())),//
                  (r.getY() + (rand.nextDouble() * r.getHeight())),//
                  (r.getX() + (rand.nextDouble() * r.getWidth())),//
                  (r.getY() + (rand.nextDouble() * r.getHeight())));
              k++;
              break;
            }
            case 1: {
              g.drawRect((r.getX() + (rand.nextDouble() * r.getWidth())),//
                  (r.getY() + (rand.nextDouble() * r.getHeight())),//
                  ((rand.nextDouble() * r.getWidth())),//
                  ((rand.nextDouble() * r.getHeight())));
              k++;
              break;
            }
            case 2: {
              g.fillRect((r.getX() + (rand.nextDouble() * r.getWidth())),//
                  (r.getY() + (rand.nextDouble() * r.getHeight())),//
                  ((rand.nextDouble() * r.getWidth())),//
                  ((rand.nextDouble() * r.getHeight())));
              k++;
              break;
            }
            case 3: {
              trans: for (;;) {
                try {
                  if (rand.nextBoolean()) {
                    g.shear((-1d + (2d * rand.nextDouble())),
                        (-1d + (2d * rand.nextDouble())));
                  } else {
                    g.rotate(Math.PI * (-1d + (2d * rand.nextDouble())));
                  }
                  at.add(g.getTransform());
                  break trans;
                } catch (final Throwable tt) {
                  //
                }
              }
              break;
            }
            case 4: {
              if (at.size() > 1) {
                at.remove(at.size() - 1);
                g.setTransform(at.get(at.size() - 1));
              }
              break;
            }
            case 5: {
              g.setStroke(new BasicStroke(rand.nextInt(30) + 1, rand
                  .nextInt(3), rand.nextInt(3)));
              ss.add(g.getStroke());
              break;
            }
            case 6: {
              if (ss.size() > 1) {
                ss.remove(ss.size() - 1);
                g.setStroke(ss.get(ss.size() - 1));
              }
              break;
            }
            case 7: {
              double[] x, y;

              x = new double[(rand.nextInt(100) + 1)];
              y = new double[x.length];
              for (int i = x.length; ((--i) >= 0);) {
                x[i] = (r.getX() + (rand.nextDouble() * r.getWidth()));
                y[i] = (r.getY() + (rand.nextDouble() * r.getHeight()));
              }
              switch (rand.nextInt(3)) {
                case 0: {
                  g.drawPolygon(x, y, x.length);
                  break;
                }
                case 1: {
                  g.drawPolyline(x, y, x.length);
                  break;
                }
                default: {
                  g.fillPolygon(x, y, x.length);
                  break;
                }
              }
              k++;
            }
            default: {
              g.drawString(
                  RandomUtils.longToString(null, rand.nextLong()),
                  (r.getX() + (rand.nextDouble() * r.getWidth())),//
                  (r.getY() + (rand.nextDouble() * r.getHeight())));
              k++;
            }
          }

        } while ((k < 100) || (rand.nextInt(10) > 0));

      }
    }
  }

  /**
   * create a list
   * 
   * @param sb
   *          the section body
   * @param rand
   *          the randomizer
   */
  private static final void __createList(final IStructuredText sb,
      final Randomizer rand) {
    try (final IList list = (rand.nextBoolean() ? sb.enumeration() : sb
        .itemization())) {

      do {
        try (final IStructuredText item = list.item()) {
          LoremIpsum.appendLoremIpsum(item, rand, null);
          if (rand.nextInt(4) <= 0) {
            DocumentExample.__createList(item, rand);
            if (rand.nextBoolean()) {
              LoremIpsum.appendLoremIpsum(item, rand, null);
            }
          }
        }
      } while (rand.nextBoolean());

    }
  }

  /**
   * create the body
   * 
   * @param body
   *          the body
   * @param rand
   *          the randomizer
   */
  private static final void __createBody(final IDocumentBody body,
      final Randomizer rand) {
    DocumentExample.__createSection(body, rand);
  }

  /**
   * create the footer
   * 
   * @param footer
   *          the footer
   * @param rand
   *          the randomizer
   */
  private static final void __createFooter(final IDocumentBody footer,
      final Randomizer rand) {

    try (final ISection section = footer.section(null)) {
      try (final IText title = section.title()) {
        title.append("The First Footer Section");//$NON-NLS-1$
      }

      try (final ISectionBody b = section.body()) {
        LoremIpsum.appendLoremIpsum(b, rand, null);
        while (rand.nextBoolean()) {
          b.appendLineBreak();
          LoremIpsum.appendLoremIpsum(b, rand, null);
        }
      }
    }

  }

}
