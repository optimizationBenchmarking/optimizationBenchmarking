package test.junit.org.optimizationBenchmarking.utils.document;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentConfiguration;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.io.paths.TempDir;

import test.junit.InstanceTest;
import examples.org.optimizationBenchmarking.utils.document.RandomDocumentExample;
import examples.org.optimizationBenchmarking.utils.document.TemplateDocumentExample;

/** A test of a document driver */
@Ignore
public class DocumentDriverTest extends
    InstanceTest<DocumentConfiguration> {

  /**
   * create
   * 
   * @param config
   *          the configuration
   */
  public DocumentDriverTest(final DocumentConfiguration config) {
    super(null, config, false, false);
  }

  /**
   * test the document driver for creating random documents
   * 
   * @param service
   *          the service
   * @param r
   *          the random number generator
   * @throws IOException
   *           if i/o fails
   * @throws ExecutionException
   *           if execution fails
   * @throws InterruptedException
   *           if execution is interrupted
   */
  private final void __doRandomTest(final ExecutorService service,
      final Random r) throws IOException, InterruptedException,
      ExecutionException {
    final DocumentConfiguration config;
    final RandomDocumentExample ex;
    final Future<?> f;

    config = this.getInstance();
    Assert.assertNotNull(config);

    try (final TempDir td = new TempDir()) {
      try (final IDocument doc = config.createDocument(td.getPath(),
          "document", null, null)) { //$NON-NLS-1$
        ex = new RandomDocumentExample(doc, r, null, 60_000L);
        if (service != null) {
          f = service.submit(ex);
          f.get();
        } else {
          ex.run();
        }
      }
    }
  }

  /**
   * test the document driver for creating template-based documents
   * 
   * @param service
   *          the service
   * @throws IOException
   *           if i/o fails
   * @throws ExecutionException
   *           if execution fails
   * @throws InterruptedException
   *           if execution is interrupted
   */
  private final void __doTemplateTest(final ExecutorService service)
      throws IOException, InterruptedException, ExecutionException {
    final DocumentConfiguration config;
    final TemplateDocumentExample ex;
    final Future<?> f;

    config = this.getInstance();
    Assert.assertNotNull(config);

    try (final TempDir td = new TempDir()) {
      try (final IDocument doc = config.createDocument(td.getPath(),
          "document", null, null)) { //$NON-NLS-1$
        ex = new TemplateDocumentExample(doc);
        if (service != null) {
          f = service.submit(ex);
          f.get();
        } else {
          ex.run();
        }
      }
    }
  }

  /**
   * Test the serial generation of documents
   * 
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testSerialDocumentCreation() throws Throwable {
    this.__doRandomTest(null, new Random());
    this.__doTemplateTest(null);
  }

  /**
   * test the document driver
   * 
   * @param proc
   *          the processors
   * @param fifo
   *          do this fifo-style
   * @param r
   *          the random number generator
   * @throws IOException
   *           if i/o fails
   * @throws ExecutionException
   *           if execution fails
   * @throws InterruptedException
   *           if execution is interrupted
   */
  private final void __doParallelTest(final int proc, final boolean fifo,
      final Random r) throws IOException, InterruptedException,
      ExecutionException {
    final ForkJoinPool p;

    p = new ForkJoinPool(proc,
        ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, fifo);

    this.__doRandomTest(p, new Random());
    this.__doTemplateTest(p);

    p.shutdown();
    p.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
  }

  /**
   * Test the fifo parallel generation of documents with 1 processor
   * 
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelDocumentCreation_1_fifo() throws Throwable {
    this.__doParallelTest(1, true, new Random());
  }

  /**
   * Test the default parallel generation of documents with 1 processor
   * 
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelDocumentCreation_1_default() throws Throwable {
    this.__doParallelTest(1, false, new Random());
  }

  /**
   * Test the fifo parallel generation of documents with 2 processors
   * 
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelDocumentCreation_2_fifo() throws Throwable {
    this.__doParallelTest(2, true, new Random());
  }

  /**
   * Test the default parallel generation of documents with 2 processors
   * 
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelDocumentCreation_2_default() throws Throwable {
    this.__doParallelTest(2, false, new Random());
  }

  /**
   * Test the fifo parallel generation of documents with 3 processors
   * 
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelDocumentCreation_3_fifo() throws Throwable {
    this.__doParallelTest(3, true, new Random());
  }

  /**
   * Test the default parallel generation of documents with 3 processors
   * 
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelDocumentCreation_3_default() throws Throwable {
    this.__doParallelTest(3, false, new Random());
  }
}
