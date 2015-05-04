package examples.org.optimizationBenchmarking.utils.io.xml.performance;

import java.io.Writer;
import java.util.Arrays;
import java.util.Random;

import org.optimizationBenchmarking.utils.MemoryUtils;
import org.optimizationBenchmarking.utils.error.ErrorUtils;

import examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc.ExampleDocument;
import examples.org.optimizationBenchmarking.utils.io.xml.performance.output.NullOutputMethod;
import examples.org.optimizationBenchmarking.utils.io.xml.performance.output.OutputMethod;
import examples.org.optimizationBenchmarking.utils.io.xml.performance.output.TempFileOutputMethod;
import examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers.SerializationMethod;
import examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers.XMLAPIParallelAsyncSerialization;
import examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers.XMLAPIParallelSerialization;
import examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers.XMLAPISerialSerialization;
import examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers.XMLStreamWriterSerialization;

/**
 * <p>
 * A performance test of our
 * {@link org.optimizationBenchmarking.utils.io.xml XMLFileType API}. We
 * compare the performance of our
 * {@link org.optimizationBenchmarking.utils.io.xml XMLFileType API}-based
 * serialization both in
 * {@link examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers.XMLAPISerialSerialization
 * serial} and
 * {@link examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers.XMLAPIParallelSerialization
 * parallel} fashion with serial XMLFileType output using Java's
 * {@link examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers.XMLDOMSerialization
 * DOM} and
 * {@link examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers.XMLStreamWriterSerialization
 * XMLStreamWriter} implementations over a set of different output
 * destinations, ranging from a
 * {@link examples.org.optimizationBenchmarking.utils.io.xml.performance.output.NullOutputMethod
 * null} device ignoring all data written to it to a
 * {@link examples.org.optimizationBenchmarking.utils.io.xml.performance.output.TempFileOutputMethod
 * temporary file}.
 * </p>
 * <p>
 * This program will take <strong>very</strong> to run. The initialization
 * procedure alone will run a long time, with the goal to inspire the JIT
 * to finish any work before the actual experiment. After that, we will
 * test document sizes from 10 to 10000 and for delays from 0 to 10000,
 * both in steps of powers of 10.
 * </p>
 */
public final class XMLPerformanceTest {

  /** The serializers which we compare in the performance test */
  public static final SerializationMethod[] SERIALIZERS;

  static {
    final int procs;
    int i, p;

    procs = Runtime.getRuntime().availableProcessors();
    SERIALIZERS = new SerializationMethod[2 + (procs << 1)];

    i = 0;
    XMLPerformanceTest.SERIALIZERS[i++] = new XMLStreamWriterSerialization();
    XMLPerformanceTest.SERIALIZERS[i++] = new XMLAPISerialSerialization();

    p = 1;
    while (p <= procs) {
      XMLPerformanceTest.SERIALIZERS[i++] = new XMLAPIParallelSerialization(
          p++);
    }

    p = 1;
    while (p <= procs) {
      XMLPerformanceTest.SERIALIZERS[i++] = new XMLAPIParallelAsyncSerialization(
          p++);
    }
  }

  /** the set of output methods on which we compare the serializers */
  public static final OutputMethod[] OUTPUT = {//
    new NullOutputMethod(),//
    // new MemoryOutputMethod(),//
    // new BufferedTempFileOutputMethod(),//
    new TempFileOutputMethod(),//
  };

  /** the numbers of nodes per document which we test */
  public static final int[] DOCUMENT_SIZES = { 10, 100, 1000, 10000 };

  /** the document delays */
  public static final int[] DOCUMENT_DELAYS = { 0, 10, 100, 1000, 10000,
    100000 };

  /** we perform {@value} runs per document size */
  public static final int RUNS_PER_DOCUMENT_SIZE = 15;

  /** we perform at least {@value} runs per document */
  public static final int RUNS_PER_DOCUMENT = 3;

  /** small scale dry runs to be done first */
  public static final int SMALL_SCALE_DRY_RUNS = 500;

  /**
   * the main method to conduct the test
   *
   * @param args
   *          the command line arguments: ignored
   * @throws Throwable
   *           if something goes wrong
   */
  public static final void main(final String[] args) throws Throwable {
    final Random rand;

    System.out.println("Beginning the experiment."); //$NON-NLS-1$
    System.out.println();

    rand = new Random();
    rand.setSeed(0L);

    System.out.println(//
        "Starting dry runs in order to finalize potential JIT actions."); //$NON-NLS-1$
    XMLPerformanceTest.__smallDryRuns(rand);
    System.out.println("Finished dry runs."); //$NON-NLS-1$
    System.out.println();

    for (final int delay : XMLPerformanceTest.DOCUMENT_DELAYS) {

      // use same documents, just with different delays
      rand.setSeed(0L);

      System.out.println();
      System.out.print("=================== <delay "); //$NON-NLS-1$
      System.out.print(delay);
      System.out.println("> ==================="); //$NON-NLS-1$
      System.out.println();
      for (final int size : XMLPerformanceTest.DOCUMENT_SIZES) {
        MemoryUtils.fullGC();
        XMLPerformanceTest.__doDocumentSize(size, delay, rand);
        MemoryUtils.fullGC();
      }
      System.out.println();
      System.out.print("=================== </delay "); //$NON-NLS-1$
      System.out.print(delay);
      System.out.println("> ==================="); //$NON-NLS-1$
      System.out.println();
    }

    System.out.println();
    System.out.println("Finished the experiment."); //$NON-NLS-1$
  }

  /**
   * do a given document size
   *
   * @param delay
   *          the document delay
   * @param size
   *          the document size
   * @param rand
   *          the randomizer
   * @throws Throwable
   *           if something goes wrong
   */
  private static final void __doDocumentSize(final int size,
      final int delay, final Random rand) throws Throwable {
    final long[][][] runtimes;
    int ser, out, runIndex;

    MemoryUtils.fullGC();

    System.out.println();
    System.out.print("Runs for documents with "); //$NON-NLS-1$
    System.out.print(size);
    System.out.print(" nodes."); //$NON-NLS-1$
    System.out.println();

    for (final OutputMethod om : XMLPerformanceTest.OUTPUT) {
      System.out.print('\t');
      System.out.print(om.toString());
    }
    System.out.println();

    runtimes = new long[XMLPerformanceTest.SERIALIZERS.length][XMLPerformanceTest.OUTPUT.length][XMLPerformanceTest.RUNS_PER_DOCUMENT_SIZE];

    for (runIndex = XMLPerformanceTest.RUNS_PER_DOCUMENT_SIZE; (--runIndex) >= 0;) {
      MemoryUtils.fullGC();
      XMLPerformanceTest.__doDocument(size, delay, rand, runtimes,
          runIndex);
      MemoryUtils.fullGC();
    }
    MemoryUtils.fullGC();

    for (ser = 0; ser < XMLPerformanceTest.SERIALIZERS.length; ser++) {
      System.out.print(XMLPerformanceTest.SERIALIZERS[ser].toString());
      for (out = 0; out < XMLPerformanceTest.OUTPUT.length; out++) {
        System.out.print('\t');
        Arrays.sort(runtimes[ser][out]);
        System.out
        .print(runtimes[ser][out][runtimes[ser][out].length >>> 1]);
      }
      System.out.println();
      System.out.flush();
      System.err.flush();
    }

    System.out.flush();
    System.err.flush();
    MemoryUtils.fullGC();
  }

  /**
   * Create and serialize a document of a given size
   * {@link #RUNS_PER_DOCUMENT} times. The minimum runtime required for a
   * given document is stored in the array {@code minTimes}. Reason: This
   * minimum time is the purest time, without influence of other issues.
   *
   * @param size
   *          the document size
   * @param delay
   *          the document delay
   * @param rand
   *          the randomizer
   * @param minTimes
   *          the minimum times destination array
   * @param runIndex
   *          the run index
   * @throws Throwable
   *           if something goes wrong
   */
  private static final void __doDocument(final int size, final int delay,
      final Random rand, final long[][][] minTimes, final int runIndex)
          throws Throwable {
    final ExampleDocument doc;
    int ser, out, run;

    for (final long[][] t : minTimes) {
      for (final long[] tt : t) {
        tt[runIndex] = Long.MAX_VALUE;
      }
    }

    doc = ExampleDocument.createExampleDocument(rand, size, delay);

    for (run = XMLPerformanceTest.RUNS_PER_DOCUMENT; (--run) >= 0;) {
      for (out = XMLPerformanceTest.OUTPUT.length; (--out) >= 0;) {
        for (ser = XMLPerformanceTest.SERIALIZERS.length; (--ser) >= 0;) {
          minTimes[ser][out][runIndex] = Math.min(
              minTimes[ser][out][runIndex],//
              XMLPerformanceTest.__serialize(
                  XMLPerformanceTest.OUTPUT[out],
                  XMLPerformanceTest.SERIALIZERS[ser], doc));
          MemoryUtils.fullGC();
        }
      }
    }
  }

  /**
   * serialize a document
   *
   * @param method
   *          the output method
   * @param serializer
   *          the serializer method
   * @param doc
   *          the document
   * @return the consumed time
   * @throws Throwable
   *           if something goes wrong
   */
  private static final long __serialize(final OutputMethod method,
      final SerializationMethod serializer, final ExampleDocument doc)
          throws Throwable {
    long time;
    try (final Writer writer = method.createWriter()) {
      time = System.nanoTime();
      serializer.store(doc, writer);
      writer.flush();
      time = (System.nanoTime() - time);
    }
    return time;
  }

  /**
   * Do the small dry runs. The goal is to make sure that any JIT action
   * has happened before the actual measurements are taken.
   *
   * @param rand
   *          the randomizer
   * @throws Throwable
   *           if something goes wrong
   */
  private static final void __smallDryRuns(final Random rand)
      throws Throwable {
    int i;

    for (i = XMLPerformanceTest.SMALL_SCALE_DRY_RUNS; (--i) >= 0;) {
      for (final SerializationMethod ser : XMLPerformanceTest.SERIALIZERS) {
        for (final OutputMethod out : XMLPerformanceTest.OUTPUT) {
          XMLPerformanceTest.__serialize(out, ser,//
              ExampleDocument.createExampleDocument(rand,//
                  (1 + rand.nextInt(200)),//
                  (rand.nextBoolean() ? 0 : rand.nextInt(1000))));
          MemoryUtils.fullGC();
        }
      }
    }
  }

  /** the forbidden constructor */
  private XMLPerformanceTest() {
    ErrorUtils.doNotCall();
  }
}