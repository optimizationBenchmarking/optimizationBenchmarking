package org.optimizationBenchmarking.utils.comparison;

import org.optimizationBenchmarking.utils.parsers.InstanceParser;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** A parser for comparisons */
public class ComparisonParser extends InstanceParser<EComparison> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create the comparison parser */
  ComparisonParser() {
    super(EComparison.class, new String[] { ReflectionUtils
        .getPackagePrefix(EComparison.class) });
  }

  /** {@inheritDoc} */
  @Override
  public final EComparison parseString(final String string)
      throws Exception {
    switch (TextUtils.toLowerCase(string)) {

      case "less than": //$NON-NLS-1$
      case "less": //$NON-NLS-1$
      case "l"://$NON-NLS-1$
      case "<": {//$NON-NLS-1$
        return EComparison.LESS;
      }

      case "less or equal": //$NON-NLS-1$
      case "less than or equal to": //$NON-NLS-1$
      case "le"://$NON-NLS-1$
      case "leq"://$NON-NLS-1$
      case "\u2264"://$NON-NLS-1$
      case "<=": {//$NON-NLS-1$
        return EComparison.LESS_OR_EQUAL;
      }

      case "equal to": //$NON-NLS-1$
      case "equal": //$NON-NLS-1$
      case "eq"://$NON-NLS-1$
      case "e"://$NON-NLS-1$
      case "="://$NON-NLS-1$
      case "==": {//$NON-NLS-1$
        return EComparison.EQUAL;
      }

      case "greater or equal": //$NON-NLS-1$
      case "greater than or equal to": //$NON-NLS-1$
      case "ge"://$NON-NLS-1$
      case "geq"://$NON-NLS-1$
      case "\u2265"://$NON-NLS-1$
      case ">=": {//$NON-NLS-1$
        return EComparison.GREATER_OR_EQUAL;
      }

      case "greater than": //$NON-NLS-1$
      case "greater": //$NON-NLS-1$
      case "g"://$NON-NLS-1$
      case ">": {//$NON-NLS-1$
        return EComparison.GREATER;
      }

      case "not equal to": //$NON-NLS-1$
      case "not equal": //$NON-NLS-1$
      case "neq"://$NON-NLS-1$
      case "ne"://$NON-NLS-1$
      case "!="://$NON-NLS-1$
      case "\u2260": {//$NON-NLS-1$
        return EComparison.NOT_EQUAL;
      }
      default: {
        return super.parseObject(string);
      }
    }
  }

  /**
   * Get the singleton instance of this parser
   *
   * @return the document driver parser
   */
  public static final ComparisonParser getInstance() {
    return __ComparisonParserLoader.INSTANCE;
  }

  /** the instance loader */
  private static final class __ComparisonParserLoader {
    /** the instance */
    static final ComparisonParser INSTANCE = new ComparisonParser();
  }
}
