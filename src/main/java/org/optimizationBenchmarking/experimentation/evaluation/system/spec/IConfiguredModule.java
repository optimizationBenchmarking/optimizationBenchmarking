package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.tools.spec.IToolJob;

/**
 * A configured version of the evaluator module.
 */
public interface IConfiguredModule extends IToolJob {

  /**
   * <p>
   * In order to determine the execution sequence of modules, this method
   * allows us to additionally define order suggestions. Different from the
   * {@link java.lang.Comparable#compareTo(Object)} method of the
   * {@link java.lang.Comparable} interface, this relationship does not
   * need to be anti-symmetric, just free of contradictions. It also allows
   * for a containment hierarchy.
   * </p>
   * <p>
   * The evaluation process will try to follow the order suggestions
   * according to a best-effort principle. However, it may be that due to
   * parallelism or general structural constraints, some order suggestions
   * will be violated.
   * </p>
   * 
   * @param module
   *          the module to compare with
   * @return the relationship be between this module and the specified
   *         module.
   */
  public abstract EModuleRelationship getRelationship(
      final IConfiguredModule module);

  /**
   * Initialize the document before any computation is performed. In this
   * method you can, for instance, allocate labels in the document.
   * 
   * @param data
   *          the data
   * @param document
   *          the document
   */
  public abstract void initialize(final ExperimentSet data,
      final IDocument document);

  /**
   * Adds text into the document summary (i.e, the abstract), if any. This
   * happens before any calculation takes place.
   * 
   * @param data
   *          the data
   * @param summary
   *          the summary
   */
  public abstract void summary(final ExperimentSet data,
      final IPlainText summary);

}
