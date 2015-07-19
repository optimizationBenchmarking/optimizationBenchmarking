package org.optimizationBenchmarking.utils.math.statistics.ranking;

import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A parser for
 * {@link org.optimizationBenchmarking.utils.math.statistics.ranking.ETieStrategy}
 * s
 */
public final class TieStrategyParser extends Parser<ETieStrategy> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final TieStrategyParser INSTANCE = new TieStrategyParser();

  /** create the parser */
  private TieStrategyParser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final Class<ETieStrategy> getOutputClass() {
    return ETieStrategy.class;
  }

  /** {@inheritDoc} */
  @Override
  public final ETieStrategy parseString(final String string)
      throws Exception {
    final String name;

    name = TextUtils.prepare(string);
    if (name == null) {
      throw new IllegalArgumentException(//
          "ETieStrategy name cannot be null, empty, or just consist of white space."); //$NON-NLS-1$
    }

    switch (name) {
      case "min"://$NON-NLS-1$
      case "minimum"://$NON-NLS-1$
      case "minimal": { //$NON-NLS-1$
        return ETieStrategy.MINIMUM;
      }

      case "minTight"://$NON-NLS-1$
      case "minimumTight"://$NON-NLS-1$
      case "minimalTight": { //$NON-NLS-1$
        return ETieStrategy.MINIMUM_TIGHT;
      }

      case "max"://$NON-NLS-1$
      case "maximum"://$NON-NLS-1$
      case "maximal": { //$NON-NLS-1$
        return ETieStrategy.MAXIMUM;
      }

      case "avg"://$NON-NLS-1$
      case "average"://$NON-NLS-1$
      case "mean": //$NON-NLS-1$
      case "arithmeticMean": {//$NON-NLS-1$
        return ETieStrategy.AVERAGE;
      }

      default: {
        return ReflectionUtils.getInstance(ETieStrategy.class,
            ETieStrategy.class, name);
      }
    }
  }
}
