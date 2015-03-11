package org.optimizationBenchmarking.experimentation.io.impl;

import java.util.LinkedHashSet;

import org.optimizationBenchmarking.experimentation.io.impl.bbob.BBOBInput;
import org.optimizationBenchmarking.experimentation.io.impl.edi.EDIInput;
import org.optimizationBenchmarking.experimentation.io.impl.tspSuite.TSPSuiteInput;
import org.optimizationBenchmarking.experimentation.io.spec.IExperimentSetInput;
import org.optimizationBenchmarking.utils.parsers.InstanceParser;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;

/**
 * A parser for experiment set input drivers.
 */
public final class ExperimentSetInputParser extends
    InstanceParser<IExperimentSetInput> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  ExperimentSetInputParser() {
    super(IExperimentSetInput.class, ExperimentSetInputParser.__prefixes());
  }

  /**
   * get the prefixes
   * 
   * @return the path prefixes
   */
  private static final String[] __prefixes() {
    final LinkedHashSet<String> paths;

    paths = new LinkedHashSet<>();
    ReflectionUtils.addPackageOfClassToPrefixList(
        ExperimentSetInputParser.class, paths);
    ReflectionUtils.addPackageOfClassToPrefixList(EDIInput.class, paths);
    ReflectionUtils.addPackageOfClassToPrefixList(TSPSuiteInput.class,
        paths);
    ReflectionUtils.addPackageOfClassToPrefixList(BBOBInput.class, paths);
    return paths.toArray(new String[paths.size()]);
  }

  /** {@inheritDoc} */
  @Override
  public final IExperimentSetInput parseString(final String string)
      throws Exception {
    if ("edi".equalsIgnoreCase(string)) { //$NON-NLS-1$
      return EDIInput.getInstance();
    }

    if ("tspsuite".equalsIgnoreCase(string) || //$NON-NLS-1$
        "tsp suite".equalsIgnoreCase(string)) { //$NON-NLS-1$
      return TSPSuiteInput.getInstance();
    }

    if ("bbob".equalsIgnoreCase(string) || //$NON-NLS-1$
        "coco".equalsIgnoreCase(string)) { //$NON-NLS-1$
      return BBOBInput.getInstance();
    }

    return super.parseString(string);
  }

  /**
   * Get the singleton instance of this parser
   * 
   * @return the tsp suite input driver parser
   */
  public static final ExperimentSetInputParser getInstance() {
    return __ExperimentSetInputParserLoader.INSTANCE;
  }

  /** the instance loader */
  private static final class __ExperimentSetInputParserLoader {
    /** the instance */
    static final ExperimentSetInputParser INSTANCE = new ExperimentSetInputParser();
  }
}
