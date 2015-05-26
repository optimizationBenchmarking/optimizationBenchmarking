package examples.org.optimizationBenchmarking.utils.io.xml;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.optimizationBenchmarking.utils.error.ErrorUtils;

/**
 * The same example as
 * {@link examples.org.optimizationBenchmarking.utils.io.xml.Example1},
 * just using the {@link javax.xml.stream.XMLStreamWriter} API.
 */
public final class Example1XMLStreamWriter {

  /**
   * the main method
   *
   * @param args
   *          the command line arguments
   * @throws Throwable
   *           if something goes wrong
   */
  public static final void main(final String[] args) throws Throwable {
    final XMLOutputFactory xof;
    final XMLStreamWriter writer;
    final String ns;

    xof = XMLOutputFactory.newFactory();

    writer = xof.createXMLStreamWriter(System.out);
    writer.writeStartDocument("1.0"); //$NON-NLS-1$

    ns = "http://www.example.org/"; //$NON-NLS-1$
    writer.setPrefix("ns", ns); //$NON-NLS-1$

    writer.writeStartElement(ns, "root"); //START_A //$NON-NLS-1$
    writer.writeNamespace("ns", ns); //$NON-NLS-1$
    writer.writeAttribute(ns, "rootAttr", "helloWorld"); //$NON-NLS-1$ //$NON-NLS-2$

    writer.writeStartElement(ns, "childA");//START_B //$NON-NLS-1$

    writer.writeStartElement(ns, "grandChildAA");//START_C //$NON-NLS-1$
    writer.writeAttribute(ns, "strangeO", "\u00d8"); //$NON-NLS-1$//$NON-NLS-2$
    writer.writeCharacters("abcdefghijklmnopq"); //$NON-NLS-1$
    writer.writeEndElement(); // END_C

    writer.writeStartElement(ns, "grandChildAB");//START_D //$NON-NLS-1$
    writer.writeAttribute(ns, "abc", "xyz"); //$NON-NLS-1$//$NON-NLS-2$
    writer.writeCharacters("xyz"); //$NON-NLS-1$
    writer.writeEndElement(); // END_D

    writer.writeEndElement(); // END_B

    writer.writeStartElement(ns, "childB");//START_E //$NON-NLS-1$
    writer.writeCharacters("\u00c5\u00c6"); //$NON-NLS-1$
    writer.writeEndElement(); // END_E

    writer.writeEndElement(); // END_A
    writer.writeEndDocument();
    writer.flush();
    System.out.flush();
  }

  /** the forbidden constructor */
  private Example1XMLStreamWriter() {
    ErrorUtils.doNotCall();
  }
}
