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
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IDocumentDriver;
import org.optimizationBenchmarking.utils.io.files.TempDir;

import test.junit.InstanceTest;
import examples.org.optimizationBenchmarking.utils.document.RandomDocumentExample;

/** A test of a document driver */
@Ignore
public class DocumentDriverTest extends InstanceTest<IDocumentDriver> {

  /**
   * create
   * 
   * @param driver
   *          the driver
   */
  public DocumentDriverTest(final IDocumentDriver driver) {
    super(null, driver, false, false);
  }

  /**
   * test the document driver
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
    final IDocumentDriver driver;
    final RandomDocumentExample ex;
    final Future<?> f;

    driver = this.getInstance();
    Assert.assertNotNull(driver);

    try (final TempDir td = new TempDir()) {
      try (final IDocument doc = driver.createDocument(td.getDir(),
          "document", null, null)) { //$NON-NLS-1$
        ex = new RandomDocumentExample(doc, r, null);
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
    final Random r;
    int i;

    r = new Random();
    for (i = 0; i < 10; i++) {
      this.__doRandomTest(null, r);
    }
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
    int i;

    p = new ForkJoinPool(proc,
        ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, fifo);

    for (i = 0; i < 10; i++) {
      this.__doRandomTest(p, r);
    }

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

  /**
   * Test the fifo parallel generation of documents with 4 processors
   * 
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelDocumentCreation_4_fifo() throws Throwable {
    this.__doParallelTest(4, true, new Random());
  }

  /**
   * Test the default parallel generation of documents with 4 processors
   * 
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelDocumentCreation_4_default() throws Throwable {
    this.__doParallelTest(4, false, new Random());
  }

  /**
   * Test the fifo parallel generation of documents with 5 processors
   * 
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelDocumentCreation_5_fifo() throws Throwable {
    this.__doParallelTest(5, true, new Random());
  }

  /**
   * Test the default parallel generation of documents with 5 processors
   * 
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelDocumentCreation_5_default() throws Throwable {
    this.__doParallelTest(5, false, new Random());
  }

  /**
   * Test the fifo parallel generation of documents with 6 processors
   * 
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelDocumentCreation_6_fifo() throws Throwable {
    this.__doParallelTest(6, true, new Random());
  }

  /**
   * Test the default parallel generation of documents with 2 processors
   * 
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelDocumentCreation_6_default() throws Throwable {
    this.__doParallelTest(6, false, new Random());
  }

  /**
   * Test the fifo parallel generation of documents with 7 processors
   * 
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelDocumentCreation_7_fifo() throws Throwable {
    this.__doParallelTest(7, true, new Random());
  }

  /**
   * Test the default parallel generation of documents with 7 processors
   * 
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelDocumentCreation_7_default() throws Throwable {
    this.__doParallelTest(7, false, new Random());
  }

  /**
   * Test the fifo parallel generation of documents with 8 processors
   * 
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelDocumentCreation_8_fifo() throws Throwable {
    this.__doParallelTest(8, true, new Random());
  }

  /**
   * Test the default parallel generation of documents with 8 processors
   * 
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelDocumentCreation_8_default() throws Throwable {
    this.__doParallelTest(8, false, new Random());
  }

  /**
   * Test the fifo parallel generation of documents with 10 processors
   * 
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelDocumentCreation_10_fifo() throws Throwable {
    this.__doParallelTest(10, true, new Random());
  }

  /**
   * Test the default parallel generation of documents with 10 processors
   * 
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelDocumentCreation_10_default() throws Throwable {
    this.__doParallelTest(10, false, new Random());
  }

  /**
   * Test the fifo parallel generation of documents with 15 processors
   * 
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelDocumentCreation_15_fifo() throws Throwable {
    this.__doParallelTest(15, true, new Random());
  }

  /**
   * Test the default parallel generation of documents with 15 processors
   * 
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelDocumentCreation_15_default() throws Throwable {
    this.__doParallelTest(15, false, new Random());
  }

  /**
   * Test the fifo parallel generation of documents with 20 processors
   * 
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelDocumentCreation_20_fifo() throws Throwable {
    this.__doParallelTest(20, true, new Random());
  }

  /**
   * Test the default parallel generation of documents with 20 processors
   * 
   * @throws Throwable
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testParallelDocumentCreation_20_default() throws Throwable {
    this.__doParallelTest(20, false, new Random());
  }
}
