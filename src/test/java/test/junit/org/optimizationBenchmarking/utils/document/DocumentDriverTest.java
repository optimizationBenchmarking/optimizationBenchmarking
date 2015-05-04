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
import org.optimizationBenchmarking.utils.MemoryUtils;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentConfiguration;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IDocumentDriver;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.paths.TempDir;

import test.junit.FileProducerCollector;
import test.junit.InstanceTest;
import test.junit.org.optimizationBenchmarking.utils.tools.ToolTest;
import examples.org.optimizationBenchmarking.utils.document.RandomDocumentExample;
import examples.org.optimizationBenchmarking.utils.document.TemplateDocumentExample;

/** A test of a document driver */
@Ignore
public abstract class DocumentDriverTest extends
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
   * Get the required file types, i.e., the file types which should be
   * produced
   *
   * @return the file types which should be produced
   */
  protected abstract IFileType[] getRequiredTypes();

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
    RandomDocumentExample example;
    FileProducerCollector files;
    Future<?> future;

    config = this.getInstance();
    Assert.assertNotNull(config);
    try {
      files = new FileProducerCollector();

      try (final TempDir td = new TempDir()) {
        try (final IDocument doc = config.createDocument(td.getPath(),
            "document", files, null)) { //$NON-NLS-1$
          example = new RandomDocumentExample(doc, r, null, 60_000L);
          try {
            if (service != null) {
              try {
                future = service.submit(example);
                future.get();
              } finally {
                future = null;
              }
            } else {
              example.run();
            }
          } finally {
            example = null;
          }
        }
      }

      files.assertFilesOfType(this.getRequiredTypes());
    } finally {
      files = null;
    }

    MemoryUtils.fullGC();
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
    TemplateDocumentExample example;
    Future<?> future;
    FileProducerCollector files;

    config = this.getInstance();
    Assert.assertNotNull(config);
    try {
      files = new FileProducerCollector();

      try (final TempDir td = new TempDir()) {
        try (final IDocument doc = config.createDocument(td.getPath(),
            "document", files, null)) { //$NON-NLS-1$
          try {
            example = new TemplateDocumentExample(doc);
            if (service != null) {
              try {
                future = service.submit(example);
                future.get();
              } finally {
                future = null;
              }
            } else {
              example.run();
            }
          } finally {
            example = null;
          }
        }
      }

      files.assertFilesOfType(this.getRequiredTypes());
    } finally {
      files = null;
    }

    MemoryUtils.fullGC();
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
    MemoryUtils.fullGC();
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
   * Get the document driver
   *
   * @return the document driver
   */
  final IDocumentDriver _getDocumentDriver() {
    return this.getInstance().getDocumentDriver();
  }

  /**
   * Test whether the document driver can correctly be used as tool.
   */
  @Test(timeout = 3600000)
  public void testDocumentDriverAsTool() {
    new ToolTest<IDocumentDriver>() {
      @Override
      protected IDocumentDriver getInstance() {
        return DocumentDriverTest.this._getDocumentDriver();
      }
    }.validateInstance();
  }

  /** {@inheritDoc} */
  @Override
  public void validateInstance() {
    super.validateInstance();
    this.testDocumentDriverAsTool();
    try {
      this.testParallelDocumentCreation_1_default();
      this.testParallelDocumentCreation_1_fifo();
      this.testParallelDocumentCreation_2_default();
      this.testParallelDocumentCreation_2_fifo();
      this.testParallelDocumentCreation_3_default();
      this.testParallelDocumentCreation_3_fifo();
    } catch (final Throwable t) {
      throw new RuntimeException(t);
    }
  }
}
