package examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc;

/** a class holding an example attribute value */
public final class ExampleElement extends _ExampleBase {

  /** the attributes */
  public final ExampleAttribute[] attributes;

  /** the element body */
  public final Object[] data;

  /**
   * Create an example attribute
   *
   * @param _namespace
   *          the namespace
   * @param _name
   *          the name
   * @param _attributes
   *          the attributes
   * @param _data
   *          the element body
   */
  ExampleElement(final ExampleNamespace _namespace, final String _name,
      final ExampleAttribute[] _attributes, final Object[] _data) {
    super(_namespace, _name);
    this.attributes = _attributes;
    this.data = _data;
  }

}
