package org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.parsers.AnyNumberParser;
import org.optimizationBenchmarking.utils.parsers.IntParser;
import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.tokenizers.WordBasedStringIterator;

/**
 * A parser which can transform a string to a property value grouper. The
 * following syntax is supported (where <code>|</code> indicates an
 * <em>exclusive or</em> choice of strings):
 * <dl>
 * <dt><code>m|? to n|?</code></dt>
 * <dd>Try to find between <code>m</code> to <code>n</code> groups. A
 * question mark (<code>?</code>) can be specified instead of either
 * <code>m</code> or <code>n</code> and indicates <em>don't care</em>, in
 * which case a reasonable default is used. The resulting groups may be
 * generated based on any grouping mode.</dd>
 * <dt>
 * <code>m|? to n|? multiples|powers|any</code></dt>
 * <dd>Try to find a grouping where the ranges are multiples or powers (or
 * anything) such that there are <code>m</code> to <code>n</code> groups. A
 * question mark (<code>?</code> ) can be specified instead of either
 * <code>m</code> or <code>n</code> and indicates <em>don't care</em>, in
 * which case a reasonable default is used.</dd>
 * <dt>
 * <code>multiples|powers of p</code></dt>
 * <dd>The grouping is to be based on powers or multiples of the number
 * <code>p</code>. The group number results automatically and cannot be
 * specified.</dd>
 * <dt>
 * <code>multiples|powers|distinct|any</code></dt>
 * <dd>Groupings are to be found which are either multiples, powers,
 * correspond to distinct values, or are any of the above. The group number
 * is <em>don't care</em> in the multiple, power, or any case and results
 * automatically in the distinct case.</dd>
 * </dl>
 */
public final class PropertyValueGrouperParser extends
    Parser<PropertyValueGrouper> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** The default value grouper parser */
  public static final PropertyValueGrouperParser DEFAULT_GROUPER_PARSER//
  = new PropertyValueGrouperParser();

  /** the don't care string */
  private static final String DONT_CARE = "?";//$NON-NLS-1$

  /** the constant for the multiples mode */
  private static final String MULTIPLES = "multiples";//$NON-NLS-1$
  /** the constant for the powers mode */
  private static final String POWERS = "powers";//$NON-NLS-1$
  /** the constant for the distinct mode */
  private static final String DISTINCT = "distinct";//$NON-NLS-1$
  /** the constant for the any mode */
  private static final String ANY = "any";//$NON-NLS-1$
  /** the constant for "of" */
  private static final String OF = "of";//$NON-NLS-1$
  /** the constant for "to" */
  private static final String TO = "to";//$NON-NLS-1$

  /** create the parser */
  private PropertyValueGrouperParser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final Class<PropertyValueGrouper> getOutputClass() {
    return PropertyValueGrouper.class;
  }

  /** {@inheritDoc} */
  @Override
  public final PropertyValueGrouper parseString(final String string)
      throws Exception {
    final String use;
    final EGroupingMode defMode;
    final int defMinGroups, defMaxGroups;
    final Number defParam;
    final WordBasedStringIterator iterator;
    boolean minMaxMode; // true=min/maxGroups, false=param defined?
    EGroupingMode mode;
    int minGroups, maxGroups;
    String current, currentLC;
    Number param;

    use = TextUtils.prepare(string);
    if (use == null) {
      throw new IllegalArgumentException(((//
          "PropertyValueGrouper definition string cannot be null, empty, or just contain white space, but is '" //$NON-NLS-1$
          + string) + '\'') + '.');
    }

    mode = defMode = //
    PropertyValueGrouper.DEFAULT_GROUPER.getGroupingMode();
    minGroups = defMinGroups = //
    PropertyValueGrouper.DEFAULT_GROUPER.getMinGroups();
    maxGroups = defMaxGroups = //
    PropertyValueGrouper.DEFAULT_GROUPER.getMaxGroups();
    param = defParam = //
    PropertyValueGrouper.DEFAULT_GROUPER.getGroupingParameter();

    try {
      iterator = new WordBasedStringIterator(use);

      current = iterator.next();
      currentLC = current.toLowerCase();

      define: {
        switch (currentLC) {
          case MULTIPLES: {
            mode = EGroupingMode.MULTIPLES;
            minMaxMode = false;
            break;
          }
          case POWERS: {
            mode = EGroupingMode.POWERS;
            minMaxMode = false;
            break;
          }
          case DISTINCT: {
            mode = EGroupingMode.DISTINCT;
            break define;
          }
          case ANY: {
            mode = EGroupingMode.ANY;
            break define;
          }
          case DONT_CARE: {
            minMaxMode = true;
            break;
          }
          default: {
            minGroups = IntParser.INSTANCE.parseInt(current);
            minMaxMode = true;
          }
        }

        if (minMaxMode) {
          current = iterator.next();
          if (!(PropertyValueGrouperParser.TO.equalsIgnoreCase(current))) {
            throw new IllegalArgumentException(
                (((//
                    '\'' + PropertyValueGrouperParser.TO) + "' expected, but '")//$NON-NLS-1$ 
                    + current)
                    + "' found.");//$NON-NLS-1$
          }

          current = iterator.next();
          if (!(PropertyValueGrouperParser.DONT_CARE.equals(current))) {
            maxGroups = IntParser.INSTANCE.parseInt(current);
          }

          if (maxGroups < minGroups) {
            throw new IllegalArgumentException(//
                "The maximum number of groups cannot be less than the minimum number of groups, but you specified "//$NON-NLS-1$
                    + maxGroups + " and " + minGroups + ", respectively.");//$NON-NLS-1$//$NON-NLS-2$
          }

          if (iterator.hasNext()) {
            current = iterator.next();
            switch (current.toLowerCase()) {
              case MULTIPLES: {
                mode = EGroupingMode.MULTIPLES;
                break;
              }
              case POWERS: {
                mode = EGroupingMode.POWERS;
                break;
              }
              case ANY: {
                mode = EGroupingMode.ANY;
                break;
              }
              default: {
                throw new IllegalArgumentException(
                    (((((((//
                        "Found '" + current) + //$NON-NLS-1$
                        "' but expected ")//$NON-NLS-1$
                        + PropertyValueGrouperParser.MULTIPLES) + ',') + ' ') + PropertyValueGrouperParser.POWERS)//
                        + ", or ") + //$NON-NLS-1$
                        PropertyValueGrouperParser.ANY);
              }
            }
          }
        } else {
          // the grouping parameter (may) follow

          if (iterator.hasNext()) {

            current = iterator.next();
            if (!(PropertyValueGrouperParser.OF.equalsIgnoreCase(current))) {
              throw new IllegalArgumentException(
                  (((//
                      '\'' + PropertyValueGrouperParser.OF) + "' expected, but '")//$NON-NLS-1$ 
                      + current)
                      + "' found.");//$NON-NLS-1$
            }

            param = AnyNumberParser.INSTANCE.parseObject(iterator.next());
          }
        }
      }

      if (EComparison.equals(mode, defMode) && //
          (minGroups == defMinGroups) && //
          (maxGroups == defMaxGroups) && //
          EComparison.equals(param, defParam)) {
        return PropertyValueGrouper.DEFAULT_GROUPER;
      }

      return new PropertyValueGrouper(mode, param, minGroups, maxGroups);
    } catch (final Throwable cause) {
      throw new IllegalArgumentException((("The string '" + string) + //$NON-NLS-1$
          "' is not a valid grouping definition."),//$NON-NLS-1$
          cause);
    }
  }
}
