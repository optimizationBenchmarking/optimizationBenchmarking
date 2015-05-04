package examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc;

/** a class holding an example namespace and name value */
class _ExampleBase {

  /** the uri */
  public final ExampleNamespace namespace;

  /** the attribute name */
  public final String name;

  /**
   * Create an example attribute
   *
   * @param _namespace
   *          the namespace
   * @param _name
   *          the name
   */
  _ExampleBase(final ExampleNamespace _namespace, final String _name) {
    super();
    this.name = _name;
    this.namespace = _namespace;
  }

}
