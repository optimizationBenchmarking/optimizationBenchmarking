package org.optimizationBenchmarking.experimentation.io.tspSuite;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.DimensionContext;
import org.optimizationBenchmarking.experimentation.data.EDimensionDirection;
import org.optimizationBenchmarking.experimentation.data.EDimensionType;
import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.data.InstanceContext;
import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.structured.FileInputDriver;
import org.optimizationBenchmarking.utils.parsers.BoundedDoubleParser;
import org.optimizationBenchmarking.utils.parsers.BoundedLongParser;

/**
 * A class for loading <a
 * href="http://www.logisticPlanning.org/tsp/">TSPSuite</a> data sets into
 * the {@link org.optimizationBenchmarking.experimentation.data experiment
 * data structures}.
 */
public class TSPSuiteInputDriver extends
    FileInputDriver<ExperimentSetContext> {

  /** the tour length dimension */
  private static final String LENGTH = "L"; //$NON-NLS-1$

  /** the number of cities */
  private static final String SCALE = "n"; //$NON-NLS-1$

  /** the tour length dimension */
  private static final String SYMMETRIC = "symmetric"; //$NON-NLS-1$

  /** the tour length dimension */
  private static final String DISTANCE_TYPE = "type"; //$NON-NLS-1$

  /** Euclidean distance based on a list of 2D coordinates */
  private static final String EUC_2D = "EUC_2D"; //$NON-NLS-1$
  /** rounded-up Euclidean distance based on a list of 2D coordinates */
  private static final String CEIL_2D = "CEIL_2D"; //$NON-NLS-1$
  /**
   * geographical distance based on a list of longitude-latitude coordinate
   * pairs
   */
  private static final String GEO = "GEO"; //$NON-NLS-1$
  /** pseudo-Euclidean distance based on a list of 2D coordinates */
  private static final String ATT = "ATT"; //$NON-NLS-1$
  /** full distance matrix */
  private static final String MATRIX = "mat"; //$NON-NLS-1$

  /** an instance name */
  private static final String BURMA14 = "burma14"; //$NON-NLS-1$
  /** an instance name */
  private static final String ULYSSES16 = "ulysses16"; //$NON-NLS-1$
  /** an instance name */
  private static final String GR17 = "gr17"; //$NON-NLS-1$
  /** an instance name */
  private static final String GR21 = "gr21"; //$NON-NLS-1$
  /** an instance name */
  private static final String ULYSSES22 = "ulysses22"; //$NON-NLS-1$
  /** an instance name */
  private static final String GR24 = "gr24"; //$NON-NLS-1$
  /** an instance name */
  private static final String FRI26 = "fri26"; //$NON-NLS-1$
  /** an instance name */
  private static final String BAYG29 = "bayg29"; //$NON-NLS-1$
  /** an instance name */
  private static final String BAYS29 = "bays29"; //$NON-NLS-1$
  /** an instance name */
  private static final String DANTZIG42 = "dantzig42"; //$NON-NLS-1$
  /** an instance name */
  private static final String SWISS42 = "swiss42"; //$NON-NLS-1$
  /** an instance name */
  private static final String ATT48 = "att48"; //$NON-NLS-1$
  /** an instance name */
  private static final String GR48 = "gr48"; //$NON-NLS-1$
  /** an instance name */
  private static final String HK48 = "hk48"; //$NON-NLS-1$
  /** an instance name */
  private static final String EIL51 = "eil51"; //$NON-NLS-1$
  /** an instance name */
  private static final String BERLIN52 = "berlin52"; //$NON-NLS-1$
  /** an instance name */
  private static final String BRAZIL58 = "brazil58"; //$NON-NLS-1$
  /** an instance name */
  private static final String ST70 = "st70"; //$NON-NLS-1$
  /** an instance name */
  private static final String EIL76 = "eil76"; //$NON-NLS-1$
  /** an instance name */
  private static final String PR76 = "pr76"; //$NON-NLS-1$
  /** an instance name */
  private static final String GR96 = "gr96"; //$NON-NLS-1$
  /** an instance name */
  private static final String RAT99 = "rat99"; //$NON-NLS-1$
  /** an instance name */
  private static final String KROA100 = "kroA100"; //$NON-NLS-1$
  /** an instance name */
  private static final String KROB100 = "kroB100"; //$NON-NLS-1$
  /** an instance name */
  private static final String KROC100 = "kroC100"; //$NON-NLS-1$
  /** an instance name */
  private static final String KROD100 = "kroD100"; //$NON-NLS-1$
  /** an instance name */
  private static final String KROE100 = "kroE100"; //$NON-NLS-1$
  /** an instance name */
  private static final String RD100 = "rd100"; //$NON-NLS-1$
  /** an instance name */
  private static final String EIL101 = "eil101"; //$NON-NLS-1$
  /** an instance name */
  private static final String LIN105 = "lin105"; //$NON-NLS-1$
  /** an instance name */
  private static final String PR107 = "pr107"; //$NON-NLS-1$
  /** an instance name */
  private static final String GR120 = "gr120"; //$NON-NLS-1$
  /** an instance name */
  private static final String PR124 = "pr124"; //$NON-NLS-1$
  /** an instance name */
  private static final String BIER127 = "bier127"; //$NON-NLS-1$
  /** an instance name */
  private static final String CH130 = "ch130"; //$NON-NLS-1$
  /** an instance name */
  private static final String PR136 = "pr136"; //$NON-NLS-1$
  /** an instance name */
  private static final String GR137 = "gr137"; //$NON-NLS-1$
  /** an instance name */
  private static final String PR144 = "pr144"; //$NON-NLS-1$
  /** an instance name */
  private static final String CH150 = "ch150"; //$NON-NLS-1$
  /** an instance name */
  private static final String KROA150 = "kroA150"; //$NON-NLS-1$
  /** an instance name */
  private static final String KROB150 = "kroB150"; //$NON-NLS-1$
  /** an instance name */
  private static final String PR152 = "pr152"; //$NON-NLS-1$
  /** an instance name */
  private static final String U159 = "u159"; //$NON-NLS-1$
  /** an instance name */
  private static final String SI175 = "si175"; //$NON-NLS-1$
  /** an instance name */
  private static final String BRG180 = "brg180"; //$NON-NLS-1$
  /** an instance name */
  private static final String RAT195 = "rat195"; //$NON-NLS-1$
  /** an instance name */
  private static final String D198 = "d198"; //$NON-NLS-1$
  /** an instance name */
  private static final String KROA200 = "kroA200"; //$NON-NLS-1$
  /** an instance name */
  private static final String KROB200 = "kroB200"; //$NON-NLS-1$
  /** an instance name */
  private static final String GR202 = "gr202"; //$NON-NLS-1$
  /** an instance name */
  private static final String TS225 = "ts225"; //$NON-NLS-1$
  /** an instance name */
  private static final String TSP225 = "tsp225"; //$NON-NLS-1$
  /** an instance name */
  private static final String PR226 = "pr226"; //$NON-NLS-1$
  /** an instance name */
  private static final String GR229 = "gr229"; //$NON-NLS-1$
  /** an instance name */
  private static final String GIL262 = "gil262"; //$NON-NLS-1$
  /** an instance name */
  private static final String PR264 = "pr264"; //$NON-NLS-1$
  /** an instance name */
  private static final String A280 = "a280"; //$NON-NLS-1$
  /** an instance name */
  private static final String PR299 = "pr299"; //$NON-NLS-1$
  /** an instance name */
  private static final String LIN318 = "lin318"; //$NON-NLS-1$
  /** an instance name */
  private static final String RD400 = "rd400"; //$NON-NLS-1$
  /** an instance name */
  private static final String FL417 = "fl417"; //$NON-NLS-1$
  /** an instance name */
  private static final String GR431 = "gr431"; //$NON-NLS-1$
  /** an instance name */
  private static final String PR439 = "pr439"; //$NON-NLS-1$
  /** an instance name */
  private static final String PCB442 = "pcb442"; //$NON-NLS-1$
  /** an instance name */
  private static final String D493 = "d493"; //$NON-NLS-1$
  /** an instance name */
  private static final String ATT532 = "att532"; //$NON-NLS-1$
  /** an instance name */
  private static final String ALI535 = "ali535"; //$NON-NLS-1$
  /** an instance name */
  private static final String SI535 = "si535"; //$NON-NLS-1$
  /** an instance name */
  private static final String PA561 = "pa561"; //$NON-NLS-1$
  /** an instance name */
  private static final String U574 = "u574"; //$NON-NLS-1$
  /** an instance name */
  private static final String RAT575 = "rat575"; //$NON-NLS-1$
  /** an instance name */
  private static final String P654 = "p654"; //$NON-NLS-1$
  /** an instance name */
  private static final String D657 = "d657"; //$NON-NLS-1$
  /** an instance name */
  private static final String GR666 = "gr666"; //$NON-NLS-1$
  /** an instance name */
  private static final String U724 = "u724"; //$NON-NLS-1$
  /** an instance name */
  private static final String RAT783 = "rat783"; //$NON-NLS-1$
  /** an instance name */
  private static final String DSJ1000 = "dsj1000"; //$NON-NLS-1$
  /** an instance name */
  private static final String PR1002 = "pr1002"; //$NON-NLS-1$
  /** an instance name */
  private static final String SI1032 = "si1032"; //$NON-NLS-1$
  /** an instance name */
  private static final String U1060 = "u1060"; //$NON-NLS-1$
  /** an instance name */
  private static final String VM1084 = "vm1084"; //$NON-NLS-1$
  /** an instance name */
  private static final String PCB1173 = "pcb1173"; //$NON-NLS-1$
  /** an instance name */
  private static final String D1291 = "d1291"; //$NON-NLS-1$
  /** an instance name */
  private static final String RL1304 = "rl1304"; //$NON-NLS-1$
  /** an instance name */
  private static final String RL1323 = "rl1323"; //$NON-NLS-1$
  /** an instance name */
  private static final String NRW1379 = "nrw1379"; //$NON-NLS-1$
  /** an instance name */
  private static final String FL1400 = "fl1400"; //$NON-NLS-1$
  /** an instance name */
  private static final String U1432 = "u1432"; //$NON-NLS-1$
  /** an instance name */
  private static final String FL1577 = "fl1577"; //$NON-NLS-1$
  /** an instance name */
  private static final String D1655 = "d1655"; //$NON-NLS-1$
  /** an instance name */
  private static final String VM1748 = "vm1748"; //$NON-NLS-1$
  /** an instance name */
  private static final String U1817 = "u1817"; //$NON-NLS-1$
  /** an instance name */
  private static final String RL1889 = "rl1889"; //$NON-NLS-1$
  /** an instance name */
  private static final String D2103 = "d2103"; //$NON-NLS-1$
  /** an instance name */
  private static final String U2152 = "u2152"; //$NON-NLS-1$
  /** an instance name */
  private static final String U2319 = "u2319"; //$NON-NLS-1$
  /** an instance name */
  private static final String PR2392 = "pr2392"; //$NON-NLS-1$
  /** an instance name */
  private static final String PCB3038 = "pcb3038"; //$NON-NLS-1$
  /** an instance name */
  private static final String FL3795 = "fl3795"; //$NON-NLS-1$
  /** an instance name */
  private static final String FNL4461 = "fnl4461"; //$NON-NLS-1$
  /** an instance name */
  private static final String RL5915 = "rl5915"; //$NON-NLS-1$
  /** an instance name */
  private static final String RL5934 = "rl5934"; //$NON-NLS-1$
  /** an instance name */
  private static final String PLA7397 = "pla7397"; //$NON-NLS-1$
  /** an instance name */
  private static final String RL11849 = "rl11849"; //$NON-NLS-1$
  /** an instance name */
  private static final String USA13509 = "usa13509"; //$NON-NLS-1$
  /** an instance name */
  private static final String BRD14051 = "brd14051"; //$NON-NLS-1$
  /** an instance name */
  private static final String D15112 = "d15112"; //$NON-NLS-1$
  /** an instance name */
  private static final String D18512 = "d18512"; //$NON-NLS-1$
  /** an instance name */
  private static final String PLA33810 = "pla33810"; //$NON-NLS-1$
  /** an instance name */
  private static final String PLA85900 = "pla85900"; //$NON-NLS-1$ // Asymmetric Instances
  /** an instance name */
  private static final String BR17 = "br17"; //$NON-NLS-1$
  /** an instance name */
  private static final String FTV33 = "ftv33"; //$NON-NLS-1$
  /** an instance name */
  private static final String FTV35 = "ftv35"; //$NON-NLS-1$
  /** an instance name */
  private static final String FTV38 = "ftv38"; //$NON-NLS-1$
  /** an instance name */
  private static final String P43 = "p43"; //$NON-NLS-1$
  /** an instance name */
  private static final String FTV44 = "ftv44"; //$NON-NLS-1$
  /** an instance name */
  private static final String FTV47 = "ftv47"; //$NON-NLS-1$
  /** an instance name */
  private static final String RY48P = "ry48p"; //$NON-NLS-1$
  /** an instance name */
  private static final String FT53 = "ft53"; //$NON-NLS-1$
  /** an instance name */
  private static final String FTV55 = "ftv55"; //$NON-NLS-1$
  /** an instance name */
  private static final String FTV64 = "ftv64"; //$NON-NLS-1$
  /** an instance name */
  private static final String FT70 = "ft70"; //$NON-NLS-1$
  /** an instance name */
  private static final String FTV70 = "ftv70"; //$NON-NLS-1$
  /** an instance name */
  private static final String KRO124P = "kro124p"; //$NON-NLS-1$
  /** an instance name */
  private static final String FTV170 = "ftv170"; //$NON-NLS-1$
  /** an instance name */
  private static final String RBG323 = "rbg323"; //$NON-NLS-1$
  /** an instance name */
  private static final String RBG358 = "rbg358"; //$NON-NLS-1$
  /** an instance name */
  private static final String RBG403 = "rbg403"; //$NON-NLS-1$
  /** an instance name */
  private static final String RBG443 = "rbg443"; //$NON-NLS-1$

  /** the list of all instance names */
  private static final String[] ALL = { TSPSuiteInputDriver.BURMA14,
      TSPSuiteInputDriver.ULYSSES16, TSPSuiteInputDriver.BR17,
      TSPSuiteInputDriver.GR17, TSPSuiteInputDriver.GR21,
      TSPSuiteInputDriver.ULYSSES22, TSPSuiteInputDriver.GR24,
      TSPSuiteInputDriver.FRI26, TSPSuiteInputDriver.BAYG29,
      TSPSuiteInputDriver.BAYS29, TSPSuiteInputDriver.FTV33,
      TSPSuiteInputDriver.FTV35, TSPSuiteInputDriver.FTV38,
      TSPSuiteInputDriver.DANTZIG42, TSPSuiteInputDriver.SWISS42,
      TSPSuiteInputDriver.P43, TSPSuiteInputDriver.FTV44,
      TSPSuiteInputDriver.ATT48, TSPSuiteInputDriver.FTV47,
      TSPSuiteInputDriver.GR48, TSPSuiteInputDriver.HK48,
      TSPSuiteInputDriver.RY48P, TSPSuiteInputDriver.EIL51,
      TSPSuiteInputDriver.BERLIN52, TSPSuiteInputDriver.FT53,
      TSPSuiteInputDriver.FTV55, TSPSuiteInputDriver.BRAZIL58,
      TSPSuiteInputDriver.FTV64, TSPSuiteInputDriver.FT70,
      TSPSuiteInputDriver.ST70, TSPSuiteInputDriver.FTV70,
      TSPSuiteInputDriver.EIL76, TSPSuiteInputDriver.PR76,
      TSPSuiteInputDriver.GR96, TSPSuiteInputDriver.RAT99,
      TSPSuiteInputDriver.KRO124P, TSPSuiteInputDriver.KROA100,
      TSPSuiteInputDriver.KROB100, TSPSuiteInputDriver.KROC100,
      TSPSuiteInputDriver.KROD100, TSPSuiteInputDriver.KROE100,
      TSPSuiteInputDriver.RD100, TSPSuiteInputDriver.EIL101,
      TSPSuiteInputDriver.LIN105, TSPSuiteInputDriver.PR107,
      TSPSuiteInputDriver.GR120, TSPSuiteInputDriver.PR124,
      TSPSuiteInputDriver.BIER127, TSPSuiteInputDriver.CH130,
      TSPSuiteInputDriver.PR136, TSPSuiteInputDriver.GR137,
      TSPSuiteInputDriver.PR144, TSPSuiteInputDriver.CH150,
      TSPSuiteInputDriver.KROA150, TSPSuiteInputDriver.KROB150,
      TSPSuiteInputDriver.PR152, TSPSuiteInputDriver.U159,
      TSPSuiteInputDriver.FTV170, TSPSuiteInputDriver.SI175,
      TSPSuiteInputDriver.BRG180, TSPSuiteInputDriver.RAT195,
      TSPSuiteInputDriver.D198, TSPSuiteInputDriver.KROA200,
      TSPSuiteInputDriver.KROB200, TSPSuiteInputDriver.GR202,
      TSPSuiteInputDriver.TS225, TSPSuiteInputDriver.TSP225,
      TSPSuiteInputDriver.PR226, TSPSuiteInputDriver.GR229,
      TSPSuiteInputDriver.GIL262, TSPSuiteInputDriver.PR264,
      TSPSuiteInputDriver.A280, TSPSuiteInputDriver.PR299,
      TSPSuiteInputDriver.LIN318, TSPSuiteInputDriver.RBG323,
      TSPSuiteInputDriver.RBG358, TSPSuiteInputDriver.RD400,
      TSPSuiteInputDriver.RBG403, TSPSuiteInputDriver.FL417,
      TSPSuiteInputDriver.GR431, TSPSuiteInputDriver.PR439,
      TSPSuiteInputDriver.PCB442, TSPSuiteInputDriver.RBG443,
      TSPSuiteInputDriver.D493, TSPSuiteInputDriver.ATT532,
      TSPSuiteInputDriver.ALI535, TSPSuiteInputDriver.SI535,
      TSPSuiteInputDriver.PA561, TSPSuiteInputDriver.U574,
      TSPSuiteInputDriver.RAT575, TSPSuiteInputDriver.P654,
      TSPSuiteInputDriver.D657, TSPSuiteInputDriver.GR666,
      TSPSuiteInputDriver.U724, TSPSuiteInputDriver.RAT783,
      TSPSuiteInputDriver.DSJ1000, TSPSuiteInputDriver.PR1002,
      TSPSuiteInputDriver.SI1032, TSPSuiteInputDriver.U1060,
      TSPSuiteInputDriver.VM1084, TSPSuiteInputDriver.PCB1173,
      TSPSuiteInputDriver.D1291, TSPSuiteInputDriver.RL1304,
      TSPSuiteInputDriver.RL1323, TSPSuiteInputDriver.NRW1379,
      TSPSuiteInputDriver.FL1400, TSPSuiteInputDriver.U1432,
      TSPSuiteInputDriver.FL1577, TSPSuiteInputDriver.D1655,
      TSPSuiteInputDriver.VM1748, TSPSuiteInputDriver.U1817,
      TSPSuiteInputDriver.RL1889, TSPSuiteInputDriver.D2103,
      TSPSuiteInputDriver.U2152, TSPSuiteInputDriver.U2319,
      TSPSuiteInputDriver.PR2392, TSPSuiteInputDriver.PCB3038,
      TSPSuiteInputDriver.FL3795, TSPSuiteInputDriver.FNL4461,
      TSPSuiteInputDriver.RL5915, TSPSuiteInputDriver.RL5934,
      TSPSuiteInputDriver.PLA7397, TSPSuiteInputDriver.RL11849,
      TSPSuiteInputDriver.USA13509, TSPSuiteInputDriver.BRD14051,
      TSPSuiteInputDriver.D15112, TSPSuiteInputDriver.D18512,
      TSPSuiteInputDriver.PLA33810, TSPSuiteInputDriver.PLA85900 };

  static {
    Arrays.sort(TSPSuiteInputDriver.ALL);
  }

  /** the globally shared instance */
  public static final TSPSuiteInputDriver INSTANCE = new TSPSuiteInputDriver();

  /** create */
  private TSPSuiteInputDriver() {
    super();
  }

  /**
   * get the instance name
   * 
   * @param n
   *          the string
   * @return the instance name, or {@code null} if none found
   */
  static final String _instanceName(final String n) {
    int i;
    i = Arrays.binarySearch(TSPSuiteInputDriver.ALL, n);
    if ((i >= 0) && (i < TSPSuiteInputDriver.ALL.length)) {
      return TSPSuiteInputDriver.ALL[i];
    }
    for (final String t : TSPSuiteInputDriver.ALL) {
      if (t.equalsIgnoreCase(n)) {
        return t;
      }
    }
    return null;
  }

  /**
   * fill in the dimension set
   * 
   * @param esb
   *          the experiment set builder
   */
  public static final void makeTSPSuiteDimensionSet(
      final ExperimentSetContext esb) {
    final BoundedLongParser p1, p2;
    final BoundedDoubleParser bd;

    p1 = new BoundedLongParser(0, Long.MAX_VALUE);
    p2 = new BoundedLongParser(1, Long.MAX_VALUE);
    bd = new BoundedDoubleParser(00, Double.MAX_VALUE);

    try (final DimensionContext d = esb.createDimension()) {
      d.setName("FEs"); //$NON-NLS-1$
      d.setDescription("the number of fully constructed candidate solutions"); //$NON-NLS-1$
      d.setDirection(EDimensionDirection.INCREASING_STRICTLY);
      d.setType(EDimensionType.ITERATION_FE);
      d.setParser(p1);
    }

    try (final DimensionContext d = esb.createDimension()) {
      d.setName("DEs"); //$NON-NLS-1$
      d.setDescription("the number of calls to the distance function (between two nodes)"); //$NON-NLS-1$
      d.setDirection(EDimensionDirection.INCREASING);
      d.setType(EDimensionType.ITERATION_SUB_FE);
      d.setParser(p2);
    }

    try (final DimensionContext d = esb.createDimension()) {
      d.setName("AT"); //$NON-NLS-1$
      d.setDescription("the consumed clock runtime in milliseconds"); //$NON-NLS-1$
      d.setDirection(EDimensionDirection.INCREASING);
      d.setType(EDimensionType.RUNTIME_CPU);
      d.setParser(p1);
    }

    try (final DimensionContext d = esb.createDimension()) {
      d.setName("NT"); //$NON-NLS-1$
      d.setDescription("the consumed normalized runtime, i.e., AT divided by a performance factor based on the clock runtime of the Double-Ended Nearest Neighbor heuristic on the same instance"); //$NON-NLS-1$
      d.setDirection(EDimensionDirection.INCREASING);
      d.setType(EDimensionType.RUNTIME_NORMALIZED);
      d.setParser(bd);
    }

    try (final DimensionContext d = esb.createDimension()) {
      d.setName(TSPSuiteInputDriver.LENGTH);
      d.setDescription("the best tour length discovered so far"); //$NON-NLS-1$
      d.setDirection(EDimensionDirection.DECREASING);
      d.setType(EDimensionType.QUALITY_PROBLEM_DEPENDENT);
      d.setParser(p2);
    }

    try (final DimensionContext d = esb.createDimension()) {
      d.setName("F"); //$NON-NLS-1$
      d.setDescription(//
      "the best tour length discovered so far (" + TSPSuiteInputDriver.LENGTH + //$NON-NLS-1$
          ") divided by the length of the optimal tour"); //$NON-NLS-1$
      d.setDirection(EDimensionDirection.DECREASING);
      d.setType(EDimensionType.QUALITY_PROBLEM_INDEPENDENT);
      d.setParser(bd);
    }

  }

  /**
   * fill in the instance set
   * 
   * @param esb
   *          the experiment set builder
   */
  public static final void makeTSPLibInstanceSet(
      final ExperimentSetContext esb) {

    // TODO: need to check distance measures
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.A280);

      i.setFeatureValue(TSPSuiteInputDriver.SCALE,
          "the number of nodes or cities in the TSP", //$NON-NLS-1$
          Integer.valueOf(280), null);

      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(2579));

      i.setFeatureValue(
          TSPSuiteInputDriver.SYMMETRIC,//
          "whether the TSP is symmetric, i.e., whether dist(i,j)=dist(j,i)",//$NON-NLS-1$
          Boolean.TRUE,//
          "the TSP is symmetric"); //$NON-NLS-1$
      i.setDescription("Drilling problem (Ludwig)."); //$NON-NLS-1$

      i.setFeatureValue(
          TSPSuiteInputDriver.DISTANCE_TYPE,
          "the distance measure",//$NON-NLS-1$
          TSPSuiteInputDriver.EUC_2D,
          "nodes are points in the two-dimensional Euclidean plane"); //$NON-NLS-1$
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.ALI535);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(535));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(202310));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("535 Airports around the globe (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE, null,
          TSPSuiteInputDriver.GEO, "geographical distances"); //$NON-NLS-1$
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.ATT48);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(48));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(10628));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("48 capitals of the US (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE, null,
          TSPSuiteInputDriver.ATT,
          "pseudo-Euclidean distance in two dimensions"); //$NON-NLS-1$
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.ATT532);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(532));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(27686));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("The so-called AT&amp;T532 is 532 city problem solved to optimality by Padberg and Rinaldi using branch-and-cut methods."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.ATT);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.BAYG29);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(29));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(1610));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("29 Cities in Bavaria, geographical distances (Gr&ouml;tschel,J&uuml;nger,Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.GEO); // "UPPER_ROW");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.BAYS29);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(29));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(2020));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("29 cities in Bavaria, street distances (Gr&ouml;tschel,J&uuml;nger,Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE, null,
          TSPSuiteInputDriver.MATRIX,
          "distances provided as matrix, origin unclear"); //$NON-NLS-1$
      // "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.BERLIN52);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(52));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(7542));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("52 locations in Berlin (Gr&ouml;tschel)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.BIER127);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(127));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(118282));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("127 Bierg&auml;rten (&quot;Beer Gardens&quot;, open-air beer restaurants) in Augsburg, Germany (J&uuml;nger/Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.BRAZIL58);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(58));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(25395));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("58 cities in Brazil (Ferreira)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.GEO);// "UPPER_ROW");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.BRD14051);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(14051));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(469385));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("West-Germany in the borders of 1989 (Bachem/Wottawa)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.BRG180);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(180));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(1950));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Bridge tournament problem (Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE, null, "bridge", //$NON-NLS-1$
          "Bridge tournament problem"); //$NON-NLS-1$
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.BURMA14);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(14));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(3323));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("14 cities in Burma (Zaw Win)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.GEO);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.CH130);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(130));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(6110));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("130 city problem (Churritz)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.CH150);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(150));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(6528));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("150 city Problem (churritz)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.D198);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(198));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(15780));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.D493);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(493));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(35002));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.D657);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(657));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(48912));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.D1291);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(1291));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(50801));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.D1655);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(1655));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(62128));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.D2103);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(2103));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(80450));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.D15112);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(15112));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(1573084));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Germany-Problem (A.Rohe)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.D18512);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(18512));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(645238));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Germany (united with GDR, the former East Germany) (Bachem/Wottawa)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.DANTZIG42);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(42));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(699));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("42 cities (Dantzig)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.GEO);// "LOWER_DIAG_ROW");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.DSJ1000);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(1000));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(18660188));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Clustered random problem (Johnson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE, null,
          TSPSuiteInputDriver.CEIL_2D,
          "rounded-up Euclidean distance in 2D plane"); //$NON-NLS-1$
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.EIL51);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(51));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(426));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("51-city problem (Christofides/Eilon)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.EIL76);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(76));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(538));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("76-city problem (Christofides/Eilon)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.EIL101);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(101));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(629));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("101-city problem (Christofides/Eilon)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.FL417);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(417));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(11861));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.FL1400);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(1400));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(20127));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.FL1577);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(1577));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(22249));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)"); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.FL3795);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(3795));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(28772));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.FNL4461);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(4461));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(182566));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("The new five provinces of Germany, i.e., the former GDR (Eastern Germany) (Bachem/Wottawa)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.FRI26);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(26));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(937));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("26 cities (Fricker)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.GEO);// "LOWER_DIAG_ROW");
      // $NON-NLS-1$
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.GIL262);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(262));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(2378));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("262-city problem (Gillet/Johnson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.GR17);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(17));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(2085));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("17-city problem (Gr&ouml;tschel)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "LOWER_DIAG_ROW"
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.GR21);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(21));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(2707));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("21-city problem (Gr&ouml;tschel)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "LOWER_DIAG_ROW"
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.GR24);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(24));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(1272));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("24-city problem (Gr&ouml;tschel)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "LOWER_DIAG_ROW"
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.GR48);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(48));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(5046));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("48-city problem (Gr&ouml;tschel)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "LOWER_DIAG_ROW"
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.GR96);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(96));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(55209));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Africa-Subproblem of 666-city TSP (Gr&ouml;tschel)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.GEO);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.GR120);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(120));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(6942));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("120 cities in Germany (Gr&ouml;tschel)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.GEO);// "LOWER_DIAG_ROW");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.GR137);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(137));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(69853));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("America-Subproblem of 666-city TSP (Gr&ouml;tschel)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.GEO);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.GR202);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(202));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(40160));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Europe-Subproblem of 666-city TSP (Gr&ouml;tschel)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.GEO);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.GR229);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(229));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(134602));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Asia/Australia-Subproblem of 666-city TSP (Gr&ouml;tschel)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.GEO);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.GR431);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(431));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(171414));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Europe/Asia/Australia-Subproblem of 666-city TSP (Gr&ouml;tschel)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.GEO);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.GR666);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(666));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(294358));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("666 cities around the world (Gr&ouml;tschel)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.GEO);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.HK48);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(48));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(11461));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("48-city problem (Held/Karp)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "LOWER_DIAG_ROW");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.KROA100);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(100));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(21282));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("100-city problem A (Krolak/Felts/Nelson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.KROB100);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(100));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(22141));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("100-city problem B (Krolak/Felts/Nelson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.KROC100);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(100));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(20749));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("100-city problem C (Krolak/Felts/Nelson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.KROD100);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(100));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(21294));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("100-city problem D (Krolak/Felts/Nelson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.KROE100);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(100));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(22068));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("100-city problem E (Krolak/Felts/Nelson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.KROA150);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(150));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(26524));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("150-city problem A (Krolak/Felts/Nelson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.KROB150);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(150));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(26130));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("150-city problem B (Krolak/Felts/Nelson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.KROA200);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(200));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(29368));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("200-city problem A (Krolak/Felts/Nelson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.KROB200);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(200));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(29437));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("200-city problem B (Krolak/Felts/Nelson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.LIN105);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(105));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(14379));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("105-city problem (Subproblem of lin318)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.LIN318);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(318));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(42029));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("The problem is posed by Lin and Kernighan as an open tour with fixed ends, but it can easily be converted to a TSP. Padberg and Gr&ouml;tschel used a combination of cutting-plane and branch-and-bound methods to find the optimal tour for this problem."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.NRW1379);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(1379));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(56638));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("1379 Orte in Nordrhein-Westfalen (Bachem/Wottawa)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.P654);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(654));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(34643));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.PA561);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(561));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(2763));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("561-city problem (Kleinschmidt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// LOWER_DIAG_ROW;
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.PCB442);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(442));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(50778));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Gr&ouml;tschel/J&uuml;nger/Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.PCB1173);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(1173));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(56892));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (J&uuml;nger/Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.PCB3038);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(3038));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(137694));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Junger/Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.PLA7397);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(7397));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(23260728));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Programmed logic array (Johnson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.CEIL_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.PLA33810);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(33810));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(66048945));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Programmed logic array (Johnson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.CEIL_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.PLA85900);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(85900));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(142382641));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Programmed logic array (Johnson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.CEIL_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.PR76);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(76));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(108159));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("76-city problem (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.PR107);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(107));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(44303));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("107-city problem (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.PR124);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(124));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(59030));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("124-city problem (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.PR136);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(136));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(96772));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("136-city problem (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.PR144);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(144));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(58537));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("144-city problem (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.PR152);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(152));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(73682));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("152-city problem (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.PR226);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(226));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(80369));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("226-city problem (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.PR264);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(264));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(49135));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("264-city problem (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.PR299);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(299));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(48191));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("299-city problem (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.PR439);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(439));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(107217));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("439-city problem (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.PR1002);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(1002));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(259045));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("1002-city problem (Padberg/Rinaldi)"); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.PR2392);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(2392));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(378032));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("2392-city problem (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.RAT99);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(99));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(1211));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Rattled grid (Pulleyblank)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.RAT195);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(195));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(2323));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Rattled grid (Pulleyblank)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.RAT575);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(575));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(6773));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Rattled grid (Pulleyblank)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.RAT783);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(783));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(8806));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Rattled grid (Pulleyblank): The city positions of this problem are obtained by small, random displacements from a regular 27&times;29 lattice. This problem has been solved to optimality by Cook et al."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.RD100);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(100));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(7910));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("100-city random TSP (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.RD400);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(400));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(15281));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("400-city random TSP (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.RL1304);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(1304));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(252948));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("1304-city TSP (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.RL1323);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(1323));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(270199));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("1323-city TSP (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.RL1889);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(1889));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(316536));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("1889-city TSP (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.RL5915);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(5915));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(565530));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("5915-city TSP (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.RL5934);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(5934));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(556045));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("5934-city TSP (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.RL11849);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(11849));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(923288));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("11849-city TSP (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.SI175);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(175));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(21407));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("TSP (M. Hofmeister)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "UPPER_DIAG_ROW");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.SI535);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(535));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(48450));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("TSP (M. Hofmeister)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "UPPER_DIAG_ROW");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.SI1032);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(1032));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(92650));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("TSP (M. Hofmeister)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "UPPER_DIAG_ROW");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.ST70);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(70));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(675));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("70-city problem (Smith/Thompson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.SWISS42);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(42));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(1273));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("42 cities in Switzerland (Fricker)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.GEO);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.TS225);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(225));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(126643));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("225-city problem (J&uuml;nger,R&auml;cke,Tsch&ouml;cke)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.TSP225);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(225));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(3916));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("A TSP problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.U159);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(159));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(42080));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.U574);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(574));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(36905));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.U724);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(724));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(41910));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.U1060);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(1060));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(224094));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.U1432);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(1432));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(152970));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.U1817);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(1817));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(57201));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.U2152);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(2152));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(64253));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.U2319);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(2319));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(234256));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.ULYSSES16);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(16));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(6859));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Odyssey of Ulysses (Gr&ouml;tschel/Padberg)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.GEO);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.ULYSSES22);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(22));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(7013));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Odyssey of Ulysses (Gr&ouml;tschel/Padberg)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.GEO);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.USA13509);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(13509));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(19982859));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Cities with population at least 500 in the continental US. Contributed by David Applegate and Andre Rohe, based on the data set &quot;US.lat-long&quot; from the ftp site <a href=\"ftp://ftp.cs.toronto.edu\">ftp.cs.toronto.edu</a>. The file US.lat-long.Z can be found in the directory /doc/geography."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.VM1084);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(1084));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(239297));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("1084-city problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.VM1748);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(1748));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(336556));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.TRUE);
      i.setDescription("1784-city problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.BR17);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(17));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(39));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.FALSE);
      i.setDescription("17 city problem (Repetto)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.FT53);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(53));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(6905));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.FT70);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(70));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(38673));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.FTV33);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(34));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(1286));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.FTV35);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(36));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(1473));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.FTV38);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(39));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(1530));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.FTV44);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(45));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(1613));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.FTV47);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(48));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(1776));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.FTV55);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(56));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(1608));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.FTV64);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(65));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(1839));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.FTV70);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(71));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(1950));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.FTV170);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(171));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(2755));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.KRO124P);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(100));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(36230));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.P43);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(43));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(5620));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Repetto,Pekny)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.RBG323);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(323));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(1326));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Stacker crane application (Ascheuer)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.RBG358);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(358));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(1163));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Stacker crane application (Ascheuer)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.RBG403);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(403));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(2465));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Stacker crane application (Ascheuer)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.RBG443);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(443));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(2720));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Stacker crane application (Ascheuer)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInputDriver.RY48P);
      i.setFeatureValue(TSPSuiteInputDriver.SCALE, Integer.valueOf(48));
      i.setLowerBound(TSPSuiteInputDriver.LENGTH, Long.valueOf(14422));
      i.setFeatureValue(TSPSuiteInputDriver.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInputDriver.DISTANCE_TYPE,
          TSPSuiteInputDriver.MATRIX);// "FULL_MATRIX");
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void doLoadPath(final ExperimentSetContext loadContext,
      final Path path, final Logger logger,
      final StreamEncoding<?, ?> defaultEncoding) throws IOException {
    Throwable error;

    error = null;

    try {
      TSPSuiteInputDriver.makeTSPSuiteDimensionSet(loadContext);
    } catch (final Throwable a) {
      error = ErrorUtils.aggregateError(error, a);
    }
    try {
      TSPSuiteInputDriver.makeTSPLibInstanceSet(loadContext);
    } catch (final Throwable b) {
      error = ErrorUtils.aggregateError(error, b);
    }

    try {
      new _TSPSuiteHandler(loadContext)._loadPath(path, logger,
          defaultEncoding);
    } catch (final Throwable c) {
      error = ErrorUtils.aggregateError(error, c);
    }

    if (error != null) {
      ErrorUtils.throwAsIOException(error);
    }
  }
}
