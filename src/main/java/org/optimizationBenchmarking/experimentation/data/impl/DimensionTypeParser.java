package org.optimizationBenchmarking.experimentation.data.impl;

import org.optimizationBenchmarking.experimentation.data.spec.EDimensionType;
import org.optimizationBenchmarking.utils.parsers.InstanceParser;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** A parser for dimension types */
public class DimensionTypeParser extends InstanceParser<EDimensionType> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the human readable strings */
  private static final String[] HUMAN_READABLE;

  static {
    HUMAN_READABLE = new String[EDimensionType.INSTANCES.size()];

    DimensionTypeParser.HUMAN_READABLE[//
    EDimensionType.ITERATION_ALGORITHM_STEP.ordinal()] = "algorithm step"; //$NON-NLS-1$
    DimensionTypeParser.HUMAN_READABLE[//
    EDimensionType.ITERATION_FE.ordinal()] = "objective function evaluation"; //$NON-NLS-1$
    DimensionTypeParser.HUMAN_READABLE[//
    EDimensionType.ITERATION_SUB_FE.ordinal()] = "sub-FE"; //$NON-NLS-1$
    DimensionTypeParser.HUMAN_READABLE[//
    EDimensionType.QUALITY_PROBLEM_DEPENDENT.ordinal()] = "quality measure (problem dependent)"; //$NON-NLS-1$
    DimensionTypeParser.HUMAN_READABLE[//
    EDimensionType.QUALITY_PROBLEM_INDEPENDENT.ordinal()] = "quality measure (problem independent)"; //$NON-NLS-1$
    DimensionTypeParser.HUMAN_READABLE[//
    EDimensionType.RUNTIME_CPU.ordinal()] = "CPU time"; //$NON-NLS-1$
    DimensionTypeParser.HUMAN_READABLE[//
    EDimensionType.RUNTIME_NORMALIZED.ordinal()] = "normalized CPU time"; //$NON-NLS-1$
  }

  /** the globally shared instance */
  public static final DimensionTypeParser INSTANCE = new DimensionTypeParser();

  /** the dimension type parser */
  private DimensionTypeParser() {
    super(EDimensionType.class, null);
  }

  /** {@inheritDoc} */
  @Override
  public final EDimensionType parseString(final String string) {
    final String use;
    int index;

    use = TextUtils.prepare(string);
    if (use == null) {
      throw new IllegalArgumentException(//
          "Dimension type name cannot be null, empty, or just consist of white space, but you provided '" //$NON-NLS-1$
              + string + '\'' + '.');
    }

    for (index = DimensionTypeParser.HUMAN_READABLE.length; (--index) >= 0;) {
      if (DimensionTypeParser.HUMAN_READABLE[index].equalsIgnoreCase(use)) {
        return EDimensionType.INSTANCES.get(index);
      }
    }

    return super.parseString(use);
  }

  /**
   * Get the human readable name of the given dimension type
   *
   * @param type
   *          the type
   * @return the human readable name
   */
  public static final String getHumanReadable(final EDimensionType type) {
    if (type == null) {
      throw new IllegalArgumentException(
          "Dimension type cannot be null."); //$NON-NLS-1$
    }
    return DimensionTypeParser.HUMAN_READABLE[type.ordinal()];
  }
}
