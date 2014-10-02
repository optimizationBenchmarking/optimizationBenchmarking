package org.optimizationBenchmarking.utils.document.object;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * A listener to object events. It is normal to use one instance of this
 * listener only for a single object. This instance will be notified with
 * the list of files that were created during the object's construction.
 */
public interface IObjectListener {

  /**
   * Notify the listener that an object has been finalized.
   * 
   * @param result
   *          the list of files created during the object construction
   */
  public abstract void onObjectFinalized(
      final ArrayListView<PathEntry> result);
}
