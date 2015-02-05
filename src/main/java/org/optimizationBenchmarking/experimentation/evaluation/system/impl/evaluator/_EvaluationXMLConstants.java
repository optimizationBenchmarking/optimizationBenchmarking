package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.net.URI;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.config.ConfigurationXMLConstants;

/** the evaluation xml constants */
final class _EvaluationXMLConstants {
  /** the namespace */
  static final URI NAMESPACE_URI = URI
      .create(
          "http://www.optimizationBenchmarking.org/formats/evaluationConfiguration/evaluationConfiguration.1.0.xsd").normalize(); //$NON-NLS-1$
  /** the namespace string */
  static final String NAMESPACE = ConfigurationXMLConstants.NAMESPACE_URI
      .toString();

  /** the root element of the evaluation process */
  static final String ELEMENT_EVALUATION = "evaluation"; //$NON-NLS-1$

  /** the module element */
  static final String ELEMENT_MODULE = "module";//$NON-NLS-1$

  /** the module attribute class */
  static final String ATTRIBUTE_CLASS = "class";//$NON-NLS-1$

  /** the evaluation source prefix */
  static final String PARAM_EVALUATION_PREFIX = "evaluation";//$NON-NLS-1$

  /** the evaluation source suffix */
  static final String PARAM_EVALUATION_SUFFIX = "Setup"; //$NON-NLS-1$

  /** the forbidden constructor */
  private _EvaluationXMLConstants() {
    ErrorUtils.doNotCall();
  }
}
