package org.optimizationBenchmarking.experimentation.io.impl.tspSuite;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.DimensionContext;
import org.optimizationBenchmarking.experimentation.data.EDimensionDirection;
import org.optimizationBenchmarking.experimentation.data.EDimensionType;
import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.data.InstanceContext;
import org.optimizationBenchmarking.experimentation.data.RunContext;
import org.optimizationBenchmarking.experimentation.io.impl.abstr.ExperimentSetFileInput;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOTool;
import org.optimizationBenchmarking.utils.parsers.BoundedDoubleParser;
import org.optimizationBenchmarking.utils.parsers.BoundedLongParser;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A class for loading <a
 * href="http://www.logisticPlanning.org/tsp/">TSPSuite</a> data sets into
 * the {@link org.optimizationBenchmarking.experimentation.data experiment
 * data structures}.
 */
public class TSPSuiteInput extends ExperimentSetFileInput {
  /** the string indicating the begin of a comment: {@value} */
  private static final String COMMENT_START = "//"; //$NON-NLS-1$
  /**
   * the identifier of the section in the job files which holds the
   * algorithm information
   */
  private static final String ALGORITHM_DATA_SECTION = "ALGORITHM_DATA_SECTION"; //$NON-NLS-1$
  /**
   * the identifier to begin the section in the job files which holds the
   * logged information: {@value}
   */
  private static final String LOG_DATA_SECTION = "LOG_DATA_SECTION"; //$NON-NLS-1$
  /**
   * the identifier beginning the section in the job files which holds the
   * infos about the deterministic initializer: {@value}
   */
  private static final String DETERMINISTIC_INITIALIZATION_SECTION = "DETERMINISTIC_INITIALIZATION_SECTION"; //$NON-NLS-1$
  /** the string used to end sections: {@value} */
  private static final String SECTION_END = "SECTION_END"; //$NON-NLS-1$

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

  /** create */
  TSPSuiteInput() {
    super();
  }

  /**
   * Get the instance of the {@link TSPSuiteInput}
   * 
   * @return the instance of the {@link TSPSuiteInput}
   */
  public static final TSPSuiteInput getInstance() {
    return __TSPSuiteInputLoader.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  protected _TSPSuiteInputToken createToken(final IOJob job,
      final ExperimentSetContext data) {
    return new _TSPSuiteInputToken(data);
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
    i = Arrays.binarySearch(TSPSuiteInput.__TSPSuiteInputLoader.ALL, n);
    if ((i >= 0) && (i < TSPSuiteInput.__TSPSuiteInputLoader.ALL.length)) {
      return TSPSuiteInput.__TSPSuiteInputLoader.ALL[i];
    }
    for (final String t : TSPSuiteInput.__TSPSuiteInputLoader.ALL) {
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

    p1 = new BoundedLongParser(0L, Long.MAX_VALUE);
    p2 = new BoundedLongParser(1L, Long.MAX_VALUE);
    bd = new BoundedDoubleParser(0d, Double.MAX_VALUE);

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
      d.setName(TSPSuiteInput.LENGTH);
      d.setDescription("the best tour length discovered so far"); //$NON-NLS-1$
      d.setDirection(EDimensionDirection.DECREASING);
      d.setType(EDimensionType.QUALITY_PROBLEM_DEPENDENT);
      d.setParser(p2);
    }

    try (final DimensionContext d = esb.createDimension()) {
      d.setName("F"); //$NON-NLS-1$
      d.setDescription(//
      "the best tour length discovered so far (" + TSPSuiteInput.LENGTH + //$NON-NLS-1$
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
      i.setName(TSPSuiteInput.A280);

      i.setFeatureValue(TSPSuiteInput.SCALE,
          "the number of nodes or cities in the TSP", //$NON-NLS-1$
          Integer.valueOf(280), null);

      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(2579));

      i.setFeatureValue(
          TSPSuiteInput.SYMMETRIC,//
          "whether the TSP is symmetric, i.e., whether dist(i,j)=dist(j,i)",//$NON-NLS-1$
          Boolean.TRUE,//
          "the TSP is symmetric"); //$NON-NLS-1$
      i.setDescription("Drilling problem (Ludwig)."); //$NON-NLS-1$

      i.setFeatureValue(
          TSPSuiteInput.DISTANCE_TYPE,
          "the distance measure",//$NON-NLS-1$
          TSPSuiteInput.EUC_2D,
          "nodes are points in the two-dimensional Euclidean plane"); //$NON-NLS-1$
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.ALI535);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(535));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(202310));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("535 Airports around the globe (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, null,
          TSPSuiteInput.GEO, "geographical distances"); //$NON-NLS-1$
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.ATT48);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(48));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(10628));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("48 capitals of the US (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, null,
          TSPSuiteInput.ATT, "pseudo-Euclidean distance in two dimensions"); //$NON-NLS-1$
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.ATT532);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(532));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(27686));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("The so-called AT&amp;T532 is 532 city problem solved to optimality by Padberg and Rinaldi using branch-and-cut methods."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.ATT);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.BAYG29);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(29));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(1610));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("29 Cities in Bavaria, geographical distances (Gr&ouml;tschel,J&uuml;nger,Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.GEO); // "UPPER_ROW");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.BAYS29);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(29));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(2020));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("29 cities in Bavaria, street distances (Gr&ouml;tschel,J&uuml;nger,Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, null,
          TSPSuiteInput.MATRIX,
          "distances provided as matrix, origin unclear"); //$NON-NLS-1$
      // "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.BERLIN52);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(52));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(7542));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("52 locations in Berlin (Gr&ouml;tschel)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.BIER127);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(127));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(118282));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("127 Bierg&auml;rten (&quot;Beer Gardens&quot;, open-air beer restaurants) in Augsburg, Germany (J&uuml;nger/Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.BRAZIL58);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(58));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(25395));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("58 cities in Brazil (Ferreira)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.GEO);// "UPPER_ROW");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.BRD14051);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(14051));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(469385));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("West-Germany in the borders of 1989 (Bachem/Wottawa)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.BRG180);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(180));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(1950));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Bridge tournament problem (Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, null, "bridge", //$NON-NLS-1$
          "Bridge tournament problem"); //$NON-NLS-1$
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.BURMA14);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(14));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(3323));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("14 cities in Burma (Zaw Win)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.GEO);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.CH130);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(130));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(6110));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("130 city problem (Churritz)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.CH150);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(150));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(6528));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("150 city Problem (churritz)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.D198);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(198));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(15780));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.D493);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(493));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(35002));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.D657);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(657));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(48912));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.D1291);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(1291));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(50801));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.D1655);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(1655));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(62128));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.D2103);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(2103));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(80450));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.D15112);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(15112));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(1573084));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Germany-Problem (A.Rohe)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.D18512);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(18512));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(645238));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Germany (united with GDR, the former East Germany) (Bachem/Wottawa)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.DANTZIG42);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(42));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(699));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("42 cities (Dantzig)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.GEO);// "LOWER_DIAG_ROW");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.DSJ1000);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(1000));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(18660188));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Clustered random problem (Johnson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, null,
          TSPSuiteInput.CEIL_2D,
          "rounded-up Euclidean distance in 2D plane"); //$NON-NLS-1$
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.EIL51);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(51));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(426));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("51-city problem (Christofides/Eilon)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.EIL76);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(76));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(538));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("76-city problem (Christofides/Eilon)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.EIL101);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(101));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(629));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("101-city problem (Christofides/Eilon)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.FL417);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(417));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(11861));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.FL1400);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(1400));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(20127));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.FL1577);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(1577));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(22249));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)"); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.FL3795);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(3795));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(28772));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.FNL4461);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(4461));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(182566));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("The new five provinces of Germany, i.e., the former GDR (Eastern Germany) (Bachem/Wottawa)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.FRI26);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(26));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(937));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("26 cities (Fricker)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.GEO);// "LOWER_DIAG_ROW");
      // $NON-NLS-1$
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.GIL262);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(262));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(2378));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("262-city problem (Gillet/Johnson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.GR17);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(17));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(2085));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("17-city problem (Gr&ouml;tschel)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "LOWER_DIAG_ROW"
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.GR21);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(21));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(2707));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("21-city problem (Gr&ouml;tschel)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "LOWER_DIAG_ROW"
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.GR24);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(24));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(1272));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("24-city problem (Gr&ouml;tschel)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "LOWER_DIAG_ROW"
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.GR48);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(48));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(5046));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("48-city problem (Gr&ouml;tschel)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "LOWER_DIAG_ROW"
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.GR96);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(96));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(55209));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Africa-Subproblem of 666-city TSP (Gr&ouml;tschel)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.GEO);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.GR120);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(120));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(6942));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("120 cities in Germany (Gr&ouml;tschel)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.GEO);// "LOWER_DIAG_ROW");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.GR137);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(137));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(69853));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("America-Subproblem of 666-city TSP (Gr&ouml;tschel)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.GEO);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.GR202);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(202));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(40160));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Europe-Subproblem of 666-city TSP (Gr&ouml;tschel)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.GEO);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.GR229);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(229));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(134602));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Asia/Australia-Subproblem of 666-city TSP (Gr&ouml;tschel)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.GEO);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.GR431);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(431));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(171414));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Europe/Asia/Australia-Subproblem of 666-city TSP (Gr&ouml;tschel)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.GEO);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.GR666);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(666));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(294358));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("666 cities around the world (Gr&ouml;tschel)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.GEO);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.HK48);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(48));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(11461));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("48-city problem (Held/Karp)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "LOWER_DIAG_ROW");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.KROA100);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(100));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(21282));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("100-city problem A (Krolak/Felts/Nelson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.KROB100);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(100));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(22141));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("100-city problem B (Krolak/Felts/Nelson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.KROC100);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(100));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(20749));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("100-city problem C (Krolak/Felts/Nelson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.KROD100);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(100));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(21294));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("100-city problem D (Krolak/Felts/Nelson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.KROE100);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(100));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(22068));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("100-city problem E (Krolak/Felts/Nelson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.KROA150);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(150));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(26524));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("150-city problem A (Krolak/Felts/Nelson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.KROB150);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(150));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(26130));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("150-city problem B (Krolak/Felts/Nelson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.KROA200);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(200));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(29368));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("200-city problem A (Krolak/Felts/Nelson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.KROB200);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(200));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(29437));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("200-city problem B (Krolak/Felts/Nelson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.LIN105);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(105));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(14379));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("105-city problem (Subproblem of lin318)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.LIN318);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(318));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(42029));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("The problem is posed by Lin and Kernighan as an open tour with fixed ends, but it can easily be converted to a TSP. Padberg and Gr&ouml;tschel used a combination of cutting-plane and branch-and-bound methods to find the optimal tour for this problem."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.NRW1379);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(1379));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(56638));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("1379 Orte in Nordrhein-Westfalen (Bachem/Wottawa)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.P654);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(654));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(34643));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.PA561);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(561));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(2763));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("561-city problem (Kleinschmidt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// LOWER_DIAG_ROW;
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.PCB442);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(442));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(50778));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Gr&ouml;tschel/J&uuml;nger/Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.PCB1173);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(1173));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(56892));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (J&uuml;nger/Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.PCB3038);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(3038));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(137694));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Junger/Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.PLA7397);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(7397));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(23260728));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Programmed logic array (Johnson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.CEIL_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.PLA33810);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(33810));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(66048945));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Programmed logic array (Johnson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.CEIL_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.PLA85900);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(85900));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(142382641));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Programmed logic array (Johnson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.CEIL_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.PR76);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(76));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(108159));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("76-city problem (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.PR107);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(107));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(44303));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("107-city problem (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.PR124);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(124));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(59030));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("124-city problem (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.PR136);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(136));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(96772));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("136-city problem (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.PR144);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(144));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(58537));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("144-city problem (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.PR152);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(152));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(73682));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("152-city problem (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.PR226);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(226));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(80369));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("226-city problem (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.PR264);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(264));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(49135));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("264-city problem (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.PR299);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(299));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(48191));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("299-city problem (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.PR439);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(439));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(107217));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("439-city problem (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.PR1002);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(1002));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(259045));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("1002-city problem (Padberg/Rinaldi)"); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.PR2392);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(2392));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(378032));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("2392-city problem (Padberg/Rinaldi)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.RAT99);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(99));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(1211));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Rattled grid (Pulleyblank)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.RAT195);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(195));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(2323));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Rattled grid (Pulleyblank)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.RAT575);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(575));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(6773));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Rattled grid (Pulleyblank)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.RAT783);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(783));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(8806));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Rattled grid (Pulleyblank): The city positions of this problem are obtained by small, random displacements from a regular 27&times;29 lattice. This problem has been solved to optimality by Cook et al."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.RD100);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(100));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(7910));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("100-city random TSP (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.RD400);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(400));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(15281));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("400-city random TSP (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.RL1304);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(1304));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(252948));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("1304-city TSP (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.RL1323);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(1323));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(270199));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("1323-city TSP (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.RL1889);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(1889));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(316536));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("1889-city TSP (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.RL5915);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(5915));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(565530));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("5915-city TSP (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.RL5934);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(5934));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(556045));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("5934-city TSP (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.RL11849);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(11849));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(923288));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("11849-city TSP (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.SI175);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(175));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(21407));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("TSP (M. Hofmeister)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "UPPER_DIAG_ROW");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.SI535);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(535));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(48450));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("TSP (M. Hofmeister)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "UPPER_DIAG_ROW");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.SI1032);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(1032));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(92650));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("TSP (M. Hofmeister)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "UPPER_DIAG_ROW");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.ST70);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(70));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(675));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("70-city problem (Smith/Thompson)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.SWISS42);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(42));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(1273));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("42 cities in Switzerland (Fricker)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.GEO);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.TS225);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(225));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(126643));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("225-city problem (J&uuml;nger,R&auml;cke,Tsch&ouml;cke)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.TSP225);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(225));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(3916));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("A TSP problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.U159);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(159));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(42080));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.U574);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(574));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(36905));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.U724);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(724));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(41910));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.U1060);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(1060));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(224094));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.U1432);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(1432));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(152970));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.U1817);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(1817));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(57201));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.U2152);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(2152));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(64253));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.U2319);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(2319));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(234256));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Drilling problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.ULYSSES16);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(16));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(6859));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Odyssey of Ulysses (Gr&ouml;tschel/Padberg)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.GEO);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.ULYSSES22);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(22));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(7013));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Odyssey of Ulysses (Gr&ouml;tschel/Padberg)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.GEO);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.USA13509);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(13509));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(19982859));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("Cities with population at least 500 in the continental US. Contributed by David Applegate and Andre Rohe, based on the data set &quot;US.lat-long&quot; from the ftp site <a href=\"ftp://ftp.cs.toronto.edu\">ftp.cs.toronto.edu</a>. The file US.lat-long.Z can be found in the directory /doc/geography."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.VM1084);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(1084));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(239297));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("1084-city problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.VM1748);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(1748));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(336556));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.TRUE);
      i.setDescription("1784-city problem (Reinelt)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.EUC_2D);
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.BR17);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(17));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(39));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.FALSE);
      i.setDescription("17 city problem (Repetto)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.FT53);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(53));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(6905));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.FT70);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(70));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(38673));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.FTV33);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(34));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(1286));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.FTV35);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(36));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(1473));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.FTV38);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(39));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(1530));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.FTV44);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(45));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(1613));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.FTV47);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(48));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(1776));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.FTV55);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(56));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(1608));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.FTV64);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(65));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(1839));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.FTV70);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(71));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(1950));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.FTV170);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(171));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(2755));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.KRO124P);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(100));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(36230));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.P43);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(43));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(5620));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Repetto,Pekny)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.RBG323);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(323));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(1326));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Stacker crane application (Ascheuer)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.RBG358);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(358));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(1163));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Stacker crane application (Ascheuer)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.RBG403);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(403));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(2465));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Stacker crane application (Ascheuer)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.RBG443);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(443));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(2720));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Stacker crane application (Ascheuer)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "FULL_MATRIX");
    }
    try (final InstanceContext i = esb.createInstance()) {
      i.setName(TSPSuiteInput.RY48P);
      i.setFeatureValue(TSPSuiteInput.SCALE, Integer.valueOf(48));
      i.setLowerBound(TSPSuiteInput.LENGTH, Long.valueOf(14422));
      i.setFeatureValue(TSPSuiteInput.SYMMETRIC, Boolean.FALSE);
      i.setDescription("Asymmetric TSP (Fischetti)."); //$NON-NLS-1$
      i.setFeatureValue(TSPSuiteInput.DISTANCE_TYPE, TSPSuiteInput.MATRIX);// "FULL_MATRIX");
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void before(final IOJob job, final ExperimentSetContext data)
      throws Throwable {
    super.before(job, data);
    TSPSuiteInput.makeTSPSuiteDimensionSet(data);
    TSPSuiteInput.makeTSPLibInstanceSet(data);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isFileInDirectoryLoadable(final IOJob job,
      final ExperimentSetContext data, final Path path,
      final BasicFileAttributes attributes) throws Throwable {
    final String name;
    int len;
    char ch;

    if (super.isFileInDirectoryLoadable(job, data, path, attributes)) {

      name = TextUtils.normalize(path.getFileName().toString());
      if (name != null) {
        len = name.length();
        if (len > 4) {
          ch = name.charAt(--len);
          if ((ch == 't') || (ch == 'T')) {
            ch = name.charAt(--len);
            if ((ch == 'x') || (ch == 'X')) {
              ch = name.charAt(--len);
              if ((ch == 't') || (ch == 'T')) {
                ch = name.charAt(--len);
                if (ch == '.') {
                  return true;
                }
              }
            }
          }
        }
      }
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  protected void leaveDirectory(final IOJob job,
      final ExperimentSetContext data, final Path path) throws Throwable {
    _TSPSuiteInputToken token;

    token = ((_TSPSuiteInputToken) (job.getToken()));

    if (token.m_instanceRunsRoot != null) {
      if (Files.isSameFile(token.m_instanceRunsRoot, path)) {
        token._popIRSC();
      }
    }

    if (token.m_experimentRoot != null) {
      if (Files.isSameFile(token.m_experimentRoot, path)) {
        token._popEC();
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void file(final IOJob job, final ExperimentSetContext data,
      final Path path, final BasicFileAttributes attributes,
      final StreamEncoding<?, ?> encoding) throws Throwable {
    final Logger logger;

    logger = job.getLogger();
    if ((logger != null) && (logger.isLoggable(IOTool.FINER_LOG_LEVEL))) {
      logger.log(IOTool.FINER_LOG_LEVEL,//
          (("Beginning to load run from file '" //$NON-NLS-1$
          + path) + '\''));
    }

    try (final InputStream stream = PathUtils.openInputStream(path)) {
      try (final InputStream input = StreamEncoding.openInputStream(
          stream, encoding)) {
        try (final Reader reader = StreamEncoding.openReader(stream,
            encoding)) {
          if (reader instanceof BufferedReader) {
            this.__reader(job, data, path, ((BufferedReader) reader));
          } else {
            try (final BufferedReader buffered = new BufferedReader(reader)) {
              this.__reader(job, data, path, buffered);
            }
          }
        }
      }
    }
    if ((logger != null) && (logger.isLoggable(IOTool.FINER_LOG_LEVEL))) {
      logger.log(IOTool.FINER_LOG_LEVEL,//
          (("Finished loading run from file '" //$NON-NLS-1$
          + path) + '\''));
    }
  }

  /**
   * prepare a string for processing
   * 
   * @param s
   *          the string
   * @return the result
   */
  private static final String __prepare(final String s) {
    int i;
    String t;

    t = TextUtils.normalize(s);
    if (t == null) {
      return null;
    }
    i = t.indexOf(TSPSuiteInput.COMMENT_START);
    if (i == 0) {
      return null;
    }
    if (i < 0) {
      return t;
    }
    return TextUtils.prepare(t.substring(0, i));
  }

  /**
   * load the file data
   * 
   * @param job
   *          the job
   * @param data
   *          the data
   * @param file
   *          the file
   * @param reader
   *          the reader
   * @throws Throwable
   *           if it fails
   */
  private final void __reader(final IOJob job,
      final ExperimentSetContext data, final Path file,
      final BufferedReader reader) throws Throwable {
    String s;
    RunContext run;
    int state, idx;
    _TSPSuiteInputToken token;

    token = ((_TSPSuiteInputToken) (job.getToken()));

    run = null;
    state = 0;

    while ((s = reader.readLine()) != null) {
      s = TSPSuiteInput.__prepare(s);
      if (s == null) {
        continue;
      }

      if (state == 0) {
        if (TSPSuiteInput.LOG_DATA_SECTION.equalsIgnoreCase(s)) {
          state = 1;
          if (run == null) {
            run = token._beginRun(file);
          }
        } else {
          if (TSPSuiteInput.ALGORITHM_DATA_SECTION.equalsIgnoreCase(s) || //
              TSPSuiteInput.DETERMINISTIC_INITIALIZATION_SECTION
                  .equalsIgnoreCase(s)) {
            state = 2;
            if (run == null) {
              run = token._beginRun(file);
            }
          }
        }

      } else {
        if (TSPSuiteInput.SECTION_END.equalsIgnoreCase(s)) {
          state = 0;
        } else {
          if (state == 1) {
            run.addDataPoint(s);
          } else {
            if (state == 2) {
              idx = s.indexOf(':');
              if (idx <= 0) {
                continue;
              }
              run.setParameterValue(
                  TextUtils.prepare(s.substring(0, idx)),
                  TextUtils.prepare(s.substring(idx + 1)));
            }
          }

        }
      }
    }

    if (run != null) {
      run.close();
    }
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "TSPSuite Experimet Data Input"; //$NON-NLS-1$
  }

  /** the loader */
  private static final class __TSPSuiteInputLoader {

    /** the globally shared instance */
    static final TSPSuiteInput INSTANCE = new TSPSuiteInput();

    /** the list of all instance names */
    static final String[] ALL = { TSPSuiteInput.BURMA14,
        TSPSuiteInput.ULYSSES16, TSPSuiteInput.BR17, TSPSuiteInput.GR17,
        TSPSuiteInput.GR21, TSPSuiteInput.ULYSSES22, TSPSuiteInput.GR24,
        TSPSuiteInput.FRI26, TSPSuiteInput.BAYG29, TSPSuiteInput.BAYS29,
        TSPSuiteInput.FTV33, TSPSuiteInput.FTV35, TSPSuiteInput.FTV38,
        TSPSuiteInput.DANTZIG42, TSPSuiteInput.SWISS42, TSPSuiteInput.P43,
        TSPSuiteInput.FTV44, TSPSuiteInput.ATT48, TSPSuiteInput.FTV47,
        TSPSuiteInput.GR48, TSPSuiteInput.HK48, TSPSuiteInput.RY48P,
        TSPSuiteInput.EIL51, TSPSuiteInput.BERLIN52, TSPSuiteInput.FT53,
        TSPSuiteInput.FTV55, TSPSuiteInput.BRAZIL58, TSPSuiteInput.FTV64,
        TSPSuiteInput.FT70, TSPSuiteInput.ST70, TSPSuiteInput.FTV70,
        TSPSuiteInput.EIL76, TSPSuiteInput.PR76, TSPSuiteInput.GR96,
        TSPSuiteInput.RAT99, TSPSuiteInput.KRO124P, TSPSuiteInput.KROA100,
        TSPSuiteInput.KROB100, TSPSuiteInput.KROC100,
        TSPSuiteInput.KROD100, TSPSuiteInput.KROE100, TSPSuiteInput.RD100,
        TSPSuiteInput.EIL101, TSPSuiteInput.LIN105, TSPSuiteInput.PR107,
        TSPSuiteInput.GR120, TSPSuiteInput.PR124, TSPSuiteInput.BIER127,
        TSPSuiteInput.CH130, TSPSuiteInput.PR136, TSPSuiteInput.GR137,
        TSPSuiteInput.PR144, TSPSuiteInput.CH150, TSPSuiteInput.KROA150,
        TSPSuiteInput.KROB150, TSPSuiteInput.PR152, TSPSuiteInput.U159,
        TSPSuiteInput.FTV170, TSPSuiteInput.SI175, TSPSuiteInput.BRG180,
        TSPSuiteInput.RAT195, TSPSuiteInput.D198, TSPSuiteInput.KROA200,
        TSPSuiteInput.KROB200, TSPSuiteInput.GR202, TSPSuiteInput.TS225,
        TSPSuiteInput.TSP225, TSPSuiteInput.PR226, TSPSuiteInput.GR229,
        TSPSuiteInput.GIL262, TSPSuiteInput.PR264, TSPSuiteInput.A280,
        TSPSuiteInput.PR299, TSPSuiteInput.LIN318, TSPSuiteInput.RBG323,
        TSPSuiteInput.RBG358, TSPSuiteInput.RD400, TSPSuiteInput.RBG403,
        TSPSuiteInput.FL417, TSPSuiteInput.GR431, TSPSuiteInput.PR439,
        TSPSuiteInput.PCB442, TSPSuiteInput.RBG443, TSPSuiteInput.D493,
        TSPSuiteInput.ATT532, TSPSuiteInput.ALI535, TSPSuiteInput.SI535,
        TSPSuiteInput.PA561, TSPSuiteInput.U574, TSPSuiteInput.RAT575,
        TSPSuiteInput.P654, TSPSuiteInput.D657, TSPSuiteInput.GR666,
        TSPSuiteInput.U724, TSPSuiteInput.RAT783, TSPSuiteInput.DSJ1000,
        TSPSuiteInput.PR1002, TSPSuiteInput.SI1032, TSPSuiteInput.U1060,
        TSPSuiteInput.VM1084, TSPSuiteInput.PCB1173, TSPSuiteInput.D1291,
        TSPSuiteInput.RL1304, TSPSuiteInput.RL1323, TSPSuiteInput.NRW1379,
        TSPSuiteInput.FL1400, TSPSuiteInput.U1432, TSPSuiteInput.FL1577,
        TSPSuiteInput.D1655, TSPSuiteInput.VM1748, TSPSuiteInput.U1817,
        TSPSuiteInput.RL1889, TSPSuiteInput.D2103, TSPSuiteInput.U2152,
        TSPSuiteInput.U2319, TSPSuiteInput.PR2392, TSPSuiteInput.PCB3038,
        TSPSuiteInput.FL3795, TSPSuiteInput.FNL4461, TSPSuiteInput.RL5915,
        TSPSuiteInput.RL5934, TSPSuiteInput.PLA7397,
        TSPSuiteInput.RL11849, TSPSuiteInput.USA13509,
        TSPSuiteInput.BRD14051, TSPSuiteInput.D15112,
        TSPSuiteInput.D18512, TSPSuiteInput.PLA33810,
        TSPSuiteInput.PLA85900 };

    static {
      Arrays.sort(__TSPSuiteInputLoader.ALL);
    }
  }

}
