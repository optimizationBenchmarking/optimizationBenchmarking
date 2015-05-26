package examples.org.optimizationBenchmarking.utils.hierarchy;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalTextOutput;

/**
 * A simple example for
 * {@link org.optimizationBenchmarking.utils.hierarchy.HierarchicalTextOutput}
 * s.
 */
public final class HierarchicalAppendableExample2 {

  /**
   * The main method
   *
   * @param args
   *          the command line arguments: ignored
   */
  @SuppressWarnings("resource")
  public final static void main(final String[] args) {
    HierarchicalTextOutput root, innerA, innerB;

    root = new HierarchicalTextOutput(System.out);
    root.append("This is the beginning of the text.\n"); //$NON-NLS-1$
    innerA = root.newText();
    innerB = root.newText();
    innerB.append("This is the second inner text.\n"); //$NON-NLS-1$
    innerA.append("This is the first inner text.\n"); //$NON-NLS-1$
    innerB.close();
    innerA.close();
    root.append("This is the end of the text."); //$NON-NLS-1$
    root.close();
  }

  /** the forbidden constructor */
  private HierarchicalAppendableExample2() {
    ErrorUtils.doNotCall();
  }
}
