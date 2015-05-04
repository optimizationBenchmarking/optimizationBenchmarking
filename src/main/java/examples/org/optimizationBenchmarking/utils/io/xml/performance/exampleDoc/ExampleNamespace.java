package examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc;

import java.net.URI;

import org.optimizationBenchmarking.utils.text.TextUtils;

/** a class holding an example namespace and name value */
public final class ExampleNamespace {

  /** the uri */
  public final URI uri;

  /** the uri string */
  public final String uriString;

  /** the prefix */
  public final String prefix;

  /**
   * Create an example attribute
   *
   * @param _uri
   *          the namespace uri
   * @param _prefix
   *          the prefix
   */
  ExampleNamespace(final URI _uri, final String _prefix) {
    super();
    this.uri = _uri;
    this.uriString = this.uri.toString();
    this.prefix = TextUtils.normalize(_prefix);
  }

}
