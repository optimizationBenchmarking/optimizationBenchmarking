package examples.org.optimizationBenchmarking.utils.document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IDocumentBody;
import org.optimizationBenchmarking.utils.document.spec.IDocumentDriver;
import org.optimizationBenchmarking.utils.document.spec.IDocumentHeader;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.template.DocumentXMLHandler;
import org.optimizationBenchmarking.utils.document.template.DocumentXMLInput;
import org.optimizationBenchmarking.utils.document.template.IDocumentCallback;
import org.optimizationBenchmarking.utils.text.numbers.SimpleNumberAppender;

/**
 * A template-based document creation example.
 */
public class TemplateDocumentExample extends DocumentExample {

  /**
   * create
   * 
   * @param doc
   *          the document
   */
  public TemplateDocumentExample(final IDocument doc) {
    super(doc);
  }

  /**
   * run the example: there are problems with the pdf output
   * 
   * @param args
   *          the arguments
   * @throws Throwable
   *           if something fails
   */
  public static final void main(final String[] args) throws Throwable {
    final Path dir;
    final Logger log;
    TemplateDocumentExample de;
    String last, cur;
    int i;

    log = DocumentExample._getLogger();

    if ((args != null) && (args.length > 0)) {
      dir = Paths.get(args[0]);
    } else {
      dir = Files.createTempDirectory("document"); //$NON-NLS-1$
    }
    synchronized (System.out) {
      System.out.print("Begin creating documents to folder ");//$NON-NLS-1$
      System.out.print(dir);
    }

    i = 0;
    cur = null;

    for (final IDocumentDriver driver : RandomDocumentExample.DRIVERS) {//
      last = cur;
      cur = driver.getClass().getSimpleName();

      if (!(cur.equals(last))) {
        i = 0;
      }
      i++;

      de = new TemplateDocumentExample(driver.createDocument(
          dir.resolve((("template/" + cur) + '_') + i),//$NON-NLS-1$
          "report",//$NON-NLS-1$ 
          new FinishedPrinter(driver), log));

      de.run();
    }

    synchronized (System.out) {
      System.out.print("Finished creating documents to folder ");//$NON-NLS-1$
      System.out.print(dir);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void run() {
    final HashMap<Object, Object> properties;

    properties = new HashMap<>();
    properties.put("numberAppender", SimpleNumberAppender.INSTANCE);//$NON-NLS-1$
    properties.put("numberFormat", new DecimalFormat("0.00"));//$NON-NLS-1$//$NON-NLS-2$
    properties.put("myNumber", Float.valueOf(0.1234f));//$NON-NLS-1$
    properties.put("printMap", new __PrintMapCallback());//$NON-NLS-1$

    try (final IDocumentHeader head = this.m_doc.header()) {
      RandomDocumentExample._createRandomHeader(head, this.m_doc
          .getClass().getSimpleName(), new Random());
    }

    try (final IDocumentBody body = this.m_doc.body()) {

      try {
        DocumentXMLInput.INSTANCE.loadResource(new DocumentXMLHandler(
            body, properties), TemplateDocumentExample.class,
            "template.xml"); //$NON-NLS-1$
      } catch (final IOException ioe) {
        ErrorUtils.throwAsRuntimeException(ioe);
      }
    }

    try (final IDocumentBody footer = this.m_doc.footer()) {
      //
    }

    this.m_doc.close();
  }

  /** an internal callback */
  private static final class __PrintMapCallback implements
      IDocumentCallback<IPlainText> {
    /** create */
    __PrintMapCallback() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final void callback(final IPlainText element,
        final Map<Object, Object> properties) {
      element.append(//
          "This is a callback invocation printing the properties map: "); //$NON-NLS-1$
      element.append(properties);
    }
  }
}
