package examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc;

/**
 * An object that can be converted to a string, but it will take some delay
 * to do so.
 */
final class _DelayedToString {

  /** the wrapped object */
  private final Object m_object;

  /** the delay */
  private final int m_delay;

  /**
   * create the object
   *
   * @param object
   *          the object
   * @param delay
   *          the delay
   */
  _DelayedToString(final Object object, final int delay) {
    super();
    this.m_object = object;
    this.m_delay = delay;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    int i;
    double x;
    String s;

    x = this.m_object.hashCode();
    for (i = this.m_delay; i >= 0; i--) {
      x += Math.cbrt(x);
      x += Math.tanh(x);
      x = Math.nextAfter(x, ((x < 0d) ? Double.NEGATIVE_INFINITY
          : Double.POSITIVE_INFINITY));
      x -= Math.sin(x);
    }

    // we do this to prevent the compiler from removing our useless, time
    // consuming loop...
    s = this.m_object.toString();
    return ((x != 0d) ? s : String.valueOf(s.toCharArray()));
  }

}
