package examples.org.optimizationBenchmarking.utils.document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibDateBuilder;
import org.optimizationBenchmarking.utils.document.impl.xhtml10.XHTML10Driver;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IDocumentBody;
import org.optimizationBenchmarking.utils.document.spec.IDocumentDriver;
import org.optimizationBenchmarking.utils.document.spec.IDocumentHeader;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.graphics.EScreenSize;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
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
   * create the body
   * 
   * @param body
   *          the body
   * @param rand
   *          the randomizer
   */
  private static final void __createBody(final IDocumentBody body,
      final Randomizer rand) {

    try (final ISection section = body.section(null)) {
      try (final IText title = section.title()) {
        title.append("The First Section");//$NON-NLS-1$
      }

      try (final ISectionBody b = section.body()) {
        LoremIpsum.appendLoremIpsum(b, rand, null);
        while (rand.nextBoolean()) {
          b.appendLineBreak();
          LoremIpsum.appendLoremIpsum(b, rand, null);
        }

        b.appendLineBreak();
        try (final IText c = b.inlineCode()) {
          c.append("This is an example for "); //$NON-NLS-1$
          try(final IText ob=c.inBraces()){
            ob.append("inline"); //$NON-NLS-1$
          }
          c.append(" code!"); //$NON-NLS-1$
        }
        b.appendLineBreak();
        try(final IText e = b.emphasize()){
          e.append("This text, on the other hand, is emphasized. "); //$NON-NLS-1$
          try(final IText iq = e.inQuotes()){
            iq.append("This text is in quotes. And this one: "); //$NON-NLS-1$
            try(final IText iq2 =iq.inQuotes()){
              iq2.append("even in nested quotes."); //$NON-NLS-1$
            }
          }
          e.append(" End of emphasized."); //$NON-NLS-1$
        }
        b.appendLineBreak();
        LoremIpsum.appendLoremIpsum(b, rand, null);
      }
    }

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
