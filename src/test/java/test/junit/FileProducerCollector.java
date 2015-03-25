package test.junit;

import java.util.Collection;

import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ProducedFileSet;

/**
 * A counter for file producer listener invocations.
 */
public final class FileProducerCollector extends ProducedFileSet {

  /** create */
  public FileProducerCollector() {
    super();
  }

  /**
   * Assert that files of the given types have been produced
   * 
   * @param types
   *          the file types
   */
  public synchronized final void assertFilesOfType(
      final IFileType... types) {
    final Collection<IFileType> coll;

    coll = this.getProducedFiles().values();
    for (final IFileType type : types) {
      if (!(coll.contains(type))) {
        throw new AssertionError("No file of type '" //$NON-NLS-1$
            + type + "' found."); //$NON-NLS-1$
      }
    }
  }
}
