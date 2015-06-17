package org.optimizationBenchmarking.experimentation.attributes.functions.aggregation2D;

import java.util.ArrayList;
import java.util.List;

import org.optimizationBenchmarking.experimentation.attributes.statistics.parameters.Median;
import org.optimizationBenchmarking.experimentation.attributes.statistics.parameters.StatisticalParameter;
import org.optimizationBenchmarking.experimentation.attributes.statistics.parameters.StatisticalParameterParser;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IDimensionSet;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.text.MathematicalFunctionParser;
import org.optimizationBenchmarking.utils.parsers.Parser;
import org.optimizationBenchmarking.utils.text.tokenizers.WordBasedStringIterator;

/**
 * A parser which can translate strings of the form
 * {@code [secondary-aggregate "of"] aggregate "of" [y-transformation] y-dimension "over" [x-transformation] x-dimension}
 * to instances of
 * {@link org.optimizationBenchmarking.experimentation.attributes.functions.aggregation2D.Aggregation2DParser}
 * . Where
 * <dl>
 * <dt>{@code secondary-aggregate}</dt>
 * <dd>The optional secondary
 * {@link org.optimizationBenchmarking.experimentation.attributes.statistics.parameters.StatisticalParameter
 * statistical parameter} to be used to join the statistical parameter data
 * from the original aggregate. Let's say you want to plot the
 * {@link org.optimizationBenchmarking.experimentation.attributes.statistics.parameters.Variance
 * variance} of solution quality over runtime. You can compute these
 * variance over all
 * {@code org.optimizationBenchmarking.experimentation.data.spec.IRun runs}
 * of an
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns
 * instance runs set}. But what to do with an experiment which consists of
 * several instance run sets? In particular, what to do if these sets
 * consist of a different number of runs each? Computing the variances over
 * all runs then makes no sense. Instead, you could compute the variance
 * function for each instance runs set separately and then plot the median
 * over all of them. The
 * {@link org.optimizationBenchmarking.experimentation.attributes.statistics.parameters.Median
 * median} would then be your secondary aggregate. If you do not specify a
 * secondary aggregate, we use the median by default.</dd>
 * <dt>{@code aggregate}</dt>
 * <dd>The primary aggregate (statistical parameter) to be computed as a
 * function over the {@code y-dimension} of each
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns
 * instance runs set}. In the example above, this would be the
 * {@link org.optimizationBenchmarking.experimentation.attributes.statistics.parameters.Variance
 * variance}.</dd>
 * <dt>{@code y-dimension}</dt>
 * <dd>The dimension over which the primary {@code aggregate} should be
 * computed. This could be a solution quality dimension and must be part of
 * {@link #m_dimensions}.</dd>
 * <dt>{@code y-transformation}</dt>
 * <dd>{@code y-transformation} is an optional
 * {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
 * unary} transformation function to be applied to the {@code y-dimension}.
 * If a name for such a function, say
 * <code>"{@link org.optimizationBenchmarking.utils.math.functions.power.Lg lg}"</code>
 * is provided, it is parsed via the
 * {@link org.optimizationBenchmarking.utils.math.text.MathematicalFunctionParser}
 * . Notice that the transformation is applied before computing the
 * statistical parameter. Knowing this is important since "
 * {@code mean of ln y}" is different from "{@code ln of the mean y}". If
 * no function is provided, no transformation will be applied to the
 * {@code y-dimension}.</dd>
 * <dt>{@code x-dimension}</dt>
 * <dd>The dimension to be the {@code x}-axis of the function. This could
 * be a time dimension and must be part of {@link #m_dimensions}.</dd>
 * <dt>{@code x-transformation}</dt>
 * <dd>{@code x-transformation} is an optional
 * {@link org.optimizationBenchmarking.utils.math.functions.UnaryFunction
 * unary} transformation function to be applied to the {@code x-dimension}.
 * If a name for such a function, say
 * <code>"{@link org.optimizationBenchmarking.utils.math.functions.power.Lg lg}"</code>
 * is provided, it is parsed via the
 * {@link org.optimizationBenchmarking.utils.math.text.MathematicalFunctionParser}
 * . If no function is provided, no transformation will be applied to the
 * {@code x-dimension}.</dd>
 * </dl>
 */
public class Aggregation2DParser extends Parser<Aggregation2D> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the {@value} string */
  private static final String OF = "of"; //$NON-NLS-1$

  /** the {@value} string */
  private static final String OVER = "over"; //$NON-NLS-1$

  /** the dimension set */
  private final IDimensionSet m_dimensions;

  /**
   * Create the parser
   *
   * @param dimensions
   *          the dimension set
   */
  public Aggregation2DParser(final IDimensionSet dimensions) {
    super();
    if (dimensions == null) {
      throw new IllegalArgumentException(//
          "Dimension set cannot be null."); //$NON-NLS-1$
    }
    this.m_dimensions = dimensions;
  }

  /** {@inheritDoc} */
  @Override
  public final Class<Aggregation2D> getOutputClass() {
    return Aggregation2D.class;
  }

  /** {@inheritDoc} */
  @Override
  public final Aggregation2D parseString(final String string)
      throws Exception {
    final ArrayList<String> split;
    final Object[] transAndDim;
    final UnaryFunction xTransformation, yTransformation;
    final IDimension xDimension, yDimension;
    WordBasedStringIterator iterator;
    StatisticalParameter secondary, primary;
    String build, current;
    int index;

    try {
      split = new ArrayList<>(12);
      iterator = new WordBasedStringIterator(string);
      while (iterator.hasNext()) {
        split.add(iterator.next());
      }

      secondary = primary = null;
      index = split.indexOf(Aggregation2DParser.OF);
      if (index <= 0) {
        throw new IllegalArgumentException(((//
            "StatisticalParameter2D definition must contain 'of' somewhere inside, but is '" //$NON-NLS-1$
            + string) + '\'') + '.');
      }

      build = null;
      while ((--index) >= 0) {
        current = split.remove(0);
        if (build == null) {
          build = current;
        } else {
          build = current;
        }
      }

      primary = StatisticalParameterParser.getInstance()
          .parseString(build);

      split.remove(0);
      index = split.indexOf(Aggregation2DParser.OF);
      if (index > 0) {
        build = null;
        while ((--index) >= 0) {
          current = split.remove(0);
          if (build == null) {
            build = current;
          } else {
            build = current;
          }
        }

        secondary = primary;
        primary = StatisticalParameterParser.getInstance().parseString(
            build);
        split.remove(0);
      }

      index = split.indexOf(Aggregation2DParser.OVER);
      if (index <= 0) {
        throw new IllegalArgumentException(((//
            "StatisticalParameter2D definition must contain 'over' somewhere inside, but is '" //$NON-NLS-1$
            + string) + '\'') + '.');
      }

      transAndDim = new Object[2];
      this.__parse(split.subList(0, index), transAndDim);
      yTransformation = ((UnaryFunction) (transAndDim[0]));
      yDimension = ((IDimension) (transAndDim[1]));

      this.__parse(split.subList((index + 1), split.size()), transAndDim);
      xTransformation = ((UnaryFunction) (transAndDim[0]));
      xDimension = ((IDimension) (transAndDim[1]));

      return new Aggregation2D(xDimension, xTransformation, yDimension,
          yTransformation, primary,//
          ((secondary != null) ? secondary : Median.INSTANCE));
    } catch (final Throwable error) {
      throw new IllegalArgumentException(
          ('\'' + string) + //
              "' is not a valid StatisticalParameter2D definition. See the causing exception for more information.", //$NON-NLS-1$
          error);
    }
  }

  /**
   * parse a string list
   *
   * @param list
   *          the list
   * @param transAndDim
   *          the transformation and dimension
   */
  private final void __parse(final List<String> list,
      final Object[] transAndDim) {
    final MathematicalFunctionParser parser;
    final int size;
    int split, index;
    String string;

    size = list.size();
    parser = MathematicalFunctionParser.getInstance();
    outer: for (split = size; (--split) >= 0;) {
      transAndDim[0] = null;
      transAndDim[1] = null;

      string = list.get(split);
      for (index = split; (++index) < size;) {
        string += (' ' + list.get(index));
      }

      if ((transAndDim[1] = this.m_dimensions.find(string)) == null) {
        continue outer;
      }

      if (split > 0) {
        string = list.get(0);
        for (index = 1; index < split; index++) {
          string += (' ' + list.get(index));
        }

        try {
          transAndDim[0] = parser.parseString(string);
        } catch (final Throwable error) {
          continue outer;
        }
      }

      return; // found
    }

    throw new IllegalArgumentException("Cannot translate list " //$NON-NLS-1$
        + list + " into a [transformation] / dimension pair.");//$NON-NLS-1$
  }

}
