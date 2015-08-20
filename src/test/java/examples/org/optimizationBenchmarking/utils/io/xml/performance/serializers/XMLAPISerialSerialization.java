package examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers;

import java.io.Writer;
import java.util.Random;

import org.optimizationBenchmarking.utils.io.xml.XMLDocument;
import org.optimizationBenchmarking.utils.io.xml.XMLElement;

import examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc.ExampleAttribute;
import examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc.ExampleDocument;
import examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc.ExampleElement;
import examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc.ExampleNamespace;

/**
 * The serial version of the usage of
 * {@link org.optimizationBenchmarking.utils.io.xml XML API}-based
 * serialization
 */
public class XMLAPISerialSerialization extends SerializationMethod {

  /** create the serialization method */
  public XMLAPISerialSerialization() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final void store(final ExampleDocument doc, final Writer out)
      throws Exception {
    try (XMLDocument xdoc = new XMLDocument(out)) {
      try (XMLElement el = xdoc.element()) {
        for (final ExampleNamespace x : doc.namespaces) {
          el.namespaceSetPrefix(x.uri, x.prefix);
        }
        XMLAPISerialSerialization.__writeElement(el, doc.root);
      }
    }
  }

  /**
   * write an element
   *
   * @param dest
   *          the destination element
   * @param e
   *          the element
   * @throws Exception
   *           if something fails
   */
  private static final void __writeElement(final XMLElement dest,
      final ExampleElement e) throws Exception {
    dest.name(e.namespace.uri, e.name);

    for (final ExampleAttribute at : e.attributes) {
      dest.attributeRaw(at.namespace.uri, at.name, at.value.toString());
    }

    for (final Object o : e.data) {
      if (o instanceof ExampleElement) {
        try (final XMLElement el = dest.element()) {
          XMLAPISerialSerialization.__writeElement(el,
              ((ExampleElement) o));
        }
      } else {
        dest.textRaw().append(o);
      }
    }

  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "XMLAPISerial"; //$NON-NLS-1$
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
    Random r;

    r = new Random();
    r.setSeed(0);

    new XMLAPISerialSerialization().print(//
        ExampleDocument.createExampleDocument(r, 100, 200));
  }

}
