package examples.org.optimizationBenchmarking.utils.io.xml;

import java.net.URI;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.io.xml.XMLDocument;
import org.optimizationBenchmarking.utils.io.xml.XMLElement;

/**
 * A first example for the hierarchical XMLFileType API.
 */
public final class Example1 {

  /**
   * the main method
   * 
   * @param args
   *          the command line arguments
   * @throws Throwable
   *           if something goes wrong
   */
  public static final void main(final String[] args) throws Throwable {
    final URI ns;

    ns = new URI("http://www.example.org/"); //$NON-NLS-1$
    try (final XMLDocument doc = new XMLDocument(System.out)) {
      try (final XMLElement root = doc.element()) { // START_A
        root.namespaceSetPrefix(ns, "ns"); //$NON-NLS-1$
        root.name(ns, "root"); //$NON-NLS-1$
        root.attributeEncoded(ns, "rootAttr", //$NON-NLS-1$
            "hello world"); //$NON-NLS-1$
        try (final XMLElement childA = root.element()) { // START_B
          childA.name(ns, "childA"); //$NON-NLS-1$
          try (final XMLElement grandChildAA = childA.element()) {// START_C
            grandChildAA.name(ns, "grandChildAA"); //$NON-NLS-1$
            grandChildAA.attributeEncoded(ns, "strangeO",//$NON-NLS-1$
                "\u00d8"); //$NON-NLS-1$
            grandChildAA.textEncoded().append("abcdefghijklmnopq"); //$NON-NLS-1$
          }// END_C
          try (final XMLElement grandChildAB = childA.element()) {// START_D
            grandChildAB.name(ns, "grandChildAB"); //$NON-NLS-1$
            grandChildAB.attributeEncoded(ns, "abc",//$NON-NLS-1$
                "xyz"); //$NON-NLS-1$
            grandChildAB.textEncoded().append("xyz"); //$NON-NLS-1$
          }// END_D
        }// END_B

        try (final XMLElement childB = root.element()) {// START_E
          childB.name(ns, "childB"); //$NON-NLS-1$
          childB.textEncoded().append("\u00c5\u00c6"); //$NON-NLS-1$
        }// END_E
      }// END_A
    }

    System.out.flush();
  }

  /** the forbidden constructor */
  private Example1() {
    ErrorUtils.doNotCall();
  }
}
