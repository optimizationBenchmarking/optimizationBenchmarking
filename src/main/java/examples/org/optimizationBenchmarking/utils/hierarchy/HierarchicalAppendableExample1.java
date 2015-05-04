package examples.org.optimizationBenchmarking.utils.hierarchy;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalTextOutput;

/**
 * A simple example for
 * {@link org.optimizationBenchmarking.utils.hierarchy.HierarchicalTextOutput}
 * s.
 */
public final class HierarchicalAppendableExample1 {

  /**
   * The main method
   *
   * @param args
   *          the command line arguments: ignored
   */
  public final static void main(final String[] args) {
    try (final HierarchicalTextOutput root = new HierarchicalTextOutput(
        System.out)) {
      root.append("This is the beginning of the text.\n"); //$NON-NLS-1$
      try (final HierarchicalTextOutput innerA = root.newText()) {
        innerA.append("This is the first inner text.\n"); //$NON-NLS-1$
      }
      try (final HierarchicalTextOutput innerB = root.newText()) {
        innerB.append("This is the second inner text.\n"); //$NON-NLS-1$
      }
      root.append("This is the end of the text."); //$NON-NLS-1$
    }

  }

  /** the forbidden constructor */
  private HierarchicalAppendableExample1() {
    ErrorUtils.doNotCall();
  }

}
