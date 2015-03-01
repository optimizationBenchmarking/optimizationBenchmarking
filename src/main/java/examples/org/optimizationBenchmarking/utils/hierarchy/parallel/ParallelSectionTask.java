package examples.org.optimizationBenchmarking.utils.hierarchy.parallel;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalTextOutput;
import org.optimizationBenchmarking.utils.math.random.LoremIpsum;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A recursive section task.
 */
public class ParallelSectionTask extends RecursiveAction {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the root */
  private final HierarchicalTextOutput m_root;

  /** the depth */
  private final String m_sectionIndex;

  /**
   * create
   * 
   * @param root
   *          the root object
   * @param sectionIndex
   *          the section index
   */
  ParallelSectionTask(final HierarchicalTextOutput root,
      final String sectionIndex) {
    super();
    this.m_root = root;
    this.m_sectionIndex = sectionIndex;
  }

  /** {@inheritDoc} */
  @Override
  public final void compute() {
    final Random r;
    int childCount;
    ArrayList<Future<Void>> l;
    ParallelSectionTask child;

    l = null;
    r = ThreadLocalRandom.current();

    this.m_root.append(TextUtils.LINE_SEPARATOR);
    this.m_root.append("=== Begin of "); //$NON-NLS-1$
    this.m_root.append(this.m_sectionIndex);
    this.m_root.append(" ==="); //$NON-NLS-1$

    this.m_root.appendLineBreak();
    LoremIpsum.appendLoremIpsum(this.m_root, r);

    childCount = 0;
    while (r.nextBoolean() && (childCount < 5)) {
      childCount++;
      child = new ParallelSectionTask(this.m_root.newText(),
          (this.m_sectionIndex + '.') + childCount);
      if (l == null) {
        l = new ArrayList<>();
      }
      l.add(child.fork());
    }

    if (l != null) {
      try {
        for (final Future<Void> y : l) {
          y.get();
        }
      } catch (final Throwable tx) {
        ErrorUtils.throwRuntimeException(//
            "Error while waiting for parallel section task.",//$NON-NLS-1$
            tx);
      }

      this.m_root.appendLineBreak();
      LoremIpsum.appendLoremIpsum(this.m_root, r);
    }

    this.m_root.append(TextUtils.LINE_SEPARATOR);
    this.m_root.append("=== End of "); //$NON-NLS-1$
    this.m_root.append(this.m_sectionIndex);
    this.m_root.append(" ==="); //$NON-NLS-1$
    this.m_root.append(TextUtils.LINE_SEPARATOR);
    this.m_root.close();
  }

}
