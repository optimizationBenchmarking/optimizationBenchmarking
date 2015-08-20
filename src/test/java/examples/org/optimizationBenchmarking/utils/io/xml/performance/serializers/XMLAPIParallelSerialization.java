package examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

import org.optimizationBenchmarking.utils.io.xml.XMLDocument;
import org.optimizationBenchmarking.utils.io.xml.XMLElement;

import examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc.ExampleDocument;
import examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc.ExampleNamespace;

/**
 * The multi-threaded ([@link java.util.concurrent.ForkJoinPool
 * fork-join-style}) usage of
 * {@link org.optimizationBenchmarking.utils.io.xml XML API}-based
 * serialization
 */
public class XMLAPIParallelSerialization extends SerializationMethod {

  /** the number of threads to allocate */
  private final int m_threads;

  /** the fork join pool to use */
  private final ForkJoinPool m_pool;

  /**
   * create the serialization method
   *
   * @param threads
   *          the number of threads to allocate
   */
  public XMLAPIParallelSerialization(final int threads) {
    super();
    this.m_threads = threads;
    this.m_pool = new ForkJoinPool(threads,
        ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("resource")
  public final void store(final ExampleDocument doc, final Writer out)
      throws Exception {
    final XMLElement el;

    try (XMLDocument xdoc = new XMLDocument(out)) {
      el = xdoc.element();
      for (final ExampleNamespace x : doc.namespaces) {
        el.namespaceSetPrefix(x.uri, x.prefix);
      }

      this.m_pool.submit(new _ElementFJTask(el, doc.root)).join();
    }

  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "XMLAPIParallel-" + this.m_threads; //$NON-NLS-1$
  }

  /**
   * The main method
   *
   * @param args
   *          the command line arguments
   * @throws Exception
   *           if something fails
   */
  public static final void main(final String[] args) throws Exception {
    final ExampleDocument doc;
    XMLAPIParallelSerialization api;
    Random r;

    r = new Random();
    r.setSeed(0);
    doc = ExampleDocument.createExampleDocument(r, 100, 200);

    for (int threads = 1; threads <= 10; threads++) {
      api = new XMLAPIParallelSerialization(threads);
      System.out.print("========================= <"); //$NON-NLS-1$
      System.out.print(api.toString());
      System.out.println("> ========================="); //$NON-NLS-1$
      try (final StringWriter w = new StringWriter()) {
        api.store(doc, w);
        System.out.println(w.toString());
      }
      System.out.println();
      System.out.print("========================= </"); //$NON-NLS-1$
      System.out.print(api.toString());
      System.out.println("> ========================="); //$NON-NLS-1$
      System.out.println();
      System.out.println();
    }
  }
}
