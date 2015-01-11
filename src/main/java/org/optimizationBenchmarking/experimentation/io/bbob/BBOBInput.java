package org.optimizationBenchmarking.experimentation.io.bbob;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import org.optimizationBenchmarking.experimentation.evaluation.data.DimensionContext;
import org.optimizationBenchmarking.experimentation.evaluation.data.EDimensionDirection;
import org.optimizationBenchmarking.experimentation.evaluation.data.EDimensionType;
import org.optimizationBenchmarking.experimentation.evaluation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.evaluation.data.InstanceContext;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.FileInputTool;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.parsers.BoundedDoubleParser;
import org.optimizationBenchmarking.utils.parsers.BoundedIntParser;

/**
 * <p>
 * A class for loading <a
 * href="http://coco.gforge.inria.fr/doku.php">COCO/BBOB</a> data sets into
 * the {@link org.optimizationBenchmarking.experimentation.evaluation.data
 * experiment data structures}.
 * </p>
 * <p>
 * TODO: The _BBOBHander class should be removed and this input tool should
 * only rely on the methods of the file input tool.
 * </p>
 */
public class BBOBInput extends FileInputTool<ExperimentSetContext> {

  /** the dimensions */
  private static final String FEATURE_DIMENSION = "dim"; //$NON-NLS-1$
  /** the approximate number of local optima */
  private static final String FEATURE_APPROX_N_LOCAL_OPT = "nopt"; //$NON-NLS-1$
  /** is the function symmetric */
  private static final String FEATURE_SYMMETRIC = "sym"; //$NON-NLS-1$

  /** is the function separable */
  private static final String FEATURE_SEPARABLE = "sep"; //$NON-NLS-1$

  /** is the function fully separable */
  private static final String FEATURE_VALUE_SEPARABLE_FULLY = "fully"; //$NON-NLS-1$
  /** is the function partially separable */
  private static final String FEATURE_VALUE_SEPARABLE_PARTIALLY = "partially"; //$NON-NLS-1$
  /** is the function none separable */
  private static final String FEATURE_VALUE_SEPARABLE_NONE = "none"; //$NON-NLS-1$

  /** the conditioning */
  private static final String FEATURE_CONDITIONING = "cond"; //$NON-NLS-1$

  /** is the optimum on the boundary */
  private static final String FEATURE_OPTIMUM_ON_BOUNDARY = "optOnBound"; //$NON-NLS-1$

  /** does the function have neutral steps */
  private static final String FEATURE_HAS_STEPS = "steps"; //$NON-NLS-1$

  /** the function id */
  private static final String FEATURE_FUNCTION_ID = "f"; //$NON-NLS-1$
  /** is the function rugged */
  private static final String FEATURE_RUGGED = "rugged"; //$NON-NLS-1$

  /** the maximum function id */
  static final int MAX_FUNCTION = 24;

  /** the dimensions */
  static final byte[] DIMENSIONS = new byte[] { (byte) 2, (byte) 3,
      (byte) 5, (byte) 10, (byte) 20, (byte) 40, };

  /** create */
  BBOBInput() {
    super();
  }

  /**
   * get the instance of the {@link BBOBInput}
   * 
   * @return the instance of the {@link BBOBInput}
   */
  public static final BBOBInput getInstance() {
    return __BBOBInputLoader.INSTANCE;
  }

  /**
   * fill in the dimension set
   * 
   * @param esb
   *          the experiment set builder
   */
  public static final void makeBBOBDimensionSet(
      final ExperimentSetContext esb) {

    try (final DimensionContext d = esb.createDimension()) {
      d.setName("FEs"); //$NON-NLS-1$
      d.setDescription("the number of fully constructed candidate solutions"); //$NON-NLS-1$
      d.setDirection(EDimensionDirection.INCREASING_STRICTLY);
      d.setType(EDimensionType.ITERATION_FE);
      d.setParser(new BoundedIntParser(1, Integer.MAX_VALUE));
    }

    try (final DimensionContext d = esb.createDimension()) {
      d.setName("F"); //$NON-NLS-1$
      d.setDescription(//
      "the best objective value - F_opt"); //$NON-NLS-1$
      d.setDirection(EDimensionDirection.DECREASING);
      d.setType(EDimensionType.QUALITY_PROBLEM_DEPENDENT);
      d.setParser(new BoundedDoubleParser(0d, Double.MAX_VALUE));
    }

  }

  /**
   * Make a function name
   * 
   * @param id
   *          the function id
   * @param dim
   *          the dimension
   * @return the name
   */
  static final String _makeFunctionName(final int id, final int dim) {
    return ((("f" + id) + '_') + dim); //$NON-NLS-1$
  }

  /**
   * fill in the instance set
   * 
   * @param esb
   *          the experiment set builder
   */
  public static final void makeBBOBInstanceSet(
      final ExperimentSetContext esb) {
    String fidDesc, dimDesc, noptDesc, symDesc, symDescSym, symDescAsym, //
    sepDesc, sepFullyDesc, sepPartiallyDesc, sepNoneDesc, condDesc, condOKDesc, //
    condIllDesc, noptUniDesc, noptMultiDesc, optOnBoundDesc, optOnBoundTrueDesc, //
    optOnBoundFalseDesc, stepsDesc, stepsTrueDesc, stepsFalseDesc, ruggedDesc, //
    ruggedYesDesc, ruggedNoDesc;
    Byte dim;
    final Double d1, d2, d21, dinf, d101;
    final Integer i1, i1e6, i10, i25, i30, i100, i1000;
    final Integer[] ints;
    Double d10pd, d;
    int fid;

    fidDesc = "the function id"; //$NON-NLS-1$
    dimDesc = "the number of dimensions"; //$NON-NLS-1$
    noptDesc = "the approximate number of local optima"; //$NON-NLS-1$
    noptUniDesc = "unimodal"; //$NON-NLS-1$
    noptMultiDesc = "multi-modal"; //$NON-NLS-1$
    symDesc = "whether the function is symmetric"; //$NON-NLS-1$
    symDescSym = "symmetric"; //$NON-NLS-1$
    symDescAsym = "asymmetric"; //$NON-NLS-1$
    sepDesc = "whether the function is separable, i.e., whether it can be represented as sum of functions of lower dimensionality"; //$NON-NLS-1$
    sepFullyDesc = "the function can be represented as sum of 1-dimensional functions"; //$NON-NLS-1$
    sepPartiallyDesc = "the function can be represented as sum of lower-dimensional functions"; //$NON-NLS-1$
    sepNoneDesc = "the function cannot be represented as sum of lower-dimensional functions"; //$NON-NLS-1$
    condDesc = "the approximate conditioning, i.e., the ratio of the largest and smallest direction of a contour line. Large values represent ill-conditioned problems."; //$NON-NLS-1$
    condOKDesc = "nicely conditioned"; //$NON-NLS-1$
    condIllDesc = "ill-conditioned"; //$NON-NLS-1$"
    optOnBoundDesc = "whether the optimum is on the search space boundary"; //$NON-NLS-1$
    optOnBoundTrueDesc = "the optimum is on the search space boundary"; //$NON-NLS-1$
    optOnBoundFalseDesc = "the optimum is not on the search space boundary"; //$NON-NLS-1$
    stepsDesc = "whether the function has steps, i.e., neutral planes"; //$NON-NLS-1$
    stepsTrueDesc = "the function has steps, i.e., neutral planes"; //$NON-NLS-1$
    stepsFalseDesc = "whether the function does not have steps, i.e., neutral planes"; //$NON-NLS-1$
    ruggedDesc = "whether the function is rugged, i.e., goes up and down quickly"; //$NON-NLS-1$
    ruggedYesDesc = "the function is rugged, i.e., goes up and down quickly"; //$NON-NLS-1$
    ruggedNoDesc = "the function is not rugged"; //$NON-NLS-1$

    ints = new Integer[BBOBInput.MAX_FUNCTION + 1];
    for (fid = ints.length; (--fid) > 0;) {
      ints[fid] = Integer.valueOf(fid);
    }

    d1 = Double.valueOf(1d);
    d2 = Double.valueOf(2d);
    d21 = Double.valueOf(21d);
    d101 = Double.valueOf(101d);
    dinf = Double.valueOf(Double.POSITIVE_INFINITY);
    i1 = ints[1];
    i10 = ints[10];
    i25 = Integer.valueOf(25);
    i30 = Integer.valueOf(30);
    i100 = Integer.valueOf(100);
    i1000 = Integer.valueOf(1000);
    i1e6 = Integer.valueOf(1000000);

    for (final byte bdim : BBOBInput.DIMENSIONS) {

      dim = Byte.valueOf(bdim);
      d10pd = Double.valueOf(Math.ceil(Math.pow(10d, bdim)));

      try (final InstanceContext i = esb.createInstance()) {

        i.setName(BBOBInput._makeFunctionName(1, bdim));
        i.setDescription("the " + bdim + //$NON-NLS-1$
            "-dimensional Sphere function"); //$NON-NLS-1$

        i.setFeatureValue(BBOBInput.FEATURE_FUNCTION_ID, fidDesc, ints[1],
            null);
        fidDesc = null;

        i.setFeatureValue(BBOBInput.FEATURE_DIMENSION, dimDesc, dim, null);
        dimDesc = null;

        i.setFeatureValue(BBOBInput.FEATURE_APPROX_N_LOCAL_OPT, noptDesc,
            d1, noptUniDesc);
        noptDesc = null;
        noptUniDesc = null;

        i.setFeatureValue(BBOBInput.FEATURE_SYMMETRIC, symDesc,
            Boolean.TRUE, symDescSym);
        symDesc = null;
        symDescSym = null;

        i.setFeatureValue(BBOBInput.FEATURE_SEPARABLE, sepDesc,
            BBOBInput.FEATURE_VALUE_SEPARABLE_FULLY, sepFullyDesc);
        sepFullyDesc = null;
        sepDesc = null;

        i.setFeatureValue(BBOBInput.FEATURE_CONDITIONING, condDesc, i1,
            condOKDesc);
        condDesc = null;
        condOKDesc = null;

        i.setFeatureValue(BBOBInput.FEATURE_OPTIMUM_ON_BOUNDARY,
            optOnBoundDesc, Boolean.FALSE, optOnBoundFalseDesc);
        optOnBoundDesc = null;
        optOnBoundFalseDesc = null;

        i.setFeatureValue(BBOBInput.FEATURE_HAS_STEPS, stepsDesc,
            Boolean.FALSE, stepsFalseDesc);
        stepsDesc = null;
        stepsFalseDesc = null;

        i.setFeatureValue(BBOBInput.FEATURE_RUGGED, ruggedDesc,
            Boolean.FALSE, ruggedNoDesc);
        ruggedDesc = null;
        ruggedNoDesc = null;
      }

      try (final InstanceContext i = esb.createInstance()) {

        i.setName(BBOBInput._makeFunctionName(2, bdim));
        i.setDescription("the " + bdim + //$NON-NLS-1$
            "-dimensional Ellipsoidal function"); //$NON-NLS-1$

        i.setFeatureValue(BBOBInput.FEATURE_FUNCTION_ID, fidDesc, ints[2],
            null);

        i.setFeatureValue(BBOBInput.FEATURE_DIMENSION, dimDesc, dim, null);
        i.setFeatureValue(BBOBInput.FEATURE_APPROX_N_LOCAL_OPT, noptDesc,
            d1, noptUniDesc);
        i.setFeatureValue(BBOBInput.FEATURE_SYMMETRIC, symDesc,
            Boolean.FALSE, symDescAsym);
        symDescAsym = null;

        i.setFeatureValue(BBOBInput.FEATURE_SEPARABLE, sepDesc,
            BBOBInput.FEATURE_VALUE_SEPARABLE_FULLY, sepFullyDesc);
        i.setFeatureValue(BBOBInput.FEATURE_CONDITIONING, condDesc, i1e6,
            condIllDesc);
        condIllDesc = null;

        i.setFeatureValue(BBOBInput.FEATURE_OPTIMUM_ON_BOUNDARY,
            optOnBoundDesc, Boolean.FALSE, optOnBoundFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_HAS_STEPS, stepsDesc,
            Boolean.FALSE, stepsFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_RUGGED, ruggedDesc,
            Boolean.FALSE, ruggedNoDesc);
      }

      try (final InstanceContext i = esb.createInstance()) {

        i.setName(BBOBInput._makeFunctionName(3, bdim));
        i.setDescription("the " + bdim + //$NON-NLS-1$
            "-dimensional Rastrigin function"); //$NON-NLS-1$

        i.setFeatureValue(BBOBInput.FEATURE_FUNCTION_ID, fidDesc, ints[3],
            null);

        i.setFeatureValue(BBOBInput.FEATURE_DIMENSION, dimDesc, dim, null);
        i.setFeatureValue(BBOBInput.FEATURE_APPROX_N_LOCAL_OPT, noptDesc,
            d10pd, noptMultiDesc);
        noptMultiDesc = null;
        i.setFeatureValue(BBOBInput.FEATURE_SYMMETRIC, symDesc,
            Boolean.FALSE, symDescAsym);

        i.setFeatureValue(BBOBInput.FEATURE_SEPARABLE, sepDesc,
            BBOBInput.FEATURE_VALUE_SEPARABLE_NONE, sepNoneDesc);
        sepNoneDesc = null;
        i.setFeatureValue(BBOBInput.FEATURE_CONDITIONING, condDesc, i10,
            null);

        i.setFeatureValue(BBOBInput.FEATURE_OPTIMUM_ON_BOUNDARY,
            optOnBoundDesc, Boolean.FALSE, optOnBoundFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_HAS_STEPS, stepsDesc,
            Boolean.FALSE, stepsFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_RUGGED, ruggedDesc,
            Boolean.FALSE, ruggedNoDesc);
      }
      try (final InstanceContext i = esb.createInstance()) {

        i.setName(BBOBInput._makeFunctionName(4, bdim));
        i.setDescription("the " + bdim + //$NON-NLS-1$
            "-dimensional B\u00fcche-Rastrigin function"); //$NON-NLS-1$

        i.setFeatureValue(BBOBInput.FEATURE_FUNCTION_ID, fidDesc, ints[4],
            null);

        i.setFeatureValue(BBOBInput.FEATURE_DIMENSION, dimDesc, dim, null);
        i.setFeatureValue(BBOBInput.FEATURE_APPROX_N_LOCAL_OPT, noptDesc,
            d10pd, noptMultiDesc);

        i.setFeatureValue(BBOBInput.FEATURE_SYMMETRIC, symDesc,
            Boolean.FALSE, symDescAsym);

        i.setFeatureValue(BBOBInput.FEATURE_SEPARABLE, sepDesc,
            BBOBInput.FEATURE_VALUE_SEPARABLE_NONE, sepNoneDesc);

        i.setFeatureValue(BBOBInput.FEATURE_CONDITIONING, condDesc, i10,
            null);

        i.setFeatureValue(BBOBInput.FEATURE_OPTIMUM_ON_BOUNDARY,
            optOnBoundDesc, Boolean.FALSE, optOnBoundFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_HAS_STEPS, stepsDesc,
            Boolean.FALSE, stepsFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_RUGGED, ruggedDesc,
            Boolean.FALSE, ruggedNoDesc);
      }

      try (final InstanceContext i = esb.createInstance()) {

        i.setName(BBOBInput._makeFunctionName(5, bdim));
        i.setDescription("the " + bdim + //$NON-NLS-1$
            "-dimensional Linear Slope function"); //$NON-NLS-1$

        i.setFeatureValue(BBOBInput.FEATURE_FUNCTION_ID, fidDesc, ints[5],
            null);

        i.setFeatureValue(BBOBInput.FEATURE_DIMENSION, dimDesc, dim, null);
        i.setFeatureValue(BBOBInput.FEATURE_APPROX_N_LOCAL_OPT, noptDesc,
            d1, noptUniDesc);

        i.setFeatureValue(BBOBInput.FEATURE_SYMMETRIC, symDesc,
            Boolean.FALSE, symDescAsym);

        i.setFeatureValue(BBOBInput.FEATURE_SEPARABLE, sepDesc,
            BBOBInput.FEATURE_VALUE_SEPARABLE_FULLY, sepFullyDesc);

        i.setFeatureValue(BBOBInput.FEATURE_CONDITIONING, condDesc, i10,
            null);

        i.setFeatureValue(BBOBInput.FEATURE_OPTIMUM_ON_BOUNDARY,
            optOnBoundDesc, Boolean.TRUE, optOnBoundTrueDesc);
        optOnBoundTrueDesc = null;

        i.setFeatureValue(BBOBInput.FEATURE_HAS_STEPS, stepsDesc,
            Boolean.FALSE, stepsFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_RUGGED, ruggedDesc,
            Boolean.FALSE, ruggedNoDesc);
      }

      try (final InstanceContext i = esb.createInstance()) {

        i.setName(BBOBInput._makeFunctionName(6, bdim));
        i.setDescription("the " + bdim + //$NON-NLS-1$
            "-dimensional Attractive Sector function"); //$NON-NLS-1$

        i.setFeatureValue(BBOBInput.FEATURE_FUNCTION_ID, fidDesc, ints[6],
            null);

        i.setFeatureValue(BBOBInput.FEATURE_DIMENSION, dimDesc, dim, null);
        i.setFeatureValue(BBOBInput.FEATURE_APPROX_N_LOCAL_OPT, noptDesc,
            d1, noptUniDesc);

        i.setFeatureValue(BBOBInput.FEATURE_SYMMETRIC, symDesc,
            Boolean.FALSE, symDescAsym);

        i.setFeatureValue(BBOBInput.FEATURE_SEPARABLE, sepDesc,
            BBOBInput.FEATURE_VALUE_SEPARABLE_NONE, sepNoneDesc);

        i.setFeatureValue(BBOBInput.FEATURE_CONDITIONING, condDesc, i10,
            null); // TODO

        i.setFeatureValue(BBOBInput.FEATURE_OPTIMUM_ON_BOUNDARY,
            optOnBoundDesc, Boolean.FALSE, optOnBoundFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_HAS_STEPS, stepsDesc,
            Boolean.FALSE, stepsFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_RUGGED, ruggedDesc,
            Boolean.FALSE, ruggedNoDesc);
      }

      try (final InstanceContext i = esb.createInstance()) {

        i.setName(BBOBInput._makeFunctionName(7, bdim));
        i.setDescription("the " + bdim + //$NON-NLS-1$
            "-dimensional Step Ellipsoidal function"); //$NON-NLS-1$

        i.setFeatureValue(BBOBInput.FEATURE_FUNCTION_ID, fidDesc, ints[7],
            null);

        i.setFeatureValue(BBOBInput.FEATURE_DIMENSION, dimDesc, dim, null);
        i.setFeatureValue(BBOBInput.FEATURE_APPROX_N_LOCAL_OPT, noptDesc,
            d1, noptUniDesc);

        i.setFeatureValue(BBOBInput.FEATURE_SYMMETRIC, symDesc,
            Boolean.FALSE, symDescAsym);

        i.setFeatureValue(BBOBInput.FEATURE_SEPARABLE, sepDesc,
            BBOBInput.FEATURE_VALUE_SEPARABLE_NONE, sepNoneDesc);

        i.setFeatureValue(BBOBInput.FEATURE_CONDITIONING, condDesc, i100,
            null); // TODO

        i.setFeatureValue(BBOBInput.FEATURE_OPTIMUM_ON_BOUNDARY,
            optOnBoundDesc, Boolean.FALSE, optOnBoundFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_HAS_STEPS, stepsDesc,
            Boolean.TRUE, stepsTrueDesc);
        stepsTrueDesc = null;

        i.setFeatureValue(BBOBInput.FEATURE_RUGGED, ruggedDesc,
            Boolean.FALSE, ruggedNoDesc);
      }

      try (final InstanceContext i = esb.createInstance()) {

        i.setName(BBOBInput._makeFunctionName(8, bdim));
        i.setDescription("the " + bdim + //$NON-NLS-1$
            "-dimensional Rosenbrock function"); //$NON-NLS-1$

        i.setFeatureValue(BBOBInput.FEATURE_FUNCTION_ID, fidDesc, ints[8],
            null);

        i.setFeatureValue(BBOBInput.FEATURE_DIMENSION, dimDesc, dim, null);

        if (bdim <= 3) {
          d = d1; // from http://en.wikipedia.org/wiki/Rosenbrock_function
        } else {
          if (bdim <= 7) {
            d = d2; // from
                    // http://en.wikipedia.org/wiki/Rosenbrock_function
          } else {
            d = d2; // doi:10.1162/evco.2009.17.3.437)
          }
        }
        i.setFeatureValue(BBOBInput.FEATURE_APPROX_N_LOCAL_OPT, noptDesc,
            d, ((d == d1) ? noptUniDesc : noptMultiDesc));

        i.setFeatureValue(BBOBInput.FEATURE_SYMMETRIC, symDesc,
            Boolean.FALSE, symDescAsym);

        i.setFeatureValue(BBOBInput.FEATURE_SEPARABLE, sepDesc,
            BBOBInput.FEATURE_VALUE_SEPARABLE_PARTIALLY, sepPartiallyDesc);
        sepPartiallyDesc = null;

        i.setFeatureValue(BBOBInput.FEATURE_CONDITIONING, condDesc, i1,
            null); // TODO

        i.setFeatureValue(BBOBInput.FEATURE_OPTIMUM_ON_BOUNDARY,
            optOnBoundDesc, Boolean.FALSE, optOnBoundFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_HAS_STEPS, stepsDesc,
            Boolean.FALSE, stepsFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_RUGGED, ruggedDesc,
            Boolean.FALSE, ruggedNoDesc);
      }

      try (final InstanceContext i = esb.createInstance()) {

        i.setName(BBOBInput._makeFunctionName(9, bdim));
        i.setDescription("the " + bdim + //$NON-NLS-1$
            "-dimensional Rotated Rosenbrock function"); //$NON-NLS-1$

        i.setFeatureValue(BBOBInput.FEATURE_FUNCTION_ID, fidDesc, ints[9],
            null);

        i.setFeatureValue(BBOBInput.FEATURE_DIMENSION, dimDesc, dim, null);

        // if (bdim <= 3) {
        // d = d1; // from http://en.wikipedia.org/wiki/Rosenbrock_function
        // } else {
        // if (bdim <= 7) {
        // d = d2; // from http://en.wikipedia.org/wiki/Rosenbrock_function
        // } else {
        // d = d2; // doi:10.1162/evco.2009.17.3.437)
        // }
        // }
        i.setFeatureValue(BBOBInput.FEATURE_APPROX_N_LOCAL_OPT, noptDesc,
            d, ((d == d1) ? noptUniDesc : noptMultiDesc));

        i.setFeatureValue(BBOBInput.FEATURE_SYMMETRIC, symDesc,
            Boolean.FALSE, symDescAsym);

        i.setFeatureValue(BBOBInput.FEATURE_SEPARABLE, sepDesc,
            BBOBInput.FEATURE_VALUE_SEPARABLE_NONE, sepNoneDesc);

        i.setFeatureValue(BBOBInput.FEATURE_CONDITIONING, condDesc, i1,
            null); // TODO

        i.setFeatureValue(BBOBInput.FEATURE_OPTIMUM_ON_BOUNDARY,
            optOnBoundDesc, Boolean.FALSE, optOnBoundFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_HAS_STEPS, stepsDesc,
            Boolean.FALSE, stepsFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_RUGGED, ruggedDesc,
            Boolean.FALSE, ruggedNoDesc);
      }

      try (final InstanceContext i = esb.createInstance()) {

        i.setName(BBOBInput._makeFunctionName(10, bdim));
        i.setDescription("the " + bdim + //$NON-NLS-1$
            "-dimensional Rotated Ellipsoidal function"); //$NON-NLS-1$

        i.setFeatureValue(BBOBInput.FEATURE_FUNCTION_ID, fidDesc,
            ints[10], null);

        i.setFeatureValue(BBOBInput.FEATURE_DIMENSION, dimDesc, dim, null);

        i.setFeatureValue(BBOBInput.FEATURE_APPROX_N_LOCAL_OPT, noptDesc,
            d1, noptUniDesc);

        i.setFeatureValue(BBOBInput.FEATURE_SYMMETRIC, symDesc,
            Boolean.FALSE, symDescAsym);

        i.setFeatureValue(BBOBInput.FEATURE_SEPARABLE, sepDesc,
            BBOBInput.FEATURE_VALUE_SEPARABLE_NONE, sepNoneDesc);

        i.setFeatureValue(BBOBInput.FEATURE_CONDITIONING, condDesc, i1e6,
            condIllDesc);

        i.setFeatureValue(BBOBInput.FEATURE_OPTIMUM_ON_BOUNDARY,
            optOnBoundDesc, Boolean.FALSE, optOnBoundFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_HAS_STEPS, stepsDesc,
            Boolean.FALSE, stepsFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_RUGGED, ruggedDesc,
            Boolean.FALSE, ruggedNoDesc);
      }

      try (final InstanceContext i = esb.createInstance()) {

        i.setName(BBOBInput._makeFunctionName(11, bdim));
        i.setDescription("the " + bdim + //$NON-NLS-1$
            "-dimensional Discusl function"); //$NON-NLS-1$

        i.setFeatureValue(BBOBInput.FEATURE_FUNCTION_ID, fidDesc,
            ints[11], null);

        i.setFeatureValue(BBOBInput.FEATURE_DIMENSION, dimDesc, dim, null);

        i.setFeatureValue(BBOBInput.FEATURE_APPROX_N_LOCAL_OPT, noptDesc,
            d1, noptUniDesc);

        i.setFeatureValue(BBOBInput.FEATURE_SYMMETRIC, symDesc,
            Boolean.FALSE, symDescAsym);

        i.setFeatureValue(BBOBInput.FEATURE_SEPARABLE, sepDesc,
            BBOBInput.FEATURE_VALUE_SEPARABLE_NONE, sepNoneDesc);

        i.setFeatureValue(BBOBInput.FEATURE_CONDITIONING, condDesc, i1e6,
            condIllDesc);

        i.setFeatureValue(BBOBInput.FEATURE_OPTIMUM_ON_BOUNDARY,
            optOnBoundDesc, Boolean.FALSE, optOnBoundFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_HAS_STEPS, stepsDesc,
            Boolean.FALSE, stepsFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_RUGGED, ruggedDesc,
            Boolean.FALSE, ruggedNoDesc);
      }

      try (final InstanceContext i = esb.createInstance()) {

        i.setName(BBOBInput._makeFunctionName(12, bdim));
        i.setDescription("the " + bdim + //$NON-NLS-1$
            "-dimensional Bent Cigar function"); //$NON-NLS-1$

        i.setFeatureValue(BBOBInput.FEATURE_FUNCTION_ID, fidDesc,
            ints[12], null);

        i.setFeatureValue(BBOBInput.FEATURE_DIMENSION, dimDesc, dim, null);

        i.setFeatureValue(BBOBInput.FEATURE_APPROX_N_LOCAL_OPT, noptDesc,
            d1, noptUniDesc);

        i.setFeatureValue(BBOBInput.FEATURE_SYMMETRIC, symDesc,
            Boolean.FALSE, symDescAsym);

        i.setFeatureValue(BBOBInput.FEATURE_SEPARABLE, sepDesc,
            BBOBInput.FEATURE_VALUE_SEPARABLE_NONE, sepNoneDesc);

        i.setFeatureValue(BBOBInput.FEATURE_CONDITIONING, condDesc, i1e6,
            condIllDesc);

        i.setFeatureValue(BBOBInput.FEATURE_OPTIMUM_ON_BOUNDARY,
            optOnBoundDesc, Boolean.FALSE, optOnBoundFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_HAS_STEPS, stepsDesc,
            Boolean.FALSE, stepsFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_RUGGED, ruggedDesc,
            Boolean.FALSE, ruggedNoDesc);
      }

      try (final InstanceContext i = esb.createInstance()) {

        i.setName(BBOBInput._makeFunctionName(13, bdim));
        i.setDescription("the " + bdim + //$NON-NLS-1$
            "-dimensional Sharp Ridge function"); //$NON-NLS-1$

        i.setFeatureValue(BBOBInput.FEATURE_FUNCTION_ID, fidDesc,
            ints[13], null);

        i.setFeatureValue(BBOBInput.FEATURE_DIMENSION, dimDesc, dim, null);

        i.setFeatureValue(BBOBInput.FEATURE_APPROX_N_LOCAL_OPT, noptDesc,
            d1, noptUniDesc);

        i.setFeatureValue(BBOBInput.FEATURE_SYMMETRIC, symDesc,
            Boolean.FALSE, symDescAsym);

        i.setFeatureValue(BBOBInput.FEATURE_SEPARABLE, sepDesc,
            BBOBInput.FEATURE_VALUE_SEPARABLE_NONE, sepNoneDesc);

        i.setFeatureValue(BBOBInput.FEATURE_CONDITIONING, condDesc, i100,
            null);

        i.setFeatureValue(BBOBInput.FEATURE_OPTIMUM_ON_BOUNDARY,
            optOnBoundDesc, Boolean.FALSE, optOnBoundFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_HAS_STEPS, stepsDesc,
            Boolean.FALSE, stepsFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_RUGGED, ruggedDesc,
            Boolean.FALSE, ruggedNoDesc);
      }

      try (final InstanceContext i = esb.createInstance()) {

        i.setName(BBOBInput._makeFunctionName(14, bdim));
        i.setDescription("the " + bdim + //$NON-NLS-1$
            "-dimensional Different Powers function"); //$NON-NLS-1$

        i.setFeatureValue(BBOBInput.FEATURE_FUNCTION_ID, fidDesc,
            ints[14], null);

        i.setFeatureValue(BBOBInput.FEATURE_DIMENSION, dimDesc, dim, null);

        i.setFeatureValue(BBOBInput.FEATURE_APPROX_N_LOCAL_OPT, noptDesc,
            d1, noptUniDesc);

        i.setFeatureValue(BBOBInput.FEATURE_SYMMETRIC, symDesc,
            Boolean.FALSE, symDescAsym);

        i.setFeatureValue(BBOBInput.FEATURE_SEPARABLE, sepDesc,
            BBOBInput.FEATURE_VALUE_SEPARABLE_NONE, sepNoneDesc);

        i.setFeatureValue(BBOBInput.FEATURE_CONDITIONING, condDesc, i25,
            null);// for
        // [-5,5]
        // ??

        i.setFeatureValue(BBOBInput.FEATURE_OPTIMUM_ON_BOUNDARY,
            optOnBoundDesc, Boolean.FALSE, optOnBoundFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_HAS_STEPS, stepsDesc,
            Boolean.FALSE, stepsFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_RUGGED, ruggedDesc,
            Boolean.FALSE, ruggedNoDesc);
      }

      try (final InstanceContext i = esb.createInstance()) {

        i.setName(BBOBInput._makeFunctionName(15, bdim));
        i.setDescription("the " + bdim + //$NON-NLS-1$
            "-dimensional Rotated Rastrigin function"); //$NON-NLS-1$

        i.setFeatureValue(BBOBInput.FEATURE_FUNCTION_ID, fidDesc,
            ints[15], null);

        i.setFeatureValue(BBOBInput.FEATURE_DIMENSION, dimDesc, dim, null);

        i.setFeatureValue(BBOBInput.FEATURE_APPROX_N_LOCAL_OPT, noptDesc,
            d10pd, noptMultiDesc);

        i.setFeatureValue(BBOBInput.FEATURE_SYMMETRIC, symDesc,
            Boolean.FALSE, symDescAsym);

        i.setFeatureValue(BBOBInput.FEATURE_SEPARABLE, sepDesc,
            BBOBInput.FEATURE_VALUE_SEPARABLE_NONE, sepNoneDesc);

        i.setFeatureValue(BBOBInput.FEATURE_CONDITIONING, condDesc, i10,
            null);

        i.setFeatureValue(BBOBInput.FEATURE_OPTIMUM_ON_BOUNDARY,
            optOnBoundDesc, Boolean.FALSE, optOnBoundFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_HAS_STEPS, stepsDesc,
            Boolean.FALSE, stepsFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_RUGGED, ruggedDesc,
            Boolean.FALSE, ruggedNoDesc);
      }

      try (final InstanceContext i = esb.createInstance()) {

        i.setName(BBOBInput._makeFunctionName(16, bdim));
        i.setDescription("the " + bdim + //$NON-NLS-1$
            "-dimensional Weierstrass function"); //$NON-NLS-1$

        i.setFeatureValue(BBOBInput.FEATURE_FUNCTION_ID, fidDesc,
            ints[16], null);

        i.setFeatureValue(BBOBInput.FEATURE_DIMENSION, dimDesc, dim, null);

        i.setFeatureValue(BBOBInput.FEATURE_APPROX_N_LOCAL_OPT, noptDesc,
            dinf, noptMultiDesc);

        i.setFeatureValue(BBOBInput.FEATURE_SYMMETRIC, symDesc,
            Boolean.FALSE, symDescAsym);

        i.setFeatureValue(BBOBInput.FEATURE_SEPARABLE, sepDesc,
            BBOBInput.FEATURE_VALUE_SEPARABLE_NONE, sepNoneDesc);

        i.setFeatureValue(BBOBInput.FEATURE_CONDITIONING, condDesc, i1,
            null);

        i.setFeatureValue(BBOBInput.FEATURE_OPTIMUM_ON_BOUNDARY,
            optOnBoundDesc, Boolean.FALSE, optOnBoundFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_HAS_STEPS, stepsDesc,
            Boolean.FALSE, stepsFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_RUGGED, ruggedDesc,
            Boolean.TRUE, ruggedYesDesc);
        ruggedYesDesc = null;
      }

      try (final InstanceContext i = esb.createInstance()) {

        i.setName(BBOBInput._makeFunctionName(17, bdim));
        i.setDescription("the " + bdim + //$NON-NLS-1$
            "-dimensional Schaffer's F7 function"); //$NON-NLS-1$

        i.setFeatureValue(BBOBInput.FEATURE_FUNCTION_ID, fidDesc,
            ints[17], null);

        i.setFeatureValue(BBOBInput.FEATURE_DIMENSION, dimDesc, dim, null);

        i.setFeatureValue(BBOBInput.FEATURE_APPROX_N_LOCAL_OPT, noptDesc,
            dinf, noptMultiDesc);

        i.setFeatureValue(BBOBInput.FEATURE_SYMMETRIC, symDesc,
            Boolean.FALSE, symDescAsym);

        i.setFeatureValue(BBOBInput.FEATURE_SEPARABLE, sepDesc,
            BBOBInput.FEATURE_VALUE_SEPARABLE_NONE, sepNoneDesc);

        i.setFeatureValue(BBOBInput.FEATURE_CONDITIONING, condDesc, i1,
            null);// TODO

        i.setFeatureValue(BBOBInput.FEATURE_OPTIMUM_ON_BOUNDARY,
            optOnBoundDesc, Boolean.FALSE, optOnBoundFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_HAS_STEPS, stepsDesc,
            Boolean.FALSE, stepsFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_RUGGED, ruggedDesc,
            Boolean.TRUE, ruggedYesDesc);
      }

      try (final InstanceContext i = esb.createInstance()) {

        i.setName(BBOBInput._makeFunctionName(18, bdim));
        i.setDescription("the " + bdim + //$NON-NLS-1$
            "-dimensional Ill-Conditioned Schaffer's F7 function"); //$NON-NLS-1$

        i.setFeatureValue(BBOBInput.FEATURE_FUNCTION_ID, fidDesc,
            ints[18], null);

        i.setFeatureValue(BBOBInput.FEATURE_DIMENSION, dimDesc, dim, null);

        i.setFeatureValue(BBOBInput.FEATURE_APPROX_N_LOCAL_OPT, noptDesc,
            dinf, noptMultiDesc);

        i.setFeatureValue(BBOBInput.FEATURE_SYMMETRIC, symDesc,
            Boolean.FALSE, symDescAsym);

        i.setFeatureValue(BBOBInput.FEATURE_SEPARABLE, sepDesc,
            BBOBInput.FEATURE_VALUE_SEPARABLE_NONE, sepNoneDesc);

        i.setFeatureValue(BBOBInput.FEATURE_CONDITIONING, condDesc, i1000,
            null);// TODO

        i.setFeatureValue(BBOBInput.FEATURE_OPTIMUM_ON_BOUNDARY,
            optOnBoundDesc, Boolean.FALSE, optOnBoundFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_HAS_STEPS, stepsDesc,
            Boolean.FALSE, stepsFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_RUGGED, ruggedDesc,
            Boolean.TRUE, ruggedYesDesc);
      }

      try (final InstanceContext i = esb.createInstance()) {

        i.setName(BBOBInput._makeFunctionName(19, bdim));
        i.setDescription("the " + bdim + //$NON-NLS-1$
            "-dimensional Composite Griewank-Rosenbrock Function F8F2"); //$NON-NLS-1$

        i.setFeatureValue(BBOBInput.FEATURE_FUNCTION_ID, fidDesc,
            ints[19], null);

        i.setFeatureValue(BBOBInput.FEATURE_DIMENSION, dimDesc, dim, null);

        i.setFeatureValue(BBOBInput.FEATURE_APPROX_N_LOCAL_OPT, noptDesc,
            dinf, noptMultiDesc);

        i.setFeatureValue(BBOBInput.FEATURE_SYMMETRIC, symDesc,
            Boolean.FALSE, symDescAsym);

        i.setFeatureValue(BBOBInput.FEATURE_SEPARABLE, sepDesc,
            BBOBInput.FEATURE_VALUE_SEPARABLE_NONE, sepNoneDesc);

        i.setFeatureValue(BBOBInput.FEATURE_CONDITIONING, condDesc, i1,
            null); // TODO

        i.setFeatureValue(BBOBInput.FEATURE_OPTIMUM_ON_BOUNDARY,
            optOnBoundDesc, Boolean.FALSE, optOnBoundFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_HAS_STEPS, stepsDesc,
            Boolean.FALSE, stepsFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_RUGGED, ruggedDesc,
            Boolean.TRUE, ruggedYesDesc);
      }

      try (final InstanceContext i = esb.createInstance()) {

        i.setName(BBOBInput._makeFunctionName(20, bdim));
        i.setDescription("the " + bdim + //$NON-NLS-1$
            "-dimensional Schwefel Function"); //$NON-NLS-1$

        i.setFeatureValue(BBOBInput.FEATURE_FUNCTION_ID, fidDesc,
            ints[20], null);

        i.setFeatureValue(BBOBInput.FEATURE_DIMENSION, dimDesc, dim, null);

        i.setFeatureValue(BBOBInput.FEATURE_APPROX_N_LOCAL_OPT, noptDesc,
            dinf, noptMultiDesc);// TODO

        i.setFeatureValue(BBOBInput.FEATURE_SYMMETRIC, symDesc,
            Boolean.FALSE, symDescAsym);

        i.setFeatureValue(BBOBInput.FEATURE_SEPARABLE, sepDesc,
            BBOBInput.FEATURE_VALUE_SEPARABLE_NONE, sepNoneDesc);

        i.setFeatureValue(BBOBInput.FEATURE_CONDITIONING, condDesc, i1,
            null); // TODO

        i.setFeatureValue(BBOBInput.FEATURE_OPTIMUM_ON_BOUNDARY,
            optOnBoundDesc, Boolean.FALSE, optOnBoundFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_HAS_STEPS, stepsDesc,
            Boolean.FALSE, stepsFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_RUGGED, ruggedDesc,
            Boolean.FALSE, ruggedNoDesc);
      }

      try (final InstanceContext i = esb.createInstance()) {

        i.setName(BBOBInput._makeFunctionName(21, bdim));
        i.setDescription("the " + bdim + //$NON-NLS-1$
            "-dimensional Gallagher's Gaussian 101-me Peaks Function"); //$NON-NLS-1$

        i.setFeatureValue(BBOBInput.FEATURE_FUNCTION_ID, fidDesc,
            ints[21], null);

        i.setFeatureValue(BBOBInput.FEATURE_DIMENSION, dimDesc, dim, null);

        i.setFeatureValue(BBOBInput.FEATURE_APPROX_N_LOCAL_OPT, noptDesc,
            d101, noptMultiDesc);

        i.setFeatureValue(BBOBInput.FEATURE_SYMMETRIC, symDesc,
            Boolean.FALSE, symDescAsym);

        i.setFeatureValue(BBOBInput.FEATURE_SEPARABLE, sepDesc,
            BBOBInput.FEATURE_VALUE_SEPARABLE_NONE, sepNoneDesc);

        i.setFeatureValue(BBOBInput.FEATURE_CONDITIONING, condDesc, i30,
            null);

        i.setFeatureValue(BBOBInput.FEATURE_OPTIMUM_ON_BOUNDARY,
            optOnBoundDesc, Boolean.FALSE, optOnBoundFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_HAS_STEPS, stepsDesc,
            Boolean.FALSE, stepsFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_RUGGED, ruggedDesc,
            Boolean.FALSE, ruggedNoDesc);
      }

      try (final InstanceContext i = esb.createInstance()) {

        i.setName(BBOBInput._makeFunctionName(22, bdim));
        i.setDescription("the " + bdim + //$NON-NLS-1$
            "-dimensional Gallagher's Gaussian 21-hi Peaks Function"); //$NON-NLS-1$

        i.setFeatureValue(BBOBInput.FEATURE_FUNCTION_ID, fidDesc,
            ints[22], null);

        i.setFeatureValue(BBOBInput.FEATURE_DIMENSION, dimDesc, dim, null);

        i.setFeatureValue(BBOBInput.FEATURE_APPROX_N_LOCAL_OPT, noptDesc,
            d21, noptMultiDesc);

        i.setFeatureValue(BBOBInput.FEATURE_SYMMETRIC, symDesc,
            Boolean.FALSE, symDescAsym);

        i.setFeatureValue(BBOBInput.FEATURE_SEPARABLE, sepDesc,
            BBOBInput.FEATURE_VALUE_SEPARABLE_NONE, sepNoneDesc);

        i.setFeatureValue(BBOBInput.FEATURE_CONDITIONING, condDesc, i1000,
            null);

        i.setFeatureValue(BBOBInput.FEATURE_OPTIMUM_ON_BOUNDARY,
            optOnBoundDesc, Boolean.FALSE, optOnBoundFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_HAS_STEPS, stepsDesc,
            Boolean.FALSE, stepsFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_RUGGED, ruggedDesc,
            Boolean.FALSE, ruggedNoDesc);
      }

      try (final InstanceContext i = esb.createInstance()) {

        i.setName(BBOBInput._makeFunctionName(23, bdim));
        i.setDescription("the " + bdim + //$NON-NLS-1$
            "-dimensional Katsuura Function"); //$NON-NLS-1$

        i.setFeatureValue(BBOBInput.FEATURE_FUNCTION_ID, fidDesc,
            ints[23], null);

        i.setFeatureValue(BBOBInput.FEATURE_DIMENSION, dimDesc, dim, null);

        i.setFeatureValue(BBOBInput.FEATURE_APPROX_N_LOCAL_OPT, noptDesc,
            d10pd, noptMultiDesc);

        i.setFeatureValue(BBOBInput.FEATURE_SYMMETRIC, symDesc,
            Boolean.FALSE, symDescAsym);

        i.setFeatureValue(BBOBInput.FEATURE_SEPARABLE, sepDesc,
            BBOBInput.FEATURE_VALUE_SEPARABLE_NONE, sepNoneDesc);

        i.setFeatureValue(BBOBInput.FEATURE_CONDITIONING, condDesc, i1000,
            null);

        i.setFeatureValue(BBOBInput.FEATURE_OPTIMUM_ON_BOUNDARY,
            optOnBoundDesc, Boolean.FALSE, optOnBoundFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_HAS_STEPS, stepsDesc,
            Boolean.FALSE, stepsFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_RUGGED, ruggedDesc,
            Boolean.TRUE, ruggedYesDesc);
      }

      try (final InstanceContext i = esb.createInstance()) {

        i.setName(BBOBInput._makeFunctionName(24, bdim));
        i.setDescription("the " + bdim + //$NON-NLS-1$
            "-dimensional Lunacek bi-Rastrigin Function"); //$NON-NLS-1$

        i.setFeatureValue(BBOBInput.FEATURE_FUNCTION_ID, fidDesc,
            ints[24], null);

        i.setFeatureValue(BBOBInput.FEATURE_DIMENSION, dimDesc, dim, null);

        i.setFeatureValue(BBOBInput.FEATURE_APPROX_N_LOCAL_OPT, noptDesc,
            dinf, // TODO
            noptMultiDesc);

        i.setFeatureValue(BBOBInput.FEATURE_SYMMETRIC, symDesc,
            Boolean.FALSE, symDescAsym);

        i.setFeatureValue(BBOBInput.FEATURE_SEPARABLE, sepDesc,
            BBOBInput.FEATURE_VALUE_SEPARABLE_NONE, sepNoneDesc);

        i.setFeatureValue(BBOBInput.FEATURE_CONDITIONING, condDesc, i1,
            null); // TODO

        i.setFeatureValue(BBOBInput.FEATURE_OPTIMUM_ON_BOUNDARY,
            optOnBoundDesc, Boolean.FALSE, optOnBoundFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_HAS_STEPS, stepsDesc,
            Boolean.FALSE, stepsFalseDesc);

        i.setFeatureValue(BBOBInput.FEATURE_RUGGED, ruggedDesc,
            Boolean.TRUE, ruggedYesDesc);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void before(final IOJob job, final ExperimentSetContext data)
      throws Throwable {
    super.before(job, data);

    BBOBInput.makeBBOBDimensionSet(data);

    BBOBInput.makeBBOBInstanceSet(data);
  }

  /** {@inheritDoc} */
  @Override
  protected void path(final IOJob job, final ExperimentSetContext data,
      final Path path, final BasicFileAttributes attributes,
      final StreamEncoding<?, ?> encoding) throws Throwable {
    new _BBOBHandler(job, data)._handle(path);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "BBOB Experiment Data Input"; //$NON-NLS-1$
  }

  /** the loader */
  private static final class __BBOBInputLoader {
    /** the globally shared instance */
    static final BBOBInput INSTANCE = new BBOBInput();
  }
}
