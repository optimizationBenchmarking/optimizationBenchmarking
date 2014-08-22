package org.optimizationBenchmarking.utils.io.files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.optimizationBenchmarking.utils.io.files.predicates.CanExecutePredicate;
import org.optimizationBenchmarking.utils.io.files.predicates.FileNamePredicate;
import org.optimizationBenchmarking.utils.predicates.AndPredicate;
import org.optimizationBenchmarking.utils.tasks.Task;

/**
 * A task for finding a given executable.
 */
public class FindExecutable extends Task<File> {

  /** the potential paths */
  private final String[] m_paths;

  /** the potential names */
  private final String[] m_names;

  /**
   * Create the find file action
   * 
   * @param paths
   *          the paths to search
   * @param names
   *          the potential names
   */
  public FindExecutable(final String[] paths, final String[] names) {
    super();
    this.m_paths = (((paths != null) && (paths.length > 0)) ? paths
        .clone() : null);
    this.m_names = names.clone();
  }

  /**
   * Create the find file action
   * 
   * @param names
   *          the potential names
   */
  public FindExecutable(final String... names) {
    this(null, names);
  }

  /** {@inheritDoc} */
  @Override
  public final File call() throws IOException, SecurityException {
    ArrayList<File> paths;
    Collection<File> sp;
    File[] ar;
    int i;
    File c;
    final FindFile ff;

    sp = Paths.getPath();
    if (this.m_paths != null) {
      paths = new ArrayList<>(this.m_paths.length + sp.size());

      i = 0;
      for (final String s : this.m_paths) {
        try {
          c = new CanonicalizeFile(s).call();
          if ((c != null) && (c.exists())) {
            if (!(paths.contains(c))) {
              paths.add(i, c);
            }
          }
        } catch (final Throwable t) {
          /** ignore */
        }
      }

      for (final File f : sp) {
        if (!(paths.contains(f))) {
          paths.add(f);
        }
      }

      ar = paths.toArray(new File[paths.size()]);
      paths.clear();
    } else {
      ar = sp.toArray(new File[sp.size()]);
    }
    sp = null;

    ff = new FindFile(ar, null, false, true,//
        new AndPredicate<>(CanExecutePredicate.INSTANCE,//
            new FileNamePredicate(this.m_names)));
    ar = null;
    return ff.call();
  }
}
