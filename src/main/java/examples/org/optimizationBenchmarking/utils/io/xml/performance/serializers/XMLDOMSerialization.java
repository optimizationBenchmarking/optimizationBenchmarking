package examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers;

import java.io.Writer;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc.ExampleAttribute;
import examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc.ExampleDocument;
import examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc.ExampleElement;

/**
 * The {@link org.w3c.dom.Document DOM}-style XML serialization: The
 * complete document tree data structure is created in memory and then
 * transformed to a text stream.
 */
public class XMLDOMSerialization extends SerializationMethod {

  /** the document builder */
  private final DocumentBuilder m_builder;

  /** the transformer */
  private final Transformer m_transformer;

  /** create the serialization method */
  public XMLDOMSerialization() {
    super();

    DocumentBuilder a;
    Transformer b;

    a = null;
    b = null;
    try {
      a = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      b = TransformerFactory.newInstance().newTransformer();
    } catch (final Throwable t) {
      RethrowMode.THROW_AS_RUNTIME_EXCEPTION
          .rethrow(//
              "Error while trying to obtain XML DOM transformer or document builder.",//$NON-NLS-1$
              true, t);
    }
    this.m_builder = a;
    this.m_transformer = b;
  }

  /** {@inheritDoc} */
  @Override
  public final void store(final ExampleDocument doc, final Writer out)
      throws Exception {
    final Document xdoc;
    final Element el;

    xdoc = this.m_builder.newDocument();

    xdoc.setStrictErrorChecking(false);

    el = xdoc.createElementNS(doc.root.namespace.uriString,
        doc.root.namespace.prefix + ':' + doc.root.name);
    // for (final ExampleNamespace ns : doc.namespaces) {
    //      el.setAttributeNS("http://www.w3.org/2000/xmlns/",//$NON-NLS-1$
    //          "xmlns:" + ns.prefix, //$NON-NLS-1$
    // ns.uriString);
    // }
    xdoc.appendChild(el);

    XMLDOMSerialization.__writeElement(xdoc, el, doc.root);

    this.m_transformer.transform(new DOMSource(xdoc),
        new StreamResult(out));
  }

  /**
   * write an element
   * 
   * @param doc
   *          the document
   * @param dest
   *          the element
   * @param e
   *          the element
   * @throws Exception
   *           if something fails
   */
  private static final void __writeElement(final Document doc,
      final Element dest, final ExampleElement e) throws Exception {
    Element v;
    ExampleElement z;

    for (final ExampleAttribute at : e.attributes) {
      dest.setAttributeNS(at.namespace.uriString,
          (at.namespace.prefix + ':' + at.name), at.value.toString());
    }

    for (final Object o : e.data) {
      if (o instanceof ExampleElement) {
        z = ((ExampleElement) o);
        v = doc.createElementNS(z.namespace.uriString, z.namespace.prefix
            + ':' + z.name);
        XMLDOMSerialization.__writeElement(doc, v, z);
        dest.appendChild(v);
      } else {
        dest.appendChild(doc.createTextNode(o.toString()));
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "DOM"; //$NON-NLS-1$
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

    new XMLDOMSerialization().print(//
        ExampleDocument.createExampleDocument(r, 100, 200));

  }
}
