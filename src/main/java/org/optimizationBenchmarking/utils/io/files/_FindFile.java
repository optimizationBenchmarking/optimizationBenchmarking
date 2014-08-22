package org.optimizationBenchmarking.utils.io.files;

import java.io.File;

import org.optimizationBenchmarking.utils.collections.visitors.IVisitor;
import org.optimizationBenchmarking.utils.predicates.IPredicate;

/** Find a given file.<@javaAutorVersion/> */
final class _FindFile implements IVisitor<File> {

  /** the owner */
  private final FindFile m_owner;

  /** the predicate */
  private final IPredicate<? super File> m_predicate;

  /**
   * Create the find file action
   * 
   * @param owner
   *          the owner
   */
  _FindFile(final FindFile owner) {
    super();
    this.m_owner = owner;
    this.m_predicate = owner.m_predicate;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean visit(final File file) {
    if (this.m_predicate.check(file)) {
      this.m_owner.m_result = file;
      return false;
    }
    return true;
  }

}
