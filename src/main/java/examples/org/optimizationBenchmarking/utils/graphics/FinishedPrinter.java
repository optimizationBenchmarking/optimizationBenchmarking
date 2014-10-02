package examples.org.optimizationBenchmarking.utils.graphics;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.object.IObjectListener;
import org.optimizationBenchmarking.utils.document.object.PathEntry;

/** A class which will print the finished files */
public final class FinishedPrinter implements IObjectListener {

  /** the internal constructor */
  public FinishedPrinter() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final void onObjectFinalized(final ArrayListView<PathEntry> id) {
    System.out.print("Finished creating "); //$NON-NLS-1$
    System.out.println(id);
  }

}
