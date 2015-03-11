package org.optimizationBenchmarking.experimentation.io.impl.edi;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import org.optimizationBenchmarking.experimentation.data.EDimensionDirection;
import org.optimizationBenchmarking.experimentation.data.EDimensionType;
import org.optimizationBenchmarking.utils.io.xml.IXMLFileType;
import org.optimizationBenchmarking.utils.io.xml.XMLFileType;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/** an internal class with EDI constants */
public enum EDI implements IXMLFileType {

  /** EDI file type */
  EDI_XML;

  /** the namespace uri */
  static final URI NAMESPACE_URI = //
  URI.create(
      "http://www.optimizationBenchmarking.org/formats/experimentDataInterchange/experimentDataInterchange.1.0.xsd").normalize(); //$NON-NLS-1$

  /** the namespace uri */
  static final String NAMESPACE = EDI.NAMESPACE_URI.toString();

  /** the name attribute */
  static final String ATTRIBUTE_DESCRIPTION = "description"; //$NON-NLS-1$
  /** the dimension attribute */
  static final String ATTRIBUTE_DIMENSION = "dimension"; //$NON-NLS-1$
  /** the dimension data type attribute */
  static final String ATTRIBUTE_DIMENSION_DATA_TYPE = "dataType"; //$NON-NLS-1$
  /** the dimension direction attribute */
  static final String ATTRIBUTE_DIMENSION_DIRECTION = "direction"; //$NON-NLS-1$
  /** the dimension type attribute */
  static final String ATTRIBUTE_DIMENSION_TYPE = "dimensionType"; //$NON-NLS-1$
  /** the feature description attribute */
  static final String ATTRIBUTE_FEATURE_DESCRIPTION = "featureDescription"; //$NON-NLS-1$
  /** the feature value attribute */
  static final String ATTRIBUTE_FEATURE_VALUE = "value"; //$NON-NLS-1$
  /** the feature value description attribute */
  static final String ATTRIBUTE_FEATURE_VALUE_DESCRIPTION = "valueDescription"; //$NON-NLS-1$
  /** the dimension floating point lower bound */
  static final String ATTRIBUTE_FLOAT_LOWER_BOUND = "fLowerBound"; //$NON-NLS-1$
  /** the dimension floating point upper bound */
  static final String ATTRIBUTE_FLOAT_UPPER_BOUND = "fUpperBound"; //$NON-NLS-1$
  /** the instance attribute */
  static final String ATTRIBUTE_INSTANCE = "instance"; //$NON-NLS-1$
  /** the dimension integer lower bound */
  static final String ATTRIBUTE_INTEGER_LOWER_BOUND = "iLowerBound"; //$NON-NLS-1$
  /** the dimension integer upper bound */
  static final String ATTRIBUTE_INTEGER_UPPER_BOUND = "iUpperBound"; //$NON-NLS-1$
  /** the name attribute */
  static final String ATTRIBUTE_NAME = "name"; //$NON-NLS-1$
  /** the parameter description attribute */
  static final String ATTRIBUTE_PARAMETER_DESCRIPTION = "parameterDescription"; //$NON-NLS-1$
  /** the parameter value attribute */
  static final String ATTRIBUTE_PARAMETER_VALUE = EDI.ATTRIBUTE_FEATURE_VALUE;
  /** the parameter value description attribute */
  static final String ATTRIBUTE_PARAMETER_VALUE_DESCRIPTION = "valueDescription"; //$NON-NLS-1$
  /** the limits element */
  static final String ELEMENT_BOUNDS = "bounds"; //$NON-NLS-1$
  /** the dimension element */
  static final String ELEMENT_DIMENSION = "dimension"; //$NON-NLS-1$
  /** the dimensions element */
  static final String ELEMENT_DIMENSIONS = "dimensions"; //$NON-NLS-1$
  /** the experiment element */
  static final String ELEMENT_EXPERIMENT = "experiment"; //$NON-NLS-1$
  // /** the namespace prefix */
  //  static final String NAMESPACE_PREFIX = "edi"; //$NON-NLS-1$

  /** the experiment set tag */
  static final String ELEMENT_EXPERIMENT_DATA = "experimentData"; //$NON-NLS-1$
  /** the experiments element */
  static final String ELEMENT_EXPERIMENTS = "experiments"; //$NON-NLS-1$
  /** the feature element */
  static final String ELEMENT_FEATURE = "feature"; //$NON-NLS-1$
  /** the float element */
  static final String ELEMENT_FLOAT = "f"; //$NON-NLS-1$
  /** the instance element */
  static final String ELEMENT_INSTANCE = "instance"; //$NON-NLS-1$
  /** the instance runs element */
  static final String ELEMENT_INSTANCE_RUNS = "instanceRuns"; //$NON-NLS-1$
  /** the instances element */
  static final String ELEMENT_INSTANCES = "instances"; //$NON-NLS-1$
  /** the int element */
  static final String ELEMENT_INT = "i"; //$NON-NLS-1$
  /** the parameter element */
  static final String ELEMENT_PARAMETER = "parameter"; //$NON-NLS-1$
  /** the point element */
  static final String ELEMENT_POINT = "p"; //$NON-NLS-1$
  /** the run element */
  static final String ELEMENT_RUN = "run"; //$NON-NLS-1$

  /** the dimension data type values */
  static final String[] ATTRIBUTE_VALUE_DIMENSION_DATA_TYPE;
  /** the dimension direction values */
  static final String[] ATTRIBUTE_VALUE_DIMENSION_DIRECTION;
  /** the dimension type values */
  static final String[] ATTRIBUTE_VALUE_DIMENSION_TYPE;

  static {
    ATTRIBUTE_VALUE_DIMENSION_TYPE = new String[EDimensionType.INSTANCES
        .size()];
    EDI.ATTRIBUTE_VALUE_DIMENSION_TYPE[EDimensionType.RUNTIME_CPU
        .ordinal()] = "runtimeCPU"; //$NON-NLS-1$
    EDI.ATTRIBUTE_VALUE_DIMENSION_TYPE[EDimensionType.RUNTIME_NORMALIZED
        .ordinal()] = "runtimeNormalized"; //$NON-NLS-1$
    EDI.ATTRIBUTE_VALUE_DIMENSION_TYPE[EDimensionType.ITERATION_ALGORITHM_STEP
        .ordinal()] = "iterationAlgorithmStep"; //$NON-NLS-1$
    EDI.ATTRIBUTE_VALUE_DIMENSION_TYPE[EDimensionType.ITERATION_FE
        .ordinal()] = "iterationFE"; //$NON-NLS-1$
    EDI.ATTRIBUTE_VALUE_DIMENSION_TYPE[EDimensionType.ITERATION_SUB_FE
        .ordinal()] = "iterationSubFE"; //$NON-NLS-1$
    EDI.ATTRIBUTE_VALUE_DIMENSION_TYPE[EDimensionType.QUALITY_PROBLEM_DEPENDENT
        .ordinal()] = "qualityProblemDependent"; //$NON-NLS-1$
    EDI.ATTRIBUTE_VALUE_DIMENSION_TYPE[EDimensionType.QUALITY_PROBLEM_INDEPENDENT
        .ordinal()] = "qualityProblemIndependent"; //$NON-NLS-1$
  }

  static {
    ATTRIBUTE_VALUE_DIMENSION_DIRECTION = new String[EDimensionDirection.INSTANCES
        .size()];
    EDI.ATTRIBUTE_VALUE_DIMENSION_DIRECTION[EDimensionDirection.DECREASING
        .ordinal()] = "decreasing"; //$NON-NLS-1$
    EDI.ATTRIBUTE_VALUE_DIMENSION_DIRECTION[EDimensionDirection.DECREASING_STRICTLY
        .ordinal()] = "decreasingStrictly"; //$NON-NLS-1$
    EDI.ATTRIBUTE_VALUE_DIMENSION_DIRECTION[EDimensionDirection.INCREASING
        .ordinal()] = "increasing"; //$NON-NLS-1$
    EDI.ATTRIBUTE_VALUE_DIMENSION_DIRECTION[EDimensionDirection.INCREASING_STRICTLY
        .ordinal()] = "increasingStrictly"; //$NON-NLS-1$
  }

  static {
    ATTRIBUTE_VALUE_DIMENSION_DATA_TYPE = new String[EPrimitiveType.TYPES
        .size()];
    EDI.ATTRIBUTE_VALUE_DIMENSION_DATA_TYPE[EPrimitiveType.BYTE.ordinal()] = "byte"; //$NON-NLS-1$
    EDI.ATTRIBUTE_VALUE_DIMENSION_DATA_TYPE[EPrimitiveType.SHORT.ordinal()] = "short"; //$NON-NLS-1$
    EDI.ATTRIBUTE_VALUE_DIMENSION_DATA_TYPE[EPrimitiveType.INT.ordinal()] = "int"; //$NON-NLS-1$
    EDI.ATTRIBUTE_VALUE_DIMENSION_DATA_TYPE[EPrimitiveType.LONG.ordinal()] = "long"; //$NON-NLS-1$
    EDI.ATTRIBUTE_VALUE_DIMENSION_DATA_TYPE[EPrimitiveType.FLOAT.ordinal()] = "float"; //$NON-NLS-1$
    EDI.ATTRIBUTE_VALUE_DIMENSION_DATA_TYPE[EPrimitiveType.DOUBLE
        .ordinal()] = "double"; //$NON-NLS-1$
  }

  /** the default suffix */
  private static final String SUFFIX = "edi";//$NON-NLS-1$

  /** the suffix characters */
  static final char[] SUFFIX_CHARS = EDI.SUFFIX.toCharArray();

  /** {@inheritDoc} */
  @Override
  public final String getDefaultSuffix() {
    return EDI.SUFFIX;
  }

  /** {@inheritDoc} */
  @Override
  public final String getMIMEType() {
    return XMLFileType.XML.getMIMEType();
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return "Experiment Data Interchange File";//$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final URI getNamespaceURI() {
    return EDI.NAMESPACE_URI;
  }

  /** {@inheritDoc} */
  @Override
  public final String getNamespace() {
    return EDI.NAMESPACE;
  }

  /** {@inheritDoc} */
  @Override
  public final URL getSchemaSource() throws IOException {
    final URL u;
    u = this.getClass().getResource(//
        EDI.NAMESPACE.substring(EDI.NAMESPACE.lastIndexOf('/') + 1));
    if (u != null) {
      return u;
    }
    return EDI.NAMESPACE_URI.toURL();
  }
}
