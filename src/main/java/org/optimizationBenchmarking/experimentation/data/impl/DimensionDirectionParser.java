package org.optimizationBenchmarking.experimentation.data.impl;

import org.optimizationBenchmarking.experimentation.data.spec.EDimensionDirection;
import org.optimizationBenchmarking.utils.parsers.InstanceParser;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** A parser for dimension directions */
public class DimensionDirectionParser extends
    InstanceParser<EDimensionDirection> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the human readable strings */
  private static final String[] HUMAN_READABLE;

  static {
    HUMAN_READABLE = new String[EDimensionDirection.INSTANCES.size()];

    DimensionDirectionParser.HUMAN_READABLE[//
    EDimensionDirection.DECREASING.ordinal()] = "decreasing"; //$NON-NLS-1$
    DimensionDirectionParser.HUMAN_READABLE[//
    EDimensionDirection.DECREASING_STRICTLY.ordinal()] = "strictly decreasing"; //$NON-NLS-1$

    DimensionDirectionParser.HUMAN_READABLE[//
    EDimensionDirection.INCREASING.ordinal()] = "increasing"; //$NON-NLS-1$
    DimensionDirectionParser.HUMAN_READABLE[//
    EDimensionDirection.INCREASING_STRICTLY.ordinal()] = "strictly increasing"; //$NON-NLS-1$
  }

  /** the globally shared instance */
  public static final DimensionDirectionParser INSTANCE = new DimensionDirectionParser();

  /** the dimension direction parser */
  private DimensionDirectionParser() {
    super(EDimensionDirection.class, null);
  }

  /** {@inheritDoc} */
  @Override
  public final EDimensionDirection parseString(final String string) {
    final String use;
    int index;

    use = TextUtils.prepare(string);
    if (use == null) {
      throw new IllegalArgumentException(//
          "Dimension direction name cannot be null, empty, or just consist of white space, but you provided '" //$NON-NLS-1$
              + string + '\'' + '.');
    }

    for (index = DimensionDirectionParser.HUMAN_READABLE.length; (--index) >= 0;) {
      if (DimensionDirectionParser.HUMAN_READABLE[index]
          .equalsIgnoreCase(use)) {
        return EDimensionDirection.INSTANCES.get(index);
      }
    }

    return super.parseString(use);
  }

  /**
   * Get the human readable name of the given dimension direction
   *
   * @param dir
   *          the direction
   * @return the human readable name
   */
  public static final String getHumanReadable(final EDimensionDirection dir) {
    if (dir == null) {
      throw new IllegalArgumentException(
          "Dimension direction cannot be null."); //$NON-NLS-1$
    }
    return DimensionDirectionParser.HUMAN_READABLE[dir.ordinal()];
  }
}
