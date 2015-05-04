package examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers;

import java.io.Writer;
import java.util.Random;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;

import examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc.ExampleAttribute;
import examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc.ExampleDocument;
import examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc.ExampleElement;
import examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc.ExampleNamespace;

/**
 * XMLFileType serialization using the
 * {@link javax.xml.stream.XMLStreamWriter} approach: Documents are written
 * in a serial fashion, similar to the way our
 * {@link org.optimizationBenchmarking.utils.io.xml API} does it.
 */
public class XMLStreamWriterSerialization extends SerializationMethod {

  /** the output factory */
  private final XMLOutputFactory m_xof;

  /** create the serialization method */
  public XMLStreamWriterSerialization() {
    super();
    this.m_xof = XMLOutputFactory.newFactory();
  }

  /** {@inheritDoc} */
  @Override
  public final void store(final ExampleDocument doc, final Writer out)
      throws Exception {
    final XMLStreamWriter writer;
    final StreamEncoding<?, ?> ec;
    final String std;

    writer = this.m_xof.createXMLStreamWriter(out);

    ec = StreamEncoding.getStreamEncoding(out);
    if ((ec != null)
        && (Writer.class.isAssignableFrom(ec.getOutputClass()))
        && (ec != StreamEncoding.TEXT)) {
      std = ec.name();
    } else {
      std = null;
    }

    if (std != null) {
      writer.writeStartDocument(std, "1.0"); //$NON-NLS-1$
    } else {
      writer.writeStartDocument("1.0"); //$NON-NLS-1$
    }

    for (final ExampleNamespace en : doc.namespaces) {
      writer.setPrefix(en.prefix, en.uriString);
    }
    XMLStreamWriterSerialization.__writeElement(writer, doc.root,
        doc.namespaces);
    writer.writeEndDocument();
    writer.close();
  }

  /**
   * write an element
   *
   * @param w
   *          the writer
   * @param e
   *          the element
   * @param ns
   *          the namespace
   * @throws Exception
   *           if something fails
   */
  private static final void __writeElement(final XMLStreamWriter w,
      final ExampleElement e, final ExampleNamespace[] ns)
      throws Exception {

    w.writeStartElement(e.namespace.uriString, e.name);
    for (final ExampleNamespace n : ns) {
      w.writeNamespace(n.prefix, n.uriString);
    }

    for (final ExampleAttribute at : e.attributes) {
      w.writeAttribute(at.namespace.uriString, at.name,
          at.value.toString());
    }

    for (final Object o : e.data) {
      if (o instanceof ExampleElement) {
        XMLStreamWriterSerialization.__writeElement(w,
            ((ExampleElement) o), SerializationMethod.EMPTY);
      } else {
        w.writeCharacters(o.toString());
      }
    }
    w.writeEndElement();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "XMLStreamWriter"; //$NON-NLS-1$
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

    new XMLStreamWriterSerialization().print(//
        ExampleDocument.createExampleDocument(r, 100, 200));

  }
}
