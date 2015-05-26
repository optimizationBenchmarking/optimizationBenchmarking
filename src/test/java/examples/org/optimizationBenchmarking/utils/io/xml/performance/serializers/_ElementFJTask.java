package examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers;

import java.util.Arrays;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

import org.optimizationBenchmarking.utils.io.xml.XMLElement;

import examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc.ExampleAttribute;
import examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc.ExampleElement;

/** the internal fork-join task for parallel xml i/o */
final class _ElementFJTask extends RecursiveAction {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the destination element */
  private final XMLElement m_dest;

  /** the element to store */
  private final ExampleElement m_store;

  /**
   * create
   *
   * @param dest
   *          the destination
   * @param store
   *          the element to store
   */
  _ElementFJTask(final XMLElement dest, final ExampleElement store) {
    super();
    this.m_dest = dest;
    this.m_store = store;
  }

  /** {@inheritDoc} */
  @Override
  protected final void compute() {
    final ExampleElement e;
    final _ElementFJTask[] tasks;
    final int len;
    int i;

    e = this.m_store;
    try (final XMLElement dest = this.m_dest) {
      dest.name(e.namespace.uri, e.name);

      for (final ExampleAttribute at : e.attributes) {
        dest.attributeRaw(at.namespace.uri, at.name, at.value.toString());
      }

      if ((len = e.data.length) > 0) {
        tasks = new _ElementFJTask[len];
        i = 0;

        for (final Object o : e.data) {
          if (o instanceof ExampleElement) {
            tasks[i++] = new _ElementFJTask(dest.element(),
                (ExampleElement) o);
          } else {
            if (i > 0) {
              ForkJoinTask.invokeAll(Arrays.copyOf(tasks, i));
              i = 0;
            }
            dest.textRaw().append(o.toString());
          }
        }

        if (i > 0) {
          ForkJoinTask.invokeAll(Arrays.copyOf(tasks, i));
        }
      }
    }
  }
}
