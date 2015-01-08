package org.optimizationBenchmarking.utils.tools.spec;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;

import org.optimizationBenchmarking.utils.io.IFileType;

/**
 * A listener for
 * {@link org.optimizationBenchmarking.utils.tools.spec.IDocumentProducerBuilder
 * file producing jobs}. It is normal to use one instance of this listener
 * only for a single object/job. This instance will be notified with the
 * list of files that were created during the object's construction.
 */
public interface IFileProducerListener {

  /**
   * Notify the listener that all the files created by a job have been
   * finalized (closed). None of their contents will change anymore,
   * neither will new files be created.
   * 
   * @param result
   *          the list of files created during the object construction
   */
  public abstract void onFilesFinalized(
      final Collection<Map.Entry<Path, IFileType>> result);

}
