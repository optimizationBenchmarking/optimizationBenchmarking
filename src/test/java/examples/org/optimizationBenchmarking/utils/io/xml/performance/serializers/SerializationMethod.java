package examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc.ExampleDocument;
import examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc.ExampleNamespace;

/** a method for serializing example xml documents */
public abstract class SerializationMethod {

  /** the empty namespaces */
  static final ExampleNamespace[] EMPTY = new ExampleNamespace[0];

  /** create */
  protected SerializationMethod() {
    super();
  }

  /**
   * Store an example document into a writer
   *
   * @param doc
   *          the document
   * @param out
   *          the writer
   * @throws Exception
   *           if i/o fails
   */
  public abstract void store(final ExampleDocument doc, final Writer out)
      throws Exception;

  /**
   * Print an xml document
   *
   * @param doc
   *          the document
   * @throws Exception
   *           if i/o fails
   */
  public void print(final ExampleDocument doc) throws Exception {
    synchronized (System.err) {
      synchronized (System.out) {
        try {
          try (final OutputStreamWriter osw = new OutputStreamWriter(
              System.out, "utf-8") {//$NON-NLS-1$
            @Override
            public void close() throws IOException {
              this.flush(); // don't close System.out...
            }
          }) {
            this.store(doc, osw);
          }
        } finally {
          System.out.flush();
        }
      }
    }
  }

}
