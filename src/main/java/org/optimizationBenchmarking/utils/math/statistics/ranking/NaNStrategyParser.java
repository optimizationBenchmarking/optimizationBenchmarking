package org.optimizationBenchmarking.utils.math.statistics.ranking;

import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A parser for
 * {@link org.optimizationBenchmarking.utils.math.statistics.ranking.ENaNStrategy}
 * s
 */
public final class NaNStrategyParser extends Parser<ENaNStrategy> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final NaNStrategyParser INSTANCE = new NaNStrategyParser();

  /** create the parser */
  private NaNStrategyParser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final Class<ENaNStrategy> getOutputClass() {
    return ENaNStrategy.class;
  }

  /** {@inheritDoc} */
  @Override
  public final ENaNStrategy parseString(final String string)
      throws Exception {
    final String name;

    name = TextUtils.prepare(string);
    if (name == null) {
      throw new IllegalArgumentException(//
          "ENaNStrategy name cannot be null, empty, or just consist of white space."); //$NON-NLS-1$
    }

    switch (name) {
      case "min"://$NON-NLS-1$
      case "minimum"://$NON-NLS-1$
      case "minimal": { //$NON-NLS-1$
        return ENaNStrategy.MINIMAL;
      }

      case "negativeInfinity": {//$NON-NLS-1$
        return ENaNStrategy.NEGATIVE_INFINITY;
      }

      case "positiveInfinity": {//$NON-NLS-1$
        return ENaNStrategy.POSITIVE_INFINITY;
      }

      case "max"://$NON-NLS-1$
      case "maximum"://$NON-NLS-1$
      case "maximal": { //$NON-NLS-1$
        return ENaNStrategy.MAXIMAL;
      }

      case "error"://$NON-NLS-1$
      case "exception": { //$NON-NLS-1$
        return ENaNStrategy.ERROR;
      }

      case "NaN": {//$NON-NLS-1$
        return ENaNStrategy.NAN;
      }

      default: {
        return ReflectionUtils.getInstance(ENaNStrategy.class,
            ENaNStrategy.class, name);
      }
    }
  }
}
