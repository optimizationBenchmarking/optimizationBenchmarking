package examples.org.optimizationBenchmarking.experimentation.dataAndIO;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.DimensionContext;
import org.optimizationBenchmarking.experimentation.data.EDimensionDirection;
import org.optimizationBenchmarking.experimentation.data.EDimensionType;
import org.optimizationBenchmarking.experimentation.data.ExperimentContext;
import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.data.InstanceContext;
import org.optimizationBenchmarking.experimentation.data.InstanceRunsContext;
import org.optimizationBenchmarking.experimentation.data.Parameter;
import org.optimizationBenchmarking.experimentation.data.RunContext;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.parsers.BoundedFloatParser;
import org.optimizationBenchmarking.utils.parsers.BoundedLongParser;
import org.optimizationBenchmarking.utils.parsers.DoubleParser;
import org.optimizationBenchmarking.utils.parsers.LongParser;
import org.optimizationBenchmarking.utils.parsers.StrictLongParser;

/** A class for creating experiment sets */
public final class Example1 extends ExperimentSetCreator {

  /** create */
  public Example1() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final ExperimentSet buildExperimentSet() {
    final ExperimentSet es;

    try (final ExperimentSetContext esb = new ExperimentSetContext(
        Logger.getGlobal())) {

      this.__createDimensionSet(esb);

      this.__createInstanceSet(esb);

      this.__createExperimentSet(esb);

      es = esb.create();
    }

    return es;
  }

  /**
   * create the dimension set
   * 
   * @param dsc
   *          the context
   */
  private final void __createDimensionSet(final ExperimentSetContext dsc) {

    try (DimensionContext dc = dsc.createDimension()) {
      dc.setName("dimA"); //$NON-NLS-1$
      dc.setDescription("DescriptionModule of dimension A."); //$NON-NLS-1$
      dc.setParser(LongParser.INSTANCE);
      dc.setType(EDimensionType.ITERATION_FE);
      dc.setDirection(EDimensionDirection.INCREASING_STRICTLY);
    }

    try (DimensionContext dc = dsc.createDimension()) {
      dc.setName("dimB"); //$NON-NLS-1$
      dc.setDescription("DescriptionModule of dimension B."); //$NON-NLS-1$
      dc.setParser(new BoundedLongParser(0, 1000L));
      dc.setType(EDimensionType.ITERATION_SUB_FE);
      dc.setDirection(EDimensionDirection.DECREASING);
    }

    try (DimensionContext dc = dsc.createDimension()) {
      dc.setName("dimC"); //$NON-NLS-1$
      dc.setDescription("DescriptionModule of dimension C."); //$NON-NLS-1$
      dc.setParser(StrictLongParser.STRICT_INSTANCE);
      dc.setType(EDimensionType.RUNTIME_CPU);
      dc.setDirection(EDimensionDirection.INCREASING);
    }

    try (DimensionContext dc = dsc.createDimension()) {
      dc.setName("dimD"); //$NON-NLS-1$
      dc.setDescription("DescriptionModule of dimension D."); //$NON-NLS-1$
      dc.setParser(DoubleParser.INSTANCE);
      dc.setType(EDimensionType.RUNTIME_NORMALIZED);
      dc.setDirection(EDimensionDirection.INCREASING);
    }

    try (DimensionContext dc = dsc.createDimension()) {
      dc.setName("dimE"); //$NON-NLS-1$
      dc.setDescription("DescriptionModule of dimension E."); //$NON-NLS-1$
      dc.setParser(new BoundedFloatParser(-100f, 100f));
      dc.setType(EDimensionType.QUALITY_PROBLEM_DEPENDENT);
      dc.setDirection(EDimensionDirection.DECREASING);
    }

    try (DimensionContext dc = dsc.createDimension()) {
      dc.setName("dimF"); //$NON-NLS-1$
      dc.setParser(DoubleParser.INSTANCE);
      dc.setType(EDimensionType.QUALITY_PROBLEM_INDEPENDENT);
      dc.setDirection(EDimensionDirection.INCREASING);
    }
  }

  /**
   * create the instance set
   * 
   * @param isc
   *          the context
   */
  private final void __createInstanceSet(final ExperimentSetContext isc) {

    isc.declareFeature("scale",//$NON-NLS-1$
        "The scale of a problem."); //$NON-NLS-1$

    try (InstanceContext ic = isc.createInstance()) {
      ic.setName("First Instance"); //$NON-NLS-1$
      ic.setDescription("DescriptionModule of instance 1."); //$NON-NLS-1$
      ic.setFeatureValue("scale", Long.valueOf(10)); //$NON-NLS-1$
      ic.setFeatureValue("symmetric", //$NON-NLS-1$
          "Is the instance symmetric?",//$NON-NLS-1$
          "true",//$NON-NLS-1$
          "Yes, the instance is symmetric."); //$NON-NLS-1$
      ic.setUpperBound("dimA", "10000.0"); //$NON-NLS-1$//$NON-NLS-2$
    }

    try (InstanceContext ic = isc.createInstance()) {
      ic.setName("Second Instance"); //$NON-NLS-1$
      ic.setFeatureValue("scale", "100"); //$NON-NLS-1$//$NON-NLS-2$
      ic.setFeatureValue("symmetric", "false"); //$NON-NLS-1$//$NON-NLS-2$
      ic.setUpperBound("dimA", "20000"); //$NON-NLS-1$//$NON-NLS-2$
      ic.setLowerBound("dimE", "-2"); //$NON-NLS-1$//$NON-NLS-2$
      ic.setUpperBound("dimE", Long.valueOf(90)); //$NON-NLS-1$
    }

    try (InstanceContext ic = isc.createInstance()) {
      ic.setName("Third Instance"); //$NON-NLS-1$
      ic.setUpperBound("dimA", "3000"); //$NON-NLS-1$//$NON-NLS-2$
      ic.setDescription("DescriptionModule of instance 3."); //$NON-NLS-1$
      ic.setFeatureValue("scale", Byte.valueOf((byte) 120)); //$NON-NLS-1$
      ic.setFeatureValue("symmetric", "true"); //$NON-NLS-1$//$NON-NLS-2$
    }

    try (InstanceContext ic = isc.createInstance()) {
      ic.setName("Fourth Instance"); //$NON-NLS-1$
      ic.setUpperBound("dimA", "34500"); //$NON-NLS-1$//$NON-NLS-2$
      ic.setFeatureValue("scale", "700"); //$NON-NLS-1$//$NON-NLS-2$
      ic.setFeatureValue("symmetric", "false"); //$NON-NLS-1$//$NON-NLS-2$
    }

    try (InstanceContext ic = isc.createInstance()) {
      ic.setName("Unused Instance"); //$NON-NLS-1$
      ic.setUpperBound("dimA", "534500"); //$NON-NLS-1$//$NON-NLS-2$
      ic.setFeatureValue("scale", "9700"); //$NON-NLS-1$//$NON-NLS-2$
      ic.setFeatureValue("symmetric", "true"); //$NON-NLS-1$//$NON-NLS-2$
    }
  }

  /**
   * create the experiment set
   * 
   * @param isc
   *          the context
   */
  private final void __createExperimentSet(final ExperimentSetContext isc) {

    isc.declareParameter("ParameterX", "An odd parameter."); //$NON-NLS-1$//$NON-NLS-2$

    try (final ExperimentContext ec = isc.createExperiment()) {
      this.__createExperiment_1(ec);
    }
    try (final ExperimentContext ec = isc.createExperiment()) {
      this.__createExperiment_2(ec);
    }
  }

  /**
   * create the first experiment
   * 
   * @param ec
   *          the ExperimentContext
   */
  private final void __createExperiment_1(final ExperimentContext ec) {
    ec.setParameterValue("ParameterX", Long.valueOf(23)); //$NON-NLS-1$
    ec.setName("First Experiment"); //$NON-NLS-1$

    try (InstanceRunsContext irc = ec.createInstanceRuns()) {
      this.__createInstanceRuns_1_A(irc);
    }
    try (InstanceRunsContext irc = ec.createInstanceRuns()) {
      this.__createInstanceRuns_1_C(irc);
    }
    try (InstanceRunsContext irc = ec.createInstanceRuns()) {
      this.__createInstanceRuns_1_B(irc);
    }

  }

  /**
   * create the first instance run set of the first experiment
   * 
   * @param irc
   *          the Instance runs context
   */
  private final void __createInstanceRuns_1_A(final InstanceRunsContext irc) {
    irc.setInstance("First Instance"); //$NON-NLS-1$
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_1_A_1(rc);
    }
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_1_A_2(rc);
    }
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_1_A_3(rc);
    }
  }

  /**
   * create the first run of the instance run set of the first experiment
   * 
   * @param rc
   *          the Instance runs context
   */
  private final void __createRun_1_A_1(final RunContext rc) {
    rc.setParameterValue("ParameterY", "1234.4"); //$NON-NLS-1$//$NON-NLS-2$
    rc.setParameterValue(Parameter.PARAMETER_ALGORITHM, "Algorithm1"); //$NON-NLS-1$

    rc.addDataPoint("10 20 30 40 50 60"); //$NON-NLS-1$
    rc.addDataPoint("11 19 31 41 49 61"); //$NON-NLS-1$
    rc.addDataPoint("12 18 32 42 48 62"); //$NON-NLS-1$
    rc.addDataPoint("13 17 33 43 47 63"); //$NON-NLS-1$
    rc.addDataPoint("14 16 34 44 46 64"); //$NON-NLS-1$
    rc.addDataPoint("15 15 35 45 45 65"); //$NON-NLS-1$
  }

  /**
   * create the first run of the instance run set of the first experiment
   * 
   * @param rc
   *          the Instance runs context
   */
  private final void __createRun_1_A_2(final RunContext rc) {
    rc.setParameterValue("ParameterY", "1234.4"); //$NON-NLS-1$//$NON-NLS-2$
    rc.setParameterValue(Parameter.PARAMETER_ALGORITHM, "Algorithm1"); //$NON-NLS-1$

    rc.addDataPoint("9 20 37 40 55 60"); //$NON-NLS-1$
    rc.addDataPoint("10 19 38 41 52 61"); //$NON-NLS-1$
    rc.addDataPoint("11 18 39 42 51 62"); //$NON-NLS-1$
    rc.addDataPoint("12 17 41 43 50 63"); //$NON-NLS-1$
    rc.addDataPoint("13 16 45 44 40 64"); //$NON-NLS-1$
    rc.addDataPoint("14 15 69 45 35 65"); //$NON-NLS-1$
  }

  /**
   * create the first run of the instance run set of the first experiment
   * 
   * @param rc
   *          the Instance runs context
   */
  private final void __createRun_1_A_3(final RunContext rc) {
    rc.setParameterValue("ParameterY", "1234.4"); //$NON-NLS-1$//$NON-NLS-2$
    rc.setParameterValue(Parameter.PARAMETER_ALGORITHM, "Algorithm1"); //$NON-NLS-1$

    rc.addDataPoint("4 32 37 45 55 60"); //$NON-NLS-1$
    rc.addDataPoint("5 29 38 46 51 63"); //$NON-NLS-1$
    rc.addDataPoint("11 27 40 48 50 67"); //$NON-NLS-1$
    rc.addDataPoint("17 19 41 53 40 69"); //$NON-NLS-1$
    rc.addDataPoint("32 14 55 64 30 71"); //$NON-NLS-1$
    rc.addDataPoint("34 11 69 75 25 77"); //$NON-NLS-1$
  }

  /**
   * create the first instance run set of the first experiment
   * 
   * @param irc
   *          the Instance runs context
   */
  private final void __createInstanceRuns_1_B(final InstanceRunsContext irc) {
    irc.setInstance("Second Instance"); //$NON-NLS-1$
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_1_B_1(rc);
    }
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_1_B_2(rc);
    }
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_1_B_3(rc);
    }
  }

  /**
   * create the first run of the instance run set of the first experiment
   * 
   * @param rc
   *          the Instance runs context
   */
  private final void __createRun_1_B_1(final RunContext rc) {
    rc.setParameterValue("ParameterY", "1234.4"); //$NON-NLS-1$//$NON-NLS-2$
    rc.setParameterValue(Parameter.PARAMETER_ALGORITHM, "Algorithm1"); //$NON-NLS-1$

    rc.addDataPoint("10 20 30 40 50 60"); //$NON-NLS-1$
    rc.addDataPoint("11 19 31 41 49 61"); //$NON-NLS-1$
    rc.addDataPoint("15 15 35 45 45 65"); //$NON-NLS-1$
  }

  /**
   * create the first run of the instance run set of the first experiment
   * 
   * @param rc
   *          the Instance runs context
   */
  private final void __createRun_1_B_2(final RunContext rc) {
    rc.setParameterValue("ParameterY", "1234.4"); //$NON-NLS-1$//$NON-NLS-2$
    rc.setParameterValue(Parameter.PARAMETER_ALGORITHM, "Algorithm1"); //$NON-NLS-1$

    rc.addDataPoint("13 16 45 44 40.7 64.3"); //$NON-NLS-1$
    rc.addDataPoint("14 15 69 45 35.556 64.5"); //$NON-NLS-1$
  }

  /**
   * create the first run of the instance run set of the first experiment
   * 
   * @param rc
   *          the Instance runs context
   */
  private final void __createRun_1_B_3(final RunContext rc) {
    rc.setParameterValue("ParameterY", "1234.4"); //$NON-NLS-1$//$NON-NLS-2$
    rc.setParameterValue(Parameter.PARAMETER_ALGORITHM, "Algorithm1"); //$NON-NLS-1$

    rc.addDataPoint("4 32 37 45 55 60"); //$NON-NLS-1$
    rc.addDataPoint("34 11 69 75 25.4 77"); //$NON-NLS-1$
  }

  /**
   * create the first instance run set of the first experiment
   * 
   * @param irc
   *          the Instance runs context
   */
  private final void __createInstanceRuns_1_C(final InstanceRunsContext irc) {
    irc.setInstance("Third Instance"); //$NON-NLS-1$
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_1_C_2(rc);
    }
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_1_C_1(rc);
    }
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_1_C_2(rc);
    }
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_1_C_3(rc);
    }
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_1_C_3(rc);
    }
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_1_C_2(rc);
    }
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_1_C_1(rc);
    }
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_1_C_2(rc);
    }
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_1_C_3(rc);
    }
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_1_C_3(rc);
    }
  }

  /**
   * create the first run of the instance run set of the first experiment
   * 
   * @param rc
   *          the Instance runs context
   */
  private final void __createRun_1_C_1(final RunContext rc) {
    rc.setParameterValue("ParameterY", "1234.4"); //$NON-NLS-1$//$NON-NLS-2$
    rc.setParameterValue(Parameter.PARAMETER_ALGORITHM, "Algorithm1"); //$NON-NLS-1$

    rc.addDataPoint("07 30 30 40.7 50.1 60.7"); //$NON-NLS-1$
    rc.addDataPoint("09 29 33 41.3 49.2 61.6"); //$NON-NLS-1$
    rc.addDataPoint("11 15 37 42.2 48.3 62.5"); //$NON-NLS-1$
    rc.addDataPoint("17 13 39 43.3 47.4 63.4"); //$NON-NLS-1$
    rc.addDataPoint("24 11 44 44.4 46.5 64.3"); //$NON-NLS-1$
    rc.addDataPoint("25 11 45 45.5 45.7 65.2"); //$NON-NLS-1$
  }

  /**
   * create the first run of the instance run set of the first experiment
   * 
   * @param rc
   *          the Instance runs context
   */
  private final void __createRun_1_C_2(final RunContext rc) {
    rc.setParameterValue("ParameterY", "1234.4"); //$NON-NLS-1$//$NON-NLS-2$
    rc.setParameterValue(Parameter.PARAMETER_ALGORITHM, "Algorithm1"); //$NON-NLS-1$

    rc.addDataPoint("07 30 30 40.7 50.1 60.7"); //$NON-NLS-1$
    rc.addDataPoint("09 29 33 41.3 49.2 61.6"); //$NON-NLS-1$
  }

  /**
   * create the first run of the instance run set of the first experiment
   * 
   * @param rc
   *          the Instance runs context
   */
  private final void __createRun_1_C_3(final RunContext rc) {
    rc.setParameterValue("ParameterY", "1234.4"); //$NON-NLS-1$//$NON-NLS-2$
    rc.setParameterValue(Parameter.PARAMETER_ALGORITHM, "Algorithm1"); //$NON-NLS-1$

    rc.addDataPoint("11 15 37 42.2 48.3 62.5"); //$NON-NLS-1$
    rc.addDataPoint("17 13 39 43.3 47.4 63.4"); //$NON-NLS-1$
    rc.addDataPoint("24 11 44 44.4 46.5 64.3"); //$NON-NLS-1$
  }

  /**
   * create the second experiment
   * 
   * @param ec
   *          the ExperimentContext
   */
  private final void __createExperiment_2(final ExperimentContext ec) {
    ec.setParameterValue("ParameterY", Double.valueOf(77)); //$NON-NLS-1$
    ec.setParameterValue("ParameterZ", "abc"); //$NON-NLS-1$//$NON-NLS-2$
    ec.setName("Second Experiment"); //$NON-NLS-1$
    ec.setParameterValue(Parameter.PARAMETER_ALGORITHM_NAME,
        "Other Algorithm"); //$NON-NLS-1$
    ec.setParameterValue(Parameter.PARAMETER_ALGORITHM_CLASS,
        "org.x.MyClass"); //$NON-NLS-1$

    try (InstanceRunsContext irc = ec.createInstanceRuns()) {
      this.__createInstanceRuns_2_D(irc);
    }
    try (InstanceRunsContext irc = ec.createInstanceRuns()) {
      this.__createInstanceRuns_2_C(irc);
    }
    try (InstanceRunsContext irc = ec.createInstanceRuns()) {
      this.__createInstanceRuns_2_B(irc);
    }

  }

  /**
   * create the first instance run set of the first experiment
   * 
   * @param irc
   *          the Instance runs context
   */
  private final void __createInstanceRuns_2_D(final InstanceRunsContext irc) {
    irc.setInstance("Fourth Instance"); //$NON-NLS-1$
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_2_D_1(rc);
    }
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_2_D_2(rc);
    }
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_2_D_2(rc);
    }
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_2_D_3(rc);
    }
  }

  /**
   * create the first run of the instance run set of the first experiment
   * 
   * @param rc
   *          the Instance runs context
   */
  private final void __createRun_2_D_1(final RunContext rc) {

    rc.addDataPoint("10 20 30 40 50 60"); //$NON-NLS-1$
    rc.addDataPoint("11 19 31 41 49 61"); //$NON-NLS-1$
    rc.addDataPoint("12 18 32 42 48 62"); //$NON-NLS-1$
    rc.addDataPoint("13 17 33 43 47 63"); //$NON-NLS-1$
    rc.addDataPoint("14 16 34 44 46 64"); //$NON-NLS-1$
    rc.addDataPoint("15 15 35 45 45 65"); //$NON-NLS-1$
  }

  /**
   * create the first run of the instance run set of the first experiment
   * 
   * @param rc
   *          the Instance runs context
   */
  private final void __createRun_2_D_2(final RunContext rc) {

    rc.addDataPoint("9 20 37 40 55 60"); //$NON-NLS-1$
    rc.addDataPoint("10 19 38 41 52 61"); //$NON-NLS-1$
    rc.addDataPoint("11 18 39 42 51 62"); //$NON-NLS-1$
    rc.addDataPoint("12 17 41 43 50 63"); //$NON-NLS-1$
    rc.addDataPoint("13 16 45 44 40 64"); //$NON-NLS-1$
    rc.addDataPoint("14 15 69 45 35 65"); //$NON-NLS-1$
  }

  /**
   * create the first run of the instance run set of the first experiment
   * 
   * @param rc
   *          the Instance runs context
   */
  private final void __createRun_2_D_3(final RunContext rc) {
    rc.addDataPoint("4 32 37 45 55 60"); //$NON-NLS-1$
    rc.addDataPoint("5 29 38 46 51 63"); //$NON-NLS-1$
    rc.addDataPoint("11 27 40 48 50 67"); //$NON-NLS-1$
    rc.addDataPoint("17 19 41 53 40 69"); //$NON-NLS-1$
    rc.addDataPoint("32 14 55 64 30 71"); //$NON-NLS-1$
    rc.addDataPoint("34 11 69 75 25 77"); //$NON-NLS-1$
  }

  /**
   * create the first instance run set of the first experiment
   * 
   * @param irc
   *          the Instance runs context
   */
  private final void __createInstanceRuns_2_B(final InstanceRunsContext irc) {
    irc.setInstance("Second Instance"); //$NON-NLS-1$
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_2_B_1(rc);
    }
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_2_B_2(rc);
    }
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_2_B_3(rc);
    }
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_2_B_3(rc);
    }
  }

  /**
   * create the first run of the instance run set of the first experiment
   * 
   * @param rc
   *          the Instance runs context
   */
  private final void __createRun_2_B_1(final RunContext rc) {
    rc.addDataPoint("10 20 30 40 50 60"); //$NON-NLS-1$
    rc.addDataPoint("11 19 31 41 49 61"); //$NON-NLS-1$
    rc.addDataPoint("15 15 35 45 45 65"); //$NON-NLS-1$
  }

  /**
   * create the first run of the instance run set of the first experiment
   * 
   * @param rc
   *          the Instance runs context
   */
  private final void __createRun_2_B_2(final RunContext rc) {

    rc.addDataPoint("13 16 45 44 40.7 64.3"); //$NON-NLS-1$
    rc.addDataPoint("14 15 69 45 35.556 64.5"); //$NON-NLS-1$
  }

  /**
   * create the first run of the instance run set of the first experiment
   * 
   * @param rc
   *          the Instance runs context
   */
  private final void __createRun_2_B_3(final RunContext rc) {

    rc.addDataPoint("4 32 37 45 55 60"); //$NON-NLS-1$
    rc.addDataPoint("34 11 69 75 25.4 77"); //$NON-NLS-1$
  }

  /**
   * create the first instance run set of the first experiment
   * 
   * @param irc
   *          the Instance runs context
   */
  private final void __createInstanceRuns_2_C(final InstanceRunsContext irc) {
    irc.setInstance("Third Instance"); //$NON-NLS-1$
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_2_C_2(rc);
    }
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_2_C_1(rc);
    }
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_2_C_3(rc);
    }
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_2_C_2(rc);
    }
    try (final RunContext rc = irc.createRun()) {
      this.__createRun_2_C_3(rc);
    }
  }

  /**
   * create the first run of the instance run set of the first experiment
   * 
   * @param rc
   *          the Instance runs context
   */
  private final void __createRun_2_C_1(final RunContext rc) {

    rc.addDataPoint("07 30 30 40.7 50.1 60.7"); //$NON-NLS-1$
    rc.addDataPoint("09 29 33 41.3 49.2 61.6"); //$NON-NLS-1$
    rc.addDataPoint("11 15 37 42.2 48.3 62.5"); //$NON-NLS-1$
    rc.addDataPoint("17 13 39 43.3 47.4 63.4"); //$NON-NLS-1$
    rc.addDataPoint("24 11 44 44.4 46.5 64.3"); //$NON-NLS-1$
    rc.addDataPoint("25 11 45 45.5 45.7 65.2"); //$NON-NLS-1$
  }

  /**
   * create the first run of the instance run set of the first experiment
   * 
   * @param rc
   *          the Instance runs context
   */
  private final void __createRun_2_C_2(final RunContext rc) {

    rc.addDataPoint("07 30 30 40.7 50.1 60.7"); //$NON-NLS-1$
    rc.addDataPoint("09 29 33 41.3 49.2 61.6"); //$NON-NLS-1$
  }

  /**
   * create the first run of the instance run set of the first experiment
   * 
   * @param rc
   *          the Instance runs context
   */
  private final void __createRun_2_C_3(final RunContext rc) {

    rc.addDataPoint("11 15 37 42.2 48.3 62.5"); //$NON-NLS-1$
    rc.addDataPoint("17 13 39 43.3 47.4 63.4"); //$NON-NLS-1$
    rc.addDataPoint("24 11 44 44.4 46.5 64.3"); //$NON-NLS-1$
  }

  /**
   * The main routine
   * 
   * @param args
   *          the command line arguments
   */
  public static final void main(final String[] args) {
    Configuration.setup(args);
    new Example1().run();
  }
}
