package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.EModuleRelationship;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IConfiguredModule;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJob;

/**
 * The base class to derive configured modules.
 */
public abstract class ConfiguredModule extends ToolJob implements
    IConfiguredModule {

  /**
   * create the configured module
   * 
   * @param logger
   *          the logger to use, or {@code null} if no logging information
   *          should be created
   */
  protected ConfiguredModule(final Logger logger) {
    super(logger);
  }

  /**
   * create the configured module
   * 
   * @param setup
   *          the setup
   */
  protected ConfiguredModule(final EvaluationModuleSetup<?, ?> setup) {
    super(setup);
  }

  /** {@inheritDoc} */
  @Override
  public EModuleRelationship getRelationship(final IConfiguredModule module) {
    return EModuleRelationship.NONE;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return TextUtils.className(this.getClass());
  }

  /** {@inheritDoc} */
  @Override
  public void initialize(final ExperimentSet data, final IDocument document) {
    //
  }

  /** {@inheritDoc} */
  @Override
  public void summary(final ExperimentSet data, final IPlainText summary) {
    //
  }

  /**
   * get the logger
   * 
   * @param module
   *          the module
   * @return the logger
   */
  static final Logger _getLogger(final ConfiguredModule module) {
    if (module == null) {
      throw new IllegalArgumentException(
          "Configured module must not be null."); //$NON-NLS-1$
    }
    return module.getLogger();
  }
}
