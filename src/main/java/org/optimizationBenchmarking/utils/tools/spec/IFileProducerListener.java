package org.optimizationBenchmarking.utils.tools.spec;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;

import org.optimizationBenchmarking.utils.io.IFileType;

/**
 * A listener for
 * {@link org.optimizationBenchmarking.utils.tools.spec.IDocumentProducerJobBuilder
 * file producing jobs}. It is normal to use one instance of this listener
 * only for a single object/job. This instance will be notified with the
 * list of files that were created during the object's construction.
 */
public interface IFileProducerListener {

  /**
   * <p>
   * Notify the listener that all the files created by a job have been
   * finalized (closed). None of their contents will change anymore,
   * neither will new files be created.
   * </p>
   * <p>
   * Notice that this method is not informed of the source of the event.
   * The reason for this is that the source may be resource which is
   * already in the process of being closed when it creates this event.
   * References to such a resource should not be published anymore. This is
   * why you would normally use a different instance of this listener
   * interface for each process or resource you listen to so that you know
   * where the event comes from.
   * </p>
   *
   * @param result
   *          the list of files created during the object construction
   */
  public abstract void onFilesFinalized(
      final Collection<Map.Entry<Path, IFileType>> result);

}
