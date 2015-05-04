package examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc;

/** a class holding an example attribute value */
public final class ExampleAttribute extends _ExampleBase {

  /** the attribute value */
  public final Object value;

  /**
   * Create an example attribute
   *
   * @param _namespace
   *          the namespace
   * @param _name
   *          the name
   * @param _value
   *          the value
   */
  ExampleAttribute(final ExampleNamespace _namespace, final String _name,
      final Object _value) {
    super(_namespace, _name);
    this.value = _value;
  }

}
